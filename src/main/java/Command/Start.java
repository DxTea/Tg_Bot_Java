package Command;

import Menu.BotController;

import java.util.Scanner;

import static Menu.BotController.givePlayerPossibleChoice;

/**
 * запуск бота
 */
public class Start implements Command {

    public Start(BotController consoleBotController) {
    }

    @Override
    public void execute(String command, Scanner user) {
        BotController.playChosenGame(givePlayerPossibleChoice());
    }
}
