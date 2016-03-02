package com.xing.bshopping.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xing.bshopping.R;

public class ClassesAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String, Object>> classesList;

	public ClassesAdapter(Context context, List<Map<String, Object>> listgrid) {
		this.context = context;
		this.classesList = listgrid;
	}

	@Override
	public int getCount() {
		return classesList.size();
	}

	@Override
	public Object getItem(int position) {

		return classesList.get(position);
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
			convertView = View.inflate(context, R.layout.classes_gridview_item,
					null);
			holder.iv_gridview_item_icon = (ImageView) convertView
					.findViewById(R.id.iv_gridview_item_icon);
			holder.tv_gridview_item_title = (TextView) convertView
					.findViewById(R.id.tv_gridview_item_title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		getItem(position);

		holder.iv_gridview_item_icon.setBackgroundResource(Integer
				.parseInt(classesList.get(position).get("image").toString()));
		holder.tv_gridview_item_title.setText(classesList.get(position)
				.get("title").toString());

		return convertView;
	}

	public static class ViewHolder {

		public ImageView iv_gridview_item_icon;
		public TextView tv_gridview_item_title;
	}

}
