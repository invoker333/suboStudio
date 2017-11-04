package com.mingli.toms;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.io.IOException;

import aid.Log;

public class Music {
	MediaPlayer mp;
	// private AudioManager am;
	private SoundPool sp;
	private Context context;

	public static boolean ex = true;
	public static boolean bgm = true;
	int musicId;
	public  static int soundPoolTime = 10;

	public Music(Context context) {
		this.context = context;
		initSoundPool();
	}

	static void setSwitch(boolean bgm, boolean ex) {
		Music.bgm = bgm;
		Music.ex = ex;
	}

	public void setBGM(int resId) {
		if (!bgm)
			return;
		if (mp != null && musicId == resId)
			return;
		musicId = resId;
		if (mp != null)
			mp.release();
		mp = MediaPlayer.create(context, resId);
		mp.setLooping(true);
		try {
			mp.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mp.start();
	}

	void initSoundPool() {
		sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);// 同时播放数量
		MusicId.loadSoundId(this);
	}

	public int loadSound(int resId) {
		return sp.load(context, resId, 1);
	}

	public void playSound(int soundId, int loop) {
		if (soundPoolTime-- > 0) {
			if (ex)
				sp.play(soundId, 1, 1, 1, loop, 1);
		}
	}

	protected void onDestroy() {
		if (mp != null)
			mp.release();
		sp.release();
		// am=null;
	}

	void releaseSoundPool() {
		sp.release();
	}

	public void hasEx() {
		ex = true;
	}

	public void noEx() {
		ex = false;
	}

	public void hasBgm() {
		bgm = true;
		resume();
	}

	public void noBgm() {
		bgm = false;
		pause();

	}
	public void changeBGMStatus(boolean b){
		if(b){
			hasBgm();
		}else noBgm();
	};
	public void pause() {
		// TODO Auto-generated method stub
		if (mp != null)
			try {
				mp.pause();
			} catch (Exception e) {
				Log.i("media的pause异常");
			}
	}

	public void resume() {
		// TODO Auto-generated method stub

		try {
			if (mp != null && !mp.isPlaying() && bgm)
				mp.start();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setLooping(boolean b) {
		// TODO Auto-generated method stub
		if (mp != null)
			mp.setLooping(b);
	}

	public void pauseBGM() {
		// TODO Auto-generated method stub
		if (mp != null)
			mp.pause();
	}
}