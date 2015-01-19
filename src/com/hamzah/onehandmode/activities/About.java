package com.hamzah.onehandmode.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.hamzah.onehandmode.R;

public class About extends OHMActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
	}

	public void twitter(View v) {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW,
				Uri.parse("https://twitter.com/hamzahrmalik"));
		startActivity(browserIntent);
	}

	public void website(View v) {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW,
				Uri.parse("http://hamzahrmalik.web44.net"));
		startActivity(browserIntent);
	}

	public void premium(View v) {
		final String pname = "com.hamzah.onehandmode.premium";
		try {
			startActivity(new Intent(Intent.ACTION_VIEW,
					Uri.parse("market://details?id=" + pname)));
		} catch (ActivityNotFoundException e) {
			startActivity(new Intent(Intent.ACTION_VIEW,
					Uri.parse("http://play.google.com/store/apps/details?id="
							+ pname)));
		}
	}
}
