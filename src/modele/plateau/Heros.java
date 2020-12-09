/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

import modele.deplacements.Direction;

import java.awt.*;

/**
 * Héros du jeu
 */
public class Heros extends EntiteDynamique {
    public Heros(Jeu _jeu) {
        super(_jeu);
    }

    public boolean peutEtreEcrase() { return true; }
    public boolean peutServirDeSupport() { return false; }
    public boolean peutPermettreDeMonterDescendre() { return false; };
    public boolean ramasseBombe() {return true;}

    public void deplacer(Jeu _jeu, Point pCourant, Point pCible) {
        _jeu.getGrille()[pCourant.x][pCourant.y] = getOldEntite();
        Entite cible = _jeu.getGrille()[pCible.x][pCible.y];
        if (_jeu.getGrille()[pCible.x][pCible.y] instanceof Bot) {
            _jeu.resetHeros();
        } else if (cible == null && pCible.y == _jeu.SIZE_Y - 1) {
            _jeu.resetHeros();
        } else {
            if (this.ramasseBombe() && cible instanceof Bombe) {
                _jeu.reduitBombe();
                _jeu.mapRemove(cible);
                setOldEntite(null);
            } else {
                setOldEntite(cible);
            }
            _jeu.getGrille()[pCible.x][pCible.y] = this;
            _jeu.mapPut(this, pCible);
        }
    }
}
