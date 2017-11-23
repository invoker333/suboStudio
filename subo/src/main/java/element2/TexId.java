package element2;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.opengl.GLUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

import Module.TexIdAndBitMap;
import aid.Log;
import aid.SkinLoader;

public class TexId{
	public TexId(Context context){
		this.context = context;
		textures = new int[TexId.textureCount];//
//		resIcon = new int[TexId.textureCount];//
		texAndBits = new TexIdAndBitMap[TexId.textureCount];//
	}
	public int[] textures;
	public TexIdAndBitMap[] texAndBits;
//	private int maxId=0;// add auto in start
	private GL10 loadGl;
//	public static int[] resIcon;
static Bitmap emptyBitmap = Bitmap.createBitmap(1, 1,Bitmap.Config.ARGB_8888);

	public static  TexIdAndBitMap ZERO=new TexIdAndBitMap(emptyBitmap,0);
	public static  TexIdAndBitMap BAMBOOPIPLE=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap PIFENG=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap STONEGRASS=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap PRICKX=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap EGG=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap BULLET=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap BACK=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap MORNING=new TexIdAndBitMap(emptyBitmap,0);
//	public static TexIdAndBitMap BUBBLE=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap RED=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap CLOCK=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap COIN=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap COINICON=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap WALKER=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap GREENWALKER=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap CREEPER=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap REDCREEPER=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap HEDGEHOG=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap WATERBALL=new TexIdAndBitMap(emptyBitmap,0);
//	public static TexIdAndBitMap NEXT=new TexIdAndBitMap(emptyBitmap,0);
//	public static TexIdAndBitMap NUMBER=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap TOMENU=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap FIREBALL=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap ZHUAN=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap BANK=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap RESULT=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap SWORD=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap PAUSE=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap FEIBIAO=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap BLANK=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap GOAL=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap EVENING=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap SUNSET=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap TIANSHAN=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap STONE=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap WOOD=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap TREE=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap H=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap SOIL=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap SOILGRASS=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap NUMBERSMALL=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap FOG=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap TOMATO=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap JINGUBANG=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap BLUE=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap GREEN=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap DESERT=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap FLYER=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap BALLER=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap BODY=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap HAND=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap FOOT=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap CLOTH=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap CAP=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap PRICK=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap BAMBOO=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap BAMBOOHEART=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap TOUKUI=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap GAO=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap HIKARI=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap EXPRESSION=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap SNOW=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap WIND=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap FIREWORK=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap THUNDER=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap BOOM=new TexIdAndBitMap(emptyBitmap,0);
    public static TexIdAndBitMap BOOMWHITE=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap BLACK=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap RAINBOW=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap LIGHTNING=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap LIGHTSPOT=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap CANDLETAIL=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap GUN=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap BLOOD=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap FIRE=new TexIdAndBitMap(emptyBitmap,0);
//	public static TexIdAndBitMap FIRE2=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap CANDLEPOR=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap PAOTA=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap GUIDEPOST=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap GUIDECIRCLE=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap SHOTGUN=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap JUJI=new TexIdAndBitMap(emptyBitmap,0);//musket hua tang qiang
	public static TexIdAndBitMap MISSILE=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap HOOKGUN=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap CUP=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap LIGHTTAIL=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap K=new TexIdAndBitMap(emptyBitmap,0);//knife
	public static TexIdAndBitMap GUANGDANQIANG=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap WOODROOT=new TexIdAndBitMap(emptyBitmap,0);
	
	
	public static TexIdAndBitMap GOALCIRCLE=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap GUIDERECT=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap FRUITFLY=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap ZIDONGDAN=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap SHUFUDAN=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap ZAN=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap BOOMGUN=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap QIGAN=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap WIPE=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap STICKER=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap CLOTHENEMY=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap CAPENEMY=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap FLAG=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap EXPRESSIONENEMY=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap PUTONGQIANG=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap FENSHEN=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap DIALOGTRINGLE=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap CAPJIANCI=new TexIdAndBitMap(emptyBitmap,0);
	public static TexIdAndBitMap BAG=new TexIdAndBitMap(emptyBitmap,0);

