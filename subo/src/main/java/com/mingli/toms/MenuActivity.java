package com.mingli.toms;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import Enviroments.FruitSet;
import Mankind.Creature;
import Module.TexIdAndBitMap;
import aid.Ad;
import aid.Client;
import aid.ConsWhenConnecting;
import aid.Log;
import aid.Producer;
import aid.RandomMap;
import aid.Shop;
import aid.SkinLoader;
import aid.Tips;
import aid.UserName;
import element2.TexId;
import fileRW.FileActivity;
import onlineStageActivity.OnlineFileActivity;

//import aid.RandomMap;

public class MenuActivity extends Activity {


	
	private SharedPreferences sp;
//	private static Context content;
//	private static Context myActivity;
	protected static int FPS;//
	public Ad ad;
	private int startTime;

	private StateWindow stateWindow;
	public World world;
	private ButtonController btnc;
	private StartMenu startMenu;
	private GameMenu gameMenu;
	private static MyHandler myHandler;
	private static Dialog dl;
	private Editor editor;
	public static int screenWidth, screenHeight;
	public int score;
	public int chance;
	public int coinCount;

	private Stagechoosser stageChooser;

	int mapIndex;
	int in[];
	String starString = "00000000000000000";
	public int[] star;
	private static final int NOT_INIT_USER_ID = -1;
	public static final int NO_USER_ID = 5;
	public static int userId=NOT_INIT_USER_ID;

	private String itemString = "HT";// a sizeFruit and a Tomato will give to ever player 
	char[] item;
	
	private Shop shop;

	private Tips tips;

	Producer buildGrass;
	 Producer buildCreature;
	 Producer buildGoods;
	private Client client;


	public static boolean titleMode=true;
	private int mapFileId;


	protected void onDestroy() {
//		System.exit(0);
		finish();//you zhe ju zhi jie bu chongxin create
		super.onDestroy();
	}
	public void finish() {
			if (world != null) {
				world.onDestroy();
				world = null;
			}	
			titleMode=true;
//			saveUserMessage();
			editor.putBoolean("allMode", World.allMode);
			editor.putBoolean("developMode", World.developMode);
			editor.commit();
			 stateWindow=null;
			 btnc=null;
			 gameMenu=null;
			 myHandler=null;
			
			if(client!=null) client.closeStream();
		super.finish();
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// quanpin
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		// buxiumian
		
//		myActivity = this;
		
		
		
		initWindowSize();

		initSp();
		initGame();
		
		
		initAd();
		getMessage();
		initDialog();
		initTips();
		
		initNetController();// must after getMessage();
		
		mapIndex = getMaxMapIndex();
	}

	private void initNetController() {
		if(client==null)client = new Client(MenuActivity.this);
		new Thread(){
			public void run(){
				while(userName==null||userName==""||userId<0){
					World.timeRush(1000);
				}
				client.connect();
			}
		}.start();
	}

	private void initTips() {
		// TODO Auto-generated method stub
		tips=new Tips(this);
	}

	void initGame() {

		 if (myHandler == null)
		myHandler = new MyHandler();

		// if (world == null)
		world = new World(this,myHandler);
		addView(world);
		// addContentView(world, new
		// LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
	}

	
	void initStartMenu() {
		
		startMenu = new StartMenu(this);
		startMenu.loadStartMenu();
		
		if(client!=null)Client.send(ConsWhenConnecting.REQUEST_PIMING_INFO+userId);
//		initPaimingInfo();
		

		
		if (startTime > 2) {
			showBanner((ViewGroup) findViewById(R.id.container));
		}
	}
	
	

	private void initSp() {
		// TODO Auto-generated method stub
		sp = getSharedPreferences("gameStore", MODE_PRIVATE);
		startTime = sp.getInt("startTime", 1);
		
		editor = sp.edit();
		editor.putInt("startTime", startTime + 1);
		editor.commit();
	}

	private void initAd() {
		// TODO Auto-generated method stub
		ad = new Ad(this);
		ad.loadAppWallAd();
		ad.loadInterstitial();
	}

	private void initWindowSize() {
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
//		content = getBaseContext();
	}

	public void startGame(int mode) {
		world.gameMode=mode;
		startGame();
	}

