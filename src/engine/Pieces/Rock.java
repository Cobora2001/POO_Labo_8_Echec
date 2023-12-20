package engine.Pieces;

public class Rock extends Ruleset {
    int xRook;
    int yRook;
    Piece rook;

    int xActivate,yActivate; // emplacement où le roi doit clicker pour le rook, peut être stocké sur le roi.
    int xDestination, yDestination; // emplacement de la tour à la fin.
    Rock(int x, int y, Piece king, Piece rook){ // king peut être optionnel si on stock dans roi.
    }
    void checkComponentMoved() {
        if(rook.getX() != xRook || rook.getY() != yRook) {
            destroy();
        }
    }

    void destroy() {
        ((King)rook.getPlayer().getKing()).remove(this);
    }


}
