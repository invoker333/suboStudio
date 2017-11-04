package Mankind;

import com.mingli.toms.World;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import Element.Animation;
import Element.BloodSet;
import Enviroments.GrassSet;
import Menu.State;
import element2.Set;
import element2.TexId;

public class EnemySet extends Set {
	private static final int _256 = 256;
	
	public ArrayList<Creature> cList=new ArrayList<Creature>();
	// 炮台和人是分开的
	public ArrayList<Emplacement> emplacementList;

	BloodSet bloodSet;
	private boolean CHASE_MODEL;
	protected Creature player;// 缓存
	private EnemySet enemySet;
	State lifecolumn;
	State backLife;
	float bloodW = 100;
	float bloodH = 16;
	private Animation guideCircle;
	private Creature systemAttacker;// to avoid attacker is null
	public Creature enemyGrass;

	public EnemySet(GrassSet gra) {
		this.cList = gra.getEnemyList();
		this.emplacementList = gra.getEmplacementList();
		loadSound();
		bloodSet = new BloodSet(15);

		lifecolumn = new State(0, 0, bloodW, bloodH);
		lifecolumn.loadTexture();
		final int edge = 3;
		backLife = new State(-edge, -edge, bloodW + edge, bloodH + edge);
		backLife.loadTexture(TexId.BLACK);
		initGuideCircle();
	
		
		enemyGrass=new EnemyGrass(gra, 0, 0);
		
	}

	public void setPlayer(Creature creature) {
		this.player = creature;
		this.setSystemAttacker(player);
	}
	
	private void initGuideCircle() {
		// TODO Auto-generated method stub
		guideCircle = new Animation();
		guideCircle.setW(_256);
		guideCircle.setH(_256);
		guideCircle.loadTexture(TexId.GUIDECIRCLE);
	}
	public void resume() {
		super.resume();
		for (int i = 0; i < cList.size(); i++)
			cList.get(i).resume();
	}

	public void pause() {
		super.pause();
		for (int i = 0; i < cList.size(); i++)
			cList.get(i).pause();
	}

//	public void loadSound() {
////		setSoundId(music.loadSound(R.raw.fresh));
////		METAL=music.loadSound(R.raw.kill);
////		int soundId;
//		int FIREBALL1 = MusicId.firecolumn;
//		int BALLER1 = MusicId.baller;
//		int WALKER1 = MusicId.walker;
//		int EMPLACEMENT1 = MusicId.emplacementAttack;
//		int FLYER1 = MusicId.flyer;
//		int HEDGEHOG1 = MusicId.hedgehog;
//		int CREEPER1 = MusicId.creeper4;
//		int SPIDE1 = MusicId.zhizhu;
//
//		for (Creature e : cList) {
//			switch (e.getSoundId()) {
//			case FIREBALL:
//				e.setSoundId(FIREBALL1);
//				break;
//			case BALLER:
//				e.setSoundId(BALLER1);
//				break;
//			case WALKER:
//				e.setSoundId(WALKER1);
//				break;
//			case EMPLACEMENT:
//				e.setSoundId(HEDGEHOG1);
//				((Emplacement)e).setSoundIdAttack(EMPLACEMENT1);
//				break;
//			case FLYER:
//				e.setSoundId(FLYER1);
//				((Flyer)e).walkerSoundId=WALKER1;
//				break;
//			case HEDGEHOG:
//				e.setSoundId(HEDGEHOG1);
//				((Hedgehog)e).creeperSoundId=CREEPER1;
//				break;
//			case CREEPER:
//				e.setSoundId(CREEPER1);
//				break;
//			case SPIDE:
//				e.setSoundId(SPIDE1);
//				break;
//			}
//		}
//
//	}


	private void randomAction(Creature c) {
		if (CHASE_MODEL)
			chasePlayer(c);
		c.randomAction();
		c.attackAnotherOne(enemySet);
	}

	private void chasePlayer(Creature c) {
		if (c.x < player.x)
			c.turnRight();
		else
			c.turnLeft();
	}

	public void drawElement(GL10 gl) {
		
		if(!World.rpgMode)timerTask();
		
		// gl.glColor4f(1f, 0.5f, 0.5f, 1f);
//		if(((Player)player).touched)
			drawGuideCircle(gl);
		for (int i = 0; i < cList.size(); i++) {
			Creature c = cList.get(i);
			if (c.isDead) {
				c.drawDeath(gl);
				continue;
			}
			

			if (c.x > Player.gx1 && c.x < Player.gx2 && c.y > Player.gy1
					&& c.y < Player.gy2) {
				// player.wheelCheck(c);

				drawLifeColumn(c, gl);

				{
					randomAction(c);
					c.drawElement(gl);
					
				}
			} else
				if (c.x > Player.hx1 && c.x < Player.hx2 && c.y > Player.hy1&& c.y < Player.hy2) 
			{
				c.move();
				c.gravity();
				
				c.drawEffect(gl);
			}
		}

		bloodSet.drawElement(gl);
		// gl.glColor4f(1,1, 1,1f);
		
		if(World.editMode)enemyGrass.drawScale(gl);
	}

