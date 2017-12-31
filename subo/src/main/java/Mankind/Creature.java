package Mankind;

import com.mingli.toms.Render;
import com.mingli.toms.World;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import Element.AnimationGrass;
import Enviroments.GrassSet;

public class Creature extends AnimationGrass {
    protected float DEATHSPEED = 0.032f;
    private int jumpHeight = 192;//
    private float g = 1;//
    private float ySpeedMax;
    private float amLand = 1f;
    float afLand = 1f;
    private float am;// �������ٶ�;
    private float af;// �������ٶ�
    /*
     * ��Щ��ν�ļ��ٶ� �������ٶ� �������ٶ� �������ٶ� ��ʵ��
     * ��Ϊ�ȼ����˶�ÿ����ʱ�����ٶȳɵȲ����� Ϊ�˷�������ȡ�� �Ȳ�
     */
    private float xSpeedMax = 7;
    //	private float xSpeedMax = 16;
    private float xSpeedMin = -xSpeedMax;
    private boolean jumpAble = true;
    protected int fdirection;// ���ķ���
    protected int direction = 1;

    private int lifeMax = World.baseLifeMax;
    private int life = lifeMax;

    public int attack = World.baseAttack;// ������
    float speedSizeMax = xSpeedMax;// ����ߴ�ȷ�����ٶȼ���
    private float[] agoMax;// ��ʼ�����ֵ ���ǵ�����

    ArrayList<Creature> friendList;
    ArrayList<Creature> enemyList;

    public boolean isDead;// 死亡与否
    private boolean hero;// hero or not
    protected boolean isDeadNoDraw;

    public Creature(GrassSet gra) {
        super(gra);
        init();
        afterInit();
        setJumpHeight(jumpHeight);// get yspeed max
        // frictionCheck();// ����Ħ�����ٶ�
    }

    protected void afterInit() {// ����ı���������
//		agoData = new float[] { 0, 0, gMax, amLand / 2 };
        agoMax = new float[]{getW(), getH(), jumpHeight, xSpeedMax, amLand,
                afLand, g};
    }

    protected void init() {// ���������ʾ��С�Զ�ȡ����

        sizeCheck();
//		setAnimationFinished(true);// �ܹ�����
        setTexture();
    }

    public Creature(char bi, GrassSet gra, float x, float y) {
        this(gra);
        mapSign = bi;
//		setPosition(x, y);
        setStartXY(x, y - gra.getGrid() + gethEdge());


        setPosition(startX, startY);
    }


    protected void autoClimb() {

        // final float a =1*afLand;//玲姐速度之
        // if(Math.abs(xSpeed)<a)return;//大于零戒指

        if (direction * xSpeed < 0) return;// direction different

        float rx = x + direction * (w + gra.getGrid() / 2);// wu cha
        int xindex = (int) (rx / gra.getGrid());

        int yIndex = (int) ((y - gethEdge()) / gra.getGrid());

        if (yIndex < gra.getMapHeight() - 1 && gra.map[xindex][yIndex + 1] != gra.getZero())
            return;// 上方为空

//		float y=this.y-1;if(y<0)y=0;
        float dy = gra.getGrid() - (y - gethEdge()) % gra.getGrid();
        if (dy > jumpHeight) dy = jumpHeight;// can

        dy += 16;// jump higher than 10
        int jumpSpeed = (int) Math.sqrt(2 * g * dy);
        // Log.i("Dy"+dy, "jumpSpeed"+jumpSpeed);
        if (fallen)
            ySpeed += jumpSpeed;
    }

    boolean turnDown() {
        if (jumpAble && gList.get(getLandId()).turnDown(this)) {
            y -= 1;
            land();
            return true;
        }
        return false;
    }

    void changeSpeed(float f) {
        if (f < 0)
            f = -f;

        setxSpeedMin(-f * speedSizeMax);
        setxSpeedMax(f * speedSizeMax);

        aniStepCheck();
    }

    protected float sizeRate = 1;

