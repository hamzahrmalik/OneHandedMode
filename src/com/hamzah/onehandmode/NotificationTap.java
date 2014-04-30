package com.hamzah.onehandmode;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

public class NotificationTap extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification_tap);
		@SuppressWarnings("deprecation")
		SharedPreferences pref = getSharedPreferences("pref", Context.MODE_WORLD_READABLE);
		Editor edit = pref.edit();
		if(pref.getBoolean(Keys.MASTER_SWITCH, false)){
			Toast.makeText(this, "Disabled One-Hand Mode", Toast.LENGTH_SHORT).show();
    		edit.putBoolean(Keys.MASTER_SWITCH, false);
		}
		else{
			Toast.makeText(this, "Enabled One-Hand Mode", Toast.LENGTH_SHORT).show();
			edit.putBoolean(Keys.MASTER_SWITCH, true);
		}
		edit.apply();
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.notification_tap, menu);
		return true;
	}

}
