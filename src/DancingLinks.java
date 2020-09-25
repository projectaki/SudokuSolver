import java.util.Stack;

public class DancingLinks {
    private final Node[][] NodeMatrix;
    ExactCoverMatrixSudoku exact;
    private final Node h;
    private final Stack<Node> solutionStack;
    private final int[][] board;

    private static class Node {
        private Node L;
        private Node R;
        private Node U;
        private Node D;
        private Node C;
        private String N;
        private int S;


        public Node() {

        }
    }



    public DancingLinks(int[][] board)
    {
        solutionStack = new Stack<>();
        h = new Node();
        this.board = board;
        exact = new ExactCoverMatrixSudoku(this.board.length,4,this.board);
        exact.fillOptionsBasedOnSudokuBoard();
        exact.fill4constraints();
        NodeMatrix = new Node[exact.options.length + 1][exact.constraints.length];
        fillMatrix(exact.constraints);
        setupLinks(h);
        search();
    }
/*
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

    private int next(int i, int j)
    {
        int circleJ = (j+1) % exact.constraints.length;
        while (NodeMatrix[i][circleJ] == null)
        {
            circleJ = (circleJ+1) % exact.constraints.length;
        }
        return circleJ;
    }

    private int nextCol(int i, int j)
    {
        int circleI = (i+1) % (exact.options.length + 1);
        while (NodeMatrix[circleI][j] == null)
        {
            circleI = (circleI+1) % (exact.options.length + 1);
        }
        return circleI;
    }

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
                next.C = NodeMatrix[0][j];
                NodeMatrix[0][j].S++;
            }
            if (x.R == null) setLinkRight(x,cI,j);
            x = next;
            cI = nextI;

        }
    }

    private void setupLinks(Node h)
    {
        for (int p = 0;p < exact.constraints.length;p++ ) {
            // System.out.println(NodeMatrix[0][p].N);
            setLinks(NodeMatrix[0][p], p);
        }
        NodeMatrix[0][0].L = h;
        h.R = NodeMatrix[0][0];
        NodeMatrix[0][exact.constraints.length - 1].R = h;
        h.L = NodeMatrix[0][exact.constraints.length - 1];
    }
/*
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
    private void fillMatrix(String[] constraints)
    {
            for(int z = 0;z < constraints.length;z++)
            {
                NodeMatrix[0][z] = new Node();
                NodeMatrix[0][z].N = constraints[z];
            }

            for (int i = 1; i < exact.options.length + 1; i++) {
                for (int j = 0; j < exact.constraints.length-(exact.constraints.length/4); j++) {
                    String firstHalf = Character.toString(exact.constraints[j].charAt(0)) + exact.constraints[j].charAt(1);
                    String secondHalf = exact.constraints[j].charAt(2) + Character.toString(exact.constraints[j].charAt(3));
                    if (exact.options[i - 1].contains(firstHalf) && exact.options[i - 1].contains(secondHalf))
                    {
                        NodeMatrix[i][j] = new Node();
                        NodeMatrix[i][j].N = Character.toString(exact.constraints[j].charAt(0)) + i;
                    }
                    else NodeMatrix[i][j] = null;
                }
                for (int k = exact.constraints.length-(exact.constraints.length/4); k < exact.constraints.length; k++) {
                    int boxNumber = Character.getNumericValue(exact.constraints[k].charAt(1));
                    String secondHalf = exact.constraints[k].charAt(2) + Character.toString(exact.constraints[k].charAt(3));
                    if (exact.isOptionInBox(exact.options[i - 1],boxNumber) && exact.options[i - 1].contains(secondHalf))
                    {
                        NodeMatrix[i][k] = new Node();
                        NodeMatrix[i][k].N = Character.toString(exact.constraints[k].charAt(0)) + i;
                    }
                    else NodeMatrix[i][k] = null;
                }

            }


    }

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

    private Node chooseColumn()
    {
        Node c = h.R;
        Node j = h.R;
        double s = Double.POSITIVE_INFINITY;
        while (j != h)
        {
            if (j.S < s)
            {
                c = j;
                s = j.S;
            }
            j = j.R;
        }
        return c;
    }

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

    private void printNodeMatrix()
    {
        for (int i = 0; i < exact.options.length + 1;i++ ) {
            for (int j = 0; j < exact.constraints.length;j++ ) {
                if (i == 0) System.out.print(NodeMatrix[i][j].N +"|");
                else
                {
                    if (NodeMatrix[i][j] != null)
                    {
                        System.out.print(NodeMatrix[i][j].N + "  |");
                    }
                    else System.out.print("0   |");
                }

            }
            System.out.println();
        }
    }


    public static void main(String[] args) {

        SudokuBoard boards = new SudokuBoard();
        DancingLinks dl = new DancingLinks(boards.hardestBoard());
        boards.printBoard(dl.board);



    }



}
