package Mankind;

import com.mingli.toms.World;

import javax.microedition.khronos.opengles.GL10;

import Enviroments.GrassSet;
import Weapon.GunDraw;
import element2.TexId;

/**
 * Created by Administrator on 2017/10/22.
 */

public class PlayerFat extends Player {


    private boolean rotateBody;

    public PlayerFat(char mapSign, GrassSet gra, World world, float x, float y) {
        super(mapSign, gra, world, x, y);
        cap.setTextureId(TexId.CAPJIANCI);

        realBlade.angleAMax = 2f;
//        realBlade.initLengthAndSpeed(120,-60);
        realBlade.setTextureId(TexId.QIGAN);
        haveGunDraw = new GunDraw(0, this, -32, -23, 96, 23, -10, 10, TexId.PAOTA, 1, 5);
        drawAngleSpeed = 7.5f;//90' 60*2 /根号(128*2)
//        setJumpHeight(192*3/4);
    }

    @Override
    protected void attack() {
        if (blade == null) return;
//        else{
//            blade.playSound();
//            blade.targetCheck();
//        }
        if (blade.getAngle() + PlayerFat.this.angle > blade.angleEnd) super.attack();
    }


    public void drawElement(GL10 gl) {
//
        if (fallen && !sitDownLand) {//角度回归
            {
                angle = angle % 360;
//				if(angle>180)angle=angle-360;
//				else
                if (angle < -180) angle = 360 + angle;
            }
            if (angle > drawAngleSpeed) angle -= 2 * drawAngleSpeed;
            else if (angle < -drawAngleSpeed) angle += 2 * drawAngleSpeed;
            else angle = 0;

        } else if(rotateBody){
            if (!sitDownAir) {
                if (angle > -90)
                    angle -= drawAngleSpeed;
            } else {
                if (angle > -180)
                    angle -= drawAngleSpeed;
            }
        }
        super.drawElement(gl);
    }

    public boolean isAnimationFinished() {
        if (!fallen) return false;
        return super.isAnimationFinished();
    }

    @Override
    protected void tooDown() {
        rotateBody=false;
        if (ySpeed < vtDestory) {
            getGra().up(getLandId(), xSpeed, ySpeed);
            goreEnemyCheck();
        }
        if (angle <= -180){
           if(gaoTime<1) gaoTime=1;//只能是1 太多会无线无敌
            downActionMoved = true;//destory land wall
        }
        sitDownAir = false;
        super.tooDown();

    }
    public void jump(float rate){
        super.jump(rate);
        sitDownAir=true;
        if(rate>0.33){
            rotateBody=true;
        }
    }

    @Override
    protected void tooLeft() {
        if (xSpeed <= getxSpeedMin()) {
            getGra().up(getCollisionId(), xSpeed, ySpeed);
            goreEnemyCheck();
        }
        super.tooLeft();
    }

    @Override
    protected void tooRight() {
        if (xSpeed >= getxSpeedMax()) {
            getGra().up(getCollisionId(), xSpeed, ySpeed);
            goreEnemyCheck();
        }
        super.tooRight();
    }
}
