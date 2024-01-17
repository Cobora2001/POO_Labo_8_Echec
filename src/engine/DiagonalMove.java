// Authors: Thomas Vuilleumier, Aurélien Richard, and Stéphane Nascimento Santos

package engine;

/**
 * DiagonalMove.java: class describing the diagonal move of a piece
 */
public class DiagonalMove extends CaresAboutObstacles {
    /**
     * Tests if the move is possible, without taking into account the other pieces
     * @param piece the piece to try to move
     * @param toX the x coordinate of the destination
     * @param toY the y coordinate of the destination
     * @return true if the move is possible, false otherwise
     */
    public boolean movePossible(Piece piece, int toX, int toY) {
        int diffX = toX - piece.getX();
        int diffY = toY - piece.getY();

        if(Math.abs(diffX) != Math.abs(diffY)) {
            return false;
        }

        return true;
    }

}
