package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.util.Iterator;
import java.util.LinkedList;

public class King extends PiecesWithInitialMove {

    private final LinkedList<Pair<Piece, Pair<Integer,Integer>>> castles = new LinkedList<>();


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

    public void addCastle(Piece castle, int x, int y){
        castles.add(new Pair<>(castle, new Pair<>(x,y)));
    }
    public void removeCastle(Piece castle){
       Iterator<Pair<Piece, Pair<Integer,Integer>>> i = castles.iterator();
       while (i.hasNext()){
           Pair<Piece, Pair<Integer,Integer>> examined = i.next();
           if(examined.getFirst() == castle){
               castles.remove(examined);
               break;
           }

       }
    }

    public Iterator<Pair<Piece, Pair<Integer,Integer>>> getCastlesIterator(){
        return castles.iterator();
    }

    public King(int x, int y, PlayerColor color) {
        super(x, y, color);
    }


}
