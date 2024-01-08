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
        if(!hasMoved && x != this.getX() || y != this.getY()) {
            System.out.println(hasMoved);
            setHasMoved();
            System.out.println(this.getX() + " : " + this.getY());
        }
        super.setCoordinate(x, y);
    }

    private void setHasMoved() {
        this.hasMoved = true;
    }
}
