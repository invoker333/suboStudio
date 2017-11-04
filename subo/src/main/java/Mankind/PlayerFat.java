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


    public PlayerFat(char mapSign, GrassSet gra, World world, float x, float y) {
        super(mapSign, gra, world, x, y);
        cap.setTextureId(TexId.CREEPER);

        realBlade.angleAMax=2f;
//        realBlade.initLengthAndSpeed(120,-60);
        realBlade.setTextureId(TexId.QIGAN);
        haveGunDraw= new GunDraw(0, this, -32, -23, 96, 23, -10, 10,				TexId.PAOTA, 1, 5);
        drawAngleSpeed=2.2f;//90' 60*2 /^384
//        setJumpHeight(192*3/4);
    }

    @Override
    protected void attack() {
        if(blade==null)return;
//        else{
//            blade.playSound();
//            blade.targetCheck();
//        }
         if(blade.getAngle()+PlayerFat.this.angle>blade.angleEnd)super.attack();
    }


    public void drawElement(GL10 gl) {
        if(fallen){//角度回归

			{
				angle=angle%360;
//				if(angle>180)angle=angle-360;
//				else
				    if(angle<-180)angle=360+angle;
			}
			if(angle>drawAngleSpeed)angle-=2*drawAngleSpeed;
			else if(angle<-drawAngleSpeed)angle+=2*drawAngleSpeed;
			else   angle=0;

        }
        else {
            angle-=drawAngleSpeed;
        }
        super.drawElement(gl);
    }


    @Override
    protected void tooDown() {
        if(ySpeed<vtDestory){
            getGra().up(getLandId(), xSpeed,ySpeed);
            goreEnemyCheck();
        }
        super.tooDown();
    }

    @Override
    protected void tooLeft() {
        if(xSpeed<=getxSpeedMin()){
            getGra().up(getCollisionId(), xSpeed,ySpeed);
            goreEnemyCheck();
        }
        super.tooLeft();
    }

    @Override
    protected void tooRight() {
        if(xSpeed>=getxSpeedMax()){
            getGra().up(getCollisionId(), xSpeed,ySpeed);
            goreEnemyCheck();
        }
        super.tooRight();
    }
}
