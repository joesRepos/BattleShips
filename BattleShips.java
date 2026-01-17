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
    }

    public static void play() {
        System.out.println("Welcome to Battle Ships!");
        Scanner scanner = new Scanner(System.in);
        int rowsSize = 10;
        int columnsSize = 10;
        boolean construction = true;
        String[][] gameBoard = createBoard(rowsSize, columnsSize);
        displayBoard(gameBoard, construction);

    }
}