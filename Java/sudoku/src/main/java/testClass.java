import java.util.HashSet;
import java.util.Set;

public class testClass {
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
        boolean[] constraint = new boolean[9];
        Set<Integer> rowSet = new HashSet<>();

        rowSet.add(board[0][0]);
        System.out.println(board[0][0]);
        System.out.println(rowSet.add(board[0][0]));

    }
}
