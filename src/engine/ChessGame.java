package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;

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
    System.out.println(String.format("TO REMOVE : from (%d, %d) to (%d, %d)", fromX, fromY, toX, toY)); // TODO remove
    return engine.move(fromX, fromY, toX, toY);
  }

  @Override
  public void newGame() {
    view.displayMessage("new game (TO REMOVE)");
    this.engine = new Engine(view);
    engine.initiateView();
  }
}
