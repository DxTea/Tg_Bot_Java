import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Hangman implements PatternForGames {
    private static final char HIDDEN_WORD = '_';
    private int lives = 5;
    private final String secret;
    private final List<Character> progress;
    private final List<Character> mistakes;

    //конструктор
    public Hangman(String word) {
        secret = word.toLowerCase();
        progress = new ArrayList<>();
        for (int i = 0; i < secret.length(); i++) {
            progress.add(HIDDEN_WORD);
        }
        mistakes = new ArrayList<>();
    }

    //набор слов для игры
    private static final String[] words = {
            "пальто", "одиночество", "лопата",
            "коромысло", "леопард"
    };

    //случайный выбор слова
    public static String generateWord() {
        return words[(int) (Math.random() * words.length)];
    }

    //запуск игры
    public static void startingHangman() {
        Hangman currentGame;
        while (true) {
            String word = generateWord();
            currentGame = new Hangman(word);
            currentGame.gameLogic();
            again();
        }

    }

    //игровое подменю
    public static void again() {
        System.out.println("Wanna try again? \n 1)yes \n 2)menu \n 3)exit");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        switch (input) {
            case ("2") -> GameChoice.starting();
            case ("3") -> System.exit(0);
            case ("1") -> startingHangman();
            default -> {
                System.out.println("Wrong name \n");
                again();
            }
        }
    }

    //игровая логика
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
                        if (!progress.contains(HIDDEN_WORD)) {
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

    //проверяем наличие введенного символа в слове
    private boolean checkGuess(char userVariant) {
        boolean guessed = false;
        for (int index = secret.indexOf(userVariant); index >= 0; index = secret.indexOf(userVariant, index + 1)) {
            progress.set(index, userVariant);
            guessed = true;
        }
        return guessed;
    }

    //метод показывает прогресс слова, оставшиеся жизни и запрашивает ввод новой буквы
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
