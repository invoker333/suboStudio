package Enviroments;

import java.util.ArrayList;



import Mankind.BattleMan;

import com.mingli.toms.MusicId;

import element2.TexId;

public class Toukui extends ShakeFruit{

	private int time;

	public Toukui(char bi,GrassSet grassSet,float x, float y,int time) {
		super(bi,grassSet, x, y);
		// TODO Auto-generated constructor stub
		this.time = time;
		name="头盔";
		instruction="使用后可以顶掉上方一些砖块";
		setGoodsCost(5,0);
	}
	protected void init(){
		loadTexture(TexId.TOUKUI);
		loadSound(MusicId.creeper4);
	}
	public void use(BattleMan player,ArrayList<Fruit> pickedList){
		if(player.getToukuiTime()>0) {
		} else {
			player.changeToukui(time);
		}
		super.use(player, pickedList);
	}

//	public
}
class Gao extends ShakeFruit{

	private int time;
	public Gao(char bi,GrassSet grassSet,float x, float y, int time) {
		super(bi,grassSet, x, y);
		// TODO Auto-generated constructor stub
		this.time = time;
		name="十字镐";
		instruction="使用手指下滑手势，可以破坏下面一些砖块";
		setGoodsCost(5,0);
//		footTail=new Tail(15,x,y,8);
	}
	protected void init(){
		loadTexture(TexId.GAO);
		loadSound(MusicId.gore);
	}
	public void use(BattleMan player,ArrayList<Fruit> pickedList){
		player.changeGao(time);
	}
}
class FruitFly extends ShakeFruit{

	private int time;
	public FruitFly(char bi,GrassSet grassSet,float x, float y, int time) {
		super(bi, grassSet, x, y);
		// TODO Auto-generated constructor stub
		this.time = time;
		name="飞行套装";
		instruction="使用后穿上帅气的披风，并且点击任意位置可跳跃";
		setGoodsCost(10,0);
	}
	protected void init(){
		loadTexture(TexId.FRUITFLY);
		loadSound(MusicId.land);
	}
	public void use(BattleMan player,ArrayList<Fruit> pickedList){
		player.addFlyTime(time);
	}
}
//class FruitAuto extends ShakeFruit{
//
//	private int time;
//	public FruitAuto(char bi,float x, float y, int time) {
//		super(bi,x, y);
//		// TODO Auto-generated constructor stub
//		this.time = time;
//		name="束缚泡泡";
//		setGoodsCost(5,10);
//	}
//	void init(){
//		loadTexture(TexId.RED);
//	}
//	public void use(Player player,ArrayList<Fruit> pickedList){
//		player.autoBulletTime+=time;
//	}
//}


 class Wudi extends ShakeFruit{

	private int time;

	public Wudi(char bi,GrassSet grassSet,float x, float y,int time) {
		super(bi,grassSet, x, y);
		// TODO Auto-generated constructor stub
		this.time = time;
		name="无敌果";
		instruction="使用后无敌"+time/60+"秒";
		setGoodsCost(50,0);
	}
	public Wudi(char bi,GrassSet grassSet,float x, float y) {
		this(bi,grassSet, x,y,10*60);
		// TODO Auto-generated constructor stub
	}
	protected void init(){
		setSoundId(MusicId.light);
		loadTexture(TexId.ZAN);
	}
	public void use(BattleMan player,ArrayList<Fruit> pickedList){
		final int max=200;
		player.incWudiTime(time);
		doubleCost(max);
		super.use(player, pickedList);
	}
}
 
 class Fenshen extends ShakeFruit{

		private int time;
		private int count=10;

		public Fenshen(char bi,GrassSet grassSet,float x, float y,int count) {
			super(bi,grassSet, x, y);
			this.count = count;
			// TODO Auto-generated constructor stub
			name="分身果";
			instruction="使用后分身为"+(count+1)+"个。点击任意个体使本体与之交换位置。";
			setGoodsCost(50,50);
		}
		public Fenshen(char bi,GrassSet grassSet,float x, float y) {
			this(bi, grassSet, x,y,4);
			// TODO Auto-generated constructor stub
		}
		protected void init(){
			setSoundId(MusicId.light);
			loadTexture(TexId.FENSHEN);
		}
		public void use(BattleMan player,ArrayList<Fruit> pickedList){
			final int max=200;
			player.fenshen(count);
			doubleCost(max);
			super.use(player, pickedList);
		}
	}
