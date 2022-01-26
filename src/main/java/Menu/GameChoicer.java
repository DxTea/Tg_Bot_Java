package Menu;

import Messeges.*;
import Games.*;

import static Menu.BotController.givePlayerPossibleChoice;
import static Messeges.GameName.*;
import static java.lang.System.*;

/**
 * основной класс выбора и запуска игры
 */
public class GameChoicer {
    private static BotController m_consoleBotController;

    public GameChoicer(BotController consoleBotController) {
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
            case BATTLESHIPWAR -> {
                m_consoleBotController.setGame(BATTLESHIPWAR.toString());
                BattleshipWar.start();
            }
            case AGAIN -> BotController.start();
            default -> {
                out.println(OutputMessages.WRONG_NAME);
                playChosenGame(givePlayerPossibleChoice());
            }
        }
    }
}
