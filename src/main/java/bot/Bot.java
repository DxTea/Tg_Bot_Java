package bot;

import Games.TicTacToe;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Bot extends TelegramLongPollingBot {
    private boolean isGameStarted = false;
    private Channel channel;

    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            String input = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();
            //SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
            //message.setChatId(update.getMessage().getChatId().toString());
            //message.setText(update.getMessage().getText());
            if (input.equals("/start_t")) {
                isGameStarted = true;
                channel = new Channel(this, new TicTacToe(), input, chatId);
                channel.startGame();
            }

            if (isGameStarted) {
                channel.updateInput(input);
            }
//            try {
//                execute(message); // Call method to send the message
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
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
}