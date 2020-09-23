public class TestDancing {
    private Node[][] NodeMatrix;
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

    public TestDancing()
    {
        NodeMatrix = new Node[5][4];
        NodeMatrix[0][0] = new Node();
        NodeMatrix[0][1] = new Node();
        NodeMatrix[0][2] = new Node();
        NodeMatrix[0][3] = new Node();
        NodeMatrix[1][1] = new Node();
        NodeMatrix[1][3] = new Node();
        NodeMatrix[2][0] = new Node();
        NodeMatrix[3][0] = new Node();
        NodeMatrix[3][2] = new Node();
        NodeMatrix[4][1] = new Node();
        NodeMatrix[4][2] = new Node();

        NodeMatrix[0][0].N = "A";
        NodeMatrix[0][1].N = "B";
        NodeMatrix[0][2].N = "C";
        NodeMatrix[0][3].N = "D";
        NodeMatrix[1][1].N = "B1";
        NodeMatrix[1][3].N = "D1";
        NodeMatrix[2][0].N = "A2";
        NodeMatrix[3][0].N = "A3";
        NodeMatrix[3][2].N = "C3";
        NodeMatrix[4][1].N = "B4";
        NodeMatrix[4][2].N = "C2";

    }

    public int next(int i, int j)
    {
        int circleJ = (j+1) % 4;
        while (NodeMatrix[i][circleJ] == null)
        {
            circleJ = (circleJ+1) % 4;
        }
        return circleJ;
    }

    public int nextCol(int i, int j)
    {
        int circleI = (i+1) % 5;
        while (NodeMatrix[circleI][j] == null)
        {
            circleI = (circleI+1) % 5;
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

    public static void main(String[] args) {
        TestDancing t = new TestDancing();
        //System.out.println(t.nextCol(0,0));
        //System.out.println(t.NodeMatrix[1][0]);

        for (int p = 0;p < 4 ;p++ ) {
            t.setLinks(t.NodeMatrix[0][p],0,p);
        }

        // t.setLinkRight(t.NodeMatrix[0][0],0,0);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                if (t.NodeMatrix[i][j] == null) System.out.print("  ");
                else System.out.print(t.NodeMatrix[i][j].N + "  ");
            }
            System.out.println();
        }

        System.out.println(t.NodeMatrix[0][0].D.R.D.R.R.D.N);
    }


}
