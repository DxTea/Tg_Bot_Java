package bot;

import Games.BasePlayer;
import Games.Player;
import Messeges.GameName;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

public class Bot extends TelegramLongPollingBot {
    private final String token = "5042608301:AAHmlep4BboIBpE9yKwbFh-Ht0N4mwTuPnA";
    private final String botUsername = "T1LER_bot";

    private final Map<String, String> playerNameToChatId = new HashMap<>();
    private final List<GameHandler> lobbies = new ArrayList<>();
    private boolean isGameChosen = false;

    private String[] currentAvailableCommands; // for checking if player answered expectedly
    private final String startCommand = "/start";
    private final String helpCommand = "/help";
    private final String exitCommand = "/exit";
    private final String startTicTacToeCommand = "/start_TicTacToe"; // createLobbyTicTacToe
    private final String startHangmanCommand = "/start_Hangman"; // createLobbyHangmanCommand
    private final String startBattleshipWarCommand = "/start_BattleshipWar"; // createLobbyBattleshipWarCommand
    private final String showLobbiesCommand = "/show_lobbies";
    private final String[] standardCommands = {startCommand, helpCommand, exitCommand};
    private final String[] standardGameCommands = {startHangmanCommand};

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageFromInput = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();
            String currentUser = update.getMessage().getChat().getUserName();

            if (!playerNameToChatId.containsKey(currentUser)) playerNameToChatId.put(currentUser, chatId);
            if (!lobbies.isEmpty()) {
                for (GameHandler lobby : lobbies) {
                    if (lobby.m_playerNameToChatId.containsKey(currentUser)) {
                        lobby.m_playerMessages.add(new Message(currentUser, messageFromInput));
                        return;
                    }
                }
            }
            operateWithUserFirstly(messageFromInput, currentUser, chatId);
        }
    }

    private void operateWithUserFirstly(String messageFromInput, String currentUser, String chatId) {
        switch (messageFromInput) {
            case startCommand -> sendOutputToUser(currentUser,
                    standardGameCommands,
                    "Привет! Это телеграмм-бот, в котором можно поиграть в виселицу, начинай!", true);
            case helpCommand -> sendOutputToUser(currentUser, standardCommands,
                    "Нажми старт", true);
            case exitCommand -> killLobby(currentUser);
            case startHangmanCommand -> createLobby(currentUser, GameName.HANGMAN);
        }
    }

    private void createLobby(String currentUser, GameName game) {
        GameHandler lobby = getLobby(currentUser, playerNameToChatId.get(currentUser), game, this);
        startLobbyThread(lobby);
        lobbies.add(lobby);
    }

    private static GameHandler getLobby(String currentUser, String chatId, GameName game, Bot telegramBot) {
        Player player = new BasePlayer(currentUser);
        GameHandler lobby = new GameHandler(currentUser, chatId, player, new GameLogicToBot(telegramBot), game);
        return lobby;
    }

    private void startLobbyThread(GameHandler lobby) {
        Thread lobbyThread = new Thread(lobby);
        lobbyThread.start();
    }

    public void killLobby(String currentUser) {
        String chatID = playerNameToChatId.get(currentUser);
        GameName game = GameName.HANGMAN;
        GameHandler lobby = getLobby(currentUser, chatID, game, this);
        sendOutputToUser(lobby.m_creator, standardCommands, "Пока!", true);
        lobbies.remove(lobby);
    }

    public void sendOutputToUser(String playerName, String[] availableCommands, String text, boolean commandsInRows) {
        currentAvailableCommands = availableCommands; // to know possible answers
        String chatId = playerNameToChatId.get(playerName); // find player's chatId by playerName
        if (chatId == null) return;

        SendMessage message = SendMessage
                .builder()
                .text(text)
                .chatId(chatId)
                .build();
        if (availableCommands.length != 0) {
            ReplyKeyboardMarkup replyKeyboardMarkup = getReplyKeyboardMarkup(availableCommands, commandsInRows);
            message.setReplyMarkup(replyKeyboardMarkup);
        }

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private ReplyKeyboardMarkup getReplyKeyboardMarkup(String[] commands, boolean commandsInRows) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        if (commandsInRows) {
            for (String command : commands) {
                KeyboardRow row = new KeyboardRow();
                row.add(command);
                keyboardRows.add(row);
            }
        } else {
            KeyboardRow row = new KeyboardRow();
            for (String command : commands) {
                row.add(command);
            }
            keyboardRows.add(row);
        }
        keyboardMarkup.setKeyboard(keyboardRows);
        return keyboardMarkup;
    }
}