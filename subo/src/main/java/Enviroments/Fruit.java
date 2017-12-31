package Enviroments;

import java.util.ArrayList;

import Element.Animation;
import Mankind.BattleMan;
import Mankind.Creature;

import com.mingli.toms.MusicId;
import com.mingli.toms.World;

import javax.microedition.khronos.opengles.GL10;

public class Fruit extends Creature{
	private float alpha=1;
	private float blue=1.2f;
	private float green=1.2f;
	private float red=1.2f;
	
	String kind="fruit";
	public String instruction="这个物品的作用作用有待您去发现";
	
	public Fruit(char bi,GrassSet grassSet,float x,float y){
		super(bi,grassSet, x, y	);

		setG(0);
		attack=0;
		setW(30);
		setH(30);

		wEdge=w;
		hEdge=h;

		setAnimationFinished(false);
		init();
		this.setPosition(x, y);
		setStartXY(x,y);
	}

    @Override
    public void die() {
        super.die();
        setG(World.baseG);
		isDeadNoDraw=true;
    }

//	@Override
//	public void drawDeath(GL10 gl) {
//		gl.glTranslatef(10,0,0);
//		super.drawElement(gl);
//		gl.glTranslatef(-10,0,0);
//	}


    void doubleCost(int max){
		if(cost<max&&chancecost<max){
			cost+=cost;
			chancecost+=chancecost;
		}
	}
	protected void init() {
		loadTexture();
		loadSound(MusicId.magic);
	}
	public boolean loadAble(BattleMan player){
		player.increaseScoreBy(getScore());
		playSound();
//		use(player, null);
		return false;
	}
	public void use(BattleMan player, ArrayList<Fruit> pickedList) {
		// TODO Auto-generated method stub
		if(pickedList!=null)pickedList.remove(this);
	}
	public void playSound() {
		music.playSound(getSoundId(), 0);
	}
	void animation(){
		if(getxState()==getxCount()-1)changeState(1000);
		else changeState(100);
	}

	public void effectCheck(BattleMan p,ArrayList<Fruit>effectedList) {
		effectedList.remove(this);
	}
	public float getBlue() {
		return blue;
	}
	public void setBlue(float blue) {
		this.blue = blue;
	}
	public float getGreen() {
		return green;
	}
	public void setGreen(float green) {
		this.green = green;
	}
	public float getRed() {
		return red;
	}
	public void setRed(float red) {
		this.red = red;
	}
	public float getAlpha() {
		return alpha;
	}
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
	
	



}
