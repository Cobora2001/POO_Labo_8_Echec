package engine;

import chess.PlayerColor;
import engine.Pieces.Piece;

import java.util.Iterator;
import java.util.LinkedList;

public class Player {
    private final Piece king;
    private final LinkedList<Piece> pieces = new LinkedList<>();
    private final PlayerColor color;
    public Piece getKing(){
        return king;
    }

    public Player(Piece king, PlayerColor color) {
        this.king = king;
        this.color = color;
    }

    Iterator getIterator(){
        return pieces.iterator();
    }

    void removePiece(Piece piece){
        pieces.remove(piece);
    }

    void addPiece(Piece piece){
        pieces.add(piece);
    }

    public PlayerColor getColor() {
        return color;
    }

    public Piece findPiece(int x, int y) {
        for(Piece piece : pieces) {
            if(piece.isThere(x, y))
                return piece;
        }
        if(king.isThere(x, y))
            return king;
        return null;
    }
}
