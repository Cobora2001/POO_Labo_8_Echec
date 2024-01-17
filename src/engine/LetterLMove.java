// Authors: Thomas Vuilleumier, Aurélien Richard, and Stéphane Nascimento Santos

package engine;

/**
 * LetterLMove.java: Class containing the rules for the LetterLMove
 */
public class LetterLMove extends Ruleset {
    /**
     * Checks if the move is possible for the piece, without taking into account the other pieces
     * @param piece the piece to try to move
     * @param toX the x coordinate of the destination
     * @param toY the y coordinate of the destination
     * @return true if the move is possible, false otherwise
     */
    public boolean movePossible(Piece piece, int toX, int toY) {
        int diffX = Math.abs(toX - piece.getX());
        int diffY = Math.abs(toY - piece.getY());

        return diffX == 2 && diffY == 1 || diffX == 1 && diffY == 2;
    }
}
