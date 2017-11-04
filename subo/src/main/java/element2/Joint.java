package element2;

import javax.microedition.khronos.opengles.GL10;

import Mankind.Creature;
import Menu.State;
import Module.TexIdAndBitMap;

public class Joint extends State {
	public float angle;
	private float dagree;
	protected float xp;// 立足点
//	private float xpReal;// 初始立足点
	protected float yp;
	protected int direction;
	int myDirection=1;


	public float mAngle = 60;
	public float angleStart = -mAngle;
	public float angleEnd = mAngle;
	public float speed;
//	private float angleDraw;
//	private FloatBuffer fbSpi_1;
//	private ByteBuffer bbSpi1;
//	private FloatBuffer fbSpi1;

	public Joint(Creature p,float x1, float y1, float x2, float y2) {
		super(x1,y1,x2,y2);
		init();
		speed = (angleEnd - angleStart) / (3f / p.getAniStep2()[1]);
		loadSound();
	}
	public Joint(Creature p, float x1, float y1, float x2, float y2,
				 TexIdAndBitMap textureId) {
		this(p,x1, y1, x2, y2);
		loadTexture(textureId);
	}
	public Joint(Creature p, float x1, float y1, float x2, float y2, float xp,
				 float yp, TexIdAndBitMap textureId) {
		this(p, x1, y1, x2, y2, textureId);
		this.xp = xp;
//		xpReal = xp;
		this.yp = yp;
	}
	public Joint(Creature p, float x1, float y1, float x2, float y2, float xp,
				 float yp, TexIdAndBitMap textureId, int myDirection) {
		this(p, x1, y1, x2, y2, xp, yp, textureId);
		this.myDirection = myDirection;
	}
	public Joint(Creature p, float x1, float y1, float x2, float y2, float xp,
				 float yp, TexIdAndBitMap textureId, int myDirection,int mAngle) {
		this(p, x1, y1, x2, y2, xp, yp, textureId,myDirection);
		this.mAngle = mAngle;//角度恒定值
		angleStart = -mAngle;
		angleEnd = mAngle;
		speed =2* (angleEnd - angleStart) / (3f / p.getAniStep2()[1]);
	}
	public Joint(int xCount,Creature p, float x1, float y1, float x2, float y2, float xp,
				 float yp, TexIdAndBitMap textureId, int myDirection,int mAngle) {
		this( p,  x1,  y1,  x2,  y2,  xp, yp,  textureId,  myDirection, mAngle);
		setxCount(xCount);
		setTexture();
//		setyCount(yCount);
	}

	public Joint() {
		// TODO Auto-generated constructor stub
	}

	protected void init() {
		// TODO Auto-generated method stub

	}

	public void start() {
		direction = myDirection;
		setDagree(speed * direction);
	}

	public void stop() {
		direction = 0;
	}
	public void faceLeft() {
//		dagree=-dagree;
		direction=-1;
	}

	public void faceRight() {
		direction=1;
	}
	public void drawNotMove(GL10 gl) {
		positionCheck();
		baseJointDrawElement(gl);
	}
	public void drawElement(GL10 gl) {
		incAngle();
		drawNotMove(gl);
	}
	public void incAngle() {
		angle += dagree;
	}

	public void baseJointDrawElement(GL10 gl) {
		gl.glTranslatef(xp, yp, 0);
		gl.glRotatef(angle, 0, 0, 1);
		super.drawElement(gl);
		gl.glRotatef(-angle, 0, 0, 1);
		gl.glTranslatef(-xp, -yp, 0);
	}
	public void positionCheck() {
//		float absAng=Math.abs(angle);
		float absSpeedLoss = Math.abs(angle / angleStart);
		float speedLos=absSpeedLoss>1?1:absSpeedLoss;
		if(direction==0) {//逆时针为证
			float slowSpeed=this.speed*absSpeedLoss+.1f;
			if (angle > speed)//dagree>0
				setDagree(-slowSpeed);
			else if (angle < -speed)
				setDagree(slowSpeed);
			else {
				setDagree(0);
				angle=0;
			}
		}else {
			if (angle < angleStart) {
				angle = angleStart;
				direction = 1;
			} else if (angle > angleEnd) {
				angle = angleEnd;
				direction = -1;
			}
			setDagree(direction * speed*(1-absSpeedLoss+.1f));//0.1 is min speedlosss
		}
	}


	public float getDagree() {
		return dagree;
	}

	public void setDagree(float dagree) {
		this.dagree = dagree;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
}

