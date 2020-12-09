package modele.plateau;

import modele.deplacements.Direction;

import java.awt.*;

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

    public void deplacer(Jeu _jeu, Point pCourant, Point pCible) {
        _jeu.getGrille()[pCourant.x][pCourant.y] = getOldEntite();
        EntiteDynamique cible = (EntiteDynamique) _jeu.getGrille()[pCible.x][pCible.y];
        if (cible != null) {
            Entite eDir = cible.regarderDansLaDirection(getDir());
            if (eDir instanceof Mur || eDir instanceof Colonne) {
                if (cible instanceof Heros)
                    setOldEntite(_jeu.resetHeros());
                if (cible instanceof Bot)
                    setOldEntite(_jeu.killBot(cible));
            } else {
                cible.avancerDirectionChoisie(getDir());
            }
        } else {
            setOldEntite(_jeu.getGrille()[pCible.x][pCible.y]);
        }
        _jeu.getGrille()[pCible.x][pCible.y] = this;
        _jeu.mapPut(this, pCible);
    }
}
