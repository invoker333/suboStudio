package com.mingli.toms;

import android.app.Dialog;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Enviroments.FruitSet;
import aid.Client;
import aid.Log;
import aid.UserName;

/**
 * Created by Administrator on 2016/7/14.
 */
public class StartMenu{
    private MenuActivity acti;
	private RadioButton noweapon;

	private RadioButton alwaysgun;

	private CheckBox rampage;

	private View startView;
	private TextView username;
	private Button onlineStageChoose;
	private Button fileChoose;
	class PaimingAdapter extends BaseAdapter {

		ArrayList<Info4> userInfoList;
		public PaimingAdapter(ArrayList<Info4> userInfoList) {
			this.userInfoList = userInfoList;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return userInfoList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return userInfoList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView userName=null;
			TextView userScore=null;
			TextView userPaiming=null;
			View v=null;
			if(convertView==null){
				 v=acti.getLayoutInflater().inflate(R.layout.paimin, null);
			}else v=convertView;
			
			userName=(TextView) v.findViewById(R.id.paiming_name);
			userScore=(TextView) v.findViewById(R.id.paiming_score);
			userPaiming=(TextView) v.findViewById(R.id.mingci);
			Info4 info4 = userInfoList.get(position);
			if(info4.userId==acti.userId)v.setBackgroundResource(R.drawable.greenrect);
			else v.setBackgroundResource(0);
			userPaiming.setText(""+info4.paiming);
			userScore.setText(""+info4.userScore);
			userName.setText(info4.userName);
			
			return v;
		}
	};
    StartMenu(MenuActivity acti){
        this.acti = acti;
//        this.click = click;
//        this.check = check;
    }
	public void handleCheck(Message msg) {
		// TODO Auto-generated method stub
		switch(msg.what){
		case World.NOTIFY_PAIMING_CAHNGED:
            acti.findViewById(R.id.user).setVisibility(View.VISIBLE);
		    acti.findViewById(R.id.paiminglayout).setVisibility(View.VISIBLE);paimingAdapter.notifyDataSetChanged();
            break;
		}
	}
    public void loadStartMenu() {
       
        startView = acti.getLayoutInflater().inflate(
				R.layout.startmenu,null);
        
//        acti.addView(startView);
      
//        acti.setContentView(R.layout.startmenu);
        acti.addView(startView);
        initPaiming(startView);
        
//        Log.i("DecView"+pareView, "风格的萨广泛的萨广泛的撒");
        
//        Animation animation=AnimationUtils.loadAnimation(acti, R.anim.self);
//		pareView.startAnimation(animation);
//        
        
        Button start = (Button) startView.findViewById(R.id.startgame);
        Button more = (Button) startView.findViewById(R.id.more);
        Button stage = (Button) startView.findViewById(R.id.stageChoose);
        battleMode = (Button) startView.findViewById(R.id.battleMode);
        fileChoose = (Button) startView.findViewById(R.id.fileChoose);
       	onlineStageChoose = (Button) startView.findViewById(R.id.onlineFileChooser);
       	random = (Button) startView.findViewById(R.id.randomchalenge);
        
        rampage = (CheckBox) startView.findViewById(R.id.rampage);
        noweapon = (RadioButton) startView.findViewById(R.id.nowepon);
        alwaysgun = (RadioButton) startView.findViewById(R.id.alwaysgun);
        user = startView.findViewById(R.id.user);
        user.setOnClickListener(click);
		Spinner spinner = (Spinner) startView.findViewById(R.id.heroSelectSpinner);
		// 初始化控件
// 建立数据源
		String[] mItems ={"探险家","女巫","剑客","胖子"};
// 建立Adapter并且绑定数据源
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(acti,android.R.layout.simple_spinner_item, mItems);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//绑定 Adapter到控件
		spinner .setAdapter(adapter);
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
									   int pos, long id) {
				acti.heroSelected(pos);
//				String[] languages = getResources().getStringArray(R.array.languages);
//				Toast.makeText(MainActivity.this, "你点击的是:"+languages[pos], 2000).show();
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// Another interface callback
			}
		});
      	if(acti.maxMapId==1){
       		stage.setVisibility(View.GONE);
       		user.setVisibility(View.INVISIBLE);
       		acti.findViewById(R.id.paiminglayout).setVisibility(View.INVISIBLE);
       	}
       	if(acti.maxMapId<4){
       		more.setVisibility(View.INVISIBLE);
       		acti.findViewById(R.id.container).setVisibility(View.INVISIBLE);
       	}
       	if(!World.allMode){
       		fileChoose.setVisibility(View.INVISIBLE);
       		onlineStageChoose.setVisibility(View.GONE);
       		random.setVisibility(View.GONE);
       	}
        
        CheckBox cb1 = (CheckBox) startView.findViewById(R.id.checkBox1);
        CheckBox cb2 = (CheckBox) startView.findViewById(R.id.checkBox2);
        editm = (CheckBox) startView.findViewById(R.id.editMode);
        cb1.setChecked(Music.ex);
        cb2.setChecked(Music.bgm);
        editm.setChecked(World.editMode);
        editm.setVisibility(World.developMode?View.VISIBLE:View.INVISIBLE);
        
        
        View gameModel=startView.findViewById(R.id.gameModel);
        gameModel.setVisibility(View.INVISIBLE);
        
        start.setOnClickListener(click);
        random.setOnClickListener(click);
        more.setOnClickListener(click);
        battleMode.setOnClickListener(click);
        fileChoose.setOnClickListener(click);
        onlineStageChoose.setOnClickListener(click);
        stage.setOnClickListener(click);
        
