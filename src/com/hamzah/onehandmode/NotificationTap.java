package com.hamzah.onehandmode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.Toast;

public class NotificationTap extends Activity {
	
	String [] toggle_names = {"Apps", "Notification Centre", "Both"};

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_notification_tap);
		
		@SuppressWarnings("deprecation")
		SharedPreferences pref = getSharedPreferences("pref", Context.MODE_WORLD_READABLE);
		
		int id = getIntent().getIntExtra(Keys.INTENT_DATA, pref.getInt(Keys.DEFAULT_TOGGLE, 2));
		toggle(id);
		if(pref.getBoolean(Keys.SHOW_TOGGLE_TOAST, true))
			Toast.makeText(this, "Toggled One-Hand Mode for " + toggle_names[id], Toast.LENGTH_SHORT).show();
		finish();
	}
	
	public void toggle(int id){
		if(id==Keys.INTENT_TOGGLE_APPS)
			toggleApps();
		else if(id==Keys.INTENT_TOGGLE_NC)
			toggleNC();
		else if(id==Keys.INTENT_TOGGLE_BOTH)
			toggleBoth();
	}
	
	public void toggleApps(){
		@SuppressWarnings("deprecation")
		SharedPreferences pref = getSharedPreferences("apps", Context.MODE_WORLD_READABLE);
		Editor editor = pref.edit();
		boolean on = pref.getBoolean(Keys.MASTER_SWITCH, false);
		editor.putBoolean(Keys.MASTER_SWITCH, !on);
		editor.apply();
	}
	
	public void toggleNC(){
		@SuppressWarnings("deprecation")
		SharedPreferences pref = getSharedPreferences("NC", Context.MODE_WORLD_READABLE);
		Editor editor = pref.edit();
		editor.putBoolean(Keys.MASTER_SWITCH, !pref.getBoolean(Keys.MASTER_SWITCH, false));
		editor.apply();
	}
	
	public void toggleBoth(){
		toggleApps();
		toggleNC();
	}
}
