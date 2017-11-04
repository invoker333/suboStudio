package aid;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mingli.toms.MenuActivity;
import com.mingli.toms.R;
import com.mingli.toms.World;

import java.util.ArrayList;

import Element.Animation;
import element2.TexId;

public class Producer {
	private PopupWindow popupWindow;
	MenuActivity acti;
	private ItemAdapter itemadapter;
	int itemcount = 4;
	Animation selectedItem;
	int selectedId=-1;
	private OnItemClickListener gridListener = new OnItemClickListener() {
//		private View selectedView;

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
//			if (selectedView != null)
			selectedId=position;
			selectedItem = animationList.get(position);
			itemadapter.notifyDataSetChanged();
//			// itemadapter.notifyDataSetChanged();
//			selectedView = view;
		}
	};
	private World world;
	private ViewGroup animationShopadcontainer;
	private ArrayList<Animation> animationList;

	public Producer(MenuActivity acti, World world,ArrayList<Animation>animationList) {
		this.acti = acti;
		this.world = world;
		this.animationList = animationList;
	}

	public void showWindow(View v) {
		long tim = System.currentTimeMillis();
		if (popupWindow != null && popupWindow.isShowing())
			return;
		else if (popupWindow == null) {
			// TODO Auto-generated method stub
			// 获取自定义布局文件activity_popupwindow_left.xml的视图
			View popupWindow_view = acti.getLayoutInflater().inflate(
					R.layout.animationshop, null);
			// 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
			popupWindow = new PopupWindow(popupWindow_view,
					WindowManager.LayoutParams.MATCH_PARENT,
					WindowManager.LayoutParams.MATCH_PARENT, true);
			GridView gridView = (GridView) popupWindow_view
					.findViewById(R.id.animationShopGridView);

			initButton(popupWindow_view);
			animationShopadcontainer = (ViewGroup) popupWindow_view
					.findViewById(R.id.animationShopadcontainer);

			gridView.setNumColumns(itemcount);// ////
			int space = 30;
			gridView.setHorizontalSpacing(space);
			gridView.setVerticalSpacing(space);

			itemadapter = new ItemAdapter(acti, animationList);

			gridView.setAdapter(itemadapter);
			gridView.setOnItemClickListener(gridListener);
			popupWindow.setAnimationStyle(R.style.AnimationFade);

			View.OnTouchListener otl = new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					hideCheck();
					return true;
				}
			};
			popupWindow_view.setOnTouchListener(otl);
		}
		acti.showBanner(animationShopadcontainer);
		popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
		// popupWindow.showAsDropDown(v);
		Log.i("AnimationShopPopTime", "" + (System.currentTimeMillis() - tim));
	}

	private void initButton(View v) {
		// TODO Auto-generated method stub
		Button buyAnimation = (Button) v.findViewById(R.id.buyAnimationanduse);
		OnClickListener click = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (selectedItem == null) {
					MenuActivity.showDialog("提示", "请选择一个东西来安放", TexId.CAP);
					return;
				}
//				if (acti.coinCount - selectedItem.cost >= 0
//						&& acti.chance - selectedItem.chancecost >= 0) {
//					acti.coinCount -= selectedItem.cost;
//					acti.chance -= selectedItem.chancecost;
//					// ///
					world.buildAnimation(selectedItem);
					hideCheck();
//
//				} else {
//					if (acti.coinCount - selectedItem.cost < 0)
//						tips.showAtTip(world, "金币不够", 0, 0);
//					else if (acti.chance - selectedItem.chancecost < 0)
//						tips.showAtTip(world, "生命数量不够！", 0, 0);
//				}
				// selectedItem = null;
				itemadapter.notifyDataSetChanged();
			}

		};
		buyAnimation.setOnClickListener(click);
	}

	public void hideCheck() {
		if (popupWindow != null && popupWindow.isShowing()) {
			popupWindow.dismiss();
		}
	}

	public ItemAdapter getItemadapter() {
		return itemadapter;
	}

	public void setItemadapter(ItemAdapter itemadapter) {
		this.itemadapter = itemadapter;
	}

	class ItemAdapter extends BaseAdapter {

		private Context context;
		ArrayList<Animation> animationList;

		// private View selectedView;

		public ItemAdapter(Context context, ArrayList<Animation> arrayList) {
			this.context = context;
			animationList = arrayList;

		}

		public int getCount() {
			// TODO Auto-generated method stub
			return animationList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return animationList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View v = null;
			View icon = null;
			TextView name = null;
			TextView cost = null;
			TextView chancecost = null;
			if (convertView == null) {
				v = acti.getLayoutInflater().inflate(R.layout.shopitem,
						null);

				// convertView.setLayoutParams(new
				// GridView.LayoutParams(ItemWindow.itemWidth,
				// ItemWindow.itemWidth));// bug

			} else {
				v = convertView;
			}
			// selectedView = v;
			icon = v.findViewById(R.id.goodsicon);
			name = (TextView) v.findViewById(R.id.goodsname);
			cost = (TextView) v.findViewById(R.id.goodscost);
			chancecost = (TextView) v.findViewById(R.id.chancecost);
			// 设置大小

			/*
			 * LayoutParams param = v.getLayoutParams(); int
			 * itemWidth=popupWindow.getWidth()/itemcount;
			 * param.width=itemWidth; param.height=itemWidth;
			 * v.setLayoutParams(param);// nobug
			 */

			if(position==selectedId)v.setBackgroundResource(R.drawable.greenrect);
			else v.setBackgroundResource(R.drawable.whitestroke);


			Animation f = animationList.get(position);
			icon.setBackground(new BitmapDrawable(f.getIcon()));
			name.setText(f.name);
			cost.setText("" + f.cost);
			if (f.chancecost > 0)
				chancecost.setText("" + f.chancecost);
			else
				v.findViewById(R.id.chancecostlayout).setVisibility(View.GONE);

			return v;

		}

	}
}
