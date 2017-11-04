package Enviroments;

import com.mingli.toms.MusicId;
import com.mingli.toms.World;

import java.util.ArrayList;
import java.util.Collections;

import javax.microedition.khronos.opengles.GL10;

import Element.Animation;
import Element.AnimationMove;
import Mankind.Baller;
import Mankind.BattleMan;
import Mankind.BladeMan;
import Mankind.BossRunner;
import Mankind.Creature;
import Mankind.Creeper;
import Mankind.EmpPlatform;
import Mankind.Emplacement;
import Mankind.FireBall;
import Mankind.FlagPlayer;
import Mankind.Flyer;
import Mankind.GunMan;
import Mankind.Hedgehog;
import Mankind.JointCreature;
import Mankind.Player;
import Mankind.Spide;
import Mankind.Spide3;
import Mankind.TestEnemy;
import Mankind.Walker;
import Module.TexIdAndBitMap;
import element2.LightningSet;
import element2.ParticleSet;
import element2.SceneTail;
import element2.Set;
import element2.Tail;
import element2.TexId;


public class GrassSet extends Set{

	public int [][]map;
	private ArrayList<Grass>gList;
	private ArrayList<Grass>drawList;
	public ArrayList<Animation>animationList; 
	private ArrayList<Fruit>coinList;
	private ArrayList<Fruit>fruitList;
	private ArrayList<Creature>enemyList;
	private int grassId;//哪些可有跳上去
	private float speedBack=0.2f;//速度恢复系数
	private int mapHeight;//格子高度数
	private float grid;//格子子大小
	private int mapWidth;
//	private float sx;
//	private float sy;

	int index=0;
	private int zero=-2;
	private Goal goal;
	public float[] bendDownData;
	public float[] bendUpData;
	private Tail grassTail;
	private float viewGrid;//隐藏格子
	public  boolean isCastle;
	public World world;
	public ArrayList<BattleMan> battleManList=new ArrayList<BattleMan>();
	

	private void newMapData(char[] b) {
		char bi = 0;
		for(int i=0;i<b.length;i++){//新建
			bi=b[i];
			if (bi == 9&&mapHeight==0) {
				mapWidth++;
			} else if (bi == 13) {
				mapHeight++;
			}
		}
		mapWidth+=1;//最后一个换行符 导致tab少一个
		
		
		map=new int[mapWidth][mapHeight];//


		for(int i=0;i<mapWidth;i++){
			for(int j=0;j<mapHeight;j++){
				map[i][j]=zero;//整体赋值
			}
		}
		isCastle=mapHeight>mapWidth;
		if(isCastle)viewGrid=0;
		else viewGrid=grid;
	}
	float[] grassData(int x,int y){
		float a,b;
		float []data;
		y=mapHeight-1-y;//0对应15 计数器比索引多一
		data=new float[]{
				a=x*grid,
				b=y*grid,
				a+grid,
				b+grid,
		};
		map[x][y]=index++;//
		return data;
	}

