package com.xing.bshopping.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xing.bshopping.R;
import com.xing.bshopping.entity.MoreItem;
import com.xing.bshopping.utils.ToastUtils;

public class MoreItemAdapter extends BaseAdapter {

	private Context context;
	private List<MoreItem> datas;

	public MoreItemAdapter(Context context, List<MoreItem> datas) {
		this.context = context;
		this.datas = datas;
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public MoreItem getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		final ViewHolder holder;
		if (convertView == null) {

			holder = new ViewHolder();

			convertView = View.inflate(context, R.layout.frag_more_listview_item, null);
			
			holder.v_divider = convertView.findViewById(R.id.v_divider);
			holder.ll_more_content = convertView
					.findViewById(R.id.ll_more_content);

			holder.tv_more_leftText = (TextView) convertView
					.findViewById(R.id.tv_more_leftText);
			holder.tv_more_righttext = (TextView) convertView
					.findViewById(R.id.tv_more_righttext);
			holder.iv_more_rightImg = (ImageView) convertView
					.findViewById(R.id.iv_more_rightImg);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// set data
		final MoreItem item = getItem(position);
		holder.tv_more_leftText.setText(item.getLeftText());
		holder.tv_more_righttext.setText(item.getRightText());
		holder.iv_more_rightImg.setBackgroundResource(item.getRightImg());
		
		holder.v_divider
				.setVisibility(item.isShowTopTopDivider() ? View.VISIBLE
						: View.GONE);
		/**
		 * 
		 */
		holder.iv_more_rightImg.setTag(new Integer(item.getRightImg()));//把资源id放入view的tag中
		holder.iv_more_rightImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Integer id = (Integer) holder.iv_more_rightImg.getTag();
				if (id == R.drawable.ic_global_uikit_switch_off) {
					holder.iv_more_rightImg.setBackgroundResource(R.drawable.ic_global_uikit_switch_on);
					holder.iv_more_rightImg.setTag(R.drawable.ic_global_uikit_switch_on);
				}else{
					holder.iv_more_rightImg.setBackgroundResource(R.drawable.ic_global_uikit_switch_off);
					holder.iv_more_rightImg.setTag(R.drawable.ic_global_uikit_switch_off);
				}
			}
		});
		
		holder.ll_more_content.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				switch (position) {
				case 0:
//					
					
					break;
				default:
					break;
				}
				
				ToastUtils.showToast(context, "item click position = "
						+ position, Toast.LENGTH_SHORT);
			}
		});

		return convertView;
	}

	public static class ViewHolder {
		public View v_divider;
		public View ll_more_content;
		public TextView tv_more_leftText;
		public TextView tv_more_righttext;
		public ImageView iv_more_rightImg;
	}

}