	public static int textureCount=200;//纹理数量 行号之差
	
	public  void loadTextureId(GL10 gl) {
		this.loadGl=gl;
		try {
			AssetManager am=context.getAssets();
			SkinLoader skLoader = new SkinLoader();
//			String zipFile="drawable.zip";//素材名字
//			InputStream is= am.open(zipFile);
//			File parent=new File( Environment.getExternalStorageDirectory().getAbsoluteFile(), FileActivity.TOMS_MAP_NAME);
//			File temp =new File(parent ,FileActivity.TEMP_NAME);
//			skLoader.loadSkin(is,temp,this);


			String dirName="drawable";
			String[] fileNames = am.list(dirName);
			for(int i=0;i<fileNames.length;i++){
				Log.i("assertsDrawableFileName"+fileNames[i]);
				skLoader.loadSingleFile(this,am.open(dirName+"/"+fileNames[i]),fileNames[i]);
			}

		} catch (Exception e) {
			Log.i("没找到zip文件");
			e.printStackTrace();
		}


//		if(1<2)return;
//		DIALOGTRINGLE=loadTexture(gl,R.drawable.dialogtringle);
//		FENSHEN=loadTexture(gl,R.drawable.fenshenguo);
//		PUTONGQIANG=loadTexture(gl,R.drawable.putongdan);
//		EXPRESSIONENEMY=loadTexture(gl,R.drawable.espressionenemy);
//		FLAG=loadTexture(gl,R.drawable.flag);
//		CAPENEMY=loadTexture(gl,R.drawable.toukuienemy);
//		CLOTHENEMY=loadTexture(gl,R.drawable.clothenemy);
//		STICKER=loadTexture(gl,R.drawable.sticker);
//		BAMBOOPIPLE=loadTexture(gl,R.drawable.bamboopiple);
//		WIPE=loadTexture(gl,R.drawable.wipe);
//		QIGAN=loadTexture(gl,R.drawable.qigan);
//		BOOMGUN=loadTexture(gl,R.drawable.boomgun);
//		SHUFUDAN=loadTexture(gl,R.drawable.shufudan);
//		ZIDONGDAN=loadTexture(gl,R.drawable.autobulletgun);
//		PIFENG=loadTexture(gl,R.drawable.pifeng);
//		FRUITFLY=loadTexture(gl,R.drawable.fruitfly);
//		STONEGRASS=loadTexture(gl,R.drawable.stonegrass);
//		GUIDERECT=loadTexture(gl,R.drawable.guiderect);
//		GOALCIRCLE=loadTexture(gl,R.drawable.goalcircle);
//		WOODROOT=loadTexture(gl,R.drawable.woodroot);
//		GUANGDANQIANG=repeatLoadTexture(gl,R.drawable.guangdan);
//		K=repeatLoadTexture(gl,R.drawable.jian);
//		GUIDECIRCLE=loadTexture(gl,R.drawable.guidecircle);
//		EGG=loadTexture(gl,R.drawable.egg);
//		LIGHTTAIL=loadTexture(gl,R.drawable.lighttail);
//		CUP=repeatLoadTexture(gl,R.drawable.cup);
//		HOOKGUN=repeatLoadTexture(gl,R.drawable.huck);
//		JUJI=repeatLoadTexture(gl,R.drawable.jujidan);
//		MISSILE=repeatLoadTexture(gl,R.drawable.daodan);
//		SHOTGUN=repeatLoadTexture(gl,R.drawable.s);
//		BULLET=loadTexture(gl,R.drawable.bullet);
//		GUIDEPOST=loadTexture(gl,R.drawable.guidepost2);
//		CANDLEPOR=loadTexture(gl,R.drawable.candlepor);
//		FIRE=loadTexture(gl,R.drawable.firetail);
//		PAOTA=loadTexture(gl,R.drawable.paotai);
//		GUN=loadTexture(gl,R.drawable.guntitle);
//		BLOOD=loadTexture(gl,R.drawable.blood);
//		CANDLETAIL=loadTexture(gl,R.drawable.candle);
//		LIGHTSPOT=loadTexture(gl,R.drawable.lightspot);
//		LIGHTNING=loadTexture(gl,R.drawable.lightning);
//		RAINBOW=loadTexture(gl,R.drawable.tail);
//		BLACK=loadTexture(gl,R.drawable.black);
//		BOOM=loadTexture(gl,R.drawable.boom2);
//		THUNDER =loadTexture(gl,R.drawable.lightningset);
//		FIREWORK=loadTexture(gl,R.drawable.firework);
//		SNOW=loadTexture(gl,R.drawable.snow);
//		WIND=loadTexture(gl,R.drawable.wind);
//		EXPRESSION=loadTexture(gl,R.drawable.espression);
//		ZAN=loadTexture(gl,R.drawable.zan);
//		HIKARI=loadTexture(gl,R.drawable.light);
//		TOUKUI=loadTexture(gl,R.drawable.toukui);
//		GAO=loadTexture(gl,R.drawable.gao);
//		BAMBOO=loadTexture(gl,R.drawable.bamboo);
//
//		BAMBOOHEART=loadTexture(gl,R.drawable.bambooheart);
//		BODY=loadTexture(gl,R.drawable.body);
//		HAND=loadTexture(gl,R.drawable.hand);
//		FOOT=loadTexture(gl,R.drawable.foot);
//		CLOTH=loadTexture(gl,R.drawable.cloth);
//		CAP=loadTexture(gl,R.drawable.cap);
//		BACK=loadTexture(gl,R.drawable.back);
//		MORNING=loadTexture(gl,R.drawable.background);
//
//		CLOCK=loadTexture(gl,R.drawable.clock);
//		WALKER=loadTexture(gl,R.drawable.walker);
//		GREENWALKER=loadTexture(gl,R.drawable.greenwalker);
//		CREEPER=loadTexture(gl,R.drawable.creeper);
//		REDCREEPER=loadTexture(gl,R.drawable.bluecreeper);
//		HEDGEHOG=loadTexture(gl,R.drawable.hedgehog);
//		FLYER=loadTexture(gl,R.drawable.flyer);
//		BALLER=loadTexture(gl,R.drawable.enemy1);
//		WATERBALL=loadTexture(gl,R.drawable.fball);
//		NEXT=loadTexture(gl,R.drawable.next);
////		NUMBER=loadTexture(gl,R.drawable.number);
//
//		COIN=repeatLoadTexture(gl,R.drawable.coin);
//		TOMENU=loadTexture(gl,R.drawable.tomenu);
//		FIREBALL=loadTexture(gl,R.drawable.fireball);
//		ZHUAN=loadTexture(gl,R.drawable.zhuan);
//		BANK=loadTexture(gl,R.drawable.bank);
//		BLANK=loadTexture(gl,R.drawable.blank);
////		RESULT=loadTexture(gl, R.drawable.result);
//		SWORD=loadTexture(gl, R.drawable.sword);
//		PAUSE=loadTexture(gl, R.drawable.pause);
//		ATTACK=loadTexture(gl, R.drawable.button_attack);
////		GOAL=loadTexture(gl, R.drawable.goal);
//
//		STONE=loadTexture(gl, R.drawable.stone);
//		H=loadTexture(gl, R.drawable.height);
//		WOOD=loadTexture(gl, R.drawable.wood);
//		TREE=loadTexture(gl, R.drawable.tree);
//		SOIL=loadTexture(gl, R.drawable.soil);
//		SOILGRASS=loadTexture(gl, R.drawable.soilgrass);
//		FOG=loadTexture(gl, R.drawable.fog);
//		TOMATO=loadTexture(gl, R.drawable.tomato);
//		JINGUBANG=loadTexture(gl, R.drawable.jingubang);
//		RED=loadTexture(gl,R.drawable.red);
//		BLUE=loadTexture(gl, R.drawable.blue);
//		GREEN=loadTexture(gl, R.drawable.green);
//		PRICK=loadTexture(gl, R.drawable.spine);
//		PRICKX=loadTexture(gl, R.drawable.spinex);
		
		
		WORD = loadBmpTexture(gl, initFontBitmap(10, 32, "Hello World"));
		
//		DESERT=repeatLoadTexture(gl, R.drawable.zhijindong);
//		EVENING=repeatLoadTexture(gl, R.drawable.huangshan2);
//		SUNSET=repeatLoadTexture(gl, R.drawable.sunset);
//		TIANSHAN=repeatLoadTexture(gl, R.drawable.tianshan);
	}