	public void loadTexture(){
		for(int i=0;i<getgList().size();i++){
			getgList().get(i).loadTexture();
		}

	}
	public byte []saveMap(){
		
		byte[][] mapdata=getMapArray();
		
		byte[]mapsequence=new byte[mapWidth*2  *mapHeight];
		
		for(int j=0;j<mapHeight;j++){
			for(int i=0;i<mapWidth;i++){
				mapsequence[2*(j*mapWidth+i)]=mapdata[i][mapHeight-1-j];
				mapsequence[2*(j*mapWidth+i)+1]='	';// tab
			}
			mapsequence[2*(j*mapWidth+mapWidth-1)+1]=13;// /r/n
		}
		
		
		return mapsequence;
	}
	public byte[][] getMapArray() {
		int  mapWidth = 0,mapHeight = 0;
		
		
		int tempOS;
		for(Animation a:animationList){
			 if(mapWidth<(tempOS=(int)(a.startX/grid)))mapWidth=tempOS;

			 if(mapHeight<(tempOS=(int)(a.startY/grid)))mapHeight=tempOS;
		}

		
//		xOffSet++;yOffSet++;// it can't be lager because when it is less than 0 it can be bigger than it self if u use / caculate
		
		mapWidth+=1;mapHeight+=1;
		byte [][]mapdata=new byte[mapWidth][mapHeight];
		for(int i=0;i<mapWidth;i++){
			for(int j=0;j<mapHeight;j++){
				mapdata[i][j]=' ';// a value let mapsequences's length be constant
			}
		}
		
		
		for(Animation a:animationList){
			if(a.startX<0||a.startY<0)continue;

			mapdata[(int) (a.startX/grid)][(int) (a.startY/grid)]=(byte) a.mapSign;
		}
		return mapdata;
	}
	public GrassSet(float grid,char[]b,LightningSet lns, World world){
		this.grid=grid;
		this.world = world;
		gList=new ArrayList<Grass>();
		ArrayList<Grass>burrorList=new ArrayList<Grass>();
		coinList=new ArrayList<Fruit>();
		enemyList=new ArrayList<Creature>();
		emplacementList = new ArrayList<Emplacement>();;
		drawList=new ArrayList<Grass>();
		animationList=new ArrayList<Animation>(); 
		fruitList=new ArrayList<Fruit>();
		char bi ;
		int x=0,y=0;//
		float edge=grid/8;
		newMapData(b);
		newBendData();
		
			Grass grass;
			Fruit fruit;
			Creature creature;
		
		for(int i=0;i<b.length;i++){
			bi= b[i];
//			if(bi!=9)bendDataCheck(x,y);
			switch(bi){
				case 48:gList.add(grass=new Grass(bi,grassData(x, y),TexId.SOIL,true));grass.name="土块";break;//0 一个数字 还有一个符号
				case 102:gList.add(grass=new Fog(bi,grassData(x, y),TexId.FOG,edge, lns));grass.name="云朵";break;//f
				case 103:gList.add(grass=new Grass(bi,grassData(x, y),TexId.SOILGRASS,true));grass.name="草地土块";break;//g
				case 108:gList.add(grass=new Grass(bi,grassData(x, y),TexId.STONEGRASS));grass.name="草地石头";break;//l lucus 灌木丛
				case 'i':gList.add(grass=new Grass(bi,grassData(x, y),TexId.BANK));grass.name="铁板砖";break;//i iron 铁
				case 'q':gList.add(grass=new GrassPrick(bi,grassData(x, y),90));grass.name="↑上尖刺";break;//p 刺shang
//				8593				8595				8592				8594 shang xcia zou you
//				-111				-109				-112				-110 byte
				case 'r':gList.add(grass=new GrassPrick(bi,grassData(x, y),270));grass.name="↓下尖刺";break;//p 刺xia
				case 'v':gList.add(grass=new GrassPrick(bi,grassData(x, y),180));grass.name="←左尖刺";break;//p 刺zuo
				case 'y':gList.add(grass=new GrassPrick(bi,grassData(x, y),0));grass.name="→右尖刺";break;//p 刺you
				case 115:gList.add(grass=new Grass(bi,grassData(x, y),TexId.STONE));grass.name="石头";break;//s
				case 116:gList.add(grass=new BigGrass(bi,grassData(x, y),TexId.TREE,true,edge));grass.name="树叶";break;//t
				case 119:gList.add(grass=new Grass(bi,grassData(x, y),TexId.WOOD));grass.name="木头";break;//w
				case 'z':gList.add(grass=new Grass(bi,grassData(x, y),TexId.ZHUAN,true));grass.name="砖块";break;//z
				case 'm':gList.add(grass=new GrassSticker(bi,grassData(x, y),TexId.STICKER));grass.name="小树枝";break;
				case 85:int ran=(int) (Math.random()*2);
					Burrow bro=new Burrow(bi,grassData(x, y),TexId.BAMBOO,grid*3/32,ran,null);
					burrorList.add(bro);
					gList.add(bro);
					bro.name="竹子";
					if(World.editMode)break;// no 2 if edit
						Burrow bro2=new Burrow(' ', grassData(x+1, y),TexId.BAMBOO,grid*3/32,ran,bro);
						burrorList.add(bro2);
						gList.add(bro2);
						bro2.name="竹子";
					break;//U 表示地洞 竹子
				case 49: coinList.add(fruit=new Coin('1', (x+0.5f)*grid,(mapHeight-y-0.5f)*grid));fruit.name="金币";break;//1
				case 50: goal=new Goal(bi, x*grid,(mapHeight-y-1)*grid);goal.name="目标";break;//2
				case 51: break;
				case 52:break;
				case 'B':fruitList.add(fruit=new FruitGun(bi, (x+0.5f)*grid,(mapHeight-y-0.5f)*grid,TexId.MISSILE));fruit.name="火箭筒";break;//B boom
				case 67:fruitList.add(fruit=new ChanceFruit(bi, (x+0.5f)*grid,(mapHeight-y-0.5f)*grid,World.baseWudiTime));fruit.name="复活蛋";break;//C 加机会
				case 68:fruitList.add(fruit=new FruitGun(bi, (x+0.5f)*grid,(mapHeight-y-0.5f)*grid,TexId.HOOKGUN));fruit.name="钩爪";break;//D drag hook
				case 'Q':fruitList.add(fruit=new Fenshen(bi, (x+0.5f)*grid,(mapHeight-y-0.5f)*grid));fruit.name="分身果";break;
				case 71:fruitList.add(fruit=new Gao(bi, (x+0.5f)*grid,(mapHeight-y-0.5f)*grid,9999));fruit.name="十字镐";break;//G
				case 72:fruitList.add(fruit=new sizeFruit(bi, (x+0.5f)*grid,(mapHeight-y-0.5f)*grid));fruit.name="变形果";break;//H
				case 75:fruitList.add(fruit=new FruitBlade( bi,(x+0.5f)*grid,(mapHeight-y-0.5f)*grid));fruit.name="剑";break;//k
				case 'J':fruitList.add(fruit=new FruitFly( bi,(x+0.5f)*grid,(mapHeight-y-0.5f)*grid, 9999));fruit.name="飞行套装";break;// J jumppen qi guo
				case 77:fruitList.add(fruit=new FruitGun( bi,(x+0.5f)*grid,(mapHeight-y-0.5f)*grid,TexId.JUJI));fruit.name="狙击枪";break;//M 
				case 79:fruitList.add(fruit=new FruitGun(bi, (x+0.5f)*grid,(mapHeight-y-0.5f)*grid,TexId.PUTONGQIANG));fruit.name="步枪";break;//O normal bullet
				case 83:fruitList.add(fruit=new FruitGun( bi,(x+0.5f)*grid,(mapHeight-y-0.5f)*grid,TexId.SHOTGUN));fruit.name="散弹枪";break;//S 
				case 'P':fruitList.add(fruit=new FruitGun( bi,(x+0.5f)*grid,(mapHeight-y-0.5f)*grid,TexId.BOOMGUN));fruit.name="炸弹枪";break;//
				case 'L':fruitList.add(fruit=new FruitGun( bi,(x+0.5f)*grid,(mapHeight-y-0.5f)*grid,TexId.ZIDONGDAN));fruit.name="自动泡泡枪";break;//L
				case 'N':fruitList.add(fruit=new FruitGun( bi,(x+0.5f)*grid,(mapHeight-y-0.5f)*grid,TexId.GUANGDANQIANG));fruit.name="光弹枪";break;
				case 84:if(World.rpgMode){
					fruitList.add(fruit=new Tomato(bi, (x+0.5f)*grid,(mapHeight-y-0.5f)*grid, 500));fruit.name="恢复药";break;//T 加血道具
				}
				case 110:fruitList.add(fruit=new Toukui(bi, (x+0.5f)*grid,(mapHeight-y-0.5f)*grid,9999));fruit.name="头盔";break;//n toukui
				case 'x':fruitList.add(fruit=new FruitGun(bi, (x+0.5f)*grid,(mapHeight-y-0.5f)*grid,TexId.SHUFUDAN));fruit.name="束缚枪";break;//
				case 'V':fruitList.add(fruit=new Wudi(bi, (x+0.5f)*grid,(mapHeight-y-0.5f)*grid));fruit.name="无敌果";break;//
				case 'X':break;
				case 'A':
					player=world.changePlayer(' ',null,this,(x+0.5f)*grid,(mapHeight-y)*grid);
//					player = new Player(' ', this, world,(x+0.5f)*grid,(mapHeight-y)*grid);
					player.name="主角";
					// here has no break because these two char has same code and different code
				case 'Y':
					if(player==null)player=new FlagPlayer(bi,this,world,(x+0.5f)*grid,(mapHeight-y)*grid);
//					setStartPosition(bi,(x+0.5f)*grid,(mapHeight-y)*grid);
					if(World.editMode){
						Creature c=new JointCreature(bi,this,(x+0.5f)*grid,(mapHeight-y)*grid);
						c.name="初始位置";
						c.setLifeMax(99999);
						c.setLife(99999);
						c.attack=0;
						enemyList.add(c);
					}
					break;
				case 69:
					emplacementList.add((Emplacement) (creature=new EmpPlatform(bi,this,(x+0.5f)*grid,(mapHeight-y)*grid)));
					creature.name="炮塔";
					break;//E paotai
				case 70:enemyList.add(creature=new FireBall(bi,this,(x+0.5f)*grid,(mapHeight-y)*grid));creature.name="火球";
					break;//F
				case 97:enemyList.add(creature=new Walker(bi,this,(x+0.5f)*grid,(mapHeight-y)*grid));creature.name="小人";break;		//a
				case 98:enemyList.add(creature=new Flyer(bi,this,(x+0.5f)*grid,(mapHeight-y)*grid));creature.name="鸟";break;//b
				case 99:enemyList.add(creature=new Creeper(bi,this,(x+0.5f)*grid,(mapHeight-y)*grid));creature.name="乌龟";break;//c
				case 100:enemyList.add(creature=new Baller(bi,this,(x+0.5f)*grid,(mapHeight-y)*grid));creature.name="球球";break;//d
				case 101:enemyList.add(creature=new Hedgehog(bi,this,(x+0.5f)*grid,(mapHeight-y)*grid));creature.name="刺猬";break;//e
				case 'o':enemyList.add(creature=new GunMan(bi,this,(x+0.5f)*grid,(mapHeight-y)*grid));creature.name="枪手";break;//e
				case 'p':enemyList.add(creature=new BladeMan(bi,this,(x+0.5f)*grid,(mapHeight-y)*grid));creature.name="剑客";break;//e
				case 'u':enemyList.add(creature=new BossRunner(world, bi,this,(x+0.5f)*grid,(mapHeight-y)*grid));creature.name="BOSS跑腿";break;//e
				
				case 106://j
//					int ran1=(int) (Math.random()*2);
//					if(ran1==0){
//						newSpide3(grid, bi, x, y);
//					}
//					else 
						emplacementList.add((Emplacement) (creature=new Spide(bi,this,(x+0.5f)*grid,(mapHeight-y-0.5f)*grid)));creature.name="蜘蛛";
					break;
				case 'k':
					enemyList.add(creature=new TestEnemy(bi,this,(x+0.25f)*grid,(mapHeight-y-0.25f)*grid));creature.name="木偶";
					enemyList.add(creature=new TestEnemy(this,(x+0.25f)*grid,(mapHeight-y-0.75f)*grid));creature.name="木偶";
					enemyList.add(creature=new TestEnemy(this,(x+0.75f)*grid,(mapHeight-y-0.25f)*grid));creature.name="木偶";
					enemyList.add(creature=new TestEnemy(this,(x+0.75f)*grid,(mapHeight-y-0.75f)*grid));
					creature.name="木偶";
					break;
				
				case 53: break;//5 结束标志
				case 13:x=0;y++;break;
				//一个是”\r“一个是“\n”
				case 10:break;
				case 9:x++;break;//tab
				case 0:break;
			}
		}
		
		enemyList.addAll(emplacementList);
		
		setGrassId(getgList().size());
		
		sort();//添加并调整绘画顺序
		Collections.reverse(burrorList);//反续
		
		drawList.addAll(burrorList);
		
	
		
		loadTexture();
		
		
		loadSound();
		ps=new ParticleSet(this, 10);
		
		animationList.addAll(coinList);
		animationList.addAll(fruitList);
		animationList.addAll(enemyList);
		animationList.addAll(gList);
		
		initGoreAnimation();
		
		float x1,x2;
		if(Math.random()-0.5>0){	
			x1=grid*mapWidth-500; 			x2=500;		}
		else {			x1=500;	x2=grid*mapWidth-500; 		}		
		
		if(goal==null)goal=new Goal('2', x1, 400);
		else goal.searchBoss(player,enemyList);// else is important
		animationList.add(goal);
		if(player==null)player=new Player('A', this, world, x2, 400);
		player.goal=goal;
		initBattleMan();
	}
	private void initBattleMan() {
		// TODO Auto-generated method stub

		if(friendList==null)friendList=new ArrayList<Creature>();
		char bi=' ';
		
		BattleMan bm;
		float _x=grid*mapWidth-player.x;// 相反方向的 x
		if(world.force_in_battle==World.RED_FORCE){
			float tempX=player.x;
			player.x=_x;
			player.startX=_x;
			player.setxPro(_x);
			_x=player.x;
		}
		// enemy and friend's position is xiangfande
		
		if(world.force_in_battle==World.RED_FORCE){
			for(int j:world.blueList){
				enemyList.add(bm=new BattleMan(bi, this,_x,player.y,World.BLUE_FORCE, j));
				battleManList.add(bm);
			}
			for(int j:world.redList){
				friendList.add(bm=new BattleMan(bi, this,player.x,player.y,World.RED_FORCE, j));
				battleManList.add(bm);
			}
		}else if(world.force_in_battle==World.BLUE_FORCE){
			for(int j:world.redList){
				enemyList.add(bm=new BattleMan(bi, this,_x,player.y,World.RED_FORCE, j));
				battleManList.add(bm);
			}
			for(int j:world.blueList){
				friendList.add(bm=new BattleMan(bi, this,player.x,player.y,World.BLUE_FORCE, j));
				battleManList.add(bm);
			}
		}
		
	}
	private void newSpide3(float grid, char bi, int x, int y) {
		int size=(int) (3*Math.random()+1);// +1 to avoid length is zero
		Creature[] spideMans = new Creature[size];
//						float dy=Spide.dsmax;
		for(int j=0;j<spideMans.length;j++){
				spideMans[j]=new Walker(' ',this,(x+0.5f)*grid,(mapHeight-y)*grid);
		};
		for(Creature spideMan:spideMans){
			enemyList.add(spideMan);
		}
		emplacementList.add(new Spide3(bi,this,(x+0.5f)*grid,(mapHeight-y-0.5f)*grid,spideMans));
	}
	private void initGoreAnimation() {
		topAni=new AnimationMove();
		goreAni=new AnimationMove();
		goreAni.w=32;
		goreAni.h=32;
		goreAni.loadTexture();
	}
	public void toStartPosition(){
		for(int i=0;i<animationList.size();i++){
			Animation ani = animationList.get(i);
			ani.toStartPosition();
		}
	}

