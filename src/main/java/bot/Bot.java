package bot;

import Menu.BotController;
import Messeges.GameName;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collections;

public class Bot extends TelegramLongPollingBot {
    private boolean isGameChosen = false;
    private Channel m_channel;
    private GameName gameName;
    private BotController m_botController;

    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            String input = update.getMessage().getText();
            char firstLetter=input.charAt(0);
            String chatId = update.getMessage().getChatId().toString();
            //SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
            //message.setChatId(update.getMessage().getChatId().toString());
            //message.setText(update.getMessage().getText());
            m_botController.setChatId(chatId);
            Thread thread=new Thread(m_botController);
            thread.start();
        }
    }

    @Override
    public String getBotUsername() {
        return "T1LER_bot";
    }

    @Override
    public String getBotToken() {
        return "5042608301:AAHmlep4BboIBpE9yKwbFh-Ht0N4mwTuPnA";
    }

    public void setBotController(BotController botController) {
        m_botController=botController;
    }
}