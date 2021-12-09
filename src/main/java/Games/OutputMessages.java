package Games;

public enum OutputMessages {
    RIGHT("Правильно!"),
    WRONG("Неправильно!"),
    WIN("Вы выйграли!"),
    LOOSE("Вы проиграли..."),
    SAME("Эта буква уже была."),
    INPUT("Введите букву: "),
    ANSWER("Это было слово: "),
    WORD("Слово: "),
    LIFE("Жизни: ");


    private final String output;

    OutputMessages(String s) {
        output = s;
    }

    public String getOutput() {
        return output;
    }
}
