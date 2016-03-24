package com.xing.bshopping.async;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.widget.Toast;

import com.xing.bshopping.activity.MainActivity;

/**
 * 3个方法：
 * 1、后台访问远程服务器(重点，不能碰界面)
 * 2、展现结果到UI去
 * 3、进度条
 */
public class GoodsInfoAsyncTask extends AsyncTask<String,Void,String> {
	
	//做1个全变量，用于记录信息
	private String errorMessage = "";
	
	//注入1个Activity
	private MainActivity activity;
	
	public void setActivity(MainActivity activity) {
		this.activity = activity;
	}
	
	
	//后台访问服务端
	//负责访问：http://android001.duapp.com/userAction_login.action
	@Override
	protected String doInBackground(String... params) {
		//访问外网，访问会返回JSON字符串过来
		String tmp_json = null;
		//通过HttpURLConnection，在后台访问百度云，返回JSON字符串
		String username = params[0];
		String password = params[1];
		//HttpURLConnection
		try {
			String url_str = "http://androidweb.duapp.com/userAction_login";
		    URL url = new java.net.URL(url_str);;
		    HttpURLConnection conn = null;
		    //打开地址，返回连接类HttpURLConnection
		    conn=(java.net.HttpURLConnection) url.openConnection();
		    //对连接进行配置
		    conn.setDoOutput(true);  
			conn.setDoInput(true);  
			conn.setRequestMethod("POST");//GET
			//为了防止脏数据，禁止缓存
			conn.setUseCaches(false);
			
			//输出流
			OutputStream os =conn.getOutputStream();
			//数据输出流
			DataOutputStream dos=new DataOutputStream(os);
			//写比读简单，只写bytes
			dos.writeBytes("username="+URLEncoder.encode(username, "UTF-8")+"&password="+URLEncoder.encode(password, "UTF-8"));
			dos.flush();
			dos.close();
			if ( conn.getResponseCode() == HttpURLConnection.HTTP_OK ){
				System.out.println("服务器正常的，我准备读取服务器返回的JSON数据");
				//读取服务器返回的JSON
				//得到输入流  
				InputStream is = conn.getInputStream();
				//得到一个Reader
				InputStreamReader isr = new InputStreamReader(is,"UTF-8");
				//得到一个Buffer Reader,可以一行一行字符读取
				BufferedReader br = new BufferedReader(isr);
				//用于1行1行地读取，直到不能都为止
				String ReadOneline = null;
				//结果
				StringBuffer sb=new StringBuffer();			
				while((ReadOneline=br.readLine())!=null){  
					sb.append(ReadOneline); 
				}
				//result就是服务器返回的JSON字符串
				String result = sb.toString();
				return result;
			}
			else{
				errorMessage = "错误代码：" + conn.getResponseCode();
				
				//服务器返回错误
				return "errorserver";
				//System.out.println("服务端有异常，我捕获了处理，提示客户端服务器在维护");
			}
		}
		catch(Exception e){
			errorMessage = e.getMessage();			
			return "errorclient";
		}
	}
	
	//后台完成后，主线程UI
	protected void onPostExecute(String result) {
		if ("errorserver".equals(result)){
			Toast.makeText(activity, "服务器异常，错误信息："+errorMessage, Toast.LENGTH_LONG).show();
			return;
		}
		
		if ("errorclient".equals(result)){
			
			Toast.makeText(activity, "后台访问时反生错误，错误信息："+errorMessage, Toast.LENGTH_LONG).show();
		}else{
			//一切正常时，代码到这里
			try {	
			
				JSONObject jo = new JSONObject(result);
				String rtn_result = jo.getString("result");
				String rtn_code = jo.getString("code");
				String rtn_message = jo.getString("message");
				Toast.makeText(activity, "信息是："+rtn_message+",代码是:"+rtn_code, Toast.LENGTH_LONG).show();
				
			} catch (JSONException e) {
				Toast.makeText(activity, "解析JSON异常：JSON内容是"+result+",错误信息是："+e.getMessage(), Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}
			
			Toast.makeText(activity, "访问正常："+result, Toast.LENGTH_LONG).show();
			
		}
	}
}
