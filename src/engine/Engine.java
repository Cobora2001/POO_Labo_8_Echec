package engine;

import chess.ChessView;
import chess.PlayerColor;
import engine.Pieces.Piece;

import java.util.LinkedList;

public class Engine {
    private final LinkedList<Piece> pieces;
    private int size = 0;
    private final Player playerOne;
    private final Player playerTwo;
    private int turn = 1;

    public Engine() {
        playerOne = new Player(PlayerColor.WHITE);
        playerTwo = new Player(PlayerColor.BLACK);
        pieces = new LinkedList<>();
    }

    public void addPiece(Piece piece) {
        this.pieces.add(piece);
        ++size;
    }

    public Piece getPiece(int i) {
        return pieces.get(i);
    }

    public int getSize() {
        return size;
    }

    public Piece getPiece(int x, int y) {
        for(Piece piece : pieces) {
            if(piece.getX() == x && piece.getY() == y) {
                return piece;
            }
        }
        return null;
    }

    public boolean checkPieceValid(Piece piece) {
        return piece.getPlayer() == getCurrentPlayer();
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    private Player getCurrentPlayer() {
        return turn % 2 == 1 ? playerOne : playerTwo;
    }

    public void printPieces(ChessView view) {
        for(Piece piece : pieces) {
            view.putPiece(piece.getType(), piece.getColor(), piece.getX(), piece.getY());
        }
    }

    public void nextTurn() {
        ++turn;
    }
}
