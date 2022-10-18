package bot;

import Games.Game;
import Games.Hangman;
import Games.Player;
import Messeges.GameName;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameHandler implements Runnable{
    public Map<String, String> m_playerNameToChatId;
    public ConcurrentLinkedQueue<Message> m_playerMessages;
    private Thread m_gameThread;
    private Game m_gameLogic;

    public String m_creator;
    public Player m_player;
    public GameLogicToBot m_gameLogicToBot;
    public GameName m_game;

    private final String startGameCommand = "/start_game";
    private final String quitGameCommand = "/quit_game";
    private final String helpCommand = "/help";
    private final String exitCommand = "/exit";
    private final String[] availableCommands = {startGameCommand, helpCommand};
    private final String[] m_defaultCommands = {helpCommand, exitCommand};
    private String[] m_availableCommandsInGame = {quitGameCommand};
    private boolean m_gameStarted;
    private final String startPrefix = "/start";

    public GameHandler(String creator, String chatId, Player player,
                       GameLogicToBot gameLogicToBot, GameName game) {
        m_creator = creator;
        m_player = player;
        m_gameLogicToBot = gameLogicToBot;
        m_game = game;
        m_playerNameToChatId = new HashMap<>();
        m_playerMessages = new ConcurrentLinkedQueue<>();
        m_playerNameToChatId.put(creator, chatId);
        m_gameStarted = false;
        String firstText = "Давай начнем!";
        sendOutputToUser(creator, availableCommands, firstText, true);
    }

    private void sendOutputToUser(String playerName, String[] availableCommands, String text, boolean commandsInRows) {
        m_availableCommandsInGame = availableCommands;
        m_gameLogicToBot.sendOutputToUser(playerName, availableCommands,  text, commandsInRows, false);
    }

    @Override
    public void run() {
        while (true) {
            while (!m_playerMessages.isEmpty()) {
                Message message = m_playerMessages.poll();
                if (!m_gameStarted) {
                    ifPlayerAsksHelp(message);
                    ifGameStarts(message);
                } else { // in-game logic
                    if (message.m_message.startsWith(startPrefix)) continue;
                    m_availableCommandsInGame = m_gameLogicToBot.getAvailableCommands();
                    sendOutputToUser(message.m_playerName, m_defaultCommands,
                            "Not your turn" + "Try again",
                            true);
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
            sendOutputToUser(message.m_playerName, availableCommands, "Press start", true);
        }
    }

    private void ifGameStarts(Message message) {
        if (isEquals(message, startGameCommand)) {
            establishAndStartGameThread();
        }
    }

    private void establishAndStartGameThread() {
        switch (m_game) {
//            case HANGMAN -> m_gameLogic = new Hangman(getBasePlayerArray(m_playerList), new Deck(m_deckType), m_gameLogicToBot);
            case HANGMAN -> Hangman.start();
            default -> throw new IllegalStateException();
        }
        m_gameThread = new Thread((Runnable) m_gameLogic);
        m_gameStarted = true;
        sendOutputToUser(m_gameLogicToBot.getCurrentPlayer(), m_availableCommandsInGame,
                "Игра началась", true);
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