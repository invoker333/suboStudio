package Mankind;

import javax.microedition.khronos.opengles.GL10;

import element2.TexId;
import Element.Animation;
import Element.AnimationMove;
import Enviroments.GrassSet;

public class EmpPlatform  extends Emplacement{

	AnimationMove platform;
	public EmpPlatform(char bi, GrassSet gra, float x, float y) {
		super(bi, gra, x, y);
		// TODO Auto-generated constructor stub
		platform=new AnimationMove();
		platform.w=this.w;
		platform.h=this.h;
		platform.loadTexture(TexId.BANK);
		platform.setPosition(x, y);
	}
	public void drawElement(GL10 gl){
		platform.drawTranElement(gl);
		super.drawElement(gl);
	}
	public void drawEffect(GL10 gl) {
		// TODO Auto-generated method stub
		// draw thing when not in screen
		platform.stringCheck(this, 1, tanxingxishu, 0.9f);
		super.drawEffect(gl);
		
	}
}
