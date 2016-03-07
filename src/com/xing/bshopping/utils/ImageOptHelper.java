package com.xing.bshopping.utils;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.xing.bshopping.R;

public class ImageOptHelper {

	public static DisplayImageOptions getListViewImgOptions() {
		DisplayImageOptions imgOptions = new DisplayImageOptions.Builder()
				.cacheOnDisc().cacheInMemory()
				.bitmapConfig(Bitmap.Config.RGB_565)
				.showStubImage(R.drawable.bg_loading_poi_list)
				.showImageForEmptyUri(R.drawable.bg_loading_poi_list)
				.showImageOnFail(R.drawable.bg_loading_poi_list).build();
		return imgOptions;
	}

	
}
