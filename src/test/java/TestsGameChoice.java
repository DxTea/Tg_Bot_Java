import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class TestsGameChoice {
    /**
     * тест запуск Виселицы
     */
    @Test
    void rightExampleHangman() {
        GameChoice choice = new GameChoice();
        String actual = choice.whichOne("Hangman");
        Assert.assertEquals("Success Hangman", actual);
    }

    /**
     * тест на запуск Крестиков-Ноликов
     */
    @Test
    void rightExampleTicTacToe() {
        GameChoice choice = new GameChoice();
        String actual = choice.whichOne("TicTacToe");
        Assert.assertEquals("Success TicTacToe", actual);
    }

    /**
     * тест на неправильный ввод названия
     */
    @Test
    void wrongName() {
        GameChoice choice = new GameChoice();
        String actual = choice.whichOne("Hanman");
        Assert.assertEquals("Wrong name", actual);
    }
}
