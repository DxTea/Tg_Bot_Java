package Menu;

import Messeges.*;
import Games.*;

import static Menu.ConsoleBotController.givePlayerPossibleChoice;

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
     * @param gameName название игры
     */
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
            case AGAIN -> ConsoleBotController.start();
            default -> {
                System.out.println(OutputMessages.WRONG_NAME);
                playChosenGame(givePlayerPossibleChoice());
            }
        }
    }
}
