public class SudokuBoard {
    private final int[][] board;
    private final boolean[][] immutable;
    private int x;
    private int y;

    public SudokuBoard(int[][] init)
    {
        board = new int[9][9];
        immutable = new boolean[9][9];
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                board[i][j] = init[i][j];
                if (init[i][j] != 0) immutable[i][j] = true;
            }
        }
        x = 1;
        y = 1;

    }

    public void solve()
    {
        solve(y);
    }

    private void solve(int j)
    {
        handlePosition();
        if (x == 9 && y == 9) return;
        if (immutable[x - 1][y - 1]) y++;
        for (int numb = 1; numb <=9;numb++)
        {

            if (isValid(x,y,numb))
            {
                board[(x)-1][(y++)-1] = numb;
                solve((y-1));
            }
        }
        board[x - 1][(y--) - 1] = 0;
        if (immutable[x - 1][y - 1]) y--;
    }

    private void handlePosition()
    {
        if (y == 10)
        {
            x++;
            y = 1;
        }
        if (y == 0)
        {
            x--;
            y = 9;
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
