package Mankind;

import com.mingli.toms.World;

import javax.microedition.khronos.opengles.GL10;

import Enviroments.GrassSet;
import Weapon.Blade;
import Weapon.Bullet;
import Weapon.GunDraw;
import element2.TexId;

public class PlayerBlader extends Player {
	private boolean rotateBlade;

	public PlayerBlader(char mapSign, GrassSet gra, World world, float x, float y) {
		super(mapSign, gra, world, x, y);
		drawAngleSpeed=18f;
		cap.setTextureId(TexId.HEDGEHOG);
		realBlade=new Blade(this,realBlade.getEs()){
			final int frameTime=2;//美珍走几次
			public void tailTringer() {

				super.tailTringer();

				for(int i=1;i<frameTime&&this.isFire();i++){
					super.incAngle();
					super.tailTringer();
				}// angle+=frameTime-1  but tailTrange =frameTime
			}
			public void incAngle() {
				float angleAgo=this.angle;
				super.incAngle();
				if(angleAgo%360> angleEnd&&angle<angle){
					blade.playSound();
					targetCheck();//
				}
			}

			protected void angleGotTargetCheck(double dx, double dy, Creature spi) {
				if(rotateBlade)gotTarget(spi);//  xuanzhuna gong ji
				else super.angleGotTargetCheck(dx,dy,spi);
			}
		};
		realBlade.initLengthAndSpeed(200,-20);
		expression.setTextureId(TexId.EXPRESSIONENEMY);
//		haveBlade();
		extendsBladeFruitId=0;//only is not -1 can have blade  and  have blade()  can not use sendEmptyMessage
//		haveGunDraw.setTextureId(TexId.FEIBIAO);
		haveGunDraw= new GunDraw(0, this, -32, -32,32, 32,8 , -10,				TexId.FEIBIAO, 1, 5){
			public void positionCheck(){
				if(_4!=gunAngle )setDagree(32);
				else if(Math.abs(getDagree())>0.01f)setDagree(getDagree()*0.95f);// smaller and small
			}
			@Override
			public void setAngle(float angle) {}
		};

		baseGunLength=32;
	}
	public void jump(float rate){
		super.jump(rate);
		if(rate>0.33)rotateBlade=true;
	}
	protected void tooDown() {
		super.tooDown();
		rotateBlade=false;
	}
	protected void gunAngleAndCdCheck() {
		// TODO Auto-generated method stub
		if(coolingId>0)coolingId--;
		if(gunAngle !=_4){
			if(gun!=null) {
				if(coolingId<1){
					setGunAngle(angle+gunAngle *180/3.14);//
					gun.gunCheck(gunAngle);
					coolingId=gun.cd;
				}
			}
		}
	}
	Shader shader;
	protected void initEffect(float x, float y) {
		super.initEffect(x,y);
		shader = new Shader(0.05f, this);
	}
	public void setDoubleClicked(boolean doubleClicked) {
		super.setDoubleClicked(doubleClicked);
		shader.backToMaster();
	}

	@Override
	protected void attack() {
		if(!rotateBlade)super.attack();
		else{
//			blade.targetCheck();
		}
	}

	public void drawElement(GL10 gl) {

		if(fallen){//角度回归
			{
				angle=angle%360;
				if(angle<-90)angle=360+angle;// >90 continue roll
			}
			if(angle>drawAngleSpeed)angle-=drawAngleSpeed;
			else if(angle<-drawAngleSpeed)angle+=drawAngleSpeed;
			else   angle=0;

		}
		else {
			if(rotateBlade)
				angle-=drawAngleSpeed;
		}

		if (isDoubleClicked()) shader.drawElement(gl);//影子
		super.drawElement(gl);
	}
	@Override
	public void changeGun(int textureId) {
		super.changeGun(textureId);
		if(gun!=null){
			for(Bullet b:gun.bList){
				b.setTextureId(TexId.FEIBIAO);
			}
		}
	}
}
