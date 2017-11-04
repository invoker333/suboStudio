package Enviroments;

import Module.TexIdAndBitMap;
import element2.TexId;

public class BankGrass extends Grass {

	public BankGrass(char mapSign, float[] data, TexIdAndBitMap texId) {
		super(mapSign, data, texId);
		// TODO Auto-generated constructor stub
	}
	public void gored(){
		setTextureId(TexId.BANK);
	}

}