	static int heroId=0;
	public void heroSelected(int pos) {
		if(pos==heroId)return;
		heroId=pos;
//		changeSkin();
		switch(pos){
			case 0:			world.changePlayer(world.player.mapSign,world.player,world.gra,world.player.x,world.player.y);break;
			case 1:			world.changePlayer(world.player.mapSign,world.player,world.gra,world.player.x,world.player.y);break;
			case 2:			world.changePlayer(world.player.mapSign,world.player,world.gra,world.player.x,world.player.y);break;

		}
	}

	public void initBuyLife(GameMenu gameMenu) {
		gameMenu.initBuyLifeView(gameMenu.popupWindow.getContentView(),world.getFruitSet().shopList.get(0));
	}

	class MyHandler extends Handler {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (stateWindow != null)
				stateWindow.handleCheck(msg);
			 if(btnc!=null)btnc.handleCheck(msg);
			 
			 if(startMenu!=null)startMenu.handleCheck(msg);
			 
			switch (msg.what) {
			case World.LOADED:
//				if(startMenu==null)// if it is loading game title or loading game
				if(titleMode) {
					initStartMenu();
					 if(stateWindow == null)
						 stateWindow = new StateWindow(MenuActivity.this, world, tips);
				} else {
					loadGameMenu();
				}
				break;
				
			case World.GAMEOVER:
				gameover();
				break;
			case World.SUCCEED:
				succeed();
				break;
			case World.DIALOG:
				showDialogView();
				break;
			case World.REQUEST_TO_STARTGAME:
				startGame();
				break;
			}
		}

		private void gameover() {
			if(titleMode)return;
			// TODO Auto-generated method stub
		
//			gameMenu.removeView();//没啥用
//			showInAd();
//			gameMenu.addView();
			gameMenu.gameover();
		}

		private void succeed() {
			// TODO Auto-generated method stub

			gameMenu.succeed();
			if(!world.CAN_GO_NEXT_STAGE)return;
			if (getMaxMapIndex() == mapIndex)// only the last stage save
//				if(mapIndex<=Map.max){
					editor.putInt("mapIndex", mapIndex + 1);
					editor.commit();
//				}
		}
		
	}

	void startGame() {
		quitGame();
		if (startMenu != null){
			startMenu.hide();
			startMenu=null;
		}
		if (stageChooser != null){
			stageChooser.hide();
			stageChooser=null;
		}
		
		titleMode=false;
		
		world.startGame(mapIndex);
		if(mapIndex==Map.max+1){
			//解锁所有模式和开发者模式
			World.allMode=true;
			World.developMode=true;
		}
		
		Log.d("startGame");
	}

	void loadGameMenu() {
		 if(stateWindow == null)
			 stateWindow = new StateWindow(this, world, tips);
			 
//		 if (gameMenu == null)
		 
		 if(gameMenu!=null)gameMenu.returnCurStateAndHide();
		 
		gameMenu = new GameMenu(this, world);
//		 if(shopView==null)
		shop = new Shop(this, world);
		
		if(World.editMode){
			buildGrass = new Producer(this, world,world.buildGrassList);
			buildCreature = new Producer(this, world,world.buildCreatureList);
			buildGoods = new Producer(this, world,world.buildGoodsList);
		}
		
		 if (btnc != null)btnc.hide();
		btnc = new ButtonController(MenuActivity.this, world, gameMenu);// 添加按键
		btnc.adController();

		// if (itemWindow == null)
		if(mapIndex==1)showGuideDialog();
	}


	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (startMenu != null){
				
				showExitDialog();
			}
			else if (stageChooser != null) {

				stageChooser.hide();
				// ////////////////
				loadTitleView();
				// super.onKeyDown(keyCode, event);
			} else if (world != null) {
				if (shop != null){
					shop.hideCheck();
				}
				
				if (buildGrass != null){
					buildGrass.hideCheck();
				} if (buildCreature != null){
					buildCreature.hideCheck();
				}if (buildGoods != null){
					buildGoods.hideCheck();
				}
				
				if (gameMenu!=null&&gameMenu.returnCurStateAndHide()){
					pauseGame();
					gameMenu.showWindow(world);
				}else {
					if (world != null)
						world.resume();
				}
				
			}
			return false;
		} else if (keyCode == KeyEvent.KEYCODE_MENU) {
			showDialog(null, "FPS:" + FPS,TexId.CAP);
//			tietuCheck();
			// if(world!=null)world.succeed();

			// Intent intent=new Intent();
			// intent.setData(Uri.parse("http://www.baidu.com"));

			/*
			 * intent.setAction(Intent.ACTION_DIAL);
			 * intent.setData(Uri.parse("tel:10000")); startActivity(intent);
			 */

			return super.onKeyDown(keyCode, event);
		} else
			return super.onKeyDown(keyCode, event);
	}

