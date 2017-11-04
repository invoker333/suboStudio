package Weapon;

import java.util.ArrayList;

import Enviroments.GrassSet;
import Mankind.Creature;
import Mankind.EnemySet;

import com.mingli.toms.MusicId;

public class ShotGun extends Gun{

	private int count=5;
	public ShotGun(EnemySet es, GrassSet gra,Creature c, int bCount) {
		super(es, gra, c, bCount);
//		count = bCount-1;
		cd=3*super.cd;
	
//		bSpeed = World;// 射速系数
		setSoundId(MusicId.shotGun);
	}
	public boolean gunCheck(float ex, float ey){
		gunCheck(ex,ey,count);
		return false;
	}
	public void gunCheck(float ex, float ey,int count){
		super.gunCheck(ex, ey);
		otherBulletCheck(count);
	}
	public void alwaysFire(){
		super.alwaysFire();
		otherBulletCheck(count);
	}
	public void gunCheck(float angle){
		super.gunCheck(angle);
		otherBulletCheck(count);
		
	}
	private void otherBulletCheck(int count) {
//		this.count=count-1;//去掉上个方法触发的一发
		double superAngle=getAngle();
		for(int i=0;i<count;i++){
			if(bulletIndex>=bList.size())bulletIndex=0;//子弹重置计数器
			Bullet b = bList.get(bulletIndex++);
			
			//180=pi 45=pi/4=0.8 30=0.6
			if (b.isFire())
				return;// 子弹停止的时候
			double angle=0.6*(Math.random()-0.5)+superAngle;
			double cos = Math.cos(angle);
			double sin = Math.sin(angle);
			b.tringer(x, y, bSpeed * cos, bSpeed * sin);
		}
		playSound2();
		setAngle(superAngle);
	}
	private void playSound2() {
		super.playSound();
	}
	// these two mathod to avoid playSound twice or do not play sound
	public void playSound(){	}
	
	protected void setBullet(int bCount) {
		bList = new ArrayList<Bullet>();
//		LightSpotSet lss=new BoomSet(10);
		for (int i = 0; i < bCount; i++) {
//			bList.add(i, new TailBullet(enemySet, 100));// 子弹敌对势力
			bList.add(i, new ToBigBullet(enemySet, gra));// 子弹敌对势力
		}
		loadTexture();
	}
	/*protected void setBullet(int bCount) {
		bList = new ArrayList<Bullet>();
		LightSpotSet lss=new BoomSet(10);
		for (int i = 0; i < bCount; i++) {
//			bList.add(i, new TailBullet(enemySet, 100));// 子弹敌对势力
			bList.add(i, new Missile(enemySet, 100, lss));// 子弹敌对势力
		}
		loadTexture();
	}*/
}
