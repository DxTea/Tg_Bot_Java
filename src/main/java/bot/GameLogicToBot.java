package bot;

public class GameLogicToBot {
    private final Bot m_telegramBot;

    private String m_inputToGameLogic;
    private String m_currentPlayer;
    private String[] m_availableCommands;

    public GameLogicToBot(Bot telegramBot) {
        m_telegramBot = telegramBot;
    }

    public void sendOutputToUser(String playerName, String[] availableCommands, String text, boolean commandsInRows, boolean fromGame){
        if (fromGame) m_currentPlayer=playerName;
        m_availableCommands=availableCommands;
        m_telegramBot.sendOutputToUser(playerName, availableCommands, text, commandsInRows);
    }

    public String getCurrentPlayer() {
        return "";
    }

    public String[] getAvailableCommands() {
        return new String[0];
    }

    public void setInputMessage(String toString) {
    }
}
