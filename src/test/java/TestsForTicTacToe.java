import Games.TicTacToe;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class TestsForTicTacToe {
    /**
     * тест на ход ИИ
     */
    @Test
    void AITurnTest() {
        TicTacToe game = new TicTacToe();
        game.initializeTable();
        char[][] tableOld = game.getTable().clone();
        game.AITurn();
        Assert.assertNotEquals(game.getTable(), tableOld);
    }

    /**
     * тест на проверку выигрыша
     */
    @Test
    void checkIfWin1() {
        TicTacToe game = new TicTacToe();
        game.initializeTable();
        game.setTable('x', 1, 1);
        game.setTable('x', 0, 1);
        game.setTable('x', 2, 1);
        Assert.assertEquals(true, game.checkIfWin('x'));
    }

    /**
     * тест на проверку выигрыша
     */
    @Test
    void checkIfWin2() {
        TicTacToe game = new TicTacToe();
        game.initializeTable();
        game.setTable('x', 1, 1);
        game.setTable('o', 0, 1);
        game.setTable('x', 2, 1);
        game.setTable('x', 0, 0);
        Assert.assertEquals(false, game.checkIfWin('x'));
    }

    /**
     * тест на проверку выигрыша
     */
    @Test
    void checkIfWin3() {
        TicTacToe game = new TicTacToe();
        game.initializeTable();
        game.setTable('x', 2, 0);
        game.setTable('o', 1, 1);
        game.setTable('x', 0, 2);
        game.setTable('x', 1, 0);
        Assert.assertEquals(false, game.checkIfWin('x'));
    }

    /**
     * тест на проверку выигрыша
     */
    @Test
    void checkIfWin4() {
        TicTacToe game = new TicTacToe();
        game.initializeTable();
        game.setTable('o', 1, 1);
        game.setTable('o', 0, 0);
        game.setTable('o', 2, 2);
        Assert.assertEquals(true, game.checkIfWin('o'));
    }

    /**
     * тест на проверку заполнено ли поле
     */
    @Test
    void tableIsFull() {
        TicTacToe game = new TicTacToe();
        game.initializeTable();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                game.setTable('x', j, i);
        Assert.assertEquals(true, game.isTableFull());
    }

    /**
     * тест на проверку заполнено ли поле
     */
    @Test
    void tableIsNotFull() {
        TicTacToe game = new TicTacToe();
        game.initializeTable();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                game.setTable('x', j, i);
        game.setTable('.', 1, 2);
        Assert.assertEquals(false, game.isTableFull());
    }
}
