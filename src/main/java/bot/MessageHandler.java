package bot;

import Menu.*;

import java.util.Queue;

public class MessageHandler implements Runnable{
    private Queue<String> messagesToCheck;
    private BotController botController;

    @Override
    public void run() {
        while (true){
            try {
                String command = messagesToCheck.poll();
                if (command==null) throw new IllegalArgumentException();
                if (botController.hasCommand(command)) botController.runCommand(command);
                else if (command.length()<5) botController.receiveMessageFromPlayer(command);
                botController.wakeUp();
                // TODO: 1) handle commands (you have those in botController)
                // TODO: 2) reroute input and output to tg or have ability to get input from here.
                // done

                //botController.sendMessageToPlayer(command);
            }
            catch (Exception ex){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public MessageHandler(Queue<String> _messagesToCheck, BotController _botController) {
        messagesToCheck=_messagesToCheck;
        botController=_botController;
    }
}
