package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PlayerColor;
import engine.Pieces.King;
import engine.Pieces.Piece;

public class ChessGame implements ChessController {

  private ChessView view;
  private int turnNb;
  private Board board;

  @Override
  public void start(ChessView view) {
    this.view = view;
    view.startView();
  }

  @Override
  public boolean move(int fromX, int fromY, int toX, int toY) {
    System.out.printf("TO REMOVE : from (%d, %d) to (%d, %d)%n", fromX, fromY, toX, toY);
    if(fromX == toX && fromY == toY) {
      System.out.println("Stayed static, still the same turn");
      return false;
    }
    if(toX < 0 || toX > 7 || toY < 0 || toY > 7) {
      System.out.println("Invalid destination position (/!\\ hard coded)");
      return false;
    }
    Piece piece = board.getPiece();
    if(piece.getX() == fromX && piece.getY() == fromY) {
      System.out.println("Bonjour");
      if(piece.move(toX, toY)) {
        view.removePiece(fromX, fromY);
        view.putPiece(piece.getType(), piece.getColor(), piece.getX(), piece.getY());
        return true;
      }
    }
    return false;
  }

  @Override
  public void newGame() {
    board = new Board();
    view.displayMessage("new game (TO REMOVE)");
    board.setPiece(new King(PlayerColor.BLACK, 3, 4));
    view.putPiece(board.getPiece().getType(), board.getPiece().getColor(), board.getPiece().getX(), board.getPiece().getY());
  }
}
