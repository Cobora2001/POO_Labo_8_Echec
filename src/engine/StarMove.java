package engine;

public class StarMove extends CaresAboutObstacles {
    static private Ruleset cross = new CrossMove();
    static private Ruleset diagonal = new DiagonalMove();

    public boolean movePossible(Piece piece, int toX, int toY) {
        return cross.movePossible(piece, toX, toY) || diagonal.movePossible(piece, toX, toY);
    }
}
