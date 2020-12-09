/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

import modele.deplacements.*;

import java.awt.Point;
import java.util.HashMap;

/** Actuellement, cette classe gère les postions
 * (ajouter conditions de victoire, chargement du plateau, etc.)
 */
public class Jeu {

    public static final int SIZE_X = 40;
    public static final int SIZE_Y = 10;

    // compteur de déplacements horizontal et vertical (1 max par défaut, à chaque pas de temps)
    private HashMap<Entite, Integer> cmptDeplH = new HashMap<Entite, Integer>();
    private HashMap<Entite, Integer> cmptDeplV = new HashMap<Entite, Integer>();

    private Point spawn=new Point(3,4);
    private Heros hector;
    private Bot smick;
    private Bot smick2;
    private int vie;
    private int nbBombe;

    private HashMap<Entite, Point> map = new  HashMap<Entite, Point>(); // permet de récupérer la position d'une entité à partir de sa référence
    private Entite[][] grilleEntites = new Entite[SIZE_X][SIZE_Y]; // permet de récupérer une entité à partir de ses coordonnées

    private Ordonnanceur ordonnanceur = new Ordonnanceur(this);

    public Jeu() {
        initialisationDesEntites();
    }

    public void resetCmptDepl() {
        cmptDeplH.clear();
        cmptDeplV.clear();
    }

    public void resetPartie(){
        map.clear();
        grilleEntites=new Entite[SIZE_X][SIZE_Y];
        Controle4Directions.getInstance().resetLst();
        ControleColonne.getInstance().resetLst();
        IA.getInstance().resetLst();
        Gravite.getInstance().resetLst();
        ordonnanceur.resetlstDepl();
        initialisationDesEntites();
    }

    public boolean victoire() {
        if(nbBombe == 0)
        {
            return true;
        }
        return false;
    }

    public boolean defaite() {
        if(vie == 0)
        {
            return true;
        }
        return false;
    }

    public void start(long _pause) {
        ordonnanceur.start(_pause);
    }
    
    public Entite[][] getGrille() {
        return grilleEntites;
    }
    
    public Heros getHector() {
        return hector;
    }
    public int getPosHector() {
        return map.get(hector).x;
    }
    public void reduitBombe(){
        nbBombe--;
    }

    private void initialisationDesEntites() {
        vie=3;
        nbBombe=0;
        hector = new Heros(this);
        addEntite(hector, spawn.x, spawn.y);

        smick = new Bot(this);
        addEntite(smick, 8, 8);

        smick2 = new Bot(this);
        addEntite(smick2, 1, 8);



        IA.getInstance().addEntiteDynamique(smick);
        IA.getInstance().addEntiteDynamique(smick2);
        ordonnanceur.add(IA.getInstance());


        ordonnanceur.add(createColonneRouge(Direction.bas,new Point(1,5),3,2));
        ordonnanceur.add(createColonneRouge(Direction.bas,new Point(14,6),3,3));
        ordonnanceur.add(createColonneRouge(Direction.haut,new Point(2,7),2,2));
        ordonnanceur.add(createColonneRouge(Direction.haut,new Point(3,7),2,2));
        ordonnanceur.add(createColonneBleu(Direction.droite,new Point(5,3),2,1));
        ordonnanceur.add(createColonneBleu(Direction.bas,new Point(11,3),3,2));

        Gravite.getInstance().addEntiteDynamique(hector);
        Gravite.getInstance().addEntiteDynamique(smick);
        Gravite.getInstance().addEntiteDynamique(smick2);
        ordonnanceur.add(Gravite.getInstance());

        Controle4Directions.getInstance().addEntiteDynamique(hector);
        ordonnanceur.add(Controle4Directions.getInstance());


        // murs extérieurs horizontaux
        for (int x = 0; x < SIZE_X; x++) {
            addEntite(new Mur(this), x, 0);
            addEntite(new Mur(this), x, SIZE_Y -1);
        }


        // murs extérieurs verticaux
        for (int y = 1; y < SIZE_Y; y++) {
            addEntite(new Mur(this), 0, y);
            addEntite(new Mur(this), SIZE_X -1, y);
        }
        grilleEntites[5][SIZE_Y -1]=null;//crée un trou dans le sol en bas
        addEntite(new Bombe(this), 6, 8);
        nbBombe++;
        addEntite(new Bombe(this), 7, 5);
        nbBombe++;
        addEntite(new Bombe(this), 18, 2);
        nbBombe++;
        addEntite(new Bombe(this), 38, 8);
        nbBombe++;

        addEntite(new Radis(this),7,8);
        addEntite(new Radis(this),22,5);

        createMur(Direction.droite,new Point(2,5),2);
        createMur(Direction.droite,new Point(2,3),2);
        createMur(Direction.droite,new Point(7,3),4);
        createMur(Direction.droite,new Point(13,3),6);
        createMur(Direction.haut,new Point(6,5),2);
        createMur(Direction.droite,new Point(6,6),8);
        createMur(Direction.droite,new Point(16,6),7);

        createCorde(Direction.haut,new Point(4,8),5);
        createCorde(Direction.haut,new Point(15,8),4);
    }

    private void createMur(Direction d,Point p,int grandeur){
        for (int i=grandeur;i>0;i--){
            addEntite(new Mur(this), p.x, p.y);
            p=calculerPointCible(p,d);
        }
    }

    private void createCorde(Direction d,Point p,int grandeur){
        for (int i=grandeur;i>0;i--){
            addEntite(new Corde(this), p.x, p.y);
            p=calculerPointCible(p,d);
        }
    }

