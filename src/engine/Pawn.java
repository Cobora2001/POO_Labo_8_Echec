/**
 * Pawn class: represents a pawn piece
 */

package engine;

import chess.PieceType;
import chess.PlayerColor;

public class Pawn extends PiecesWithInitialMove {
    // Will be the pawn that has moved two squares in the last turn, if any
    static private Pawn enPassant;
    private static final Ruleset ruleset = new PawnMove();

    /**
     * @return the type of the piece
     */
    @Override
    public PieceType getPieceType() {
        return PieceType.PAWN;
    }

    /**
     * Can the piece move to the given coordinates?
     * @param toX the x coordinate of the destination
     * @param toY the y coordinate of the destination
     * @param engine the engine to use
     * @return a string describing the error if the move is not possible, null otherwise
     */
    @Override
    public String canMove(int toX, int toY, Engine engine) {
        return ruleset.availableMove(this, toX, toY, engine);

    }

    /**
     * Update the matrix of the engine to reflect the move of the piece
     * @param toX the x coordinate of the destination
     * @param toY the y coordinate of the destination
     * @param engine the engine to use
     */
    @Override
    public void updateMatrix(int toX, int toY, Engine engine) {
        ruleset.updateMatrix(this, toX, toY, engine);

    }

    /**
     * Constructor
     * @param x the x coordinate of the piece
     * @param y the y coordinate of the piece
     * @param color the color of the piece
     */
    public Pawn(int x, int y, PlayerColor color) {
        super(x, y, color);
    }

    /**
     * Set the en passant pawn to null
     */
    static public void resetEnPassant() {
        enPassant = null;
    }

    /**
     * @return the en passant pawn
     */
    static public Pawn getEnPassant() {
        return enPassant;
    }

    /**
     * Set the en passant pawn
     * @param pawn the pawn to set
     */
    static public void setEnPassant(Pawn pawn) {
        enPassant = pawn;
    }
}
