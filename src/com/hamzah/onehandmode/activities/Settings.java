package com.hamzah.onehandmode.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.hamzah.onehandmode.Keys;
import com.hamzah.onehandmode.NotifService;
import com.hamzah.onehandmode.R;

public class Settings extends OHMActivity {

	CheckBox CB_show_notification, CB_show_toast, CB_transparent_notification,
			CB_show_overlay;
	Spinner spinner_toggle_option, spinner_overlay_colour;
	EditText ET_dpi;

	SharedPreferences pref;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		pref = getSharedPreferences("apps", Context.MODE_WORLD_READABLE);

		CB_show_notification = (CheckBox) findViewById(R.id.show_notification);
		CB_show_toast = (CheckBox) findViewById(R.id.show_toggle_toast);
		CB_transparent_notification = (CheckBox) findViewById(R.id.transparent_notification);
		CB_show_overlay = (CheckBox) findViewById(R.id.show_overlay);

		spinner_toggle_option = (Spinner) findViewById(R.id.toggle_activity_option);
		spinner_overlay_colour = (Spinner) findViewById(R.id.overlay_colour);

		ET_dpi = (EditText) findViewById(R.id.dpi_option);

		loadPreviousSettings();
	}
	
	public void apply(View view){
    	Editor editor = pref.edit();
    	editor.putBoolean(Keys.SHOW_NOTIFICATION, CB_show_notification.isChecked());
    	editor.putBoolean(Keys.SHOW_TOGGLE_TOAST, CB_show_toast.isChecked());
    	editor.putBoolean(Keys.TRANSPARENT_NOTIFICATION, CB_transparent_notification.isChecked());
    	editor.putBoolean(Keys.SHOW_OVERLAY, CB_show_overlay.isChecked());
    	editor.putInt(Keys.DEFAULT_TOGGLE, spinner_toggle_option.getSelectedItemPosition());
		editor.putString(Keys.OVERLAY_COLOR, HexFromID(spinner_overlay_colour.getSelectedItemPosition()));
		editor.putInt(Keys.REDUCE_DPI, Integer.parseInt(ET_dpi.getText().toString()));
    	editor.apply();
    	
    	Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
    	
    	initNotification(this);
    	
    	finish();
	}
	
	// gets the hex code based on position in spinner
		public String HexFromID(int id) {
			String s = null;
			switch (id) {
			case 0:
				s = "#FF0000";
				break;
			case 1:
				s = "#04B404";
				break;
			case 2:
				s = "#0000FF";
				break;
			case 3:
				s = "#2ECCFA";
				break;
			case 4:
				s = "#FF8000";
				break;
			case 5:
				s = "#D7DF01";
				break;
			case 6:
				s = "#9A2EFE";
				break;
			case 7:
				s = "#FFFFFF";
				break;
			case 8:
				s = "#000000";
				break;
			case 9:
				s = "#6E6E6E";
				break;

			}
			return s;
		}
	
	public void loadPreviousSettings(){
     	CB_show_notification.setChecked(pref.getBoolean(Keys.SHOW_NOTIFICATION, true));
    	CB_show_toast.setChecked(pref.getBoolean(Keys.SHOW_TOGGLE_TOAST, true));
    	CB_transparent_notification.setChecked(pref.getBoolean(Keys.TRANSPARENT_NOTIFICATION, false));
    	CB_show_overlay.setChecked(pref.getBoolean(Keys.SHOW_OVERLAY, false));
    	spinner_toggle_option.setSelection(pref.getInt(Keys.DEFAULT_TOGGLE, 2));
    	ET_dpi.setText("" + pref.getInt(Keys.REDUCE_DPI, 0));
	}
	
    public static void initNotification(Context context){		
    	Intent service = new Intent(context, NotifService.class);
        context.stopService(service);
        context.startService(service);
	}
}