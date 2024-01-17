// Authors: Thomas Vuilleumier, Aurélien Richard, and Stéphane Nascimento Santos

package engine;

import chess.PlayerColor;

/**
 * PiecesWithInitialMove.java: Abstract class for pieces that have an initial move (i.e. rook, king, pawn)
 */
public abstract class PiecesWithInitialMove extends Piece {
    // True if the piece has moved already, false otherwise
    private boolean hasMoved = false;

    /**
     * Constructor for PiecesWithInitialMove
     * @param x x coordinate of the piece
     * @param y y coordinate of the piece
     * @param color color of the piece
     */
    public PiecesWithInitialMove(int x, int y, PlayerColor color) {
        super(x, y, color);
    }

    /**
     * Getter for hasMoved
     * @return true if the piece has moved, false otherwise
     */
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * Setter for the coordinate of the piece, also sets hasMoved to true if the piece has not moved yet if it is moved
     * @param x x coordinate of the piece
     * @param y y coordinate of the piece
     */
    public void setCoordinate(int x, int y) {
        if(!hasMoved && x != this.getX() || y != this.getY()) {
            setHasMoved();
        }
        super.setCoordinate(x, y);
    }

    /**
     * Setter for hasMoved
     */
    private void setHasMoved() {
        this.hasMoved = true;
    }

}
