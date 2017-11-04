package Weapon;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import Element.Animation;
import Element.Boom;
import Element.LightSpot;
import Mankind.Creature;
import Mankind.EnemySet;
import aid.Log;

public class BoomAttack {
	private final Boom ls;

	public BoomAttack() {
		// TODO Auto-generated constructor stub
		ls = new Boom();
	}
	protected void gotTarget(Creature enemy,Animation bullet, ArrayList<Creature> eList, EnemySet es,int attack){
		ls.tringer(bullet.x, bullet.y);
		Log.i("boom.x"+bullet.x+"  y  "+bullet.y);
		ls.angle=(float) (360f*Math.random());
//		super.gotTarget(enemy);
		
//		int size=bullet.eList.size();//to avoid if the size is changed in attacking
		for (int i = 0; i < eList.size(); i++) {
			enemy = eList.get(i);
			if (!enemy.isDead)

				if (Math.pow(enemy.x-bullet.x,2)+Math.pow(enemy.y-bullet.y,2)<Math.pow(64+Math.max(enemy.getwEdge(),enemy.gethEdge()),2)) {
					es.attacked(enemy, attack);
				}
		}
	}
	public void drawElement(GL10 gl){
		ls.drawElement(gl);
	}
}
