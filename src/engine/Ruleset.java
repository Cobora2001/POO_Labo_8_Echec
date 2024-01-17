// Authors: Thomas Vuilleumier, Aurélien Richard, and Stéphane Nascimento Santos

package engine;

/**
 * Ruleset is an abstract class that defines the rules for the movement of a piece.
 */
public abstract class Ruleset {

    /**
     * Check if the move is possible, taking into account the state of the game but not the consequences of the move
     * (e.g. could lead to our king being in check)
     * @param piece the piece to try to move
     * @param toX the x coordinate of the destination
     * @param toY the y coordinate of the destination
     * @param engine the engine of the game
     * @return null if the move is possible, a string explaining why it is not possible otherwise
     */
     public String availableMove(Piece piece, int toX, int toY, Engine engine) { // can't be static if overridden
        if(!movePossible(piece, toX, toY)) {
            return "This piece can't move in such a way.";
        }
        return null;
    }

    /**
     * Check if the piece can move from where it is to the given coordinates mechanically,
     * without taking the board into account
     * @param piece the piece to try to move
     * @param toX the x coordinate of the destination
     * @param toY the y coordinate of the destination
     * @return true if the move is possible, false otherwise
     */
    abstract public boolean movePossible(Piece piece, int toX, int toY);

    /**
     * Update the matrix of the engine to reflect the move of the piece
     * @param piece the piece to move
     * @param toX the x coordinate of the destination
     * @param toY the y coordinate of the destination
     * @param engine the engine of the game
     */
    public void updateMatrix(Piece piece, int toX, int toY, Engine engine) {
        engine.getMatrix()[piece.getX()][piece.getY()] = null;
        engine.getMatrix()[toX][toY] = piece;
    }
}
