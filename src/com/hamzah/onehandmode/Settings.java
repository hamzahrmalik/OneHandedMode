package com.hamzah.onehandmode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

public class Settings extends Activity {
	
	CheckBox CB_show_notification, CB_show_toast, CB_transparent_notification;
	Spinner spinner_toggle_option;
	boolean show_toggle_toast = true;
	boolean show_notification = true;
	boolean transparent_notification = false;
	int toggle_option;
	
	SharedPreferences pref;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		pref = getSharedPreferences("pref", Context.MODE_WORLD_READABLE);
		
    	CB_show_notification = (CheckBox) findViewById(R.id.show_notification);
    	CB_show_toast= (CheckBox) findViewById(R.id.show_toggle_toast);
    	CB_transparent_notification = (CheckBox) findViewById(R.id.transparent_notification);
    	
    	spinner_toggle_option = (Spinner) findViewById(R.id.toggle_activity_option);
    	
		loadPreviousSettings();
	}
	
	public void apply(View view){
    	show_notification = CB_show_notification.isChecked();
    	show_toggle_toast = CB_show_toast.isChecked();
    	transparent_notification = CB_transparent_notification.isChecked();
    	toggle_option = spinner_toggle_option.getSelectedItemPosition();
    	
    	Editor editor = pref.edit();
    	editor.putBoolean(Keys.SHOW_NOTIFICATION, show_notification);
    	editor.putBoolean(Keys.SHOW_TOGGLE_TOAST, show_toggle_toast);
    	editor.putBoolean(Keys.TRANSPARENT_NOTIFICATION, transparent_notification);
    	editor.putInt(Keys.DEFAULT_TOGGLE, toggle_option);
    	editor.apply();
    	
    	Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
    	
    	initNotification(this);
    	
    	finish();
	}
	
	public void loadPreviousSettings(){
     	CB_show_notification.setChecked(pref.getBoolean(Keys.SHOW_NOTIFICATION, true));
    	CB_show_toast.setChecked(pref.getBoolean(Keys.SHOW_TOGGLE_TOAST, true));
    	CB_transparent_notification.setChecked(pref.getBoolean(Keys.TRANSPARENT_NOTIFICATION, false));
    	spinner_toggle_option.setSelection(pref.getInt(Keys.DEFAULT_TOGGLE, 2));
	}
	
    public static void initNotification(Context context){		
    	Intent service = new Intent(context, NotifService.class);
        context.stopService(service);
        context.startService(service);
	}
}