public class Main {

    public static void main(String[] args) {

        int[][] SUDOKU = new int[9][9];
        SUDOKU[1][4] = 5;
        SUDOKU[4][7] = 3;
        SUDOKU[5][8] = 2;
        SudokuBoard sb = new SudokuBoard(SUDOKU);

        System.out.println(sb.isValid(4,9,2));
    }
}
