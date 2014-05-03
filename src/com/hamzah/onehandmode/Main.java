package com.hamzah.onehandmode;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import android.app.Activity;
import android.view.View;
import android.view.Window;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Main implements IXposedHookLoadPackage, IXposedHookZygoteInit{
	
	XSharedPreferences pref;
	
	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		findAndHookMethod(Activity.class, "onResume", new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				pref.reload();
				final int left_margin = pref.getInt(Keys.LEFT_MARGIN, 0);
				final int right_margin = pref.getInt(Keys.RIGHT_MARGIN, 0);
				final int top_margin = pref.getInt(Keys.TOP_MARGIN, 0);
				final int bottom_margin = pref.getInt(Keys.BOTTOM_MARGIN, 0);
				final boolean leaveActionbar = pref.getBoolean(Keys.LEAVE_ACTIONBAR, false);
				
				Activity activity = (Activity) param.thisObject;
				View rootLayer = activity.getWindow().getDecorView()
						.findViewById(android.R.id.content);
				
				Window window = activity.getWindow();
			    View v = window.getDecorView();
			    int resId = activity.getResources().getIdentifier("action_bar_container", "id", "android");
			    View actionBar = v.findViewById(resId);			    
			    
				if(pref.getBoolean(Keys.MASTER_SWITCH, false)){
					if(actionBar==null||leaveActionbar)
						rootLayer.setPadding(left_margin, top_margin, right_margin, bottom_margin);
					else
						rootLayer.setPadding(left_margin, 0, right_margin, bottom_margin);
				if(!leaveActionbar)
				actionBar.setPadding(left_margin, top_margin, right_margin, 0);
				}
			}
		});
		}
	
	@Override
	public void initZygote(StartupParam startupParam) throws Throwable {
		pref = new XSharedPreferences("com.hamzah.onehandmode", "pref");
	}
}