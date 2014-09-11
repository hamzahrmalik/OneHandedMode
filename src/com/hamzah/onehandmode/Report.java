package com.hamzah.onehandmode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class Report extends Activity {
	
	String xposed_log_path = "/data/data/de.robv.android.xposed.installer/log/error.log";
	String dest_path = "";
	
	String OHM_VERSION = "2.0 free";
	
	String log = "ERROR";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);
		
		dest_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/one_hand_mode_error_file.txt";
		
		Log.d("EXT STORAGE", dest_path);
	}
	
	public void generate(View v){
		try {
			File dest_file = new File(dest_path);
			File xposed_log_file = new File(xposed_log_path);
			
			StringBuilder log_sb = new StringBuilder();
			BufferedReader br = new BufferedReader(new FileReader(xposed_log_file));
			String line;
		    while ((line = br.readLine()) != null) {
		        log_sb.append(line);
		        log_sb.append('\n');
		    }
		    br.close();
		    
		    log = log_sb.toString();
			
			FileWriter fw = new FileWriter(dest_file);
			fw.write(OHM_VERSION + "\n\n\n");
			fw.append(log);
			fw.close();
			Toast.makeText(this, "Log file saved to " + dest_path, Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}
}
