public class Main {

    public static void main(String[] args) {

        int[][] SUDOKU = new int[9][9];
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


        SudokuBoard sb = new SudokuBoard(SUDOKU);

        for(int i = 0; i<9; i++)
        {
            for(int j = 0; j<9; j++)
            {
                System.out.print(SUDOKU[i][j] + "|");
            }
            System.out.println();
        }
        System.out.println("------------------------------");
        System.out.println("Solution");
        //System.out.println(sb.isValid(4,9,2));
        //System.out.println(SUDOKU[5][5]);
        sb.solve();
        sb.printBoard();

    }
}
