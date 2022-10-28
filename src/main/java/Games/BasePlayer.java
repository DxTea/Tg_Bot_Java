package Games;

public class BasePlayer implements Player{
    /**
     * Имя игрока (тег в тг)
     */
    public String name;

    /**
     * Создание игрока по имени
     * @param name как назвать игрока
     */
    public BasePlayer(String name){
        this.name = name;
    }

    @Override
    public String getPlayerName() {
        return name;
    }
}