	private Context context;
	TexIdAndBitMap WORD;
	private int maxId=1;
	
	
	
	public static Bitmap initFontBitmap(int pad,int textSize,String str){
		return initFontBitmap(pad, textSize, str.split(" "));
	}
	public static Bitmap initFontBitmap(int pad,int textSize,String []strs){
		int max=1;
		for(String s:strs){
			if(s.length()>max){
				max=s.length();
			}
		}
		 Bitmap bitmap = Bitmap.createBitmap(max*textSize+2*pad, strs.length*textSize+3*pad,
				 Bitmap.Config.ARGB_8888);// strs.length+1 because draw direction is up but tail is down...
		 Canvas canvas = new Canvas(bitmap);
		//背景颜色
//		canvas.drawColor(Color.LTGRAY);
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		RectF rect=new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
		canvas.drawRoundRect(rect, pad, pad, paint);
		//字体设置
		String fontType = "宋体";
		Typeface typeface = Typeface.create(fontType, Typeface.BOLD);
		//消除锯齿
		paint.setAntiAlias(true);
		//字体为红色
		paint.setColor(Color.BLACK);
		paint.setTypeface(typeface);
		paint.setTextSize(textSize);
		//绘制字体
//		canvas.drawText(font, 0, 100, paint);
		for(int i=0;i<strs.length;i++){
			canvas.drawText(strs[i], pad, pad+textSize*(i+1), paint);
		}
		return bitmap;
	}
	
