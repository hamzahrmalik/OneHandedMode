package com.hamzah.onehandmode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootStartupReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

           // Start Service On Boot Start Up
           Intent service = new Intent(context, NotifService.class);
           context.startService(service);
    }
    
}