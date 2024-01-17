// Authors: Thomas Vuilleumier, Aurélien Richard, and Stéphane Nascimento Santos

package engine;

import chess.ChessController;
import chess.ChessView;

/**
 * ChessGame.java: manage the chess game
 */
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
    return engine.move(fromX, fromY, toX, toY);
  }

  /**
   * Start a new game
   */
  @Override
  public void newGame() {
    view.displayMessage("Welcome to a new game of chess!");
    this.engine = new Engine(view);
    engine.initiateView();
  }
}
