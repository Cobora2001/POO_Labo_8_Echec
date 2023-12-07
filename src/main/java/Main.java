

import chess.ChessController;
import chess.ChessView;
import chess.views.console.ConsoleView;
import chess.views.gui.GUIView;
import chess.engine.Board;

public class Main {

    public static void main(String[] args) {
        try {
            // 1. Création du contrôleur pour gérer le jeu d'échec
            ChessController controller = new Board(); // Board est une classe faite et nommée par les étudiant

            // 2. Création de la vue désirée
            ChessView view = new GUIView(controller); // ou console
            //ChessView view = new ConsoleView(controller); // ou GUI

            // 3. Lancement du programme
            controller.start(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
