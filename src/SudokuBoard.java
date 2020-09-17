public class SudokuBoard {
    private final int[][] board;

    public SudokuBoard(int[][] init)
    {
        board = new int[9][9];
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                board[i][j] = init[i][j];
            }
        }

    }

    public boolean isValid(int i, int j, int numb)
    {
        return boxSafe(i,j,numb) && rowSafe(i,numb) && colSafe(j,numb);
    }

    private boolean boxSafe(int i, int j, int numb)
    {
        int startI;
        int startJ;
        if (i % 3 == 0) startI = ((i/3) - 1)*3;
        else startI = (i/3) * 3;
        if (j % 3 == 0) startJ = ((j/3) - 1)*3;
        else startJ = (j/3) * 3;

        for (int k = startI; k < startI + 3; k++)
        {
            for (int l = startJ; l < startJ + 3; l++)
            {
                if (board[k][l] == numb) return false;
            }
        }
        return true;
    }

    private boolean rowSafe(int i, int numb)
    {

        for (int j = 0; j < 9; j++)
        {
            if (board[i - 1][j] == numb) return false;
        }
        return true;

    }

    private boolean colSafe(int j, int numb)
    {
        for (int i = 0; i < 9; i++)
        {
            if (board[i][j - 1] == numb) return false;
        }
        return true;
    }



}