	private void newBendTail() {
		grassTail=new SceneTail(mapWidth,TexId.BAMBOOPIPLE);
//		grassTail.width=(int) (1*grid);
		
		int width=(int) (bendUpData[0]-bendDownData[0]);
		int dW=width*1/4;
		grassTail.w=width*3/2;
		
		for(int i=0;i<mapWidth;i++)
			grassTail.tringer(i*grid,bendUpData[i]+dW);
	}

	private void newBendData() {
		bendDownData=new float[mapWidth];
		bendUpData=new float[mapWidth];
		
		float grid=5*this.grid;
		for(int i=0;i<mapWidth;i++)
			bendUpData[i]=grid+this.grid*(float) Math.sin(i/3f);
		
		 grid=1*this.grid;
		for(int i=0;i<mapWidth;i++)
			bendDownData[i]=grid+this.grid*(float) Math.sin(i/3f);
		
		newBendTail();
	}


	public void downHoleCheck(float x,float y){
		int x1=(int) (x/grid);
		int y1=(int) (y/grid);
		if(x1<0||x1>=mapWidth||y1<0||y1>=mapHeight)return;
		
		int id;
		if((id=map[x1][y1])==zero)return;
		Grass grass=gList.get(id);
			if(grass.getTextureId()==TexId.BAMBOO)
				grass.setTextureId(TexId.BAMBOOHEART);		
		
	}
	
