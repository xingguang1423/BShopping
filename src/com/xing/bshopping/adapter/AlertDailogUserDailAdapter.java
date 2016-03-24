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

public class AlertDailogUserDailAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String, Object>> dailogList;

	public AlertDailogUserDailAdapter(Context context,
			List<Map<String, Object>> dailogList) {
		super();
		this.context = context;
		this.dailogList = dailogList;
	}

	@Override
	public int getCount() {
		return dailogList.size();
	}

	@Override
	public Object getItem(int position) {
		return dailogList.get(position);
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

			convertView = View.inflate(context,
					R.layout.activity_userdail_alertdialog, null);
			holder.user_alertdialog_img = (ImageView) convertView
					.findViewById(R.id.user_alertdialog_img);
			holder.user_alertdialog_text = (TextView) convertView
					.findViewById(R.id.user_alertdialog_text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// getItem(position);

		holder.user_alertdialog_img.setBackgroundResource(Integer
				.parseInt(dailogList.get(position).get("dailogImg").toString()));
		
		holder.user_alertdialog_text.setText(dailogList.get(position).get("dailogTitle").toString());

		return convertView;
	}

	public static class ViewHolder {

		public ImageView user_alertdialog_img;
		public TextView user_alertdialog_text;
	}

}
