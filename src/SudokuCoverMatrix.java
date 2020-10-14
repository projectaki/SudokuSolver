/**
 * Class for generating a cover matrix for a n dimension sudoku board, given a n dimension, a constraint set number (4 for Sudoku), and a sudoku board
 */
public class SudokuCoverMatrix {

    public final int[][] grid;
    private final int n;
    private final int smallN;
    private final int[][] board;

    /**
     * Cover Matrix generator given a 2D array representing a Sudoku board
     * @param N Dimension of sudoku board
     * @param constraintNumber The amount of constraint sets
     * @param board 2D array <== Sudoku board
     */
    public SudokuCoverMatrix(int N, int constraintNumber, int[][] board) {
        n = N;
        smallN = (int)Math.sqrt(n);
        this.board = board;
        grid = new int[howManyOptions()][n*n*constraintNumber];
        fillGrid();
    }

    /**
     * Number of available options based on clues given, generally we have n*n*n options (n*n squares)*n numbers
     * Given clues reduce this number
     * @return Returns the amount of options based on given clues
     */
    private int howManyOptions() {
        int clues = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != 0) clues++;
            }

        }
        return (n*n*n) - (clues*(n-1));
    }

    /**
     * Method for filling the grid with 0s and 1s to create the cover matrix
     */
    private void fillGrid() {
        int counter = 0;
        // first for loop is Row
        for (int i = 0; i < n;i++) {
            // Second for loop is Col
            for (int j = 0; j < n;j++) {
                // box number is the box where the current i,j is positioned in
                int boxNumber = ((i)/smallN)*smallN + ((j)/smallN);

                // when the board at i,j doesn't have a clue we loop all options for that cell
                if (board[i][j] == 0) {
                    for (int k = 0; k < n;k++) {
                        // first constraint set (cell)
                        int set1 = (i)*n + j ;
                        // second constraint set (row)
                        int set2 = (i)*n + k +  (n*n);
                        // third constraint set (col)
                        int set3 = (j)*n + k + (2*n*n);
                        // fourth constraint set (box)
                        int set4 = boxNumber*n +k + (3*n*n);

                        // Fill the cover matrix
                        grid[counter][set1] = 1;
                        grid[counter][set2] = 1;
                        grid[counter][set3] = 1;
                        grid[counter][set4] = 1;
                        counter++;
                    }
                }
                // when board at i,j has a clue we just use the number at that cell
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
