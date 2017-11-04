package Weapon;

import javax.microedition.khronos.opengles.GL10;

import Enviroments.Grass;
import Enviroments.GrassSet;
import Mankind.Creature;
import Mankind.EnemySet;
import element2.TexId;

public class BoomBullet extends  Bullet{

	BoomAttack boomA;
	BulletCreature c;
	public BoomBullet(EnemySet es,GrassSet gra) {
		super( es,gra);
		// TODO Auto-generated constructor stub
		setSize(20, 20);
		setTextureId(TexId.BULLET);
		boomA=new BoomAttack();
		initCreature(gra);

	}

	private void initCreature(final GrassSet gra) {
		c=new BulletCreature(gra,this);

	}

//	public void gravity(){
//		final float g=1;
//		ySpeed-=g;
//		super.gravity();
//	}

	boolean grassCheck(){
		if(c.grassCheck())gotTarget(es.enemyGrass);
		return c.gotTar;
	}

	protected void gotTarget(Creature enemy){
		if(
		Math.abs(c.getxSpeed())+Math.abs(c.getySpeed()) <3
						||enemy!=es.enemyGrass){
			boomA.gotTarget(enemy,this,eList,es,attack);
			resetBullet();
			c.setPosition(x,y);
		}

	}
	public void tringer(float x, float y, double sx, double sy) {
		super.tringer(x,y,sx,sy);
		c.tringer(x,y,sx,sy);
	}
	public void drawElement(GL10 gl){
		boomA.drawElement(gl);
		super.drawElement(gl);
	}

	
}