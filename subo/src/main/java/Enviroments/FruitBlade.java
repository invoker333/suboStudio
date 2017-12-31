package Enviroments;

import com.mingli.toms.MenuActivity;

import java.util.ArrayList;

import Mankind.BattleMan;
import element2.TexId;

public class FruitBlade extends RotateFruit{

	public FruitBlade(char bi,GrassSet grassSet,float x, float y) {
		super(bi,grassSet,x, y);
		name="桃木剑";
		setGoodsCost(30, 0);
		// TODO Auto-generated constructor stub
		setTextureId(TexId.K);
	}
	public boolean loadAble(BattleMan player){
		
		for(Fruit f:FruitSet.pickedList)
			if(f.getTextureId()==getTextureId()){
				MenuActivity.showDialog("", "限购一个", getTextureId());
				return false;// can't get more blade cause it is useless
			}
		
		super.loadAble(player);
		return true;
	}
	public void use( BattleMan player,ArrayList<Fruit> pickedList){
//		super.use(player, pickedList);
		player.changeBlade(getTextureId().textureId);
	}
	
	
	
	
	public void syncTextureSize(){
		syncTextureSize(getW(),getW()/2,getW()/3);
	}
	
	
	
	
}
