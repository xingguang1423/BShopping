package com.xing.bshopping.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xing.bshopping.R;
import com.xing.bshopping.R.id;
import com.xing.bshopping.R.layout;
import com.xing.bshopping.entity.BusinessInfo;

public class PoiDailActivity extends Activity {

	// 返回键
	private ImageView titlebar_iv_poidail_left;

	private ImageView iv_poidail_bgimg;
	private ImageView iv_poidail_bPhone;
	private TextView tv_poidail_bName;
	private TextView tv_poidail_bType;
	private TextView tv_poidail_bAddress;
	private TextView poidail_content_bContentInfo;

	private ImageLoader imageLoader;

	BusinessInfo businessInfo = null;


	// 分享View
	private ImageView titlebar_iv2_poidail_right;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_poidail);

		businessInfo = (BusinessInfo) getIntent().getSerializableExtra(
				"businessInfo");
		imageLoader = ImageLoader.getInstance();

		initView();
	}

	private void initView() {

		titlebar_iv_poidail_left = (ImageView) findViewById(R.id.titlebar_iv_poidail_left);
		titlebar_iv_poidail_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		iv_poidail_bgimg = (ImageView) findViewById(R.id.iv_poidail_bgimg);
		iv_poidail_bPhone = (ImageView) findViewById(R.id.iv_poidail_bPhone);
		tv_poidail_bName = (TextView) findViewById(R.id.tv_poidail_bName);
		tv_poidail_bType = (TextView) findViewById(R.id.tv_poidail_bType);
		tv_poidail_bAddress = (TextView) findViewById(R.id.tv_poidail_bAddress);
		poidail_content_bContentInfo = (TextView) findViewById(R.id.poidail_content_bContentInfo);
		// 分享View
		titlebar_iv2_poidail_right = (ImageView) findViewById(R.id.titlebar_iv2_poidail_right);

		// 分享按钮
		titlebar_iv2_poidail_right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent shareIntent = new Intent();
				shareIntent.setAction(Intent.ACTION_SEND);
				shareIntent.putExtra(Intent.EXTRA_TEXT,
						businessInfo.getbName());
				shareIntent.setType("text/plain");

				Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(
						getContentResolver(),
						takeScreenShot(PoiDailActivity.this), null, null));
				shareIntent.setAction(Intent.ACTION_SEND);
				shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
				shareIntent.setType("image/*");

				// 设置分享列表的标题，并且每次都显示分享列表
				startActivity(Intent.createChooser(shareIntent, "分享到"));
			}
		});
		
		imageLoader.displayImage(businessInfo.getbImgUrl(),
				iv_poidail_bgimg);
		
		//电话号码的操作 
		//iv_poidail_bPhone
		iv_poidail_bPhone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction("android.intent.action.CALL");
				intent.setData(Uri.parse("tel:"+businessInfo.getbPhone()));
				startActivity(intent);
			}
		});
		
		tv_poidail_bName.setText(businessInfo.getbName());
		tv_poidail_bType.setText(businessInfo.getbType());
		tv_poidail_bAddress.setText(businessInfo.getbAddress());
		poidail_content_bContentInfo.setText(businessInfo.getbContentInfo());
		
		
		
	}
	

	
	private static Bitmap takeScreenShot(Activity activity) {
        // View是你需要截图的View
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();

        // 获取状态栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        Log.i("TAG", "" + statusBarHeight);

        // 获取屏幕长和高
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay()
                .getHeight();
        // 去掉标题栏
        // Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }

	
}
