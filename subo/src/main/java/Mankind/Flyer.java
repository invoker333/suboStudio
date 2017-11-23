package Mankind;

import com.mingli.toms.MusicId;

import javax.microedition.khronos.opengles.GL10;

import Enviroments.GrassSet;
import Weapon.BoomBullet;
import element2.TexId;

public class Flyer extends Enemy {
	private static final int cdMAX = 180;
	float yStart;
	private BoomBullet b;
	private int cd;
//	private float lifeToChange;
	public Flyer(char bi,GrassSet gra, float x, float y) {
		super(bi,gra, x, y);
		changeLifeRate(0.5f);
//		lifeToChange=getLifeMax()-2;
		setSoundId(MusicId.flyer);
		yStart=y+1;
	}
	public void setEnemySet(EnemySet es){
		super.setEnemySet(es);
		initbullet(es);
	}
	
	void initbullet(EnemySet es) {
		this.enemySet = es;
//		b = new TailBullet(es, gra, 3.33f);
//		b = new Missile(es, gra);
//		b.loadTexture(TexId.GREEN);
		b=new BoomBullet(enemySet, gra);
		b.loadTexture(TexId.EGG);
		b.boomA.setTextureId(TexId.BOOMWHITE);
		// b.speed=20;
	}
	public void drawElement(GL10 gl){
		super.drawElement(gl);
		b.drawElement(gl);
	}
	protected void init() {
		setH(55);
		sethRate(35 / 64f);
		setTextureId(TexId.FLYER);
		super.init();
	}
	protected void afterInit(){
		setG(0.5f);
		setJumpHeight(192);
		setxSpeedMax(4);
		setxSpeedMin(-4);
		super.afterInit();
	}
	public void sizeCheck(){
		setwEdge((int) (getW() * 62 / 64f));// 左边身体宽度
		sethEdge((int) (getH() * 32 / 64f));// 60/64f=15/16
		aniStepCheck();
	}
	public void sizeCheck2(){
		setwEdge((int) (getW() * 55 / 64f));// 左边身体宽度
		sethEdge((int) (getH() * 62 / 64f));// 60/64f=15/16
		aniStepCheck();
	}
	public void attacked(int attack){
//		float life1=getLife();
		 super.attacked(attack);
//		 if(life1>=lifeToChange&&getLife()<lifeToChange)
		if(isDead&&getTextureId()==TexId.FLYER)	 toWalker();
	}
	void toWalker(){
		setLife(getLifeMax());
		isDead=false;//20170131
		
		
		angle=getDirection()==1?-90:90;
		hwChange();// change h to w w to h
		setSoundId(MusicId.walker);
		sizeCheck2();
		syncTextureSize();
		setTextureId(TexId.GREENWALKER);
		// TODO Auto-generated constructor stub
//		setxSpeedMax(8);
//		setxSpeedMin(-getxSpeedMax());
	}
	private void hwChange() {
		float h1 = h;
		h=w*0.65f;
		w=h1*(gethRate()+10f/60f);
	}
	public void attackAnotherOne(EnemySet es){// walker's code
		Creature another;
		for (int i = 0; i < es.cList.size(); i++) {
			another = es.cList.get(i);
			if (!another.isDead&&
					Math.abs(x - another.x) < another.getwEdge() + getwEdge()
					&& another.y - another.gethEdge() < y
					&& another.y + another.gethEdge() > y - gethEdge()) {
				tooClose(another, es);
			}
		}
	}
	protected void tooClose(Creature another,EnemySet es) {
		// TODO Auto-generated method stub
		super.tooClose(another, es);
		if(getTextureId()==TexId.FLYER)return;
		
		//walker's code
		float dsmax=getwEdge()+another.getwEdge();
		float tanxingxishu= 0.1f;
		float zuni=1;
		
		
		if(another.getxSpeed()/(x-another.x)>0)//baller push enemy 
			another.xPushCheck(this,  dsmax,tanxingxishu,zuni);
		
		if(xSpeed/(another.x-x)>0)//walker push player 
			xPushCheck(another, dsmax, tanxingxishu, zuni);
		if(another.getxSpeed()>getxSpeedMax())another.setxSpeed(getxSpeedMax());
		else if(another.getxSpeed()<getxSpeedMin())another.setxSpeed(getxSpeedMin());
		
	}
	
	public void randomAction() {// 周期
		if(getTextureId()==TexId.FLYER) {
				setAngle(getAngle() + -1*getDirection());
				if(getAngle()>15)rotateSpeed=-1;
				else if(getAngle()<-15)rotateSpeed=1;
				
				if(!b.isFire()&&cd++>cdMAX){b.tringer(x, y, xSpeed, ySpeed);
				cd=0;
				}
				
			if(y<yStart   ){
				jump();
			}
		}else{
			rotateSpeed=0;
			float speed=5;
			if(angle>speed)angle-=speed;
			else if(angle<-speed)angle+=speed;
			else angle=0;
		}



//		switch ((int) (10 * Math.random())) {
//		case 0:
//			turnRight();
//			break;
//		case 1:
//			turnLeft();
//			break;
//		default:
//			if (getDirection() == 1)
//				turnRight();
//			else if (getDirection() == -1)
//				turnLeft();
//			if (isAnimationFinished()) {
//				attack();break;
//			}
//			if (fdirection != 0 && getDirection() != fdirection) {
//				setDirection(fdirection);
//				attack();break;
//			}
//			jump();
//			break;
//		case 3:
//			stopMove();
//			break;
//
//		}
	}

}
