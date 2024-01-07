package engine;

import chess.PlayerColor;

public abstract class PiecesWithInitialMove extends Piece {
    private boolean hasMoved = false;

    public PiecesWithInitialMove(int x, int y, PlayerColor color) {
        super(x, y, color);
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setCoordinate(int x, int y) {
        if(x != this.getX() || y != this.getY())
            setHasMoved();
        super.setCoordinate(x, y);
    }

    private void setHasMoved() {
        this.hasMoved = true;
    }
}
