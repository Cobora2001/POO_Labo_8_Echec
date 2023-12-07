

package chess.engine;

import chess.engine.Pieces.Piece;

import java.util.Objects;

public class Move {
    private Piece piece;
    private Tile from;
    private Tile to;
    private Piece enPassantPiece;
    private boolean isSmallCastling = false;
    private boolean isBigCastling = false;

    /**
     * Instanciation d'un mouvement
     *
     * @param from  case source
     * @param to    case d'arrivée
     * @param piece pièce bougée
     */
    public Move(Tile from, Tile to, Piece piece) {
        this(from, to, piece, null);
    }

    /**
     * Instanciation d'un mouvement avec roque
     *
     * @param from            case source
     * @param to              case d'arrivée
     * @param piece           pièce bougée
     * @param isSmallCastling booléen si c'est le mouvement du petit roque ou non
     * @param isBigCastling   booléen si c'est le mouvement du grand roque ou non
     */
    public Move(Tile from, Tile to, Piece piece, boolean isSmallCastling, boolean isBigCastling) {
        this(from, to, piece, null);
        this.isSmallCastling = isSmallCastling;
        this.isBigCastling = isBigCastling;
    }

    /**
     * Instanciation d'un mouvement
     *
     * @param from           case source
     * @param to             case d'arrivée
     * @param piece          pièce bougée
     * @param enPassantPiece pièce concernée par la prise en passant du mouvement
     */
    public Move(Tile from, Tile to, Piece piece, Piece enPassantPiece) {
        if (from == null || to == null || piece == null)
            throw new RuntimeException("Mouvement invalide");

        this.from = from;
        this.to = to;
        this.piece = piece;
        this.enPassantPiece = enPassantPiece;
    }

    /**
     * @return la pièce concernée par le mouvement
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * @return la pièce concernée par la prise en passant
     */
    public Piece getEnPassantPiece() {
        return enPassantPiece;
    }

    /**
     * @return le nombre de cases bougées selon l'axe Y (utilisé pour la prise en passant)
     */
    public int nbTileMovedOnY() {
        return Math.abs(from.getPosY() - to.getPosY());
    }

    /**
     * @return la case de la pièce avant le mouvement
     */
    public Tile getFrom() {
        return from;
    }

    /**
     * @return la case de la pièce après le mouvement
     */
    public Tile getTo() {
        return to;
    }

    /**
     * @return booléen si c'est le mouvement du petit roque ou non
     */
    public boolean isSmallCastling() {
        return isSmallCastling;
    }

    /**
     * @return booléen si c'est le mouvement du grand roque ou non
     */
    public boolean isBigCastling() {
        return isBigCastling;
    }

    /**
     * Compare l'égalité de objets
     *
     * @param o l'objet avec lequel comparer
     * @return booléen si les objets sont égaux ou non
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return Objects.equals(piece, move.piece) &&
                Objects.equals(from, move.from) &&
                Objects.equals(to, move.to);
    }

    /**
     * @return le HashCode du mouvemement
     */
    @Override
    public int hashCode() {
        return Objects.hash(piece, from, to);
    }
}
