/**
 * Bishop class: a class that represents a bishop piece
 */

package engine;

import chess.PieceType;
import chess.PlayerColor;

public class Bishop extends Piece {
    // The ruleset for the bishop
    private static final Ruleset ruleset = new DiagonalMove();

    /**
     * Constructor
     * @param x the x position
     * @param y the y position
     * @param color the color of the piece
     */
    public Bishop(int x, int y, PlayerColor color) {
        super(x, y, color);
    }

    /**
     * Get the piece type
     * @return the piece type
     */
    @Override
    public PieceType getPieceType() {
        return PieceType.BISHOP;
    }

    /**
     * Check if the bishop can move to the given position
     * @param toX the x position
     * @param toY the y position
     * @param engine the engine
     * @return null if the bishop can move to the given position, and a message explaining why it can't otherwise
     */
    @Override
    public String canMove(int toX, int toY, Engine engine) {
        return ruleset.availableMove(this, toX, toY, engine);
    }

    /**
     * Update the matrix of the engine according to the bishop move
     * @param toX the x position
     * @param toY the y position
     * @param engine the engine
     */
    @Override
    public void updateMatrix(int toX, int toY, Engine engine) {
        ruleset.updateMatrix(this, toX, toY, engine);
    }
}