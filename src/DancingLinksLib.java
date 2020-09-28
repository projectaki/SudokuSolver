import java.util.Stack;

public class DancingLinksLib {
    // External library for constraints, options
    private ExactCoverLib lib;
    // Main header Node to start the search
    private final ColumnNode h;
    // Stack for holding a solution
    private final Stack<Node> solutionStack;

    private static class Node {
        protected Node L;
        protected Node R;
        protected Node U;
        protected Node D;
        protected ColumnNode C;

        public Node() {
            L = this;
            R = this;
            U = this;
            D = this;
        }
    }

    private static class ColumnNode extends Node {
        private String N;
        private int S;

        public ColumnNode()
        {

        }

        public ColumnNode(String name)
        {
            N = name;
        }
    }

    public DancingLinksLib()
    {
        solutionStack = new Stack<>();
        h = new ColumnNode();
    }

    private int[][] setUp(int[][] board)
    {
        this.lib = new ExactCoverLib(9,4,board);
        lib.fillOptionsBasedOnSudokuBoard();
        lib.fill4constraints();
        lib.fillCoverMatrix4Constraints();
        return lib.coverMatrix;
    }


    private void DLX(int[][] matrix,String[] constraintNames)
    {
        coverMatrixToLinkedList(matrix, constraintNames);
        search();
    }

    // helper method for getting the solutions into the sudoku board
    // (takes a concat string and extracts the values for Row,Col and Val
    private int[] convertResultStringToBoardValues(String concatResult)
    {
        int[] numbs = new int[3];
        for (int i = 0; i <(4*4) - 1;i++)
        {

            int temp = Character.getNumericValue(concatResult.charAt(i+1));


            if (Character.toString(concatResult.charAt(i)).equals("R"))
            {
                numbs[0] = temp;
            }
            else if (Character.toString(concatResult.charAt(i)).equals("C"))
            {
                numbs[1] = temp;
            }
            else if (Character.toString(concatResult.charAt(i)).equals("#"))
            {
                numbs[2] = temp;
            }
        }
        return numbs;
    }

    private void coverMatrixToLinkedList(int[][] matrix, String[] constraints) {
        ColumnNode[] columns = new ColumnNode[constraints.length];

        for (int i = 0; i < constraints.length; i++) {
            ColumnNode x = new ColumnNode(constraints[i]);
            x.L = h.L;
            x.R = h;
            h.L.R = x;
            h.L = x;
            columns[i] = x;
        }

        for (int j = 0; j < matrix.length;j++ ) {
            Node lastNode = null;
            for (int k = 0; k < matrix[0].length; k++) {
                if (matrix[j][k] == 1)
                {
                    Node x = new Node();
                    ColumnNode col = columns[k];
                    x.U = col.U;
                    x.D = col;
                    col.U.D = x;
                    col.U = x;
                    x.C = col;
                    col.S++;

                    if (lastNode == null) lastNode = x;
                    x.L = lastNode.L;
                    x.R = lastNode;
                    lastNode.L.R = x;
                    lastNode.L = x;
                }
            }
        }
    }


    // DLX method for covering a column (from Knuth's paper)
    // removes the given column and also removes the Nodes in given column from other columns
    private void cover(Node c)
    {
        // System.out.println("cover");
        c.R.L = c.L;
        c.L.R = c.R;

        Node i = c.D;
        while (i != c)
        {
            Node j = i.R;
            while (j != i)
            {
                j.D.U = j.U;
                j.U.D = j.D;
                j.C.S -= 1;
                j = j.R;
            }
            i = i.D;
        }
    }

    // Uncover method (from Knuth's paper), exact reverse of cover method
    // Resets the nodes in other columns and then resets the column
    private void uncover(Node c)
    {
        // System.out.println("uncover");
        Node i = c.U;
        while (i != c)
        {
            Node j = i.L;
            while (j != i)
            {
                j.C.S += 1;
                j.D.U = j;
                j.U.D = j;

                j = j.L;
            }
            i = i.U;
        }

        c.R.L = c;
        c.L.R = c;
    }

    // Heuristic method for choosing the column with least amount of nodecount
    private Node chooseColumn()
    {
        ColumnNode c = (ColumnNode)h.R;
        ColumnNode j = (ColumnNode)h.R;
        double s = Double.POSITIVE_INFINITY;
        while (j != h)
        {
            if (j.S < s)
            {
                c = j;
                s = j.S;
            }
            j = (ColumnNode) j.R;
        }
        return c;
    }

    // Search method for exact cover (Knuth's paper) if Node h right link is itself(all columns removed) we have solution
    // Else recursivley cover/uncover columns (backtracking) if we cant move further with solution
    private void search()
    {

        if (h.R == h)
        {

            SudokuBoard sb = new SudokuBoard();
            int[][] board = new int[9][9];
            for (Node x : solutionStack)
            {
                String concat = x.C.N + x.R.C.N + x.R.R.C.N + x.R.R.R.C.N;
                int[] temp = convertResultStringToBoardValues(concat);
                // System.out.println(counter++ + " " + x.C.N +  " " + x.R.C.N + " " + x.R.R.C.N +" " + x.R.R.R.C.N);
                board[temp[0] - 1][temp[1] - 1] = temp[2];

            }
            sb.printBoard(board);

        }
        else
        {
            Node c = chooseColumn();
            cover(c);
            Node i = c.D;
            while (i != c)
            {
                solutionStack.add(i);
                Node j = i.R;
                while (j != i)
                {
                    cover(j.C);
                    j = j.R;
                }
                search();
                i = solutionStack.pop();
                c = i.C;
                j = j.L;
                while (j != i)
                {
                    uncover(j.C);
                    j = j.L;
                }
                i = i.D;
            }
            uncover(c);
        }
    }

    // method for printing the NodeMatrix (compare to normal Matrix)

    public static void main(String[] args) {

        SudokuBoard boards = new SudokuBoard();
        DancingLinksLib dl = new DancingLinksLib();
        dl.setUp(boards.hardestBoard());
        int[][] matrix = dl.lib.coverMatrix;
        String[] consts = dl.lib.constraints;
        long startTime = System.currentTimeMillis();
        dl.DLX(matrix,consts);
        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime-startTime) + "ms");



    }



}
