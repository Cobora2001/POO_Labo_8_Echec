package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static java.io.FileDescriptor.err;

public class King extends PiecesWithInitialMove {

    public void initiatePosition(int x, int y) {
        super.initiatePosition(x, y);
    }

    private final LinkedList<Pair<Rook, Integer>> castles = new LinkedList<>();


    private static Ruleset ruleset = new KingMove();

    public PieceType getPieceType() {
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

    private void addCastle(Piece castle){



        if(castle == null  ){
            System.err.println("Null Rook given, can't add to castle");
        } else if (castle.getClass() != Rook.class) {
            System.err.println("The castle piece is not a rook");
        } else if(castle.getColor() != this.getColor())
            System.err.println("Can't add to castle, Rook not of the same color");
        else if(castle.getY() == this.getY()) {
            int diffX = castle.getX() - this.getX();
            castles.add(new Pair<>((Rook)castle, this.getX() + 2 * diffX / Math.abs(diffX)));
        } else
            System.err.println("Can't add to castles, Rook not on the same line");
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

    public King(int x, int y, PlayerColor color, List<Piece> list) {
        super(x, y, color);
        for(Piece rook : list){
            addCastle(rook);
        }

    }
}
