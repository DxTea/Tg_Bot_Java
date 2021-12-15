package Messeges;

/**
 * список команд по завершению игр
 */
public enum PlayerAskAgainMenu {
    EXIT("3"),
    TO_MAIN_MENU("2"),
    PLAY_AGAIN("1");

    private final String code;

    PlayerAskAgainMenu(String s) {
        code = s;
    }

    public static PlayerAskAgainMenu getNameByGameNumber(String name) {
        if (PLAY_AGAIN.getCode().equals(name))
            return PLAY_AGAIN;
        if (TO_MAIN_MENU.getCode().equals(name))
            return TO_MAIN_MENU;
        return EXIT;
    }

    private String getCode() {
        return this.code;
    }
}
