package engine;

public class UniMove extends Ruleset {
    public boolean movePossible(Piece piece, int toX, int toY) {
        int diffX = toX - piece.getX();
        int diffY = toY - piece.getY();

        if(Math.abs(diffX) > 1 || Math.abs(diffY) > 1) {
            return false;
        }

        return true;
    }
}