	public  void reLoadFront( TexIdAndBitMap texId,String s) {
		reLoadFront(texId, s.split(" "));
	}
	public  void reLoadFront( TexIdAndBitMap texId,String []strs) {// ������Ĭ����������id��bitmap����һ��
		Log.i("TextureId ago "+texId);
		Bitmap bm = initFontBitmap(20, 36, strs);
		WORD=reloadBitMapTexture(loadGl, bm,WORD.textureId);//  overwrite
		Log.i("TextureId after "+WORD.textureId);
	}
	
	public int reloadTextureId(TexIdAndBitMap tex,Object obj){
		Bitmap bitmap=null;
		if(obj instanceof File){
			File f = (File) obj;
			Log.i("file:"+f.getAbsolutePath());
			 bitmap = getBitMapByFile(f);
		}else if(obj instanceof InputStream){
			bitmap = BitmapFactory.decodeStream((InputStream)obj);
		}

		if(tex.textureId==0) tex.textureId=maxId++;
//		tex.bitmap.recycle();//回收以前的bitmap
		tex.bitmap=bitmap;

		TexIdAndBitMap rbm= reloadBitMapTexture(loadGl, bitmap, tex.textureId);
		texAndBits[tex.textureId]=rbm;
		return rbm.textureId;



//		deleteTex(loadGl);/////////////////
//		Log.i("texDEleted");
		////////////
//		reLoadFront(texId,new String[]{"dasdas das dasdas dsa","dsadas"});
//		return WORD.textureId;

	}
	public  TexIdAndBitMap loadBmpTexture(GL10 gl, Bitmap bm) {// ������Ĭ����������id��bitmap����һ��
		maxId++;
		getIndexAndBindTextureId(gl);
		return reloadBitMapTexture(gl, bm,textures[maxId]);
	}

