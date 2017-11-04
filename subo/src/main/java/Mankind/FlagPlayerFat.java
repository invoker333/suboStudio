package Mankind;

import com.mingli.toms.World;

import Enviroments.GrassSet;
import element2.Tail;
import element2.TexId;

/**
 * Created by Administrator on 2017/10/22.
 */

public class FlagPlayerFat extends PlayerFat {
    public FlagPlayerFat(char bi, GrassSet grassSet, World world, float x, float y) {
        super(bi, grassSet, world, x, y);
        setFlag();
        downData[2]=true;
        setPosition(x, y);
    }
    private void setFlag() {
        wudiTime=0;


        haveBlade();
        noGun();
        downData[0]=true;
        realBlade.tail=new Tail(25,TexId.REDCREEPER);
        realBlade.tail.setTextureId(TexId.CREEPER);
        realBlade.setTextureId(TexId.QIGAN);
//		realBlade.angleStart=60;
        realBlade.angleStart =105;
        realBlade.angleEnd =75;
        realBlade.h=4;
        realBlade.angleAMax=0.1f;
        realBlade.loadTexture();
    }
    protected void tooRight() {
        super.tooRight();
//		setAnimationFinished(true);
        downData[0]=true;
        downData[1]=false;
    }
    protected void tooLeft() {
        super.tooLeft();
//		setAnimationFinished(true);
        downData[0]=false;
        downData[1]=true;
    }
    //	public void drawElement(GL10 gl){
//		super.drawElement(gl);
//		setViewPot();
//	}
    public void die(){goal.hasFirstBlood=false;}// avoid gameOver
    void sendIcon(int i){world.sendMessage(World.NOTREADICON);}
    //	public void quitgame(){
//		for(int i=0;i<downData.length;i++){
//			downData[i]=false;
//		}
//	}
    public void sendBattleMessage() {}
}
