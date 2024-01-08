package engine;

public class KingMove extends CaresAboutObstacles {

    public String availableMove(Piece piece, int toX, int toY, Engine engine) { // can't be static if overridden
        String response = super.availableMove(piece, toX, toY, engine);

        if(response != null) {
            return response;
        }

        if(!canCastle(piece, toX, toY, engine.getMatrix())) {
            return "Cannot castle.";
        }
        return null;
    }
    public boolean movePossible(Piece piece, int toX, int toY) {
        int diffX = toX - piece.getX();
        int diffY = toY - piece.getY();

        if(Math.abs(diffX) > 1 || Math.abs(diffY) > 1) {
            return false;
        }

        return true;
    }

    private boolean canCastle(Piece piece, int toX, int toY, Piece[][] matrix){

        int diffX = toX - piece.getX();
        int diffY = toY - piece.getY();

        // can't castle if vertical movement
        if (Math.abs(diffY) > 0 ){
            return false;
        }

        // check if king has moved
        if(((King)piece).hasMoved()){
            return false;
        }

        // find the targeted Tower using the king destination (either big or small castle)
        // check if tower has moved, if not, can't castle

        // check if empty between king and tower
        // check if king can be checked while traveling
        return true;

    }
}
