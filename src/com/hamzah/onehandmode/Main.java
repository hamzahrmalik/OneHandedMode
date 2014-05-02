package com.hamzah.onehandmode;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import android.app.ActionBar;
import android.app.Activity;
import android.view.View;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
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
				
				Activity activity = (Activity) param.thisObject;
				View rootLayer = activity.getWindow().getDecorView()
						.findViewById(android.R.id.content);
				if(pref.getBoolean(Keys.MASTER_SWITCH, false))
				rootLayer.setPadding(left_margin, top_margin, right_margin, bottom_margin);
			}
		});
		}
	
	@Override
	public void initZygote(StartupParam startupParam) throws Throwable {
		pref = new XSharedPreferences("com.hamzah.onehandmode", "pref");
	}
}