package bot;

import Menu.*;

import java.util.Queue;

public class messagesQueue implements Runnable{
    private Queue<String> messagesToCheck;

    @Override
    public void run() {

    }

    public messagesQueue() {
        BotController bot = new BotController();
        messagesToCheck = bot.getM_messagesToCheck();
    }
}
