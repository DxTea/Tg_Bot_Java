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

    private static LaunchEnvironment m_environment=LaunchEnvironment.CONSOLE;
    private static Bot m_bot;
    private static String m_chatId;
    private Queue<String> m_messagesToCheck;


    public BotController() {
        fillCommands();
        GameChoicer gameChoicer = new GameChoicer(this);
    }

    public BotController(LaunchEnvironment environment, Bot bot){
        this();
        m_environment=environment;
        m_bot=bot;
        m_messagesToCheck=new ArrayDeque<>();

    }

    @Override
    public void run() {
        start();


    }

    /**
     * запуск бота
     */
    public static void start() {
        printToUser(OutputMessages.HELLO_LINE.getOutput());
        printToUser(OutputMessages.HELLO_MENU.getOutput());

        Scanner input = new Scanner(in);
        String key = input.nextLine().toLowerCase(Locale.ROOT);
        runCommand(key, input);
    }

    private static String getMessageFromPlayer() {
        switch (m_environment){
            case CONSOLE -> {
                Scanner input = new Scanner(in);
                String key = input.nextLine().toLowerCase(Locale.ROOT);
                return key;
            }
            case TELEGRAM -> {
            }
        }
        return null;
    }

    public static String chooseDifficulty() {
        printToUser(OutputMessages.CHOOSE_DIFFICULT.getOutput());
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
            printToUser(OutputMessages.WRONG_COMMAND_LINE.getOutput());
            BotController.start();
        }

    }

    /**
     * пользователь выбирает игру
     *
     * @return строка с номером выбранной игры
     */
    public static String givePlayerPossibleChoice() {
        printToUser(OutputMessages.CHOOSE_GAME_MENU.getOutput());
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
        commands.put("/help", new Help());
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

    private static void printToUser(String text){
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

    public void putMessageFromPlayer(String text){
        m_messagesToCheck.add(text);
    }
    //private void setBot

}
