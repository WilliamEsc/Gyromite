package modele.plateau;

import modele.deplacements.Direction;

public class Colonne extends EntiteDynamique {
    private int longueur;
    private int longueurMax;
    private int type;
    public Colonne(Jeu _jeu,Direction d,int n,int max,int t) {
        super(_jeu);
        directionCourante=d;
        longueur=n;
        longueurMax=max;
        type=t;
    }

    public int getType(){
        return type;
    }

    public void setLongueur(int n){
        longueur=n;
    }

    public int getLongueur(){
        return longueur;
    }

    public int getLongueurMax(){
        return longueurMax;
    }

    public boolean peutEtreEcrase() { return false; }
    public boolean peutServirDeSupport() { return true; }
    public boolean peutPermettreDeMonterDescendre() { return false; }
}
