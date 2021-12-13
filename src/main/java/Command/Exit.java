package Command;

import Menu.ConsoleBotController;

import java.util.Scanner;

/**
 * выход из бота
 */
public class Exit implements Command {
    public Exit(ConsoleBotController consoleBotController) {
    }

    @Override
    public void execute(String command, Scanner user) {
        System.exit(0);
    }
}
