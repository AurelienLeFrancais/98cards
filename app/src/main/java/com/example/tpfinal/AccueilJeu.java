package com.example.tpfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class AccueilJeu extends AppCompatActivity {
    Button boutonJouer;
    Button boutonScore;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boutonJouer = findViewById(R.id.boutonJouer);
        Ecouteur ec = new Ecouteur();
        boutonScore = findViewById(R.id.boutonVoirScore);
        boutonScore.setOnClickListener(ec);
        boutonJouer.setOnClickListener(ec);
    }

    public class Ecouteur implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            Intent i;
            if(v == boutonJouer) {
                i = new Intent(AccueilJeu.this, Jeu.class);
                startActivity(i);
            }
            else if( v == boutonScore)
            {
                i = new Intent(AccueilJeu.this, Resultat.class);
                startActivity(i);

            }

        }
    }
}