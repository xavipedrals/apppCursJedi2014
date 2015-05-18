package com.example.xavivaio.appxavi.Reproductor;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xavivaio.appxavi.R;

import java.io.IOException;

/**
 * Created by xavivaio on 10/02/2015.
 */
public class Reproductor extends Fragment{

    private MyService bService;
    boolean bound = false;
    ImageView ivPlay, ivStop;
    TextView tvEstat;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MyService.MyBinder binder = (MyService.MyBinder) iBinder;
            bService = binder.getService();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            bService = null;
            bound = false;
        }
    };

    void doBindService() {
        // Establish a connection with the service.  We use an explicit
        // class name because we want a specific service implementation that
        // we know will be running in our own process (and thus won't be
        // supporting component replacement by other applications).
        Log.d("REPRODUCTOR", "FAIG UN BIND");
        getActivity().bindService(new Intent(getActivity(),
                MyService.class), connection, Context.BIND_AUTO_CREATE);
        bound = true;
    }

    void doUnbindService() {
        if (bound) {
            // Detach our existing connection.
            getActivity().unbindService(connection);
            bound = false;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.reproductor, container, false);

        doBindService();

        ivPlay = (ImageView) rootView.findViewById(R.id.imagePlay);
        ivStop = (ImageView) rootView.findViewById(R.id.imageStop);
        tvEstat = (TextView) rootView.findViewById(R.id.estat);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.imagePlay){
                    if (bound) {
                        try {
                            bService.play();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (bService.mediaPlayer.isPlaying()) tvEstat.setText("Reproduint");
                        else tvEstat.setText("Aturat");
                    }
                } else if (v.getId() == R.id.imageStop){
                    if (bound) {
                        bService.stop();
                        tvEstat.setText("Aturat");
                    }
                }
            }
        };

        ivStop.setOnClickListener(onClickListener);
        ivPlay.setOnClickListener(onClickListener);

        return rootView;
    }

    @Override
    public void onDestroy() {
        doUnbindService();
        super.onDestroy();
    }
}