	 TexIdAndBitMap reloadBitMapTexture(GL10 gl, Bitmap newBitmap,int texId) {
		bindTextureId(gl,texId);
		repeatNo(gl);
		TexIdAndBitMap tb
		=new TexIdAndBitMap(newBitmap,texId);
		
		texImage2d( newBitmap);
		
		return tb;
	}
	public int loadTexture(GL10 gl, int resId) {// ������Ĭ����������id��bitmap����һ��
		maxId++;
		Bitmap bm = getBitMapByResId(resId);
		getIndexAndBindTextureId(gl);
		bindTextureId(gl,textures[maxId]);
		repeatNo(gl);
		texImage2d( bm);
		return textures[maxId];
	}
	public int repeatLoadTexture(GL10 gl, int resId) {// ������Ĭ����������id��bitmap����һ��
		maxId++;
		Bitmap bm = getBitMapByResId(resId);
		getIndexAndBindTextureId(gl);
		bindTextureId(gl,textures[maxId]);
		repeatYes(gl);
		texImage2d( bm);
		return textures[maxId];
	}
	private Bitmap getBitMapByFile(File f) {
//		BitmapFactory.Options options = new BitmapFactory.Options(); 
//		options.inPreferredConfig = Bitmap.Config.ARGB_4444;      
//		ALPHA_8：每个像素占用1byte内存
//		ARGB_4444:每个像素占用2byte内存
//		ARGB_8888:每个像素占用4byte内存
//		RGB_565:每个像素占用2byte内存
//		android默认的色彩模式为ARGB_8888，这个色彩模式色彩最细腻，显示质量最高。但同样的，占用的内存也最大。  
		
//		Bitmap img = BitmapFactory.decodeFile(f.getAbsolutePath(), options);  ////////////////////////////
//		resIcon[maxId] = resId;
		FileInputStream fis = null;
		Bitmap bitmap = null;
		try {
			fis = new FileInputStream(f);
			bitmap = BitmapFactory.decodeStream(fis);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return bitmap;
	}
	private Bitmap getBitMapByResId(int resId) {
//		resIcon[maxId] = resId;
		InputStream is = null;
		Bitmap bm;
		try {
			is = context.getResources().openRawResource(resId);
			bm = BitmapFactory.decodeStream(is);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bm;
	}
	private void texImage2d(Bitmap bm) {
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bm, 0);// ͼ--���� ָ������
//		emptyBitmap.recycle();//////////////回收bitmap
		Log.i("texturesId 【】"+textures[maxId]+" "+maxId);
	}
	private void repeatNo(GL10 gl) {
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
				GL10.GL_CLAMP_TO_EDGE);// another is GL_REPEAT
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
				GL10.GL_CLAMP_TO_EDGE);
	}
	
	private void repeatYes(GL10 gl) {
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
				GL10.GL_REPEAT);// GL_REPEAT
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
				GL10.GL_REPEAT);
	}
	private void getIndexAndBindTextureId(GL10 gl) {
		gl.glGenTextures(1, textures, maxId);//拿1st没用的索引 in 2 nd textures's  No.3 position
	}
	private void bindTextureId(GL10 gl,int texId) {
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);// 将一个命名的纹理(id)绑定到一个纹理目标（GL_TEXTURE_2D）上
		// ����������id��������������ڻ״̬���ڵ�һ�ΰ�һ���������ʱ,
		// �Ὣһϵ�г�ʼֵ����Ӧ���Ӧ�á�
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_NEAREST); // ���ù�С NEAREST�������
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_LINEAR); // �<a>��������˲�
	}

	public  void deleteTex(GL10 gl) {
		// TODO Auto-generated method stub
		for (int i = 0; i < textures.length; i++)
			gl.glDeleteTextures(1, textures, i);
	}
}


