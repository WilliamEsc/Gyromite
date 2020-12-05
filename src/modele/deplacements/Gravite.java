package modele.deplacements;

import modele.plateau.Entite;
import modele.plateau.EntiteDynamique;
import modele.plateau.Heros;

public class Gravite extends RealisateurDeDeplacement {
    private static Gravite g;

    public static Gravite getInstance() {
        if (g == null) {
            g = new Gravite();
        }
        return g;
    }
    @Override
    public boolean realiserDeplacement() {
        boolean ret = false;

        for (EntiteDynamique e : lstEntitesDynamiques) {
                Entite eBas = e.regarderDansLaDirection(Direction.bas);
                if ((e.getOldEntite() != null && !e.getOldEntite().peutPermettreDeMonterDescendre())||
                        (e.getOldEntite() == null && eBas != null && !eBas.peutServirDeSupport()) ||
                        (eBas == null && e.getOldEntite() == null)
                ) {
                    if (e.avancerDirectionChoisie(Direction.bas))
                        ret = true;
                }
        }

        return ret;
    }
}
