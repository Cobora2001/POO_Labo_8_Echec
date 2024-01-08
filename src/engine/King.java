package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.util.Iterator;
import java.util.LinkedList;

public class King extends PiecesWithInitialMove {

    private final LinkedList<Pair<Rook, Integer>> castles = new LinkedList<>();


    private static Ruleset ruleset = new KingMove();

    public PieceType getType() {
        return PieceType.KING;
    }

    @Override
    public String canMove(int toX, int toY, Engine engine) {
        return ruleset.availableMove(this, toX, toY, engine);
    }

    @Override
    public void updateMatrix(int toX, int toY, Engine engine) {
        ruleset.updateMatrix(this, toX, toY, engine);

    }

    public void addCastle(Rook castle){
        if(castle == null)
            System.out.println("Null Rook given, can't add to castle");
        else if(castle.getColor() != this.getColor())
            System.out.println("Can't add to castle, Rook not of the same color");
        else if(castle.getY() == this.getY()) {
            int diffX = castle.getX() - this.getX();
            castles.add(new Pair<>(castle, this.getX() + 2 * diffX / Math.abs(diffX)));
        } else
            System.out.println("Can't add to castles, Rook not on the same line");
    }
    public void removeCastle(Piece castle){
        for (Pair<Rook, Integer> examined : castles) {
            if (examined.getFirst() == castle) {
                castles.remove(examined);
                break;
            }

        }
    }

    public Iterator<Pair<Rook, Integer>> getCastlesIterator(){
        return castles.iterator();
    }

    public King(int x, int y, PlayerColor color) {
        super(x, y, color);
    }
}
