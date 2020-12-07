package ir.ccpro.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SensorRestarterBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("EEEEEEEEE", "Service Stops! Oooooooooooooppppssssss!!!!");
        context.startService(new Intent(context, SensorService.class));
    }
}
