import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class TestsGameChoice {
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
