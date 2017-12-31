package Enviroments;

import javax.microedition.khronos.opengles.GL10;

public class ShakeFruit extends Fruit{
	float angleSpeed=0;
	float angleA=1;
	float aMax=0.1f;
	private float angle=-30;//起始位置
	
	public ShakeFruit(char bi,GrassSet grassSet,float x, float y){
		super(bi, grassSet, x, y);
	}
	public void drawElement(GL10 gl){
		move();
		gravity();

		if(angle<0){//7*7=49 居中位置
//			angleSpeed=-angleSpeed;
			angleA=aMax;
		} else  {
//			angleSpeed=-angleSpeed;
			angleA=-aMax;
		}
		angleSpeed+=angleA;
		angle+=angleSpeed;
		
		float dCo=angleA/10;
		setRed(getRed() + dCo);setGreen(getGreen() + dCo);setBlue(getBlue() + dCo);
		gl.glColor4f(getRed(), getGreen(), getBlue(), getAlpha());
		gl.glTranslatef(x, y, 0);
		gl.glRotatef(angle, 0, 0, 1);
		super.baseDrawElement(gl);
		gl.glRotatef(-angle, 0, 0, 1);
		gl.glTranslatef(-x, -y, 0);
		gl.glColor4f(1, 1, 1, 1);
	}
}
