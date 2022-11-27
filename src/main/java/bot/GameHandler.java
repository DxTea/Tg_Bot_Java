package bot;

import Games.*;
import Messeges.GameName;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import static Messeges.OutputMessages.GAME_START_LINE;
import static Messeges.OutputMessages.START_HANGMAN;

public class GameHandler implements Runnable{
    public Map<String, String> m_playerNameToChatId;
    public Map<String, BaseGameLogic> m_playerNameToGame;
    public ConcurrentLinkedQueue<Message> m_playerMessages;
    private BaseGameLogic m_gameLogic;
    public volatile boolean exitFlag;

    public String m_creator;
    public Player m_player;
    public GameLogicToBot m_gameLogicToBot;
    public GameName m_game;

    private final String startGameCommand = "/start_game";
    private final String quitGameCommand = "/quit_game";
    private final String helpCommand = "/help";
    private final String exitCommand = "/exit";
    private final String[] availableCommands = {startGameCommand, helpCommand, exitCommand};
    private final String[] m_defaultCommands = {helpCommand, exitCommand};
    private final String[] m_availableCommandsInGame = {quitGameCommand};
    private boolean m_gameStarted;

    public GameHandler(String creator, String chatId, Player player,
                       GameLogicToBot gameLogicToBot, GameName game) {
        m_creator = creator;
        m_player = player;
        m_gameLogicToBot = gameLogicToBot;
        m_game = game;
        m_playerNameToChatId = new HashMap<>();
        m_playerNameToGame = new HashMap<>();
        m_playerMessages = new ConcurrentLinkedQueue<>();
        m_playerNameToChatId.put(creator, chatId);
        m_gameStarted = false;
        String firstText = "_";
        sendOutputToUser(creator, availableCommands, firstText, true);
    }

    private void sendOutputToUser(String playerName, String[] availableCommands, String text, boolean commandsInRows) {
//        m_availableCommandsInGame = availableCommands;
        m_gameLogicToBot.sendOutputToUser(playerName, availableCommands,  text, commandsInRows, false);
    }

    @Override
    public void run() {
        while (true) {
            while (!m_playerMessages.isEmpty()) {
                Message message = m_playerMessages.poll();
                if (!m_gameStarted) {                 // extract to GameManager.java
                    ifPlayerAsksHelp(message);
                    ifGameStarts(message);
                    ifPlayerAskExit(message);
                } else { // in-game logic
                    if (m_gameLogic.defineEndOfGame()) {
                        ifPlayerAskRestart(message);
                        ifPlayerAskQuit(message);
                        continue;
                    }
                    ifPlayerAskQuit(message);
                    m_gameLogicToBot.setInputMessage(message.m_message);
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void ifPlayerAsksHelp(Message message) {
        if (isEquals(message, helpCommand)){
            sendOutputToUser(message.m_playerName, availableCommands, START_HANGMAN.getOutput(), true);
        }
    }

    private void ifGameStarts(Message message) {
        if (isEquals(message, startGameCommand)) {
            establishAndStartGameThread();
        }
    }

    private void ifPlayerAskExit(Message message) {
        if (isEquals(message, exitCommand)) {
            m_gameLogicToBot.removeAllLobbies();
        }
    }

    private void ifPlayerAskRestart(Message message) {
        String restartCommand = "/restart";
        if (isEquals(message, restartCommand)) {
            m_gameLogicToBot.removeLobby();
            exitFlag = false;
            establishAndStartGameThread();
        }
    }

    private void ifPlayerAskQuit(Message message) {
        if (isEquals(message, quitGameCommand)) {
            exitFlag = true;
            m_gameLogicToBot.removeAllLobbies();
        }
    }

    private void establishAndStartGameThread() {
        switch (m_game) { // extract to GameManager.java
            case HANGMAN -> {
                m_gameLogic = new Hangman((BasePlayer) m_player, "", m_gameLogicToBot, this);
            }
            default -> throw new IllegalStateException();
        }
        m_playerNameToGame.put(((BasePlayer) m_player).name, m_gameLogic);
        Thread m_gameThread = new Thread(m_gameLogic);
        m_gameStarted = true;
        sendOutputToUser(this.m_creator, m_availableCommandsInGame,
                GAME_START_LINE.getOutput(), true);
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        m_gameThread.start();
    }

    private void sendInputToGameLogic(String m_message) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < m_message.length(); i++) {
            if (m_message.charAt(i) == '.') break;
            result.append(m_message.charAt(i));
        }
        m_gameLogicToBot.setInputMessage(result.toString());
    }

    private boolean isEquals(Message message, String command) {
        return message.m_message.equals(command);
    }
}