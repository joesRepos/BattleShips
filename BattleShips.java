import java.util.Scanner;

public class BattleShips {
    public static void main(String[] args) {
        play();
    }

    public static String[][] createBoard(int rows, int cols) {
        String[][] board = new String[rows][(cols * 2) + 2];

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

    public static void displayBoard(String[][] board, boolean left) {
        System.out.print(" ");
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

    public static String[][] placeShips(String[][] board, int ships) {
        displayBoard(board, true);
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < ships; i++) {
            System.out.print("Next Ship:");
            String coordString = scanner.nextLine();
            String[] coords = coordString.split("\\s+");
            if (coords.length == 2) {
                board[Integer.parseInt(coords[1]) - 1][(int) coords[0].charAt(0) - 'A'] = "S";
            }
            else {
                System.out.println("Wrong Formatting.");
                i--;
            }
        }
        return board;
    }

    public static String[][] playerGuess(String[][] board, int rowsSize, String[] enemyPos) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Next Guess: ");
        String coordString = scanner.nextLine();
        String[] coords = coordString.split("\\s+");
            if (coords.length == 2) {
                for (int i = 0; i < enemyPos.length; i++) {
                    if (coordString == enemyPos[i]) {
                        int coordY = (int) coords[0].charAt(0) - 'A' + rowsSize + 1;
                        board[Integer.parseInt(coords[1]) - 1][coordY] = "X";
                    }
                }
            }
        return board;
    }

    public static void play() {
        System.out.println("Welcome to Battle Ships!");
        Scanner scanner = new Scanner(System.in);
        int rowsSize = 10;
        int columnsSize = 10;
        int shipNo = 2;
        String[] enemyCoords = {"A 1", "B 1"};
        String[][] gameBoard = createBoard(rowsSize, columnsSize);
        gameBoard = placeShips(gameBoard, shipNo);
        displayBoard(gameBoard, false);
        while (shipNo > 0) {
            gameBoard = playerGuess(gameBoard, rowsSize, enemyCoords);
        }

    }
}