public class SudokuBoard {
    private final int[][] board;
    private final int smallN;

    public SudokuBoard()
    {
        board = new int[9][9];
        smallN  = 3;
    }

    private boolean boxSafe(int i, int j, int numb)
    {
        //get small box from coord
        boolean safe = false;
        int bsI;
        int bfI;
        if (i % smallN == 0) bsI = i/smallN - 1;
        else bsI = i/smallN;
        bfI = bsI + smallN;

        int bsJ;
        int bfJ;

        if (j % smallN == 0) bsJ = j/smallN - 1;
        else bsJ = j/smallN;
        bfJ = bsJ + smallN;

        for (int k = 0; k < 9; k++)
        {
            for (int l = 0; l < 9; l++)
            {
                if ((k >= bsI && k <= bfI) && (l >= bsJ && l <= bfJ)) {
                    safe = numb != board[k][l];
                }
            }
        }

        return safe;
    }

    private boolean rowSafe(int i, int numb)
    {
        boolean safe = false;
        for (int k = 0; k < 9; k++)
        {
            for (int l = 0; l < 9; l++)
            {
                if (k == i)
                {
                    safe = numb != board[k][l];
                }
            }
        }

        return safe;
    }

    private boolean colSafe(int j, int numb)
    {
        return false;
    }



}
