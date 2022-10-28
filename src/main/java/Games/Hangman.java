package Games;

import bot.GameLogicToBot;

import java.util.ArrayList;
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
    private boolean exitFlag;
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

    /**
     * конструктор
     *
     * @param word зашифрованное слово
     */
    public Hangman(BasePlayer player, String word, GameLogicToBot logic) {
        super(player);
        currentPlayer = player;
        gameLogicToBot = logic;
        hiddenWord = word.toLowerCase();
        progress = new ArrayList<>();
        for (int i = 0; i < hiddenWord.length(); i++) {
            progress.add(hiddenWordMask);
        }
        mistakes = new ArrayList<>();
        exitFlag = false;
    }

    /**
     * набор слов для игры
     */
    private static final String[] wordsEasy = {
            "пальто", "лопата",
            "зебра", "магазин"
    };
    private static final String[] wordsMedium = {
            "коромысло", "леопард", "ноутбук", "конфета", "телефон"
    };
    private static final String[] wordsHard = {
            "одиночество",
            "коромысло", "влюблённость",
            "рефакторинг", "человечество"
    };

    /**
     * случайный выбор слова
     *
     * @return слово
     */
    public static String generateWord(String difficult) {
        return switch (difficult) {
            case ("1") -> wordsEasy[(int) (Math.random() * wordsEasy.length)];
            case ("2") -> wordsMedium[(int) (Math.random() * wordsMedium.length)];
            case ("3") -> wordsHard[(int) (Math.random() * wordsHard.length)];
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
        while (!exitFlag) {
            currentGame = new Hangman(currentPlayer, word, gameLogicToBot);
            currentGame.play();
        }
    }

    private String chooseDifficulty() {
        return "1";
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
//        exitFlag = false;
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
            exitFlag = true;
            return true;
        }
        return false;
    }

    private boolean winCase() {
        if (!progress.contains(hiddenWordMask)) {
            String[] msg = new String[]{WIN.getOutput()};
            sendToUser(msg, currentPlayer.getPlayerName(), false);
            exitFlag = true;
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
//            String[] msg1 = new String[]{" " + letter};
//            sendToUser(msg1, currentPlayer.getPlayerName(), false);
            msg1[0] = msg1[0] + " " + letter;
        }
        sendToUser(msg1, currentPlayer.getPlayerName(), false);

        String[] msg2 = new String[]{"\n" + LIFE.getOutput()};
        sendToUser(msg2, currentPlayer.getPlayerName(), false);
        String[] msg3 = new String[lives];
        for (int i = 0; i < lives; i++) {
//            String[] msg3 = new String[]{"💙"};
//            sendToUser(msg3, currentPlayer.getPlayerName(), false);
            msg3[i] = "💙";
        }
        sendToUser(msg3, currentPlayer.getPlayerName(), false);

//        String[] msg4 = new String[]{"\n\n\n" + INPUT.getOutput()};
//        sendToUser(msg4, currentPlayer.getPlayerName(), false);
    }

    @Override
    public boolean defineEndOfGame() {
        return exitFlag;
    }

    @Override
    public void run() {
        start();
    }
}