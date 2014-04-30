package com.hamzah.onehandmode;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends Activity {
	//the widgets, ya know
	public static Switch switch_master_switch;
	public static EditText ET_left_margin;
	public static EditText ET_right_margin;
	public static EditText ET_top_margin;
	public static EditText ET_bottom_margin;
	
	boolean master_switch = false;
	int left_margin = 0;
	int right_margin = 0;
	int top_margin = 0;
	int bottom_margin = 0;
	//used to make sure the user doesnt do something stupid
	int screen_width = 0;
	int screen_height = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        if(getIntent().getExtras()!=null){
            Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show();
        }
        
        switch_master_switch= (Switch) findViewById(R.id.master_switch);
    	ET_left_margin= (EditText) findViewById(R.id.left_margin);
    	ET_right_margin= (EditText) findViewById(R.id.right_margin);
    	ET_top_margin= (EditText) findViewById(R.id.top_margin);
    	ET_bottom_margin= (EditText) findViewById(R.id.bottom_margin);
    	
    	Display display = getWindowManager().getDefaultDisplay();
    	Point size = new Point();
    	display.getSize(size);
    	screen_width = size.x;
    	screen_height = size.y;
    	Log.d("SCREEN SIZE", screen_width  + ", " + screen_height);
    	loadPreviousSettings();
    	
    	Intent intent = new Intent(this, NotificationTap.class);
        PendingIntent pIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
        
    	@SuppressWarnings("deprecation")
		Notification mNotification = new Notification.Builder(this)
        .setContentTitle("One-Handed Mode")
        .setContentText("Touch to toggle")
        .setSmallIcon(R.drawable.ic_launcher)
        .setContentIntent(pIntent)
        .setOngoing(true)
        .getNotification();
    	
    	NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    	notificationManager.notify(0, mNotification);
    }
    
    @SuppressWarnings("deprecation") //cos of world readable
	public void apply(View v){
    	// read raw values from the input widgets
    	master_switch = switch_master_switch.isChecked();
    	left_margin = Integer.parseInt(ET_left_margin.getText().toString());
    	right_margin = Integer.parseInt(ET_right_margin.getText().toString());
    	top_margin = Integer.parseInt(ET_top_margin.getText().toString());
    	bottom_margin = Integer.parseInt(ET_bottom_margin.getText().toString());
    	
    	//save the values
    	SharedPreferences pref = getSharedPreferences("pref", Context.MODE_WORLD_READABLE);
    	Editor editor = pref.edit();
    	editor.putBoolean(Keys.MASTER_SWITCH, master_switch);
    	editor.putInt(Keys.LEFT_MARGIN, left_margin);
    	editor.putInt(Keys.RIGHT_MARGIN, right_margin);
    	editor.putInt(Keys.TOP_MARGIN, top_margin);
    	editor.putInt(Keys.BOTTOM_MARGIN, bottom_margin);
    	editor.apply();
    	
    	Toast.makeText(this, "Changes applied!", Toast.LENGTH_SHORT).show();
    	if(left_margin-right_margin<screen_width*0.6||top_margin-bottom_margin<screen_height*0.6)
    		Toast.makeText(this, "Warning, the view area may be too small!", Toast.LENGTH_LONG).show();
    	if(left_margin>screen_width||top_margin>screen_height||right_margin<0||bottom_margin<0)
    		Toast.makeText(this, "Your view area goes off the screen!", Toast.LENGTH_LONG).show();
    }
    //get the previous settings from the pref and load them into input widgets
    @SuppressWarnings("deprecation")
	public void loadPreviousSettings(){
    	SharedPreferences pref = getSharedPreferences("pref", Context.MODE_WORLD_READABLE);
    	switch_master_switch.setChecked(pref.getBoolean(Keys.MASTER_SWITCH, true));
    	ET_left_margin.setText(Integer.toString(pref.getInt(Keys.LEFT_MARGIN, 0)));
    	ET_right_margin.setText(Integer.toString(pref.getInt(Keys.RIGHT_MARGIN, 0)));
    	ET_top_margin.setText(Integer.toString(pref.getInt(Keys.TOP_MARGIN, 0)));
    	ET_bottom_margin.setText(Integer.toString(pref.getInt(Keys.BOTTOM_MARGIN, 0)));
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
		case R.id.menu_help:
			openHelp();
			break;
		case R.id.menu_xda:
			openXDA();
			break;
		}
		return true;
    }
    
    public void openHelp(){
    	Intent intent = new Intent(this, Help.class);
		startActivity(intent);
    }
    
    public void openXDA(){
    	Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://forum.xda-developers.com/xposed/modules/mod-one-handed-mode-devices-t2735815/post52293457"));
		startActivity(browserIntent);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
