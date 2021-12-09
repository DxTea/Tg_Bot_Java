package Games;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static Games.OutputMessages.*;
import static java.lang.System.*;

/**
 * Виселица
 */
public class Hangman implements Game {
    /**
     * маска скрытого слова
     */
    private static final char hiddenWordMask = '_';
    /**
     * флаг выхода из программы
     */
    private static boolean exitFlag = false;
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

    /**
     * конструктор
     *
     * @param word зашифрованное слово
     */
    public Hangman(String word) {
        hiddenWord = word.toLowerCase();
        progress = new ArrayList<>();
        for (int i = 0; i < hiddenWord.length(); i++) {
            progress.add(hiddenWordMask);
        }
        mistakes = new ArrayList<>();
    }

    /**
     * набор слов для игры
     */
    private static final String[] words = {
            "пальто", "одиночество", "лопата",
            "коромысло", "леопард", "зебра",
            "виселица", "влюблённость", "ноутбук",
            "рефакторинг", "человечество", "магазин"
    };

    /**
     * случайный выбор слова
     *
     * @return слово
     */
    public static String generateWord() {
        return words[(int) (Math.random() * words.length)];
    }

    /**
     * запуск игры
     */
    public static void play() {
        Hangman currentGame;
        String word = generateWord();
        while (!exitFlag) {
            currentGame = new Hangman(word);
            currentGame.playGame();
            ConsoleBotController.askPlayerAgain();
        }
    }

    /**
     * игровая логика
     */
    @Override
    public void playGame() {
        printProgress();
        Scanner scanner = new Scanner(in);
        for (; ; ) {
            String input = scanner.nextLine().toLowerCase();
            if (input.length() == 1 && Character.isLetter(input.charAt(0))) {
                char userVariant = input.charAt(0);
                if (!mistakes.contains(userVariant) && !progress.contains(userVariant)) {
                    if (checkGuess(userVariant)) {
                        out.print(RIGHT.getOutput() + "\n");
                        if (!progress.contains(hiddenWordMask)) {
                            out.println(WIN.getOutput());
                            exitFlag = true;
                            break;
                        }
                    } else {
                        out.print(WRONG.getOutput() + "\n");
                        mistakes.add(userVariant);
                        if (--lives == 0) {
                            out.println("\n" + LOOSE.getOutput());
                            exitFlag = true;
                            break;
                        }
                    }
                    printProgress();
                } else {
                    out.println(SAME.getOutput());
                    out.print(INPUT.getOutput());
                }
            } else {
                out.print(INPUT.getOutput());
            }
        }
        out.println(ANSWER.getOutput() + hiddenWord.toUpperCase() + "\n");
        exitFlag = false;

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
        out.print(WORD.getOutput());
        for (char letter : progress) {
            out.print(" " + letter);
        }
        out.print("\n" + LIFE.getOutput());
        for (int i = 0; i < lives; i++) {
            out.print("💙");
        }
        out.print("\n\n\n" + INPUT.getOutput());
    }
}