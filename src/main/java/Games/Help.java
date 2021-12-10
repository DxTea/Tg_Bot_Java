package Games;

import java.util.Scanner;

public class Help implements Command {

    public Help(ConsoleBotController consoleBotController) {
    }

    @Override
    public void execute(String command, Scanner user) {
        System.out.println(OutputMessages.DESCRIPTION_LINE.getOutput());
        System.out.println(OutputMessages.HELP_LINE.getOutput());
        user.nextLine();
        ConsoleBotController.starting();
    }
}
