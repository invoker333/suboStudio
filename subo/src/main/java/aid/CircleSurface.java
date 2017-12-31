package aid;


import Mankind.Player;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

@SuppressLint("WrongCall")
public class CircleSurface extends SurfaceView implements Callback, Runnable {
    private Thread th;
    private SurfaceHolder sfh;
    private Canvas canvas;
    private Paint paint;
    private boolean flag;
    //固定摇杆背景圆形的X,Y坐标以及半径  
    private int LENGTH = 128;
    private int RockerCircleX = LENGTH;
    private int RockerCircleY = LENGTH;

    //摇杆的X,Y坐标以及摇杆的半径  
    private int SmallRockerCircleX = LENGTH;
    private int SmallRockerCircleY = LENGTH;

    private int SmallRockerCircleR = 30;
    private int RockerCircleR = LENGTH - SmallRockerCircleR;
    private double rad;
    public Player player;
    private int viewId;

    private void cons() {
        Log.v("Himi", "MySurfaceView");
        this.setKeepScreenOn(true);
        sfh = this.getHolder();
        sfh.addCallback(this);
        paint = new Paint();
        paint.setAntiAlias(true);
        setFocusable(true);
        setFocusableInTouchMode(true);
        if (!isInEditMode()) {
            //造成错误的代码段
            this.setZOrderOnTop(true);//beijingtoumings
            this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        }
    }

    /* protected void onDraw(Canvas c){
         draw(c);
     }*/
    public CircleSurface(Context context, AttributeSet a) {
        super(context, a);
        cons();
    }

    public CircleSurface(Context context, AttributeSet a, int b) {
        super(context, a, b);
        cons();
    }

    public CircleSurface(Context context) {
        super(context);
        cons();
    }

    public void surfaceCreated(SurfaceHolder holder) {
        th = new Thread(this);
        flag = true;
        th.start();
    }

    /*** 
     * 得到两点之间的弧度 
     */
    public double getRad(float px1, float py1, float px2, float py2) {
        rad = Math.atan2(py2 - py1, px2 - px1);
        return rad;
    }

    //    @Override
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN ||
                event.getAction() == MotionEvent.ACTION_MOVE) {
            // 当触屏区域不在活动范围内  
            if (Math.sqrt(Math.pow((RockerCircleX - (int) event.getX()), 2)
                    + Math.pow((RockerCircleY - (int) event.getY()), 2)) >= RockerCircleR) {
                //得到摇杆与触屏点所形成的角度  
                double tempRad = getRad(RockerCircleX, RockerCircleY, event.getX(), event.getY());
                //保证内部小圆运动的长度限制  
                getXY(RockerCircleX, RockerCircleY, RockerCircleR, tempRad);
            } else {//如果小球中心点小于活动区域则随着用户触屏点移动即可  
                SmallRockerCircleX = (int) event.getX();
                SmallRockerCircleY = (int) event.getY();
            }
            player.CircleDown((float) -rad, viewId, (int) event.getX() - SmallRockerCircleX, (int) event.getY() - SmallRockerCircleY);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            //当释放按键时摇杆要恢复摇杆的位置为初始位置  
            SmallRockerCircleX = LENGTH;
            SmallRockerCircleY = LENGTH;
            player.CircleUp();
        }

        invalidate();
        return true;
    }

    /**
     * @param R       圆周运动的旋转点
     * @param centerX 旋转点X
     * @param centerY 旋转点Y
     * @param rad     旋转的弧度
     */
    public void getXY(float centerX, float centerY, float R, double rad) {
        //获取圆周运动的X坐标   
        SmallRockerCircleX = (int) ((R * Math.cos(rad)) + centerX);
        //获取圆周运动的Y坐标  
        SmallRockerCircleY = (int) ((R * Math.sin(rad)) + centerY);
    }

    public void draw() {
        try {
            canvas = sfh.lockCanvas();
            onDraw(canvas);
        } catch (Exception e) {
            // TODO: handle exception  
        } finally {
            try {
                if (canvas != null)
                    sfh.unlockCanvasAndPost(canvas);
            } catch (Exception e2) {
            }
        }
    }

    public void onDraw(Canvas canvas) {
//        try {  
//            canvas.drawColor(Color.WHITE);  
        paint.setColor(0xdddddddd);
        canvas.drawCircle(RockerCircleX, RockerCircleY, LENGTH, paint);
        //设置透明度
        paint.setColor(0x70000000);
        //绘制摇杆背景
        canvas.drawCircle(RockerCircleX, RockerCircleY, RockerCircleR, paint);
        paint.setColor(0x70ff0000);
        //绘制摇杆
        canvas.drawCircle(SmallRockerCircleX, SmallRockerCircleY,
                SmallRockerCircleR, paint);
//        } catch (Exception e) {  
        // TODO: handle exception
//        } finally {  
//        }  
    }

    public void run() {
        // TODO Auto-generated method stub  
        while (flag) {
            draw();
            try {
                Thread.sleep(16);
            } catch (Exception ex) {
            }
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.v("Himi", "surfaceChanged");
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        flag = false;
        Log.v("Himi", "surfaceDestroyed");
    }
}