package com.example.afinal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;

public class Wifi_Reciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if(isWifiModeOn(context.getApplicationContext()))
        {
            Toast.makeText(context, "Wifi Mode is on", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Wifi Mode is off", Toast.LENGTH_SHORT).show();
        }
    }
    private static boolean isWifiModeOn(Context context)
    {
        return Settings.System.getInt(context.getContentResolver(),Settings.Global.WIFI_ON,0)!= 0;
    }
}
