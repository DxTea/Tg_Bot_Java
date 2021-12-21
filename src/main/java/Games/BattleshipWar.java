package Games;

import java.util.Scanner;

import static Menu.ConsoleBotController.*;
import static Messeges.OutputMessages.*;
import static java.lang.System.in;
import static java.lang.System.out;

public class BattleshipWar implements Game{
    private static Scanner scanner = new Scanner(in);
    /**
     * размер игрового поля
     */
    static final int FIELD_LENGTH = 10;
    /**
     * имя 1 игрока
     */
    String player1Name;
    /**
     * имя второго игрока
     */
    String player2Name;
    /**
     * карта игрока 1
     */
    char[][] playerField1 = new char[FIELD_LENGTH][FIELD_LENGTH];
    /**
     * карта игрока 2
     */
    char[][] playerField2 = new char[FIELD_LENGTH][FIELD_LENGTH];
    /**
     * карта выстрелов игрока 1
     */
    char[][] playerBattleField1 = new char[FIELD_LENGTH][FIELD_LENGTH];
    /**
     * карта выстрелов игрока 1
     */
    char[][] playerBattleField2 = new char[FIELD_LENGTH][FIELD_LENGTH];
    /**
     * символ пустой клетки
     */
    final static char emptyCage = '.';
    /**
     * символ палубы корабля
     */
    final static char shipSymbol = '■';

    /**
     * запуск игры
     */
    public static void start() {
        BattleshipWar currentGame = new BattleshipWar();
        currentGame.play();
    }

    /**
     * инициализация имен игроков
     */
    private void initializeNames() {
        out.println(NAME1.getOutput());
        player1Name = scanner.nextLine();

        out.println(NAME2.getOutput());
        player2Name = scanner.nextLine();
    }

    /**
     * расстановка кораблей
     * @param playerField игровое поле
     */
    private static void fillPlayerField(char[][] playerField) {
        int x = 0;
        int y = 0;
        int position = 0;
        for (int i = 4; i >= 1; i--) {
            for (int k = 1; k <= 5 - i; k++) {
                out.println("Расставляем " + i + "-палубный корабль. Осталось расставить: " + (5-i-k+1));

                int validationResult = 1;
                while (validationResult != 0) {
                    out.println(COOR_X.getOutput());
                    x = scanner.nextInt();

                    out.println(COOR_Y.getOutput());
                    y = scanner.nextInt();

                    out.println(WHAT_POSITION.getOutput());
                    position = scanner.nextInt();
                    validationResult = validateCoordForShip(playerField, x, y, position, i);
                }

                if (position == 1) {
                    for (int q = 0; q < i; q++) {
                        playerField[y-1][x-1 + q] = shipSymbol;
                    }
                }

                if (position == 2) {
                    for (int m = 0; m < i; m++) {
                        playerField[y-1 + m][x-1] = shipSymbol;
                    }
                }
                printField(playerField);
            }
        }
    }

    /**
     * вывод игрового поля
     */
    static void printField(char[][] field) {
        for (char[] cells : field) {
            for (char cell : cells) {
                if (cell == 0) {
                    out.print(emptyCage + "  ");
                } else {
                    out.print(cell + "  ");
                }
            }
            out.println();
        }
    }

    /**
     * основная логика игры
     */
    @Override
    public void play() {
        initializeNames();
        fillPlayerField(playerField1);
        fillPlayerField(playerField2);
        String currentPlayerName = player1Name;
        char[][] currentPlayerField = playerField2;
        char[][] currentPlayerBattleField = playerBattleField1;

        while (isPlayerAlive(playerField1) && isPlayerAlive(playerField2)) {
            out.println(currentPlayerName + SHOT_X.getOutput());
            int xShot = scanner.nextInt();
            out.println(currentPlayerName + SHOT_Y.getOutput());
            int yShot = scanner.nextInt();

            int shotResult = handleShot(currentPlayerBattleField, currentPlayerField, xShot, yShot);

            if (shotResult == 0) {
                currentPlayerName = player2Name;
                currentPlayerField = playerField1;
                currentPlayerBattleField = playerBattleField2;
            }
        }
        out.println(currentPlayerName + " выиграл!");
        askPlayerAgain();
    }

    /**
     * обработка выстрела
     */
    private static int handleShot(char[][] battleField, char[][] field, int x, int y) {
        if (field[y][x] == shipSymbol) {
            field[y][x] = '#';
            battleField[y][x] = '#';
            out.println(RIGHT_SHOT.getOutput());
            return 1;
        }
        battleField[y][x] = '*';
        out.println(BAD_SHOT.getOutput());
        return 0;
    }

    /**
     * проверка жив ли игрок
     */
    private static boolean isPlayerAlive(char[][] field) {
        for (char[] cells : field) {
            for (char cell : cells) {
                if (shipSymbol == cell) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * проверка на допустимые координаты и позицию корабля
     * @return 0, если все хорошо, в ином случае -1
     */
    private static int validateCoordForShip(char[][] field, int x, int y, int position, int shipType) {
        if (position == 1) {
            for (int i = 0; i < shipType - 1; i++) {
                try {
                if (shipSymbol == field[y][x + i]
                        || shipSymbol == field[y - 1][x + i]
                        || shipSymbol == field[y + 1][x + i]
                        || shipSymbol == field[y][x + i + 1]
                        || shipSymbol == field[y][x + i - 1]
                        || (x + i) > 9) {
                    return -1;
                } }
                catch (ArrayIndexOutOfBoundsException e) { return -1; }
            }
        } else if (position == 2) {
            for (int i = 0; i < shipType - 1; i++) {
                try {
                if (shipSymbol == field[y][x + i]
                        || shipSymbol == field[y - 1][x + i]
                        || shipSymbol == field[y + 1][x + i]
                        || shipSymbol == field[y][x + i + 1]
                        || shipSymbol == field[y][x + i - 1]
                        || (y + i) > 9) {
                    return -1;
                } }
                catch (ArrayIndexOutOfBoundsException e) { return -1; }
            }
        }
        return 0;
    }
}
