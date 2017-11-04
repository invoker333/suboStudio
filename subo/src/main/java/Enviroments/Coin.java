package Enviroments;


import android.graphics.Bitmap;

import com.mingli.toms.MusicId;

import java.util.ArrayList;

import Mankind.BattleMan;
import element2.TexId;

public class Coin extends SixFruit {
	private int coinCount = 1;
	
	Coin(char bi,float x, float y) {
		super(bi,x, y);
		name="金币";
		setScore(5);
//		Log.i("coinx"+x,""+y);
	}
	public int getCoinCount() {
//		// TODO Auto-generated method stub
//		Log.i("returncoinCount"+coinCount);
		return coinCount;
	}
	public void setCoinCount(int coinCount) {
		this.coinCount = coinCount;
	}
	public Bitmap getIcon() {
		// TODO Auto-generated method stub
		return getTextureId().bitmap;
	}
	void init() {
		loadTexture(TexId.COIN);
		loadSound(MusicId.coin);
	}
	public void use(BattleMan player, ArrayList<Fruit> pickedList) {
		// TODO Auto-generated method stub
		super.use(player, pickedList);
		visible=false;
	}
	public boolean loadAble(BattleMan player){
		player.increaseScoreBy(getScore());
		playSound();
		return false;
	}
	
}
