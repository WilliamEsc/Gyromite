package VueControleur;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

import modele.deplacements.Controle4Directions;
import modele.deplacements.ControleColonne;
import modele.deplacements.Direction;
import modele.plateau.*;


/** Cette classe a deux fonctions :
 *  (1) Vue : proposer une représentation graphique de l'application (cases graphiques, etc.)
 *  (2) Controleur : écouter les évènements clavier et déclencher le traitement adapté sur le modèle (flèches direction Pacman, etc.))
 *
 */
public class VueControleurGyromite extends JFrame implements Observer {
    private Jeu jeu; // référence sur une classe de modèle : permet d'accéder aux données du modèle pour le rafraichissement, permet de communiquer les actions clavier (ou souris)
    private int tour;

    private int sizeX; // taille de la grille affichée
    private int sizeY;

    // icones affichées dans la grille
    private ImageIcon[] icoHero=new ImageIcon[7];
    private ImageIcon[] icoBot=new ImageIcon[7];
    private ImageIcon[] icoColonneR=new ImageIcon[6];
    private ImageIcon[] icoColonneB=new ImageIcon[6];
    private ImageIcon icoBombe;
    private ImageIcon icoVide;
    private ImageIcon icoMur;
    private ImageIcon icoCorde;
    private ImageIcon icoRadis;

    private JLabel[][] tabJLabel; // cases graphique (au moment du rafraichissement, chaque case va être associée à une icône, suivant ce qui est présent dans le modèle)


    public VueControleurGyromite(Jeu _jeu) {
        sizeX = 20;
        sizeY = _jeu.SIZE_Y;
        jeu = _jeu;

        chargerLesIcones();
        placerLesComposantsGraphiques();
        ajouterEcouteurClavier();
    }

