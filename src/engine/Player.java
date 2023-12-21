package engine;

import chess.ChessView;
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

    public boolean move(int fromX, int fromY, int toX, int toY, Engine engine) {
        Piece piece = findPiece(fromX, fromY);
        if(piece == null) {
            engine.displayMessage("No piece found from the " + getColor() + " player at starting position");
            return false;
        }
        // On a à ce moment-là déjà appelé de quoi savoir que la position de départ et d'arrivée sont différentes, donc
        // les deux pièces peuvent pas être les mêmes, à moins qu'on ait plusieurs fois la même pièce sur le plateau...
        if(findPiece(toX, toY) != null) {
            engine.displayMessage("There was a " + getColor() + " piece at the place we wanted to put our piece at.");
        }
        if(piece.move(toX, toY, engine)) {
            engine.removeFromView(fromX, fromY);
            engine.addPieceToView(piece, getColor());
            engine.nextTurn();
            engine.displayMessage("Move made");
            return true;
        }
        engine.displayMessage("Move not allowed");
        return false;
    }
}
