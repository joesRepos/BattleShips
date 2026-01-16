import java.util.Scanner;

public class BattleShips {
    public static void main(String[] args) {
        play();
    }

    public static String[][] createBoard(int rows, int cols) {
        String[][] board = new String[rows * 2][(cols * 2) + 2];

        for (int i = 0; i < rows * 2; i++) {
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

    public static void displayBoard(String[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
        }
    }

    public static void play() {
        System.out.println("Welcome to Battle Ships!");
        Scanner scanner = new Scanner(System.in);
        int rowsSize = 10;
        int columnsSize = 10;
        String[][] gameBoard = createBoard(rowsSize, columnsSize);
        displayBoard(gameBoard);

    }
}