//	int index;
//	private void tietuCheck() {
//		// TODO Auto-generated method stub
//		Log.i("gl:"+Render.gl);
//		if(Render.gl==null)return;
//		if(index++%2==0)Render.gl.glEnable(GL10.GL_TEXTURE_2D);
//		else Render.gl.glDisable(GL10.GL_TEXTURE_2D);
//	}

	void initDialog() {
		if (dl != null)
			return;
		dl = new Dialog(MenuActivity.this, R.style.myDialog);
		View v = getLayoutInflater().inflate(R.layout.dialog, null);
		dl.setContentView(v);
		dl.getWindow().setGravity(Gravity.LEFT | Gravity.TOP);

		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dl.cancel();
			}
		});
	}

	private static String speaker;

	private static String talk;

	private static TexIdAndBitMap resId;
	private static int direction1;
	private static int direction2;
//	private Client client;
	public  String userName;
	public ArrayList<Info4> userInfoList=new ArrayList<Info4>();
	private String paimingString;
	String selectedToSaveOnlineFileName="";
	private final String requestFoItemMap = "requestFoItemMap.txt";
	private RandomMap randomMap;
	public static int maxMapId;
	static BattleActivity battleActi;
	public void initPaimingInfo(){
		paimingString=sp.getString("paimingString", "00000"+userName+" "+score+"1");
		
		String[] sa = paimingString.split(" ");
		userInfoList.clear();
		try{
			for(int i=0;i<sa.length;i+=3){
//				userInfoList.add(new Info4(Integer.parseInt(sa[i]),sa[i+1],Integer.parseInt(sa[i+2]),Integer.parseInt(sa[i+3])));
				userInfoList.add(new Info4(0,sa[i],Integer.parseInt(sa[i+1]),Integer.parseInt(sa[i+2])));
				// default id is 0
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		myHandler.sendEmptyMessage(World.NOTIFY_PAIMING_CAHNGED);
	}
	
	
//	Info3 inf;
//	for(int i=0;i<10;i++){
//		userInfoList.add(inf=new Info3(i+1,userName.randomName(),(int)(score*Math.random()+0.5)));
//	}
//	userInfoList.set(5, new Info3(6,userName,score));
	
	public void savePaiming(String paimingString) {
		this.paimingString = paimingString;
		// TODO Auto-generated method stub
		
		editor.putString("paimingString", paimingString);
		editor.commit();
	}

//	public static boolean titleMode;
	
	public static void showDialog(String speaker, String talk, TexIdAndBitMap resId,int direction1,int direction2) {
		MenuActivity.direction1=direction1;
		MenuActivity.direction2=direction2;
		MenuActivity.speaker = speaker;
		MenuActivity.talk = talk;
		MenuActivity.resId = resId;
		
		myHandler.sendEmptyMessage(World.DIALOG);
		
	}
	
	public static void showDialog(String speaker, String talk, TexIdAndBitMap resId) {
		showDialog(speaker, talk, resId, Gravity.TOP,Gravity.CENTER_HORIZONTAL);
	}
	public void showGuideDialog(){
		final Dialog d=new Dialog(this,R.style.toumingDialog);
		View v = getLayoutInflater().inflate(R.layout.osguidelayout, null);
		v.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				d.cancel();
				return false;
			}
		});
		ViewGroup.LayoutParams layoutParams = new  ViewGroup.LayoutParams(screenWidth, screenHeight);
		d.setContentView(v,layoutParams);
		d.show();
	}
	
	public static void showDialogView() {	
		dl.getWindow().setGravity(direction1|direction2);
		TextView nameView = (TextView) dl.findViewById(R.id.speakername);
		TextView talkView = (TextView) dl.findViewById(R.id.dialogue);
		ImageView img=(ImageView) dl.findViewById(R.id.speaker);
		if(resId.textureId!=0)
			img.setImageBitmap(resId.bitmap);
		Log.i("nameView+speaker " + nameView + speaker, "talkView " + talkView);
		if (speaker == null)
			nameView.setText("神秘人");
		else
			nameView.setText(speaker);
		if (talk == null)
			talkView.setText("...");
		else
			talkView.setText(talk);

		try{
			dl.show();
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}

	private void showExitDialog() {
		showInAd();
		Builder qdg = new AlertDialog.Builder(this );
				// 不能用getApplicationContext() 结果同上
				// .setTitle(" ")
		View v=getLayoutInflater().inflate(R.layout.quitbuilder, null);
		qdg.setView(v);
		ViewGroup con=(ViewGroup) v.findViewById(R.id.quitcontainer);
		ad.showBanner(con);
		qdg.setMessage("确定退出游戏吗？");
				qdg.setPositiveButton("退出游戏",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface di, int i) {
								if (world != null) {
									world.onDestroy();
//									world = null;
									overridePendingTransition(R.anim.fadein,
											R.anim.fadeout);
									finish();
									System.exit(0);
								}
							}
					});
				qdg.setNegativeButton("继续游戏", null);
				qdg.show();
	}



	public void save() {
		editor.putInt("coin", coinCount);
		editor.putInt("chance", chance);
		
		editor.putInt("score", score);
		Client.send(ConsWhenConnecting.REQUEST_UPDATE_SCORE+userId+" "+score);

		starString = "";
		for (int i = 0; i < star.length; i++) {
			starString += star[i];
		}
		editor.putString("starString", starString);
		
		item=FruitSet.getItemArray();
		Log.i("itemString"+new String(item));
		itemString=new String(item);
		editor.putString("itemString",itemString);
		// Log.i("starString"+new String(starString));
		editor.commit();
	}

	private void getMessage() {
		// TODO Auto-generated method stub
		coinCount = sp.getInt("coin", 150);
		chance = sp.getInt("chance", 150);
		score = sp.getInt("score", 0);
		
		World.allMode=sp.getBoolean("allMode", false);
		World.developMode=sp.getBoolean("developMode", false);
		
		starString = sp.getString("starString", starString);
		if(starString.length()<Map.max){
			int d=Map.max-starString.length();
			StringBuilder sb=new StringBuilder(starString);
			for(int i=0;i<d;i++){
				sb.append("0");
			}
			starString=sb.toString();
		}
		Log.i("starString"+starString);
		itemString =sp .getString("itemString", itemString);
		star = stringToInts(starString);
		item=itemString.toCharArray();
		userName=sp.getString("userName", UserName.randomName());
		if(!sp.contains("userName")){
			editor.putString("userName", userName);
		}
		userId=sp.getInt("userId", NO_USER_ID);
	}
	void saveUserMessage(String sn){
		userName=sn;
		startMenu.setUserName(userName);
		Client.send(ConsWhenConnecting.REQUEST_UPDATE_NAME+userId+" "+userName);
		editor.putString("userName",userName);
		// Log.i("starString"+new String(starString));
		editor.commit();
	}

	int getMaxMapIndex() {
//		editor.putInt("mapIndex",18);//重置 管卡
		return maxMapId=sp.getInt("mapIndex", 1);
	}

	public void reLoadGame() {
//		quitGame();
		startGame();
		// sort about mapIndex++ or not
	}

	public void nextStage() {
		quitGame();
		if(world.gameMode==World.FILE_MODE&&
				gameFiles!=null//
				&&mapFileId<gameFiles.size()){
			loadFile(gameFiles.get(mapFileId++));
			return;
		}
		if (mapIndex<getMaxMapIndex() ) {
			mapIndex++;
			if(mapIndex>=Map.max+1){
				startWithIndex(0);//只有在下一关模式会进入终点
				return;
			}
			startGame();
		}

		// =to more 1 than max stageIndex to got last stage

	}

	public void startWithIndex(int index) {
		quitGame();
		mapIndex = index;
		if(mapIndex>Map.max)mapIndex=mapIndex%Map.max;
		World.CAN_GO_NEXT_STAGE=true;
		world.gameMode=World.MAP_ID_MODE;
		world.mapFile=null;world.mapString=null;world.mapCharSet=null;
		try {
			AssetManager am=this.getAssets();
			String zipFile="w"+mapIndex+".zip";//关卡文件位置
			InputStream is= null;
			is = am.open(zipFile);
			this.changeSkin(is);

		} catch (Exception e) {
			Log.i("没找到zip文件");
			startGame();
			e.printStackTrace();
		}
	}

	public void quitGame() {
		// TODO Auto-generated method stub
//		if(stateWindow!=null)stateWindow.hide();
		if(btnc!=null)btnc.hide();
		if (world != null) {
			// world.onDestroy();
			// world = null;
			world.quitGame();
		}
		Log.d("quitGame+gameSaved");
		save();
		
	}

	void loadTitleView() {
		// TODO Auto-generated method stub
		quitGame();
		titleMode=true;
		world.gameMode=World.MAP_ID_MODE;
		world.loadTitleView();
	}

	public void resumeGame() {
		// TODO Auto-generated method stub
		if(gameMenu!=null)	gameMenu.returnCurStateAndHide();
		if (world != null)
			world.resume();
//		gameMenu.
	}
	protected void onResume(){
		super.onResume();
		if(startMenu!=null||stageChooser!=null)
		 resumeGame();
	}

	public void onPause() {
		super.onPause();
		pauseGame();
		if(startMenu==null&&stageChooser==null)
			if(gameMenu!=null)gameMenu.showWindow(world);
	}
	void pauseGame() {
		// TODO Auto-generated method stub
		if (world != null) {
			world.pauseGame();
		}
	}

	void showShop(View v) {
		if (world != null)
			world.pauseDraw();

		shop.showWindow(v);
	}

	private View getContentView111() {
		ViewGroup view = (ViewGroup) getWindow().getDecorView();
		// content = view.getChildAt(0);
		// return content.getChildAt(0);
		return view;
	}

	public void showAppWall() {
		// TODO Auto-generated method stub
		ad.showAppWallAd();
	}

	public void initStageChooser() {
		// TODO Auto-generated method stub

		if (startMenu != null)
			startMenu.hide();
		startMenu=null;
		// if(stageChooser==null)
		stageChooser = new Stagechoosser(this);
	
	}

	public void setStar(int sum) {
		// TODO Auto-generated method stub
		if(mapIndex>Map.max)return;//最大限制
		int id = this.mapIndex - 1;
		if (star[id] < sum) {
			// starString.r
			// starString.indexOf(mapIndex)= sum;
			star[id] = sum;
		}
		String ss = "";
		for (int i = 0; i < star.length; i++) {
			ss += star[i];
		}
		Log.i("Star" + ss);
	}

	public int[] stringToInts(String s) {
		int[] n = new int[s.length()];
		for (int i = 0; i < s.length(); i++) {
			n[i] = Integer.parseInt(s.substring(i, i + 1));
		}
		return n;
	}


	void addView(View child) {
		// TODO Auto-generated method stub
//		group.addView(child);
		this.addContentView(child, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

	public void removeView(View view) {
		// TODO Auto-generated method stub
		if (view != null && view.getParent() != null) {
			((ViewGroup) view.getParent()).removeView(view);
//			((ViewGroup) view.getParent()).removeViewAt(index);
//			((ViewGroup) view.getParent()).removeAllViews();
		}
//		addView(view);
	}

	public void showBanner(ViewGroup shopadcontainer ) {
		// TODO Auto-generated method stub
		shopadcontainer.removeAllViews();
		removeView(ad.getBannerview());
		ad.showBanner(shopadcontainer);
		Log.i("show banner");
	}

	ArrayList<String> gameFiles;
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		battleActi=null;
		Log.i("onActivityResult");
		if(dl!=null)dl.dismiss();
		switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
		   case RESULT_OK:
		   	gameFiles = data.getStringArrayListExtra("mapFiles");
		     mapFileId=data.getIntExtra("mapfileId",0);//str即为回传的值
			   world.gameMode=World.FILE_MODE;
			   loadFile(gameFiles.get(mapFileId));
			   String onlineFileSelected=data.getStringExtra(OnlineFileActivity.ONLINE_STAGE_ITEM_SELECTED);
		    if(onlineFileSelected!=null&&onlineFileSelected!=""){
				World.CAN_GO_NEXT_STAGE=false;
		    	Client.send(ConsWhenConnecting.REQUEST_THIS_ONE_ONLINE_STAGE+onlineFileSelected);
		    	selectedToSaveOnlineFileName=onlineFileSelected;
		    }
		 }
	}

	protected void loadFile(String mapfile) {
		if(mapfile!=null&&mapfile!=""){
            if(mapfile.endsWith(".txt")){
                Log.i("mapfile"+mapfile);
                startGame(new File(mapfile));
            }else if(mapfile.endsWith(".zip")){
                changeSkin(mapfile);
            };
        }
	}

	void changeSkin(final Object mapfile) {
		final Creature c=new Creature(world.gra){
			boolean ziped=false;
			public void drawElement(GL10 gl){
				if(ziped)return;
				File parent=new File( Environment.getExternalStorageDirectory().getAbsoluteFile(), FileActivity.TOMS_MAP_NAME);
				File temp =new File(parent ,FileActivity.TEMP_NAME);
				if(temp.exists())deleteAllFile(temp);
				SkinLoader skinLoader = new SkinLoader();
				File f=null;
				if(mapfile instanceof  String){
					 f=skinLoader.loadSkin(new File((String) mapfile),temp,world.texId);
				}else if(mapfile instanceof InputStream){
					f=skinLoader.loadSkin((InputStream) mapfile,temp,world.texId);

				}
				setPosition(world.player.x, world.player.y);// can be draw
				die();
				ziped=true;
				if(f!=null){
					startGame(f);//如果 发现地图文件  没有结束游戏 不知是否会报错
					World.CAN_GO_NEXT_STAGE=true;
				}

			}
			private void deleteAllFile(File temp) {
				// TODO Auto-generated method stub
				File  f=temp;
				if(f.isDirectory()) {
					File[] fs = f.listFiles();
					for(File f1:fs){
						deleteAllFile(f1);
					}
				} else
					f.delete();
			}
		};
		world.addDrawAnimation(c);
	}

	public boolean getLifeFree() {
		// TODO Auto-generated method stub
		if(isNetworkAvailable(this)){
			showInAd();
			return true;
		}else {
			showDialog("网络错误", "请在联网状态下观看广告复活", TexId.COIN);
		}
		if(World.rpgMode){
			btnc.freshItem();
			MenuActivity.showDialog("", "复活蛋已发放", TexId.EGG);
			FruitSet.pickedList.add(FruitSet.shopList.get(0));
		}
		return false;
	}
	private void showInAd() {
		// TODO Auto-generated method stub
		ad.showInterstitial();
	}

	public static boolean isNetworkAvailable(Context context) {   
        ConnectivityManager cm = (ConnectivityManager) context   
                .getSystemService(Context.CONNECTIVITY_SERVICE);   
        if (cm == null) {   
        } else {
	//如果仅仅是用来判断网络连接
	 //则可以使用 cm.getActiveNetworkInfo().isAvailable();  
            NetworkInfo[] info = cm.getAllNetworkInfo();   
            if (info != null) {   
                for (int i = 0; i < info.length; i++) {   
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {   
                        return true;   
                    }
                }
            }
        }
		return false;
	}




	public void adShow() {
		// TODO Auto-generated method stub
		int count=1;
		increaseCoin(count);
		increaseChance(count);
	}
	 void increaseChance(int count) {
		chance+=count;
//		showDialog("显示了广告", "金币+"+count+" 生命能量+"+count, R.drawable.coinicon);
		myHandler.sendEmptyMessage(World.CHANCE);
	}
	 void increaseCoin(int count) {
		coinCount+=count;
		myHandler.sendEmptyMessage(World.COIN);
	}

	public void adClicked() {
		// TODO Auto-generated method stub
		int count=10;
		coinCount+=count;
		chance+=count;
//		showDialog("点击了广告", "金币+"+count+" 生命能量+"+count, R.drawable.egg);
		myHandler.sendEmptyMessage(World.COIN);
		myHandler.sendEmptyMessage(World.CHANCE);
	}

	public void saveUserId(String s) {
		// TODO Auto-generated method stub
		userId=Integer.parseInt(s);
		editor.putInt("userId", userId);
	}

	public void showBuyLifeShop(View v) {
		// TODO Auto-generated method stub
		showShop(v);

		shop.toBuyLife();
	}




