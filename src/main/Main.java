package main;

import model.DAO;

import javax.swing.text.View;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static Model model;
    private static Controller controller;
    private static View view;

    private static final String CLI = "console";
    private static final String GUI = "gui";

    private static final String USAGE = "java -jar swingy.jar console\njava -jar swingy.jar GUI";

    public static void main(String[] args) throws SQLException {
        String mode = "";
        if (args.length == 0 || (args.length == 1 && args[0].equals("-help"))) {
            System.out.println(USAGE);
            return;
        } else if (args.length == 1 && args[0].equals(CLI)) {
            mode = CLI;
        } else if (args.length == 1 && args[0].equals(GUI)) {
            mode = GUI;
        } else {
            System.out.println("You have not pick valid game mode to launch");
            mode = setGameMode();
        }
        DAO dataBase = new DAO();
        model = new Model();
        model.setDataBase(db);

        controller = new Controller(model);

        if (GUI.equals(mode))
            view = new View(ViewType.GUI, model, controller);
        else if (CLI.equals(mode))
            view = new View(ViewType.CLI, model, controller);

        controller.setView(view);
        controller.startGame();
    }

    public static String setGameMode() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please, pick on of the regime of Game\n" + "1. play in terminal\n" + "2. play with swing (GUI)");
        while (scanner.hasNext()) {
            String gameMode = scanner.nextLine();
            switch (gameMode) {
                case "1":
                    return CLI;
                case "2":
                    return GUI;
                default:
                    System.out.println("Incorrect input of game mode.\nDon't waste my time and play with GUI");
                    break;
            }
        }
        return GUI;
    }
}
