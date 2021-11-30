package Games;

import java.util.Objects;
import java.util.Scanner;

/**
 * класс для выбора игры, основное меню
 */
public class GameChoicer {
    public static String game = "no game";

    /**
     * запуск бота
     */
    public static void starting() {
        System.out.println("\nHello!");
        System.out.println("Print 'help' to get info about bot. \nOr print 'start'.");
        Scanner input = new Scanner(System.in);
        String key = input.nextLine();
        switch (key) {
            case ("help") -> {
                System.out.println("This is telegram bot with games");
                System.out.println("List of commands: \n start- to run the bot \n exit- to exit the bot \n help- info about bot \n print anything to continue");
                input.nextLine();
                GameChoicer.starting();
            }
            case ("start") -> whichOne(choice());
            case ("exit") -> System.exit(0);
            default -> {
                System.out.println("Wrong command");
                GameChoicer.starting();
            }
        }
    }

    /**
     * пользователь выбирает игру
     * @return строка с номером выбранной игры
     */
    private static String choice() {
        System.out.println("Choose your game: \nprint number \n 1) Hangman  \n 2) TicTacToe  \n 3) back");
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }

    /**
     * запуск выбранной игры
     * @param gameName номер выбранной игры
     */
    private static void whichOne(String gameName) {
        switch (gameName) {
            case ("1") -> {
                game = "Hangman";
                Hangman.play();
            }
            case ("2") -> {
                game = "TicTacToe";
                TicTacToe.startingTicTacToe();
            }
            case ("3") -> GameChoicer.starting();
            default -> {
                System.out.println("Wrong name \n");
                whichOne(choice());
            }
        }
    }

    /**
     * игровое подменю
     */
    public static void again() {
        System.out.println("Wanna try again? \n 1) Yes \n 2) Main menu \n 3) Exit");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        switch (input) {
            case ("2") -> GameChoicer.starting();
            case ("3") -> System.exit(0);
            case ("1") -> {
                if (Objects.equals(game, "Hangman")) {
                    Hangman.play();
                } else if (Objects.equals(game, "TicTacToe")) {
                    TicTacToe.startingTicTacToe();
                } else System.out.println("No game selected");
            }

            default -> {
                System.out.println("Wrong name \n");
                again();
            }
        }
    }

}
