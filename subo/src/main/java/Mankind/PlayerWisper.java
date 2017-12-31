package Mankind;

import com.mingli.toms.World;

import javax.microedition.khronos.opengles.GL10;

import Enviroments.GrassSet;
import Weapon.AutoBubbleGun;
import Weapon.Blade;
import element2.TexId;

public class PlayerWisper extends Player {
    private boolean highEnoughToFly;
    private float standHeightEdge;
    AutoBubbleGun abg;
    private boolean KEY_DIRECTION_DOWN;

    float cosKey, sinKey;

    public PlayerWisper(char mapSign, GrassSet gra, World world, float x, float y) {
        super(mapSign, gra, world, x, y);
        cap.setTextureId(TexId.FENSHEN);

        realBlade = new Blade(this, realBlade.getEs()) {
            protected void gotEnd() {
                super.gotEnd();
                abg.gunCheck(direction == 1 ? angleEnd : -angleEnd);
            }
        };
        expression.setTextureId(TexId.EXPRESSIONENEMY);
        extendsBladeFruitId = 0;//only is not -1 can have blade  and  have blade()  can not use sendEmptyMessage
        haveGunDraw.setTextureId(TexId.GOAL);
    }
    void actCheck(Creature controller) {
        super.actCheck(controller);

        if(highEnoughToFly){
           if(KEY_DIRECTION_DOWN){
               xSpeed=getxSpeedMax() * cosKey;
               ySpeed=getySpeedMax() * sinKey;
           }
            else {
               xSpeed+=xSpeed>0?-afLand:afLand;
               ySpeed+=ySpeed>0?-getG() :getG();
           }
        }
    }

    public void jump(float rate) {
        super.jump(rate);
        if (rate > 0.33) {
            highEnoughToFly = true;
        }
    }
    public void reLife(int time){
        super.reLife(time);
        haveBlade();
    }

    protected void tooDown() {
        super.tooDown();
        if (highEnoughToFly) {
            highEnoughToFly = false;
            flyId=0;
        }
    }

    public void drawElement(GL10 gl) {
        abg.drawElement(gl);
        super.drawElement(gl);

    }

    int flyId=0;
    final int flyIntMax=180;
    @Override
    public void gravity() {
        super.gravity();
        if (highEnoughToFly) {
            if(flyId++<flyIntMax){
                ySpeed+=getG();//fly
            }
        }
    }

    public void setEnemySet(EnemySet es){
        super.setEnemySet(es);
        if(abg==null){
            abg = new AutoBubbleGun(enemySet, gra, this, 2);
            abg.setBulletTextureId(TexId.FENSHEN);
            if(abg!=null)abg.setEnemySet(es);
        }
    }
    void cirlceUpDirection() {
        super.cirlceUpDirection();
        KEY_DIRECTION_DOWN=false;
    }
    void cirlceDownDirection(float rad, int dx, int dy) {
        super.cirlceDownDirection(rad, dx, dy);
        cosKey = (float) Math.cos(rad);
        sinKey =(float) Math.sin(rad);
        KEY_DIRECTION_DOWN=true;
    }
}