	public void timerTask() {
		// TODO Auto-generated method stub
		Creature self;
		Creature another;
		
		for (int j = 0; j < cList.size(); j++) {
			self = cList.get(j);
			if(!self.isDead)
			for (int i = 0; i < enemySet.cList.size(); i++) {
				another = enemySet.cList.get(i);
				if (Math.abs(self.x - another.x) < another.getwEdge() + self.getW()
						&& another.y - another.gethEdge() < self.y // foot
						&& another.y + another.gethEdge() > self.y - self.gethEdge()) {// head higher than foot
					tooClose(self,another);
				}
			}
		}
	
	}

	private void tooClose(Creature self, Creature another) {
		// TODO Auto-generated method stub
		attacked(self,another,self.attack);
	}

	private void drawGuideCircle(GL10 gl) {
		for(Emplacement e:emplacementList){
			if (e.x > Player.gx1 && e.x < Player.gx2 && e.y > Player.gy1
					&& e.y < Player.gy2) {
			}else {
				e.drawRange = false;
				continue;
			}
			if(!e.drawRange)continue;
			gl.glTranslatef(e.x, e.y, 0);
			float rate=(float)e.getRange()/(float)_256;
			gl.glScalef(rate, rate, 0);
			guideCircle.drawElement(gl);
			rate=1/rate;
			gl.glScalef(rate, rate, 0);
			gl.glTranslatef(-e.x, -e.y, 0);
		}
	}

	private void drawLifeColumn(Creature c, GL10 gl) {
		// TODO Auto-generated method stub

		float rate = (float) c.getLife() / (float) c.getLifeMax();
		if (rate <= 0)
			return;
		if (rate == 1)
			return;

		float w = c.x - bloodW / 2;
		float h = c.y + c.getH() + bloodH + 20;
		gl.glTranslatef(w, h, 0);

		backLife.drawElement(gl);
		gl.glColor4f(0, 1, 0, 1);
		gl.glScalef(rate, 1, 1);
		lifecolumn.drawElement(gl);
		gl.glScalef(1 / rate, 1, 1);
		gl.glColor4f(1, 1, 1, 1);
		gl.glTranslatef(-w, -h, 0);
	}

	public void resetSpirit() {
		for (int i = 0; i < cList.size(); i++)
			cList.get(i).resetSpirit();
	}

	public boolean attacked(Creature another, int attack) {
//		Log.i("systemAttacker.score:"+systemAttacker.score);
		return attacked(getSystemAttacker(),another,attack);
	}
	public boolean attacked(Creature attacker,Creature enemy, int attack) {
		if (enemy!=null&&!enemy.isDead) {
			enemy.attacked(attack);
			bloodSet.tringerExplode(
					(float) (enemy.x + enemy.getwEdge() * (2 * Math.random() - 1)),
					(float) (enemy.y + enemy.getwEdge() * (2 * Math.random() - 1)),
					1.5f * enemy.getxSpeed(), 1.5f * enemy.getySpeed(),
					(int)(Math.random()/3*bloodSet.getCount()*Math.min(attack,enemy.getLifeMax())/enemy.getLifeMax()));
			if (enemy.isDead) {// 被攻击致死
			// World.recycleDraw(spi);
				attacker.increaseScoreBy(enemy.getScore());
				attacker.increaseChanceBy(enemy.chance);

				// eList.remove(spi);
				return true;

			}
		}
		return false;

	}

	public void treaded(Player player, Creature spi, int attack) {
		attacked(player, spi,attack);
		spi.treaded(player);
		spi.playSound();
	}

	public void removeEnemy() {
		// TODO Auto-generated method stub

	}

	public boolean isCHASE_MODEL() {
		return CHASE_MODEL;
	}

	public void setCHASE_MODEL(boolean cHASE_MODEL) {
		CHASE_MODEL = cHASE_MODEL;
	}

	/*
	 * public Creature getPlayer() { // TODO Auto-generated method stub return
	 * player; }
	 */
	public void addEmplacement(Emplacement e) {
		emplacementList.add(e);
		cList.add(e);
	}

	public void addCreature(Creature e) {
		cList.add(e);
	}

	public void setFriendSet(EnemySet friendSet) {
		// TODO Auto-generated method stub
		for(Creature c:cList){
			c.setFriendSet(friendSet);
		}
	}
	public void setEnemySet(EnemySet es) { // di dui shi li
		this.enemySet = es;
		for(Creature c:cList){
			c.setEnemySet(es);
		}
	}

	public Creature getSystemAttacker() {
		return systemAttacker;
	}

	public void setSystemAttacker(Creature systemAttacker) {
		this.systemAttacker = systemAttacker;
	}

}
