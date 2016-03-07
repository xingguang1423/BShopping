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
import com.xing.bshopping.entity.GoodsInfo;

public class GoodsInfoAdapter extends BaseAdapter {

	private Context context;
	private List<GoodsInfo> datas;
	private ImageLoader imageLoader;

	public GoodsInfoAdapter(Context context, List<GoodsInfo> datas) {
		this.context = context;
		this.datas = datas;

		imageLoader = ImageLoader.getInstance();
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public GoodsInfo getItem(int position) {
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
			
			convertView = View.inflate(context, R.layout.home_listview_goodsinfo_item, null);
			holder.home_iv_img = (ImageView) convertView.findViewById(R.id.home_iv_img);
			holder.home_iv_nobooking_img = (ImageView) convertView.findViewById(R.id.home_iv_nobooking_img);
			holder.home_tv_title_goodsName = (TextView) convertView.findViewById(R.id.home_tv_title_goodsName);
			holder.home_tv_num = (TextView) convertView.findViewById(R.id.home_tv_num);
			holder.home_tv_goodscontent = (TextView) convertView.findViewById(R.id.home_tv_goodscontent);
			holder.home_goodsprice = (TextView) convertView.findViewById(R.id.home_goodsprice);
			holder.home_tv_goodsshoprice = (TextView) convertView.findViewById(R.id.home_tv_goodsshoprice);
			holder.home_tv_goodsshopnum = (TextView) convertView.findViewById(R.id.home_tv_goodsshopnum);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		GoodsInfo goodsInfo = getItem(position);

		if (goodsInfo.getIsGoodsBooking()) {  //true
			holder.home_iv_nobooking_img.setVisibility(View.VISIBLE);
		}else{
			holder.home_iv_nobooking_img.setVisibility(View.GONE);
		}
		
		imageLoader.displayImage(goodsInfo.getGoodsImgUrl(),holder.home_iv_img);
		
		holder.home_tv_title_goodsName.setText(goodsInfo.getGoodsName());
		holder.home_tv_num.setText("3.5km");
		holder.home_tv_goodscontent.setText(goodsInfo.getGoodsContent());
		
		holder.home_goodsprice.setText(goodsInfo.getGoodsPrice()+"");
		holder.home_tv_goodsshoprice.setText(goodsInfo.getGoodsShopPrice()+"");
		holder.home_tv_goodsshopnum.setText("已售24680");
		
		return convertView;
	}
	
	public static class ViewHolder {
		public ImageView home_iv_img;
		public ImageView home_iv_nobooking_img;
		public TextView home_tv_title_goodsName;
		public TextView home_tv_num;
		public TextView home_tv_goodscontent;
		public TextView home_goodsprice;//商品价格
		public TextView home_tv_goodsshoprice;  //门市价
		public TextView home_tv_goodsshopnum;  //已售24680
	}

	public void addItem(GoodsInfo goodsInfo){
		datas.add(goodsInfo);
	}
	
}
