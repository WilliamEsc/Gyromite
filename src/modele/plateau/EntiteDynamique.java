package modele.plateau;

import modele.deplacements.Direction;

/**
 * Entités amenées à bouger (colonnes, ennemis)
 */
public abstract class EntiteDynamique extends Entite {
    protected Entite oldEntite;
    protected Direction directionCourante;

    public EntiteDynamique(Jeu _jeu) {
        super(_jeu);
        oldEntite=null;
    }

    public Entite getOldEntite(){
        return oldEntite;
    }
    public void setOldEntite(Entite e){
        oldEntite=e;
    }
    public boolean avancerDirectionChoisie(Direction d) {
        return jeu.deplacerEntite(this, d);
    }
    public Entite regarderDansLaDirection(Direction d) { return jeu.regarderDansLaDirection(this, d); }
    public Entite regarderDansLaDirectionBas(Direction d) { return jeu.regarderDansLaDirectionBas(this, d); }
    public boolean ramasseBombe() {return false;}



    public Direction getDir(){ return directionCourante;}
    public void setDir(Direction d){ directionCourante=d;}
}
