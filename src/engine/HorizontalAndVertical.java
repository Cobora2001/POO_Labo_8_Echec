package engine;

public class HorizontalAndVertical extends Vertical {

    public boolean movePossible(Piece piece, int toX, int toY) { // can't be static if overridden
        return piece.getY() == toY || super.movePossible(piece, toX, toY);
    }
}