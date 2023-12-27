package engine;

import chess.ChessController;
import chess.ChessView;
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
    return engine.move(fromX, fromY, toX, toY, view);
  }

  @Override
  public void newGame() {
    engine = new Engine(view, new PlayerColor[]{PlayerColor.WHITE, PlayerColor.BLACK});
    view.displayMessage("new game (TO REMOVE)");
    engine.drawCurrentState(view);
  }
}
