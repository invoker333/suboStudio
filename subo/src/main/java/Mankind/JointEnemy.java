package Mankind;

import com.mingli.toms.World;

import Enviroments.GrassSet;

public class JointEnemy extends JointCreature{
	private Player player;
	 int cdMAX=World.baseActionCdMax;
	 int cd=cdMAX/2;
	public JointEnemy(char bi, GrassSet gra, float x, float y) {
		super(bi, gra, x, y);
		// TODO Auto-generated constructor stub
		attack = World.baseAttack;
		turnLeft();
	}
	protected void tooRight() {
		super.tooRight();
		setAnimationFinished(true);
		if(xSpeed>getxSpeedMax())xSpeed=getxSpeedMax();
		turnLeft();
	}

	protected void tooLeft() {
		super.tooLeft();
		setAnimationFinished(true);
		if(xSpeed<getxSpeedMin())xSpeed=getxSpeedMin();
		turnRight();
	}
	public void die(){
		super.die();
		if(player!=null)player.succeed();
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayerAndToBeBoss(Player player) {
		toBeHero();
		setLifeMax(50*World.baseAttack);
		this.player = player;
	}

}
