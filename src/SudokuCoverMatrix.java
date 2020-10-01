import java.io.FileNotFoundException;

public class SudokuCoverMatrix {

    public final int[][] grid;
    private final int n;
    private final int smallN;
    private final int[][] board;


    public SudokuCoverMatrix(int N, int constraintNumber, int[][] board)
    {
        n = N;
        smallN = (int)Math.sqrt(n);
        this.board = board;
        grid = new int[howManyOptions()][n*n*constraintNumber];
        fillGrid();
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

    private void fillGrid()
    {
        int counter = 0;
        for (int i = 0; i < n;i++)
        {
            for (int j = 0; j < n;j++)
            {
                int boxNumber = ((i)/smallN)*smallN + ((j)/smallN);

                if (board[i][j] == 0)
                {
                    for (int k = 0; k < n;k++)
                    {
                        int set1 = (i)*n + j ;
                        int set2 = (i)*n + k +  (n*n);
                        int set3 = (j)*n + k + (2*n*n);
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

    public static void main(String[] args){


    }

}
