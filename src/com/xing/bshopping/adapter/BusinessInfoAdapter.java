package com.xing.bshopping.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xing.bshopping.R;
import com.xing.bshopping.entity.BusinessInfo;

public class BusinessInfoAdapter extends BaseAdapter {

	private Context context;
	private List<BusinessInfo> datas;
	private ImageLoader imageLoader;

	public BusinessInfoAdapter(Context context, List<BusinessInfo> datas) {
		this.context = context;
		this.datas = datas;

		imageLoader = ImageLoader.getInstance();
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public BusinessInfo getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if (convertView == null) {
			
			holder = new ViewHolder();
			
			convertView = View.inflate(context, R.layout.frag_poi_listview_businessinfo_item, null);
			
			holder.poi_iv_img = (ImageView) convertView.findViewById(R.id.poi_iv_img);
			holder.poi_tv_bName = (TextView) convertView.findViewById(R.id.poi_tv_bName);
			holder.poi_tv_bType = (TextView) convertView.findViewById(R.id.poi_tv_bType);
			holder.poi_tv_bAddress = (TextView) convertView.findViewById(R.id.poi_tv_bAddress);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		BusinessInfo businessInfo = getItem(position);
		
		imageLoader.displayImage(businessInfo.getbImgUrl(),holder.poi_iv_img);
		
		holder.poi_tv_bName.setText(businessInfo.getbName());
		holder.poi_tv_bType.setText(businessInfo.getbType());
		holder.poi_tv_bAddress.setText(businessInfo.getbAddress());
		
		
		return convertView;
	}
	
	public static class ViewHolder {
		public ImageView poi_iv_img;  //商家的图片地址
		public TextView poi_tv_bName;  //商家名
		public TextView poi_tv_bType;  //商家类型
		public TextView poi_tv_bAddress;  //商家地址
	}

	public void addItem(BusinessInfo businessInfo){
		datas.add(0,businessInfo);
	}
	
}
