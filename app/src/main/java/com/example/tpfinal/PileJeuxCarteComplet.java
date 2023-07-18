package com.example.tpfinal;

import java.util.Vector;

public class PileJeuxCarteComplet {
    //reprensete les 97 carte
    private Vector<Carte> jeuxCarteComplet;

    public PileJeuxCarteComplet()
    {

        jeuxCarteComplet = new Vector();
    }

    public void rempliPileJeuxComplet(Carte carte)
    {
        jeuxCarteComplet.add(carte);
    }

    public void enleveCarteDeLaPile(int index)
    {
        jeuxCarteComplet.remove(index);
    }


    public Vector<Carte> getJeuxCarteComplet() {
        return jeuxCarteComplet;
    }



}
