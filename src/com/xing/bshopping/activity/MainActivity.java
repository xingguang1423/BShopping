package com.xing.bshopping.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.xing.bshopping.R;
import com.xing.bshopping.entity.CustomInfo;
import com.xing.bshopping.fragment.FragmentController;
import com.xing.bshopping.utils.ToastUtils;

public class MainActivity extends FragmentActivity implements
		OnCheckedChangeListener {

	private RadioGroup rg_tab;
	private FragmentController controller;
	public CustomInfo customInfo;
	/**
	 * 经纬度
	 */
	private double latitude = 0.0;
	private double longitude = 0.0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
		// setTranslucentStatus(true);
		// SystemBarTintManager tintManager = new SystemBarTintManager(this);
		// tintManager.setStatusBarTintEnabled(true);
		// tintManager.setStatusBarTintResource(R.color.bshoppingcolor);//通知栏所需颜色
		// }

		setContentView(R.layout.activity_main);

		controller = FragmentController.getInstance(MainActivity.this,
				R.id.fl_content);
		controller.showFragment(0);

		initView();

		/**
		 * 定位
		 */
		final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Location location = locationManager
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);

			if (location != null) {
				latitude = location.getLatitude();
				longitude = location.getLongitude();
			}

		} else {
			LocationListener locationListener = new LocationListener() {

				// Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
				@Override
				public void onStatusChanged(String provider, int status,
						Bundle extras) {

				}

				// Provider被enable时触发此函数，比如GPS被打开
				@Override
				public void onProviderEnabled(String provider) {

				}

				// Provider被disable时触发此函数，比如GPS被关闭
				@Override
				public void onProviderDisabled(String provider) {

				}

				// 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
				@Override
				public void onLocationChanged(Location location) {
					if (location != null) {
						Log.e("Map",
								"Location changed : Lat: "
										+ location.getLatitude() + " Lng: "
										+ location.getLongitude());
					}
				}
			};
			locationManager
					.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
							1000, 0, locationListener);
			Location location = locationManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			if (location != null) {
				latitude = location.getLatitude(); // 经度
				longitude = location.getLongitude(); // 纬度
			}
		}

		RequestQueue requestQueue = Volley.newRequestQueue(this);
		String JSONDateUrl = "http://api.map.baidu.com/geocoder?output=json&location="
				+ latitude + "," + longitude + "&key=MXmG5r388N2uKLi70QxSDcuY";
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET, JSONDateUrl, null,
				new Response.Listener<JSONObject>() {
					public void onResponse(JSONObject response) {
						
						try {
							JSONObject result = response
									.getJSONObject("result");
							
							JSONObject location = result
									.getJSONObject("location");
							String formatted_address = result
									.getString("formatted_address");
							String business = result.getString("business");
							JSONObject addressComponent = result
									.getJSONObject("addressComponent");
							String cityCode = result.getString("cityCode");

							final String city = addressComponent.getString("city");
							String direction = addressComponent
									.getString("direction");
							String distance = addressComponent
									.getString("distance");
							String district = addressComponent
									.getString("district");
							String province = addressComponent
									.getString("province");
							String street = addressComponent
									.getString("street");
							String street_number = addressComponent
									.getString("street_number");
							
							
							Toast.makeText(MainActivity.this, "你当前的位置是:"+city,1).show();
							
						} catch (JSONException e) {
							e.printStackTrace();
						}
						
					}
				}, new Response.ErrorListener() {
					public void onErrorResponse(
							com.android.volley.VolleyError arg0) {
						ToastUtils.showToast(MainActivity.this,
								"对不起，网络出现问题".toString(), 1);
					}
				});
		requestQueue.add(jsonObjectRequest);
		
		// 得到登陆的用户信息
		getUserCostomInfo();
	}

	private void getUserCostomInfo() {

		customInfo = (CustomInfo) getIntent().getSerializableExtra(
				"userCustomInfo");
		if (customInfo != null) {
			// ToastUtils.showToast(this,"1111"+customInfo.toString(), 1);
			System.out.println("用户信息：----->" + customInfo.toString());

		} else {
			// ToastUtils.showToast(this,"用户请登陆", 1);
		}

	}

	// @TargetApi(19)
	// private void setTranslucentStatus(boolean on) {
	// Window win = getWindow();
	// WindowManager.LayoutParams winParams = win.getAttributes();
	// final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
	// if (on) {
	// winParams.flags |= bits;
	// } else {
	// winParams.flags &= ~bits;
	// }
	// win.setAttributes(winParams);
	// }

	private void initView() {
		rg_tab = (RadioGroup) findViewById(R.id.rg_tab);

		rg_tab.setOnCheckedChangeListener(this);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.rb_home:
			controller.showFragment(0);
			break;
		case R.id.rb_poi:
			controller.showFragment(1);
			break;
		case R.id.rb_user:
			controller.showFragment(2);
			break;
		case R.id.rb_more:
			controller.showFragment(3);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		FragmentController.onDestroy();
	}

}
