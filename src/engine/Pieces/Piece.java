package engine.Pieces;

import chess.PieceType;
import chess.PlayerColor;

public abstract class Piece {
    private PieceType type;
    private PlayerColor color;
    private int x;
    private int y;


    public Piece(PieceType type, PlayerColor color, int x, int y) {
        this.type = type;
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public boolean move(int x, int y) {
        if(!internalRule(x, y)) {
            return false;
        }
        this.x = x;
        this.y = y;
        return true;
    }

    public PieceType getType() {
        return type;
    }

    public PlayerColor getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract boolean internalRule(int x, int y);
}
