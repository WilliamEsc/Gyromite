package modele.deplacements;

import modele.plateau.EntiteDynamique;
import modele.plateau.Heros;

import java.util.ArrayList;

/**
Tous les déplacement sont déclenchés par cette classe (gravité, controle clavier, IA, etc.)
 */
public abstract class RealisateurDeDeplacement {

    protected ArrayList<EntiteDynamique> lstEntitesDynamiques = new ArrayList<EntiteDynamique>();
    protected abstract boolean realiserDeplacement();

    public void addEntiteDynamique(EntiteDynamique ed) {lstEntitesDynamiques.add(ed);}
    public void resetLst(){
        lstEntitesDynamiques= new ArrayList<EntiteDynamique>();
    }
    public void delHector(){
        for(EntiteDynamique e: lstEntitesDynamiques){
            if(e instanceof Heros){
                lstEntitesDynamiques.remove(e);
            }
        }
    }
}