	void sort(){//添加并调整绘画顺序
		int size=gList.size();
		 isCastle = false;
		if(mapHeight>mapWidth)isCastle=true;
		ArrayList<Grass>leafList=new ArrayList<Grass>();
		float xMax = grid*mapWidth;
		for(int i=0;i<size;i++){
			Grass gra=gList.get(i);
			TexIdAndBitMap textureId = gra.getTextureId();
			if (textureId == TexId.FOG||textureId==TexId.TREE) {
				leafList.add(gra);
			}else {
				if(isCastle)drawList.add(gra);
				else if(gra.data[0]!=0&&gra.data[2]!=xMax||gra.data[1]<=grid)
					drawList.add(gra);
			}
		}
		drawList.addAll(leafList);
	}
	public void drawElement(GL10 gl) {
//		grassTail.drawElement(gl);
		if(gore) {
			topAni.springCheck(goreAni, 0,3f / 5f,0.9f);
			goreAni.drawElement(gl);
		}
		
		for(int i=0;i<drawList.size();i++){
			Grass g=drawList.get(i);
			if(g.x>=Player.gx1&&g.x<=Player.gx2
					&&g.y>=Player.gy1&&g.y<=Player.gy2)
			{
				if(g.notBroken)
				g.drawElement(gl);
			}
		}
		gl.glColor4f(0.015f, 0.1f, 0.1f, 0.8f);
		for(int i=0;i<drawList.size();i++){
			Grass g=drawList.get(i);
			if(g.x>=Player.gx1&&g.x<=Player.gx2
					&&g.y>=Player.gy1&&g.y<=Player.gy2)
			{
				if(!g.notBroken)
				g.drawElement(gl);
			}
		}
		gl.glColor4f(1,1,1,1);
		
//		for(Draw draw :effectList)
//			draw.drawElement(gl);
		
		ps.drawElement(gl);
		goal.drawElement(gl);
	}
	public void run1(){
		setLiving(true);
		while(isRunning()){
//			topAction();
			World.timeRush();
		}
		setLiving(false);
	}


