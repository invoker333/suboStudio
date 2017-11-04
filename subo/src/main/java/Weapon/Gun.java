package Weapon;

import com.mingli.toms.MusicId;
import com.mingli.toms.Render;
import com.mingli.toms.World;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import Enviroments.GrassSet;
import Mankind.Creature;
import Mankind.EnemySet;
import element2.Set;
import element2.TexId;

public class Gun extends Set {// 子弹

	protected int bulletIndex;// 子弹计数器
	public float gunLength = 64;// 枪长系数
	float x, y;// 枪口位置
	public double bSpeed = World.baseBSpeed;// 射速系数
	public ArrayList<Bullet> bList;
	protected ArrayList<Creature> sList;
	protected Creature player;
	protected float et = 120;// [释义] error-tolerant rate;技能与普攻的触发范围差
	protected EnemySet enemySet;
//	private Bullet mainBullet;
	private double angle;
	protected double cos;
	protected double sin;
	public int cd=7;
	protected GrassSet gra;

	// ParticleSet ps;
	// protected Context context;
	public Gun(EnemySet es,GrassSet gra,Creature c, int bCount) {
		this.enemySet = es;
		this.gra = gra;
		this.sList = es.cList;// 着弹对象
		this.player = c;
		setBullet(bCount);
		// ps=new ParticleSet(gra, count);
	}


	protected void setBullet(int bCount) {
		bList = new ArrayList<Bullet>();
		for (int i = 0; i < bCount; i++) {
			bList.add(i,new Bullet(enemySet,gra));// 子弹敌对势力
		}
		loadTexture();
		setSoundId(MusicId.bubble);
	}

	public void gunCheck(float angle){
		this.angle=angle;
		
		if (bulletIndex >= bList.size())
			bulletIndex = 0;// 子弹重置计数器
		
		Bullet firstBullet = bList.get(bulletIndex++);
//		Log.i("mBullet","frame"+mainBullet.frame+" fire "+mainBullet.fire);
		if (firstBullet.isFire())
			return;// 子弹停止的时候
		
		

		
		playSound();
		tringerCheck(firstBullet);
	}
	public boolean gunCheck(float ex, float ey) {
		if (bulletIndex >= bList.size())
			bulletIndex = 0;// 子弹重置计数器
		
		Bullet firstBullet = bList.get(bulletIndex++);
//		Log.i("mBullet","frame"+mainBullet.frame+" fire "+mainBullet.fire);
		if (firstBullet.isFire())
			return false;// 子弹停止的时候
		
		ex = Render.px + ex;
		ey = Render.py + ey;
		
		double dx = ex - player.x;
		double dy = ey - player.y;
		angle = Math.atan2(dy, dx);
		
		double s = 500;
		if (s < et) return false; // 只在范围内才发射

		
		playSound();
		
		tringerCheck(firstBullet);
		return true;
	}

	protected void tringerCheck(Bullet bullet,float x,float y) {
		culCosSin();
		bullet.tringer(x, y, bSpeed * cos, bSpeed * sin);
	}
	private void culCosSin() {
		cos = Math.cos(angle);
		sin = Math.sin(angle);
	}
	protected void tringerCheck(Bullet bullet) {
		culCosSin();
		
		final int gunY=10;// gun center to gun mouth
		
		x = (float) (gunLength * cos + player.x);
		y = (float) (gunLength * sin + player.y+gunY);// 枪长更新
		
		bullet.tringer(x, y, bSpeed * cos, bSpeed * sin);
	}
	public void alwaysFire() {// luan kai qiang
		if (bulletIndex >= bList.size())
			bulletIndex = 0;// 子弹重置计数器
		Bullet bullet = bList.get(bulletIndex++);
		if (bullet.isFire())
			return;// 子弹停止的时候
		x = (float) (gunLength * cos + player.x);
		y = (float) (gunLength * sin + player.y);// 枪长更新
		bullet.tringer(x, y, bSpeed * cos, bSpeed * sin);
	}
	public void loadTexture() {
		// 使用统一纹理贴图
		 setTextureId(TexId.BULLET);
		 for(int i=0;i<bList.size();i++){
			 Bullet b=bList.get(i);
//		 	b.setTextureId(TexId.BULLET);
		 	b.setTexture();
		 }
//		Bullet bullet;
//		for (int i = 0; i < bList.size(); i++) {
//			bullet = bList.get(i);
//			int j = (int) (3 * Math.random());
//			switch (j) {
//			case 0:
//				bullet.setTextureId(TexId.RED);
//				break;
//			case 1:
//				bullet.setTextureId(TexId.GREEN);
//				break;
//			case 2:
//				bullet.setTextureId(TexId.BLUE);
//				break;
//			}
//			bullet.setTexture();
//		}
	}

	public void drawElement(GL10 gl) {
		for (int i = 0; i < bList.size(); i++) {
			if(bList.get(i).isFire())
			bList.get(i).drawElement(gl);
		}
	}

	public EnemySet getEnemySet() {
		return enemySet;
	}

	public void setEnemySet(EnemySet enemySet) {
		this.enemySet = enemySet;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
//		Log.d("angleGun", ""+angle);
	}


	public void setGunLength(float gunLength) {
		// TODO Auto-generated method stub
		this.gunLength=gunLength;
	}

	


}
