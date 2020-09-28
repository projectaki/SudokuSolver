public class testing {

    private final int[][] grid;
    private final int n;
    private final int smallN;
    private final int[][] board;
    private final int optionSize;

    public testing(int N, int constraintNumber, int[][] board)
    {
        n = N;
        smallN = (int)Math.sqrt(n);
        this.board = board;
        optionSize = howManyOptions();
        grid = new int[optionSize][n*n*constraintNumber];




    }

    private int howManyOptions()
    {
        int clues = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != 0) clues++;
            }

        }
        return (n*n*n) - (clues*(n-1));
    }

    public void fillGrid()
    {
        int counter = 0;
        for (int i = 0; i < n;i++)
        {
            for (int j = 0; j < n;j++)
            {
                if (board[i][j] == 0)
                {
                    for (int k = 0; k < n;k++)
                    {
                        int set1 = (i)*n + j ;
                        int set2 = (i)*n + k +  (n*n);
                        int set3 = (j)*n + k + (2*n*n);
                        int boxNumber = ((i)/smallN)*smallN + ((j)/smallN);
                        int set4 = boxNumber*n +k + (3*n*n);

                        grid[counter][set1] = 1;
                        grid[counter][set2] = 1;
                        grid[counter][set3] = 1;
                        grid[counter][set4] = 1;
                        counter++;
                    }
                }
                else {
                    int k = board[i][j] - 1;
                    int set1 = (i)*n + j ;
                    int set2 = (i)*n + k +  (n*n);
                    int set3 = (j)*n + k + (2*n*n);
                    int boxNumber = ((i)/smallN)*smallN + ((j)/smallN);
                    int set4 = boxNumber*n +k + (3*n*n);

                    grid[counter][set1] = 1;
                    grid[counter][set2] = 1;
                    grid[counter][set3] = 1;
                    grid[counter][set4] = 1;
                    counter++;
                }

            }
        }
    }


    public static void main(String[] args) {

        SudokuBoard s = new SudokuBoard();

        long startTime = System.currentTimeMillis();
        testing test = new testing(9, 4,s.hardestBoard());
        test.fillGrid();
        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime-startTime) + "ms");


        for (int i = 0; i < (test.optionSize); i++) {
            for (int j = 0; j < (4*9*9); j++) {
                System.out.print(test.grid[i][j]);
            }
            System.out.println();
        }

        /*
        long startTime2 = System.currentTimeMillis();

        ExactCoverLib X = new ExactCoverLib(9,4,s.hardestBoard());
        X.fillOptionsBasedOnSudokuBoard();
        X.fill4constraints();
        X.fillCoverMatrix4Constraints();
        long endTime2 = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime2-startTime2) + "ms");
*/


    }
}
