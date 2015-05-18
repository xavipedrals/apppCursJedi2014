package com.example.xavivaio.appxavi.Perfil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.xavivaio.appxavi.MainActivity;
import com.example.xavivaio.appxavi.R;

/**
 * Created by xavivaio on 05/02/2015.
 */
public class Perfil extends Fragment {

    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;

    Button submitButton;
    EditText nom, cognom, segonCognom;
    RadioButton male, female;
    ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.perfil, container, false);
        getActivity().setTitle("Perfil");

        imageView = (ImageView) rootView.findViewById(R.id.imageview_profile);
        nom = (EditText) rootView.findViewById(R.id.usuari_nom);
        cognom = (EditText) rootView.findViewById(R.id.usuari_cognom);
        segonCognom = (EditText) rootView.findViewById(R.id.usuari_Scognom);
        male = (RadioButton) rootView.findViewById(R.id.radiobutton_male);
        female = (RadioButton) rootView.findViewById(R.id.radiobutton_female);
        submitButton = (Button) rootView.findViewById(R.id.guardar);

        SharedPreferences prefs = getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        boolean b = prefs.getBoolean("I_HAVE_THE_INFO", false);

        if (b) {
            nom.setText(prefs.getString("NAME", "Merda"));
            cognom.setText(prefs.getString("SURNAME", "Merda"));
            segonCognom.setText(prefs.getString("SURNAME2", "Merda"));
            String genere = prefs.getString("GENDER", "Merda");
            selectedImagePath = prefs.getString("IMG_PATH", "");

            nom.setEnabled(false);
            nom.setTextColor(getResources().getColor(R.color.primary_text_default_material_light));;
            cognom.setEnabled(false);
            cognom.setTextColor(getResources().getColor(R.color.primary_text_default_material_light));;
            segonCognom.setEnabled(false);
            segonCognom.setTextColor(getResources().getColor(R.color.primary_text_default_material_light));;
            male.setEnabled(false);
            male.setTextColor(getResources().getColor(R.color.primary_text_default_material_light));;
            female.setEnabled(false);
            female.setTextColor(getResources().getColor(R.color.primary_text_default_material_light));;
            if (genere.equals("male")) {
                male.setChecked(true);
            } else {
                female.setChecked(true);
            }
            if (!selectedImagePath.equals("")) {
                Bitmap bitmap = getBitmap(selectedImagePath);
                imageView.setImageBitmap(bitmap);
            }
            submitButton.setText("BORRAR");
            submitButton.setBackgroundColor(Color.parseColor("#F44336"));   //RED
            submitButton.setTextColor(Color.parseColor("#FFFFFF"));
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences prefs = getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
                    prefs.edit().remove("NAME").apply();
                    prefs.edit().remove("SURNAME").apply();
                    prefs.edit().remove("GENDER").apply();
                    prefs.edit().remove("SURNAME2").apply();
                    prefs.edit().remove("IMG_PATH").apply();
                    prefs.edit().putBoolean("I_HAVE_THE_INFO", false).apply();

                    Perfil perfil = new Perfil();
                    getFragmentManager().beginTransaction()
                            .replace(R.id.container, perfil)
                            .commit();
                }
            });

        } else {
            submitButton.setBackgroundColor(Color.parseColor("#4CAF50"));   //GREEN
            submitButton.setTextColor(Color.parseColor("#FFFFFF"));
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nomS = nom.getText().toString();
                    String cognomS = cognom.getText().toString();
                    String sCognomS = segonCognom.getText().toString();
                    String genderS = "";
                    if (male.isChecked()) {
                        genderS = "male";
                    } else if (female.isChecked()) {
                        genderS = "female";
                    }
                    if (nomS.matches("") || cognomS.matches("") || sCognomS.matches("") || genderS.matches("")) {
                        Toast.makeText(getActivity(), "Completa la informació", Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences prefs = getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
                        prefs.edit().putString("NAME", nomS).apply();
                        prefs.edit().putString("SURNAME", cognomS).apply();
                        prefs.edit().putString("SURNAME2", sCognomS).apply();
                        prefs.edit().putString("GENDER", genderS).apply();
                        prefs.edit().putBoolean("I_HAVE_THE_INFO", true).apply();

                        Perfil perfil = new Perfil();
                        getFragmentManager().beginTransaction()
                                .replace(R.id.container, perfil)
                                .commit();
                    }
                }
            });
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,
                            "Select Picture"), SELECT_PICTURE);
                }
            });
        }

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.perfil_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout){
            SharedPreferences prefs = getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
            prefs.edit().putBoolean("IS_USER_LOGGED", false).apply();
            getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getActivity();
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                SharedPreferences prefs = getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
                prefs.edit().putString("IMG_PATH", selectedImagePath).apply();
                Bitmap bitmap = getBitmap(selectedImagePath);
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    /**
     * helper to retrieve the path of an image URI
     */
    public String getPath(Uri uri) {
        // just some safety built in
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
    }

    /**
     * helper to retrieve the Bitmap of path
     */

    public Bitmap getBitmap(String path) {
        if (path.equals("")) {
            Toast.makeText(getActivity(), "Puta bida", Toast.LENGTH_SHORT).show();
            return null;
        } else {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            return BitmapFactory.decodeFile(path, options);
        }
    }

}
