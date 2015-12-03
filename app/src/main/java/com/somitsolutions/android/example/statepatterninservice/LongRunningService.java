package com.somitsolutions.android.example.statepatterninservice;

/**
 * Created by som on 3/12/15.
 */
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;

public class LongRunningService extends Service {

    private static final int CONNECTING = 1;
    private static final int CONNECTED = 2;
    private static final int DOWNLOADSTARTED = 3;
    private static final int DOWNLOADFINISHED = 4;

    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;   // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {


            Messenger messenger= MainActivity.getMainActivity().mMessenger;

            try {


                messenger.send(Message.obtain(null, CONNECTING, "Connecting"));
                // Normally we would do some work here, like download a file.
                // For our sample, we just sleep for 10 seconds.
                Thread.sleep(1000);
                // Normally we would do some work here, like download a file.
                // For our sample, we just sleep for 10 seconds.

                messenger.send(Message.obtain(null, CONNECTED, "Connected"));
                // Normally we would do some work here, like download a file.
                // For our sample, we just sleep for 10 seconds.
                Thread.sleep(1000);


                messenger.send(Message.obtain(null, DOWNLOADSTARTED, "Download Started"));
                // Normally we would do some work here, like download a file.
                // For our sample, we just sleep for 10 seconds.
                Thread.sleep(1000);


                messenger.send(Message.obtain(null, DOWNLOADFINISHED, "Download Finished"));


            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
            stopSelf(msg.arg1);
        }
    }

    @Override
    public void onCreate() {
        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Thread.NORM_PRIORITY);
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "download service starting", Toast.LENGTH_SHORT).show();

        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }
}

