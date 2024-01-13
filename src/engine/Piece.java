/**
 * Piece: abstract class for all pieces
 */

package engine;

import chess.PieceType;
import chess.PlayerColor;

abstract public class Piece {
    // coordinates of the piece
    private int x;
    private int y;

    private final PlayerColor color;

    /**
     * Constructor
     * @param x: x coordinate
     * @param y: y coordinate
     * @param color: color of the piece
     */
    public Piece( int x, int y, PlayerColor color) {
        this.x = x;
        this.y = y;
        this.color = color;

    }

    /**
     * Get the type of the piece
     * @return the type of the piece
     */
    abstract public PieceType getPieceType();

    /**
     * Get the x coordinate of the piece
     * @return the x coordinate of the piece
     */
    public int getX() {
        return x;
    }

    /**
     * Get the y coordinate of the piece
     * @return the y coordinate of the piece
     */
    public int getY() {
        return y;
    }

    /**
     * Get the color of the piece
     * @return the color of the piece
     */
    public PlayerColor getColor() {
        return color;
    }

    /**
     * Set the coordinates of the piece
     * @param x: x coordinate
     * @param y: y coordinate
     */
    public void setCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Set the coordinates of the piece
     * @param x: x coordinate
     * @param y: y coordinate
     */
    abstract public String canMove(int x, int y, Engine engine) ;
       // return ruleset.availableMove(this, x, y, engine);

    /**
     * Update the matrix of the engine according to the move of the piece
     * @param toX destination x coordinate
     * @param toY destination y coordinate
     * @param engine the engine with the matrix to update
     */
    abstract public void updateMatrix(int toX, int toY, Engine engine) ;
       // ruleset.updateMatrix(this, toX, toY, engine);
}

