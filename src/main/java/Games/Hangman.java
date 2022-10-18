package Games;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static Messeges.OutputMessages.*;
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
    public static void start() {
//        String difficult = chooseDifficulty();
        String difficult = "1";
        Hangman currentGame;
        String word = generateWord(difficult);
        while (!exitFlag) {
            currentGame = new Hangman(word);
            currentGame.play();
        }
    }

    /**
     * игровая логика
     */
    @Override
    public void play() {
        printProgress();
        Scanner scanner = new Scanner(in);
        for (; ; ) {
            String input = scanner.nextLine().toLowerCase();
            if (checkCorrectInput(input)) break;
        }
        out.println(ANSWER.getOutput() + hiddenWord.toUpperCase() + "\n");
        exitFlag = false;

    }

    private boolean checkCorrectInput(String input) {
        if (input.length() == 1 && Character.isLetter(input.charAt(0))) {
            char userVariant = input.charAt(0);
            return checkInput(userVariant);
        } else {
            out.print(INPUT.getOutput());
        }
        return false;
    }

    private boolean checkInput(char userVariant) {
        if (!mistakes.contains(userVariant) && !progress.contains(userVariant)) {
            return checkGuessCase(userVariant);
        } else {
            out.println(SAME.getOutput());
            out.print(INPUT.getOutput());
        }
        return false;
    }

    private boolean checkGuessCase(char userVariant) {
        if (checkGuess(userVariant)) {
            out.print(RIGHT.getOutput() + "\n");
            if (winCase()) return true;
        } else {
            out.print(WRONG.getOutput() + "\n");
            mistakes.add(userVariant);
            if (looseCase()) return true;
        }
        printProgress();
        return false;
    }

    private boolean looseCase() {
        if (--lives == 0) {
            out.println("\n" + LOOSE.getOutput());
            exitFlag = true;
            return true;
        }
        return false;
    }

    private boolean winCase() {
        if (!progress.contains(hiddenWordMask)) {
            out.println(WIN.getOutput());
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