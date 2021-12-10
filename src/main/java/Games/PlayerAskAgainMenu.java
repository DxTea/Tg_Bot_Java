package Games;

public enum PlayerAskAgainMenu {
    EXIT("3"),
    TO_MAIN_MENU("2"),
    PLAY_AGAIN("1");

    private String code;

    PlayerAskAgainMenu(String s) {
        code=s;
    }

    public static PlayerAskAgainMenu getNameByGameNumber(String name) {
        if (PLAY_AGAIN.getCode().equals(name)) // надо сделать switch наверное или я что-то не понимаю
            return PLAY_AGAIN;
        if (TO_MAIN_MENU.getCode().equals(name))
            return TO_MAIN_MENU;
        return EXIT;
    }

    private String getCode(){
        return this.code;
    }
}
