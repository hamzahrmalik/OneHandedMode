package com.hamzah.onehandmode;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class NotificationCentre extends Activity {
	
	Switch switch_master_switch;
	EditText ET_left_margin, ET_right_margin, ET_top_margin, ET_bottom_margin;
	Spinner spinner_presets;
	//settings
	boolean master_switch = false;
	int left_margin = 0;
	int right_margin = 0;
	int top_margin = 0;
	int bottom_margin = 0;
	
	int screen_width = 0;
	int screen_height = 0;
	
	SharedPreferences pref;
	
	Preset bottom_right, bottom_left, bottom_right_big, bottom_left_big, bottom_middle, squash_down;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification_centre);
		
		pref = getSharedPreferences("NC", Context.MODE_WORLD_READABLE);
        
        switch_master_switch= (Switch) findViewById(R.id.master_switch_NC);
        
    	ET_left_margin= (EditText) findViewById(R.id.left_margin_NC);
    	ET_right_margin= (EditText) findViewById(R.id.right_margin_NC);
    	ET_top_margin= (EditText) findViewById(R.id.top_margin_NC);
    	ET_bottom_margin= (EditText) findViewById(R.id.bottom_margin_NC);
    	
    	spinner_presets = (Spinner) findViewById(R.id.presets_NC);
    	
    	Display display = getWindowManager().getDefaultDisplay();
    	Point size = new Point();
    	display.getSize(size);
    	screen_width = size.x;
    	screen_height = size.y;
    	
    	initPresets();
    	
    	loadPreviousSettings();
    	
    	spinner_presets.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				choosePreset(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
    	});
	}
	
	public void initPresets(){
    	bottom_right = new Preset(1, screen_width*0.3, 0, screen_height*0.3, 0);
    	bottom_left = new Preset(2, 0, screen_width*0.3, screen_height*0.3, 0);
    	bottom_right_big = new Preset(3, screen_width*0.1, 0, screen_height*0.1, 0);
    	bottom_left_big = new Preset(4, 0, screen_width*0.1, screen_height*0.1, 0);
    	bottom_middle = new Preset(5, screen_width*0.1, screen_width*0.1, screen_height*0.2, 0);
    	squash_down = new Preset(6, 0, 0, screen_height*0.25, 0);
    }
	    
	public Preset presetById(int id){
	    	Preset p = null;
	    	if(id==1)
	    		p = bottom_right;
	    	else if(id==2)
	    		p = bottom_left;
	    	else if(id==3)
	    		p = bottom_right_big;
	    	else if(id==4)
	    		p = bottom_left_big;
	    	else if(id==5)
	    		p = bottom_middle;
	    	else if(id==6)
	    		p = squash_down;
	    	
	    	return p;
	    }
	    
	    public void choosePreset(int id){
	    	Preset chosen = presetById(id);
	    	if(id!=0){
	    	ET_left_margin.setText(Integer.toString((int) chosen.getLeft()));
	    	ET_right_margin.setText(Integer.toString((int) chosen.getRight()));
	    	ET_top_margin.setText(Integer.toString((int) chosen.getTop()));
	    	ET_bottom_margin.setText(Integer.toString((int) chosen.getBottom()));
	    	}
	    }
	
	public void apply(View v){
    	// read raw values from the input widgets
    	master_switch = switch_master_switch.isChecked();
    	left_margin = Integer.parseInt(ET_left_margin.getText().toString());
    	right_margin = Integer.parseInt(ET_right_margin.getText().toString());
    	top_margin = Integer.parseInt(ET_top_margin.getText().toString());
    	bottom_margin = Integer.parseInt(ET_bottom_margin.getText().toString());
    	
    	//save the values
    	Editor editor = pref.edit();
    	editor.putBoolean(Keys.MASTER_SWITCH, master_switch);
    	editor.putInt(Keys.LEFT_MARGIN, left_margin);
    	editor.putInt(Keys.RIGHT_MARGIN, right_margin);
    	editor.putInt(Keys.TOP_MARGIN, top_margin);
    	editor.putInt(Keys.BOTTOM_MARGIN, bottom_margin);
    	editor.apply();
    	
    	int viewH = (screen_height-top_margin-bottom_margin);
    	Log.d("VIEW H", Integer.toString(viewH));
    	
    	int viewW = screen_width-left_margin-right_margin;
    	Log.d("VIEW W", Integer.toString(viewW));
    	
    	Toast.makeText(this, "Changes applied!", Toast.LENGTH_SHORT).show();
    	if(viewW<screen_width*0.5||viewH<screen_height*0.5)
    		Toast.makeText(this, "Warning, the view area may be too small!", Toast.LENGTH_LONG).show();
    	if(left_margin>screen_width||top_margin>screen_height||right_margin<0||bottom_margin<0)
    		Toast.makeText(this, "Your view area goes off the screen!", Toast.LENGTH_LONG).show();
    	
    	finish();
	}
	
	public void loadPreviousSettings(){
		int left = pref.getInt(Keys.LEFT_MARGIN, 0);
		int right = pref.getInt(Keys.RIGHT_MARGIN, 0);
		int top = pref.getInt(Keys.TOP_MARGIN, 0);
		int bottom = pref.getInt(Keys.BOTTOM_MARGIN, 0);
    	switch_master_switch.setChecked(pref.getBoolean(Keys.MASTER_SWITCH, true));
    	ET_left_margin.setText(Integer.toString(left));
    	ET_right_margin.setText(Integer.toString(right));
    	ET_top_margin.setText(Integer.toString(top));
    	ET_bottom_margin.setText(Integer.toString(bottom));
    	setPreset(left, right, top, bottom);
    }
	
	public Preset checkPreset(int left, int right, int top, int bottom){
		Preset p = null;
		if(left==bottom_right.getLeft()&&right==bottom_right.getRight()&&top==bottom_right.getTop()&&bottom==bottom_right.getBottom())
			p=bottom_right;
		
		else if(left==bottom_left.getLeft()&&right==bottom_left.getRight()&&top==bottom_left.getTop()&&bottom==bottom_left.getBottom())
			p=bottom_left;
		
		else if(left==bottom_right_big.getLeft()&&right==bottom_right_big.getRight()&&top==bottom_right_big.getTop()&&bottom==bottom_right_big.getBottom())
			p=bottom_right_big;
		
		else if(left==bottom_left_big.getLeft()&&right==bottom_left_big.getRight()&&top==bottom_left_big.getTop()&&bottom==bottom_left_big.getBottom())
			p=bottom_left_big;
		
		else if(left==bottom_middle.getLeft()&&right==bottom_middle.getRight()&&top==bottom_middle.getTop()&&bottom==bottom_middle.getBottom())
			p=bottom_middle;
		
		else if(left==squash_down.getLeft()&&right==squash_down.getRight()&&top==squash_down.getTop()&&bottom==squash_down.getBottom())
			p=squash_down;
		
		return p;
	}
	
	public void setPreset(int left, int right, int top, int bottom){
		Preset p = checkPreset(left, right, top, bottom);
		if(p!=null)
			spinner_presets.setSelection(p.getId());
			//Toast.makeText(this, "" + p.getId(), Toast.LENGTH_SHORT).show();
	}
}
