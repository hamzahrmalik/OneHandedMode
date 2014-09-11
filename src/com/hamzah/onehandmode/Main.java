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

public class Main implements IXposedHookLoadPackage, IXposedHookZygoteInit {

	XSharedPreferences pref_apps;
	XSharedPreferences pref_NC;

	int left_margin, right_margin, top_margin, bottom_margin;
	boolean leaveActionbar;

	@Override
	public void handleLoadPackage(final LoadPackageParam lpparam)
			throws Throwable {
		if (lpparam.packageName.equals("com.android.systemui")) {
			findAndHookMethod("com.android.systemui.statusbar.phone.PanelView",
					lpparam.classLoader, "setExpandedFraction", float.class,
					new XC_MethodHook() {
						@Override
						protected void afterHookedMethod(MethodHookParam param)
								throws Throwable {
							pref_NC.reload();
							// get all prefs
							left_margin = pref_NC.getInt(Keys.LEFT_MARGIN, 0);
							right_margin = pref_NC.getInt(Keys.RIGHT_MARGIN, 0);
							top_margin = pref_NC.getInt(Keys.TOP_MARGIN, 0);
							View v = (View) param.thisObject;
							if (pref_NC.getBoolean(Keys.MASTER_SWITCH, false))
								v.setPadding(left_margin, top_margin,
										right_margin, 0);
							else
								v.setPadding(0, 0, 0, 0);
						}
					});
		}
		findAndHookMethod(Activity.class, "onResume", new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(MethodHookParam param)
					throws Throwable {
				// get the activity
				Activity activity = (Activity) param.thisObject;
				// content view
				View rootLayer = activity.getWindow().getDecorView()
						.findViewById(android.R.id.content);
				// get window and actionbar
				Window window = activity.getWindow();
				View v = window.getDecorView();
				int resId = activity.getResources().getIdentifier(
						"action_bar_container", "id", "android");
				// view containing actionbar
				View actionBar = v.findViewById(resId);

				pref_apps.reload();
				if (pref_apps.getBoolean(Keys.MASTER_SWITCH, false)) {
					// get all prefs
					left_margin = pref_apps.getInt(Keys.LEFT_MARGIN, 0);
					right_margin = pref_apps.getInt(Keys.RIGHT_MARGIN, 0);
					top_margin = pref_apps.getInt(Keys.TOP_MARGIN, 0);
					bottom_margin = pref_apps.getInt(Keys.BOTTOM_MARGIN, 0);
					leaveActionbar = pref_apps.getBoolean(Keys.LEAVE_ACTIONBAR,
							false);
					// if theres no actionbar or if they dont want it moved, set
					// padding to root layer
					// otherwise no top padding for rootlayer and instead pad
					// the actionbar
					if (actionBar == null || leaveActionbar)
						rootLayer.setPadding(left_margin, top_margin,
								right_margin, bottom_margin);
					else
						rootLayer.setPadding(left_margin, 0, right_margin,
								bottom_margin);
					// pad actionbar
					if (!leaveActionbar && actionBar != null)
						actionBar.setPadding(left_margin, top_margin,
								right_margin, 0);
				} else {
					// if disabled, set padding to zero, this means you dont
					// have to restart app to set it but has bad side effects on
					// apps which dont use 0 for default
					rootLayer.setPadding(0, 0, 0, 0);
					if (actionBar != null)
						actionBar.setPadding(0, 0, 0, 0);
				}
			}
		});
	}

	@Override
	public void initZygote(StartupParam startupParam) throws Throwable {
		pref_apps = new XSharedPreferences("com.hamzah.onehandmode", "apps");
		pref_NC = new XSharedPreferences("com.hamzah.onehandmode", "NC");
	}
}