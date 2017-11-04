package Element;

import javax.microedition.khronos.opengles.GL10;

import Enviroments.BackGround;
import element2.TexId;

public class Curtain extends BackGround {
	private float alpha=1;
	private float blue=1;
	private float green=1;
	private float red=1;
	
	float dAlpha=0.05f;
	
//	private float alphaSpeed=0.016f;
	private float alphaSpeed=0.03f;
	public Curtain(){
//		super();
		setTextureId(TexId.BLACK);
	}

	public void drawElement(GL10 gl){
		if(alpha>0&&alpha<1)
			incAlpha(dAlpha);
		gl.glColor4f(red, green, blue, alpha);
//		gl.glRotatef((1f-alpha)*360, 0, 0, 1);
		drawTranElement(gl);
		gl.glColor4f(1, 1, 1, 1);
	}
	public void open(){//xiemu
//		if(alpha<=0)
		alpha=1-alphaSpeed;
		dAlpha=-alphaSpeed;
	}
	public void close(){
//		if(alpha>=1)
			alpha=alphaSpeed;
		dAlpha=alphaSpeed;
	}
	public void incAlpha(float dAlpha){
		this.alpha+= dAlpha;
//		this.red = 1-alpha;
//		this.green = 1-alpha;
//		this.blue = 1-alpha;
		this.red = alpha;
		this.green = alpha;
		this.blue = alpha;

	}
}
