package com.hamzah.onehandmode;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;

public class OverlayService extends Service {

	FrameLayout left, right, top, bottom;
	WindowManager wm;

	@Override
	public IBinder onBind(Intent intent) {
		// not used
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate() {
		super.onCreate();

		wm = (WindowManager) getSystemService(WINDOW_SERVICE);

		SharedPreferences pref = this.getApplicationContext()
				.getSharedPreferences("apps", Context.MODE_WORLD_READABLE);
		
		SharedPreferences pref_main = this.getApplicationContext()
				.getSharedPreferences("pref", Context.MODE_WORLD_READABLE);
		if (pref_main.getBoolean(Keys.SHOW_OVERLAY, true)) {

			DisplayMetrics metrics = getApplicationContext().getResources()
					.getDisplayMetrics();
			int screen_width = metrics.widthPixels;
			int screen_height = metrics.heightPixels;

			int color = Color.parseColor(pref_main.getString(Keys.OVERLAY_COLOR, "#000000"));

			left = new FrameLayout(this);
			left.setBackgroundColor(color);

			right = new FrameLayout(this);
			right.setBackgroundColor(color);

			top = new FrameLayout(this);
			top.setBackgroundColor(color);

			bottom = new FrameLayout(this);
			bottom.setBackgroundColor(color);

			LayoutParams params_left = new WindowManager.LayoutParams(0, 0);
			params_left.width = pref.getInt(Keys.LEFT_MARGIN, 0);
			params_left.height = screen_height;
			params_left.gravity = Gravity.TOP | Gravity.LEFT;
			params_left.flags = LayoutParams.FLAG_NOT_FOCUSABLE;

			LayoutParams params_right = new WindowManager.LayoutParams(0, 0);
			params_right.width = pref.getInt(Keys.RIGHT_MARGIN, 0);
			params_right.height = screen_height;
			params_right.gravity = Gravity.TOP | Gravity.RIGHT;
			params_right.flags = LayoutParams.FLAG_NOT_FOCUSABLE;

			LayoutParams params_top = new WindowManager.LayoutParams(0, 0);
			params_top.width = screen_width;
			params_top.height = pref.getInt(Keys.TOP_MARGIN, 0);
			params_top.gravity = Gravity.TOP | Gravity.LEFT;
			params_top.flags = LayoutParams.FLAG_NOT_FOCUSABLE;

			LayoutParams params_bottom = new WindowManager.LayoutParams(0, 0);
			params_bottom.width = screen_width;
			params_bottom.height = pref.getInt(Keys.BOTTOM_MARGIN, 0);
			params_bottom.gravity = Gravity.BOTTOM | Gravity.LEFT;
			params_bottom.flags = LayoutParams.FLAG_NOT_FOCUSABLE;

			wm.addView(left, params_left);
			wm.addView(right, params_right);
			wm.addView(top, params_top);
			wm.addView(bottom, params_bottom);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (left != null)
			wm.removeView(left);
		if (right != null)
			wm.removeView(right);
		if (top != null)
			wm.removeView(top);
		if (bottom != null)
			wm.removeView(bottom);
	}
}
