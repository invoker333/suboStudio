package Enviroments;

import Element.AnimationGrass;
import Module.TexIdAndBitMap;

public class GrassSticker extends Grass {

	public GrassSticker(char mapSign, float[] data, TexIdAndBitMap texId) {
		super(mapSign, data, texId);
		// TODO Auto-generated constructor stub
		attackedAble=false;
	}
	
	public boolean tooDown(AnimationGrass animationGrass) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean tooHigh(AnimationGrass animationGrass) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean tooLeft(AnimationGrass animationGrass) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean tooRight(AnimationGrass animationGrass) {
		// TODO Auto-generated method stub
		return false;
	}

}
