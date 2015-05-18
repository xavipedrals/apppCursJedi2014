package com.example.xavivaio.appxavi.Memory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.xavivaio.appxavi.Dades.GestorBD;
import com.example.xavivaio.appxavi.R;
import com.example.xavivaio.appxavi.Ranking.Ranking;

/**
 * Created by xavivaio on 02/02/2015.
 */
public class InsertPuntuacioDialog extends DialogFragment {

    private EditText mEditText;
    private Button okButton;
    private int punts;

    public InsertPuntuacioDialog() {
        // Empty constructor required for DialogFragment
        punts = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle b = getArguments();
        punts = b.getInt("punts");
        Log.d("PUNTS", "Soc el dialog i tinc " + punts);
        View view = inflater.inflate(R.layout.insert_puntuacio_dialog, container);
        mEditText = (EditText) view.findViewById(R.id.txt_your_name);
        okButton = (Button) view.findViewById(R.id.okButton);


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GestorBD(getActivity().getApplicationContext()).insertPuntuacio(mEditText.getText().toString(), punts);
                dismiss();
                Ranking ranking = new Ranking();
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, ranking)
                        .commit();
            }
        });
        getDialog().setTitle("Insert your name");
        getDialog().setCancelable(false);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
