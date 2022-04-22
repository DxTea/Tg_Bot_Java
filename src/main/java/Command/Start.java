package Command;

import Menu.BotController;

import java.util.Scanner;

import static Menu.BotController.givePlayerPossibleChoice;

/**
 * запуск бота
 */
public class Start implements Command {
    private BotController botController;

    public Start(BotController consoleBotController) {
        botController=consoleBotController;
    }

    @Override
    public void execute(String command) {
        givePlayerPossibleChoice();
        String answer=botController.getUserAnswer();
        BotController.playChosenGame(answer);
    }

}
