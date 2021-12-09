package Games;

import java.util.*;

import static java.lang.System.*;

/**
 * класс для выбора игры, основное меню бота
 */
public class ConsoleBotController {
    public static String game = "no game";
    public static Map<String, Command> commands;

    public ConsoleBotController() {
        fillCommands();
        GameChoicer gameChoicer = new GameChoicer(this);
    }

    /**
     * запуск бота
     */
    public static void starting() {
        out.println("\nHello!");
        out.println("Print 'help' to get info about bot. \nOr print 'start'.");
        Scanner input = new Scanner(in);
        String key = input.nextLine().toLowerCase(Locale.ROOT);

        runCommand(key, input);
    }

    private static void runCommand(String command, Scanner user) {
        Command commandToExecute = commands.get(command);
        if (commandToExecute != null) commandToExecute.execute(command, user);
        else {
            out.println("Wrong command");
            ConsoleBotController.starting();
        }

    }

    /**
     * пользователь выбирает игру
     *
     * @return строка с номером выбранной игры
     */
    public static String givePlayerPossibleChoice() {
        out.println("Choose your game: \nprint number \n 1) Hangman  \n 2) TicTacToe  \n 3) back");
        Scanner input = new Scanner(in);
        return input.nextLine();
    }

    /**
     * запуск выбранной игры
     * * @param gameName номер выбранной игры
     */
    public static void playChosenGame(String gameName) {
        GameChoicer.playChosenGame(gameName);
    }

    /**
     * игровое подменю
     */
    public static void askPlayerAgain() {
        out.println("Wanna try again? \n 1) Yes \n 2) Main menu \n 3) Exit");
        Scanner scanner = new Scanner(in);
        String input = scanner.nextLine();
        switch (input) {
            case ("2") -> ConsoleBotController.starting();
            case ("3") -> exit(0);
            case ("1") -> {
                switch (game) {
                    case "Hangman" -> Hangman.play();
                    case "TicTacToe" -> TicTacToe.play();
                    default -> out.println("No game selected");
                }
            }

            default -> {
                out.println("Wrong name \n");
                askPlayerAgain();
            }
        }
    }

    private void fillCommands() {
        commands=new HashMap<>();
        commands.put("/start", new Start(this));
        commands.put("/help", new Help(this));
        commands.put("/exit", new Exit(this));
    }

    public void setGame(String gameName) {
        game=gameName;
    }
}
