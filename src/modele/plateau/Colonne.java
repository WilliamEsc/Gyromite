package modele.plateau;

import modele.deplacements.Direction;

public class Colonne extends EntiteDynamique {
    private int longueur;
    private int longueurMax;
    public Colonne(Jeu _jeu,Direction d,int n,int max) { super(_jeu); directionCourante=d; longueur=n;longueurMax=max; }

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
