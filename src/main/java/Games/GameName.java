package Games;

public enum GameName {
    HANGMAN("1"),
    TICTACTOE("2"),
    AGAIN("3");

    private String gameNumber;
    GameName(String s) {
        gameNumber =s;
    }

    public static GameName getNameByGameNumber(String name){
        if (HANGMAN.getGameNumber().equals(name)) // надо сделать switch наверное или я что-то не понимаю
            return HANGMAN;
    else if (TICTACTOE.getGameNumber().equals(name))
            return TICTACTOE;
    else if (AGAIN.getGameNumber().equals(name))
            return AGAIN;
        return null;
    }

    public String getGameNumber(){
        return gameNumber;
    }
}
