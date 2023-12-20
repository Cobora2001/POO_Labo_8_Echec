package engine;

import chess.ChessView;
import chess.PlayerColor;
import engine.Pieces.King;
import engine.Pieces.Piece;

import java.util.Iterator;
import java.util.LinkedList;

public class Engine {
    private final Player[] players = new Player[2];
    private int turn = 1;

    public Engine() {
        players[0] = new Player(new King(0, 0), PlayerColor.WHITE);
        players[1] = new Player(new King(7, 7), PlayerColor.BLACK);
    }

    public Piece getPiece(int x, int y) {
        for(Player player : players) {
            Piece piece = player.findPiece(x, y);
            if(piece != null)
                return piece;
        }
        return null;
    }

    public Player getPlayerOne() {
        return players[0];
    }

    public Player getPlayerTwo() {
        return players[1];
    }

    private Player getCurrentPlayer() {
        return turn % 2 == 1 ? getPlayerOne() : getPlayerTwo();
    }

    public void nextTurn() {
        ++turn;
    }

    public void drawCurrentState(ChessView view) {
        for(Player player : players) {
            Iterator<Piece> iterator = player.getIterator();
            while (iterator.hasNext()) {
                iterator.next().draw(view, player.getColor());
            }
            player.getKing().draw(view, player.getColor());
        }
    }

    public boolean move(int fromX, int fromY, int toX, int toY, ChessView view) {
        if(fromX == toX && fromY == toY) {
            view.displayMessage("Movement cancelled");
            return false;
        }
        if(toX < 0 || toX > 7 || toY < 0 || toY > 7) {
            view.displayMessage("Invalid destination position (/!\\ hard coded)");
            return false;
        }
        Player playerTurn = getCurrentPlayer();
        Piece piece = playerTurn.findPiece(fromX, fromY);
        if(piece == null) {
            view.displayMessage("No piece found from the " + playerTurn.getColor() + " player at starting position");
            return false;
        }
        if(piece.move(toX, toY)) {
            view.removePiece(fromX, fromY);
            view.putPiece(piece.getType(), playerTurn.getColor(), piece.getX(), piece.getY());
            nextTurn();
            view.displayMessage("Move made");
            return true;
        }
        view.displayMessage("Move not allowed");
        return false;
    }
}
