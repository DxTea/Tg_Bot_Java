package Games;

import java.util.Scanner;

import static Games.ConsoleBotController.givePlayerPossibleChoice;

public class Start implements Command{
    private ConsoleBotController m_consoleBotController;

    public Start(ConsoleBotController consoleBotController) {
        m_consoleBotController=consoleBotController;
    }

    @Override
    public void execute(String command, Scanner user) {
        ConsoleBotController.playChosenGame(givePlayerPossibleChoice());
    }
}
