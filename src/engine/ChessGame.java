/**
 * ChessGame.java: manage the chess game
 */

package engine;

import chess.ChessController;
import chess.ChessView;

public class ChessGame implements ChessController {
  // The view that will be used to display the game
  private ChessView view;

  // The engine that will be used to manage the game
  private Engine engine;

  /**
   * Start the game
   * @param view the view that will be used to display the game
   */
  @Override
  public void start(ChessView view) {
    this.view = view;
    view.startView();
  }

    /**
     * Move a piece
     * @param fromX the x coordinate of the piece to move
     * @param fromY the y coordinate of the piece to move
     * @param toX the x coordinate of the destination
     * @param toY the y coordinate of the destination
     * @return true if the move was successful, false otherwise
     */
  @Override
  public boolean move(int fromX, int fromY, int toX, int toY) {
    System.out.println(String.format("TO REMOVE : from (%d, %d) to (%d, %d)", fromX, fromY, toX, toY)); // TODO remove
    return engine.move(fromX, fromY, toX, toY);
  }

  /**
   * Start a new game
   */
  @Override
  public void newGame() {
    view.displayMessage("new game (TO REMOVE)");
    this.engine = new Engine(view);
    engine.initiateView();
  }
}
