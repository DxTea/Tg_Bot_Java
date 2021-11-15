package Games;

import java.util.Random;
import java.util.Scanner;

/**
 * класс реализации игры "Крестики - Нолики"
 */
public class TicTacToe implements PatternForGames {
    private Random random;
    private Scanner scanner;
    /**
     * обозначение пустого места на поле
     */
    final char emptyCage = '.';
    /**
     * игровое поле
     */
    private char[][] table;
    /**
     * символ, которым играет пользователь
     */
    private char userSign;
    /**
     * символ, которым играет бот
     */
    private char AISign;

    /**
     * конструктор класса для инициализации полей
     */
    public TicTacToe() {
        table = new char[3][3];
        random = new Random();
        scanner = new Scanner(System.in);
    }

    /**
     * игрок выбирает играть крестиками или ноликами
     */
    private void whichSign() {
        System.out.println("Если хотите играть крестиками - введите x, иначе o (по английски)");
        while (true) {
            char symbol = scanner.next().charAt(0);
            if (symbol == 'x') {
                userSign = 'x';
                AISign = 'o';
                break;
            }
            if (symbol == 'o') {
                userSign = 'x';
                AISign = 'o';
                break;
            }
            System.out.println("Введите повторно");
        }
    }

    /**
     * реализация игровой логики
     */
    @Override
    public void gameLogic() {
        initializationTable();
        whichSign();
        while (true) {
            userTurn();
            if (checkIfWin(userSign)) {
                System.out.println("Вы выиграли!");
                break;
            }
            if (isTableFull()) {
                System.out.println("Ничья!");
                break;
            }
            AITurn();
            printTable();
            if (checkIfWin(AISign)) {
                System.out.println("Вы проиграли!");
                break;
            }
            if (isTableFull()) {
                System.out.println("Ничья!");
                break;
            }
        }
        System.out.println("GAME OVER");
        printTable();
    }

    /**
     * создание игрового поля
     */
    private void initializationTable() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                table[i][j] = emptyCage;
    }

    /**
     * показ текущего состояние стола
     */
    private void printTable() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                System.out.print(" " + table[i][j]);
            System.out.println();
        }
    }

    /**
     * ход игрока
     */
    @Override
    public void userTurn() {
        int x, y;
        do {
            System.out.println("Введите номер строки и столбца через пробел");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        }
        while (!isCellValid(x, y));
        table[y][x] = userSign;
    }

    /**
     * проверяет доступна ли клетка для хода
     * @param x номер столбца
     * @param y номер строки
     * @return true если доступна, иначе false
     */
    private boolean isCellValid(int x, int y) {
        if (x < 0 || y < 0 || x >= 3|| y >= 3)
            return false;
        return table[y][x] == emptyCage;
    }

    /**
     * ход ИИ
     */
    @Override
    public void AITurn() {
        int x, y;
        do {
            x = random.nextInt(3);
            y = random.nextInt(3);
        }
        while (!isCellValid(x, y));
        table[y][x] = AISign;
    }

    /**
     * проверка на выигрыш одной из сторон
     * @param sign крестик или нолик
     * @return true, если сторона выиграла, иначе false
     */
    private boolean checkIfWin(char sign) {
        for (int i = 0; i < 3; i++) {
            if ((table[i][0] == sign && table[i][1] == sign && table[i][2] == sign) ||
                    (table[0][i] == sign && table[1][i] == sign && table[2][i] == sign))
                return true;
        }
        return ((table[0][0] == sign && table[1][1] == sign && table[2][2] == sign) ||
                    (table[2][0] == sign && table[1][1] == sign && table[0][2] == sign));
    }

    /**
     * проверка на заполненное поле
     * нужна в случае ничьи
     * @return true, если поле заполнено, иначе false
     */
    private boolean isTableFull() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (table[i][j] == emptyCage)
                    return false;
        return true;
    }
}
