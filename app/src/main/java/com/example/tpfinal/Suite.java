package com.example.tpfinal;

import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Vector;

public class Suite {

    public Suite(){

    }

    public void rempliSuite(Vector<Carte> suiteCarte, Carte carte)
    {
        //if(suiteCarte.size() > 0)
           // suiteCarte.remove(0);

       //suiteCarte.add(0,carte);
        suiteCarte.add(carte);

    }

    //pour undoButton
    public void enleveCarteDelaSuite(Vector<Carte> suiteCarte)
    {
        suiteCarte.remove(0);
    }

}
