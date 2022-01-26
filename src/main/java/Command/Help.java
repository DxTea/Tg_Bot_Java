package Command;

import Menu.BotController;
import Messeges.OutputMessages;

import java.util.Scanner;

/**
 * справочная информация
 */
public class Help implements Command {

    public Help(BotController consoleBotController) {
    }

    @Override
    public void execute(String command, Scanner user) {
        System.out.println(OutputMessages.DESCRIPTION_LINE.getOutput());
        System.out.println(OutputMessages.HELP_LINE.getOutput());
        user.nextLine();
        BotController.start();
    }
}
