package com.example.tpfinal;

import android.graphics.Typeface;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Vector;

public class DonnerNouvelleCarte {
    private TextView carte;

    //constructeur
    public DonnerNouvelleCarte(TextView carteN)
    {
        this.carte = carteN;
    }

    public void Donner2nouvellesCarte(LinearLayout conteneurHaut, Vector<Carte> carteQueLonPeutDonner, TextView carteN, TextView carteChoisi)
    {
        for (int i = 0; i < conteneurHaut.getChildCount(); i++) {
            LinearLayout t = (LinearLayout) conteneurHaut.getChildAt(i);
            if (t.getChildAt(0) == null) {
                //si vectorCarteQueLonPeutDonner contient des carte
                if(carteQueLonPeutDonner.size() > 0) {
                    int valeurNouvelleCarte = carteQueLonPeutDonner.get(0).getValeur();//donne la valeur
                    //supprime la carte que lon peut donner
                    carteQueLonPeutDonner.remove(0);
                    String valeur = String.valueOf(valeurNouvelleCarte);
                    //ajouter la carte dans la case vide

                    carteN.setText(valeur);
                    carteN.setTextSize(25);
                    carteN.setTypeface(null, Typeface.BOLD);
                    carteN.setLayoutParams(new LinearLayout.LayoutParams(170, 260));

                    carteN.setVisibility(View.VISIBLE);
                    t.removeView(carteChoisi);
                    t.addView(carteN);
                    //t.getChildAt(0).setOnTouchListener(ec);
                }

            }

        }

    }
}
