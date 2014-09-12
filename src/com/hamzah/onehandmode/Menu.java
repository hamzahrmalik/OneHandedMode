package com.hamzah.onehandmode;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class Menu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		Settings.initNotification(this);
	}
	
	public void apps(View v){
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
	}
	
	public void NC(View v){
		Intent i = new Intent(this, NotificationCentre.class);
		startActivity(i);
	}
	
	public void help(View v){
		Intent i = new Intent(this, Help.class);
		startActivity(i);
	}
	
	public void about(View v){
		Intent i = new Intent(this, About.class);
		startActivity(i);
	}
	
	public void report(View v){
		Intent i = new Intent(this, Report.class);
		startActivity(i);
	}
	
	public void XDA(View v){
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://forum.xda-developers.com/xposed/modules/mod-one-handed-mode-devices-t2735815/post52293457"));
		startActivity(browserIntent);
	}
	
	public void settings(View v){
		Intent i = new Intent(this, Settings.class);
		startActivity(i);
	}
}