    private ControleColonne createColonneRouge(Direction d,Point p,int grandeurCol,int nbDeplMax){
        ColonneRouge cRed;
        for (int i=grandeurCol;i>0;i--){
            if(i==grandeurCol){
                if (d==Direction.haut || d==Direction.gauche ){
                    cRed=new ColonneRouge(this,d,0,nbDeplMax,2);
                }else{
                    cRed=new ColonneRouge(this,d,0,nbDeplMax,0);
                }
            }else if(i==1){
                if (d==Direction.haut || d==Direction.gauche ){
                    cRed=new ColonneRouge(this,d,0,nbDeplMax,0);
                }else{
                    cRed=new ColonneRouge(this,d,0,nbDeplMax,2);
                }
            }else {
                cRed=new ColonneRouge(this,d,0,nbDeplMax,1);
            }
            addEntite(cRed,p.x,p.y);
            ControleColonne.getInstance().addEntiteDynamique(cRed);
            p=calculerPointCible(p,d);
        }
        return ControleColonne.getInstance();
    }

    private ControleColonne createColonneBleu(Direction d,Point p,int grandeurCol,int nbDeplMax){
        ColonneBleu cBleu;
        for (int i=grandeurCol;i>0;i--){
            if(i==grandeurCol){
                if (d==Direction.haut || d==Direction.gauche ){
                    cBleu=new ColonneBleu(this,d,0,nbDeplMax,2);
                }else{
                    cBleu=new ColonneBleu(this,d,0,nbDeplMax,0);
                }
            }else if(i==1){
                if (d==Direction.haut || d==Direction.gauche ){
                    cBleu=new ColonneBleu(this,d,0,nbDeplMax,0);
                }else{
                    cBleu=new ColonneBleu(this,d,0,nbDeplMax,2);
                }
            }else {
                cBleu=new ColonneBleu(this,d,0,nbDeplMax,1);
            }
            addEntite(cBleu,p.x,p.y);
            ControleColonne.getInstance().addEntiteDynamique(cBleu);
            p=calculerPointCible(p,d);
        }
        return ControleColonne.getInstance();
    }

    public void mapRemove(Entite e){
        map.remove(e);
    }

    public void mapPut(Entite e,Point p){
        map.put(e,p);
    }

    public Entite resetHeros(){
        Entite ret=hector.getOldEntite();
        vie--;
        hector.setOldEntite(null);
        map.remove(hector);
        if(grilleEntites[spawn.x][spawn.y] instanceof EntiteDynamique){
            addEntite(hector, spawn.x, spawn.y-2);
        }else{
            addEntite(hector, spawn.x, spawn.y);
        }
        return ret;
    }

    public Entite killBot(EntiteDynamique e){
        Entite ret=e.getOldEntite();
        e.setOldEntite(null);
        e.setDir(null);
        return ret;
    }

    private void addEntite(Entite e, int x, int y) {
        grilleEntites[x][y] = e;
        map.put(e, new Point(x, y));
    }
    
    /** Permet par exemple a une entité  de percevoir sont environnement proche et de définir sa stratégie de déplacement
     *
     */
    public Entite regarderDansLaDirection(Entite e, Direction d) {
        Point positionEntite = map.get(e);
        return objetALaPosition(calculerPointCible(positionEntite, d));
    }

    public Entite regarderDansLaDirectionBas(Entite e, Direction d) {
        Point positionEntite = map.get(e);
        return objetALaPosition(calculerPointCible(calculerPointCible(positionEntite, d),Direction.bas));
    }
    
    /** Si le déplacement de l'entité est autorisé (pas de mur ou autre entité), il est réalisé
     * Sinon, rien n'est fait.
     */
    public boolean deplacerEntite(EntiteDynamique e, Direction d) {
        if(defaite() || victoire()){
            return true;
        }
        boolean retour = false;
        
        Point pCourant = map.get(e);
        
        Point pCible = calculerPointCible(pCourant, d);
        
        if (contenuDansGrille(pCible) &&
                (objetALaPosition(pCible) == null ||
                    objetALaPosition(pCible) instanceof Corde ||
                        objetALaPosition(pCible) instanceof Radis ||
                        objetALaPosition(pCible).peutEtreEcrase())) { // a adapter (collisions murs, etc.)
            // compter le déplacement : 1 deplacement horizontal et vertical max par pas de temps par entité

            switch (d) {
                case bas: case haut:
                    if (cmptDeplV.get(e) == null) {
                        cmptDeplV.put(e, 1);

                        retour = true;
                    }
                    break;
                case gauche: case droite:
                    if (cmptDeplH.get(e) == null) {
                        cmptDeplH.put(e, 1);
                        retour = true;
                    }
                    break;
            }
        }

        if (retour) {
            e.deplacer(this,pCourant, pCible);
        }

        return retour;
    }
    
    
    private Point calculerPointCible(Point pCourant, Direction d) {
        Point pCible = null;

        switch(d) {
            case haut: pCible = new Point(pCourant.x, pCourant.y - 1); break;
            case bas : pCible = new Point(pCourant.x, pCourant.y + 1); break;
            case gauche : pCible = new Point(pCourant.x - 1, pCourant.y); break;
            case droite : pCible = new Point(pCourant.x + 1, pCourant.y); break;
        }
        
        return pCible;
    }
    
    /** Indique si p est contenu dans la grille
     */
    private boolean contenuDansGrille(Point p) {
        return p.x >= 0 && p.x < SIZE_X && p.y >= 0 && p.y < SIZE_Y;
    }
    
    private Entite objetALaPosition(Point p) {
        Entite retour = null;
        
        if (contenuDansGrille(p)) {
            retour = grilleEntites[p.x][p.y];
        }
        
        return retour;
    }

    public Ordonnanceur getOrdonnanceur() {
        return ordonnanceur;
    }
}
