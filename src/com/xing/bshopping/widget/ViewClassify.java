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

public class ViewClassify extends LinearLayout implements ViewBaseAction {
	
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

	public ViewClassify(Context context) {
		super(context);
		init(context);
	}

	public ViewClassify(Context context, AttributeSet attrs) {
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
				groups.add("全部分类");
for(int j=0;j<15;j++){
					
					switch (j) {
					case 0:
						tItem.add("全部分类");
						break;
					default:
						break;
					}
				}
				break;
			case 1:
				groups.add("电影");
				for(int j=0;j<1;j++){
					switch (j) {
					case 0:
						tItem.add("电影");
						break;
					default:
						break;
					}
				}
				
				break;
			case 2:
				groups.add("美食");
				
				for(int j=0;j<15;j++){
					
					switch (j) {
					case 1:
						tItem.add("火锅");
						break;
					case 2:
						tItem.add("自助餐");
						break;
					case 3:
						tItem.add("西餐");
						break;
					case 4:
						tItem.add("日韩料理");
						break;
					case 5:
						tItem.add("蛋糕甜点");
						break;
					case 6:
						tItem.add("烧烤烤鱼");
						break;
					case 7:
						tItem.add("湘菜");
						break;
					case 8:
						tItem.add("西北菜");
						break;
					case 9:
						tItem.add("素食");
						break;
					case 10:
						tItem.add("台湾菜");
						break;
					case 11:
						tItem.add("小吃快餐");
						break;
					case 12:
						tItem.add("其他美食");
						break;
					default:
						break;
					}
				}
				
				break;
			case 3:
				groups.add("摄影写真");
				for(int j=0;j<1;j++){
					switch (j) {
					case 0:
						tItem.add("摄影写真");
						break;
					default:
						break;
					}
				}
				break;
			case 4:
				groups.add("酒店");
				for(int j=0;j<15;j++){
					
					switch (j) {
					case 1:
						tItem.add("经济型酒店");
						break;
					case 2:
						tItem.add("豪华酒店");
						break;
					case 3:
						tItem.add("主题酒店");
						break;
					case 4:
						tItem.add("公寓型酒店");
						break;
					default:
						break;
					}
				}
				break;
			case 5:
				groups.add("休闲娱乐");
				for(int j=0;j<15;j++){
					
					switch (j) {
					case 1:
						tItem.add("KTV");
						break;
					case 2:
						tItem.add("足疗按摩");
						break;
					case 3:
						tItem.add("运动建身");
						break;
					case 4:
						tItem.add("真人CS");
						break;
					case 5:
						tItem.add("密室逃脱");
						break;
					case 6:
						tItem.add("其它娱乐");
						break;
					default:
						break;
					}
				}
				break;
			case 6:
				groups.add("汽车服务");
				for(int j=0;j<1;j++){
					switch (j) {
					case 0:
						tItem.add("汽车服务");
						break;
					default:
						break;
					}
				}
				break;
			case 7:
				groups.add("生活服务");
				for(int j=0;j<15;j++){
					
					switch (j) {
					case 1:
						tItem.add("母婴亲子");
						break;
					case 2:
						tItem.add("体检保健");
						break;
					case 3:
						tItem.add("照片冲印");
						break;
					case 4:
						tItem.add("培训课程");
						break;
					case 5:
						tItem.add("鲜花婚庆");
						break;
					case 6:
						tItem.add("其它生活");
						break;
					default:
						break;
					}
				}
				break;

			case 8:
				groups.add("丽人");
				for(int j=0;j<15;j++){
					
					switch (j) {
					case 1:
						tItem.add("美发");
						break;
					case 2:
						tItem.add("美甲");
						break;
					case 3:
						tItem.add("美容SPA");
						break;
					case 4:
						tItem.add("瑜伽/舞蹈");
						break;
					default:
						break;
					}
				}
				break;
			case 9:
				groups.add("旅游");
				for(int j=0;j<15;j++){
					
					switch (j) {
					case 1:
						tItem.add("本地/周边游");
						break;
					case 2:
						tItem.add("景点门票");
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
