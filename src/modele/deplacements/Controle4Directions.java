package modele.deplacements;

import modele.plateau.Entite;
import modele.plateau.EntiteDynamique;
import modele.plateau.Corde;

import java.util.ArrayList;

/**
 * Controle4Directions permet d'appliquer une direction (connexion avec le
 * clavier) à un ensemble d'entités dynamiques
 */
public class Controle4Directions extends RealisateurDeDeplacement {

    // Design pattern singleton
    private static Controle4Directions c3d;

    public static Controle4Directions getInstance() {
        if (c3d == null) {
            c3d = new Controle4Directions();
        }
        return c3d;
    }

    public void setDirectionCourante(Direction _directionCourante) {
        for (EntiteDynamique e : lstEntitesDynamiques) {
            e.setDir(_directionCourante);
        }
    }

    public boolean realiserDeplacement() {
        boolean ret = false;
        for (EntiteDynamique e : lstEntitesDynamiques) {
            Direction _directionCourante= e.getDir();
            if (_directionCourante != null)
                switch (_directionCourante) {
                    case gauche:
                    case droite:
                        if (e.avancerDirectionChoisie(_directionCourante))
                            ret = true;
                        break;

                    case haut:
                        // on ne peut pas sauter
                        Entite eHaut = e.regarderDansLaDirection(_directionCourante);
                        if (eHaut instanceof Corde) {
                            if (e.avancerDirectionChoisie(_directionCourante))
                                ret = true;
                        }
                        break;
                    case bas:
                        Entite eBas = e.regarderDansLaDirection(_directionCourante);
                        if (eBas instanceof Corde || eBas == null) {
                            if (e.avancerDirectionChoisie(_directionCourante))
                                ret = true;
                        }
                        break;
                }
        }

        return ret;

    }

    public void resetDirection() {
        for (EntiteDynamique e : lstEntitesDynamiques) {
            e.setDir(null);
        }
    }
}