    private void ajouterEcouteurClavier() {
        addKeyListener(new KeyAdapter() { // new KeyAdapter() { ... } est une instance de classe anonyme, il s'agit d'un objet qui correspond au controleur dans MVC
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {  // on regarde quelle touche a été pressée
                    case KeyEvent.VK_LEFT : Controle4Directions.getInstance().setDirectionCourante(Direction.gauche); break;
                    case KeyEvent.VK_RIGHT : Controle4Directions.getInstance().setDirectionCourante(Direction.droite); break;
                    case KeyEvent.VK_DOWN : Controle4Directions.getInstance().setDirectionCourante(Direction.bas); break;
                    case KeyEvent.VK_UP : Controle4Directions.getInstance().setDirectionCourante(Direction.haut); break;
                    case KeyEvent.VK_A : ControleColonne.getInstance().inverserDirRouge(); break;
                    case KeyEvent.VK_E : ControleColonne.getInstance().inverserDirBleu(); break;
                    case KeyEvent.VK_P : jeu.resetPartie(); break;
                }
            }
        });
    }


    private void chargerLesIcones() {
        chargerIconesHeros();
        chargerIconesSmick();
        chargerIconesColonneR();
        chargerIconesColonneB();
        icoMur = chargerIcone("Images/Mur.png");
        icoCorde = chargerIcone("Images/Corde.png");
        icoBombe = chargerIcone("Images/Chloroquine.png");
        icoVide = chargerIcone("Images/Vide.png");
        icoRadis = chargerIcone("Images/GiletJaune.png");
    }

    private void chargerIconesHeros() {
        icoHero[0] = chargerIcone("Images/RaoultDroite1.png");
        icoHero[1] = chargerIcone("Images/RaoultDroite2.png");
        icoHero[2] = chargerIcone("Images/RaoultGauche1.png");
        icoHero[3] = chargerIcone("Images/RaoultGauche2.png");
        icoHero[4] = chargerIcone("Images/RaoultHaut1.png");
        icoHero[5] = chargerIcone("Images/RaoultHaut2.png");
        icoHero[6] = chargerIcone("Images/RaoultDefault.png");
    }

    private void chargerIconesSmick() {
        icoBot[0] = chargerIcone("Images/MacronDroite1.png");
        icoBot[1] = chargerIcone("Images/MacronDroite2.png");
        icoBot[2] = chargerIcone("Images/MacronGauche1.png");
        icoBot[3] = chargerIcone("Images/MacronGauche2.png");
        icoBot[4] = chargerIcone("Images/MacronHaut1.png");
        icoBot[5] = chargerIcone("Images/MacronHaut2.png");
        icoBot[6] = chargerIcone("Images/MacronDefault.png");
    }

    private void chargerIconesColonneR() {
        icoColonneR[0] = chargerIcone("Images/ColonneRougeVerticalTete.png");
        icoColonneR[1] = chargerIcone("Images/ColonneRougeVertical.png");
        icoColonneR[2] = chargerIcone("Images/ColonneRougeVerticalQueue.png");
        icoColonneR[3] = chargerIcone("Images/ColonneRougeHorizontalTete.png");
        icoColonneR[4] = chargerIcone("Images/ColonneRougeHorizontal.png");
        icoColonneR[5] = chargerIcone("Images/ColonneRougeHorizontalQueue.png");
    }

    private void chargerIconesColonneB() {
        icoColonneB[0] = chargerIcone("Images/ColonneBleuVerticalTete.png");
        icoColonneB[1] = chargerIcone("Images/ColonneBleuVertical.png");
        icoColonneB[2] = chargerIcone("Images/ColonneBleuVerticalQueue.png");
        icoColonneB[3] = chargerIcone("Images/ColonneBleuHorizontalTete.png");
        icoColonneB[4] = chargerIcone("Images/ColonneBleuHorizontal.png");
        icoColonneB[5] = chargerIcone("Images/ColonneBleuHorizontalQueue.png");
    }

    private ImageIcon chargerIcone(String urlIcone) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(urlIcone));
        } catch (IOException ex) {
            Logger.getLogger(VueControleurGyromite.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        return new ImageIcon(image);
    }

    private void placerLesComposantsGraphiques() {
        setTitle("Raoultmite");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // permet de terminer l'application à la fermeture de la fenêtre

        JComponent grilleJLabels = new JPanel(new GridLayout(sizeY, sizeX)); // grilleJLabels va contenir les cases graphiques et les positionner sous la forme d'une grille

        tabJLabel = new JLabel[sizeX][sizeY];
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                JLabel jlab = new JLabel();
                tabJLabel[x][y] = jlab; // on conserve les cases graphiques dans tabJLabel pour avoir un accès pratique à celles-ci (voir mettreAJourAffichage() )
                grilleJLabels.add(jlab);
            }
        }
        add(grilleJLabels);
    }

    
    /**
     * Il y a une grille du côté du modèle ( jeu.getGrille() ) et une grille du côté de la vue (tabJLabel)
     */
    private void mettreAJourAffichage() {
        if(!jeu.defaite() && !jeu.victoire()) {
            tour = (tour + 1) % 2;
            int debutScreenX=jeu.getPosHector()-10;
            if(debutScreenX<0){
                debutScreenX=0;
            }else if(debutScreenX > jeu.SIZE_X-20){
                debutScreenX=jeu.SIZE_X-20;
            }
            for (int x = 0; x < sizeX; x++) {
                for (int y = 0; y < sizeY; y++) {
                    if (jeu.getGrille()[x+debutScreenX][y] instanceof Heros) { // si la grille du modèle contient un Pacman, on associe l'icône Pacman du côté de la vue
                        tabJLabel[x][y].setIcon(afficheHeros(x+debutScreenX, y));
                    } else if (jeu.getGrille()[x+debutScreenX][y] instanceof Bot) {
                        tabJLabel[x][y].setIcon(afficheSmick(x+debutScreenX, y));
                    } else if (jeu.getGrille()[x+debutScreenX][y] instanceof ColonneRouge) {
                        tabJLabel[x][y].setIcon(afficheColonne(x+debutScreenX, y));
                    } else if (jeu.getGrille()[x+debutScreenX][y] instanceof ColonneBleu) {
                        tabJLabel[x][y].setIcon(afficheColonne(x+debutScreenX, y));
                    } else if (jeu.getGrille()[x+debutScreenX][y] instanceof Mur) {
                        tabJLabel[x][y].setIcon(icoMur);
                    } else if (jeu.getGrille()[x+debutScreenX][y] instanceof Corde) {
                        tabJLabel[x][y].setIcon(icoCorde);
                    } else if (jeu.getGrille()[x+debutScreenX][y] instanceof Bombe) {
                        tabJLabel[x][y].setIcon(icoBombe);
                    } else if (jeu.getGrille()[x+debutScreenX][y] instanceof Radis) {
                        tabJLabel[x][y].setIcon(icoRadis);
                    } else {
                        tabJLabel[x][y].setIcon(icoVide);
                    }
                }
            }
        }
    }


    private ImageIcon afficheHeros(int x,int y){
        EntiteDynamique eDyn= (EntiteDynamique) jeu.getGrille()[x][y];
        if(eDyn.getDir()==Direction.droite){
            return icoHero[tour];
        }else if(eDyn.getDir()==Direction.gauche){
            return icoHero[tour+2];
        }else if(eDyn.getOldEntite() instanceof Corde){
            return icoHero[tour+4];
        }
        return icoHero[6];
    }

    private ImageIcon afficheSmick(int x,int y){
        EntiteDynamique eDyn= (EntiteDynamique) jeu.getGrille()[x][y];
        if(eDyn.getDir()==Direction.droite){
            return icoBot[tour];
        }else if(eDyn.getDir()==Direction.gauche){
            return icoBot[tour+2];
        }else if(eDyn.getDir()==Direction.haut || eDyn.getDir()==Direction.bas){
            return icoBot[tour+4];
        }
        return icoBot[6];
    }

    private ImageIcon afficheColonne(int x,int y){
        if(jeu.getGrille()[x][y] instanceof ColonneRouge){
            Colonne eDyn= (Colonne) jeu.getGrille()[x][y];
            int dir=eDyn.getType();
            if(eDyn.getDir()==Direction.droite || eDyn.getDir()==Direction.gauche){
                dir+=3;
            }
            return icoColonneR[dir];
        }
        Colonne eDyn= (Colonne) jeu.getGrille()[x][y];
        int dir=eDyn.getType();
        if(eDyn.getDir()==Direction.droite || eDyn.getDir()==Direction.gauche){
            dir+=3;
        }
        return icoColonneB[dir];
    }


    @Override
    public void update(Observable o, Object arg) {
        mettreAJourAffichage();
        /*
        SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        mettreAJourAffichage();
                    }
                }); 
        */

    }
}
