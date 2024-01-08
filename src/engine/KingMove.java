package engine;

import java.util.Iterator;

public class KingMove extends CaresAboutObstacles {

    public String availableMove(Piece piece, int toX, int toY, Engine engine) { // can't be static if overridden
        String response = super.availableMove(piece, toX, toY, engine);

        if(response == null) {
            return null;
        }

        if(!canCastle(piece, toX, toY, engine)) {
            return response;
        }
        return null;
    }
    public boolean movePossible(Piece piece, int toX, int toY) {
        int diffX = toX - piece.getX();
        int diffY = toY - piece.getY();

        return Math.abs(diffX) <= 1 && Math.abs(diffY) <= 1;
    }

    private boolean canCastle(Piece piece, int toX, int toY, Engine engine){

        System.out.println(1);

        if(toY != piece.getY())
            return false;

        int diffX = toX - piece.getX();

        System.out.println(2);

        // can't castle if vertical movement
        if (toY != piece.getY()){
            return false;
        }

        System.out.println(3);

        // check if king has moved
        if(((King)piece).hasMoved()){
            return false;
        }

        Iterator<Pair<Rook, Integer>> castles = ((King) piece).getCastlesIterator();

        System.out.println(4);

        Rook rook = null;
        while(castles.hasNext() && rook == null) {
            Pair<Rook, Integer> castle = castles.next();
            if(castle.getSecond() == toX) {
                rook = castle.getFirst();
            }
        }

        System.out.println(5);

        if(rook == null || engine.getMatrix()[rook.getX()][rook.getY()] != rook) {
            return false;
        }

        System.out.println(6);

        if(rook.hasMoved()) {
            return false;
        }

        System.out.println(7);

        int stepX = -1 * diffX / Math.abs(diffX);

        int indicatorX = rook.getX();

        indicatorX += stepX;

        while(indicatorX != piece.getX()) {
            if(engine.getMatrix()[indicatorX][piece.getY()] != null)
                return false;
            indicatorX += stepX;
        }

        System.out.println(8);

        boolean flag = true;

        while(flag) {
            if(engine.moveWouldThreaten(piece.getColor(), indicatorX, piece.getY()))
                return false;
            if(indicatorX != piece.getX())
                indicatorX += stepX;
            else flag = false;
        }

        System.out.println(9);

        return true;
    }

    @Override
    public void updateMatrix(Piece piece, int toX, int toY, Engine engine) {
        super.updateMatrix(piece, toX, toY, engine);

        Iterator<Pair<Rook, Integer>> castles = ((King)piece).getCastlesIterator();

        boolean flag = true;

        while(castles.hasNext() && flag) {
            Pair<Rook, Integer> castle = castles.next();
            if(castle.getSecond() == toX) {
                flag = false;
                Rook rook = castle.getFirst();
                int diffX = toX - rook.getX();
                engine.getMatrix()[toX + diffX / Math.abs(diffX)][toY] = rook;
                engine.getMatrix()[rook.getX()][rook.getY()] = null;
            }
        }
    }
}
