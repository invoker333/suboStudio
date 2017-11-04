package Weapon;

import Element.Animation;
import Element.AnimationMove;
import Enviroments.GrassSet;
import Mankind.Creature;

/**
 * Created by Administrator on 2017/9/2.
 */

public class BulletCreature extends Creature {
    boolean gotTar;
    private Animation b;

    public BulletCreature(GrassSet gra, Animation b) {
        super(gra);
        setAf(0.3f);
        this.b = b;
        sethEdge(b.gethEdge());
        setH(b.getH());
        setW(b.w);
        setwEdge(b.getwEdge());
    }

    @Override
    protected void tooLeft() {
        setxPro(xdata[2] + wEdge);
        xSpeed=0.7f*Math.abs(xSpeed);
        gotTar=true;
    }
    protected void tooRight(){
        setxPro(xdata[0] - wEdge);// 牵扯到向下取整
        xSpeed=-0.7f*Math.abs(xSpeed);
        gotTar=true;
    }

    @Override
    protected void tooDown() {
        fallen = true;//摩擦计算
        setyPro(ydata[3] +gethEdge());
        ySpeed=0.7f*Math.abs(ySpeed);
        gotTar=true;
    }

    @Override
    protected void tooHigh() {
        scaleTringer(getW());
        setyPro(ydata[1] - gethEdge());
        ySpeed=-0.7f*Math.abs(ySpeed);
        gotTar=true;
    }

    public boolean grassCheck() {
        gotTar=false;
        move();gravity();
        b.setPosition(x,y);
        return gotTar;
    }
    public void tringer(float x, float y, double sx, double sy) {
        setPosition(x, y); // 初始位置重置 start==true
        setSpeed(sx, sy);
    }
}
