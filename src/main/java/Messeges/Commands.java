package Messeges;

public enum Commands {
    RESTART("/restart"),
    QUIT_GAME("/quit_game"),
    EASY("/easy"),
    MEDIUM("/medium"),
    HARD("/hard");

    private final String command;

    Commands(String c) {
        command = c;
    }

    public String getCommand() {
        return command;
    }
}
