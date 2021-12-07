package Games;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
            "коромысло", "леопард"
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
        while (!exitFlag) {
            String word = generateWord();
            currentGame = new Hangman(word);
            currentGame.playGame();
            GameChoicer.again();
        }
    }

    /**
     * игровая логика
     */
    @Override
    public void playGame() {
        printProgress();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine().toLowerCase();
            if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                System.out.print("Введите букву: ");
            } else {
                char userVariant = input.charAt(0);
                if (mistakes.contains(userVariant) || progress.contains(userVariant)) {
                    System.out.println("Эта буква уже была");
                    System.out.print("Введите букву: ");
                } else {
                    if (checkGuess(userVariant)) {
                        System.out.print("Правильно! ");
                        if (!progress.contains(hiddenWordMask)) {
                            System.out.println("Вы выйграли!");
                            exitFlag = true;
                            break;
                        }
                    } else {
                        System.out.print("Неправильно! ");
                        mistakes.add(userVariant);
                        if (--lives == 0) {
                            System.out.println("\nВы проиграли...");
                            exitFlag = true;
                            break;
                        }
                    }
                    printProgress();
                }
            }
        }
        System.out.println("Это было слово: " + hiddenWord.toUpperCase());
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
        System.out.print("Слово:");
        for (char letter : progress) {
            System.out.print(" " + letter);
        }
        System.out.print("\nЖизни: ");
        for (int i = 0; i < lives; i++) {
            System.out.print("💙");
        }
        System.out.print("\n\n\nВведите букву: ");
    }
}