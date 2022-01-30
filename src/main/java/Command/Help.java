package Command;

import Menu.BotController;
import Messeges.OutputMessages;

import java.util.Scanner;

/**
 * справочная информация
 */
public class Help implements Command {

    @Override
    public void execute(String command) {
        System.out.println(OutputMessages.DESCRIPTION_LINE.getOutput());
        System.out.println(OutputMessages.HELP_LINE.getOutput());
//        user.nextLine();
        BotController bot = new BotController();
        bot.start();
    }
}
