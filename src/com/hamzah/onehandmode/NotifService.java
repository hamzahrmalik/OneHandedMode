package com.hamzah.onehandmode;

import com.hamzah.onehandmode.activities.Menu;
import com.hamzah.onehandmode.activities.NotificationTap;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;

public class NotifService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		
		return null;
	}
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
    public void onCreate() {
		Context c = getApplicationContext();
		initNotification(c);
	}
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@SuppressWarnings("deprecation")
    public void initNotification(Context c){
		Intent toggle_apps = new Intent(c, NotificationTap.class);
        toggle_apps.putExtra(Keys.INTENT_DATA, Keys.INTENT_TOGGLE_APPS);
        PendingIntent Ptoggle_apps = PendingIntent.getActivity(c, 0, toggle_apps, PendingIntent.FLAG_UPDATE_CURRENT);
		
        Intent toggle_NC = new Intent(c, NotificationTap.class);
        toggle_NC.putExtra(Keys.INTENT_DATA, Keys.INTENT_TOGGLE_NC);
        PendingIntent Ptoggle_NC = PendingIntent.getActivity(c, 1, toggle_NC, PendingIntent.FLAG_UPDATE_CURRENT);
        
        Intent toggle_both = new Intent(c, NotificationTap.class);
        toggle_both.putExtra(Keys.INTENT_DATA, Keys.INTENT_TOGGLE_BOTH);
        PendingIntent Ptoggle_both = PendingIntent.getActivity(c, 2, toggle_both, PendingIntent.FLAG_UPDATE_CURRENT);
        
       Intent mainActivityIntent = new Intent(c, Menu.class);
       PendingIntent PmainActivityIntent = PendingIntent.getActivity(c, 3, mainActivityIntent, 0);
        
		SharedPreferences pref = getSharedPreferences("pref", Context.MODE_WORLD_READABLE);
        int notif_icon = R.drawable.notification;
        if(pref.getBoolean(Keys.TRANSPARENT_NOTIFICATION, false))
        	notif_icon = R.drawable.blank;
        
        Notification mNotification;
        
        if(Build.VERSION.SDK_INT>=16){
		mNotification = new Notification.Builder(c)
        .setContentTitle("One-Handed Mode")
        .setContentText("Tap here for settings page, or select a toggle below")
        .setSmallIcon(notif_icon)
        .setContentIntent(PmainActivityIntent)
        .setOngoing(true)
        .addAction(0, "Apps", Ptoggle_apps)
        .addAction(0, "NC", Ptoggle_NC)
        .addAction(0, "Both", Ptoggle_both)
        .build();
		}
        else{
        	mNotification = new Notification.Builder(c)
            .setContentTitle("One-Handed Mode")
            .setContentText("Touch to toggle")
            .setSmallIcon(notif_icon)
            .setContentIntent(Ptoggle_both)
            .setOngoing(true)
            .getNotification();
        }
    	
    	NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    	notificationManager.cancelAll();
    	if(pref.getBoolean(Keys.SHOW_NOTIFICATION, true))
    		notificationManager.notify(0, mNotification);
        
	}

}
