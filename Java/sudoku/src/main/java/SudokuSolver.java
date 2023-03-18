import java.util.*;
import java.util.stream.IntStream;

public class SudokuSolver {
    public static class CustomException extends RuntimeException {
        CustomException(String message) {
            super(message);
        }
    }
//    private static int[][] board;
    private static int[][] board = {
        {8, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 3, 6, 0, 0, 0, 0, 0},
        {0, 7, 0, 0, 9, 0, 2, 0, 0},
        {0, 5, 0, 0, 0, 7, 0, 0, 0},
        {0, 0, 0, 0, 4, 5, 7, 0, 0},
        {0, 0, 0, 1, 0, 0, 0, 3, 0},
        {0, 0, 1, 0, 0, 0, 0, 6, 8},
        {0, 0, 8, 5, 0, 0, 0, 1, 0},
        {0, 9, 0, 0, 0, 0, 4, 0, 0}
      };

    
    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("enter a 9x9 sudoku puzzle:");
        SudokuSolver solver = new SudokuSolver();
        if(!solver.solve(board)){
            System.out.println("wrong");
        }else {
            solver.printBoard();
        }
    }

    // Print the Sudoku board
    public static void printBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Solve the Sudoku board
    public static boolean solve(int[][] board) throws CustomException{
        // initialize row and col temporarily as -1

        int row = -1;
        int col = -1;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) { // Found a cell equal to 0
                    row = i;
                    col = j;
                    // Try numbers from 1-9 in the cell
                    for (int num = 1; num <= 9; num++) {
                        board[row][col] = num;
                        if (isValid(board, row, col, num)) {
                            // Recursively call solve
                            if (solve(board)) {
                                return true;
                            }
                        }
                        // Backtrack if the solution doesnt pass valid check
                        board[row][col] = 0;
                    }
                    return false;
                }
            }
        }
        return true;
    }

    // Check if a number can be used in a cell
    private static boolean isValid(int[][] board, int row, int col, int num) {
        // Check if row is valid
        Set<Integer> rowSet = new HashSet<>();
        if(IntStream.range(0, 9).anyMatch(i -> board[row][i] != 0 && !rowSet.add(board[row][i]))){
            return false;
        }

        // Check if column is valid
        Set<Integer> colSet = new HashSet<>();
        if(IntStream.range(0, 9).anyMatch(i -> board[i][col] != 0 && !colSet.add(board[i][col]))){
            return false;
        }

        // Check if section is valid
        Set<Integer> sectSet = new HashSet<>();
        int sectionRow = (row / 3) * 3;
        int sectionCol = (col / 3) * 3;

        if(IntStream.range(sectionRow, sectionRow + 3).anyMatch(i -> IntStream.range(sectionCol, sectionCol + 3).anyMatch(j -> board[i][j] != 0 && !sectSet.add(board[i][j])))){
            return false;
        }


        // The number can be used in the cell
        return true;
    }
}
