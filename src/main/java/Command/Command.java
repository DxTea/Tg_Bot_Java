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
     */
    void execute(String command);
}
