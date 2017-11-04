package Mankind;

import javax.microedition.khronos.opengles.GL10;

import Element.Draw;
import element2.Tail;

public class Shader extends Draw{

	private Creature master;
	private Tail tail;
	public Shader(float time,Creature master) {
		this.master = master;
		// TODO Auto-generated constructor stub
		int frame=(int) (time*60f);
		position=new float [frame][2];
		
		tail=new Tail(frame);
		tail.w=(int) (master.getW());
		tail.startTouch(master.x, master.y);
	}
	float [][]position;
	int id;
	private float x;
	private float y;
//	private int direction;

	public void attacked(int attack){
		
	}
	public void move(){
		tail.tringer(master.x, master.y);
		
		
		if(id>=position.length)id=0;
		setPosition(position[id][0],position[id][1]);
		position[id][0]=master.x;
		position[id][1]=master.y;
//		Log.i("Shader.x+y"+x+"  "+y);
		id++;
		
		
//		super.move();
	}
	private void setPosition(float x, float y) {
		// TODO Auto-generated method stub
		this.x=x;
		this.y=y;
	}
	public void drawElement(GL10 gl){
		float alpha=0.2f;
		gl.glColor4f(alpha,alpha,alpha,alpha);
		move();
		int id2=id;
		for(int i=0;i<position.length;i++){
			
			if(id2>=position.length)id2=0;
			setPosition(position[id2][0],position[id2][1]);
			id2++;
			
//			if(id2%2==0)continue;
			gl.glTranslatef(x, y, 0);
			
			gl.glScalef(master.getxScaleRate(), master.getyScaleRate(), 0);

			
			float yAngle = master.direction == master.rightDirection ? 0:180f;
			gl.glRotatef(yAngle, 0, 1, 0);
			gl.glRotatef(master.angle, 0, 0, 1);
			master.baseDrawElement(gl);
			gl.glRotatef(-master.angle, 0, 0, 1);
			gl.glRotatef(-yAngle, 0, 1, 0);
			gl.glScalef(1/master.getxScaleRate(), 1/master.getyScaleRate(), 0);

			gl.glTranslatef(-x, -y, 0);
		}
//		tail.drawElement(gl);
		
		gl.glColor4f(1, 1, 1, 1f);
	}
	public void backToMaster() {
		// TODO Auto-generated method stub
		for(int i=0;i<position.length;i++){
			position[i][0]=master.x;
			position[i][1]=master.y;
		}
	}
}
