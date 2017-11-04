package fileRW;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mingli.toms.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class FileActivity extends Activity {

	public static final String TOMS_MAP_NAME = "TomsMap";
	public static final String TEMP_NAME = "temp";
	File currentParent;
	File[] currentFiles;

	FileListAdapter fileListAdapter;

	String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_file);
		Log.v(TAG, "onCreate");
		initView();

	}

	private void initView() {
		path = (EditText) findViewById(R.id.filepathtextview);

		list = (ListView) findViewById(R.id.list);
		list.setOnItemClickListener(listitemClicked);

		Button parent = (Button) findViewById(R.id.parent);
		parent.setOnClickListener(parentClicked);

		Button go = (Button) findViewById(R.id.gotofilepath);
		go.setOnClickListener(parentClicked);

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			currentParent = Environment.getExternalStorageDirectory();
//			Environment.getDataDirectory().getPath()
			File f = new File(currentParent.getAbsoluteFile(), TOMS_MAP_NAME);
			Log.i("file dir" + f, TAG);
			if (!f.exists()) {
				f.mkdirs();
				currentParent = f;
			} else if (f.exists()) {
				currentParent = f;
			}

			currentFiles = currentParent.listFiles();
			initList(FileActivity.this, currentFiles);
		}
	}



	private OnClickListener parentClicked = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.gotofilepath:
				File f = new File (path.getText().toString());
				if(f.exists()&&f.isDirectory()){
					currentFiles = f.listFiles();
					initList(FileActivity.this, currentFiles);
					currentParent=f;
				}
				break;
			case R.id.parent:
				try {
					if (currentParent != null
							&& !currentParent.getCanonicalPath().equals("/mnt/sdcard")
							&& !currentParent.getCanonicalPath().equals("/mnt/sdcard0")
							&& !currentParent.getCanonicalPath().equals("/mnt/sdcard1")) {
						currentParent = currentParent.getParentFile();

						if (currentParent == null) {
							finish();
							return;
						}

						currentFiles = currentParent.listFiles();
						initList(FileActivity.this, currentFiles);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
	};

	private OnItemClickListener listitemClicked = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			if (currentFiles[position].isFile()
//					&& currentFiles[position].getName().contains(".txt")
					) {

				Intent intent = getIntent();
//				String f = currentFiles[position].getAbsolutePath();

				intent.putExtra("mapfileId", position);
//				intent.putExtra("mapfiles", currentFiles);
				intent.putStringArrayListExtra("mapFiles",getAddressStrings(currentFiles));
				setResult(RESULT_OK, intent); // intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
				finish();// 此处一定要调用finish()方法
				// ///

				// 下面的代码的作用：调用打开方式
				// Intent intent = new Intent();
				// intent.setAction(android.content.Intent.ACTION_VIEW);
				// intent.setDataAndType(Uri.fromFile(currentFiles[position]),
				// "text/plain");
				// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				// startActivity(intent);

				return;
			}
			File[] tmp = currentFiles[position].listFiles();
			if (tmp == null
			// || tmp.length==0
			) {
				Toast.makeText(FileActivity.this, "无效文件",
						Toast.LENGTH_SHORT).show();
			} else {
				currentParent = currentFiles[position];
				currentFiles = tmp;
				initList(FileActivity.this, currentFiles);
			}
		}
	};

	private ArrayList<String> getAddressStrings(File[] currentFiles) {
		ArrayList<String> al=new ArrayList<String>(currentFiles.length);
		for(File f:currentFiles){
			al.add(f.getAbsolutePath());
		}
		return al;
	}

	private ListView list;
	private EditText path;
	TextView fptv;
	
	private void initList(Context context, File[] files) {
		if(files==null)return;
		Arrays.sort(files,new FileComparator());
		
		fptv=(TextView) findViewById(R.id.filepathtextview);
		fptv.setText(currentParent.getPath());
		fileListAdapter = new FileListAdapter(context, files);
		list.setAdapter(fileListAdapter);
	}
	class FileComparator implements Comparator<File>{
		@Override
		public int compare(File f1, File f2) {
			// TODO Auto-generated method stub
			if(f1.isDirectory()&&f2.isFile()){
				return 1;
			}else if(f2.isDirectory()&&f1.isFile()){
				return -1;
			}else if(f1.isFile()&&f2.isFile()){
				if(f1.getName().endsWith(".zip")&&!f2.getName().endsWith(".zip")){
					return -1;
				}else if(!f1.getName().endsWith(".zip")&&f2.getName().endsWith(".zip")){
					return 1;
				}
			}
			return 0;
		}
	}

	

}
