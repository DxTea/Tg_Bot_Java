package bot;

import Games.BasePlayer;
import Games.Player;

public class GameLogicToBot {
    private final Bot m_telegramBot;

    private String m_inputToGameLogic;
    private Player m_currentPlayer;
    private String[] m_availableCommands;

    public GameLogicToBot(Bot telegramBot) {
        m_telegramBot = telegramBot;
        m_currentPlayer = new BasePlayer("");
    }

    public String getInputToGameLogic() {
        while (m_inputToGameLogic == null){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String tmp=m_inputToGameLogic;
        m_inputToGameLogic=null;
        return tmp;
    }

    public void sendOutputToUser(String playerName, String[] availableCommands, String text, boolean commandsInRows, boolean fromGame){
        if (fromGame) m_currentPlayer = new BasePlayer(playerName);
        m_availableCommands=availableCommands;
        m_telegramBot.sendOutputToUser(playerName, availableCommands, text, commandsInRows);
    }

    public void setInputMessage(String m_message) {
        m_inputToGameLogic = m_message;
    }

    public void removeLobby(){ m_telegramBot.removeLobby(m_currentPlayer.getPlayerName());}

    public void removeAllLobbies(){ m_telegramBot.removeAllLobbies();}

    public Player getCurrentPlayer(){ return m_currentPlayer;}

    public String[] getAvailableCommands() {
        return m_availableCommands;
    }
}
