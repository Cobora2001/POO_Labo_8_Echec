package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.move.Horizontal;
import engine.move.Vertical;

/**
 * Classe tour héritant de la sous-classe PieceMove
 *
 * @author : Valentin Bugna, Theodros Mulugeta et Stéphane Nascimento Santos
 * @since : JDK 17.0.4
 */
public class Rook extends PieceMove {

    public Rook(PlayerColor pieceColor) {
        super(pieceColor);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.ROOK;
    }

    @Override
    public void move(Board board, int fromX, int fromY, int toX, int toY) {
        board.movePiece(fromX, fromY, toX, toY, this);
        setHasMoved();
    }

    @Override
    public boolean isMoveValid(Board board, boolean checkLegality, int fromX, int fromY, int toX, int toY) {
        return (new Vertical().isValid(fromX, fromY, toX, toY)
                || new Horizontal().isValid(fromX, fromY, toX, toY))
                && board.isMoveFree(fromX, fromY, toX, toY)
                && board.isMoveAuthorized(fromX, fromY, toX, toY)
                && (!checkLegality || board.isMoveLegal(fromX, fromY, toX, toY));
    }
}