package Command;

import Menu.BotController;

import java.util.Scanner;

/**
 * выход из бота
 */
public class Exit implements Command {
    public Exit(BotController consoleBotController) {
    }

    @Override
    public void execute(String command) {
        System.exit(0);
    }
}
