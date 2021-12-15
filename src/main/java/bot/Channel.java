package bot;

import Games.TicTacToe;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class Channel {
    public Channel(Bot bot, TicTacToe game, String input, String chatId) {
        m_bot = bot;
        m_game = game;
        m_game.channel = this;
        m_input = input;
        m_chatId = chatId;

    }
    private Bot m_bot;
    private TicTacToe m_game;
    private String m_input;
    private String m_chatId;


    public void startGame() {
        m_game.play();
    }

    public void sendToUser(String output) {
        try {
            m_bot.execute(SendMessage.builder().chatId(m_chatId).text(output).build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public void updateInput (String input) {
        m_input = input;
    }

    public String sendToGame () {
        return m_input;
    }
}
