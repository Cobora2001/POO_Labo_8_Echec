// Authors: Thomas Vuilleumier, Aurélien Richard, and Stéphane Nascimento Santos

package engine;

/**
 * CaresAboutObstacles.java: represents a ruleset that cares about
 * obstacles in the matrix of the engine
 */
public abstract class CaresAboutObstacles extends Ruleset{
    /**
     * Check if the piece can move to the given position
     * @param piece the piece
     * @param toX the x position
     * @param toY the y position
     * @param engine the engine
     * @return null if the piece can move to the given position, and a message explaining why it can't otherwise
     */
    public String availableMove(Piece piece, int toX, int toY, Engine engine) { // can't be static if overridden
        String response = super.availableMove(piece, toX, toY, engine);

        if(response != null) {
            return response;
        }

        if(isObstructed(piece, toX, toY, engine.getMatrix())) {
            return "The way is obstructed.";
        }
        return null;
    }

    /**
     * Check if the way the piece wants to move is obstructed
     * @param piece the piece
     * @param toX the x position
     * @param toY the y position
     * @param matrix the matrix
     * @return true if the piece can move to the given position without anything in its way,
     * false otherwise
     */
    public boolean isObstructed(Piece piece, int toX, int toY, Piece[][] matrix) {
        int fromX = piece.getX();
        int fromY = piece.getY();

        int diffX = toX - fromX;
        int diffY = toY - fromY;

        // We check if the piece is moving in a straight line
        if(Math.abs(diffX) == Math.abs(diffY)) {
            if(diffX == 0) {
                return false;
            }
        } else {
            if(diffX != 0 && diffY != 0) {
                return true;
            }
        }

        int changeX = Integer.compare(diffX, 0);
        int changeY = Integer.compare(diffY, 0);

        fromX += changeX;
        fromY += changeY;

        // We check if there is a piece in the way
        while(!(fromX == toX && fromY == toY)) {
            if(matrix[fromX][fromY] != null) {
                return true;
            }

            fromX += changeX;
            fromY += changeY;
        }

        return false;
    }
}
