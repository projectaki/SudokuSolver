// Class for solving n dimension Sudoku with a Brute force Approach

import java.io.FileNotFoundException;

public class BruteForceSolver {
    private final int[][] board;
    private final int n;
    private final int smallN;
    private boolean finished;

    public BruteForceSolver(int[][] init)
    {
        board = init;
        finished = false;
        n = board.length;
        smallN = (int)Math.sqrt(n);
        solve();
    }

    private void solve()
    {
        solve(0);
    }

    // Recursive backtracking brute force solution
    private void solve(int count)
    {
        // start at 0, convert number to i,j mapping
        int i = count/n;
        int j = count % n;
        // if i = n then that means we have solved the board ( it only happens if we successfully fill out the last cell)
        // Print solution
        if (i == n)
        {
            finished = true;
            SudokuBoard b = new SudokuBoard();
            b.printBoard(board);
        }
        // If we have found a solution then any more calls to the solve function will do nothing
        if(!finished)
        {
            // Skip the already filled out cells
            if (board[i][j] == 0)
            {
                for (int k = 1; k <= n; k++) {
                    if (isValid(i,j,k))
                    {
                        // optimization
                        board[i][j] = k;
                        solve(count + 1);
                    }
                }
                board[i][j] = 0;
            }
            else
            {
                solve(count + 1);
            }
        }

    }

    private boolean isValid(int i, int j, int numb)
    {
        return boxSafe(i,j,numb) && rowSafe(i,numb) && colSafe(j,numb);
    }

    // Given i, j and a number returns true if the box of i,j doesnt contain the number
    private boolean boxSafe(int i, int j, int numb)
    {
        int startI;
        int startJ;
        if (i % smallN == 0) startI = ((i/smallN))*smallN;
        else startI = (i/smallN) * smallN;
        if (j % smallN == 0) startJ = ((j/smallN))*smallN;
        else startJ = (j/smallN) * smallN;

        for (int k = startI; k < startI + smallN; k++)
        {
            for (int l = startJ; l < startJ + smallN; l++)
            {
                if (board[k][l] == numb) return false;
            }
        }
        return true;
    }

    // Given i and a number, return true if row at i doesnt contain the number
    private boolean rowSafe(int i, int numb)
    {

        for (int j = 0; j < n; j++)
        {
            if (board[i][j] == numb) return false;
        }
        return true;

    }

    // Given j and a number, return true if col at h doesnt contain the number
    private boolean colSafe(int j, int numb)
    {
        for (int i = 0; i < n; i++)
        {
            if (board[i][j] == numb) return false;
        }
        return true;
    }

    public static void main(String[] args) throws FileNotFoundException {

        SudokuBoard sb = new SudokuBoard();
        long startTime = System.currentTimeMillis();
        BruteForceSolver b = new BruteForceSolver(sb.bigBoard());
        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime-startTime) + "ms");


    }



}
