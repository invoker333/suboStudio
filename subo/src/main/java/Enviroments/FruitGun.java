package Enviroments;

import com.mingli.toms.MenuActivity;

import java.util.ArrayList;

import Mankind.BattleMan;
import Module.TexIdAndBitMap;
import element2.TexId;

public class FruitGun extends RotateFruit{

	public FruitGun(char bi,GrassSet grassSet, float x, float y, TexIdAndBitMap textureId) {
		super(bi,grassSet, x, y);
		mapSign=bi;
		// TODO Auto-generated constructor stub
		setTextureId(textureId);
		name="泡泡枪";
		kind="fruitgun";
		instruction="用来攻击敌人的武器";
		nameCheck(getTextureId());
		
	}
	private void nameCheck(TexIdAndBitMap textureId) {
		// TODO Auto-generated method stub
		if (textureId == TexId.BOOMGUN) {
			name = "炸弹";
			instruction = "扔出去炸倒一片敌人";
			setGoodsCost(35, 0);

		} else if (textureId == TexId.ZIDONGDAN) {
			name = "自动泡泡枪";
			instruction = "子弹可以自动跟踪最近目标，射速较慢，也可以点击任意目标发射";
			setGoodsCost(20, 0);

		} else if (textureId == TexId.SHUFUDAN) {
			name = "束缚枪";
			instruction = "可以将敌人束缚在原地，点击任意目标发射，或者自动寻找敌人";
			setGoodsCost(20, 0);

		} else if (textureId == TexId.SHOTGUN) {
			name = "霰弹枪";
			instruction = "大范围发射子弹";
			setGoodsCost(20, 0);

		} else if (textureId == TexId.GUANGDANQIANG) {
			name = "光弹枪";
			instruction = "威力大 范围广 射速慢";
			setGoodsCost(10, 0);

		} else if (textureId == TexId.JUJI) {
			name = "狙击枪";
			instruction = "射出的子弹可以穿透弱小目标，射速慢";
			setGoodsCost(30, 0);

		} else if (textureId == TexId.HOOKGUN) {
			setGoodsCost(15, 0);
			instruction = "或者将自己拉向地形边缘，点击地形发射或解除。\n如果拉到敌人也会把他拉过来";
			name = "钩爪";

		} else if (textureId == TexId.MISSILE) {
			setGoodsCost(40, 0);
			name = "火箭筒";
			instruction = "直线运行，造成范围伤害";

		} else if (textureId == TexId.PUTONGQIANG) {
			setGoodsCost(20, 0);
			name = "步枪";
			instruction = "快速射击的步枪，威力一般射速较快。";

		}
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
		player.changeGun(getTextureId().textureId);
	}
}
