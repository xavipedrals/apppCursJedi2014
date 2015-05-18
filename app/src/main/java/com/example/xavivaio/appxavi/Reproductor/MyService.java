package com.example.xavivaio.appxavi.Reproductor;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class MyService extends Service {


    MediaPlayer mediaPlayer;
    // This is the object that receives interactions from clients.
    private final IBinder binder = new MyBinder();
    boolean prepared = false;

    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class MyBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer = new MediaPlayer();
        File sdCard = Environment.getExternalStorageDirectory();
        File song = new File(sdCard.getAbsolutePath() + "/Music/song.mp3");
        try {
            mediaPlayer.setDataSource(song.getAbsolutePath());
            prepared = true;
            mediaPlayer.prepare();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    void play() throws IOException {
        if (!mediaPlayer.isLooping() && !mediaPlayer.isPlaying()) {
            Log.wtf("prep", prepared + "");
            if (!prepared) {
                Log.wtf("prep", prepared + "");
                mediaPlayer.prepare();
                mediaPlayer.seekTo(0);
                prepared = true;
                Log.wtf("prep", prepared + "");

            }
            mediaPlayer.start();
        }
        else {
            if (mediaPlayer.isPlaying()) mediaPlayer.pause();
            else mediaPlayer.start();
        }
    }

    void stop() {
        mediaPlayer.stop();
        prepared = false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
