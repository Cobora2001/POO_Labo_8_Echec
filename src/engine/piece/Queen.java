package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.move.*;

/**
 * Classe reine héritant de la classe Piece
 *
 * @author : Valentin Bugna, Theodros Mulugeta et Stéphane Nascimento Santos
 * @since  : JDK 17.0.4
 */
public class Queen extends Piece {

    public Queen(PlayerColor pieceColor) {
        super(pieceColor);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.QUEEN;
    }

    @Override
    public void move(Board board, int fromX, int fromY, int toX, int toY) {
            board.movePiece(fromX, fromY, toX, toY, this);
    }

    @Override
    public boolean isMoveValid(Board board, boolean checkLegality, int fromX, int fromY, int toX, int toY) {
        return (new Vertical().isValid(fromX, fromY, toX, toY)
                || new Horizontal().isValid(fromX, fromY, toX, toY)
                || new Diagonal().isValid(fromX, fromY, toX, toY))
                && board.isMoveFree(fromX, fromY, toX, toY)
                && board.isMoveAuthorized(fromX, fromY, toX, toY)
                && (!checkLegality || board.isMoveLegal(fromX, fromY, toX, toY));
    }
}
