package ir.ccpro.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.WebView;

public class App {
    Intent mServiceIntent;
    private SensorService mSensorService;

    Context ctx;

    public Context getCtx() {
        return ctx;
    }
    public App(Context context){
        ctx = context;
        mSensorService = new SensorService(getCtx());
        mServiceIntent = new Intent(getCtx(), mSensorService.getClass());
        if (!isMyServiceRunning(mSensorService.getClass(),ctx)) {
           ctx.startService(mServiceIntent);
        }
        init(ctx);
    }


    private static boolean isMyServiceRunning(Class<?> serviceClass,Context context) {
        ActivityManager manager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("EEEEEEEEEE", "isMyServiceRunning?" + true+"");
                return true;
            }
        }
        Log.i ("EEEEEEEEEEE", "isMyServiceRunning?"+false+"");
        return false;
    }


    public static void init(Context context){
          Intent broadcastIntent = new Intent(context, SensorRestarterBroadcastReceiver.class);
          context.sendBroadcast(broadcastIntent);
    }

    //@Override
//    protected void onDestroy() {
//        ctx.stopService(mServiceIntent);
//        Log.i("MAINACT", "onDestroy!");
//       /// super.onDestroy();
//
//    }
}
