package com.example.tpfinal;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

public class Jeu extends AppCompatActivity {
    Vector<Carte> vectorCarteJoueur, vectorCarteQueLonPeutDonner;// represente les 8 cartes du joeurs
    Vector<Integer> index;
    Partie partie;
    TextView carteJoueur1, carteJoueur2, carteJoueur3, carteJoueur4, carteJoueur5, carteJoueur6, carteJoueur7, carteJoueur8, nbrCarte;
    TextView cartePoseBasGauche, cartePoseHautGauche, cartePoseBasDroit, cartePoseHautDroit;
    LinearLayout conteneur, conteneurCarteJoueurHaut, conteneurPileGaucheBas, conteneurCarteJoueurBas, conteneurPileDroitBas, conteneurPileHautDroit, conteneurPileHautGauche;
    Suite suiteCarte;//est la suite de carte qui a ete joue
    PileJeuxCarteComplet pileJeuxCarteCompletJeuxComplet;
    int nbrCarteRestante;
    ImageView undoButton;
    LinearLayout conteneurPileHaut, conteneurPileBas, parent, conteneurOuJeDeposeLaCarte;
    TextView carteChoisi;
    boolean peutContinuer = false, peutFaireUndo = false, aGagner = false;
    Chronometer chronometer;
    TextView score;
    int points = 0; //pour score
    int seconds;
    int minutes;
    DatabaseHelper instance;
    //pour remplir les pile
    Vector<Carte> vectorSuiteBasGauche, vectorSuiteBasDroit, vectorSuiteHautGauche, vectorSuiteHautDroit;
    Carte carteJoue, cartePrecedementJoue;
    int indexPourUndo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jeu);
        carteJoueur1 = findViewById(R.id.carteJoueur1);
        carteJoueur2 = findViewById(R.id.carteJoueur2);
        carteJoueur3 = findViewById(R.id.carteJoueur3);
        carteJoueur4 = findViewById(R.id.carteJoueur4);
        carteJoueur5 = findViewById(R.id.carteJoueur5);
        carteJoueur6 = findViewById(R.id.carteJoueur6);
        carteJoueur7 = findViewById(R.id.carteJoueur7);
        carteJoueur8 = findViewById(R.id.carteJoueur8);
        nbrCarte = findViewById(R.id.nbrCarte);
        conteneur = findViewById(R.id.conteneur);
        conteneurCarteJoueurHaut = findViewById(R.id.conteneurCarteJoueur);
        conteneurCarteJoueurBas = findViewById(R.id.conteneurCarteJoueurBas);
        conteneurPileGaucheBas = findViewById(R.id.conteneurPileGaucheBas);
        conteneurPileDroitBas = findViewById(R.id.conteneurPileDroitBas);
        conteneurPileHautDroit = findViewById(R.id.conteneurPileDroitHaut);
        conteneurPileHautGauche = findViewById(R.id.conteneurPileGaucheHaut);
        chronometer = findViewById(R.id.chrono);
        conteneurPileHaut = findViewById(R.id.linearCartePoseHaut);
        conteneurPileBas = findViewById(R.id.linearCartePoseBas);
        cartePoseBasGauche = findViewById(R.id.cartePoseBasGauche);
        cartePoseBasDroit = findViewById(R.id.cartePoseBasDroit);
        cartePoseHautGauche = findViewById(R.id.cartePoseHautGauche);
        cartePoseHautDroit = findViewById(R.id.cartePoseHautDroit);
        score = findViewById(R.id.score);
        undoButton = findViewById(R.id.boutonUndo);
        pileJeuxCarteCompletJeuxComplet = new PileJeuxCarteComplet();
        vectorCarteJoueur = new Vector();
        vectorCarteQueLonPeutDonner = new Vector();
        vectorSuiteBasDroit = new Vector();
        vectorSuiteBasGauche = new Vector();
        vectorSuiteHautDroit = new Vector();
        vectorSuiteHautGauche = new Vector();
        suiteCarte = new Suite();
        partie = new Partie(pileJeuxCarteCompletJeuxComplet);
        index = new Vector();

        //1/remplir la pile de 98 cartes
        partie.remplirJeuCarte(pileJeuxCarteCompletJeuxComplet);
        nbrCarteRestante = pileJeuxCarteCompletJeuxComplet.getJeuxCarteComplet().size();
        //2 donner 8 carte au joueur
        partie.donne8CarteJoueur(pileJeuxCarteCompletJeuxComplet, vectorCarteJoueur);//on donne 8 carte au joueur
        //remplir le vecteur des cartes que lon peut donner
        partie.remplirCarteQueLonPeutDonner(pileJeuxCarteCompletJeuxComplet, vectorCarteJoueur, vectorCarteQueLonPeutDonner);//on utilise vectorCarteJoueur pour representer les 8 carte du joueur!!!
        nbrCarte.setText(String.valueOf(pileJeuxCarteCompletJeuxComplet.getJeuxCarteComplet().size()));//affiche le nbr de carte qua le joueur

        //une fois les cartes distribue au joueur, on affiche la valeur de chaque carte
        carteJoueur1.setText(String.valueOf(vectorCarteJoueur.get(0).getValeur()));
        carteJoueur2.setText(String.valueOf(vectorCarteJoueur.get(1).getValeur()));
        carteJoueur3.setText(String.valueOf(vectorCarteJoueur.get(2).getValeur()));
        carteJoueur4.setText(String.valueOf(vectorCarteJoueur.get(3).getValeur()));
        carteJoueur5.setText(String.valueOf(vectorCarteJoueur.get(4).getValeur()));
        carteJoueur6.setText(String.valueOf(vectorCarteJoueur.get(5).getValeur()));
        carteJoueur7.setText(String.valueOf(vectorCarteJoueur.get(6).getValeur()));
        carteJoueur8.setText(String.valueOf(vectorCarteJoueur.get(7).getValeur()));

        Ecouteur ec = new Ecouteur();
        for (int i = 0; i < conteneurCarteJoueurHaut.getChildCount(); i++)//ecouteur au carte du joueur du haut
        {
            LinearLayout conteneurCarteJoueurH = (LinearLayout) conteneurCarteJoueurHaut.getChildAt(i);
            conteneurCarteJoueurH.setOnDragListener(ec);//on met un ecouteur sur le conteneur pour Drague
            conteneurCarteJoueurH.getChildAt(0).setOnTouchListener(ec);//on met un ecouteur OnTouch sur les enfants pour lorsquon les prends
        }
        //ecouteur pour carte du bas
        for (int i = 0; i < conteneurCarteJoueurBas.getChildCount(); i++)//ecouteur au carte du joueur du haut
        {
            LinearLayout conteneurCarteJoueurB = (LinearLayout) conteneurCarteJoueurBas.getChildAt(i);
            conteneurCarteJoueurB.setOnDragListener(ec);//on met un ecouteur sur le conteneur pour Drague
            conteneurCarteJoueurB.getChildAt(0).setOnTouchListener(ec);//on met un ecouteur OnTouch sur les enfants pour lorsquon les prends
        }

        //ajout des ecouteur sur les 4 piles ou les cartes sont poses du jeu
        conteneurPileGaucheBas.setOnDragListener(ec);//on met le DragListeneur sur le conteneur ou je vais deposer ma carte
        conteneurPileHautGauche.setOnDragListener(ec);
        conteneurPileHautDroit.setOnDragListener(ec);
        conteneurPileDroitBas.setOnDragListener(ec);
        //ecouteur pour le undoButton
        undoButton.setOnTouchListener(ec);
        chronometer.setFormat("%m:%s");
        chronometer.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        instance = DatabaseHelper.getInstance(this);//se connecter a la bd
        instance.ouvrirBD();
    }

    private class Ecouteur implements View.OnDragListener, View.OnTouchListener {
        @Override
        public boolean onDrag(View source, @NotNull DragEvent event) {//source = ou on depose la carte
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_ENTERED: //quand je click sur une carte du joueur
                    break;
                case DragEvent.ACTION_DRAG_EXITED: //quand la carte sort de son conteneur
                    break;

                case DragEvent.ACTION_DROP://quand je relache la souris
                    peutContinuer = false;
                    carteChoisi = (TextView) event.getLocalState();//recupere la carte choisi
                    int valeurCarteJoue = Integer.parseInt(carteChoisi.getText().toString());
                    carteJoue = new Carte(valeurCarteJoue);
                    //aller chercher le conteneur d'origine de la carte choisi
                    parent = (LinearLayout) carteChoisi.getParent();
                    conteneurOuJeDeposeLaCarte = (LinearLayout) source;//conteneur ou je pose la carte. on converti source en LinearLayout + conteneurOuJeDeposeLaCarte = LinearL
                    //recupere le  lenfant du conteneur ou on depose la carte qui est un textView
                    TextView pileOujeDeposeCarte = null;
                    View v = conteneurOuJeDeposeLaCarte.getChildAt(0);
                    if (v instanceof TextView) {
                        pileOujeDeposeCarte = (TextView) v;
                    }
                    //valeur de la carte sur la pile
                    int valeurCartePile = Integer.parseInt(pileOujeDeposeCarte.getText().toString());

                    //Si on pose carte joue en haut
                    if (pileOujeDeposeCarte.getParent().getParent().getParent() == conteneurPileHaut) {
                        if (partie.VerifCoupHaut(valeurCarteJoue, valeurCartePile)) {

                            if (pileOujeDeposeCarte.getParent() == conteneurPileHautGauche) {
                                suiteCarte.rempliSuite(vectorSuiteHautGauche, carteJoue);
                            } else if (pileOujeDeposeCarte.getParent() == conteneurPileHautDroit) {
                                suiteCarte.rempliSuite(vectorSuiteHautDroit, carteJoue);
                            }
                            points += 100;
                            peutContinuer = true;
                            parent.removeView(carteChoisi);
                            conteneurOuJeDeposeLaCarte.removeView(pileOujeDeposeCarte);
                            pileOujeDeposeCarte = carteChoisi;
                            if (conteneurOuJeDeposeLaCarte == conteneurPileHautDroit)
                                conteneurOuJeDeposeLaCarte.addView(carteChoisi, 0);
                            else
                                conteneurOuJeDeposeLaCarte.addView(carteChoisi);

                        } else
                            peutContinuer = false;
                    }
                    //si on pose carte joue en bas
                    else if (pileOujeDeposeCarte.getParent().getParent().getParent() == conteneurPileBas) {
                        if (partie.VerifCoupBas(valeurCarteJoue, valeurCartePile)) {
                            //ajouter la carte dans la pile
                            if (pileOujeDeposeCarte.getParent() == conteneurPileGaucheBas) {
                                suiteCarte.rempliSuite(vectorSuiteBasGauche, carteJoue);
                            } else if (pileOujeDeposeCarte.getParent() == conteneurPileDroitBas) {
                                suiteCarte.rempliSuite(vectorSuiteBasDroit, carteJoue);
                            }
                            //augmente le score
                            points += 100;
                            peutContinuer = true;
                            parent.removeView(carteChoisi);
                            conteneurOuJeDeposeLaCarte.removeView(pileOujeDeposeCarte);
                            pileOujeDeposeCarte = carteChoisi;
                            if (conteneurOuJeDeposeLaCarte == conteneurPileDroitBas)
                                conteneurOuJeDeposeLaCarte.addView(carteChoisi, 0);
                            else
                                conteneurOuJeDeposeLaCarte.addView(carteChoisi);
                        } else
                            peutContinuer = false;

                    }
                    score.setText(String.valueOf(points));//setter laffichage du score
                    carteChoisi.setVisibility(View.VISIBLE); //permet dafficher la carte que je viens de jouer sur la pile

                    if (peutContinuer) {
                        int couleurPile = R.drawable.bordurecarte;
                        carteChoisi.setBackgroundResource(couleurPile);//on modifie la couleur de la carte lorsquon la pose sur la pile
                        //enlever la carte de la pile du jeu (jeu complet)
                        partie.enleveCartePile(pileJeuxCarteCompletJeuxComplet, carteJoue);//ca me permet davoir le nbr de carte restante du joueur
                        //mettre a jour laffichage du nbr de carte
                        nbrCarte.setText(String.valueOf(pileJeuxCarteCompletJeuxComplet.getJeuxCarteComplet().size()));
                        //recuperer lindex du vecteurCarteJoueur de la ou on enleve la carte si on fait un undo
                        indexPourUndo = partie.recupIndex(vectorCarteJoueur, valeurCarteJoue);
                        //supprimer la carte du vecteur carte joueur (donc 1 des 8 carte)
                        partie.supprimerCarteDuJoueurJoue(vectorCarteJoueur, valeurCarteJoue);
                        //ajout nouvel ecouteur sur la nouvelle carte
                        Ecouteur ec = new Ecouteur();
                        int nbrDeCarteManquanteHaut = partie.verifierSiManqueCarteJoueurHaut(conteneurCarteJoueurHaut);
                        int nbrDeCarteManquanteBas = partie.verifierSiManqueCarteJoueurBas(conteneurCarteJoueurBas);
                        int carteManquantTotal = nbrDeCarteManquanteBas + nbrDeCarteManquanteHaut;
                        //si il manque 2 cartes les redonner au joueur
                        if (carteManquantTotal == 2) {
                            peutFaireUndo = false;
                            int ressource = R.drawable.carte_joueur;//pour donner une couleur aux carte
                            if (nbrDeCarteManquanteHaut > 0 && !(nbrDeCarteManquanteBas == 2)) {
                                for (int i = 0; i < conteneurCarteJoueurHaut.getChildCount(); i++) {//on fait le tour du conteneur du haut
                                    LinearLayout t = (LinearLayout) conteneurCarteJoueurHaut.getChildAt(i);
                                    if (t.getChildAt(0) == null) {//si le conteneur est null
                                        if (vectorCarteQueLonPeutDonner.size() > 0) {//si on peut tjr donner une carte
                                            Carte carteQueLonDonne = new Carte(vectorCarteQueLonPeutDonner.get(0).getValeur());//prend la carte
                                            //int valeurNouvelleCarte = vectorCarteQueLonPeutDonner.get(0).getValeur();//donne la valeur
                                            int valeurNouvelleCarte = carteQueLonDonne.getValeur();
                                            //supprime la carte que lon peut donner
                                            vectorCarteQueLonPeutDonner.remove(0);
                                            String valeur = String.valueOf(valeurNouvelleCarte);
                                            //ajouter la carte dans la case vide
                                            TextView carteN = new TextView(Jeu.this);
                                            afficherCarte(t, valeur, carteN, ressource, ec);
                                            //ajouter la nouvelle carte dans le vectorCarteJoueur.
                                            partie.donner1Carte(vectorCarteJoueur, carteQueLonDonne, i);
                                        }
                                    }
                                }
                            }
                            //puis dans le conteneur du bas si il faut
                            if (nbrDeCarteManquanteBas > 0 && !(nbrDeCarteManquanteHaut == 2))//parcourir le conteneur de carte du bas
                            {
                                for (int i = 0; i < conteneurCarteJoueurBas.getChildCount(); i++) {
                                    LinearLayout t = (LinearLayout) conteneurCarteJoueurBas.getChildAt(i);
                                    if (t.getChildAt(0) == null) {
                                        if (vectorCarteQueLonPeutDonner.size() > 0) {
                                            Carte carteQueLonDonne = new Carte(vectorCarteQueLonPeutDonner.get(0).getValeur());//prend la carte
                                            int valeurNouvelleCarte = vectorCarteQueLonPeutDonner.get(0).getValeur();//donne la valeur
                                            vectorCarteQueLonPeutDonner.remove(0);
                                            String valeur = String.valueOf(valeurNouvelleCarte);
                                            TextView carteN = new TextView(Jeu.this);
                                            afficherCarte(t, valeur, carteN, ressource, ec);
                                            //ajouter la nouvelle carte dans le vectorCarteJoueur..-> lajouter au bonne index...
                                            partie.donner1Carte(vectorCarteJoueur, carteQueLonDonne, i);
                                        }
                                    }
                                }
                            }
                        }

                        if (carteManquantTotal < 2)
                            peutFaireUndo = true;

                        //si il ny a plus de carte le joueur a gagner
                        if (pileJeuxCarteCompletJeuxComplet.getJeuxCarteComplet().size() == 0) {
                            aGagner = true;
                            chronometer.stop();
                            //date du jour
                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            String date = dateFormat.format(calendar.getTime());
                            // Récupérer le temps de base du Chronometer, en millisecondes
                            long baseTime = chronometer.getBase();
                            // Calculer les minutes et les secondes à partir du temps de base
                            minutes = (int) ((SystemClock.elapsedRealtime() - baseTime) / 1000) / 60;
                            seconds = (int) ((SystemClock.elapsedRealtime() - baseTime) / 1000) % 60;
                            //ajouter score
                            AjouterScore ajouterScore = new AjouterScore(date, points, minutes, seconds);
                            instance.ajoutScore(ajouterScore);
                            finish();
                        }
                        //si le joueur ne peut plus poser de carte alors quil lui en reste il a perdu -> on le renvoit a laccueil
                        if (!(partie.peuxToujoursJoueur(vectorCarteJoueur, vectorSuiteHautGauche, vectorSuiteHautDroit, vectorSuiteBasGauche, vectorSuiteBasDroit))) {
                            if(aGagner == false) {
                                Toast.makeText(getApplicationContext(), "Vous avez perdu", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Jeu.this, Resultat.class);
                                startActivity(i);
                            }
                        }
                        break;
                    }

                case DragEvent.ACTION_DRAG_ENDED://si on depose la carte nimporte ou
                    // Si le drop n'a pas été pris en compte, rétablir la position initiale de la carte
                    if (!event.getResult()) {
                        carteChoisi = (TextView) event.getLocalState();//recupere la carte choisi
                        parent = (LinearLayout) carteChoisi.getParent();
                        parent.removeViewAt(0);
                        parent.addView(carteChoisi);
                        carteChoisi.setVisibility(View.VISIBLE);
                    }
                    break;
            }
            return true;
        }

        @Override
        public boolean onTouch(View view, MotionEvent event) {//1 quand je clique sur 1 carte ou sur undo
            if (view != undoButton) {
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                int sdkVersion = Build.VERSION.SDK_INT;
                if (sdkVersion <= 24)
                    view.startDrag(null, shadowBuilder, view, 0);//peut etre que dans le tp dans data: on mettra quelquechose...
                else
                    view.startDragAndDrop(null, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE); //pour cacher le jeton tant quil nest pas depose
            } else if (view == undoButton) {
                if (peutFaireUndo) {
                    Undo undo = new Undo();
                    undo.undoRemettreCarteVecteurJouer(vectorCarteJoueur, carteJoue, indexPourUndo);
                    cartePrecedementJoue = (undo.undoRecupCartePrecedente(vectorSuiteHautGauche, vectorSuiteHautDroit, vectorSuiteBasGauche, vectorSuiteBasDroit, carteJoue));

                    //AFFICHAGE CARTE JOUEUR: carte du haut
                    for (int i = 0; i < conteneurCarteJoueurHaut.getChildCount(); i++) {//on fait le tour du conteneur du haut
                        int ressource = R.drawable.carte_joueur;
                        Ecouteur ec = new Ecouteur();
                        LinearLayout t = (LinearLayout) conteneurCarteJoueurHaut.getChildAt(i);
                        if (t.getChildAt(0) == null) {//si le conteneur est null
                            int valeurNouvelleCarte = carteJoue.getValeur();
                            String valeur = String.valueOf(valeurNouvelleCarte);
                            //ajouter la carte dans la case vide
                            TextView carteN = new TextView(Jeu.this);
                            afficherCarte(t, valeur, carteN, ressource, ec);
                        }
                    }
                    //carte du bas
                    for (int i = 0; i < conteneurCarteJoueurBas.getChildCount(); i++) {//on fait le tour du conteneur du haut
                        int ressource = R.drawable.carte_joueur;
                        Ecouteur ec = new Ecouteur();
                        LinearLayout t = (LinearLayout) conteneurCarteJoueurBas.getChildAt(i);
                        if (t.getChildAt(0) == null) {//si le conteneur est null
                            int valeurNouvelleCarte = carteJoue.getValeur();
                            String valeur = String.valueOf(valeurNouvelleCarte);
                            //ajouter la carte dans la case vide
                            TextView carteN = new TextView(Jeu.this);
                            afficherCarte(t, valeur, carteN, ressource, ec);
                        }
                    }

                    undo.undoRemettreCarteDansJeuxComplet(pileJeuxCarteCompletJeuxComplet, carteJoue);
                    int valeurNouvelleCarte = cartePrecedementJoue.getValeur();
                    int couleurRetour = R.drawable.bordurecarte;//remettre en orange
                    String valeur = String.valueOf(valeurNouvelleCarte);
                    Ecouteur ec = new Ecouteur();
                    //ajouter la carte dans la case vide
                    TextView carteN = new TextView(Jeu.this);
                    conteneurOuJeDeposeLaCarte.removeViewAt(0);
                    //affichage de la carte dans la pile
                    undo.undoAfficherCarte(conteneurOuJeDeposeLaCarte, valeur, carteN, couleurRetour);
                    //mettre ecouteur sur carte
                    carteN.setOnTouchListener(ec);
                    points -= 100;
                    score.setText(String.valueOf(points));//setter laffichage du score
                    nbrCarte.setText(String.valueOf(pileJeuxCarteCompletJeuxComplet.getJeuxCarteComplet().size()));//affiche le nbr de carte qua le joueur
                    peutFaireUndo = false;
                }
            }
            return true;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        DatabaseHelper.getInstance(this).fermerBD();
    }

    public void afficherCarte(LinearLayout t, String valeur, TextView carteN, int ressource, Ecouteur ec) {
        carteN.setText(valeur);
        carteN.setTextSize(25);
        carteN.setTag("ppppp");
        carteN.setTypeface(null, Typeface.BOLD);
        carteN.setLayoutParams(new LinearLayout.LayoutParams(170, 260));
        carteN.setBackgroundResource(ressource);
        carteN.setVisibility(View.VISIBLE);
        t.addView(carteN);
        t.getChildAt(0).setOnTouchListener(ec);
    }
}

