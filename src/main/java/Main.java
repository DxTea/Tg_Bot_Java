import Menu.BotController;
import bot.LaunchEnvironment;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import bot.Bot;

import java.util.Scanner;

/**
 * основной класс
 */
public class Main {
    public static void main(String[] args) {
        LaunchEnvironment environment = null;
        showInstructions();
        Scanner scanner = new Scanner(System.in);
        Integer input = Integer.parseInt(scanner.nextLine());
        boolean toGo = true;
        while (toGo) {
            if (!input.equals(1) && !input.equals(2)) {
                showInstructions();
                input = scanner.nextInt();
            } else {
                toGo=false;
                break;
            }
        }
        if (input == 1) environment = LaunchEnvironment.CONSOLE;
        if (input == 2) environment = LaunchEnvironment.TELEGRAM;

        switch (environment) {
            case CONSOLE -> {
                BotController.start();
            }
            case TELEGRAM -> {
                try {
                    TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
                    Bot bot = new Bot();
                    botsApi.registerBot(bot);
                    BotController botController = new BotController(LaunchEnvironment.TELEGRAM, bot);
                    bot.setBotController(botController);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + environment);
        }

    }

    private static void showInstructions() {
        System.out.println("Введите 1 или 2");
        System.out.println("1 - Консоль");
        System.out.println("2 - Телеграм");
    }
}
