package engine;

import chess.*;
import engine.piece.Piece;

/**
 * Classe permettant de contrôle l'échiquier
 *
 * @author : Valentin Bugna, Theodros Mulugeta et Stéphane Nascimento Santos
 * @since  : JDK 17.0.4
 */
public class Controller implements ChessController {
    private ChessView view;
    private Board board;

    /**
     * Fonciton qui démarre une partie d'échec
     * @param view la vue à utiliser
     */
    @Override
    public void start(ChessView view) {
        this.view = view;
        this.view.startView();
    }

    /**
     * Fonction qui effectue le mouvement des pièces
     * @param fromX la coordonnée de départ sur l'axe des abscisses
     * @param fromY la coordonnée de départ sur l'axe des ordonnées
     * @param toX   la coordonnée d'arrivée sur l'axe des abscisses
     * @param toY   la coordonnée d'arrivée sur l'axe des ordonnées
     * @return vrai si le mouvement a eu lieu
     */
    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {
        Piece fromPiece = board.getPiece(fromX, fromY);

        if ((fromPiece == null) || fromPiece.getPieceColor() != board.getCurrentPlayerColor()) {
            return false;

        } else if (fromPiece.isMoveValid(board, true, fromX, fromY, toX, toY)) {
            fromPiece.move(board, fromX, fromY, toX, toY);
            if(board.isOpponentPlayerInCheck()){
                view.displayMessage("Check !");
            }
            board.setTurn();
            return true;

        } else {
            return false;
        }
    }

    /**
     * Fonction qui initialise un nouveau jeu
     */
    @Override
    public void newGame() {
        this.board = new Board(view);
    }
}
