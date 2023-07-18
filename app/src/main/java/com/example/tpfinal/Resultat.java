package com.example.tpfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Vector;

public class Resultat extends AppCompatActivity {
    ListView liste;
    DatabaseHelper instance;
    Button boutonRetour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultat);
        liste = findViewById(R.id.list);
        boutonRetour = findViewById(R.id.button);
        Ecouteur ec = new Ecouteur();
        boutonRetour.setOnClickListener(ec);

    }

    @Override
    protected void onStart() {
        super.onStart();
        instance = DatabaseHelper.getInstance(this);
        instance.ouvrirBD();
        Vector<String> meilleurScore;
        meilleurScore = instance.retournerScore();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, meilleurScore);
        liste.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        instance.fermerBD();
    }

    public class Ecouteur implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            Intent i;
            i = new Intent(Resultat.this, AccueilJeu.class);
            startActivity(i);
        }
    }
}
