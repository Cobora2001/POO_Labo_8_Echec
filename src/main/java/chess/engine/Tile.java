

package chess.engine;

import chess.engine.Pieces.Piece;

import java.util.Objects;

public class Tile {
    private Piece piece;
    private int posX;
    private int posY;

    /**
     * Instanciation d'une case de l'échiquier
     *
     * @param posX position X de la case
     * @param posY position Y de la case
     */
    public Tile(int posX, int posY) {
        this(null, posX, posY);
    }

    /**
     * Instanciation d'une case de l'échiquier
     *
     * @param piece pièce sur la case
     * @param posX position X de la case
     * @param posY position Y de la case
     */
    public Tile(Piece piece, int posX, int posY) {
        if(posX < 0 || posX > 7 || posY < 0 || posY > 7)
            throw new RuntimeException("La case doit être comprise entre 0 et 7");

        this.posX = posX;
        this.posY = posY;
        this.piece = piece;

        if (piece != null)
            this.piece.setTile(this);
    }

    /**
     * @return la position en X de la case
     */
    public int getPosX() {
        return posX;
    }

    /**
     * @return la position en Y de la case
     */
    public int getPosY() {
        return posY;
    }

    /**
     * @return la pièce sur la case
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Ajouter une pièce sur la case
     *
     * @param piece à ajouter
     * @return la pièce qui a été ajoutée
     */
    public Piece setPiece(Piece piece) {
        if (this.piece != null)
            this.piece.removeTile();

        this.piece = piece;

        if (this.piece != null)
            this.piece.setTile(this);

        return this.piece;
    }

    /**
     * Suppresion de la pièce sur la case
     *
     * @return la pièce supprimée de la case
     */
    public Piece removePiece() {
        Piece oldPiece = null;
        if (this.piece != null) {
            oldPiece = this.piece;
            piece.removeTile();
        }
        this.piece = null;
        return oldPiece;
    }

    /**
     * @return booléen si la case est occupée par un pièce ou non
     */
    public boolean isOccupied() {
        return piece != null;
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
        Tile tile = (Tile) o;
        return posX == tile.posX &&
                posY == tile.posY;
    }

    /**
     * @return le HashCode de la case
     */
    @Override
    public int hashCode() {
        return Objects.hash(piece, posX, posY);
    }
}
