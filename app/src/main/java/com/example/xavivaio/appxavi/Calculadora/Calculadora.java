package com.example.xavivaio.appxavi.Calculadora;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.xavivaio.appxavi.R;

/**
 * Created by xavivaio on 02/02/2015.
 */
public class Calculadora extends Fragment {
    Button button0, button1, button2, button3, button4, button5, button6, button7, button8, button9, buttonCALL,
            buttonPlus, buttonMinus, buttonChange, buttonC, buttonANS, buttonX, buttonDivide, buttonDot, buttonEqual;
    TextView resposta;
    View.OnClickListener lis1;
    float resultat;
    float ANS;
    Boolean netejar;

    char lastOp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.calculadora, container, false);
        getActivity().setTitle("Calculadora");

        lastOp = '0';
        netejar = false;

        button0 = (Button) rootView.findViewById(R.id.button0);
        button1 = (Button) rootView.findViewById(R.id.button1);
        button2 = (Button) rootView.findViewById(R.id.button2);
        button3 = (Button) rootView.findViewById(R.id.button3);
        button4 = (Button) rootView.findViewById(R.id.button4);
        button5 = (Button) rootView.findViewById(R.id.button5);
        button6 = (Button) rootView.findViewById(R.id.button6);
        button7 = (Button) rootView.findViewById(R.id.button7);
        button8 = (Button) rootView.findViewById(R.id.button8);
        button9 = (Button) rootView.findViewById(R.id.button9);
        buttonPlus = (Button) rootView.findViewById(R.id.buttonPlus);
        buttonMinus = (Button) rootView.findViewById(R.id.buttonMinus);
        buttonEqual = (Button) rootView.findViewById(R.id.buttonEqual);
        buttonDot = (Button) rootView.findViewById(R.id.buttonpoint);
        buttonChange = (Button) rootView.findViewById(R.id.buttonChange);
        buttonC = (Button) rootView.findViewById(R.id.buttonC);
        buttonX = (Button) rootView.findViewById(R.id.buttonX);
        buttonDivide = (Button) rootView.findViewById(R.id.buttondivide);
        buttonANS = (Button) rootView.findViewById(R.id.buttonANS);
        buttonCALL = (Button) rootView.findViewById(R.id.buttonClon); //canviar clon per trucar?

        resposta = (TextView) rootView.findViewById(R.id.resposta);

        lis1 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (netejar) {resposta.setText(""); netejar = false;}
                switch (view.getId()) {
                    case (R.id.button0): resposta.setText(resposta.getText() + "0"); break;
                    case (R.id.button1): resposta.setText(resposta.getText() + "1"); break;
                    case (R.id.button2): resposta.setText(resposta.getText() + "2"); break;
                    case (R.id.button3): resposta.setText(resposta.getText() + "3"); break;
                    case (R.id.button4): resposta.setText(resposta.getText() + "4"); break;
                    case (R.id.button5): resposta.setText(resposta.getText() + "5"); break;
                    case (R.id.button6): resposta.setText(resposta.getText() + "6"); break;
                    case (R.id.button7): resposta.setText(resposta.getText() + "7"); break;
                    case (R.id.button8): resposta.setText(resposta.getText() + "8"); break;
                    case (R.id.button9): resposta.setText(resposta.getText() + "9"); break;
                    case (R.id.buttonpoint): resposta.setText(resposta.getText() + "."); break;
                    case (R.id.buttonPlus):
                        carregarNum(lastOp);
                        lastOp = '+';
                        resposta.setText("+");
                        break;
                    case (R.id.buttonMinus):
                        carregarNum(lastOp);
                        lastOp = '-';
                        resposta.setText("-");
                        break;
                    case (R.id.buttonX):
                        carregarNum(lastOp);
                        lastOp = 'x';
                        resposta.setText("x");
                        break;
                    case (R.id.buttondivide):
                        carregarNum(lastOp);
                        lastOp = '/';
                        resposta.setText("%");
                        break;
                    case (R.id.buttonEqual):
                        carregarNum(lastOp);
                        resposta.setText(String.valueOf(resultat));
                        ANS = resultat;
                        lastOp = '0';
                        break;
                    case (R.id.buttonANS):
                        resposta.setText(String.valueOf(ANS));
                        netejar = true;
                        break;
                    case (R.id.buttonC): resposta.setText(""); break;
                    case (R.id.buttonChange): resposta.setText("-" + resposta.getText()); break;
                    case (R.id.buttonClon):
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + resposta.getText().toString()));
                        startActivity(intent);
                        break;
                }
            }
        };
        button0.setOnClickListener(lis1);
        button1.setOnClickListener(lis1);
        button2.setOnClickListener(lis1);
        button3.setOnClickListener(lis1);
        button4.setOnClickListener(lis1);
        button5.setOnClickListener(lis1);
        button6.setOnClickListener(lis1);
        button7.setOnClickListener(lis1);
        button8.setOnClickListener(lis1);
        button9.setOnClickListener(lis1);

        buttonPlus.setOnClickListener(lis1);
        buttonMinus.setOnClickListener(lis1);
        buttonEqual.setOnClickListener(lis1);
        buttonX.setOnClickListener(lis1);
        buttonChange.setOnClickListener(lis1);
        buttonDivide.setOnClickListener(lis1);
        buttonDot.setOnClickListener(lis1);
        buttonANS.setOnClickListener(lis1);
        buttonC.setOnClickListener(lis1);
        buttonCALL.setOnClickListener(lis1);

        return rootView;
    }

    public void carregarNum(char lastOp) {
        try{
            if (lastOp == '0') resultat = Float.parseFloat(resposta.getText().toString());
            else if (lastOp == '+') resultat += Float.parseFloat(resposta.getText().toString());
            else if (lastOp == '-') resultat -= Float.parseFloat(resposta.getText().toString());
            else if (lastOp == 'x') resultat *= Float.parseFloat(resposta.getText().toString());
            else if (lastOp == '/') resultat /= Float.parseFloat(resposta.getText().toString());
        } catch(Exception e) {
            //Toast.makeText(getActivity().getApplicationContext(), "Excepció", Toast.LENGTH_LONG).show();
        }
        netejar = true;
    }
}
