package Command;

import Menu.ConsoleBotController;

import java.util.Scanner;

import static Menu.ConsoleBotController.givePlayerPossibleChoice;

/**
 * запуск бота
 */
public class Start implements Command {

    public Start(ConsoleBotController consoleBotController) {
    }

    @Override
    public void execute(String command, Scanner user) {
        ConsoleBotController.playChosenGame(givePlayerPossibleChoice());
    }
}
