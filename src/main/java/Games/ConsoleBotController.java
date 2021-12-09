package Games;

import java.util.*;

/**
 * класс для выбора игры, основное меню бота
 */
public class ConsoleBotController {
    public static String game = "no game";
    public static Map<String, Command> commands;
    private static GameChoicer gameChoicer;

    public ConsoleBotController() {
        fillCommands();
        gameChoicer=new GameChoicer(this);
    }

    /**
     * запуск бота
     */
    public static void starting() {
        System.out.println("\nHello!");
        System.out.println("Print 'help' to get info about bot. \nOr print 'start'.");
        Scanner input = new Scanner(System.in);
        String key = input.nextLine().toLowerCase(Locale.ROOT);

        Scanner user = input;
        runCommand(key, user);
    }

    private static void runCommand(String command, Scanner user) {
        Command commandToExecute = commands.get(command);
        if (commandToExecute == null) {
            System.out.println("Wrong command");
            ConsoleBotController.starting();
        }
        else {
            commandToExecute.execute(command, user);
        }

    }

    /**
     * пользователь выбирает игру
     *
     * @return строка с номером выбранной игры
     */
    public static String givePlayerPossibleChoice() {
        System.out.println("Choose your game: \nprint number \n 1) Hangman  \n 2) TicTacToe  \n 3) back");
        Scanner input = new Scanner(System.in);
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
        System.out.println("Wanna try again? \n 1) Yes \n 2) Main menu \n 3) Exit");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        switch (input) {
            case ("2") -> ConsoleBotController.starting();
            case ("3") -> System.exit(0);
            case ("1") -> {
                if (Objects.equals(game, "Hangman")) {
                    Hangman.play();
                } else if (Objects.equals(game, "TicTacToe")) {
                    TicTacToe.startingTicTacToe();
                } else System.out.println("No game selected");
            }

            default -> {
                System.out.println("Wrong name \n");
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
