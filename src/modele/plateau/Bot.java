/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

import modele.deplacements.Direction;

import java.util.Random;

/**
 * Ennemis (Smicks)
 */
public class Bot extends EntiteDynamique {
    private Random r = new Random();
    private Direction directionCourante;

    public Direction getDir(){ return directionCourante;}
    public void setDir(Direction d){ directionCourante=d;}

    public Random getR(){
        return r;
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
    public boolean peutServirDeSupport() { return true; }
    public boolean peutPermettreDeMonterDescendre() { return false; };
}
