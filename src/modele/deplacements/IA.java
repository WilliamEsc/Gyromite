package modele.deplacements;

import modele.plateau.*;

public class IA extends RealisateurDeDeplacement {

    private static IA ia;

    public static IA getInstance() {
        if (ia == null) {
            ia = new IA();
        }
        return ia;
    }

    protected boolean realiserDeplacement() {
        boolean ret = false;
        for (EntiteDynamique e : lstEntitesDynamiques) {
            Direction _directionCourante= e.getDir();
            if (_directionCourante != null){
                switch (_directionCourante) {
                    case gauche:
                        Entite eGauche= e.regarderDansLaDirection(_directionCourante);
                        if( !(eGauche instanceof Mur) && !(eGauche instanceof Bot) && !(eGauche instanceof Colonne) && e.regarderDansLaDirectionBas(_directionCourante) != null){
                            if (e.avancerDirectionChoisie(_directionCourante))
                                ret = true;
                            break;
                        } else {
                            e.setDir(Direction.droite);
                        }
                        break;
                    case droite:
                        Entite eDroite= e.regarderDansLaDirection(_directionCourante);
                        if( !(eDroite instanceof Mur) && !(eDroite instanceof Bot) && !(eDroite instanceof Colonne) && e.regarderDansLaDirectionBas(_directionCourante) != null){
                            if (e.avancerDirectionChoisie(_directionCourante))
                                ret = true;
                        } else{
                            e.setDir(Direction.gauche);
                        }
                        break;
                    case bas:
                        Entite eBas = e.regarderDansLaDirection(Direction.bas);
                        if (eBas instanceof Corde || eBas == null) {
                            if (e.avancerDirectionChoisie(Direction.bas))
                                ret = true;
                        }
                        break;
                }
            }
        }

        return ret;
    }
}
