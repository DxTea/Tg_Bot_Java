package Messeges;

/**
 * сообщения бота
 */
public enum OutputMessages {
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
    DESCRIPTION_LINE("Это телеграмм-бот с играми"),
    HELP_LINE("Список команд: \n /start- для запуска бота \n /exit- для выхода из бота \n /help- справочная информация \n введите любой символ чтобы продолжить"),
    NO_GAME_SELECTED("Вы не выбрали никакой игры"),
    WANT_TRY_AGAIN_MENU("Хотите попробовать еще раз? \n 1) Да \n 2) Главное меню \n 3) Выход из бота"),
    CHOOSE_GAME_MENU("Выберите вашу игру: \nвведите номер \n 1) Hangman  \n 2) TicTacToe  \n 3) назад"),
    HELLO_LINE("\nПривет!"),
    HELLO_MENU("Введите '/help' чтобы получить информацию о боте. \nИли введите '/start'."),
    WRONG_COMMAND_LINE("Неправильная команда"),
    WRONG_NAME("Неправильное имя"),
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
