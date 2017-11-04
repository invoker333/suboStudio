package Weapon;

import javax.microedition.khronos.opengles.GL10;

import Enviroments.Grass;
import Enviroments.GrassSet;
import Mankind.Creature;
import Mankind.EnemySet;

import com.mingli.toms.MusicId;
import com.mingli.toms.World;

public class Missile extends TailBullet{


	private final GrassDestoryer grassDestoryer;
	BoomAttack boomA;
	public Missile(EnemySet es,GrassSet gra) {
		super(es,gra);
		attack=4*World.baseAttack;
		// TODO Auto-generated constructor stub
		setSize(12, 12);
		boomA=new BoomAttack();
		grassDestoryer=new GrassDestoryer(gra,this);
	}
	
	void shot(){
		super.shot();		
		final float a=1.03f;
		xSpeed*=a;
		ySpeed*=a;
	}
	
	protected void gotTarget(Creature enemy){
		boomA.gotTarget(enemy,this,eList,es,attack);
		if(enemy==es.enemyGrass){
			grassDestoryer.destory(x,y,grassId);
		}
		resetBullet();
	}

	public void drawElement(GL10 gl){
		boomA.drawElement(gl);
		super.drawElement(gl);
	}

}

 