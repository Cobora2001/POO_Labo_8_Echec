package engine;

import chess.PlayerColor;

public abstract class PiecesWithInitialMove extends Piece {
    private boolean hasMoved;


    public PiecesWithInitialMove(int x, int y, PlayerColor color) {
        super(x, y, color);
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setCoordinates(int x, int y) {
        super.setCoordinate(x, y);
        setHasMoved();

    }

    private void setHasMoved() {
        this.hasMoved = true;
    }
}
