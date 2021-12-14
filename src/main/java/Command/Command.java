package Command;

import java.util.Scanner;

/**
 * интерфейс для команд
 */
public interface Command {
    /**
     * выполнение команд
     *
     * @param command команда
     * @param user    ввод пользователя
     */
    void execute(String command, Scanner user);
}
