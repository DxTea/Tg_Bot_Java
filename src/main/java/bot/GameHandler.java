package bot;

import Games.Player;
import Menu.*;
import Messeges.GameName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameHandler implements Runnable{
    public Map<String, String> m_playerNameToChatId;
    public ConcurrentLinkedQueue<Message> m_playerMessages;

    public String m_creator;
    public Player m_player;
    public GameLogicToBot m_gameLogicToBot;
    public GameName m_game;
    private final String[] availableCommands = {};
    private String[] m_availableCommandsInGame = {};

    public GameHandler(String creator, String chatId, Player player,
                       GameLogicToBot gameLogicToBot, GameName game) {
        m_creator = creator;
        m_player = player; // creator already there
        m_gameLogicToBot = gameLogicToBot;
        m_game = game;
        m_playerNameToChatId = new HashMap<>();
        m_playerMessages = new ConcurrentLinkedQueue<>();
        m_playerNameToChatId.put(creator, chatId);
        sendOutputToUser(creator, availableCommands, true);
    }

    private void sendOutputToUser(String playerName, String[] availableCommands, boolean commandsInRows) {
        m_availableCommandsInGame = availableCommands;
        m_gameLogicToBot.sendOutputToUser(playerName, availableCommands, commandsInRows, false);
    }

    @Override
    public void run() {
        while (true){
            try {
//                String command = messagesToCheck.poll();
//                if (command==null) throw new IllegalArgumentException();
//                if (botController.hasCommand(command)) botController.runCommand(command);
//                else if (command.length()<5)
//                    botController.receiveMessageFromPlayer(command);
                // botController.wakeUp();
                // TODO: 1) handle commands (you have those in botController)
                // TODO: 2) reroute input and output to tg or have ability to get input from here.
                // done

                //botController.sendMessageToPlayer(command);
            }
            catch (Exception ex){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
