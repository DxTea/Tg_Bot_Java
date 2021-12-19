package Games;

import bot.Channel;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static Menu.ConsoleBotController.*;
import static Messeges.OutputMessages.*;
import static java.lang.System.*;

/**
 * класс реализации игры "Крестики - Нолики"
 */
public class TicTacToe implements Game {
    public Channel channel;
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
    private final char[][] table = new char[size][size];
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
        //out.println(USER_TICTACTOE_CHOOSE_CHAR_LINE.getOutput());
        printToUser(USER_TICTACTOE_CHOOSE_CHAR_LINE.getOutput());
        while (true) {
            char symbol = channel.sendToGame().charAt(0); //scanner.next().charAt(0); //
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
            //out.println(USER_TICTACTOE_TRY_AGAIN_LINE.getOutput());
            printToUser(USER_TICTACTOE_TRY_AGAIN_LINE.getOutput());
            channel.sendToUser(USER_TICTACTOE_TRY_AGAIN_LINE.getOutput());

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
                //out.println(LOOSE.getOutput());
                printToUser((LOOSE.getOutput()));
                printTable();
                askPlayerAgain();
                break;
            }
            if (isTableFull()) {
                //out.println(DRAW.getOutput());
                printToUser(DRAW.getOutput());
                printTable();
                askPlayerAgain();
                break;
            }
            userTurn();
            if (checkIfWin(userSign)) {
                //out.println(WIN.getOutput());
                printToUser(WIN.getOutput());
                printTable();
                askPlayerAgain();
                break;
            }
            if (isTableFull()) {
                //out.println(DRAW.getOutput());
                printToUser(DRAW.getOutput());
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
        printToUser(SET_TABLE.getOutput());

        String n = getInput(); //scanner.next();
        while (!isInteger(n)) {
            //out.println(WRONG_SIZE.getOutput());
            printToUser(WRONG_SIZE.getOutput());
            //n = scanner.next();
            n = getInput();
        }

        //out.println(WIN_SIZE.getOutput());
        printToUser(WIN_SIZE.getOutput());
        String amount = getInput(); //scanner.next();
        while (!isInteger(amount)) {
            //out.println(WRONG_SIZE.getOutput());
            printToUser(WRONG_SIZE.getOutput());
            //amount = scanner.next();
            amount = getInput();
        }

        size = Integer.parseInt(n);
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
            String line = "";
            for (int j = 0; j < size; j++){
                line += " " + table[i][j];
                //out.print(" " + table[i][j]);
            }
            //out.println();
            printToUser(line);
        }
    }

    /**
     * ход игрока
     */
    public void userTurn() {
        int x, y;
        //out.println(USER_TICTACTOE_HELP_LINE.getOutput());
        printToUser(USER_TICTACTOE_HELP_LINE.getOutput());
        x = Integer.parseInt(getInput())-1;//scanner.nextInt() - 1;
        y = Integer.parseInt(getInput())-1;//scanner.nextInt() - 1;
        while (isCellNotValid(x, y)) {
            //out.println(USER_TICTACTOE_TRY_AGAIN_LINE.getOutput());
            printToUser(USER_TICTACTOE_TRY_AGAIN_LINE.getOutput());
            x = Integer.parseInt(getInput())-1;//scanner.nextInt() - 1;
            y = Integer.parseInt(getInput())-1;//scanner.nextInt() - 1;
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
     * @param sign крестик или нолик
     * @return true, если сторона выиграла, иначе false
     */
    public boolean checkIfWin(char sign) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                if (table[i][j] == userSign) {
                    for (int n = -1; n <= 1; n++)
                        for (int m = -1; m <= 1; m++) {
                            if (!isCellNotValid(j + m, i + n)) {
                                table[i + n][j + m] = AISign;
                                return true;
                            }
                        }
                }
        }

        for (int i = 0; i < 3; i++) {
            if ((table[i][0] == sign && table[i][1] == sign && table[i][2] == sign) ||
                    (table[0][i] == sign && table[1][i] == sign && table[2][i] == sign))
                return true;
        }
        return ((table[0][0] == sign && table[1][1] == sign && table[2][2] == sign) ||
                (table[2][0] == sign && table[1][1] == sign && table[0][2] == sign));
    }

    boolean checkWin(char symbol) {
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
    boolean checkDiagonal(char symb, int offsetX, int offsetY) {
        boolean toright, toleft;
        toright = true;
        toleft = true;
        for (int i=0; i<winNumber; i++) {
            toright &= (table[i+offsetX][i+offsetY] == symb);
            toleft &= (table[winNumber-i-1+offsetX][i+offsetY] == symb);
        }

        if (toright || toleft) return true;

        return false;
    }

    /**
     * Проверяем вертикаль и горизонталь
     */
    boolean checkLanes(char symb, int offsetX, int offsetY) {
        boolean cols, rows;
        for (int col=offsetX; col<winNumber+offsetX; col++) {
            cols = true;
            rows = true;
            for (int row=offsetY; row<winNumber+offsetY; row++) {
                cols &= (table[col][row] == symb);
                rows &= (table[row][col] == symb);
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

    private String getInput() {
        //scanner.next();
        return channel.sendToGame();
    }

    private void printToUser(String output) {
        out.println(output);
        channel.sendToUser(output);
    }
}
