// Authors: Thomas Vuilleumier, Aurélien Richard, and Stéphane Nascimento Santos

package engine;

/**
 * CrossMove.java: a class implementing a cross move
 */
public class CrossMove extends CaresAboutObstacles {
    /**
     * Tests if a move is possible without taking into account the other pieces on the board
     * @param piece the piece to try to move
     * @param toX the x coordinate of the destination
     * @param toY the y coordinate of the destination
     * @return true if the move is possible, false otherwise
     */
    public boolean movePossible(Piece piece, int toX, int toY) { // can't be static if overridden
        return piece.getY() == toY || piece.getX() == toX;
    }
}