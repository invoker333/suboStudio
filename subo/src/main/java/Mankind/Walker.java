package Mankind;

import com.mingli.toms.MusicId;

import Enviroments.GrassSet;
import element2.Joint;
import element2.TexId;

public class Walker extends JointEnemy {

	protected void initJoint() {
		float x0 = getW();
		float y0 = getH();

		float footHeight=-10;
		jointList.add(foot1=new Joint(this, -9, -24, 9, 0, 9, footHeight, TexId.FOOT));
		blade=noBlade;

		jointList
				.add(body=new Joint(this, -x0, -y0, x0, y0, 0, 11, TexId.WALKER, 1, 5));
//		jointList.add(expression=new Joint(4, this, -20, -24, 20, 24, 16, 18,				TexId.EXPRESSIONENEMY, 1, 5));

		jointList.add(foot=new Joint(this, -9, -24, 9, 0, -9, footHeight, TexId.FOOT, -1));
		normalHand=new Joint( this, -11, -23, 11, 5, -15, -0,				TexId.HAND);
		jointList.add(normalHand);// 38
	}


	public Walker(char bi,GrassSet gra, float x, float y) {
		super(bi,gra, x, y);
		setSoundId(MusicId.walker);
		
		setxSpeedMax(getxSpeedMax()/4);
		setxSpeedMin(-getxSpeedMax());
	}
	public void sizeCheck(){
		setwEdge((int) (getW() * 50 / 64f));// 左边身体宽度
		sethEdge((int) (getH() * 56 / 64f));// 60/64f=15/16
		aniStepCheck();
	}
	public void init(){
		setH(0.6f*getH());
		w*=0.6f;
		setTextureId(TexId.WALKER);
		super.init();
	}
	public void attackAnotherOne(EnemySet es){
		Creature another;
		for (int i = 0; i < es.cList.size(); i++) {
			another = es.cList.get(i);
			if (Math.abs(x - another.x) < another.getwEdge() + getwEdge()
					&& another.y - another.gethEdge() < y
					&& another.y + another.gethEdge() > y - gethEdge()) {
				tooClose(another, es);
				
			}
		}
	}
	
	protected void tooClose(Creature another,EnemySet es) {
		// TODO Auto-generated method stub
		float dsmax=getwEdge()+another.getwEdge();
		final float tanxingxishu= 0.1f;
		final float zuni=1;
//		xWheelCheck(another, dsmax,tanxingxishu,zuni);
//		another.xWheelCheck(this,  dsmax*2,tanxingxishu,zuni);
		
		if(xSpeed/(another.x-x)>0)// faceto
			xPushCheck(another, dsmax, tanxingxishu, zuni);
		
		if(another.getxSpeed()>getxSpeedMax())another.setxSpeed(getxSpeedMax());
		else if(another.getxSpeed()<getxSpeedMin())another.setxSpeed(getxSpeedMin());
	}
}
