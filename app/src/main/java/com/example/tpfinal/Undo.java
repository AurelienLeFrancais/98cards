package com.example.tpfinal;

import android.graphics.Typeface;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Vector;

public class Undo {

    public Undo(){

    }

    //remettre la carte joue dans le vecteur joueur
    public void undoRemettreCarteVecteurJouer(Vector<Carte> vectorCarteJoueur, Carte carteJoue, int indexOuRemettreCarte){
        vectorCarteJoueur.add(indexOuRemettreCarte,carteJoue);

    }

    public Carte undoRecupCartePrecedente(Vector<Carte> vectorPileHautGauche, Vector<Carte> vectorPileHautDroit, Vector<Carte> vectorPileBasGauche,Vector<Carte> vectorPileBasDroit, Carte carteJoue)
    {
        Carte carte = new Carte(0);
        //avoir la carte qui etait pose avant pour la faire reapparaitre
        //parcourir chaque vecteur pour trouver la carte

        //on parcours suite haut gauche et on enleve en meme temps la carte du vecteur
        for (int i = 0; i < vectorPileHautGauche.size(); i++) {
            if (vectorPileHautGauche.get(i).getValeur() == carteJoue.getValeur()) {
                if(vectorPileHautGauche.size() == 1){
                   // carte = new Carte(vectorPileHautGauche.get(i).getValeur());
                    vectorPileHautGauche.remove(i);
                    break;
                }
                else {
                    carte = new Carte(vectorPileHautGauche.get(i - 1).getValeur());//donne a carte la valeur de la carte en dessous de celle joue
                    vectorPileHautGauche.remove(i);
                    break;
                }

            }
        }

        //on parcours suite haut droit
        for (int i = 0; i < vectorPileHautDroit.size(); i++){
            if(vectorPileHautDroit.get(i).getValeur() == carteJoue.getValeur()){
                if(vectorPileHautDroit.size() == 1) {
                   // carte = new Carte(vectorPileHautDroit.get(i).getValeur());
                    vectorPileHautDroit.remove(i);
                    break;
                }

                else {
                    carte = new Carte(vectorPileHautDroit.get(i-1).getValeur());
                    break;
                }

            }
        }

        //on parcours suite bas gauche
        for (int i = 0; i < vectorPileBasGauche.size(); i++){
            if(vectorPileBasGauche.get(i).getValeur() == carteJoue.getValeur()){
                if(vectorPileBasGauche.size() == 1) {
                    //carte = new Carte(100);
                    carte.setValeur(100);
                    vectorPileBasGauche.remove(i);
                    break;
                }
                else {
                    carte = new Carte(vectorPileBasGauche.get(i-1).getValeur());
                    break;
                }

            }
        }
        //on parcours suite bas droit
        for (int i = 0; i < vectorPileBasDroit.size(); i++){
            if(vectorPileBasDroit.get(i).getValeur() == carteJoue.getValeur()){
                if(vectorPileBasDroit.size() == 1) {
                    //carte = new Carte(100);
                    carte.setValeur(100);
                    vectorPileBasDroit.remove(i);
                    break;
                }
                else {
                    carte = new Carte(vectorPileBasDroit.get(i-1).getValeur());
                    break;
                }

            }
        }

        return carte;
    }


    //Pour undo remettre carte de la suite dans jeuComplet
    public void undoRemettreCarteDansJeuxComplet(PileJeuxCarteComplet jeuxCarteComplet, Carte carteEnleve)
    {
        jeuxCarteComplet.getJeuxCarteComplet().add(carteEnleve);

    }

    public void undoEnleveCartePile(PileJeuxCarteComplet pileJeuxCarteComplet, Carte carteJoue)
    {
        int valeur = carteJoue.getValeur();

        for(int i = 0; i < pileJeuxCarteComplet.getJeuxCarteComplet().size(); i++)
        {
            int valeurCarteJeuxComplet = pileJeuxCarteComplet.getJeuxCarteComplet().get(i).getValeur();
            if(valeurCarteJeuxComplet == valeur) {//si on retrouve la valeur de la carte joue dans la pile alors on lenleve de la pile
                pileJeuxCarteComplet.enleveCarteDeLaPile(i);
                break;
            }

        }
    }

    public void undoAfficherCarte(LinearLayout t, String valeur, TextView carteN, int ressource) {
        carteN.setText(valeur);
        carteN.setTextSize(25);
        carteN.setTag("ppppp");
        carteN.setTypeface(null, Typeface.BOLD);
        carteN.setLayoutParams(new LinearLayout.LayoutParams(170, 260));
        carteN.setBackgroundResource(ressource);
        if(valeur.equals("0")) {
            carteN.setBackgroundResource(R.drawable.carte_pile_couleur_base);
            carteN.setText("1");
        }
        else if(valeur.equals("100")){
            carteN.setBackgroundResource(R.drawable.carte_pile_couleur_base);
            carteN.setText("100");
        }

        carteN.setVisibility(View.VISIBLE);
        t.addView(carteN);

    }



}
