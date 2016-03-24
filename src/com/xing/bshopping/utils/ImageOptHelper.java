package com.xing.bshopping.utils;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.xing.bshopping.R;

public class ImageOptHelper {

	public static DisplayImageOptions getListViewImgOptions() {
		DisplayImageOptions imgOptions = new DisplayImageOptions.Builder()
				.cacheOnDisc().cacheInMemory()
				.bitmapConfig(Bitmap.Config.RGB_565)
				.showStubImage(R.drawable.review_list_thumbnail_none_b)
				.showImageForEmptyUri(R.drawable.review_list_thumbnail_none_b)
				.showImageOnFail(R.drawable.review_list_thumbnail_none_b).build();
		return imgOptions;
	}

	
}
