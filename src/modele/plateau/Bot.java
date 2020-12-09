/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

import modele.deplacements.Direction;

import java.awt.*;
import java.util.Random;

/**
 * Ennemis (Smicks)
 */
public class Bot extends EntiteDynamique {
    private Random r = new Random();
    private int stop=0;

    public Random getR(){
        return r;
    }
    public int getStop(){
        return stop;
    }
    public void setStop(int n){
        stop=n;
    }
    public void addStop(int n){
        stop+=n;
    }

    public Bot(Jeu _jeu) {
        super(_jeu);
        switch (r.nextInt(2)) {
            case 0:
                directionCourante = Direction.droite;
                break;
            case 1:
                directionCourante = Direction.gauche;
                break;
        }
    }

    public boolean peutEtreEcrase() { return true; }
    public boolean peutServirDeSupport() { return false; }
    public boolean peutPermettreDeMonterDescendre() { return false; }

    public void deplacer(Jeu _jeu, Point pCourant, Point pCible){
        _jeu.getGrille()[pCourant.x][pCourant.y] = getOldEntite();
        Entite cible=_jeu.getGrille()[pCible.x][pCible.y];
        if( cible instanceof Radis ){
            _jeu.mapRemove(cible);
            setOldEntite(null);
        }else if(cible instanceof Heros){
            setOldEntite(_jeu.resetHeros());
        }else{
            setOldEntite(cible);
        }
        _jeu.getGrille()[pCible.x][pCible.y] = this;
        _jeu.mapPut(this,pCible);
    }
}
