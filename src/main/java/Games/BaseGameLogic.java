package Games;

import bot.GameLogicToBot;
import java.util.Scanner;

public abstract class BaseGameLogic implements Game, Runnable{
    protected final BasePlayer player;
    protected Scanner scanner = new Scanner(System.in);
    protected GameLogicToBot gameLogicToBot;

    public BaseGameLogic(BasePlayer player) {
        this.player = player;
    }

    /**
     * Определяет закончилась ли игра
     * @return true, закончилась, false ещё не закончилась
     */
    public abstract boolean defineEndOfGame();

    protected void sendToUser(String[] message, String playerName, boolean changeKeyboard) {
        if(changeKeyboard){
            gameLogicToBot.sendOutputToUser(playerName,message,String.join("\n",message),true, true);
        }
        else {
            gameLogicToBot.sendOutputToUser(playerName, new String[0], String.join("\n", message), true, true);
        }
    }

    protected String getFromUser(){return gameLogicToBot.getInputToGameLogic();}
}
