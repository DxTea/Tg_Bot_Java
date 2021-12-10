package Games;

import java.util.Objects;

import static Games.ConsoleBotController.givePlayerPossibleChoice;

public class GameChoicer {
    private static ConsoleBotController m_consoleBotController;

    public GameChoicer(ConsoleBotController consoleBotController) {
        m_consoleBotController = consoleBotController;
    }

    public static void playChosenGame(String gameName) {
        switch (GameName.getNameByGameNumber(gameName)) {
            case HANGMAN -> {
                m_consoleBotController.setGame(GameName.HANGMAN.toString());
                Hangman.play();
            }
            case TICTACTOE -> {
                m_consoleBotController.setGame(GameName.TICTACTOE.toString());
                TicTacToe.play();
            }
            case AGAIN -> ConsoleBotController.starting();
            default -> {
                System.out.println(OutputMessages.WRONG_NAME.toString());
                playChosenGame(givePlayerPossibleChoice());
            }
        }
    }
}
