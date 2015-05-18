package com.example.xavivaio.appxavi.Memory;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;

import com.example.xavivaio.appxavi.Dades.GestorBD;
import com.example.xavivaio.appxavi.R;

import java.util.Random;

/**
 * Created by xavivaio on 30/01/2015.
 */
public class Memory extends Fragment {

    View.OnClickListener lis;
    int[] images;
    ImageView[] icons;
    int[] pairs;
    int lastClicked, firstMissed;
    TextView score;
    boolean lastMiss;
    boolean lock;
    GestorBD gestorBD;

    public Memory(){
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Memory");
        View rootView = inflater.inflate(R.layout.memory, container, false);

        gestorBD = new GestorBD(getActivity().getApplicationContext());

        images = new int[8];
        images[0] = R.drawable.analytics;
        images[1] = R.drawable.cake;
        images[2] = R.drawable.messages;
        images[3] = R.drawable.money;
        images[4] = R.drawable.notes;
        images[5] = R.drawable.plus;
        images[6] = R.drawable.up;
        images[7] = R.drawable.numbers;

        icons = new ImageView[16];
        icons[0] = (ImageView) rootView.findViewById(R.id.imageView);
        icons[1] = (ImageView) rootView.findViewById(R.id.imageView2);
        icons[2] = (ImageView) rootView.findViewById(R.id.imageView3);
        icons[3] = (ImageView) rootView.findViewById(R.id.imageView4);
        icons[4] = (ImageView) rootView.findViewById(R.id.imageView5);
        icons[5] = (ImageView) rootView.findViewById(R.id.imageView6);
        icons[6] = (ImageView) rootView.findViewById(R.id.imageView7);
        icons[7] = (ImageView) rootView.findViewById(R.id.imageView8);
        icons[8] = (ImageView) rootView.findViewById(R.id.imageView9);
        icons[9] = (ImageView) rootView.findViewById(R.id.imageView10);
        icons[10] = (ImageView) rootView.findViewById(R.id.imageView11);
        icons[11] = (ImageView) rootView.findViewById(R.id.imageView12);
        icons[12] = (ImageView) rootView.findViewById(R.id.imageView13);
        icons[13] = (ImageView) rootView.findViewById(R.id.imageView14);
        icons[14] = (ImageView) rootView.findViewById(R.id.imageView15);
        icons[15] = (ImageView) rootView.findViewById(R.id.imageView16);

        score = (TextView) rootView.findViewById(R.id.score);

        //posa la mateixa imatge a tot arreu
        for (int i = 0; i < 16; ++i) icons[i].setImageResource(R.drawable.circle);

        pairs = new int[16];
        fillPairs();
        randomizePairs();

        lastClicked = -1;
        firstMissed = -1;
        lastMiss = false;
        lock = false;

        lis = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!lock) {
                    if (lastMiss) {
                        icons[firstMissed].setImageResource(R.drawable.circle);
                        icons[lastClicked].setImageResource(R.drawable.circle);
                        firstMissed = -1;
                        lastClicked = -1;
                        lastMiss = false;
                    }

                    if (lastClicked == -1){
                        int punts = Integer.parseInt(score.getText().toString());
                        ++punts;
                        score.setText(String.valueOf(punts));
                    }

                    try{
                        switch (view.getId()){
                            case (R.id.imageView): doInSwitch(0); break;
                            case (R.id.imageView2): doInSwitch(1); break;
                            case (R.id.imageView3): doInSwitch(2); break;
                            case (R.id.imageView4): doInSwitch(3); break;
                            case (R.id.imageView5): doInSwitch(4); break;
                            case (R.id.imageView6): doInSwitch(5); break;
                            case (R.id.imageView7): doInSwitch(6); break;
                            case (R.id.imageView8): doInSwitch(7); break;
                            case (R.id.imageView9): doInSwitch(8); break;
                            case (R.id.imageView10): doInSwitch(9); break;
                            case (R.id.imageView11): doInSwitch(10); break;
                            case (R.id.imageView12): doInSwitch(11); break;
                            case (R.id.imageView13): doInSwitch(12); break;
                            case (R.id.imageView14): doInSwitch(13); break;
                            case (R.id.imageView15): doInSwitch(14); break;
                            case (R.id.imageView16): doInSwitch(15); break;

                        }
                    } catch (Exception e) {Log.v("MISSATGE", "Excepcio al switch");}
                }
            }
        };

        for (int i = 0; i < 16; ++i) icons[i].setOnClickListener(lis);
        return rootView;
    }

    private void showEditDialog(int punts) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        InsertPuntuacioDialog editNameDialog = new InsertPuntuacioDialog();
        Bundle b = new Bundle();
        b.putInt("punts", punts);
        editNameDialog.setArguments(b);
        editNameDialog.show(fm, "fragment_edit_name");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.memory_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        if (id == R.id.new_game) {
            for (int i = 0; i < 16; ++i) icons[i].setImageResource(R.drawable.circle);
            fillPairs();
            randomizePairs();
            score.setText("0");
        }
        return super.onOptionsItemSelected(item);
    }

    private void doInSwitch (final int iSwitch) {
        icons[iSwitch].setImageResource(pairs[iSwitch]);
        if(lastClicked == -1) lastClicked = iSwitch;
        else if (lastClicked != iSwitch && pairs[lastClicked] == pairs[iSwitch]) {
            lock = true;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    icons[iSwitch].setImageResource(R.drawable.tick);
                    icons[lastClicked].setImageResource(R.drawable.tick);

                    icons[iSwitch].setOnClickListener(null);
                    icons[lastClicked].setOnClickListener(null);

                    pairs[lastClicked] = -1;
                    pairs[iSwitch] = -1;

                    checkFinished();
                    lastClicked = -1;
                    lock = false;
                }
            }, 1000);

        }
        else {
            lastMiss = true;
            firstMissed = iSwitch;
        }
    }



    private void checkFinished (){
        Log.v("MISSATGE", "entro al chack finished");
        boolean finished = true;
        int i = 0;
        while (i < pairs.length && finished) {
            if(pairs[i] != -1) finished = false;
            ++i;
        }
        if (finished) {
            int points = Integer.parseInt(score.getText().toString());
            //mostra el FragmentDialog que escriurà el nom i la puntuació a la base de dades
            showEditDialog(Integer.parseInt(score.getText().toString()));
        }
    }

    private void fillPairs() {
        for (int i = 0; i < pairs.length; ++i) {
            pairs[i] = images[i%8];
        }
    }

    private void randomizePairs () {
        Random rgen = new Random();
        for (int i=0; i<pairs.length; i++) {
            int randomPosition = rgen.nextInt(pairs.length);
            int temp = pairs[i];
            pairs[i] = pairs[randomPosition];
            pairs[randomPosition] = temp;
        }
    }

}

