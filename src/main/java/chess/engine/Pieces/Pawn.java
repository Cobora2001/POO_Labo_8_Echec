

package chess.engine.Pieces;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.Board;
import chess.engine.Move;
import chess.engine.Tile;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    /**
     * Instanciation d'un pion
     *
     * @param pc couleur du pion
     */
    public Pawn(PlayerColor pc) {
        super(PieceType.PAWN, pc);
    }

    /**
     * Obtenir la liste des mouvements disponibles du pion
     *
     * @param board de l'échiquier
     * @return la liste des mouvements disponibles pour le pion
     */
    @Override
    public List<Move> getMoves(Board board) {
        if (board == null)
            throw new InvalidParameterException("board nulle");

        List<Move> moves = new ArrayList<>();
        Tile currentTile = super.tile;
        int x = currentTile.getPosX();
        int y = currentTile.getPosY();

        // Nous n'utilisons pas addSimpleMoveWithChoosedVariation car les mouvements des pions sont plus conditionnés
        if (playerColor == PlayerColor.BLACK) {
            forwardMove(x, y - 1, moves, currentTile, board);
            twoForwardMove(x, y - 2, y - 2, moves, currentTile, board);
            diagonalMove(x + 1, y - 1, moves, currentTile, board);
            diagonalMove(x - 1, y - 1, moves, currentTile, board);
        } else {
            forwardMove(x, y + 1, moves, currentTile, board);
            twoForwardMove(x, y + 2, y + 1, moves, currentTile, board);
            diagonalMove(x + 1, y + 1, moves, currentTile, board);
            diagonalMove(x - 1, y + 1, moves, currentTile, board);
        }

        return moves;
    }

    /**
     * Ajout du déplacement en avant du pion dans les mouvement disponibles
     *
     * @param x la position en X du pion
     * @param y la position en Y du pion
     * @param moves la liste des mouvements dans laquelle ajouter le nouveau mouvement
     * @param currentTile la case actuelle du pion
     * @param board l'échiquier
     */
    private void forwardMove(int x, int y, List<Move> moves, Tile currentTile, Board board) {
        Tile newTile = board.getTile(x, y);
        if (valid(x, y) && !newTile.isOccupied())
            moves.add(new Move(currentTile, newTile, this));
    }

    /**
     * Ajout du déplacement en avant 2 fois du pion dans les mouvement disponibles
     *
     * @param x la position en X du pion
     * @param yTo la position en Y du pion
     * @param yBy la position en Y par laquelle le pion passe
     * @param moves la liste des mouvements dans laquelle ajouter le nouveau mouvement
     * @param currentTile la case actuelle du pion
     * @param board l'échiquier
     */
    private void twoForwardMove(int x, int yTo, int yBy, List<Move> moves, Tile currentTile, Board board) {
        if (currentTile.getPosY() != (playerColor == PlayerColor.BLACK ? 6 : 1))
            return;

        Tile newTile = board.getTile(x, yTo);
        Tile byTile = board.getTile(x, yBy);
        if (!newTile.isOccupied() && !byTile.isOccupied())
            moves.add(new Move(currentTile, newTile, this));
    }

    /**
     * Ajout du déplacement du pion pour manger une autre pièce
     *
     * @param x la position en X du pion
     * @param y la position en Y du pion
     * @param moves la liste des mouvements dans laquelle ajouter le nouveau mouvement
     * @param currentTile la case actuelle du pion
     * @param board l'échiquier
     */
    private void diagonalMove(int x, int y, List<Move> moves, Tile currentTile, Board board) {
        if (!valid(x, y))
            return;

        Tile newTile = board.getTile(x, y);
        if (newTile.isOccupied() && newTile.getPiece().playerColor != playerColor)
            moves.add(new Move(currentTile, newTile, this));
    }

    /**
     * @return booléen si le pion peut être promu ou non
     */
    public boolean canBePromoted() {
        int y = super.tile.getPosY();
        return y == (playerColor.equals(PlayerColor.BLACK) ? 0 : 7);
    }

    /**
     * Obtenir le mouvement enPassant s'il est disponible
     *
     * @param lastMove dernier mouvement effectué sur l'échiquier
     * @param board l'échiquier
     * @return le mouvement de la prise en passant ou null s'il n'est pas disponible
     */
    public Move enPassant(Move lastMove, Board board) {
        if (board == null)
            throw new InvalidParameterException("board nulle");

        if (lastMove == null || lastMove.getPiece().getPieceType() != PieceType.PAWN)
            return null;

        // Vérification que les conditions de la prise en passant sont respectées
        if (lastMove.nbTileMovedOnY() == 2) {
            if (playerColor == PlayerColor.BLACK && tile.getPosY() == 3 && Math.abs(lastMove.getTo().getPosX() - tile.getPosX()) == 1)
                return new Move(tile, board.getTile(lastMove.getTo().getPosX(), 2), this, lastMove.getPiece());
            else if (tile.getPosY() == 4 && Math.abs(lastMove.getTo().getPosX() - tile.getPosX()) == 1)
                return new Move(tile, board.getTile(lastMove.getTo().getPosX(), 5), this, lastMove.getPiece());
        }

        return null;
    }
}
