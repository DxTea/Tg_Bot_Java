package Games;

import java.util.Scanner;

public class Exit implements Command {
    public Exit(ConsoleBotController consoleBotController) {
    }

    @Override
    public void execute(String command, Scanner user) {
        System.exit(0);
    }
}
