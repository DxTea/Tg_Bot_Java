package Games;

public enum GameName {
    HANGMAN("1"),
    TICTACTOE("2"),
    AGAIN("3");

    private final String gameNumber;
    GameName(String s) {
        gameNumber =s;
    }

    public static GameName getNameByGameNumber(String name){
        if (HANGMAN.getGameNumber().equals(name)) // надо сделать switch наверное или я что-то не понимаю
            return HANGMAN;
        if (TICTACTOE.getGameNumber().equals(name))
            return TICTACTOE;
        return AGAIN.getGameNumber().equals(name) ? AGAIN : null;
    }

    public String getGameNumber(){
        return gameNumber;
    }
}
