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
     * тест на неправильный ввод названия
     */
    @Test
    void wrongName() {
        GameChoice choice = new GameChoice();
        String actual = choice.whichOne("Hanman");
        Assert.assertEquals("Wrong name", actual);
    }
}
