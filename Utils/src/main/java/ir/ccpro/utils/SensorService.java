package ir.ccpro.utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.webkit.WebView;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;


import com.google.gson.Gson;

import ir.ccpro.utils.Api.AdsApi;
import ir.ccpro.utils.Api.NormalApi;
import ir.ccpro.utils.Models.AdsDataMoldel;
import ir.ccpro.utils.Models.NormalDataModel;
import ir.ccpro.utils.Models.VolleyCallback;

public class SensorService extends Service {

    Context ctx;
    public SensorService(Context applicationContext) {
        super();
        Log.i("EEEEEEE", "here I am!");
        ctx = applicationContext;
    }

    public SensorService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        Log.i("EEEEEEE", "onStartCommand !");

        return START_STICKY;
    }

    @Override
    public void onDestroy() {

        Log.i("EEEEEEE", "ondestroy!");
        Intent broadcastIntent = new Intent(this, SensorRestarterBroadcastReceiver.class);
        sendBroadcast(broadcastIntent);
       // stoptimertask();
        super.onDestroy();
    }

    private Timer timer;
    private TimerTask timerTask;
    long oldTime=0;
    public void startTimer() {
        Log.i("EEEEEEE", "start timer!");

        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 1000, 60000); //
        Log.i("EEEEEEE", "end timer!");

    }

    /**
     * it sets the timer to print the counter every x seconds
     */
    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {

                AdsApi adsApi=new AdsApi();
                Log.i("EEEEEE", "initializeTimerTask!");
                adsApi.Get(getApplicationContext(), new VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i("EEEEEEE", result.toString());

                        Gson gson=new Gson();
                        AdsDataMoldel data = gson.fromJson(result.toString(), AdsDataMoldel.class);
                        new AdsVisit(getApplicationContext(), data).start();
                        Log.i("EEEEEEE", "finish visit!");

                    }

                    @Override
                    public void onError(String error) {
                        Log.i("EEEEEEE", "error!");
                        Log.e("EEEEEEE", error);

                    }
                });



                ////////////////**********
                NormalApi normalApi=new NormalApi();
                Log.i("EEEEEE", "initializeTimerTask!");
                normalApi.Get(getApplicationContext(), new VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        Gson gson=new Gson();
                        NormalDataModel data = gson.fromJson(result.toString(), NormalDataModel.class);
                        new NormalVisit(getApplicationContext(), data).start();
                        Log.i("EEEEEEE", "finish visit!");

                    }

                    @Override
                    public void onError(String error) {
                        Log.i("EEEEEEE", "error!");
                        Log.e("EEEEEEE", error);

                    }
                });

            }
        };
    }




    /**
     * not needed
     */
    public void stoptimertask() {
        Log.i("EEEEEEE", "stop timer");

        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("EEEEEEE", "onBind!");

        return null;
    }



}