	private boolean gore;//顶
//	private float xMax;// x s max not grid index
	private ArrayList<Emplacement> emplacementList;
	private int topId;
	public void loadSound() {
		setSoundId(MusicId.gore);
	}

	@Override
	public void playSound() {
		music.playSound(getSoundId(), 0);
	}
	public void up(int topId,float xSpeed,float ySpeed) {
		this.topId = topId;
		Grass g=gList.get(topId);
		
		g.gored();
		
		gore=true;			// gore start
		playSound();
		
		topAni.setPosition(g.x, g.y);
		goreAni.setPosition(g.x, g.y);
		
		goreAni.setTextureId(g.getTextureId());
		
		goreAni.setxSpeed(xSpeed);
		goreAni.setySpeed(ySpeed);
//		goreAni.setxSpeed(5);
//		goreAni.setySpeed(5);
	}

	public int getGrassId() {
		return grassId;
	}
	public void setGrassId(int grassId) {
		this.grassId = grassId;
	}
	public float getSpeedBack() {
		return speedBack;
	}
	public void setSpeedBack(float speedBack) {
		this.speedBack = speedBack;
	}
	public void disappear(int i){
//		gCount--;
//		setGrassId(gCount);
//		data[i];
		getgList().remove(i);
	}
	public ArrayList<Grass> getgList() {
		return gList;
	}
	public void setgList(ArrayList<Grass> gList) {
		this.gList = gList;
	}

