package Games;

public enum GameName {
    HANGMAN("1"),
    TICTACTOE("2"),
    AGAIN("3");

    private final String gameNumber;

    GameName(String s) {
        gameNumber = s;
    }

    public static GameName getNameByGameNumber(String name) {
        if (HANGMAN.getGameNumber().equals(name))
            return HANGMAN;
        if (TICTACTOE.getGameNumber().equals(name))
            return TICTACTOE;
        return AGAIN;
    }

    public String getGameNumber() {
        return gameNumber;
    }
}
