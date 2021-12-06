package Games;

import java.util.Random;
import java.util.Scanner;

/**
 * класс реализации игры "Крестики - Нолики"
 */
public class TicTacToe implements Game {
    private final Random random = new Random();
    private final Scanner scanner = new Scanner(System.in);
    /**
     * обозначение пустого места на поле
     */
    final char emptyCage = '.';
    /**
     * игровое поле
     */
    private final char[][] table = new char[3][3];;
    /**
     * символ, которым играет пользователь
     */
    private char userSign;
    /**
     * символ, которым играет бот
     */
    private char AISign;

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
                userSign = 'o';
                AISign = 'x';
                break;
            }
            System.out.println("Введите повторно");
        }
    }

    /**
     * позволяет нам самим составлять table
     *
     * @param sign символ, который хотим поставить
     * @param x    столбец
     * @param y    строка
     */
    public char[][] setTable(char sign, int x, int y) {
        table[y][x] = sign;
        return table;
    }

    /**
     * запуск игры
     */
    public static void startingTicTacToe(){
        TicTacToe currentGame = new TicTacToe();
        currentGame.playGame();
    }

    /**
     * реализация игровой логики
     */
    @Override
    public void playGame() {
        initializeTable();
        whichSign();
        if (userSign == 'x') userTurn();
        while (true) {
            AITurn();
            printTable();
            if (checkIfWin(AISign)) {
                System.out.println("Вы проиграли!");
                printTable();
                GameChoicer.again();
                break;
            }
            if (isTableFull()) {
                System.out.println("Ничья!");
                printTable();
                GameChoicer.again();
                break;
            }
            userTurn();
            if (checkIfWin(userSign)) {
                System.out.println("Вы выиграли!");
                printTable();
                GameChoicer.again();
                break;
            }
            if (isTableFull()) {
                System.out.println("Ничья!");
                printTable();
                GameChoicer.again();
                break;
            }
        }
    }

    /**
     * создание игрового поля
     */
    public void initializeTable() {
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
    public void userTurn() {
        int x, y;
        System.out.println("Введите номер столбца и строки (от 1 до 3) через пробел или с новой строки");
        x = scanner.nextInt() - 1;
        y = scanner.nextInt() - 1;
        while (isCellNotValid(x, y)) {
            System.out.println("Что-то не так. Введите повторно");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        }
        table[y][x] = userSign;
    }

    /**
     * проверяет доступна ли клетка для хода
     *
     * @param x номер столбца
     * @param y номер строки
     * @return true если доступна, иначе false
     */
    private boolean isCellNotValid(int x, int y) {
        if (x < 0 || y < 0 || x >= 3 || y >= 3)
            return true;
        return table[y][x] != emptyCage;
    }

    /**
     * ход ИИ
     */
    public void AITurn() {
        int x, y;
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++)
            if (table[i][j] == userSign) {
                for (int n = -1; n <=1; n++)
                    for (int m = -1; m <=1; m++) {
                        if (!isCellNotValid(j + m, i + n)) {
                            table[i+n][j+m] = AISign;
                            return;
                        }
                    }
            }
        }
        do {
            x = random.nextInt(3);
            y = random.nextInt(3);
        }
        while (isCellNotValid(x, y));
        table[y][x] = AISign;
    }

    /**
     * проверка на выигрыш одной из сторон
     *
     * @param sign крестик или нолик
     * @return true, если сторона выиграла, иначе false
     */
    public boolean checkIfWin(char sign) {
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
     *
     * @return true, если поле заполнено, иначе false
     */
    public boolean isTableFull() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (table[i][j] == emptyCage)
                    return false;
        return true;
    }
}
