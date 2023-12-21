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
    private ChessView view;

    public Engine(ChessView view) {
        this.view = view;
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
            displayMessage("Movement cancelled");
            return false;
        }
        if(toX < 0 || toX > 7 || toY < 0 || toY > 7) {
            displayMessage("Invalid destination position (/!\\ hard coded)");
            return false;
        }
        Player playerTurn = getCurrentPlayer();
        return playerTurn.move(fromX, fromY, toX, toY, this);
    }

    private boolean isInLine(int fromX, int fromY, int toX, int toY) {
        int difX = fromX - toX;
        int difY = fromY - toY;
        return difX == 0 || difY == 0 || difX == difY;
    }

    private LinkedList<int[]> pointsInTheWay(int fromX, int fromY, int toX, int toY) {
        return null; // FIXME
    }

    public boolean checkObstruction(int fromX, int fromY, int toX, int toY) {
        LinkedList<int[]> listOfPoints = pointsInTheWay(fromX, fromY, toX, toY);
        return true;
    }

    public void displayMessage(String message) {
        view.displayMessage(message);
    }

    public void addPieceToView(Piece piece, PlayerColor color) {
        view.putPiece(piece.getType(), color, piece.getX(), piece.getY());
    }

    public void removeFromView(int x, int y) {
        view.removePiece(x, y);
    }
}
