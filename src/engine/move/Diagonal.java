package engine.move;

/**
 * Classe secondaire du package engine.move qui définie le mouvement diagonal
 *
 * @author : Valentin Bugna, Theodros Mulugeta et Stéphane Nascimento Santos
 * @since  : JDK 17.0.4
 */
public class Diagonal extends Move {
    @Override
    public boolean isValid(int fromX, int fromY, int toX, int toY) {
        return (Math.abs(fromX - toX) == Math.abs(fromY - toY));
    }
}
