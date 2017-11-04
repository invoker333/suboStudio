package Mankind;

import com.mingli.toms.MusicId;
import com.mingli.toms.World;

import Enviroments.GrassSet;
import element2.Joint;
import element2.TexId;

public class Hedgehog extends JointEnemy {

    /**
     * @param bi culTreadSpeedAndCanBeTread
     * @param x  x坐标
     * @param y  y坐标
     */
    public Hedgehog(char bi, GrassSet gra, float x, float y) {
        super(bi, gra, x, y);
        setSoundId(MusicId.hedgehog);
        changeLifeRate(2);
        attack = (int) (0.2f * World.baseAttack);
        treadable = false;
    }

    protected void initJoint() {
        setH(29);
        setW(w * 3 / 4);
        sethRate(0.85f);
        float x0 = getW();
        float y0 = getH();

        final float footHeight = -5;
        jointList.add(foot1 = new Joint(this, -9, -24, 9, 0, -16, footHeight, TexId.FOOT));
        blade = noBlade;
        jointList
                .add(body = new Joint(this, -x0, -y0, x0, y0, 0, 5, TexId.HEDGEHOG, 1, 5));

        jointList.add(foot = new Joint(this, -9, -24, 9, 0, -12, footHeight, TexId.FOOT, -1));
        normalHand = new Joint(this, -11, -23, 11, 5, 15, footHeight, TexId.FOOT);
        jointList.add(normalHand);// 38
    }


    void toCrepper() {
        setLife(getLifeMax());
        isDead = false;
        angle = 0;
        attack = 0;
        treadable = false;
        rotateSpeed = 0;
        sethRate(0.85f);
        changeSize(0.6f);
        ySpeed += 10;
        setxSpeedMax(1);
        setxSpeedMin(-1);
        body.setTextureId(TexId.REDCREEPER);
        setSoundId(MusicId.creeper4);
    }

    protected void afterInit() {
        setxSpeedMax(3);
        setxSpeedMin(-3);
        super.afterInit();
    }

    public void die() {
        if (getTextureId() == TexId.HEDGEHOG) toCrepper();
        else super.die();
    }

    /**
     * @return culTreadSpeedAndCanBeTread
     */
    public boolean culTreadSpeedAndCanBeTread(Creature c) {
        return false;
    }

    /**
     * @param player 参数名的描述	方法	用来说明传递给一个方法的参数，其中包括参数的类型/类和用法。每个参数各有一个标记。
     * @return 方法返回值的描述    方法	若方法有返回值，对该返回值进行说明。应说明返回值的类型/类和可能的用途。
     * @throws NullPointerException description	方法	说明由方法发出的异常。一个异常采用一个标记，并要给出异常的完整类名。
     * @author mingli 作者的名称	类、接口	说明特定某一段程序代码的作者。每一个作者各有一个标记。
     * @deprecated 类、方法	说明该类的应用程序编程接口 (API) 已被废弃，因此应不再使用。
     * @since 类、方法	例如： sinceJDK 1.1：说明自从有 JDK 1.1 以来，该项已存在了多长时间。
     * @version 111 //版本号	类、接口	说明特定一段代码的版本信息。
     * @see Creeper 类名	类、接口、方法、字段	在文档中生成指向特定类的超文本链接。可以并且应该采用完全合法的类名。
     * @see Hedgehog#treaded(Creature)#jumpHeight    类、接口、方法、字段	在文档中生成指向特定方法的超文本链接。可以并且应该采用完全合法的类名。
     */
    public void treaded(Creature player) {//creeper's code
        super.treaded(player);
        float width = wEdge + player.gethEdge();
        float dx = player.x - x;
        player.setxSpeed(player.getxSpeed() + (float) (-player.getySpeed() * Math.sin(3.14f * dx / width)));
        this.xSpeed += -player.getxSpeed() / 2;
    }

    public void attackAnotherOne(EnemySet es) {
        Creature another;
        for (int i = 0; i < es.cList.size(); i++) {
            another = es.cList.get(i);
            if (Math.abs(x - another.x) < another.getW() + getW()
                    && Math.abs(y - another.y) < another.getH() + getH()) {
                tooClose(another, es);

            }
        }
    }

    protected void tooClose(Creature another, EnemySet es) {
        if (getTextureId() == TexId.HEDGEHOG) es.attacked(another, attack);
        else {//creeper's code
            float dYspeed = another.getySpeed() - getySpeed();
            if (dYspeed > 5) return;//相对向上跳速度相差太大不踩

            if (Math.abs(another.y - another.gethEdge() - y) <= getH()) {// code from creeper
                float dx = Math.abs(x - another.x);
                float ds = getwEdge() + another.getwEdge();
                float rate = 1 - dx * dx / (ds * ds);

                int dsmax = (int) (rate * (gethEdge() + another.gethEdge()));
                float tanxingxishu = 0.2f;
                float zuni = 1;
                yStandCheck(another, dsmax, tanxingxishu, zuni);
                another.setySpeed(another.getySpeed() + another.getG());
            }
        }
    }

    public void randomAction() {// 周期
//		switch ((int) (9 * Math.random())) {
//		case 0:
//			turnRight();
//			break;
//		case 1:
//			turnLeft();
//			break;
//		case 2:
//			stopMove();
//			break;
//		}
    }
}
