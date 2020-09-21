public class SudokuBoard {

    public SudokuBoard()
    {

    }

    public int[][] hardestBoard()
    {
        int[][] SUDOKU;
        SUDOKU = new int[9][9];
        SUDOKU[0][0] = 8;
        SUDOKU[1][2] = 3;
        SUDOKU[1][3] = 6;
        SUDOKU[2][1] = 7;
        SUDOKU[2][4] = 9;
        SUDOKU[2][6] = 2;
        SUDOKU[3][1] = 5;
        SUDOKU[3][5] = 7;
        SUDOKU[4][4] = 4;
        SUDOKU[4][5] = 5;
        SUDOKU[4][6] = 7;
        SUDOKU[5][3] = 1;
        SUDOKU[5][7] = 3;
        SUDOKU[6][2] = 1;
        SUDOKU[6][7] = 6;
        SUDOKU[6][8] = 8;
        SUDOKU[7][2] = 8;
        SUDOKU[7][3] = 5;
        SUDOKU[7][7] = 1;
        SUDOKU[8][1] = 9;
        SUDOKU[8][6] = 4;
        return SUDOKU;
    }

    public int[][] testBoard()
    {
        int[][] SUDOKU;
        SUDOKU = new int[9][9];
        SUDOKU[0][1] = 3;
        SUDOKU[0][6] = 5;
        SUDOKU[1][1] = 2;
        SUDOKU[1][2] = 5;
        SUDOKU[1][3] = 6;
        SUDOKU[1][5] = 8;
        SUDOKU[2][3] = 7;
        SUDOKU[2][5] = 2;
        SUDOKU[2][6] = 8;
        SUDOKU[2][7] = 9;
        SUDOKU[2][8] = 3;
        SUDOKU[3][3] = 9;
        SUDOKU[3][4] = 4;
        SUDOKU[3][5] = 3;
        SUDOKU[3][6] = 2;
        SUDOKU[3][8] = 8;
        SUDOKU[4][0] = 3;
        SUDOKU[4][2] = 2;
        SUDOKU[4][6] = 4;
        SUDOKU[4][8] = 6;
        SUDOKU[5][0] = 7;
        SUDOKU[5][3] = 2;
        SUDOKU[6][0] = 5;
        SUDOKU[6][1] = 1;
        SUDOKU[6][3] = 8;
        SUDOKU[6][4] = 7;
        SUDOKU[6][5] = 9;
        SUDOKU[6][6] = 3;
        SUDOKU[6][7] = 2;
        SUDOKU[6][8] = 4;
        SUDOKU[7][1] = 7;
        SUDOKU[7][3] = 5;
        SUDOKU[7][7] = 8;
        SUDOKU[8][0] = 4;
        SUDOKU[8][4] = 2;
        SUDOKU[8][6] = 7;
        return SUDOKU;
    }

    public static int optionSize(int[][] grid)
    {
        int size = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j] != 0) size += 1;
                else size += 9;
            }
        }
        return size;
    }

    public void printBoard(int[][] grid)
    {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                System.out.print(grid[i][j] + "  ");

            }
            System.out.println();
        }
    }

}
