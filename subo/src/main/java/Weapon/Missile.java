package Weapon;

import com.mingli.toms.World;

import javax.microedition.khronos.opengles.GL10;

import Element.BoomSet;
import Enviroments.GrassSet;
import Mankind.Creature;
import Mankind.EnemySet;
import element2.TexId;

public class Missile extends TailBullet{


	private final GrassDestoryer grassDestoryer;
	BoomAttack boomA;
	BoomSet bs;
	public Missile(EnemySet es,GrassSet gra) {
		super(es,gra);
		attack=4*World.baseAttack;
		// TODO Auto-generated constructor stub
		setSize(12, 12);
		boomA=new BoomAttack();
		grassDestoryer=new GrassDestoryer(gra,this);
		bs=new BoomSet(10, TexId.FOG);
	}
	
	void shot(){
		super.shot();		
		final float a=1.03f;
		xSpeed*=a;
		ySpeed*=a;
		if(Math.pow(boomx-x,2)+Math.pow(boomy-y,2)>4000){
			bs.tringer(x,y);
			boomx=x;
			boomy=y;
		}
	}
	float boomx,boomy;
	protected void gotTarget(Creature enemy){
		boomA.gotTarget(enemy,this,eList,es,attack);
		if(enemy==es.enemyGrass){
			grassDestoryer.destory(x,y,grassId);
		}
		resetBullet();
	}

	public void drawElement(GL10 gl){
		boomA.drawElement(gl);
		bs.drawElement(gl);
		super.drawElement(gl);
	}

}

 