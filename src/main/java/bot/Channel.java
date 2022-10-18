package bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayDeque;
import java.util.Queue;


public class Channel {
//    public Channel(Bot bot, String chatId) {
//        m_bot = bot;
//        m_chatId = chatId;
//    }
//    private final Bot m_bot;
//    private final String m_chatId;
//
//    private Queue<String> m_messagesToCheck = new ArrayDeque<>();
//
//    public void sendToUser(String output) {
//        try {
//            m_bot.execute(SendMessage.builder().chatId(m_chatId).text(output).build());
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void queueMessagesToGame(String input) {
//        m_messagesToCheck.add(input);
//    }
//
//    public String sendToGame() {
//        if (!m_messagesToCheck.isEmpty())
//            return m_messagesToCheck.poll();
//        return null;
//    }
}
