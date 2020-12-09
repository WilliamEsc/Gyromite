package modele.deplacements;

import modele.plateau.*;

import java.util.Random;

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
            Bot bot = (Bot) e;
            choixDir(bot);
            Direction _directionCourante = bot.getDir();
            if (_directionCourante != null) {
                switch (_directionCourante) {
                    case gauche:
                        Entite eGauche = bot.regarderDansLaDirection(_directionCourante);
                        if (!(eGauche instanceof Mur) &&
                            !(eGauche instanceof Bot) &&
                            !(eGauche instanceof Colonne) &&
                            bot.regarderDansLaDirectionBas(_directionCourante) != null)
                        {
                            //si il ne rencontre pas de radis ou si il ont été stopé assez longtemps
                            if (!(eGauche instanceof Radis) || bot.getStop() >= 5) {
                                if (bot.avancerDirectionChoisie(_directionCourante)) {
                                    ret = true;
                                    bot.setStop(0);
                                }
                            } else {
                                //incremente le compteur d'arret
                                bot.addStop(1);
                            }
                        } else {
                            e.setDir(Direction.droite);
                        }
                        break;
                    case droite:
                        Entite eDroite = bot.regarderDansLaDirection(_directionCourante);

                        if (!(eDroite instanceof Mur) &&
                            !(eDroite instanceof Bot) &&
                            !(eDroite instanceof Colonne) &&
                            bot.regarderDansLaDirectionBas(_directionCourante) != null)
                        {
                            //si il ne rencontre pas de radis ou si il ont été stopé assez longtemps

                            if (!(eDroite instanceof Radis) || bot.getStop() >= 5) {
                                if (bot.avancerDirectionChoisie(_directionCourante))
                                {
                                    ret = true;
                                    bot.setStop(0);
                                }
                            } else {
                                //incremente le compteur d'arret
                                bot.addStop(1);
                            }
                        } else {
                            e.setDir(Direction.gauche);
                        }
                        break;
                    case haut:
                        Entite eHaut = bot.regarderDansLaDirection(Direction.haut);
                        if (eHaut instanceof Corde || eHaut == null || eHaut instanceof Heros)
                        {
                            if (bot.avancerDirectionChoisie(Direction.haut))
                                ret = true;
                        }
                        break;
                    case bas:
                        Entite eBas = bot.regarderDansLaDirection(Direction.bas);
                        if (eBas instanceof Corde || eBas == null || eBas instanceof Heros)
                        {
                            if (bot.avancerDirectionChoisie(Direction.bas))
                                ret = true;
                        }
                        break;
                }
            }
        }

        return ret;
    }

    //Ia des bots: choix des directions quand ils rencontrent une corde
    private void choixDir(Bot b){
        Random rand= new Random();
        Direction[] dir=new Direction[4];
        int nbChoix=0;
        //Quand il se dirige a droite ou gauche
        if(
                b.getOldEntite() instanceof Corde &&
                        (b.getDir()==Direction.droite || b.getDir()==Direction.gauche)
        ){
            //si il est possible de monter
            if( (b.regarderDansLaDirection(Direction.haut) instanceof Corde) ||
                (b.regarderDansLaDirection(Direction.haut) instanceof Heros)
            ){
                dir[nbChoix]=Direction.haut;
                nbChoix++;
            }
            //si il est possible de monter
            if( (b.regarderDansLaDirection(Direction.bas) instanceof Corde) ||
                 (b.regarderDansLaDirection(Direction.bas) instanceof Heros)
            ){
                dir[nbChoix]=Direction.bas;
                nbChoix++;
            }
            //si il est possible d'allez a droite
            if(
               !(b.regarderDansLaDirection(Direction.droite) instanceof Mur) &&
                       b.regarderDansLaDirectionBas(Direction.droite) != null
            ){
                dir[nbChoix]=Direction.droite;
                nbChoix++;
            }
            //si il est possible d'allez a gauche
            if(
                    !(b.regarderDansLaDirection(Direction.gauche) instanceof Mur) &&
                            b.regarderDansLaDirectionBas(Direction.gauche) != null
            ){
                dir[nbChoix]=Direction.gauche;
                nbChoix++;
            }
            b.setDir(dir[rand.nextInt(nbChoix)]);
        }
        //si il n'est plus possible de monter
        else if(
                b.getOldEntite() instanceof Corde && (
                b.regarderDansLaDirection(Direction.haut) == null ||
                b.regarderDansLaDirection(Direction.haut) instanceof Mur ||
                b.regarderDansLaDirection(Direction.haut) instanceof Colonne)
        ){
            if(! (b.regarderDansLaDirection(Direction.bas) instanceof Mur)){
                dir[nbChoix]=Direction.bas;
                nbChoix++;
            }
            if(
                    !(b.regarderDansLaDirection(Direction.droite) instanceof Mur) &&
                            b.regarderDansLaDirectionBas(Direction.droite) != null
            ){
                dir[nbChoix]=Direction.droite;
                nbChoix++;
            }
            if(
                    !(b.regarderDansLaDirection(Direction.gauche) instanceof Mur) &&
                            b.regarderDansLaDirectionBas(Direction.gauche) != null
            ){
                dir[nbChoix]=Direction.gauche;
                nbChoix++;
            }
            b.setDir(dir[rand.nextInt(nbChoix)]);
        }
        //si il n'est plus possible de descendre
        else if(
                b.getOldEntite() instanceof Corde && (
                b.regarderDansLaDirection(Direction.bas) == null ||
                b.regarderDansLaDirection(Direction.bas) instanceof Mur||
                b.regarderDansLaDirection(Direction.haut) instanceof Colonne)
        ){
            if(! (b.regarderDansLaDirection(Direction.haut) instanceof Mur)){
                dir[nbChoix]=Direction.haut;
                nbChoix++;
            }
            if(
                    !(b.regarderDansLaDirection(Direction.droite) instanceof Mur) &&
                            b.regarderDansLaDirectionBas(Direction.droite) != null
            ){
                dir[nbChoix]=Direction.droite;
                nbChoix++;
            }
            if(
                    !(b.regarderDansLaDirection(Direction.gauche) instanceof Mur) &&
                            b.regarderDansLaDirectionBas(Direction.gauche) != null
            ){
                dir[nbChoix]=Direction.gauche;
                nbChoix++;
            }
            b.setDir(dir[rand.nextInt(nbChoix)]);
        }
    }
}