    //	protected void culYScaleRate() {
//		setyScaleRate(2*sizeRate  - getxScaleRate());
//	}
    public void changeSize(float rate) {
        sizeRate = rate;
//		setH(agoMax[1] * rate);
//		ySpeed+=g;

        float h1 = getH();
        setH(agoMax[1] * rate);
        y = y + getH() - h1 + g;


        setW(rate * agoMax[0]);
        maxScaleLengthX = 0.8f * w;
        sizeCheck();

        speedSizeMax = rate * agoMax[3];
        amLand = rate * agoMax[4];
        // gra.setAfLand(rate * agoMax[5]);
        jumpHeight = (int) (rate * agoMax[2]);
        ySpeedMax = (float) Math.sqrt(2 * jumpHeight * g);
        xSpeedMax = agoMax[3] * rate;
        xSpeedMin = -xSpeedMax;

        setxScaleRate(rate);
        setyScaleRate(rate);

        wallCheck();
    }

    /**
     * 是否穿过墙体
     */
    private void wallCheck() {


    }


    public void stopJump() {
        setySpeed(0);
    }

    public void drawElement(GL10 gl) {
        super.drawElement(gl);
        animation();
    }

    float red = 1, green = 1, blue = 1;
    float alpha = 1;

    void disappear(float rate) {
        red -= rate;
        green -= rate;
        blue -= rate;
        alpha -= rate;
        if(alpha<=0){
            isDeadNoDraw=true;
        }
    }

    public void drawDeath(GL10 gl) {
        drawEffect(gl);
        if (isDeadNoDraw) {
            return;// 死亡不画
        }
        disappear(DEATHSPEED);
        move();
        gravity();

        animation();

        float time = alpha / 1;
        float returnTime = 1 / alpha;

        // gl.glColor4f(red, green, blue, alpha);
        setAngle(getAngle() + rotateSpeed);
        gl.glTranslatef(x, y, 0);
        gl.glRotatef(getAngle(), 0, 0, 1);
        gl.glScalef(time, time, 0);
        baseDrawElement(gl);
        gl.glScalef(returnTime, returnTime, 0);
        gl.glRotatef(-getAngle(), 0, 0, 1);
        gl.glTranslatef(-x, -y, 0);

        // gl.glColor4f(1,1,1,1);
    }

    public void gravity() {
        setyPro(y + getySpeed());
        gravityCheck();// shunxu bu neng huan  made
        setySpeed(getySpeed() - g);

        if (fallen)
            changeToLandData();
        else
            changeToAirData();// 判断是否在空气中

        y = getyPro();
        // setyPro(y + vt);
        // setVt(vt - g);
        // gravityCheck();
        // y = yPro;
        // changeHeight();
        // changePosition();
    }

    protected void changeToLandData() {
        setAf(afLand);
        setAm(amLand);
        jumpAble = true;
    }

    protected void tooDown() {
        stopJump();
        super.tooDown();
    }

    protected void moveCheck() {
        // if (speed > 0)
        // rightMove();
        // else if (speed < 0)
        // leftMove();
        super.moveCheck();
        speedCheck();

    }

    float index;// ����������
    protected boolean attackable = true;
    //	private boolean moving;
    private float aniStep = xSpeedMax / 100f;//
    float[] aniStep2 = {aniStep, 0.4f};
    int chance = 1;
    protected int attackSoundId;
    protected EnemySet enemySet;
    protected EnemySet friendSet;
    public int rightDirection = -1;
    public boolean treadable = true;


    protected void faceRight() {
        // setyState(2);
        // fbSpi=fbSpi1;
        direction = 1;
    }

    public void stopMove() {
        fdirection = 0;
    }


    protected void turnRight() {
        fdirection = 1;// ͨ���������ķ�������ƶ� �����ı����Ĵ�С
        if (isAnimationFinished()) {
            faceRight();
        }
    }

    protected void turnLeft() {
        fdirection = -1;
        if (isAnimationFinished()) {
            faceLeft();
        }
    }

    protected void faceLeft() {
        // setyState(0);
        // fbSpi=fbSpi_1;
        direction = -1;
    }

