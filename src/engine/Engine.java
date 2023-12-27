package engine;

import chess.ChessView;
import chess.PlayerColor;
import engine.Pieces.Piece;

import java.util.Iterator;
import java.util.LinkedList;

public class Engine {
    private Player[] players;
    private Board board;
    private int turn = 1;
    private final ChessView view;


    public Engine(ChessView view, PlayerColor[] colorsAtPlay) {
        this.view = view;

        LinkedList<PlayerColor> validColors = validColorList(colorsAtPlay);

        initializePlayers(validColors);

    }

    // Add the color if it's not already there
    private void manageColor(LinkedList<PlayerColor> colors, PlayerColor color) {
        boolean flag = true;
        int size = colors.size();
        for(int i = 0; i < size && flag; ++i) {
            if(color == colors.get(i)){
                colors.add(color);
                flag = false;
            }
        }
    }

    // Get unique colors at play
    private LinkedList<PlayerColor> validColorList(PlayerColor[] colors) {
        LinkedList<PlayerColor> validColors = new LinkedList<>();
        for(PlayerColor color : colors) {
            manageColor(validColors, color);
        }
        return validColors;
    }

    private void initializePlayers(LinkedList<PlayerColor> validColors) {
        int size = validColors.size();
        players = new Player[size];
        for(int i = 0; i < size; ++i) {
            players[i] = new Player(validColors.get(i));
        }
    }

    public Piece getPiece(int x, int y) {
        return board.getCase(x, y);
    }

    private PlayerColor getCurrentColor() {
        return players[turn % players.length].getColor();
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
