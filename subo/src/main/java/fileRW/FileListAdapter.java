package fileRW;

import java.io.File;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingli.toms.R;

public class FileListAdapter extends BaseAdapter {
	private File[] currentFiles;
	private LayoutInflater inflater;
	public FileListAdapter(Context context,File[] currentFiles) {
		// TODO Auto-generated constructor stub
		this.currentFiles=currentFiles;
		this.inflater=LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
//		Log.v("lidong", ""+currentFiles.length);
		return currentFiles.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v= null;
		ImageView icon = null;
		TextView file_name= null;
		TextView file_type= null;
		if(convertView==null){
			v=inflater.inflate(R.layout.file_list, null);
		}
		else {
			v=convertView;
		}
		 icon = (ImageView) v.findViewById(R.id.icon);
		 file_name = (TextView) v.findViewById(R.id.file_name);
		 file_type = (TextView) v.findViewById(R.id.file_type);
		 
		if(currentFiles[position].isDirectory()){
			icon.setImageResource(R.drawable.icon);
		}else{
			if(currentFiles[position].getName().endsWith(".zip")){
				icon.setImageResource(R.drawable.cap);
				file_type.setText("皮肤");
			}else {
				icon.setImageResource(R.drawable.file_icon);
			}
		}
		file_name.setText( currentFiles[position].getName());
				
		return v;
	}
}
