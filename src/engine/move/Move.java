package engine.move;

/**
 * Classe principale du package engine.move
 *
 * @author : Valentin Bugna, Theodros Mulugeta et Stéphane Nascimento Santos
 * @since  : JDK 17.0.4
 */
public abstract class Move {
    /**
     * Fonction redéfinie dans chaque sous-classe du mouvement
     * @param fromX la coordonnée de départ sur l'axe des abscisses
     * @param fromY la coordonnée de départ sur l'axe des ordonnées
     * @param toX   la coordonnée d'arrivée sur l'axe des abscisses
     * @param toY   la coordonnée d'arrivée sur l'axe des ordonnées
     * @return vrai si le mouvement correspond au nom de la sous-classe
     */
    public abstract boolean isValid(int fromX, int fromY, int toX, int toY);
}
