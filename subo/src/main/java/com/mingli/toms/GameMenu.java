package com.mingli.toms;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;

import Enviroments.Fruit;
import Mankind.Player;
import aid.Log;

/**
 * Created by Administrator on 2016/7/10.
 */

public class GameMenu {
	PopupWindow popupWindow;
	MenuActivity acti;
	private PopWindowTouchEvent touchEvent;
	private RatingBar summarize;
	private World world;

	View rateGrid;
	View resume, next;
//	private boolean starShowable;
	private int sum;
	private TextView gameTitle;
	private ViewGroup gameMenuAdContainer;
	private LayoutParams lp;
	Player player;
	private TextView instruction;
	private View getLifeFree;
	private View buyLife;
	private boolean freeRelifed;
	
	GameMenu(MenuActivity acti, World world) {
		this.acti = acti;
		this.world = world;
		this.touchEvent = new PopWindowTouchEvent((MenuActivity) acti);
		player=world.player;
	}

	public void showWindow(View v) {
		long tim = System.currentTimeMillis();
		if (popupWindow != null && popupWindow.isShowing())
			return;// 防止重新出发
		
		// 获取自定义布局文件activity_popupwindow_left.xml的视图
		else if (popupWindow == null) {
			View popupWindow_view = acti.getLayoutInflater().inflate(
					R.layout.gamemenu, null);
			// 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
			popupWindow = new PopupWindow(popupWindow_view,
					WindowManager.LayoutParams.MATCH_PARENT,
					WindowManager.LayoutParams.MATCH_PARENT, true);
			popupWindow.setAnimationStyle(R.style.AnimationFade);

			gameMenuAdContainer=(ViewGroup) popupWindow_view.findViewById(R.id.gamemenuadcontainer);
			
			touchEvent.findView(popupWindow_view);// 寻找自定义事件的
			findView(popupWindow_view);
			rateGrid=popupWindow_view.findViewById(R.id.ratinggrid);
			rateGrid.setVisibility(View.INVISIBLE);
			lp=rateGrid.getLayoutParams();
			lp.height=30;
			
			View.OnTouchListener otl1 = new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					touchEvent.touchAction(v, event);
					returnCurStateAndHide();
					return false;
				}
			};
			popupWindow_view.setOnKeyListener(new OnKeyListener() {
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					if (keyCode == KeyEvent.KEYCODE_BACK) {
						world.resume();
					}
					return true;
				}
			});
//			popupWindow_view.setOnTouchListener(otl);
			/////////////
		}
		// 这里是位置显示方式,在屏幕的左侧
		
		
		acti.showBanner(gameMenuAdContainer);
		popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
//		popupWindow.update();
		
		
		if(!player.isDead&&player.goal.hasFirstBlood)normalMenu();
		
		Log.i("popTime", "" + (System.currentTimeMillis() - tim));
		
