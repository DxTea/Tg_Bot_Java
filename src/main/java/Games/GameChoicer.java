package Games;

import java.util.Objects;

import static Games.ConsoleBotController.givePlayerPossibleChoice;

public class GameChoicer {
    private static ConsoleBotController m_consoleBotController;

    public GameChoicer(ConsoleBotController consoleBotController) {
        m_consoleBotController = consoleBotController;
    }

    public static void playChosenGame(String gameName) {
        switch (Objects.requireNonNull(GameName.getNameByGameNumber(gameName))) {
            case HANGMAN -> {
                m_consoleBotController.setGame("Hangman");
                Hangman.play();
            }
            case TICTACTOE -> {
                m_consoleBotController.setGame("TicTacToe");
                TicTacToe.play();
            }
            case AGAIN -> ConsoleBotController.starting();
            default -> {
                System.out.println("Wrong name \n");
                playChosenGame(givePlayerPossibleChoice());
            }
        }
    }
}
