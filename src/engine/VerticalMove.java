/**
 * VerticalMove.java: class that implements the vertical move of a piece
 */

package engine;

public class VerticalMove extends CaresAboutObstacles{
    /**
     * Checks if the move is possible, taking into accout the other pieces on the board
     * @param piece the piece to try to move
     * @param toX the x coordinate of the destination
     * @param toY the y coordinate of the destination
     * @return true if the move is possible, false otherwise
     */
    public boolean movePossible(Piece piece, int toX, int toY) { // can't be static if overridden
        return piece.getX() == toX;
    }
}
