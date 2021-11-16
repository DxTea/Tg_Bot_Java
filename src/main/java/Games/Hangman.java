package Games;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Hangman implements PatternForGames {
    /**
     * символ для обозначения скрывающихся букв
     */
    private static final char hiddenSymbol = '_';
    /**
     * количество попыток (допустимых ошибок)
     */
    private int lives = 5;
    /**
     * зашифрованное слово
     */
    private String secret;
    private List<Character> progress, mistakes;

    /**
     * конструктор для инициализации полей
     */
    public Hangman(String word) {
        secret = word.toLowerCase();
        progress = new ArrayList<>();
        for (int i = 0; i < secret.length(); i++) {
            progress.add(hiddenSymbol);
        }
        mistakes = new ArrayList<Character>();
    }

    /**
     * набор слов для игры
     */
    private static String[] words = {
            "пальто", "одиночество", "лопата",
            "коромысло", "леопард", "кошка"
    };

    public static String generateWord() {
        return words[(int) (Math.random() * words.length)];
    }

    /**
     * предлагаем игроку поиграть, начало игры
     */
    public static void startingHangman(){
        Hangman currentGame = null;
        while (true) {
            String word = generateWord();
            currentGame = new Hangman(word);
            currentGame.gameLogic();

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Хотите сыграть ещё раз? +/-");
                String input = scanner.nextLine();
                if (input.length() == 1) {
                    char answer = input.charAt(0);
                    if (answer == '-') System.exit(0);
                    if (answer == '+') break;
                }
            }
        }
    }

    /**
     * игровая логика, основной метод игры
     */
    @Override
    public void gameLogic() {
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
                        if (!progress.contains(hiddenSymbol)) {
                            System.out.println("Вы выйграли!");
                            break;
                        }
                    } else {
                        System.out.print("Неправильно! ");
                        mistakes.add(userVariant);
                        if (--lives == 0) {
                            System.out.println("\nВы проиграли...");
                            break;
                        }
                    }
                    printProgress();
                }
            }
        }
        System.out.println("Это было слово: " + secret.toUpperCase());
    }

    /**
     * проверяем наличие введенного символа в слове
     * @param userVariant буква пользователя
     * @return true если буква есть, иначе false
     */
    private boolean checkGuess(char userVariant) {
        boolean guessed = false;
        for (int index = secret.indexOf(userVariant); index >= 0; index = secret.indexOf(userVariant, index + 1)) {
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
