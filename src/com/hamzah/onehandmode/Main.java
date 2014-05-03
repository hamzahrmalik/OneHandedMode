package com.hamzah.onehandmode;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import android.app.Activity;
import android.inputmethodservice.KeyboardView;
import android.view.View;
import android.view.Window;
import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Main implements IXposedHookLoadPackage, IXposedHookZygoteInit, IXposedHookInitPackageResources{
	
	XSharedPreferences pref;
	KeyboardView kv;
	
	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		findAndHookMethod(Activity.class, "onResume", new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				pref.reload();
				//get all prefs
				final int left_margin = pref.getInt(Keys.LEFT_MARGIN, 0);
				final int right_margin = pref.getInt(Keys.RIGHT_MARGIN, 0);
				final int top_margin = pref.getInt(Keys.TOP_MARGIN, 0);
				final int bottom_margin = pref.getInt(Keys.BOTTOM_MARGIN, 0);
				final boolean leaveActionbar = pref.getBoolean(Keys.LEAVE_ACTIONBAR, false);
				//get the activity
				Activity activity = (Activity) param.thisObject;
				View rootLayer = activity.getWindow().getDecorView()
						.findViewById(android.R.id.content);
				//get window and actionbar
				Window window = activity.getWindow();
			    View v = window.getDecorView();
			    int resId = activity.getResources().getIdentifier("action_bar_container", "id", "android");
			    View actionBar = v.findViewById(resId);
			    //if masterswitch is one, enable padding
				if(pref.getBoolean(Keys.MASTER_SWITCH, false)){
					//if theres no actionbar or if they dont want it moved, set padding to root layer
					//otherwise no top padding for rootlayer and instead pad the actionbar
					if(actionBar==null||leaveActionbar)
						rootLayer.setPadding(left_margin, top_margin, right_margin, bottom_margin);
					else
						rootLayer.setPadding(left_margin, 0, right_margin, bottom_margin);
				//pad actionbar
				if(!leaveActionbar&&actionBar!=null)
				actionBar.setPadding(left_margin, top_margin, right_margin, 0);
				}
				else{
					//if disables, set padding to zero, this means you dont have to restart app to set it
					rootLayer.setPadding(0, 0, 0, 0);
					actionBar.setPadding(0, 0, 0, 0);
				}
			}
		});
		}
	
	@Override
	public void initZygote(StartupParam startupParam) throws Throwable {
		pref = new XSharedPreferences("com.hamzah.onehandmode", "pref");
	}

	@Override
	public void handleInitPackageResources(InitPackageResourcesParam resparam)
			throws Throwable {
		//this doesnt work will try for next version
		/*if(resparam.packageName.contains("com.android.inputmethod"))
		resparam.res.hookLayout("com.android.inputmethod.latin", "layout", "input_view", new XC_LayoutInflated() {
	        @Override
	        public void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable {
	           View keyboard = (View) liparam.view.findViewById(
	                    liparam.res.getIdentifier("key_preview_backing", "id", "com.android.inputmethod.latin"));
	         //  keyboard.setPadding(50, 0, 0, 0);
	           keyboard.setLeft(50);
	           
	        }
	    });*/
	}
}