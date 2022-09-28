package Menu;

import Games.*;
import Command.*;
import Messeges.*;
import bot.*;
import bot.LaunchEnvironment;

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
    private Queue<String> m_messagesToHandle;
    private final Queue<String> m_messagesToTryFindAnswer;
    private boolean go=false;
    public Channel channel;

    public BotController() {
        fillCommands();
        GameChoicer gameChoicer = new GameChoicer(this);
        m_messagesToTryFindAnswer=new ArrayDeque<>();
    }

    public BotController(LaunchEnvironment environment, Bot bot){
        this();
        m_environment=environment;
        m_bot=bot;
        m_messagesToHandle =new ArrayDeque<>();
    }

    @Override
    public void run() {
        start();


    }

    /**
     * запуск бота
     */
    public void start() {
        printToUser(OutputMessages.HELLO_LINE.getOutput());
        printToUser(OutputMessages.HELLO_MENU.getOutput());

//        GameHandler gameHandler = new GameHandler(m_messagesToHandle, this);
//        Thread messageHandlerThread= new Thread(gameHandler);
//        messageHandlerThread.start();
    }

/*
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
*/

    public static String chooseDifficulty() {
        printToUser(OutputMessages.CHOOSE_DIFFICULT.getOutput());
        Scanner scanner = new Scanner(in);

        return scanner.nextLine();
    }

    /**
     * запуск команд
     *
     * @param command команда
     */
    public void runCommand(String command) {
        fillCommands();
        Command commandToExecute = commands.get(command);
        if (commandToExecute != null) commandToExecute.execute(command);
        else {
            printToUser(OutputMessages.WRONG_COMMAND_LINE.getOutput());
            this.start();
        }

    }

    /**
     * пользователь выбирает игру
     *
     * @return строка с номером выбранной игры
     */

    public static void givePlayerPossibleChoice() {
        printToUser(OutputMessages.CHOOSE_GAME_MENU.getOutput());
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
        BotController bot = new BotController();
        switch (playerAskAgainMenu) {
            case EXIT -> exit(0);
            case TO_MAIN_MENU -> bot.start();
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
        commands.put("/"+ StartTicTacToe.class.getSimpleName(), new StartTicTacToe(this));
        commands.put("/"+ StartHangman.class.getSimpleName(), new StartHangman(this));
        commands.put("/"+ StartBattleShipWar.class.getSimpleName(), new StartBattleShipWar(this));
        commands.put("/"+ Back.class.getSimpleName(), new Back(this));
        //commands.put("any", new TranferInput(this, ))
    }

    /**
     * определения игры
     *
     * @param gameName имя игры, которую выбирает пользователь
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
//                    m_bot.sendMessageToUser(m_chatId, text);
            }
        }
    }

    public void setChatId(String chatId) {
        m_chatId=chatId;
    }

    public void putMessageFromPlayer(String text){
        m_messagesToHandle.add(text);
        channel.queueMessagesToGame(text);
    }
    //private void setBot

    public Queue<String> getM_messagesToHandle() {
        return m_messagesToHandle;
    }

//    public void sendMessageToPlayer(String text){
//        m_bot.sendMessageToUser(m_chatId, text);
//    }

    public void receiveMessageFromPlayer(String message){
        synchronized (m_messagesToTryFindAnswer){
            m_messagesToTryFindAnswer.add(message);
        }
    }

    public void wakeUp(){

        this.notify();
    }

    public String getUserAnswer() {
        switch (m_environment){

            case CONSOLE -> {
                return new Scanner(in).nextLine();
            }
            case TELEGRAM -> {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                    String answer=m_messagesToTryFindAnswer.poll();
                    return answer;

            }
        }
        throw new IllegalArgumentException();
    }

    public boolean hasCommand(String command) {
        return commands.containsKey(command);
    }

    public void letGo() { go=true;
    }
}
