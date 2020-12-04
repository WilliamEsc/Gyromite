package modele.deplacements;

import modele.plateau.Entite;
import modele.plateau.EntiteDynamique;
import modele.plateau.Heros;

public class Gravite extends RealisateurDeDeplacement {
    @Override
    public boolean realiserDeplacement() {
        boolean ret = false;

        for (EntiteDynamique e : lstEntitesDynamiques) {
                Entite eBas = e.regarderDansLaDirection(Direction.bas);
                if ((e.getOldEntite() != null && !e.getOldEntite().peutPermettreDeMonterDescendre())||
                        (e.getOldEntite() == null && eBas != null && !eBas.peutServirDeSupport()) ||
                        eBas == null ) {
                    if (e.avancerDirectionChoisie(Direction.bas))
                        ret = true;
                }
        }

        return ret;
    }
}
