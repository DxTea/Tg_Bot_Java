package Games;

import bot.GameHandler;
import bot.GameLogicToBot;
import bot.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static Messeges.OutputMessages.*;

/**
 * Виселица
 */
public class Hangman extends BaseGameLogic {
    /**
     * маска скрытого слова
     */
    private static final char hiddenWordMask = '_';
    /**
     * количество жизней
     */
    private int lives = 5;
    /**
     * скрытое слово
     */
    private final String hiddenWord;
    /**
     * прогресс отгадывания слова
     */
    private final List<Character> progress;
    /**
     * mistakes- неправильные буквы
     */
    private final List<Character> mistakes;

    private final BasePlayer currentPlayer;
    private final GameHandler currentLobby;

    /**
     * конструктор
     *
     * @param word зашифрованное слово
     */
    public Hangman(BasePlayer player, String word, GameLogicToBot logic, GameHandler lobby) {
        super(player);
        currentPlayer = player;
        gameLogicToBot = logic;
        hiddenWord = word.toLowerCase();
        progress = new ArrayList<>();
        for (int i = 0; i < hiddenWord.length(); i++) {
            progress.add(hiddenWordMask);
        }
        mistakes = new ArrayList<>();
        currentLobby = lobby;
    }



    /**
     * случайный выбор слова
     *
     * @return слово
     */
    public static String generateWord(String difficult) {
        return switch (difficult) {
            case ("/easy") -> Dictionary.wordsEasy[(int) (Math.random() * Dictionary.wordsEasy.length)];
            case ("/medium") -> Dictionary.wordsMedium[(int) (Math.random() * Dictionary.wordsMedium.length)];
            case ("/hard") -> Dictionary.wordsHard[(int) (Math.random() * Dictionary.wordsHard.length)];
            default -> throw new IllegalStateException("Unexpected value: " + difficult);
        };
    }

    /**
     * запуск игры
     */
    public void start() {
        String difficult = chooseDifficulty();
        Hangman currentGame;
        String word = generateWord(difficult);
        while (!defineEndOfGame()) {
            currentGame = new Hangman(currentPlayer, word, gameLogicToBot, currentLobby);
            currentGame.play();
        }
        sendToUser(new String[]{"/restart", "/quit_game"}, currentPlayer.getPlayerName(), true);
    }

    private String chooseDifficulty() {
        String[] msg6 = new String[]{CHOOSE_DIFFICULT.getOutput()};
        sendToUser(msg6, currentPlayer.getPlayerName(), false);
        sendToUser(new String[]{"/easy", "/medium", "/hard"}, currentPlayer.getPlayerName(), true);
        String difficult = getFromUser();
        String[] listOfDif = {"/easy", "/medium", "/hard"};
        if (!Arrays.asList(listOfDif).contains(difficult)){
            String[] msg5 = new String[]{""+WRONG_DIFFICULT};
            sendToUser(msg5, currentPlayer.getPlayerName(), false);
            chooseDifficulty();
        }
        return difficult;
    }

    /**
     * игровая логика
     */
    @Override
    public void play() {
        printProgress();
        String[] msg4 = new String[]{"\n\n\n" + INPUT.getOutput()};
        sendToUser(msg4, currentPlayer.getPlayerName(), false);
        for (; ; ) {
            String input = getFromUser().toLowerCase();
            if (checkCorrectInput(input)) break;
        }
//        String[] msg = new String[]{ANSWER.getOutput() + hiddenWord.toUpperCase() + "\n"};
        String[] msg = new String[]{ANSWER.getOutput() + hiddenWord.toUpperCase()};
        sendToUser(msg, currentPlayer.getPlayerName(), false);

    }

    private boolean checkCorrectInput(String input) {
        if (input.length() == 1 && Character.isLetter(input.charAt(0))) {
            char userVariant = input.charAt(0);
            return checkInput(userVariant);
        } else {
            String[] msg = new String[]{INPUT.getOutput()};
            sendToUser(msg, currentPlayer.getPlayerName(), false);
        }
        return false;
    }

    private boolean checkInput(char userVariant) {
        if (!mistakes.contains(userVariant) && !progress.contains(userVariant)) {
            return checkGuessCase(userVariant);
        } else {
            String[] msg = new String[]{SAME.getOutput(), "\n", INPUT.getOutput()};
            sendToUser(msg, currentPlayer.getPlayerName(), false);
        }
        return false;
    }

    private boolean checkGuessCase(char userVariant) {
        if (checkGuess(userVariant)) {
            String[] msg = new String[]{RIGHT.getOutput() + "\n"};
            sendToUser(msg, currentPlayer.getPlayerName(), false);
            if (winCase()) return true;
        } else {
            String[] msg = new String[]{WRONG.getOutput() + "\n"};
            sendToUser(msg, currentPlayer.getPlayerName(), false);
            mistakes.add(userVariant);
            if (looseCase()) return true;
        }
        printProgress();
        return false;
    }

    private boolean looseCase() {
        if (--lives == 0) {
            String[] msg = new String[]{"\n" + LOOSE.getOutput()};
            sendToUser(msg, currentPlayer.getPlayerName(), false);
            currentLobby.exitFlag = true;
            return true;
        }
        return false;
    }

    private boolean winCase() {
        if (!progress.contains(hiddenWordMask)) {
            String[] msg = new String[]{WIN.getOutput()};
            sendToUser(msg, currentPlayer.getPlayerName(), false);
            currentLobby.exitFlag = true;
            return true;
        }
        return false;
    }

    /**
     * проверяем наличие введенного символа в слове
     *
     * @param userVariant буква пользователя
     * @return true, если буква есть, иначе false
     */
    private boolean checkGuess(char userVariant) {
        boolean guessed = false;
        for (int index = hiddenWord.indexOf(userVariant); index >= 0; index = hiddenWord.indexOf(userVariant, index + 1)) {
            progress.set(index, userVariant);
            guessed = true;
        }
        return guessed;
    }

    /**
     * метод показывает прогресс слова, оставшиеся жизни и запрашивает ввод новой буквы
     */
    private void printProgress() {
        String[] msg = new String[]{WORD.getOutput()};
        sendToUser(msg, currentPlayer.getPlayerName(), false);
        String[] msg1 = new String[1];
        msg1[0] = "";
        for (char letter : progress) {
            msg1[0] = msg1[0] + " " + letter;
        }
        sendToUser(msg1, currentPlayer.getPlayerName(), false);

        String[] msg2 = new String[]{"\n" + LIFE.getOutput()};
        sendToUser(msg2, currentPlayer.getPlayerName(), false);
        String[] msg3 = new String[lives];
        for (int i = 0; i < lives; i++) {
            msg3[i] = "💙";
        }
        sendToUser(msg3, currentPlayer.getPlayerName(), false);


    }

    @Override
    public boolean defineEndOfGame() {
        return currentLobby.exitFlag;
    }

    @Override
    public void run() {
        start();
    }
}