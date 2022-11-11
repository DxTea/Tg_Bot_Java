package Games;

public class BasePlayer implements Player{

    public String name;

    public BasePlayer(String name){
        this.name = name;
    }

    @Override
    public String getPlayerName() {
        return name;
    }
}
