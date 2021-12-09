package Games;

import java.util.Scanner;

import static Games.ConsoleBotController.givePlayerPossibleChoice;

public class Start implements Command {

    public Start(ConsoleBotController consoleBotController) {
    }

    @Override
    public void execute(String command, Scanner user) {
        ConsoleBotController.playChosenGame(givePlayerPossibleChoice());
    }
}
