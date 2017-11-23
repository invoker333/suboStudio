package Mankind;

import com.mingli.toms.World;

import Enviroments.GrassSet;
import element2.Tail;
import element2.TexId;

public class FlagPlayerWisper extends PlayerWisper{

    public FlagPlayerWisper(char bi, GrassSet grassSet, World world, float x, float y) {
        // TODO Auto-generated constructor stub
        super(bi, grassSet,world, x, y);
        setFlag();
        setPosition(x, y);
        jump(1);
    }
    private void setFlag() {
        wudiTime=0;
        haveBlade();
        noGun();
        downData[0]=true;
        realBlade.tail=new Tail(35,TexId.RAINBOW);
//		realBlade.setTextureId(TexId.QIGAN);
        realBlade.angleAMax=0.1f;
//		realBlade.h=4;
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
    public void die(){goal.hasFirstBlood=false;}// avoid gameOver
    void sendIcon(int i){world.sendMessage(World.NOTREADICON);}
    //	public void quitgame(){
//		for(int i=0;i<downData.length;i++){
//			downData[i]=false;
//		}
//	}
    public void sendBattleMessage() {}
}