    protected void attack() {
        // aniStep = aniStep2[1];
        index = 0;
        attackable = false;
        // if (direction == -1)
        setState(0, 1);
        // else
        // setState(0, 3);
    }

    void animation() {
        changeState(aniStep);
    }

    protected void speedCheck() {
        if (fdirection == 0) {
            if (xSpeed > 0) {//
                if ((xSpeed += -af) < 0)
                    xSpeed = 0;//
            } else if (xSpeed < 0) {//
                if ((xSpeed += af) > 0)
                    xSpeed = 0;// ���ٲ��ܳ���0
            }
        } else if (fdirection == -1 && xSpeed > xSpeedMin || fdirection == 1
                && xSpeed < xSpeedMax) {
            xSpeed += fdirection * am;//
        }
    }

    // protected void dropCheck() {
    // ydata = gList.get(getLandId()).data;// ��i������
    // // float x1=data1[0];//x
    // if (x + wEdge < ydata[0] || x - wEdge > ydata[2])// ��̤������
    // land();// ������������yֵһ��������ʱ ���ǻ����ȥ��
    // }

    public void jump() {
        setySpeed(ySpeedMax);
        // setG(gMax);

        changeToAirData();

    }

    private void changeToAirData() {
        setAf(0f); // ���е������Ͷ���
        setAm(amLand / 2);
        jumpAble = false;// ��ֹ������
    }

    public void jump(float rate) {
        setySpeed((float) Math.sqrt(2 * jumpHeight * rate * g));
        changeToAirData();
    }

    void land() {
        setySpeed(0);
    }

    void setY(float y) {
        this.y = y;
    }


    public float getG() {
        return g;
    }

    public void setG(float g) {
        this.g = g;
    }

    public void increaseScoreBy(int score) {
//		this.score+=score;
    }

    public void increaseChanceBy(int ch) {
//		this.chance+=ch;
    }

    public void attacked(int attack) {
        baseAttacked(attack);
        // return false;
    }

    private void baseAttacked(int attack) {
        // TODO Auto-generated method stub
        if ((life -= attack) > 0) {
        } else {
            die();
            // return true;
        }
    }


    public void resetSpirit() {
        setLife(1000);
        x = Render.px + Render.width / 2;
        y = Render.py + Render.height;
        stopJump();
        resume();
        isDead = false;
    }

    public float getAm() {
        return am;
    }

    public void setAm(float am) {
        this.am = am;
    }

