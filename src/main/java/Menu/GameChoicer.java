package Menu;

import Messeges.*;
import Games.*;

import static Menu.ConsoleBotController.givePlayerPossibleChoice;
import static Messeges.GameName.*;
import static java.lang.System.*;

/**
 * основной класс выбора и запуска игры
 */
public class GameChoicer {
    private static ConsoleBotController m_consoleBotController;

    public GameChoicer(ConsoleBotController consoleBotController) {
        m_consoleBotController = consoleBotController;
    }

    /**
     * запуск выбранной игры
     *
     * @param gameName название игры
     */
    public static void playChosenGame(String gameName) {
        switch (getNameByGameNumber(gameName)) {
            case HANGMAN -> {
                m_consoleBotController.setGame(HANGMAN.toString());
                Hangman.start();
            }
            case TICTACTOE -> {
                m_consoleBotController.setGame(TICTACTOE.toString());
                TicTacToe.start();
            }
            case AGAIN -> ConsoleBotController.start();
            default -> {
                out.println(OutputMessages.WRONG_NAME);
                playChosenGame(givePlayerPossibleChoice());
            }
        }
    }
}
