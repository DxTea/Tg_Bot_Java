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
        out.println(OutputMessages.HELLO_LINE.getOutput());
        out.println(OutputMessages.HELLO_MENU.getOutput());
        Scanner input = new Scanner(in);
        String key = input.nextLine().toLowerCase(Locale.ROOT);

        runCommand(key, input);
    }

    private static void runCommand(String command, Scanner user) {
        Command commandToExecute = commands.get(command);
        if (commandToExecute != null) commandToExecute.execute(command, user);
        else {
            out.println(OutputMessages.WRONG_COMMAND_LINE.getOutput());
            ConsoleBotController.starting();
        }

    }

    /**
     * пользователь выбирает игру
     *
     * @return строка с номером выбранной игры
     */
    public static String givePlayerPossibleChoice() {
        out.println(OutputMessages.CHOOSE_GAME_MENU.getOutput());
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
        out.println(OutputMessages.WANT_TRY_AGAIN_MENU.getOutput());
        Scanner scanner = new Scanner(in);
        String input = scanner.nextLine();
        PlayerAskAgainMenu playerAskAgainMenu=PlayerAskAgainMenu.getNameByGameNumber(input);

        switch (playerAskAgainMenu) {
            case EXIT -> exit(0);
            case TO_MAIN_MENU -> ConsoleBotController.starting();
            case PLAY_AGAIN -> {
                GameName gameName= GameName.valueOf(game);
                switch (gameName) {
                    case HANGMAN -> Hangman.play();
                    case TICTACTOE -> TicTacToe.play();
                    default -> out.println(OutputMessages.NO_GAME_SELECTED.getOutput());
                }
            }

            default -> {
                out.println(OutputMessages.WRONG_NAME.toString());
                askPlayerAgain();
            }
        }
    }

    private void fillCommands() {
        commands = new HashMap<>();
        commands.put("/start", new Start(this));
        commands.put("/help", new Help(this));
        commands.put("/exit", new Exit(this));
    }

    public void setGame(String gameName) {
        game = gameName;
    }
}
