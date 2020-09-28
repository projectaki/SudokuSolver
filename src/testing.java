public class testing {

    private int[][] grid;
    private int n;
    private int smallN;

    public testing(int N, int constraintNumber)
    {
        n = N;
        smallN = (int)Math.sqrt(n);
        grid = new int[n*n*n][n*n*constraintNumber];


    }

    public void fillGrid()
    {
        int counter = 0;
        for (int i = 0; i < n;i++)
        {
            for (int j = 0; j < n;j++)
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
        }
    }


    public static void main(String[] args) {

        testing test = new testing(9, 4);
        test.fillGrid();

        for (int i = 0; i < (9*9*9); i++) {
            for (int j = 0; j < (4*9*9); j++) {
                System.out.print(test.grid[i][j]);
            }
            System.out.println();
        }

    }
}
