package Games;

import java.util.Objects;
import java.util.Scanner;

public class GameChoice {
    public static String game = "no game";

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
                GameChoice.starting();
            }
            case ("start") -> whichOne(choice());
            case ("exit") -> System.exit(0);
            default -> {
                System.out.println("Wrong command");
                GameChoice.starting();
            }
        }
    }

    private static String choice() {
        System.out.println("Choose your game: \nprint number \n 1) Hangman  \n 2) TicTacToe  \n 3) back");
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }

    private static void whichOne(String gameName) {
        switch (gameName) {
            case ("1") -> {
                game = "Hangman";
                Hangman.startingHangman();
            }
            case ("2") -> {
                game = "TicTacToe";
                TicTacToe game = new TicTacToe();
                game.gameLogic();
            }
            case ("3") -> GameChoice.starting();
            default -> {
                System.out.println("Wrong name \n");
                whichOne(choice());
            }
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
            case ("1") -> {
                if (Objects.equals(game, "Hangman")) {
                    Hangman.startingHangman();
                } else if (Objects.equals(game, "TicTacToe")) {
                    //TicTacToe.startingTicTacToe();
                } else System.out.println("No game selected");
            }

            default -> {
                System.out.println("Wrong name \n");
                again();
            }
        }
    }

}
