package Games;

public enum OutputMessages { // called OUTPUT MESSAGES, not Hangman or something else, thus adding ALL STRING CONSTANTS here
    RIGHT("Правильно!"),
    WRONG("Неправильно!"),
    WIN("Вы выйграли!"),
    LOOSE("Вы проиграли..."),
    DRAW("Ничья"),
    SAME("Эта буква уже была."),
    INPUT("Введите букву: "),
    ANSWER("Это было слово: "),
    WORD("Слово: "),
    LIFE("Жизни: "),
    DESCRIPTION_LINE("This is telegram bot with games"),
    HELP_LINE("List of commands: \n /start- to run the bot \n /exit- to exit the bot \n /help- info about bot \n print anything to continue"),
    NO_GAME_SELECTED("No game selected"),
    WANT_TRY_AGAIN_MENU("Wanna try again? \n 1) Yes \n 2) Main menu \n 3) Exit"),
    CHOOSE_GAME_MENU("Choose your game: \nprint number \n 1) Hangman  \n 2) TicTacToe  \n 3) back"),
    HELLO_LINE("\nHello!"),
    HELLO_MENU("Print '/help' to get info about bot. \nOr print '/start'."),
    WRONG_COMMAND_LINE("Wrong command"),
    WRONG_NAME("Wrong name"),
    USER_TICTACTOE_HELP_LINE("Введите номер столбца и строки (от 1 до 3) через пробел или с новой строки"),
    USER_TICTACTOE_TRY_AGAIN_LINE("Что-то не так. Введите повторно"),
    USER_TICTACTOE_CHOOSE_CHAR_LINE("Если хотите играть крестиками - введите x, иначе o (по английски)");


    private final String output;

    OutputMessages(String s) {
        output = s;
    }

    public String getOutput() {
        return output;
    }
}
