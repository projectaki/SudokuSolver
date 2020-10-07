import java.io.FileNotFoundException;
import java.util.Stack;

public class DancingLinks {
    // External library for constraints, options
    private final int[][] matrix;
    // Main header Node to start the search
    private final ColumnNode h;
    // Stack for holding a solution
    private final Stack<Node> solutionStack;
    //Number of Solutions
    private int howManySolutions;


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

    public DancingLinks(int[][] matrix)
    {
        solutionStack = new Stack<>();
        h = new ColumnNode();
        this.matrix = matrix;
        howManySolutions = 0;

        coverMatrixToLinkedList();
    }

    public void DLX()
    {
        search();
    }

    // Given a cover matrix in 0s and 1s sets up the circular doubly linked list
    private void coverMatrixToLinkedList() {
        // Array of columns
        ColumnNode[] columns = new ColumnNode[matrix[0].length];

        //Names of colums 0 - to columns length
        // links the columns together
        for (int i = 0; i < columns.length; i++) {
            ColumnNode x = new ColumnNode(i +"");
            x.L = h.L;
            x.R = h;
            h.L.R = x;
            h.L = x;
            columns[i] = x;
        }

        // Add and link nodes together
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

            SudokuSolution handler = new SudokuSolution(9);
            for (Node x : solutionStack)
            {
                Node n = x;
                // System.out.print(x.C.N + " ");
                handler.getParams(x.C.N);
                x = x.R;
                while (n != x)
                {
                    handler.getParams(x.C.N);
                    //System.out.print(x.C.N + " ");
                    x = x.R;
                }
                handler.fillSolutionBoard();
                //System.out.println();
                //System.out.println(x.C.N);
            }
            handler.printSolution();
            howManySolutions+=1;
            System.out.println("------------------------------------------ " + howManySolutions);


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

    public static void main(String[] args) throws FileNotFoundException {

        SudokuBoard boards = new SudokuBoard();
        long startTime = System.currentTimeMillis();

        SudokuCoverMatrix e = new SudokuCoverMatrix(9,4,boards.hardestBoard());
        DancingLinks dl = new DancingLinks(e.grid);
        dl.DLX();
        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime-startTime) + "ms");



    }



}