    public void setAf(float af) {
        this.af = af;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getLifeMax() {
        return lifeMax;
    }

    public void setLifeMax(int lifeMax) {
        life = life * lifeMax / this.lifeMax;
        this.lifeMax = lifeMax;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isAttackAble() {
        return attackable;
    }

    public void setAttackAble(boolean attacking) {
        this.attackable = attacking;
    }

    // @Override
    // public void run() {
    // setLiving(true);
    // // while (isRunning()) { // ��������һֱ����
    // // move();
    // // gravity();
    // // World.timeRush();
    // // }
    // setLiving(false);
    // }

    public float[] getAniStep2() {
        return aniStep2;
    }

    public void setAniStep2(float[] aniStep2) {
        this.aniStep2 = aniStep2;
    }

    public int getJumpHeight() {
        return jumpHeight;
    }

    public void setJumpHeight(int jumpHeight) {
        this.jumpHeight = jumpHeight;
        ySpeedMax = (float) Math.sqrt(2 * jumpHeight * g);
    }

    public float getxSpeedMax() {
        return xSpeedMax;
    }

    public void setxSpeedMax(float speedMax) {
        this.xSpeedMax = speedMax;
//		if(xSpeed>xSpeedMax)xSpeed=xSpeedMax;
    }

    public float getxSpeedMin() {
        return xSpeedMin;
    }

    public void setxSpeedMin(float speedMin) {
        this.xSpeedMin = speedMin;
//		if(xSpeed<xSpeedMin)xSpeed=xSpeedMin;
    }

    public boolean isJumpAble() {
        return jumpAble;
    }

    public void setJumpAble(boolean jumpAble) {
        this.jumpAble = jumpAble;
    }

    public float getAf() {
        return af;
    }

    public void randomAction() {// ����
    /*
	 * switch ((int) (5 * Math.random())) { case 0: turnRight(); break; case 1:
	 * turnLeft(); break; case 2: stopMove(); break; case 3: if (jumpAble)
	 * jump(); break; case 4: if (isAnimationFinished()) { attack(); break; } if
	 * (fdirection != 0 && getDirection() != fdirection) {
	 * setDirection(fdirection); attack(); break; } break;
	 * 
	 * }
	 */
    }

    public float getAmMax() {
        return amLand;
    }

    public void setAmMax(float amMax) {
        this.amLand = amMax;
    }

    public float[] getAgoMax() {
        return agoMax;
    }

    public void setAgoMax(float[] agoMax) {
        this.agoMax = agoMax;
    }

    public void treaded(Creature player) {// ����
//	 playSound();
        scaleTringer(maxScaleLengthX);
    }

    public float getAmLand() {
        return amLand;
    }

    public void setAmLand(float amLand) {
        this.amLand = amLand;
    }

    public GrassSet getGra() {
        return gra;
    }

    public void setGra(GrassSet gra) {
        this.gra = gra;
    }

    public void attackAnotherOne(EnemySet es) {
    }

    public void die() {
        // TODO Auto-generated method stub
        rotateSpeed = -xSpeed;
        isDead = true;
//		stopMove();
        am = 0;
    }


    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public float getBlue() {
        return blue;
    }

    public void setBlue(float blue) {
        this.blue = blue;
    }

    public float getGreen() {
        return green;
    }

    public void setGreen(float green) {
        this.green = green;
    }

    public float getRed() {
        return red;
    }

    public void setRed(float red) {
        this.red = red;
    }

    public void setRgb(float red, float green, float blue) {
        this.red = red;
        this.blue = blue;
        this.green = green;
    }

    public void setGunAngle(double d) {
        // TODO Auto-generated method stub
        if (d < 90 && d > -90)
            faceRight();
        else
            faceLeft();
    }

    public void setSpeed(double xSpeed, double ySpeed) {
        // TODO Auto-generated method stub
        this.xSpeed = (float) xSpeed;
        this.ySpeed = (float) ySpeed;
    }

    public float getySpeedMax() {
        return ySpeedMax;
    }

    public void setySpeedMax(float ySpeedMax) {
        this.ySpeedMax = ySpeedMax;
    }

    void changeLifeRate(float rate) {
        life *= rate;
        lifeMax *= rate;
    }

    public void setSoundIdAttack(int attackSoundId) {
        this.attackSoundId = attackSoundId;
        // TODO Auto-generated method stub
    }

    public void setEnemySet(EnemySet es) {
        // TODO Auto-generated method stub
        this.enemySet = es;
        enemyList = es.cList;
    }

    public EnemySet getEnemySet() {
        return enemySet;
    }

    public EnemySet getFriendSet() {
        return friendSet;
    }

    public void setFriendSet(EnemySet friendSet) {
        // TODO Auto-generated method stub
        this.friendSet = friendSet;
        friendList = friendSet.cList;
    }

    public boolean culTreadSpeedAndCanBeTread(Creature c) {
        float rs = c.xSpeed - getxSpeed();    //relativeSpeed;


        if (c.fdirection == 0) {
            c.xSpeed = getxSpeed();
        } else {
            if (c.fdirection == 1) {
                if (rs < c.getxSpeedMax()) {
                    c.xSpeed += c.am;
                }
            } else if (c.fdirection == -1) {
                if (rs > c.getxSpeedMin()) {
                    c.xSpeed -= c.am;
                }
            }
        }

        {
            c.ySpeed += c.getG();// remove the g 's effect
            yStandCheck(c, c.gethEdge() + gethEdge(), 0.2f, 1);
        }
        return true;

    }

    public void timerTask() {//
    }

    public void drawEffect(GL10 gl) {
        // TODO Auto-generated method stub
        // draw thing when not in screen
    }

    void toBeHero() {
        hero = true;
    }

}
