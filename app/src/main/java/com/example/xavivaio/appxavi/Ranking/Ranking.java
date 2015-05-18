package com.example.xavivaio.appxavi.Ranking;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.xavivaio.appxavi.Dades.GestorBD;
import com.example.xavivaio.appxavi.R;

import java.util.ArrayList;

/**
 * Created by xavivaio on 02/02/2015.
 */
public class Ranking extends Fragment{

    //ArrayAdapter<String> namesAdapter, puntsAdapter;
    GestorBD gestorBD;
    TableLayout tableLayout;

    public Ranking(){
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ranking, container, false);

        tableLayout = (TableLayout) rootView.findViewById(R.id.taula_punts);

        gestorBD = new GestorBD(getActivity().getApplicationContext());

        int i = 1;
        boolean odd = true;
        Cursor c = gestorBD.getPuntuacions();
        if (c.moveToFirst()) {
            do {
                TextView nom = new TextView(getActivity().getApplicationContext());
                nom.setText(c.getString(c.getColumnIndex("name")));
                nom.setTextSize(20);
                nom.setTextColor(getResources().getColor(R.color.primary_text_default_material_light));

                TextView punts = new TextView(getActivity().getApplicationContext());
                punts.setText(c.getString(c.getColumnIndex("punts")));
                punts.setTextSize(20);
                punts.setTextColor(getResources().getColor(R.color.primary_text_default_material_light));;

                TableRow tableRow = new TableRow(getActivity().getApplicationContext());
                tableRow.setPadding(3,3,3,3);
                tableRow.addView(nom);
                tableRow.addView(punts);


                tableLayout.addView(tableRow,i);
                ++i;

            } while (c.moveToNext());
        }

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.ranking_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.reset_table) {
            gestorBD.resetTablePuntuacio();
            Ranking ranking = new Ranking();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, ranking)
                    .commit();
        }
        return super.onOptionsItemSelected(item);
    }
}
