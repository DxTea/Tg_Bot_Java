package Games;


import java.util.Random;
import java.util.Scanner;

import static Menu.ConsoleBotController.*;
import static Messeges.OutputMessages.*;
import static java.lang.System.*;

/**
 * класс реализации игры "Крестики - Нолики"
 */
public class TicTacToe implements Game {
    private final Random random = new Random();
    private final Scanner scanner = new Scanner(in);
    /**
     * обозначение пустого места на поле
     */
    final char emptyCage = '.';
    /**
     * крестик
     */
    final char x = 'x';
    /**
     * нолик
     */
    final char zero = 'o';
    /**
     * размер поля
     */
    private int n = 3;
    /**
     * игровое поле
     */
    private final char[][] table = new char[n][n];
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
    private void chooseSign() {
        out.println(USER_TICTACTOE_CHOOSE_CHAR_LINE.getOutput());
        while (true) {
            char symbol = scanner.next().charAt(0);
            if (symbol == x) {
                userSign = x;
                AISign = zero;
                break;
            }
            if (symbol == zero) {
                userSign = zero;
                AISign = x;
                break;
            }
            out.println(USER_TICTACTOE_TRY_AGAIN_LINE.getOutput());
        }
    }

    /**
     * позволяет нам самим составлять table
     *
     * @param sign символ, который хотим поставить
     * @param x    столбец
     * @param y    строка
     */
    public void setTable(char sign, int x, int y) {
        table[y][x] = sign;
    }

    /**
     * запуск игры
     */
    public static void start() {
        TicTacToe currentGame = new TicTacToe();
        currentGame.play();
    }

    /**
     * реализация игровой логики
     */
    @Override
    public void play() {
        initializeTable();
        chooseSign();
        if (userSign == x) userTurn();
        while (true) {
            AITurn();
            printTable();
            if (checkIfWin(AISign)) {
                out.println(LOOSE.getOutput());
                printTable();
                askPlayerAgain();
                break;
            }
            if (isTableFull()) {
                out.println(DRAW.getOutput());
                printTable();
                askPlayerAgain();
                break;
            }
            userTurn();
            if (checkIfWin(userSign)) {
                out.println(WIN.getOutput());
                printTable();
                askPlayerAgain();
                break;
            }
            if (isTableFull()) {
                out.println(DRAW.getOutput());
                printTable();
                askPlayerAgain();
                break;
            }
        }
    }

    /**
     * создание игрового поля
     */
    public void initializeTable() {
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                table[i][j] = emptyCage;
    }

    /**
     * показ текущего состояние стола
     */
    private void printTable() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                out.print(" " + table[i][j]);
            out.println();
        }
    }

    /**
     * ход игрока
     */
    public void userTurn() {
        int x, y;
        out.println(USER_TICTACTOE_HELP_LINE.getOutput());
        x = scanner.nextInt() - 1;
        y = scanner.nextInt() - 1;
        while (isCellNotValid(x, y)) {
            out.println(USER_TICTACTOE_TRY_AGAIN_LINE.getOutput());
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
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                if (table[i][j] == userSign) {
                    for (int n = -1; n <= 1; n++)
                        for (int m = -1; m <= 1; m++) {
                            if (!isCellNotValid(j + m, i + n)) {
                                table[i + n][j + m] = AISign;
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