//		showStar();
	}

	private void findView(View v) {
		// TODO Auto-generated method stub
		summarize = (RatingBar) v.findViewById(R.id.ratingBar4);
		
		gameTitle=(TextView)v.findViewById(R.id.gameMenuTitle);
		
		gameTitle.setText("第"+acti.mapIndex+"关");
		
		buyLife=v.findViewById(R.id.buyLife);
		getLifeFree=v.findViewById(R.id.getLifeFree);

	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	void initBuyLifeView(View v, Fruit f) {

		TextView cost = (TextView) v.findViewById(R.id.goodscost);
		TextView chancecost = (TextView) v.findViewById(R.id.chancecost);
		View goodsLayout = v.findViewById(R.id.goodscostlayout);
		View chanceLayout=v.findViewById(R.id.chancecostlayout);

		if(f.cost>0) {
			cost.setText("" + f.cost);
			goodsLayout.setVisibility(View.VISIBLE);
		} else {
			goodsLayout.setVisibility(View.GONE);
		}

		if (f.chancecost > 0) {
			chanceLayout.setVisibility(View.VISIBLE);
			chancecost.setText("" + f.chancecost);
		} else {
			chanceLayout.setVisibility(View.GONE);
		}
	}

	void showStar() {
//		if(!starShowable)return;
		lp.height=LayoutParams.WRAP_CONTENT;
		
		Log.i("LayoutParams.WRAP_CONTENT"+LayoutParams.WRAP_CONTENT);
		rateGrid.setVisibility(View.VISIBLE);

		
		sum=(world.getSpeedStar() + world.getCoinStar() + world
				.getItemStar()) / 2;
		acti.setStar(sum);
		summarize.setProgress(sum);
		summarize.setVisibility(View.INVISIBLE);
		initSummaryAnimation();
	}

	void initSummaryAnimation(){
		summarize.setVisibility(View.VISIBLE);
		Animation b=new ScaleAnimation(0, 1, 1,1);
		b.setDuration(500);
		summarize.setAnimation(b);
		b.start();
	}

	boolean returnCurStateAndHide() {
		if (popupWindow != null && popupWindow.isShowing()) {
			popupWindow.dismiss();
			return false;
		}
		return true;
	}

	class PopWindowTouchEvent {

		private MenuActivity menuActivity;
		CheckBox ex, music;
		private View randomMap;

		PopWindowTouchEvent(MenuActivity acti) {
			this.menuActivity = acti;

		}

		void findView(View view) {
			view.findViewById(R.id.tomenu).setOnTouchListener(otl);
			(resume = view.findViewById(R.id.resume)).setOnTouchListener(otl);
			view.findViewById(R.id.retry).setOnTouchListener(otl);
			(randomMap=view.findViewById(R.id.randommap)).setOnTouchListener(otl);
			if(world.mapCharSet==null)randomMap.setVisibility(View.GONE);
			(next = view.findViewById(R.id.next)).setOnTouchListener(otl);
			 next.setVisibility(View.INVISIBLE);
			ex = (CheckBox) view.findViewById(R.id.ex);
			ex.setOnTouchListener(otl);
			music = (CheckBox) view.findViewById(R.id.music);
			music.setOnTouchListener(otl);
			
			
			Button save = (Button) view.findViewById(R.id.savemap);
			save.setOnTouchListener(otl);
			View buyLife =  view.findViewById(R.id.buyLife);
			buyLife.setOnTouchListener(otl);
			
			
			View backtostagechoosser = view.findViewById(R.id.backtostagechoosser);
			if(acti.maxMapId==1)backtostagechoosser.setVisibility(View.GONE);
				backtostagechoosser.setOnTouchListener(otl);
			(view.findViewById(R.id.getLifeFree)).setOnTouchListener(otl);
			View chosef;
			View chosonline;
			(chosef=view.findViewById(R.id.choseFileGameMenu)).setOnTouchListener(otl);
			(chosonline=view.findViewById(R.id.choseNetstageGameMenu)).setOnTouchListener(otl);
			if(!World.allMode){
				chosef.setVisibility(View.GONE);
				chosonline.setVisibility(View.GONE);
			}
			if(!World.editMode&&world.mapCharSet==null){
				save.setVisibility(View.GONE);
				
			}
		}

		;

		private View.OnTouchListener otl = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				touchAction(v, event);
				return false;
			}
		};

		public void touchAction(View v, MotionEvent e) {
			// this.v = v;
			// this.e = e;
			if (e.getAction() == MotionEvent.ACTION_DOWN
					|| e.getAction() == MotionEvent.ACTION_POINTER_DOWN) {
				switch (v.getId()) {
				case R.id.tomenu:
					returnCurStateAndHide();
					menuActivity.loadTitleView();
					// viewList.get(0).setBackgroundResource(R.drawable.tomenu);
					break;
				case R.id.resume:
					// viewList.get(1).setBackgroundResource(R.drawable.back);
					menuActivity.resumeGame();
					break;
				case R.id.randommap:
					// viewList.get(1).setBackgroundResource(R.drawable.back);
					menuActivity.randomChalenge();
					break;
				case R.id.backtostagechoosser:
					// viewList.get(1).setBackgroundResource(R.drawable.back);
					menuActivity.quitGame();
					menuActivity.initStageChooser();
					break;
				case R.id.choseFileGameMenu:
//					menuActivity.quitGame();
					menuActivity.intentToFileChooser();
					return;
				case R.id.choseNetstageGameMenu:
//					menuActivity.quitGame();
					menuActivity.sendOnlineStageRequest();
					return;
				case R.id.getLifeFree:
					// viewList.get(1).setBackgroundResource(R.drawable.back);
					
					if(menuActivity.getLifeFree()){
						player.reLife(180);
						getLifeFree.setVisibility(View.GONE);
						freeRelifed=true;
					}
					break;
				case R.id.savemap:
					Log.i("saveClicked");
					world.saveMap();
					break;
				case R.id.buyLife:
//					acti.showShop(world);
					acti.showBuyLifeShop(world);
					break;
				case R.id.retry:
					// viewList.get(2).setBackgroundResource(R.drawable.retry);
					menuActivity.reLoadGame();
					break;
				case R.id.next:
					// viewList.get(3).setBackgroundResource(R.drawable.next);
					menuActivity.nextStage();
					break;
				case R.id.ex:
					if (v == ex) {
						if (ex.isChecked())
							World.music.noEx();
						else
							World.music.hasEx();
					}
					break;
				case R.id.music:
					if (v == music) {
						if (music.isChecked())
							World.music.noBgm();
						else
							World.music.hasBgm();
					}
					break;
				default:return;
				}
				boolean h = returnCurStateAndHide();
				if(!h)acti.resumeGame();
			}
		}
	}

	public void normalMenu() {
		// TODO Auto-generated method stub
//		starShowable=false;
		showWindow(world);
		resume.setVisibility(View.VISIBLE);
		getLifeFree.setVisibility(View.GONE);
		buyLife.setVisibility(View.GONE);
		gameTitle.setText(world.mapName);
		lp.height=0;
	}
	
	public void gameover() {
		// TODO Auto-generated method stub
//		starShowable=false;
		showWindow(world);
		if(world.gameTime==0)gameTitle.setText("时间结束！");
		else gameTitle.setText("体力耗尽！");
		resume.setVisibility(View.INVISIBLE);
		next.setVisibility(View.INVISIBLE);
		
		if(!freeRelifed){
			getLifeFree.setVisibility(View.VISIBLE);
		}
		buyLife.setVisibility(View.VISIBLE);
		acti.initBuyLife(this);

		lp.height=0;
	}

	public void succeed() {
		// TODO Auto-generated method stub
		
//		starShowable=true;
		showWindow(world);
		gameTitle.setText("成功过关！");
		resume.setVisibility(View.INVISIBLE);
		if(World.CAN_GO_NEXT_STAGE)
			next.setVisibility(View.VISIBLE);
		
		getLifeFree.setVisibility(View.GONE);
		buyLife.setVisibility(View.GONE);
		showStar();
	}

}