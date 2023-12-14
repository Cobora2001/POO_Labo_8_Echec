package engine.Pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.Player;

public abstract class Piece {
    private PieceType type;
    private int x;
    private int y;
    private final Player player;


    public Piece(PieceType type, int x, int y, Player player) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.player = player;
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
        return getPlayer().getColor();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract boolean internalRule(int x, int y);

    public Player getPlayer() {
        return player;
    }

    public void changeType(PieceType type) {
        this.type = type;
    }
}
