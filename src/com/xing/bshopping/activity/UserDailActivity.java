package com.xing.bshopping.activity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.xing.bshopping.R;
import com.xing.bshopping.dao.CustomInfoDao;
import com.xing.bshopping.entity.CustomInfo;
import com.xing.bshopping.utils.ImageUtils;

public class UserDailActivity extends Activity {

	private ImageView iv_userdail_back;
	private ImageView iv_userdail_touphoto;
	private TextView tv_userdail_username;
	private CustomInfo customInfo;

	private CustomInfoDao customInfoDao;

	Bitmap myBitmap;
	private byte[] mContent = null;
	ContentResolver resolver = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userdail);

		resolver = getContentResolver();

		customInfoDao = new CustomInfoDao(this);

		iv_userdail_touphoto = (ImageView) findViewById(R.id.iv_userdail_touphoto);
		iv_userdail_back = (ImageView) findViewById(R.id.iv_userdail_back);
		tv_userdail_username = (TextView) findViewById(R.id.tv_userdail_username);

		iv_userdail_touphoto.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ImageUtils.showImagePickDialog(UserDailActivity.this);
			}
		});

		// 取到用户对象
		customInfo = (CustomInfo) getIntent().getSerializableExtra(
				"toUserdailCustomInfo");
		// 用户不为null
		if (customInfo != null) {
//			ToastUtils.showToast(this, "1111" + customInfo.toString(), 1);
			System.out.println("用户信息：----->" + customInfo.toString());

			tv_userdail_username.setText(customInfo.getcName());
			// 用户头像uri在的话，就设置图片
			// Uri imgUri = Uri.parse(customInfo.getcImgUrl());
			// iv_userdail_touphoto.setImageURI(imgUri);

			System.out.println("customInfo.getcImgUrl()---->"
					+ customInfo.getcImgUrl());

			if (customInfo.getcImgUrl() != null) {

				try {

					// 将图片内容解析成字节数组
					mContent = readStream(resolver.openInputStream(Uri
							.parse(customInfo.getcImgUrl())));
					// 将字节数组转换为ImageView可调用的Bitmap对象
					myBitmap = getPicFromBytes(mContent, null);
					// //把得到的图片绑定在控件上显示
					iv_userdail_touphoto.setImageBitmap(myBitmap);

				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

			}

		}

		iv_userdail_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case ImageUtils.REQUEST_CODE_FROM_ALBUM:
			if (resultCode == RESULT_CANCELED) {
				return;
			}

			Uri imageUri = data.getData(); // 目标页面返回的data
			iv_userdail_touphoto.setImageURI(imageUri);

			System.out.println("imageUri:--->" + imageUri.toString());

			customInfo.setcImgUrl(imageUri.toString());
			customInfoDao.updateImageUrl(customInfo);
			System.out.println("imageUri:--->" + imageUri.toString());

			break;
		case ImageUtils.REQUEST_CODE_FROM_CAMERA:

			// 不做拍照操作，直接返回
			if (resultCode == RESULT_CANCELED) {
				// //把URL给删除
				ImageUtils.deleteImageUri(this, ImageUtils.imageUriFromCamera);
			} else {
				Uri imageUriCamera = ImageUtils.imageUriFromCamera;

				iv_userdail_touphoto.setImageURI(imageUriCamera);

				customInfo.setcImgUrl(imageUriCamera.toString());
				customInfoDao.updateImageUrl(customInfo);
				System.out.println("imageUri:--->" + imageUriCamera.toString());
			}

			break;

		default:
			break;
		}
	}

	public static Bitmap getPicFromBytes(byte[] bytes,
			BitmapFactory.Options opts) {
		if (bytes != null)
			if (opts != null)
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
						opts);
			else
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		return null;
	}

	public static byte[] readStream(InputStream inStream) throws Exception {
		byte[] buffer = new byte[1024];
		int len = -1;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inStream.close();
		return data;

	}

}
