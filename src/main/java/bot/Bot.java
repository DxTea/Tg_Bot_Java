package bot;

import Menu.BotController;
import Messeges.GameName;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

public class Bot extends TelegramLongPollingBot {
    private boolean isGameChosen = false;
    private BotController m_botController;
    private final Map<String, BotController> chatIdToBotController=new HashMap<>();

    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {

            String input = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();

            if (!chatIdToBotController.containsKey(chatId)){
                BotController controller = new BotController(LaunchEnvironment.TELEGRAM, this);
                controller.setChatId(chatId);
                chatIdToBotController.put(chatId, controller);
                Thread controllerThread=new Thread(controller);
                controllerThread.start();
            } else {
                synchronized (chatIdToBotController){
                    chatIdToBotController.get(chatId).putMessageFromPlayer(input);
                }
            }
        }
    }

    public void sendMessageToUser(String chatId, String message){
        try {
            execute(SendMessage.builder().chatId(chatId).text(message).build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
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