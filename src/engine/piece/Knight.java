package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;

/**
 * Classe cavalier héritant de la classe Piece
 *
 * @author : Valentin Bugna, Theodros Mulugeta et Stéphane Nascimento Santos
 * @since  : JDK 17.0.4
 */
public class Knight extends Piece {

    public Knight(PlayerColor pieceColor) {
        super(pieceColor);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KNIGHT;
    }

    @Override
    public void move(Board board, int fromX, int fromY, int toX, int toY) {
            board.movePiece(fromX, fromY, toX, toY, this);
    }

    @Override
    public boolean isMoveValid(Board board, boolean checkLegality, int fromX, int fromY, int toX, int toY) {
        return (Math.abs(fromX - toX) * Math.abs(fromY - toY)) == 2
                && board.isMoveAuthorized(fromX, fromY, toX, toY)
                && (!checkLegality || board.isMoveLegal(fromX, fromY, toX, toY));
    }
}