//	void 

	public void sendOnlineStageRequest(){
		client.send(ConsWhenConnecting.REQUEST_ONLINE_STAGE);
		showDialog("提示", "已发送请求",TexId.FOG);
	}
	public void showOnlineStage(String gotonString) {
		Intent intent = new Intent();
		intent.putExtra(OnlineFileActivity.ONLINE_STAGE_STRING_FROM_NET, gotonString);
		
		intent.setClass(this, onlineStageActivity.OnlineFileActivity.class);
		this.startActivityForResult(intent, 0);
	}

	public void getTheOnlineStage(String ss) {
		// TODO Auto-generated method stub
		if(selectedToSaveOnlineFileName.equals(requestFoItemMap)){
			if(randomMap==null)randomMap=new RandomMap(world);
			
			Map map=new Map(ss);
			
			if(World.Item3Mode)randomMap.set3Item(map);
			else randomMap.setWholeItem(map);
			showDialog("地图更新成功", "随机地图更新成功", TexId.CAP);
			return;
		}
		world.saveMap(selectedToSaveOnlineFileName,ss);
		startGame(ss);
		World.CAN_GO_NEXT_STAGE=false;
	}
	
	void requestFoItemMap(){
		Client.send(ConsWhenConnecting.REQUEST_THIS_ONE_ONLINE_STAGE+requestFoItemMap);
    	selectedToSaveOnlineFileName=requestFoItemMap;
	}
	void startGame(File file) {
		// TODO Auto-generated method stub
		 world.mapFile=file;
		 world.mapString=null;
		 world.mapCharSet=null;
		world.gameMode=World.FILE_MODE;
		 myHandler.sendEmptyMessage(World.REQUEST_TO_STARTGAME);
	}
	private void startGame(String ss) {
		// TODO Auto-generated method stub
		world.mapString=ss;
		world.mapFile=null;
		 world.mapCharSet=null;
//		Log.i(ss);
		world.gameMode=World.STRING_MODE;
		myHandler.sendEmptyMessage(World.REQUEST_TO_STARTGAME);
	}
	public void randomChalenge() {
		// TODO Auto-generated method stub
		if(randomMap==null){
			randomMap=new RandomMap(world);
			Map map=new Map(-333, this);
			world.gameMode=World.RANDOM_MODE;
			if(World.Item3Mode)randomMap.set3Item(map);
			else randomMap.setWholeItem(map);
		}
	
		
		char []cs;
		if(World.Item3Mode){
			cs=randomMap.getRandom333Map(16,1);
		}
		else {
			cs=randomMap.getRandomWholeMap(16,1);
		}
		world.mapCharSet=cs;
		world.mapString=null;
		world.mapFile=null;
		myHandler.sendEmptyMessage(World.REQUEST_TO_STARTGAME);
	}
	public void battleAction(String ss) {
		// TODO Auto-generated method stub
		// id x y angle gunid actionid fdirection
		String[] strSet = ss.split(" ");
		world.battleAction(strSet);
	}
	public void roomSetMessage(String ss) {
		// TODO Auto-generated method stub
		if(battleActi!=null)battleActi.roomSetMessage(ss);
	}
	public void roomOneInfo(String ss) {
		// TODO Auto-generated method stub
		if(battleActi!=null)battleActi.roomOneInfo(ss);
	}
	public void intentToFileChooser() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(this, fileRW.FileActivity.class);
		this.startActivityForResult(intent, 0);
	}
	public void intentToBattleMode() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(this, BattleActivity.class);
		this.startActivityForResult(intent, 111);
	}
	public void addForce(int force, int userId) {
		// TODO Auto-generated method stub
		world.addForce(force,userId);
	}
	public void useItemBattleMan(String strRes) {
		// TODO Auto-generated method stub
		world.useItemBattleMan(strRes);
	}
	public void thisOneDie(String strRes) {
		// TODO Auto-generated method stub
		world.thisOneDie(strRes);
	}
}
