package engine.Pieces;

public class Ruleset implements Piece.Rule {
    boolean eatingReach(int x, int y) {
        return true;}
    @Override
    public boolean valid(int x, int y) {
        return (eatingReach(x, y) && true) || movingReach(x, y) && true;
    }
    boolean movingReach(int x, int y){
        return true;
    }

}
