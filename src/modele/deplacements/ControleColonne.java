package modele.deplacements;

import modele.plateau.*;

/**
 * A la reception d'une commande, toutes les cases (EntitesDynamiques) des colonnes se déplacent dans la direction définie
 * (vérifier "collisions" avec le héros)
 */
public class ControleColonne extends RealisateurDeDeplacement {
    private static ControleColonne cc;

    public static ControleColonne getInstance() {
        if (cc == null) {
            cc = new ControleColonne();
        }
        return cc;
    }

    public void inverserDirRouge(){
        for (EntiteDynamique e : lstEntitesDynamiques) {
            if(e instanceof ColonneRouge){
                Colonne col=(Colonne) e;
                Direction directionCourante= e.getDir();
                if (directionCourante != null){
                    col.setLongueur(col.getLongueurMax()-col.getLongueur());
                    switch (directionCourante){
                        case gauche: e.setDir(Direction.droite) ; break;
                        case droite: e.setDir(Direction.gauche); break;
                        case haut: e.setDir(Direction.bas); break;
                        case bas: e.setDir(Direction.haut); break;
                    }
                }
            }
        }
    }

    public void inverserDirBleu(){
        for (EntiteDynamique e : lstEntitesDynamiques) {
            if(e instanceof ColonneBleu){
                Colonne col=(Colonne) e;
                Direction directionCourante= e.getDir();
                if (directionCourante != null){
                    col.setLongueur(col.getLongueurMax()-col.getLongueur());
                    switch (directionCourante){
                        case gauche: e.setDir(Direction.droite) ; break;
                        case droite: e.setDir(Direction.gauche); break;
                        case haut: e.setDir(Direction.bas); break;
                        case bas: e.setDir(Direction.haut); break;
                    }
                }
            }
        }
    }

    protected boolean realiserDeplacement() {
        boolean ret = false;
        for (EntiteDynamique e : lstEntitesDynamiques) {
            Colonne col=(Colonne) e;
            Direction directionCourante= col.getDir();
            if (directionCourante != null && col.getLongueur()>0){
                if (e.avancerDirectionChoisie(directionCourante)){
                    col.setLongueur(col.getLongueur()-1);
                    ret = true;
                }
            }
        }

        return ret;
    }
}
