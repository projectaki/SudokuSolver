import java.util.Arrays;

public class DancingLinks {
    private final Node[][] NodeMatrix;
    ExactCoverMatrixSudoku exact;

    private class Node {
        private Node L;
        private Node R;
        private Node U;
        private Node D;
        private Node C;
        private String N;
        private int S;

        public Node(Node l, Node r, Node u, Node d, Node c, String n, int s)
        {
            L = l;
            R = r;
            U = u;
            D = d;
            C = c;
            N = n;
            S = s;

        }

        public Node() {

        }
    }



    public DancingLinks()
    {
        SudokuBoard boards = new SudokuBoard();
        int[][] grid = boards.smallTestBoard();
        exact = new ExactCoverMatrixSudoku(grid.length,3,grid);
        exact.fillOptionsBasedOnSudokuBoard();
        exact.fillConstraints();
        NodeMatrix = new Node[exact.options.length + 1][exact.constraints.length];
        fillCoverMatrix3Constraints(exact.constraints);
    }

    public int next(int i, int j)
    {
        int circleJ = (j+1) % exact.constraints.length;
        while (NodeMatrix[i][circleJ] == null)
        {
            circleJ = (circleJ+1) % exact.constraints.length;
        }
        return circleJ;
    }

    public int nextCol(int i, int j)
    {
        int circleI = (i+1) % exact.options.length;
        while (NodeMatrix[circleI][j] == null)
        {
            circleI = (circleI+1) % exact.options.length;
        }
        return circleI;
    }

    public void setLinkRight(Node start, int i, int j)
    {
        Node x = NodeMatrix[i][j];
        int cJ = j;
        while (start.L == null)
        {
            int nextJ = next(i,cJ);
            Node next = NodeMatrix[i][nextJ];
            x.R = next;
            next.L = x;
            if(x != NodeMatrix[0][cJ]) x.C = NodeMatrix[0][cJ];
            x = next;
            cJ = nextJ;
        }
    }

    public void setLinks(Node start, int i, int j)
    {
        Node x = NodeMatrix[i][j];
        int cI = i;
        while (start.U == null)
        {
            int nextI = nextCol(cI,j);
            Node next = NodeMatrix[nextI][j];
            x.D = next;
            next.U = x;
            if(x != NodeMatrix[0][j]) x.C = NodeMatrix[0][j];
            if (x.R == null) setLinkRight(x,cI,j);
            x = next;
            cI = nextI;
        }
    }

    public void setupLinks()
    {
        for (int p = 0;p < exact.constraints.length;p++ ) {
            System.out.println(NodeMatrix[0][p].N);
            setLinks(NodeMatrix[0][p],0,p);
        }
    }

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
                    NodeMatrix[i][j] = new Node();
                else NodeMatrix[i][j] = null;
            }
        }
    }

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
                    if (exact.options[i].contains(firstHalf) && exact.options[i].contains(secondHalf)) NodeMatrix[i][j] = new Node();
                    else NodeMatrix[i][j] = null;
                }
                for (int k = exact.constraints.length-(exact.constraints.length/4); k < exact.constraints.length; k++) {
                    int boxNumber = Character.getNumericValue(exact.constraints[k].charAt(1));
                    String secondHalf = exact.constraints[k].charAt(2) + Character.toString(exact.constraints[k].charAt(3));
                    if (exact.isOptionInBox(exact.options[i],boxNumber) && exact.options[i].contains(secondHalf)) NodeMatrix[i][k] = new Node();
                    else NodeMatrix[i][k] = null;
                }

            }


    }


    public static void main(String[] args) {
        SudokuBoard b = new SudokuBoard();
        ExactCoverMatrixSudoku e = new ExactCoverMatrixSudoku(2,3,b.smallTestBoard());
        DancingLinks dl = new DancingLinks();
        dl.setupLinks();
        for (int i = 0; i < e.options.length + 1;i++ ) {
            for (int j = 0; j < e.constraints.length;j++ ) {
                if (i == 0) System.out.print(dl.NodeMatrix[i][j].N +"|");
                else
                {
                    if (dl.NodeMatrix[i][j] != null)
                    {
                        System.out.print("1   |");
                    }
                    else System.out.print("0   |");
                }

            }
            System.out.println();
        }
        System.out.println(dl.NodeMatrix[0][0].D.R.U.N);

    }








}
