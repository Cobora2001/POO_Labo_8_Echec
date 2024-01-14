package test;

import chess.ChessController;
import chess.ChessView;
import chess.views.gui.GUIView;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SimulationTest {

    private static class Point{

        private final int x;
        private final int y;

        private Point (int x, int y){
            this.x = x;
            this.y = y;
        }

        int x(){
            return this.x;
        }

        int y(){
            return this.y;
        }
    }

    // Games taken from chess.com
    // Some do not finish with a checkmate because the opponent forfeited before the checkmate...
    // Games have been transformed from PGN to Coordinate notations using this website : https://marianogappa.github.io/ostinato-examples/convert
    // Then transformed into UCI using a custom Python script to parse the data
    // UCI : https://en.wikipedia.org/wiki/Universal_Chess_Interface

    // Morphy vs. Allies, Paris Opera 1858
    // https://www.chess.com/games/view/765
    static final String[] MORPHY_ALLIES_1858 = new String[]{
            "e2e4", "e7e5", "g1f3", "d7d6", "d2d4", "c8g4", "d4e5", "g4f3", "d1f3",
            "d6e5", "f1c4", "g8f6", "f3b3", "d8e7", "b1c3", "c7c6", "c1g5", "b7b5",
            "c3b5", "c6b5", "c4b5", "b8d7", "e1c1", "a8d8", "d1d7", "d8d7", "h1d1",
            "e7e6", "b5d7", "f6d7", "b3b8", "d7b8", "d1d8"
    };

    // Boris Spassky vs Bobby Fischer, 1992
    // https://www.chess.com/games/view/584202
    static final String[] SPASSKY_FISCHER_1992 = new String[]{
            "e2e4", "c7c5", "b1c3", "b8c6", "g1e2", "d7d6", "d2d4", "c5d4", "e2d4",
            "e7e6", "c1e3", "g8f6", "d1d2", "f8e7", "f2f3", "a7a6", "e1c1", "e8g8",
            "g2g4", "c6d4", "e3d4", "b7b5", "g4g5", "f6d7", "h2h4", "b5b4", "c3a4",
            "c8b7", "a4b6", "a8b8", "b6d7", "d8d7", "c1b1", "d7c7", "f1d3", "b7c8",
            "h4h5", "e6e5", "d4e3", "c8e6", "d1g1", "a6a5", "g5g6", "e7f6", "g6h7",
            "g8h8", "e3g5", "c7e7", "g1g3", "f6g5", "g3g5", "e7f6", "h1g1", "f6f3",
            "g5g7", "f3f6", "h5h6", "a5a4", "b2b3", "a4b3", "a2b3", "f8d8", "d2g2",
            "d8f8", "g7g8", "h8h7", "g8g7", "h7h8", "h6h7"
    };

    // Byrne vs. Fischer, New York 1956
    // https://www.chess.com/games/view/75289
    static final String[] BYRNE_FISCHER_1956 = new String[]{
            "g1f3", "g8f6", "c2c4", "g7g6", "b1c3", "f8g7", "d2d4", "e8g8", "c1f4",
            "d7d5", "d1b3", "d5c4", "b3c4", "c7c6", "e2e4", "b8d7", "a1d1", "d7b6",
            "c4c5", "c8g4", "f4g5", "b6a4", "c5a3", "a4c3", "b2c3", "f6e4", "g5e7",
            "d8b6", "f1c4", "e4c3", "e7c5", "f8e8", "e1f1", "g4e6", "c5b6", "e6c4",
            "f1g1", "c3e2", "g1f1", "e2d4", "f1g1", "d4e2", "g1f1", "e2c3", "f1g1",
            "a7b6", "a3b4", "a8a4", "b4b6", "c3d1", "h2h3", "a4a2", "g1h2", "d1f2",
            "h1e1", "e8e1", "b6d8", "g7f8", "f3e1", "c4d5", "e1f3", "f2e4", "d8b8",
            "b7b5", "h3h4", "h7h5", "f3e5", "g8g7", "h2g1", "f8c5", "g1f1", "e4g3",
            "f1e1", "c5b4", "e1d1", "d5b3", "d1c1", "g3e2", "c1b1", "e2c3", "b1c1",
            "a2c2"
    };

    // Kasparov vs. Topalov, Wijk aan Zee 1999
    // https://www.chess.com/games/view/969971
    static final String[] KASPAROV_TOPALOV_1999 = new String[]{
            "e2e4", "d7d6", "d2d4", "g8f6", "b1c3", "g7g6", "c1e3", "f8g7", "d1d2",
            "c7c6", "f2f3", "b7b5", "g1e2", "b8d7", "e3h6", "g7h6", "d2h6", "c8b7",
            "a2a3", "e7e5", "e1c1", "d8e7", "c1b1", "a7a6", "e2c1", "e8c8", "c1b3",
            "e5d4", "d1d4", "c6c5", "d4d1", "d7b6", "g2g3", "c8b8", "b3a5", "b7a8",
            "f1h3", "d6d5", "h6f4", "b8a7", "h1e1", "d5d4", "c3d5", "b6d5", "e4d5",
            "e7d6", "d1d4", "c5d4", "e1e7", "a7b6", "f4d4", "b6a5", "b2b4", "a5a4",
            "d4c3", "d6d5", "e7a7", "a8b7", "a7b7", "d5c4", "c3f6", "a4a3", "f6a6",
            "a3b4", "c2c3", "b4c3", "a6a1", "c3d2", "a1b2", "d2d1", "h3f1", "d8d2",
            "b7d7", "d2d7", "f1c4", "b5c4", "b2h8", "d7d3", "h8a8", "c4c3", "a8a4",
            "d1e1", "f3f4", "f7f5", "b1c1", "d3d2", "a4a7"
    };



    public static void main(String[] args) {
        ChessController controller = new engine.ChessGame();
        ChessView view = new GUIView(controller);
        controller.start(view);
        simulateGames(controller);
    }

    public static void simulateGames(ChessController controller) {
        List<String[]> games = new ArrayList<>();
        games.add(MORPHY_ALLIES_1858);
        games.add(SPASSKY_FISCHER_1992);
        games.add(BYRNE_FISCHER_1956);
        games.add(KASPAROV_TOPALOV_1999);

        for (String[] game : games) {
            controller.newGame();
            List<List<Point>> moves = UCItoPoint(game);
            for (List<Point> move : moves) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                controller.move(move.get(0).x(), move.get(0).y(), move.get(1).x(), move.get(1).y());
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<List<Point>> UCItoPoint(String[] uciCommands) {
        List<List<Point>> sourceToDestination = new ArrayList<>();
        for (String uciCommand : uciCommands) {
            List<Point> points = new ArrayList<>();
            points.add(new Point(uciCommand.charAt(0) - 'a', uciCommand.charAt(1) - '1')); // source
            points.add(new Point(uciCommand.charAt(2) - 'a', uciCommand.charAt(3) - '1')); // destination
            sourceToDestination.add(points);
        }
        return sourceToDestination;
    }
}
