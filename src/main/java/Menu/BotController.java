package Menu;

import Games.*;
import Command.*;
import Messeges.*;
import bot.Bot;
import bot.LaunchEnvironment;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

import static java.lang.System.*;

/**
 * класс для выбора игры, основное меню для бота
 */
public class BotController implements Runnable{
    public static String game = "no game";
    public static Map<String, Command> commands;

    private LaunchEnvironment m_environment=LaunchEnvironment.CONSOLE;
    private Bot m_bot;
    private String m_chatId;

    public BotController() {
        fillCommands();
        GameChoicer gameChoicer = new GameChoicer(this);
    }

    public BotController(LaunchEnvironment environment, Bot bot){
        this();
        m_environment=environment;
        m_bot=bot;
    }

    @Override
    public void run() {
        start();
    }

    /**
     * запуск бота
     */
    public static void start() {
        out.println(OutputMessages.HELLO_LINE.getOutput());
        out.println(OutputMessages.HELLO_MENU.getOutput());
        Scanner input = new Scanner(in);
        String key = input.nextLine().toLowerCase(Locale.ROOT);

        runCommand(key, input);
    }

    public static String chooseDifficulty() {
        out.println(OutputMessages.CHOOSE_DIFFICULT.getOutput());
        Scanner scanner = new Scanner(in);

        return scanner.nextLine();
    }

    /**
     * запуск команд
     *
     * @param command команда
     * @param user    ввод пользователя
     */
    private static void runCommand(String command, Scanner user) {
        Command commandToExecute = commands.get(command);
        if (commandToExecute != null) commandToExecute.execute(command, user);
        else {
            out.println(OutputMessages.WRONG_COMMAND_LINE.getOutput());
            BotController.start();
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
        PlayerAskAgainMenu playerAskAgainMenu = PlayerAskAgainMenu.getNameByGameNumber(input);

        switch (playerAskAgainMenu) {
            case EXIT -> exit(0);
            case TO_MAIN_MENU -> BotController.start();
            case PLAY_AGAIN -> {
                GameName gameName = GameName.valueOf(game);
                switch (gameName) {
                    case HANGMAN -> Hangman.start();
                    case TICTACTOE -> TicTacToe.start();
                    case BATTLESHIPWAR -> BattleshipWar.start();
                    default -> out.println(OutputMessages.NO_GAME_SELECTED.getOutput());
                }
            }

            default -> {
                out.println(OutputMessages.WRONG_NAME);
                askPlayerAgain();
            }
        }
    }

    /**
     * инициализация команд
     */
    private void fillCommands() {
        commands = new HashMap<>();
        commands.put("/start", new Start(this));
        commands.put("/help", new Help(this));
        commands.put("/exit", new Exit(this));
    }

    /**
     * определения игры
     *
     * @param gameName имя игры, которую выбрает пользователь
     */
    public void setGame(String gameName) {
        game = gameName;
    }

    private void printToUser(String text){
        switch (m_environment){
            case CONSOLE -> {
                out.println(text);
            }
            case TELEGRAM -> {
                try {
                    m_bot.execute(SendMessage.builder().text(text).chatId(m_chatId).build());
                } catch (TelegramApiException e) {

                }
            }
        }
    }

    public void setChatId(String chatId) {
        m_chatId=chatId;
    }

    //private void setBot

}
