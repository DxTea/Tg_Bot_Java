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
    private int size = 3;
    /**
     * количество символов для выигрыша
     */
    private int winNumber = 3;
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
        out.println(SET_TABLE.getOutput());
        String n = scanner.next();
        while (!isInteger(n)) {
            out.println(WRONG_SIZE.getOutput());
            n = scanner.next();
        }

        out.println(WIN_SIZE.getOutput());
        String amount = scanner.next();
        while (!isInteger(amount)) {
            out.println(WRONG_SIZE.getOutput());
            amount = scanner.next();
        }

        size = Integer.parseInt(n);
        table = new char[size][size];
        while (!isInteger(amount) || Integer.parseInt(amount) > size) {
            out.println(WRONG_SIZE.getOutput());
            amount = scanner.next();
        }
        winNumber = Integer.parseInt(amount);

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                table[i][j] = emptyCage;
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * показ текущего состояние стола
     */
    private void printTable() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
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
        if (x < 0 || y < 0 || x >= size || y >= size)
            return true;
        return table[y][x] != emptyCage;
    }

    /**
     * ход ИИ
     */
    public void AITurn() {
        int x, y;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
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
            x = random.nextInt(size);
            y = random.nextInt(size);
        }
        while (isCellNotValid(x, y));
        table[y][x] = AISign;
    }

    /**
     * проверка на выигрыш одной из сторон
     *
     * @param symbol крестик или нолик
     * @return true, если сторона выиграла, иначе false
     */
    public boolean checkIfWin(char symbol) {
        for (int i = 0; i < size-winNumber+1; i++) {
            for (int j = 0; j < size-winNumber+1; j++) {
                if (checkDiagonal(symbol, i, j) || checkLanes(symbol, i, j)) return true;
            }
        }
        return false;
    }

    /**
     * Проверяем диагонали
     */
    boolean checkDiagonal(char symbol, int offsetX, int offsetY) {
        boolean toright, toleft;
        toright = true;
        toleft = true;
        for (int i = 0; i < winNumber; i++) {
            toright &= (table[i][i+offsetY] == symbol);
            toleft &= (table[winNumber-i-1+offsetX][i+offsetY] == symbol);
        }

        return (toright || toleft);
    }

    /**
     * Проверяем вертикаль и горизонталь
     */
    boolean checkLanes(char symbol, int offsetX, int offsetY) {
        boolean cols, rows;
        for (int col = offsetX; col < winNumber+offsetX; col++) {
            cols = true;
            rows = true;
            for (int row = offsetY; row < winNumber+offsetY; row++) {
                cols &= (table[col][row] == symbol);
                rows &= (table[row][col] == symbol);
            }

            if (cols || rows) return true;
        }
        return false;
    }

    /**
     * проверка на заполненное поле
     * нужна в случае ничьи
     *
     * @return true, если поле заполнено, иначе false
     */
    public boolean isTableFull() {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (table[i][j] == emptyCage)
                    return false;
        return true;
    }
}
