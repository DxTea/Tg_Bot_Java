package Games.SeaBattle;

import java.io.*;
import java.util.Properties;

public class Battle {
    private static final String NEWLINE = System.lineSeparator();
    private static char space = ' ';
    private static char ship = '#';
    private static char fail = '•';
    private static char hit = 'X';
    private static int sizeOfField = 10;
    private static int maxCountOfBattleShip = 1;
    private static int maxCountOfCruiser = 2;
    private static int maxCountOfDestroyer = 3;
    private static int maxCountOfSubmarine = 4;

    private static final char[][] computerField = new char[sizeOfField][sizeOfField];
    private static final char[][] playerField = new char[sizeOfField][sizeOfField];

    public static char getShip() {
        return ship;
    }

    public static char getFail() {
        return fail;
    }

    public static int getSizeOfField() {
        return sizeOfField;
    }

    public static char[][] getComputerField() {
        return computerField;
    }

    public static char[][] getPlayerField() {
        return playerField;
    }

    public static void main(String[] args){

        loadProperties();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String answer;
            while (true) {
                // clear all data in ship class (actually for for repeated plays)
                AIShips.clearAll();
                PlayerShips.clearAll();
                System.out.print("Do you want to place your ships manually? [Y/n] ");
                answer = reader.readLine();
                if ("y".equals(answer.toLowerCase()) || "".equals(answer)) {
                    //manually placing players ships on the field
                    manualPlacingPlayersShips(reader);
                } else
                    //generate players ships on the field
                    generatePlayersShips();
                //generate computers ships on the field
                generateComputersField();

                // Turn-Based game
                while (PlayerShips.countAliveShip != 0 && AIShips.countAliveShip != 0) {
                    playerFireAndCheck(reader);
                    if (AIShips.countAliveShip != 0) {
                        computerFireAndCheck();
                    }
                }
                // If game is over
                System.out.print("Do you want to play again? Y/n: ");
                answer = reader.readLine();
                if (!("y".equals(answer.toLowerCase()) || "".equals(answer))) {
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void manualPlacingPlayersShips(BufferedReader reader) throws IOException {
        //clear the field, fill with space character
        for (int i = 0; i < sizeOfField; i++) {
            for (int j = 0; j < sizeOfField; j++) {
                playerField[i][j] = 32;
            }
        }
        printPlayersField();
        manuallyCreatePlayersShip(reader, 4, maxCountOfBattleShip);             //create battleship
        manuallyCreatePlayersShip(reader, 3, maxCountOfCruiser);                //create Cruisers
        manuallyCreatePlayersShip(reader, 2, maxCountOfDestroyer);              //create Destroyers
        manuallyCreatePlayersShip(reader, 1, maxCountOfSubmarine);              //create Submarines
    }

    private static void generateComputersField() {
        //clear the field, fill with space character
        for (int i = 0; i < sizeOfField; i++) {
            for (int j = 0; j < sizeOfField; j++) {
                computerField[i][j] = 32;
            }
        }
        createComputersShip(4, maxCountOfBattleShip);                            //generate battleship
        createComputersShip(3, maxCountOfCruiser);                               //generate Cruisers
        createComputersShip(2, maxCountOfDestroyer);                             //generate Destroyers
        createComputersShip(1, maxCountOfSubmarine);                             //generate Submarines
    }

    private static void generatePlayersShips() {
        //clear the field, fill with space character
        for (int i = 0; i < sizeOfField; i++) {
            for (int j = 0; j < sizeOfField; j++) {
                playerField[i][j] = 32;
            }
        }
        createPlayersShip(4, maxCountOfBattleShip);                            //generate battleship
        createPlayersShip(3, maxCountOfCruiser);                               //generate Cruisers
        createPlayersShip(2, maxCountOfDestroyer);                             //generate Destroyers
        createPlayersShip(1, maxCountOfSubmarine);                             //generate Submarines
    }

    private static void manuallyCreatePlayersShip(BufferedReader reader, int countOfDecker, int maxCountOfShip) throws IOException {
        //count of created ships
        int countOfShip = countOfDecker == 1 ? PlayerShips.countOfSubmarine : countOfDecker == 2 ?
                PlayerShips.countOfDestroyer : countOfDecker == 3 ? PlayerShips.countOfCruiser : PlayerShips.countOfBattleShip;

        while (countOfShip < maxCountOfShip) {
            System.out.println(String.format("Create and place your %s!", countOfDecker == 1 ? "submarine" : countOfDecker == 2 ?
                    "destroyer" : countOfDecker == 3 ? "cruiser" : "battleship"));

            if (countOfDecker != 1) {
                System.out.println("Write separate by spaces: coordinates, direction from this coordinates (n for north, e - east, s - south, w - west)" + NEWLINE + "Example: d2 e");
            } else {
                System.out.println("Write: coordinates" + NEWLINE + "Example: d2");
            }

            String parameters = reader.readLine();
            // exit for exit from game.
            if ("exit".equals(parameters.toLowerCase())) {
                System.exit(0);
            }

            String[] param =  parameters.split("[ ]+");
            String coordinates = param[0];
            int number;
            int letter;
            try {
                letter = Character.toLowerCase(coordinates.charAt(0)) - 97;                      // a=0, b=1, c=2, d=3, e=4,f=5, g=6, h=7, i=8, j=9 .....
                number = Integer.parseInt(coordinates.substring(1, coordinates.length())) - 1;   // 1 => 0
            } catch (NumberFormatException e) {
                System.out.println("You need to use space to separate coordinates and direction! Try again");
                manuallyCreatePlayersShip(reader, countOfDecker, maxCountOfShip);
                break;
            }
            // Check coordinates, that they valid
            if ((number < 0 || number > sizeOfField - 1 ) || (letter < 0 || letter > sizeOfField - 1)){
                System.out.println("Coordinates might be from a to j and from 1 to 10! Try again");
                manuallyCreatePlayersShip(reader, countOfDecker, maxCountOfShip);
                break;
            }
            //If creating ship is battleship, or Cruise, or Destroyer
            if (countOfDecker > 1) {
                //If we have directions parameter
                if (param.length > 1) {
                    char direction = param[1].charAt(0);
                    // Check the direction
                    if (direction != 'n' && direction != 'e' && direction != 's' && direction != 'w'){
                        System.out.println("direction is wrong might be n for north, e - east, s - south, w - west");
                        manuallyCreatePlayersShip(reader, countOfDecker, maxCountOfShip);
                        break;
                    }
                    // create ship and set parameters
                    PlayerShips playersShip = new PlayerShips();
                    playersShip.setShip(direction, countOfDecker, number, letter);
                    printPlayersField();
                    countOfShip = countOfDecker == 2 ? PlayerShips.countOfDestroyer : countOfDecker == 3 ? PlayerShips.countOfCruiser : PlayerShips.countOfBattleShip;
                } else {
                    System.out.println("You have forgot write a direction! Try again");
                    manuallyCreatePlayersShip(reader, countOfDecker, maxCountOfShip);
                    break;
                }
                // If creating ship is Submarine
            } else {
                // create submarine and set parameters
                PlayerShips playersShip = new PlayerShips();
                playersShip.setShip(number, letter);
                printPlayersField();
                countOfShip = PlayerShips.countOfSubmarine;
            }
        }
    }

    private static void createPlayersShip(int countOfDecker, int maxCountOfShip) {
        //generate players ships on the field
        int countOfShip = countOfDecker == 1 ? PlayerShips.countOfSubmarine : countOfDecker == 2 ?
                PlayerShips.countOfDestroyer : countOfDecker == 3 ? PlayerShips.countOfCruiser : PlayerShips.countOfBattleShip;

        while (countOfShip < maxCountOfShip) {
            // generate coordinates
            int randomNumber = (int) (Math.random() * sizeOfField);                                    // a=0, b=1, c=2, d=3, e=4,f=5, g=6, h=7, i=8, j=9 .....
            int randomLetter = (int) (Math.random() * sizeOfField);
            // generate coordinates
            if (countOfDecker > 1) {
                int randomDirection = (int) (Math.random()*4);
                char direction = randomDirection == 0 ? 'n': randomDirection == 1 ? 'e': randomDirection == 2 ? 's': 'w';
                // create ship and set parameters
                PlayerShips ship = new PlayerShips();
                ship.setShip(direction, countOfDecker, randomNumber, randomLetter);
                countOfShip = countOfDecker == 2 ? PlayerShips.countOfDestroyer : countOfDecker == 3 ?
                        PlayerShips.countOfCruiser : PlayerShips.countOfBattleShip;

            } else {
                // create submarine and set parameters
                PlayerShips ship = new PlayerShips();
                ship.setShip(randomNumber, randomLetter);
                countOfShip = PlayerShips.countOfSubmarine;
            }
        }
    }

    private static void createComputersShip(int countOfDecker, int maxCountOfShip){
        //generate computers ships on the field
        int countOfShip = countOfDecker == 1 ? AIShips.countOfSubmarine : countOfDecker == 2 ?
                AIShips.countOfDestroyer : countOfDecker == 3 ? AIShips.countOfCruiser : AIShips.countOfBattleShip;

        while (countOfShip < maxCountOfShip) {
            // generate coordinates
            int randomNumber = (int) (Math.random() * sizeOfField);                                    // a=0, b=1, c=2, d=3, e=4,f=5, g=6, h=7, i=8, j=9 .....
            int randomLetter = (int) (Math.random() * sizeOfField);
            // generate coordinates
            if (countOfDecker > 1) {
                int randomDirection = (int) (Math.random()*4);
                char direction = randomDirection == 0 ? 'n': randomDirection == 1 ? 'e': randomDirection == 2 ? 's': 'w';
                // create ship and set parameters
                AIShips ship = new AIShips();
                ship.setShip(direction, countOfDecker, randomNumber, randomLetter);
                countOfShip = countOfDecker == 2 ? AIShips.countOfDestroyer : countOfDecker == 3 ?
                        AIShips.countOfCruiser : AIShips.countOfBattleShip;

            } else {
                // create submarine and set parameters
                AIShips ship = new AIShips();
                ship.setShip(randomNumber, randomLetter);
                countOfShip = AIShips.countOfSubmarine;
            }
        }
    }

    private static void computerFireAndCheck() throws InterruptedException {

        int fireNumber = (int) (Math.random() * sizeOfField);
        int fireLetter= (int) (Math.random() * sizeOfField);

        if (playerField[fireLetter][fireNumber] == space) {
            playerField[fireLetter][fireNumber] = fail;
            printTheField();
            System.out.println("Computer hasn't hit you on " + (char)(fireNumber + 97) + "" + (fireLetter + 1));
        } else if (playerField[fireLetter][fireNumber] == ship) {
            playerField[fireLetter][fireNumber] = hit;
            printTheField();
            System.out.println("Computer has shot and hit you on " + (char)(fireNumber + 97) + "" + (fireLetter + 1) + "!");
/***/            Thread.sleep(2000);
            for(PlayerShips playersShip: PlayerShips.shipsList) {
                for (int j = 0; j < playersShip.coordinates.length; j++ ) {
                    int shipNumber = playersShip.coordinates[j].number;
                    int shipLetter = playersShip.coordinates[j].letter;
                    if (fireNumber == shipNumber && fireLetter == shipLetter) {
                        playersShip.countAliveDecker--;
                        if (playersShip.countAliveDecker == 0){
                            int ignore = playersShip.countOfDecker==1? PlayerShips.countOfSubmarine-- : playersShip.countOfDecker==2?
                                    PlayerShips.countOfDestroyer-- : playersShip.countOfDecker==3?
                                    PlayerShips.countOfCruiser-- : PlayerShips.countOfBattleShip--;

                            playersShip.areaAroundTheShip();
                            printTheField();
                            System.out.println("Computer has sank your ship!");
                            PlayerShips.countAliveShip--;

                            if(PlayerShips.countAliveShip == 0) {
                                System.out.println("You are sucker! Computer win!");
                                return;
                            }
                        }
                    }
                }
            }
            computerFireAndCheck();

        } else if (playerField[fireLetter][fireNumber] == hit
                || playerField[fireLetter][fireNumber] == fail) {
            computerFireAndCheck();
        }
    }


    private static void playerFireAndCheck(BufferedReader reader) throws IOException
    {
        printTheField();
        int fireNumber;
        int fireLetter;
        while (true) {
            System.out.print("Fire: ");
            String fire = reader.readLine();

            // exit for exit from game.
            if ("exit".equals(fire.toLowerCase())) {
                System.exit(0);
            }
            if (fire.length()>1) {
                fireLetter = Character.toLowerCase(fire.charAt(0)) - 97;
                fireNumber = Integer.parseInt(fire.substring(1, fire.length())) - 1;
                if ((fireNumber > -1 && fireNumber < sizeOfField )
                        && (fireLetter > -1 && fireLetter < sizeOfField)) {
                    break;
                } else System.out.println("Coordinates are wrong! Please fire again");
            } else System.out.println("Coordinates are wrong! Please fire again");
        }

        if (computerField[fireLetter][fireNumber] == space) {
            computerField[fireLetter][fireNumber] = fail;
            printTheField();

        } else if (computerField[fireLetter][fireNumber] == ship) {
            computerField[fireLetter][fireNumber] = hit;
            System.out.println("You have hit him!");
            for(AIShips computer: AIShips.shipsList) {
                for (int j = 0; j < computer.coordinates.length; j++ ) {
                    int shipNumber = computer.coordinates[j].number;
                    int shipLetter = computer.coordinates[j].letter;
                    if (fireNumber == shipNumber && fireLetter == shipLetter) {
                        computer.countAliveDecker--;
                        if (computer.countAliveDecker == 0){
                            int ignore = computer.countOfDecker==1? AIShips.countOfSubmarine-- : computer.countOfDecker==2?
                                    AIShips.countOfDestroyer-- : computer.countOfDecker==3?
                                    AIShips.countOfCruiser-- : AIShips.countOfBattleShip--;

                            computer.areaAroundTheShip();
                            System.out.println("You have sank him!");
                            AIShips.countAliveShip--;

                            if(AIShips.countAliveShip == 0) {
                                System.out.println("Congratulation! You win!");
                                printTheField();
                                return;
                            }
                        }
                    }
                }
            }
            System.out.println("Fire again!");
            playerFireAndCheck(reader);

        } else if (computerField[fireLetter][fireNumber] == hit
                || computerField[fireLetter][fireNumber] == fail) {
            System.out.println("You already fired here! Fire again!");
            playerFireAndCheck(reader);
        }
    }

    private static void loadProperties(){
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
            space = properties.getProperty("space").charAt(0);
            ship = properties.getProperty("ship").charAt(0);
            fail = properties.getProperty("fail").charAt(0);
            hit = properties.getProperty("hit").charAt(0);
            sizeOfField = Integer.parseInt(properties.getProperty("sizeOfField"));
            maxCountOfBattleShip = Integer.parseInt(properties.getProperty("maxCountOfBattleShip"));
            maxCountOfCruiser = Integer.parseInt(properties.getProperty("maxCountOfCruiser"));
            maxCountOfDestroyer = Integer.parseInt(properties.getProperty("maxCountOfDestroyer"));
            maxCountOfSubmarine = Integer.parseInt(properties.getProperty("maxCountOfSubmarine"));
        } catch (IOException e) {
            System.out.println("Can't find config file, using default");
        }
    }

    private static void printPlayersField()
    {
        System.out.println("   1  2  3  4  5  6  7  8  9  10");
        for (int i = 0; i < sizeOfField; i++)
        {
            System.out.print(String.format("%2.2s", (char)(i + 65)));
            for (int j = 0; j < sizeOfField; j++)
            {
                System.out.print("[" + playerField[i][j] + "]");
            }
            System.out.println();
        }
    }

    private static void printTheField()
    {
        System.out.println("              Player                                             Computer");
        System.out.println("    1  2  3  4  5  6  7  8  9  10                      1  2  3  4  5  6  7  8  9  10");
        for (int i = 0; i < sizeOfField; i++)
        {
            System.out.print(String.format("%2.2s ", (char)(i + 65)));
            for (int j = 0; j < sizeOfField; j++)
            {
                char cross = playerField[i][j];
                if (cross == Battle.hit) {
                    System.out.print('[' + 'X' + ']');
                } else if (cross == Battle.ship) {
                    System.out.print('[' + '#' + ']');
                } else {
                    System.out.print('[' + '.' + ']');
                }
            }

            String ship = i==0? "Battleship: " : i==1? "Cruiser: " : i==2? "Destroyer: " : i==3? "Submarine: " : " ";
            String countOfPlayersShips = String.valueOf(i==0? PlayerShips.countOfBattleShip : i==1? PlayerShips.countOfCruiser : i==2?
                    PlayerShips.countOfDestroyer : i==3? PlayerShips.countOfSubmarine : "");
            String playersShips = String.format("  %-12s%1s   ", ship, countOfPlayersShips);
            System.out.print(playersShips);
            System.out.print(String.format("%2.2s ", (char)(i + 65)));
            for (int j = 0; j < sizeOfField; j++)
            {
                char cross = computerField[i][j] == Battle.ship ? ' ': computerField[i][j];
                if (cross == Battle.hit) {
                    System.out.print('[' + 'X' + ']');
                } else {
                    System.out.print('[' + '.' + ']');
                }
            }
            String countOfComputersShips = String.valueOf(i==0? AIShips.countOfBattleShip : i==1? AIShips.countOfCruiser : i==2?
                    AIShips.countOfDestroyer : i==3? AIShips.countOfSubmarine : "");
            String computersShips = String.format("  %-12s%1s", ship, countOfComputersShips);
            System.out.println(computersShips);
        }
    }
}
