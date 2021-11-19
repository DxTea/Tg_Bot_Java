import java.util.Scanner;

public class GameChoice {
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
            case ("1") -> Hangman.startingHangman();
            case ("2") -> {
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

}