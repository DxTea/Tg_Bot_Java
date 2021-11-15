package Games;

/**
 * инфенфейс, служащий шаблоном для игр
 */
public interface PatternForGames {
    /**
     * основная логика игры
     */
    void gameLogic();

    /**
     * ход игрока
     */
    void userTurn();

    /**
     * ход ИИ
     */
    void AITurn();
}
