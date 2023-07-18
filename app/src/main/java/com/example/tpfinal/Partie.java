package com.example.tpfinal;

import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class Partie {

    private PileJeuxCarteComplet jeuxComplet;

    //constructeur
    public Partie(PileJeuxCarteComplet jeuxComplet)
    {
        this.jeuxComplet = jeuxComplet;

    }

    public void remplirJeuCarte(PileJeuxCarteComplet pileJeuxCarteComplet)//on rempli le jeu de car avec 97 valeurs
    {
        for(int i = 1; i <= 97; i++)//97
        {
            Carte carte = new Carte(i);//on cree carte: 1, 2 ,3 ect....
            pileJeuxCarteComplet.rempliPileJeuxComplet(carte);//on rempli le vecteur quil ya dans la class pile
        }
    }

    public void donne8CarteJoueur(PileJeuxCarteComplet pileJeuxCarteComplet, Vector<Carte> carteJoueur)
    {
        //Avoir une valeur Aleatoire
        Random random = new Random();
        List<Integer> indexDejaChoisis = new ArrayList<>();
        for(int i = 0; i < 8; i ++)//on passe 8 fois
        {
            int indexAleatoire;
            do {
                indexAleatoire = random.nextInt(pileJeuxCarteComplet.getJeuxCarteComplet().size());//on a un chiffre entre 1 et 97
            } while (indexDejaChoisis.contains(indexAleatoire));//est ce que indexDejaChoisis a indexAleatoire dedans? Si oui repasse dans le do
            indexDejaChoisis.add(indexAleatoire);//Non? donc ajoute indexAleatoire

            //donner les 8 carte au joueur
            carteJoueur.add(pileJeuxCarteComplet.getJeuxCarteComplet().get(indexAleatoire));

        }


    }
    public void remplirCarteQueLonPeutDonner(PileJeuxCarteComplet pileJeuxCarteComplet, Vector<Carte> carteJoueur, Vector<Carte> carteQueLonPeutDonner)//est le vecteur des 12 cartes restantes
    {
        for (Carte carte : pileJeuxCarteComplet.getJeuxCarteComplet()) {
            if (!carteJoueur.contains(carte)) {
                carteQueLonPeutDonner.add(carte);
            }
        }
        Collections.shuffle(carteQueLonPeutDonner);//on melange les cartes
        System.out.println(carteQueLonPeutDonner);

    }

    public void donner1Carte(Vector<Carte> carteJoueur, Carte carteAdonner, int indexOuMettreCarte){
        carteJoueur.add(indexOuMettreCarte,carteAdonner);

    }

    public int verifierSiManqueCarteJoueurHaut(LinearLayout conteneurCarteJoueurHaut) {

        int nbrDeCarteManquante = 0;
        for (int i = 0; i < conteneurCarteJoueurHaut.getChildCount(); i++) {
            LinearLayout test = (LinearLayout) conteneurCarteJoueurHaut.getChildAt(i);

            if (test.getChildAt(0) == null)//si il ny a rien dans le container
            {
                nbrDeCarteManquante += 1;
                //indexCarte.add(i);//ajoute a lindexe les index pour remettre les 2 prochaines cartes
                if (nbrDeCarteManquante == 2)
                    break;

            }
        }
        return  nbrDeCarteManquante;
    }

    public int verifierSiManqueCarteJoueurBas(LinearLayout conteneurCarteJoueurBas) {

        int nbrDeCarteManquante = 0;
        for (int i = 0; i < conteneurCarteJoueurBas.getChildCount(); i++) {
            LinearLayout test = (LinearLayout) conteneurCarteJoueurBas.getChildAt(i);

            if (test.getChildAt(0) == null)//si il ny a rien dans le container
            {
                nbrDeCarteManquante += 1;
                //indexCarte.add(i);//ajoute a lindexe les index pour remettre les 2 prochaines cartes
                if (nbrDeCarteManquante == 2)
                    break;

            }
        }
        return  nbrDeCarteManquante;
    }



    // quand joueur depose une carte sur une des 4 suite = on enleve 1 carte du jeu complet
    public void enleveCartePile(PileJeuxCarteComplet pileJeuxCarteComplet, Carte carteJoue)
    {
        int valeur = carteJoue.getValeur();

        for(int i = 0; i <= pileJeuxCarteComplet.getJeuxCarteComplet().size(); i++)
        {
            int valeurCarteJeuxComplet = pileJeuxCarteComplet.getJeuxCarteComplet().get(i).getValeur();
            if(valeurCarteJeuxComplet == valeur) {//si on retrouve la valeur de la carte joue dans la pile alors on lenleve de la pile
                pileJeuxCarteComplet.enleveCarteDeLaPile(i);
                break;
            }

        }
    }

    public int recupIndex(Vector<Carte> carteJoueur, int valeurCarteChoisi)//avoir index pour replacer la carte
    {
        int indexVecteur = 0;
        for(int i = 0; i < carteJoueur.size();i++)
        {
            Carte carte = carteJoueur.get(i);
            if(carte.getValeur() == valeurCarteChoisi){
                indexVecteur = i;
                break;
            }
        }
        return  indexVecteur;

    }

    public void supprimerCarteDuJoueurJoue(Vector<Carte> carteJoueur, int valeurCarteChoisi)//enleve la carte du joueur dans le vecteur carteJoueur
    {
        int indexVecteur = 0;
        for(int i = 0; i < carteJoueur.size();i++)
        {
            Carte carte = carteJoueur.get(i);
            if(carte.getValeur() == valeurCarteChoisi){//enlever la carte qui a ete joue
                indexVecteur = i;
                carteJoueur.remove(i);
                break;
            }
        }

    }


    public boolean peuxToujoursJoueur(Vector<Carte> vectorCarteJoueur, Vector<Carte> vectorPileHautGauche, Vector<Carte> vectorPileHautDroit, Vector<Carte> vectorPileBasGauche, Vector<Carte> vectorPileBasDroit)
    {
        boolean peuxEncoreJouer = false;
        Carte carteTempHaut = new Carte(0);
        Carte carteTempBas = new Carte(100);
        if(vectorPileHautGauche.size() == 0)
            vectorPileHautGauche.add(carteTempHaut);
        if(vectorPileHautDroit.size()==0)
            vectorPileHautDroit.add(0,carteTempHaut);
        if(vectorPileBasGauche.size() == 0)
            vectorPileBasGauche.add(0, carteTempBas);
        if(vectorPileBasDroit.size() == 0)
            vectorPileBasDroit.add(0,carteTempBas);

        for(int i = 0; i < vectorCarteJoueur.size(); i++){
            Carte carte = new Carte(vectorCarteJoueur.get(i).getValeur());
            if(carte.getValeur() > vectorPileHautGauche.get(vectorPileHautGauche.size()-1).getValeur() || carte.getValeur() > vectorPileHautDroit.get(vectorPileHautDroit.size()-1).getValeur()){
                peuxEncoreJouer = true;
                break;
            }
            else if(carte.getValeur() < vectorPileBasGauche.get(vectorPileBasGauche.size()-1).getValeur() || carte.getValeur() < vectorPileBasDroit.get(vectorPileBasDroit.size()-1).getValeur()) {
                peuxEncoreJouer = true;
                break;
            }
            else peuxEncoreJouer = false;
        }
        return peuxEncoreJouer;
    }

    public boolean VerifCoupHaut(int valeurCarteJoue, int valeurCartePile)
    {
        //si la carte que je joue est > a la carte qui est sur la pile.
        //ici je met valeurCarteJoue >= valeurCartePile au cas ou le joueur decide de jouer la son premier coup avec la carte numero 1
        //comme il ne peut pas y avoir 2 fois les memes carte dans le jeu ca ne pose pas de probleme
        if(valeurCarteJoue >= valeurCartePile || valeurCarteJoue == valeurCartePile - 10)
            return true;
        return false;

    }

    public boolean VerifCoupBas(int valeurCarteJoue, int valeurCartePile)
    {
        if(valeurCarteJoue < valeurCartePile || valeurCarteJoue == valeurCartePile + 10)
            return true;
        return false;
    }

}
