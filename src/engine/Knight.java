/**
 * Knight.java: Class containing the logic of the knight piece
 */

package engine;

import chess.PieceType;
import chess.PlayerColor;

public class Knight extends Piece {
    // The ruleset for the knight
    private static final Ruleset ruleset = new LetterLMove();

    /**
     * Constructor
     * @param x: initial x coordinate
     * @param y: initial y coordinate
     * @param color: color of the piece
     */
    public Knight(int x, int y, PlayerColor color) {
        super(x, y, color);
    }

    /**
     * @return the type of the piece
     */
    @Override
    public PieceType getPieceType() {
        return PieceType.KNIGHT;
    }

    /**
     * Tells if the piece can move to the given coordinates, taking into account the other pieces on the board
     * @param x: destination x coordinate
     * @param y: destination y coordinate
     * @param engine the engine to use
     * @return null if the move is valid, or a string explaining why it is not
     */
    @Override
    public String canMove(int x, int y, Engine engine) {
        return ruleset.availableMove(this, x, y, engine);
    }

    /**
     * Updates the matrix of available moves for the piece
     * @param toX: destination x coordinate
     * @param toY: destination y coordinate
     * @param engine the engine to use
     */
    @Override
    public void updateMatrix(int toX, int toY, Engine engine) {
        ruleset.updateMatrix(this, toX, toY, engine);

    }
}