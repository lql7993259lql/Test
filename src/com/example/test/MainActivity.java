package com.example.test;

import java.io.File;

import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.test.util.ApkUtils;
import com.example.test.util.BsPatch;
import com.example.test.util.Constants;
import com.example.test.util.DownloadUtils;
import com.jixiang.chat.IChatApi;
import com.weile.h5.Callback;
import com.weile.h5.H5Api;


public class MainActivity extends Activity {
	public static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button chat = (Button)findViewById(R.id.chat);
        Button h5 = (Button)findViewById(R.id.h5);
        chat.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try{
		        	JSONObject jso = new JSONObject();
		            jso.put("channel_id", 1);
		            jso.put("app_id", 1);
		            jso.put("ui", "1");
		            jso.put("client_version", "1");
		            jso.put("user_id", 1);
		            jso.put("region", 1);
		            jso.put("device_code", "1");
		            jso.put("domain", "1");
		            jso.put("room_id", 1);
		            jso.put("game_id", 1);
		            jso.put("url_vc", "1");
		            jso.put("url_login", "1");
		            Log.i(TAG, jso.toString());
		            IChatApi.startService(MainActivity.this, jso.toString());
		        }catch(Exception e){
		        	e.printStackTrace();
		        }
				
			}
		});
        
        
        h5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 try{
				        JSONObject jso = new JSONObject();
				        	 jso.put("app_id", 1);
							 jso.put("channel_id", 1);
							 jso.put("version", "1");
							 jso.put("devicecode", "1");
							 jso.put("region", 1);
							 jso.put("debug", 1);
							 
							 jso.put("type", "1");
							 jso.put("goods", "1");
							 
							 jso.put("userid", 1);
							 jso.put("autobuy", 1);
							 jso.put("ingame", 1);
							 jso.put("roomid", 1);
							 jso.put("domain", "1");
							 H5Api.unAgent(jso.toString(), MainActivity.this, new Callback() {
									
									@Override
									public void getResylt(String error) {
										Log.e(TAG, error);
										
									}
								});
				    }catch(Exception e){
				    	e.printStackTrace();
				    }	
				
			}
		});
        
       
        
        new ApkUpdateTask().execute();

        
    }
    
    
    
    class ApkUpdateTask extends AsyncTask<Void, Void, Boolean>{

		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				//1.下载差分包
				Log.d("jason", "开始下载");
				File patchFile = DownloadUtils.download(Constants.URL_PATCH_DOWNLOAD);
				
				//获取当前应用的apk文件/data/app/app
				String oldfile = ApkUtils.getSourceApkPath(MainActivity.this, getPackageName());
				//2.合并得到最新版本的APK文件
				String newfile = Constants.NEW_APK_PATH;
				String patchfile = patchFile.getAbsolutePath();
				BsPatch.patch(oldfile, newfile, patchfile);
				
				Log.d("jason", "oldfile:"+oldfile);
				Log.d("jason", "newfile:"+newfile);
				Log.d("jason", "patchfile:"+patchfile);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			
			return true;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			//3.安装
			if(result){
				Toast.makeText(MainActivity.this, "您正在进行无流量更新", Toast.LENGTH_SHORT).show();
				ApkUtils.installApk(MainActivity.this, Constants.NEW_APK_PATH);
			}
		}
		
	}
}
