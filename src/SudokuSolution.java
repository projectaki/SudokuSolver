//Class for transforming results from DancingLinks column names to Sudoku board i,j and value

public class SudokuSolution {
    private final int[][] board;
    private final int n;
    private int i;
    private int j;
    private int val;

    public SudokuSolution(int N)
    {
        n = N;
        board  = new int[n][n];
    }

    public void getParams(String x)
    {
        if (isFirstSet(Integer.parseInt(x)))
        {
            i = getFirstNumberConstraint(Integer.parseInt(x));
            j = getSecondNumberConstraint(Integer.parseInt(x));
        }

        if(isSecondSet(Integer.parseInt(x))) val = getSecondNumberConstraint(Integer.parseInt(x));
    }

    public void fillSolutionBoard()
    {
        board[i][j] = val + 1;
    }

    public void printSolution()
    {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j] + "  ");

            }
            System.out.println();
        }
    }

    private int getFirstNumberConstraint(int number)
    {
        int setId = number%(n*n);
        return setId/n;

    }

    private int getSecondNumberConstraint(int number)
    {
        int setId = number%(n*n);
        return setId%n;
    }

    private boolean isFirstSet(int number)
    {
        return number/(n*n) == 0;
    }

    private boolean isSecondSet(int number)
    {
        return number/(n*n) == 1;
    }

}
