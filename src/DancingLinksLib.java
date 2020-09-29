import java.util.Stack;

public class DancingLinksLib {
    // External library for constraints, options
    private final ExactCoverEfficient eCover;
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

    public DancingLinksLib(int N, int constraintNumber, int[][] board)
    {
        solutionStack = new Stack<>();
        h = new ColumnNode();
        eCover = new ExactCoverEfficient(N,constraintNumber,board);
        eCover.fillGrid();
    }

    private void DLX(int[][] matrix)
    {
        coverMatrixToLinkedList(matrix);
        search();
    }

    // helper method for getting the solutions into the sudoku board
    // (takes a concat string and extracts the values for Row,Col and Val


    private void coverMatrixToLinkedList(int[][] matrix) {
        ColumnNode[] columns = new ColumnNode[eCover.grid[0].length];

        for (int i = 0; i < columns.length; i++) {
            ColumnNode x = new ColumnNode(i +"");
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
                Node n = x;
                int i=0;
                int j=0;
                int val=0;
                //System.out.print(x.C.N + " ");
                if (eCover.isFirstSet(Integer.parseInt(x.C.N)))
                {
                    i = eCover.getFirstNumberConstraint(Integer.parseInt(x.C.N));
                    j = eCover.getSecondNumberConstraint(Integer.parseInt(x.C.N));
                }

                if(eCover.isSecondSet(Integer.parseInt(x.C.N))) val = eCover.getSecondNumberConstraint(Integer.parseInt(x.C.N));
                x = x.R;
                while (n != x)
                {
                    if (eCover.isFirstSet(Integer.parseInt(x.C.N)))
                    {
                        i = eCover.getFirstNumberConstraint(Integer.parseInt(x.C.N));
                        j = eCover.getSecondNumberConstraint(Integer.parseInt(x.C.N));
                    }

                    if(eCover.isSecondSet(Integer.parseInt(x.C.N))) val = eCover.getSecondNumberConstraint(Integer.parseInt(x.C.N));
                    //System.out.print(x.C.N + " ");
                    x = x.R;
                }
                board[i][j] = val + 1;
                //System.out.println();
                //System.out.println(x.C.N);


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
        long startTime = System.currentTimeMillis();
        DancingLinksLib dl = new DancingLinksLib(9,4,boards.hardestBoard());
        dl.DLX(dl.eCover.grid);
        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime-startTime) + "ms");



    }



}
