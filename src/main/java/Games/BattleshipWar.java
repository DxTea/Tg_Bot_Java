package Games;

import java.util.Random;
import java.util.Scanner;


import static Messeges.OutputMessages.*;
import static java.lang.System.in;
import static java.lang.System.out;

public class BattleshipWar implements Game{
    private static Scanner scanner = new Scanner(in);
    private static Random random = new Random();
    /**
     * размер игрового поля
     */
    static final int FIELD_LENGTH = 12;
    /**
     * имя 1 игрока
     */
    private String playerName = "игрок";
    /**
     * имя компьютер
     */
    private String AIName = "компьютер";
    /**
     * карта игрока 1
     */
    private char[][] playerField = new char[FIELD_LENGTH][FIELD_LENGTH];
    /**
     * карта игрока 2
     */
    private char[][] AIField = new char[FIELD_LENGTH][FIELD_LENGTH];
    /**
     * карта выстрелов игрока 1
     */
    private char[][] playerBattleField = new char[FIELD_LENGTH][FIELD_LENGTH];
    /**
     * карта выстрелов игрока 1
     */
    private char[][] AIBattleField = new char[FIELD_LENGTH][FIELD_LENGTH];
    /**
     * символ пустой клетки
     */
    final static char emptyCage = '.';
    /**
     * символ палубы корабля
     */
    final static char shipSymbol = '■';
    /**
     * символ, если игрок попал
     */
    public static final char hitSymbol = '#';
    /**
     * символ, если мимо
     */
    public static final char pastSymbol = '*';

    /**
     * запуск игры
     */
    public static void start() {
        BattleshipWar currentGame = new BattleshipWar();
        currentGame.play();
    }

//    /**
//     * инициализация имен игроков
//     */
//    private void initializeNames() {
//        out.println(NAME1.getOutput());
//        player1Name = scanner.nextLine();
//
//        out.println(NAME2.getOutput());
//        player2Name = scanner.nextLine();
//    }

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

                    if (i != 1) {
                        out.println(WHAT_POSITION.getOutput());
                        position = scanner.nextInt();
                    }
                    else {
                        position = 1;
                    }
                    validationResult = validateCoordForShip(playerField, x, y, position, i);
                }

                if (position == 1) {
                    for (int q = 0; q < i; q++) {
                        playerField[y][x + q] = shipSymbol;
                    }
                }

                if (position == 2) {
                    for (int m = 0; m < i; m++) {
                        playerField[y + m][x] = shipSymbol;
                    }
                }
                char [][] emptyBattleField = new char[12][2];
                printField(playerField, emptyBattleField);
            }
        }
    }

    /**
     * расстановка кораблей компьютера
     * @param AIField игровое поле
     */
    private static void fillAIField(char[][] AIField) {
        int x = 0;
        int y = 0;
        int position = 0;
        for (int i = 4; i >= 1; i--) {
            for (int k = 1; k <= 5 - i; k++) {
                int validationResult = 1;
                while (validationResult != 0) {
                    x = random.nextInt(1, 11);
                    y = random.nextInt(1, 11);

                    if (i != 1) {
                        position = random.nextInt(1, 3);
                    }
                    else {
                        position = 1;
                    }
                    validationResult = validateCoordForShip(AIField, x, y, position, i);
                }

                if (position == 1) {
                    for (int q = 0; q < i; q++) {
                        AIField[y][x + q] = shipSymbol;
                    }
                }

                if (position == 2) {
                    for (int m = 0; m < i; m++) {
                        AIField[y + m][x] = shipSymbol;
                    }
                }
                char [][] emptyBattleField = new char[12][2];
            }
        }
    }

    /**
     * вывод игрового поля
     */
    static void printField(char[][] field, char[][] battleField) {
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                char cell = field[i][j];
                if (cell == 0) out.print(emptyCage + "  ");
                else out.print(cell + "  ");
            }
            out.print("            ");
            for (int k = 1; k <= battleField[i].length-2; k++) {
                char cell = battleField[i][k];
                if (cell == 0) out.print(emptyCage + "  ");
                else out.print(cell + "  ");
            }
            out.println();
        }
    }

    public static void main(String[] args){
        start();
    }

    /**
     * основная логика игры
     */
    @Override
    public void play() {
        fillPlayerField(playerField);
        out.println("Вы заполнили игровое поле.");
        fillAIField(AIField);

        String currentPlayerName = playerName;
        char[][] currentPlayerField = AIField;
        char[][] currentPlayerBattleField = playerBattleField;

        while (isPlayerAlive(playerField) && isPlayerAlive(AIField)) {
            out.println(currentPlayerName + SHOT_X.getOutput());
            int xShot = scanner.nextInt();
            out.println(currentPlayerName + SHOT_Y.getOutput());
            int yShot = scanner.nextInt();

            int shotResult = handleShot(currentPlayerBattleField, currentPlayerField, xShot, yShot);
            printField(playerBattleField, playerField);

            if (shotResult == 0) {
                out.println("Ход компьютера!");
                AITurn();
                out.println("Компьютер закончил!");
            }
        }
        out.println(currentPlayerName + " выиграл!");
    }

    private void AITurn() {
        int shotResult = -1;
        while (shotResult != 0) {
            int xShot = random.nextInt(1, 11);
            int yShot = random.nextInt(1, 11);
            shotResult = handleShot(AIBattleField, playerField, xShot, yShot);
        }
    }

    /**
     * обработка выстрела
     */
    private static int handleShot(char[][] battleField, char[][] field, int x, int y) {
        if (field[y][x] == shipSymbol) {
            field[y][x] = hitSymbol;
            battleField[y][x] = hitSymbol;
            out.println(RIGHT_SHOT.getOutput());
            return 1;
        }
        battleField[y][x] = pastSymbol;
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
            for (int i = -1; i < shipType + 1; i++) {
                try {
//                if (shipSymbol == field[y][x + i]
//                        || shipSymbol == field[y - 1][x + i]
//                        || shipSymbol == field[y + 1][x + i]
//                        || shipSymbol == field[y][x + i + 1]
//                        || shipSymbol == field[y][x + i - 1]
//                        || (x + i) > 9) {
                    if (field[y][x+i] == shipSymbol
                            || field[y-1][x+i] == shipSymbol
                            || field[y+1][x+i] == shipSymbol)
                        return -1;
                }
                catch (ArrayIndexOutOfBoundsException e) { return -1; }
            }
        } else if (position == 2) {
            for (int i = -1; i < shipType + 1; i++) {
                try {
//                if (shipSymbol == field[y][x + i]
//                        || shipSymbol == field[y - 1][x + i]
//                        || shipSymbol == field[y + 1][x + i]
//                        || shipSymbol == field[y][x + i + 1]
//                        || shipSymbol == field[y][x + i - 1]
//                        || (y + i) > 9) {
                    if (field[y+i][x] == shipSymbol
                            || field[y+i][x-1] == shipSymbol
                            || field[y+i][x+1] == shipSymbol)
                        return -1;
                }
                catch (ArrayIndexOutOfBoundsException e) { return -1; }
            }
        }
        return 0;
    }
}
