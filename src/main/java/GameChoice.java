import Games.Hangman;
import Games.TicTacToe;

import java.util.Scanner;

/**
 * класс выбора игры, то есть самое начало
 */
public class GameChoice {
    /**
     * основной метод класса, который запускает два следующих
     * и который мы и вызываем в main
     */
    public void starting(){
        whichOne(choice());
    }

    /**
     * метод выводит первые фразы и получает строку от пользователя
     * @return строку от пользователя
     */
    private String choice() {
        System.out.println("Выберите игру: Hangman или TicTacToe");
        System.out.println("Введите одно из предложенных названий");
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }

    /**
     * метод для запуска выбранной игры
     * @param gameName название игры
     * @return строку, показывающую успешное или нет запуск конкретной игры
     */
    protected String whichOne(String gameName){
        String result;
        switch (gameName){
            case("Hangman"):
                result = "Success Hangman";
                Hangman.startingHangman();
                break;
            case ("TicTacToe"):
                result = "Success TicTacToe!";
                TicTacToe game = new TicTacToe();
                game.gameLogic();
                break;
            default:
                result = "Wrong name";
                break;
        }
        return result;
    }
}