	public float getGrid() {
		return grid;
	}

	public void setGrid(float grid) {
		this.grid = grid;
	}

	public int getMapWidth() {
		return mapWidth;
	}

	public void setMapWidth(int xLength) {
		this.mapWidth = xLength;
	}
	public int getMapHeight() {
		return mapHeight;
	}
	public void setMapHeight(int mapHeight) {
		this.mapHeight = mapHeight;
	}
	public ArrayList<Fruit> getCoinList() {
		return coinList;
	}
	public void setCoinList(ArrayList<Fruit> coinList) {
		this.coinList = coinList;
	}

	public ArrayList<Creature> getEnemyList() {
		return enemyList;
	}

	public void setEnemyList(ArrayList<Creature> enemyList) {
		this.enemyList = enemyList;
	}

	public int getZero() {
		return zero;
	}
	public Goal getGoal() {
		return goal;
	}
	public void setGoal(Goal goal) {
		this.goal = goal;
	}
	public ArrayList<Fruit> getFruitList() {
		return fruitList;
	}
	public void setFruitList(ArrayList<Fruit> fruitList) {
		this.fruitList = fruitList;
	}
	public boolean isGore() {
		return gore;
	}
	public void setGore(boolean gore) {
		this.gore = gore;
	}

	public float getViewGrid() {
		return viewGrid;
	}
	public void setViewGrid(float viewGrid) {
		this.viewGrid = viewGrid;
	}
	public float getRate(int landId) {
		TexIdAndBitMap textureId=gList.get(landId).getTextureId();
		if (textureId == TexId.ZHUAN) {
			return 0.5f;
		}else if(textureId==TexId.STONE) {
			return 0.4f;
		} else if(textureId==TexId.BANK) {
			return 0.3f;
		}
		return 0.6f;
	}
	public void toNull(int grassID, int mx1, int my1) {
		Grass grass=gList.get(grassID);

//		if(drawList.contains(grass))
		if(grass.breakCheck())
			map[mx1][my1]=zero;
	}
	public ArrayList<Emplacement> getEmplacementList() {
		return emplacementList;
	}
	public void setEmplacementList(ArrayList<Emplacement> emplacementList) {
		this.emplacementList = emplacementList;
	}
	ParticleSet ps;
	public Player player;
	private AnimationMove topAni;// master
	public AnimationMove goreAni;// springer
	public ArrayList<Creature> friendList=new ArrayList<Creature>();
	
	public void particleCheck(int tringerId, int i, AnimationMove player) {
		// TODO Auto-generated method stub
		ps.tringerCheck(tringerId, i, player);
	}
	public Grass getFromXY(int ex, int ey) {
		// TODO Auto-generated method stub
		return gList.get(map[ex][ey]);
	}
}
