package modele.plateau;

public class Radis extends EntiteStatique{
    public Radis(Jeu _jeu) {
        super(_jeu);
    }

    public boolean peutServirDeSupport() { return false; }
}
