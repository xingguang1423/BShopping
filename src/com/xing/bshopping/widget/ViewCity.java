package com.xing.bshopping.widget;

import java.util.ArrayList;
import java.util.LinkedList;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.xing.bshopping.R;
import com.xing.bshopping.adapter.TextAdapter;

public class ViewCity extends LinearLayout implements ViewBaseAction {
	
	private ListView regionListView;
	private ListView plateListView;
	private ArrayList<String> groups = new ArrayList<String>();
	private LinkedList<String> childrenItem = new LinkedList<String>();
	private SparseArray<LinkedList<String>> children = new SparseArray<LinkedList<String>>();
	private TextAdapter plateListViewAdapter;
	private TextAdapter earaListViewAdapter;
	private OnSelectListener mOnSelectListener;
	private int tEaraPosition = 0;
	private int tBlockPosition = 0;
	private String showString = "不限";

	public ViewCity(Context context) {
		super(context);
		init(context);
	}

	public ViewCity(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public void updateShowText(String showArea, String showBlock) {
		if (showArea == null || showBlock == null) {
			return;
		}
		for (int i = 0; i < groups.size(); i++) {
			if (groups.get(i).equals(showArea)) {
				earaListViewAdapter.setSelectedPosition(i);
				childrenItem.clear();
				if (i < children.size()) {
					childrenItem.addAll(children.get(i));
				}
				tEaraPosition = i;
				break;
			}
		}
		for (int j = 0; j < childrenItem.size(); j++) {
			if (childrenItem.get(j).replace("不限", "").equals(showBlock.trim())) {
				plateListViewAdapter.setSelectedPosition(j);
				tBlockPosition = j;
				break;
			}
		}
		setDefaultSelect();
	}

	private void init(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_region, this, true);
		regionListView = (ListView) findViewById(R.id.listView);
		plateListView = (ListView) findViewById(R.id.listView2);
		setBackgroundDrawable(getResources().getDrawable(
				R.drawable.choosearea_bg_left));
		
		for(int i=0;i<10;i++){
			LinkedList<String> tItem = new LinkedList<String>();
			
			switch (i) {
			case 0:
				groups.add("附近");
				for(int j=0;j<15;j++){
					
					switch (j) {
					case 0:
						tItem.add("全城");
						break;
					case 1:
						tItem.add("1千米");
						break;
					case 2:
						tItem.add("3千米");
						break;
					case 3:
						tItem.add("5千米");
						break;
					case 4:
						tItem.add("10千米");
						break;
					default:
						break;
					}
				}
				
				break;
			case 1:
				groups.add("斗门区");
				
				for(int j=0;j<15;j++){
					
					switch (j) {
					case 1:
						tItem.add("井岸镇");
						break;
					default:
						break;
					}
				}
				
				break;
			case 2:
				groups.add("香洲区");
				
				for(int j=0;j<15;j++){
					
					switch (j) {
					case 1:
						tItem.add("吉大");
						break;
					case 2:
						tItem.add("拱北");
						break;
					case 3:
						tItem.add("香洲");
						break;
					case 4:
						tItem.add("前山");
						break;
					case 5:
						tItem.add("南屏");
						break;
					case 6:
						tItem.add("新香洲");
						break;
					case 7:
						tItem.add("华发商都");
						break;
					case 8:
						tItem.add("富华里");
						break;
					case 9:
						tItem.add("湾仔");
						break;
					case 10:
						tItem.add("夏湾");
						break;
					case 11:
						tItem.add("摩尔广场");
						break;
					default:
						break;
					}
				}
				
				break;
			case 3:
				groups.add("金湾区");
				for(int j=0;j<15;j++){
					
					switch (j) {
					case 1:
						tItem.add("红旗镇");
						break;
					case 2:
						tItem.add("三灶镇");
						break;
					default:
						break;
					}
				}
				break;
			case 4:
				groups.add("香洲其它");
				for(int j=0;j<1;j++){
					switch (j) {
					case 0:
						tItem.add("香洲其它");
						break;
					default:
						break;
					}
				}
				break;

			default:
				break;
			}
			children.put(i, tItem);
		}
		
//		for(int i=0;i<10;i++){
//			groups.add(i+"行");
//			LinkedList<String> tItem = new LinkedList<String>();
//			for(int j=0;j<15;j++){
//				
//				tItem.add(i+"行"+j+"列");
//				
//			}
//			children.put(i, tItem);
//		}

		earaListViewAdapter = new TextAdapter(context, groups,
				R.drawable.choose_item_selected,
				R.drawable.choose_eara_item_selector);
		earaListViewAdapter.setTextSize(17);
		earaListViewAdapter.setSelectedPositionNoNotify(tEaraPosition);
		regionListView.setAdapter(earaListViewAdapter);
		earaListViewAdapter
				.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

					@Override
					public void onItemClick(View view, int position) {
						if (position < children.size()) {
							childrenItem.clear();
							childrenItem.addAll(children.get(position));
							plateListViewAdapter.notifyDataSetChanged();
						}
					}
				});
		if (tEaraPosition < children.size())
			childrenItem.addAll(children.get(tEaraPosition));
		plateListViewAdapter = new TextAdapter(context, childrenItem,
				R.drawable.choose_item_right,
				R.drawable.choose_plate_item_selector);
		plateListViewAdapter.setTextSize(15);
		plateListViewAdapter.setSelectedPositionNoNotify(tBlockPosition);
		plateListView.setAdapter(plateListViewAdapter);
		plateListViewAdapter
				.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

					@Override
					public void onItemClick(View view, final int position) {
						
						showString = childrenItem.get(position);
						if (mOnSelectListener != null) {
							
							mOnSelectListener.getValue(showString);
						}

					}
				});
		if (tBlockPosition < childrenItem.size())
			showString = childrenItem.get(tBlockPosition);
		if (showString.contains("不限")) {
			showString = showString.replace("不限", "");
		}
		setDefaultSelect();

	}

	public void setDefaultSelect() {
		regionListView.setSelection(tEaraPosition);
		plateListView.setSelection(tBlockPosition);
	}

	public String getShowText() {
		return showString;
	}

	public void setOnSelectListener(OnSelectListener onSelectListener) {
		mOnSelectListener = onSelectListener;
	}

	public interface OnSelectListener {
		public void getValue(String showText);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}
}
