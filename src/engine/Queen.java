// Authors: Thomas Vuilleumier, Aurélien Richard, and Stéphane Nascimento Santos

package engine;

import chess.PieceType;
import chess.PlayerColor;

/**
 * Queen class: contains the rules for the queen piece
 */
public class Queen extends Piece {
    // The ruleset for the queen piece
    private static final Ruleset ruleset = new StarMove();

    /**
     * Constructor for the queen piece
     * @param x the x coordinate of the piece
     * @param y the y coordinate of the piece
     * @param color the color of the piece
     */
    public Queen(int x, int y, PlayerColor color) {
        super(x, y, color);
    }

    /**
     * Get the type of the piece
     * @return the type of the piece
     */
    @Override
    public PieceType getPieceType() {
        return PieceType.QUEEN;
    }

    /**
     * Check if the queen can move to the given coordinates, taking into account the other pieces on the board
     * @param x: x coordinate
     * @param y: y coordinate
     * @param engine
     * @return
     */
    @Override
    public String canMove(int x, int y, Engine engine) {
        return ruleset.availableMove(this, x, y, engine);
    }

    /**
     * Update the matrix of the queen piece
     * @param toX: x coordinate
     * @param toY: y coordinate
     * @param engine engine of the game
     */
    @Override
    public void updateMatrix(int toX, int toY, Engine engine) {
        ruleset.updateMatrix(this, toX, toY, engine);

    }
}
