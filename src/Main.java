public class Main {

    public static void main(String[] args) {

        int[][] SUDOKU = new int[9][9];
        SUDOKU[1][2] = 3;
        SUDOKU[1][7] = 5;
        SUDOKU[2][2] = 2;
        SUDOKU[2][3] = 5;
        SUDOKU[2][4] = 6;
        SUDOKU[2][6] = 8;
        SUDOKU[3][4] = 7;
        SUDOKU[3][6] = 2;
        SUDOKU[3][7] = 8;
        SUDOKU[3][8] = 9;
        SUDOKU[3][9] = 3;
        SUDOKU[4][4] = 9;
        SUDOKU[4][5] = 4;
        SUDOKU[4][6] = 3;
        SUDOKU[4][7] = 2;
        SUDOKU[4][9] = 8;
        SUDOKU[5][1] = 3;
        SUDOKU[5][3] = 2;
        SUDOKU[5][7] = 4;
        SUDOKU[5][9] = 6;
        SUDOKU[6][1] = 7;
        SUDOKU[6][4] = 2;
        SUDOKU[7][1] = 5;
        SUDOKU[7][2] = 1;
        SUDOKU[7][4] = 8;
        SUDOKU[7][5] = 7;
        SUDOKU[7][6] = 9;
        SUDOKU[7][7] = 3;
        SUDOKU[7][8] = 2;
        SUDOKU[7][9] = 4;
        SUDOKU[8][2] = 7;
        SUDOKU[8][4] = 5;
        SUDOKU[8][8] = 8;
        SUDOKU[9][1] = 4;
        SUDOKU[9][5] = 2;
        SUDOKU[9][7] = 7;


        SudokuBoard sb = new SudokuBoard(SUDOKU);

        System.out.println(sb.isValid(4,9,2));
        System.out.println(SUDOKU[5][5]);
        sb.solve();
    }
}
