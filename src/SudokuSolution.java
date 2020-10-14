/**
 * Class for transforming results from DancingLinks column names to Sudoku board i,j and value
 */
public class SudokuSolution {
    private final int[][] board;
    private final int n;
    private int i;
    private int j;
    private int val;

    /**
     *
     * @param N Board dimension
     */
    public SudokuSolution(int N) {
        n = N;
        board  = new int[n][n];
    }

    /**
     * Given the col name as string extracts the solution
     * @param x Col name as string
     */
    public void getParams(String x) {
        if (isFirstSet(Integer.parseInt(x))) {
            i = getFirstNumberConstraint(Integer.parseInt(x));
            j = getSecondNumberConstraint(Integer.parseInt(x));
        }

        if(isSecondSet(Integer.parseInt(x))) val = getSecondNumberConstraint(Integer.parseInt(x));
    }

    /**
     * Fill out the board with the solution
     */
    public void fillSolutionBoard() {
        board[i][j] = val + 1;
    }

    public void printSolution() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j] + "  ");

            }
            System.out.println();
        }
    }

    /**
     * Given a number returns the first numbers constraint
     * @param number column number
     * @return returns
     */
    private int getFirstNumberConstraint(int number) {
        int setId = number%(n*n);
        return setId/n;

    }

    /**
     * Given a number returns the first numbers constraint
     * @param number column number
     * @return returns
     */
    private int getSecondNumberConstraint(int number) {
        int setId = number%(n*n);
        return setId%n;
    }

    /**
     * Is the number in 1st set
     * @param number column number
     * @return True if the column is in first set
     */
    private boolean isFirstSet(int number) {
        return number/(n*n) == 0;
    }

    /**
     * Is the number in 2nd set
     * @param number column number
     * @return True if the column is in second set
     */
    private boolean isSecondSet(int number) {
        return number/(n*n) == 1;
    }

}
