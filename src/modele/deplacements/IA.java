package modele.deplacements;

import modele.plateau.*;

public class IA extends RealisateurDeDeplacement {

    private static IA ia;

    public static IA getInstance(int r) {
        if (ia == null) {
            ia = new IA();
        }
        return ia;
    }

    protected boolean realiserDeplacement() {
        boolean ret = false;
        for (EntiteDynamique e : lstEntitesDynamiques) {
            Direction directionCourante= e.getDir();
            if (directionCourante != null){
                switch (directionCourante) {
                    case gauche:
                        Entite eGauche= e.regarderDansLaDirection(directionCourante);
                        if( !(eGauche instanceof Mur) && !(eGauche instanceof Bot) && e.regarderDansLaDirectionBas(directionCourante) != null){
                            if (e.avancerDirectionChoisie(directionCourante))
                                ret = true;
                            break;
                        } else {
                            e.setDir(Direction.droite);
                        }
                        break;
                    case droite:
                        Entite eDroite= e.regarderDansLaDirection(directionCourante);
                        if( !(eDroite instanceof Mur) && !(eDroite instanceof Bot) && e.regarderDansLaDirectionBas(directionCourante) != null){
                            if (e.avancerDirectionChoisie(directionCourante))
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
    } // TODO
}
