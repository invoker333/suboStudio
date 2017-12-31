package Mankind;

import com.mingli.toms.World;

import Enviroments.GrassSet;
import element2.Tail;
import element2.TexId;

public class FlagPlayer extends Player{

	public FlagPlayer(char bi, GrassSet grassSet, World world,float x, float y) {
		// TODO Auto-generated constructor stub
		super(bi, grassSet,world, x, y);
		setFlag();
		KEY_ATTACK_DOWN=true;
		setPosition(x, y);
	}
	private void setFlag() {
		wudiTime=0;
		
		
		haveBlade();
		noGun();
		KEY_LEFT_DOWN=true;
		realBlade.tail=new Tail(25,TexId.REDCREEPER);
		realBlade.tail.setTextureId(TexId.FLAG);
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
		KEY_LEFT_DOWN=true;
		KEY_RIGHT_DOWN=false;
	}
	protected void tooLeft() {
		super.tooLeft();
//		setAnimationFinished(true);
		KEY_LEFT_DOWN=false;
		KEY_RIGHT_DOWN=true;
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
