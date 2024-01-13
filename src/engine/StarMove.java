/**
 * StarMove.java: This class is used to check if a move is possible in a star shape.
 */

package engine;

public class StarMove extends CaresAboutObstacles {
    static private final Ruleset cross = new CrossMove();
    static private final Ruleset diagonal = new DiagonalMove();

    /**
     * Check if a move is possible in a star shape.
     * @param piece The piece to move.
     * @param toX The x coordinate of the destination.
     * @param toY The y coordinate of the destination.
     * @return True if the move is possible, false otherwise.
     */
    public boolean movePossible(Piece piece, int toX, int toY) {
        return cross.movePossible(piece, toX, toY) || diagonal.movePossible(piece, toX, toY);
    }
}