//        mario.setOnClickListener(click);
//        adventure.setOnClickListener(click);
//        rampage.setOnClickListener(click);
//        randomrampage.setOnClickListener(click);
//        contra.setOnClickListener(click);
//        normalshot.setOnClickListener(click);
        noweapon.setOnClickListener(click);
        alwaysgun.setOnClickListener(click);
        
        cb1.setOnCheckedChangeListener(check);
        cb2.setOnCheckedChangeListener(check);
        editm.setOnCheckedChangeListener(check);
        
        username=(TextView)startView.findViewById(R.id.nicheng);
        username.setText(acti.userName);
    }
  
    private void initPaiming(View v) {
		// TODO Auto-generated method stub
		ListView biao=(ListView) v.findViewById(R.id.paimingbiao);
		biao.setAdapter(paimingAdapter=new PaimingAdapter(acti.userInfoList));
    }

	OnClickListener click = new OnClickListener() {
    	int t=0;
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.startgame:
				acti.startWithIndex(acti.mapIndex);
				// clear the map dile and string
				break;
			case R.id.randomchalenge:
				acti.randomChalenge();// clear the map dile and string
				break;
			case R.id.fileChoose:
				try{
					
				acti.intentToFileChooser();
				}catch(Exception e){
					Client.send(e.getMessage());
				}
				break;
			case R.id.battleMode:
				acti.intentToBattleMode();
				break;
			case R.id.onlineFileChooser:
				acti.sendOnlineStageRequest();
				break;
			case R.id.stageChoose:
				acti.initStageChooser();
				return;//bujiazaiyouxi//
			case R.id.more:
				acti.showAppWall();
				return;//bujiazaiyouxi//
		
			case R.id.rampage:
				World.RAMPAGE=rampage.isChecked();
				break;
			case R.id.nowepon:
				World.NOWEPON=noweapon.isChecked();
				break;
			case R.id.alwaysgun:
				World.ALWAYSGUN=alwaysgun.isChecked();
				break;
			case R.id.user:
				newEditDialog();
				break;

			}
			
			
		}
		private void newEditDialog() {
			// TODO Auto-generated method stub
			final Dialog dl = new Dialog(acti, R.style.myDialog);
			dl.setTitle("修改昵称");
			View v =acti. getLayoutInflater().inflate(R.layout.userinfo, null);
			dl.setContentView(v);
//			dl.getWindow().setGravity(Gravity.LEFT | Gravity.TOP);

			final TextView editT=(TextView) v.findViewById(R.id.usernameedit);
			final TextView idText=(TextView) v.findViewById(R.id.userIdText);
			if(acti.userId>10)idText.setText("id:"+acti.userId);
			editT.setText(acti.userName);
			OnClickListener edit = new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					switch(v.getId()){
					case R.id.editok:
						String sn=editT.getText().toString();
						if(sn.length()>5)sn=sn.substring(0, 5);
						if(sn.contains("\n"))sn=sn.substring(0, sn.indexOf("\n"));
						
						acti.saveUserMessage(sn);
						dl.cancel();
						break;
					case R.id.editcancel:dl.cancel();break;
					case R.id.randomNameButton:
						editT.setText(UserName.randomName());
						break;
					}
				}
			};
			Button ok=(Button) v.findViewById(R.id.editok);
			Button cancel=(Button) v.findViewById(R.id.editcancel);
			Button randomName=(Button) v.findViewById(R.id.randomNameButton);
			randomName.setOnClickListener(edit);
			ok.setOnClickListener(edit);
			cancel.setOnClickListener(edit);
			
			dl.show();
		}
	};

	OnCheckedChangeListener check = new OnCheckedChangeListener() {

		
		String str="";
		String moneyStr="010110100011";//作弊
		String editStr="00001111";//编辑模式
		String rpgStr="11110000";//rpg
		String bigMode = "000111";//自由变形模式
		String openStr = "01010101";//开发者模式
		String item3Str = "10101010";//三地形模式
		int maxLength=15;// max length in the up number
		
		@Override
		public void onCheckedChanged(CompoundButton v, boolean isChecked) {
//			t-=3;
			
			if (v.getId() == R.id.checkBox2) {
				Music.ex = isChecked;
				str+=0;
			}
			else if (v.getId() == R.id.checkBox1) {
				World.music.changeBGMStatus(isChecked);
				str+=1;
			}else if (v.getId() == R.id.editMode) {
				World.editMode=isChecked;
			}
			
		if(str.length()>maxLength){
			str="";
			Toast.makeText(acti, "少侠好手速！佩服佩服！", Toast.LENGTH_SHORT).show();
		}
			
			Log.i("str:"+str+""+"targetStr"+moneyStr+str.equals(moneyStr));
		
			if(str.equals(moneyStr)){
				user.setVisibility(View.VISIBLE);
//				et.setHint("豪礼相送！");
				int count=10000;
				acti.increaseChance(count);
				acti.increaseCoin(count);
				FruitSet.cml(null);
				Toast.makeText(acti, "豪礼相送", Toast.LENGTH_SHORT).show();
			
			}else if(str.equals(editStr)){
				if(World.developMode==false){
					World.developMode=true;
					editm.setVisibility(View.VISIBLE);
					Toast.makeText(acti, "开发模式开启", Toast.LENGTH_SHORT).show();
				}else {
					World.developMode=false;
					editm.setVisibility(View.INVISIBLE);
					Toast.makeText(acti, "开发模式关", Toast.LENGTH_SHORT).show();
				}
			}else if(str.equals(openStr)){
				if(World.allMode==false){
					World.allMode=true;
					onlineAndFileMode(View.VISIBLE	);
					Toast.makeText(acti, "开启了所有模式", Toast.LENGTH_SHORT).show();
				}else {
					World.allMode=false;
					onlineAndFileMode(View.INVISIBLE	);
					Toast.makeText(acti, "关闭了所有模式", Toast.LENGTH_SHORT).show();
				}
			}else if(str.equals(rpgStr)){
				if(World.rpgMode==false){
					World.baseLifeMax=1000;
					World.rpgMode=true;
					Toast.makeText(acti, "RPG模式开启", Toast.LENGTH_SHORT).show();
				}else {
					World.rpgMode=false;
					Toast.makeText(acti, "RPG模式关闭", Toast.LENGTH_SHORT).show();
				}
			}
			else if(str.equals(bigMode)){
				if(World.bigMode==false){
					World.bigMode=true;
					Toast.makeText(acti, "自由变形模式开启", Toast.LENGTH_SHORT).show();
				}else {
					World.bigMode=false;
					Toast.makeText(acti, "自由变形模式关闭", Toast.LENGTH_SHORT).show();
				}
			}
			else if(str.equals(item3Str)){
				if(World.Item3Mode==false){
					World.Item3Mode=true;
					Toast.makeText(acti, "3地形模式开启", Toast.LENGTH_SHORT).show();
				}else {
					World.Item3Mode=false;
					Toast.makeText(acti, "3地形模式关闭", Toast.LENGTH_SHORT).show();
				}
			}
		
			else {
				return;// to avoid str to be  not empty
			}str="";
			
		}

	};
	public void onlineAndFileMode(int visible) {
		
		fileChoose.setVisibility(visible);
		onlineStageChoose.setVisibility(visible);
		random.setVisibility(visible);
	}
//	private EditText et;
	private Button fileChoose1;
	private View user;
	private PaimingAdapter paimingAdapter;
	private Button battleMode;
	private Button random;
	private CheckBox editm;
	boolean battleMode(){
		return battleMode.getVisibility()==View.VISIBLE;
	}
	public void hide() {
		// TODO Auto-generated method stub
		acti.removeView(startView);
//		acti.ad.
	}
	public void setUserName(String username) {
		// TODO Auto-generated method stub
		    this.username.setText(username);
	}


}
