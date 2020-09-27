import java.util.Stack;

public class DancingLinksLib {
    //  Matrix to hold the Nodes
    private final Node[][] NodeMatrix;
    // External library for constraints, options
    private final ExactCoverLib lib;
    // Main header Node to start the search
    private final ColumnNode h;
    // Stack for holding a solution
    private final Stack<Node> solutionStack;
    // Sudoku board with clues
    private final int[][] board;
    // Number of constraints
    private final int constraintNumber;

    private static class Node {
        protected Node L;
        protected Node R;
        protected Node U;
        protected Node D;
        protected ColumnNode C;

        public Node() {

        }
    }

    private static class ColumnNode extends Node {
        private String N;
        private int S;

        public ColumnNode()
        {

        }
    }

    public DancingLinksLib(int[][] board)
    {
        solutionStack = new Stack<>();
        h = new ColumnNode();
        constraintNumber = 4;
        this.board = board;
        lib = new ExactCoverLib(this.board.length,constraintNumber,this.board);
        lib.fillOptionsBasedOnSudokuBoard();
        lib.fill4constraints();
        NodeMatrix = new Node[lib.options.length + 1][lib.constraints.length];
        fillMatrix(lib.constraints);
        setupLinks();
    }

    private void DLX()
    {
        search();
    }
/*
// For testing on 2x2 sudoku board with 3 cosntraints on cell,row,col without box constraint
    private void testDLX()
    {
        SudokuBoard boards = new SudokuBoard();
        int[][] grid = boards.smallTestBoard();
        exact = new ExactCoverMatrixSudoku(grid.length,3,grid);
        exact.fillOptionsBasedOnSudokuBoard();
        exact.fillConstraints();
        NodeMatrix = new Node[exact.options.length + 1][exact.constraints.length];
        fillCoverMatrix3Constraints(exact.constraints);
        setupLinks(h);
        search();
    }
*/
    // helper method for getting the solutions into the sudoku board
    // (takes a concat string and extracts the values for Row,Col and Val
    private int[] convertResultStringToBoardValues(String concatResult)
    {
        int[] numbs = new int[3];
        for (int i = 0; i <(constraintNumber*4) - 1;i++)
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
    // helper method for setting up links
    // returns the "j" of the next available Node in the matrix row i
    private int next(int i, int j)
    {
        int circleJ = (j+1) % lib.constraints.length;
        while (NodeMatrix[i][circleJ] == null)
        {
            circleJ = (circleJ+1) % lib.constraints.length;
        }
        return circleJ;
    }

    // helper method for setting up links
    // returns the "i" of the next available Node in the matrix col j
    private int nextCol(int i, int j)
    {
        int circleI = (i+1) % (lib.options.length + 1);
        while (NodeMatrix[circleI][j] == null)
        {
            circleI = (circleI+1) % (lib.options.length + 1);
        }
        return circleI;
    }

    // given a start Node and the Nodes i,j in Matrix => set up the circular double links in that row
    private void setLinkRight(Node start, int i, int j)
    {
        Node x = NodeMatrix[i][j];
        int cJ = j;
        while (start.L == null)
        {
            int nextJ = next(i,cJ);
            Node next = NodeMatrix[i][nextJ];
            x.R = next;
            next.L = x;
            x = next;
            cJ = nextJ;
        }
    }
    // Given a start Node and the j value in Matrix => set up circular double links in column
    // and if there is no links in the current Nodes row => also set up links for that row
    private void setLinks(Node start, int j)
    {
        Node x = NodeMatrix[0][j];
        int cI = 0;
        while (start.U == null)
        {
            int nextI = nextCol(cI,j);
            Node next = NodeMatrix[nextI][j];
            x.D = next;
            next.U = x;
            if(next != NodeMatrix[0][j])
            {
                next.C = (ColumnNode) NodeMatrix[0][j];
                next.C.S++;
            }
            if (x.R == null) setLinkRight(x,cI,j);
            x = next;
            cI = nextI;

        }
    }

    // Starting at the Initial Node (Matrix[0][0]), go through all column headers and set up links for the whole matrix
    // After links are set up also add the Node h
    private void setupLinks()
    {
        for (int p = 0; p < lib.constraints.length; p++ ) {
            // System.out.println(NodeMatrix[0][p].N);
            setLinks(NodeMatrix[0][p], p);
        }
        NodeMatrix[0][0].L = h;
        h.R = NodeMatrix[0][0];
        NodeMatrix[0][lib.constraints.length - 1].R = h;
        h.L = NodeMatrix[0][lib.constraints.length - 1];
    }
/*
    // test method for filling Matrix with only 3 constraints for 2x2 board
    public void fillCoverMatrix3Constraints(String[] constraints)
    {
        for(int z = 0;z < constraints.length;z++)
        {
            NodeMatrix[0][z] = new Node();
            NodeMatrix[0][z].N = constraints[z];
        }

        for (int i = 1; i < exact.options.length + 1; i++) {
            for (int j = 0; j < exact.constraints.length; j++) {
                String firstHalf = Character.toString(exact.constraints[j].charAt(0)) + exact.constraints[j].charAt(1);
                String secondHalf = exact.constraints[j].charAt(2) + Character.toString(exact.constraints[j].charAt(3));
                if (exact.options[i - 1].contains(firstHalf) && exact.options[i - 1].contains(secondHalf))
                {
                    NodeMatrix[i][j] = new Node();
                    NodeMatrix[i][j].N = Character.toString(exact.constraints[j].charAt(0)) + i;
                }

                else NodeMatrix[i][j] = null;
            }
        }
    }
*/
    // Compare the options against contraints and fill out the intersections of options and constraints
    // if its the first row (z=0) => set the Names of the Nodes to the constraint names as these are the headers
    // for the 3/4 of the constraints we can directly compare the option against cosntraints and fill intersection
    // for the last 1/4 (box constraints) we have to use a helper method (isOptionInBox) to see if the intersection exists
    // that is because we cant directly compare eg: "R1C1" to "B1", the helper method disects B1 to R1C1-R3C3 etc..
    private void fillMatrix(String[] constraints)
    {
            for(int z = 0;z < constraints.length;z++)
            {
                NodeMatrix[0][z] = new ColumnNode();
                ColumnNode cn = (ColumnNode)NodeMatrix[0][z];
                cn.N = constraints[z];
            }

            for (int i = 1; i < lib.options.length + 1; i++) {
                for (int j = 0; j < lib.constraints.length-(lib.constraints.length/4); j++) {
                    String firstHalf = Character.toString(lib.constraints[j].charAt(0)) + lib.constraints[j].charAt(1);
                    String secondHalf = lib.constraints[j].charAt(2) + Character.toString(lib.constraints[j].charAt(3));
                    if (lib.options[i - 1].contains(firstHalf) && lib.options[i - 1].contains(secondHalf))
                    {
                        NodeMatrix[i][j] = new Node();
                    }
                    else NodeMatrix[i][j] = null;
                }
                for (int k = lib.constraints.length-(lib.constraints.length/4); k < lib.constraints.length; k++) {
                    int boxNumber = Character.getNumericValue(lib.constraints[k].charAt(1));
                    String secondHalf = lib.constraints[k].charAt(2) + Character.toString(lib.constraints[k].charAt(3));
                    if (lib.isOptionInBox(lib.options[i - 1],boxNumber) && lib.options[i - 1].contains(secondHalf))
                    {
                        NodeMatrix[i][k] = new Node();
                    }
                    else NodeMatrix[i][k] = null;
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
            //SudokuBoard sb = new SudokuBoard();
            //int[][] board = new int[this.board.length][this.board.length];
            for (Node x : solutionStack)
            {
                String concat = x.C.N + x.R.C.N + x.R.R.C.N + x.R.R.R.C.N;
                int[] temp = convertResultStringToBoardValues(concat);
                // System.out.println(counter++ + " " + x.C.N +  " " + x.R.C.N + " " + x.R.R.C.N +" " + x.R.R.R.C.N);
                this.board[temp[0] - 1][temp[1] - 1] = temp[2];

            }


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
        DancingLinksLib dl = new DancingLinksLib(boards.hardestBoard());
        long startTime = System.currentTimeMillis();
        dl.DLX();
        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime-startTime) + "ms");
        boards.printBoard(dl.board);



    }



}
