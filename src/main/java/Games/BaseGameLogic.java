package Games;

import bot.GameLogicToBot;
import java.util.Scanner;

public abstract class BaseGameLogic implements Game, Runnable{
    protected final BasePlayer player;
    protected GameLogicToBot gameLogicToBot;

    public BaseGameLogic(BasePlayer player) {
        this.player = player;
    }


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
