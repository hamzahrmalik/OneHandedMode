package com.hamzah.onehandmode;

import com.hamzah.onehandmode.activities.NotificationTap;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class Widget extends AppWidgetProvider{
	
	@Override
	  public void onUpdate(Context c, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		for(int i=0; i<appWidgetIds.length; i++){
			int currentWidgetId = appWidgetIds[i];
			Intent toggle_apps = new Intent(c, NotificationTap.class);
	        toggle_apps.putExtra(Keys.INTENT_DATA, Keys.INTENT_TOGGLE_APPS);
	        PendingIntent Ptoggle_apps = PendingIntent.getActivity(c, 0, toggle_apps, PendingIntent.FLAG_UPDATE_CURRENT);
			
	        Intent toggle_NC = new Intent(c, NotificationTap.class);
	        toggle_NC.putExtra(Keys.INTENT_DATA, Keys.INTENT_TOGGLE_NC);
	        PendingIntent Ptoggle_NC = PendingIntent.getActivity(c, 1, toggle_NC, PendingIntent.FLAG_UPDATE_CURRENT);
	        
	        Intent toggle_both = new Intent(c, NotificationTap.class);
	        toggle_both.putExtra(Keys.INTENT_DATA, Keys.INTENT_TOGGLE_BOTH);
	        PendingIntent Ptoggle_both = PendingIntent.getActivity(c, 2, toggle_both, PendingIntent.FLAG_UPDATE_CURRENT);
			
			
			RemoteViews views = new RemoteViews(c.getPackageName(), R.layout.widget);
			views.setOnClickPendingIntent(R.id.widget_toggle_app, Ptoggle_apps);
			views.setOnClickPendingIntent(R.id.widget_toggle_NC, Ptoggle_NC);
			views.setOnClickPendingIntent(R.id.widget_toggle_both, Ptoggle_both);
			appWidgetManager.updateAppWidget(currentWidgetId,views);
		}
	}

}
