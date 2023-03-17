import java.util.*;

public class SudokuSolver {

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
        SudokuSolver solver = new SudokuSolver();
        solver.solve(board);
        solver.printBoard();
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
    public static boolean solve(int[][] board) {
        // initialize row and col temporarily as -1

        int row = -1;
        int col = -1;
        boolean isEmpty = true;
        // Find an empty cell
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    row = i;
                    col = j;

                    // Found a cell equal to 0, break out of loop since board is not empty
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
            if (!isEmpty) {
                break;
            }
        }
        return true;
    }


    // Check if a number can be used in a cell
    private static boolean isValid(int[][] board, int row, int col, int num) {
        // Check if row is valid
        Set<Integer> rowSet = new HashSet<>();
        for (int i = 0; i < 9; i++) {
            if (board[row][i] != 0 && !rowSet.add(board[row][i])) {
                return false;
            }
        }

        // Check if column is valid
        Set<Integer> colSet = new HashSet<>();
        for (int i = 0; i < 9; i++) {
            if (board[i][col] != 0 && !colSet.add(board[i][col])) {
                return false;
            }
        }

        // Check if section is valid
        Set<Integer> sectSet = new HashSet<>();
        int sectionRow = (row / 3) * 3;
        int sectionCol = (col / 3) * 3;
        for (int i = sectionRow; i < sectionRow + 3; i++) {
            for (int j = sectionCol; j < sectionCol + 3; j++) {
                if (board[i][j] != 0 && !sectSet.add(board[i][j])) {
                    return false;
                }
            }
        }

        // The number can be used in the cell
        return true;
    }
}
