import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class BattleShips {
    public static void main(String[] args) {
        play();
    }

    /**
     * Creates the board at the specified size.
     * @param rows the number of rows.
     * @param cols the number of columns.
     * @return the created board.
     */
    public static String[][] createBoard(int rows, int cols) {
        String[][] board = new String[rows][(cols * 2) + 2];

        // Loops over for the size of the board with a nested loop.
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < (cols * 2) + 1; j++) {
                if (j == cols) {
                    board[i][j] = "|";
                }
                else {
                    board[i][j] = "O";
                }
            }
            board[i][(cols * 2) + 1] = "\n";
        }
        return board;
    }

    /**
     * Displays the current state of the game displaying the co-ordinates at the correct side.
     * @param board the game board current state.
     * @param left boolean of of the coordinate side.
     */
    public static void displayBoard(String[][] board, boolean left) {
        System.out.print(" ");
        // Loops over the board.
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (left && j == 0) {
                    System.out.print((i + 1) + "\t" + board[i][j] + " ");
                }
                else if (!left && j == board.length * 2 + 1) {
                    System.out.print(i + " " + board[i][j] + " ");
                }
                else {
                    System.out.print(board[i][j] + " ");
                }
            }
        }
        System.out.print("\t");
        // Loops over the length for the coordinates.
        for (int i = 0, j = 'A'; i < board.length * 2 + 5; i++) {
            if (left && i < board.length) {
              System.out.print( (char) j + " ");
              j++;
            }
            else if (!left && i >= board.length + 5) {
                System.out.print((char) j + " ");
                j++;
            }
            else if (!left) {
                System.out.print(" ");
            }
        }
        System.out.println("\n");
    }

    /**
     * From the players input, places the player ships.
     * @param board the current state of the board.
     * @param ships the number of ships in play.
     * @return the new state of the board.
     */
    public static String[][] placeShips(String[][] board, int ships) {
        displayBoard(board, true);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Place your ships! (Format:X-CORD [SPACE] Y-CORD");

        // Loops for the number of ships to be placed on the board.
        for (int i = 0; i < ships; i++) {
            System.out.print("Next Ship:");
            String coordString = scanner.nextLine();
            if (coordString == "EXIT") {
                System.exit(0);
            }
            String[] coords = coordString.split("\\s+");
            if (coords.length == 2) {
                try {
                    // Places the S at where the player specifies.
                    board[Integer.parseInt(coords[1]) - 1][(int) coords[0].charAt(0) - 'A'] = "S";
                }
                catch (Exception e) {
                    System.out.println("Wrong Formatting.");
                    i--;
                }
                
            }
            else {
                System.out.println("Wrong Formatting.");
                i--;
            }
        }
        return board;
    }

    /**
     * Places the enemy ships randomly.
     * @param rows the number of rows.
     * @param cols the number of columns.
     * @param ships the number of ships in play.
     * @return the array of enemy ship positions.
     */
    public static String[] placeEnemyShips(int rows, int cols, int ships) {
        String[] enemyShips = new String[ships];
        String xcoords = "";
        String ycoords = "";
        Random rand = new Random();
        // Loops forthe number of ships to be placed.
        for (int i = 0; i < ships; i++) {
            // Gets random coordinates.
            String currentX = String.valueOf((char) ('A' + rand.nextInt(cols)));
            String currentY = String.valueOf(rand.nextInt(rows));
            // Checks the new coordinates are unique for the enemy.
            if (!xcoords.contains(currentX) && !ycoords.contains(currentY)) {
                xcoords += currentX;
                ycoords += currentY;
                enemyShips[i] = currentX + " " + currentY;
            }
            else {
                i--;
            }
        }
        return enemyShips;

    }

    /**
     * The players go on the game.
     * @param board the current board state.
     * @param rowsSize the row size.
     * @param enemyPos the array of enenemy positions.
     * @return the new state of the board.
     */
    public static String[][] playerGuess(String[][] board, int rowsSize, String[] enemyPos) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Next Guess: ");
        String coordString = scanner.nextLine();
        if (coordString == "EXIT") {
            System.exit(0);
        }
        try {
            String[] coords = coordString.split("\\s+");
            int coordY = (int) coords[0].charAt(0) - 'A' + rowsSize + 1;
            // Makes sure the correct number of inputs have been entered.
            if (coords.length == 2) {
                for (int i = 0; i < enemyPos.length; i++) {
                    if (coordString == enemyPos[i]) {
                        board[Integer.parseInt(coords[1]) - 1][coordY] = "X";
                        System.out.println("You sank a ship!");
                    }
                    else {
                        board[Integer.parseInt(coords[1]) - 1][coordY] = "I";
                        System.out.println("You missed.");
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println("Wrong formatting.");
            playerGuess(board, rowsSize, enemyPos);
        }
        return board;
    }

    /**
     * Plays an enemy guess.
     * @param board the current board state.
     * @param known a list of known information by the enemey.
     * @param rows the number of rows.
     * @param cols the number of columns.
     * @return the list of information known to the enemy.
     */
    public static ArrayList<String> enemyGuess(String[][] board, ArrayList<String> known, int rows, int cols) {
        Random rand = new Random();
        int intGuessY = rand.nextInt(cols);
        // Gets a random guess for the enemy.
        String guessX = String.valueOf((char) ('A' + intGuessY));
        String guessY = String.valueOf(rand.nextInt(rows));
        String currentGuess = guessX + " " + guessY;

        // Recursively calls the function if the guess has already been made. 
        if (known.contains(currentGuess)) {
            known = enemyGuess(board, known, rows, cols);
            return known;
        }
        
        known.add(currentGuess);
        return known;
    }

    /**
     * Maps the enemy plays to the board.
     * @param board the current state of the board.
     * @param known the list of known information by the enemy.
     * @return the new state of the board.
     */
    public static String[][] displayEnemyHit(String[][] board, ArrayList<String> known) {

        for (int i = 0; i < known.size(); i++) {
            String[] positions = known.get(i).split(" ");
            if (board[positions[0].charAt(0) - 'A'][Integer.valueOf(positions[1])] == "S") {
                board[positions[0].charAt(0) - 'A'][Integer.valueOf(positions[1])] = "D";
            }
            else {
                board[positions[0].charAt(0) - 'A'][Integer.valueOf(positions[1])] = "A";
            }
        }
        
        return board;
    }

    /**
     * Check for if the game is finished and if so, who has won.
     * @param board the current state of the board.
     * @param boats the number of boats in play.
     * @return
     */
    public static int checkWinState(String[][] board, int ships) {
        int count = 0;
        int countEnemy= 0;

        // Loops over the board and counts the number of enemy and player hits.
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == "X") {
                    count++;
                }else if (board[i][j] == "D") {
                    countEnemy++;
                }
            }
        }
        if (countEnemy >= ships) {
            return 1;
        }
        else if (count >= ships) {
            return 2;
        }
        return 0;
    }

    /**
     * Plays the game.
     */
    public static void play() {
        System.out.println("Welcome to Battle Ships!");
        int rowsSize = 10;
        int columnsSize = 10;
        int shipNo = 2;
        String[] enemyCoords = placeEnemyShips(rowsSize, columnsSize, shipNo);
        String[][] gameBoard = createBoard(rowsSize, columnsSize);
        ArrayList<String> enemyKnowledge = new ArrayList<>();
        gameBoard = placeShips(gameBoard, shipNo);

        int winSate = 0;
        while (winSate == 0) {
            displayBoard(gameBoard, false);
            gameBoard = playerGuess(gameBoard, rowsSize, enemyCoords);
            enemyKnowledge = enemyGuess(gameBoard, enemyKnowledge, rowsSize, columnsSize);
            gameBoard = displayEnemyHit(gameBoard, enemyKnowledge);
            winSate = checkWinState(gameBoard, shipNo);
        }

        if (winSate == 1) {
            System.out.println("GAME OVER");
        }
        else {
            System.out.println("You Win!");
        }

    }
}