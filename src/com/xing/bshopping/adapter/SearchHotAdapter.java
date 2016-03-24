package com.xing.bshopping.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xing.bshopping.R;

public class SearchHotAdapter extends BaseAdapter{
	
	private Context context;
	private List<Map<String, Object>> searchHotList;

	
	public SearchHotAdapter(Context context,
			List<Map<String, Object>> searchHotList) {
		this.context = context;
		this.searchHotList = searchHotList;
	}

	@Override
	public int getCount() {
		return searchHotList.size();
	}

	@Override
	public Object getItem(int position) {
		return searchHotList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.searchhot_item,
					null);
			
			holder.tv_search_gridview_item = (TextView) convertView.findViewById(R.id.tv_search_gridview_item);
			
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_search_gridview_item.setText(searchHotList.get(position)
				.get("hotSearchTitle").toString());
		
		
		return convertView;
	}
	
	public static class ViewHolder {
		public TextView tv_search_gridview_item;
	}


	
}
