package engine;

import chess.ChessController;
import chess.ChessView;
import engine.Pieces.King;
import engine.Pieces.Piece;
import engine.Pieces.Rook;

public class ChessGame implements ChessController {

  private ChessView view;
  private Engine engine;

  @Override
  public void start(ChessView view) {
    this.view = view;
    view.startView();
  }

  @Override
  public boolean move(int fromX, int fromY, int toX, int toY) {
    System.out.printf("TO REMOVE : from (%d, %d) to (%d, %d)%n", fromX, fromY, toX, toY);
    if(fromX == toX && fromY == toY) {
      System.out.println("Movement cancelled");
      return false;
    }
    if(toX < 0 || toX > 7 || toY < 0 || toY > 7) {
      System.out.println("Invalid destination position (/!\\ hard coded)");
      return false;
    }
    Piece piece = engine.getPiece(fromX, fromY);
    if(piece == null) {
      System.out.println("No piece found at starting position");
      return false;
    }
    if(!engine.checkPieceValid(piece)) {
      System.out.println("This piece isn't from the player who's turn it is");
      return false;
    }
    if(piece.move(toX, toY)) {
      view.removePiece(fromX, fromY);
      view.putPiece(piece.getType(), piece.getColor(), piece.getX(), piece.getY());
      engine.nextTurn();
      System.out.println("Move made");
      return true;
    }
    System.out.println("Move not allowed");
    return false;
  }

  @Override
  public void newGame() {
    engine = new Engine();
    view.displayMessage("new game (TO REMOVE)");
    engine.addPiece(new King(3, 4, engine.getPlayerOne()));
    engine.addPiece(new Rook(4, 4, engine.getPlayerTwo()));
    engine.printPieces(view);
  }
}
