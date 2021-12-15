package Messeges;

/**
 * список игр бота
 */
public enum GameName {
    HANGMAN("1"),
    TICTACTOE("2"),
    BATTLESHIPWAR ("3"),
    AGAIN("4");

    private final String gameNumber;

    GameName(String s) {
        gameNumber = s;
    }

    public static GameName getNameByGameNumber(String name) {
        if (HANGMAN.getGameNumber().equals(name))
            return HANGMAN;
        if (TICTACTOE.getGameNumber().equals(name))
            return TICTACTOE;
        if (BATTLESHIPWAR.getGameNumber().equals(name))
            return BATTLESHIPWAR;
        return AGAIN;
    }

    public String getGameNumber() {
        return gameNumber;
    }
}
