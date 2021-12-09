package Games;

import java.util.Scanner;

public class Help implements Command {
    private ConsoleBotController m_consoleBotController;

    public Help(ConsoleBotController consoleBotController) {
        m_consoleBotController=consoleBotController;
    }

    @Override
    public void execute(String command, Scanner user) {
        System.out.println("This is telegram bot with games");
        System.out.println("List of commands: \n start- to run the bot \n exit- to exit the bot \n help- info about bot \n print anything to continue");
        user.nextLine();
        ConsoleBotController.starting();
    }
}
