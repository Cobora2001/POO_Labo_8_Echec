package engine;

import chess.PieceType;
import chess.PlayerColor;

public class Piece {
    private final PieceType type;

    private int x;
    private int y;

    private final PlayerColor color;

    private final Ruleset ruleset;

    public Piece(PieceType type, int x, int y, PlayerColor color, Ruleset ruleset) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.color = color;
        this.ruleset = ruleset;
    }

    public PieceType getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public PlayerColor getColor() {
        return color;
    }

    public Ruleset getRuleset() {
        return ruleset;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
