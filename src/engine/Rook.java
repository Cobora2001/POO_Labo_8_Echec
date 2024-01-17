/**
 * Rook class: represents a rook piece in chess
 */

package engine;

import chess.PieceType;
import chess.PlayerColor;

public class Rook extends PiecesWithInitialMove {
    // The ruleset for the rook
    private static final Ruleset ruleset = new CrossMove();

    /**
     * Constructor
     * @param x     the x position of the piece
     * @param y     the y position of the piece
     * @param color the color of the piece
     */
    public Rook(int x, int y, PlayerColor color) {
        super(x, y, color);
    }

    /**
     * @return the type of the piece
     */
    @Override
    public PieceType getPieceType() {
        return PieceType.ROOK;
    }

    /**
     * Check if the rook can move to the given position, taking into account the other pieces on the board
     * @param toX: x coordinate
     * @param toY: y coordinate
     * @param engine
     * @return
     */
    @Override
    public String canMove(int toX, int toY, Engine engine) {
        return ruleset.availableMove(this, toX, toY, engine);
    }

    /**
     * Update the matrix of the engine, according to the move of the rook
     * @param toX destination x coordinate
     * @param toY destination y coordinate
     * @param engine the engine with the matrix to update
     */
    @Override
    public void updateMatrix(int toX, int toY, Engine engine) {
        ruleset.updateMatrix(this, toX, toY, engine);

    }
}