/*package aid;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.http.util.EncodingUtils;

import android.content.Context;

public class Fileeee {
	private Context c;

	Fileeee(Context c){
		this.c=c;
	}
	*//**
	 * 一、私有文件夹下的文件存取（/data/data/包名/files）
	 * 
	 * @param fileName
	 * @param message
	 *//*
	public void writeFileData(String fileName, String message) {
		try {
			FileOutputStream fout = c.openFileOutput(fileName, Context.MODE_PRIVATE);
			byte[] bytes = message.getBytes();
			fout.write(bytes);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	*//**
	 * //读文件在./data/data/包名/files/下面
	 * 
	 * @param fileName
	 * @return
	 *//*
	public String readFileData(String fileName) {
		String res = "";
		try {
			FileInputStream fin = c.openFileInput(fileName);
			int length = fin.available();
			byte[] buffer = new byte[length];
			fin.read(buffer);
			res = EncodingUtils.getString(buffer, "UTF-8");
			fin.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	*//**
	 * 写， 读sdcard目录上的文件，要用FileOutputStream， 不能用openFileOutput
	 * 不同点：openFileOutput是在raw里编译过的，FileOutputStream是任何文件都可以
	 * 
	 * @param fileName
	 * @param message
	 *//*
	// 写在/mnt/sdcard/目录下面的文件
	public void writeFileSdcard(String fileName, String message) {

		try {

			// FileOutputStream fout = openFileOutput(fileName, MODE_PRIVATE);

			FileOutputStream fout = new FileOutputStream(fileName);

			byte[] bytes = message.getBytes();

			fout.write(bytes);

			fout.close();

		}

		catch (Exception e) {

			e.printStackTrace();

		}

	}

	// 读在/mnt/sdcard/目录下面的文件

	public String readFileSdcard(String fileName) {

		String res = "";

		try {

			FileInputStream fin = new FileInputStream(fileName);

			int length = fin.available();

			byte[] buffer = new byte[length];

			fin.read(buffer);

			res = EncodingUtils.getString(buffer, "UTF-8");

			fin.close();

		}

		catch (Exception e) {

			e.printStackTrace();

		}

		return res;

	}

	*//**
	 * 二、从resource中的raw文件夹中获取文件并读取数据（资源文件只能读不能写）
	 * 
	 * @param fileInRaw
	 * @return
	 *//*
	public String readFromRaw(int fileInRaw) {
		String res = "";
		try {
			InputStream in =c. getResources().openRawResource(fileInRaw);
			int length = in.available();
			byte[] buffer = new byte[length];
			in.read(buffer);
			res = EncodingUtils.getString(buffer, "GBK");
			// res = new String(buffer,"GBK");
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	*//**
	 * 三、从asset中获取文件并读取数据（资源文件只能读不能写）
	 * 
	 * @param fileName
	 * @return
	 *//*
	public String readFromAsset(String fileName) {
		String res = "";
		try {
			InputStream in = c.getResources().getAssets().open(fileName);
			int length = in.available();
			byte[] buffer = new byte[length];
			in.read(buffer);
			res = EncodingUtils.getString(buffer, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

}*/