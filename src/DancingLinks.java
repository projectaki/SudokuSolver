public class DancingLinks {
    private String[] columnNames;
    ColumnNode h;
    ColumnNode lastColumnNode;

    private class Node {
        private Node L;
        private Node R;
        private Node U;
        private Node D;
        private Node C;

        public Node(Node l, Node r, Node u, Node d, Node c)
        {
            L = l;
            R = r;
            U = u;
            D = d;
            C = c;
        }
    }

    private class ColumnNode {
        private ColumnNode L;
        private ColumnNode R;
        private ColumnNode U;
        private ColumnNode D;
        private ColumnNode C;
        private int size;
        private String Name;

        public ColumnNode(ColumnNode l, ColumnNode r, ColumnNode u,
                          ColumnNode d, ColumnNode c, int s, String name)
        {
            L = l;
            R = r;
            U = u;
            D = d;
            C = c;
            size = s;
            Name = name;
        }
        public ColumnNode()
        {

        }
    }

    public DancingLinks(String[] columnNames)
    {
        this.columnNames = columnNames;
        h = new ColumnNode(null,null,null,null,null,0,"");
        lastColumnNode = new ColumnNode(null,null,null,null,null,0,"");

    }

    public void insertHeader(String name)
    {
        ColumnNode x = new ColumnNode();
        if (h.R == null)
        {
            h.R = x;
            h.L = x;
            x.L = h;
            x.R = h;

        }
        else
        {
            lastColumnNode.R = x;
            x.R = h;
            h.L = x;
            x.L = lastColumnNode;
        }
        x.Name = name;
        x.size = 0;
        lastColumnNode = x;
    }

    private int[][] testMatrix()
    {
        int[][] matrix = new int[6][7];
        matrix[0][0] = 0;
        matrix[0][1] = 0;
        matrix[0][2] = 1;
        matrix[0][3] = 0;
        matrix[0][4] = 1;
        matrix[0][5] = 1;
        matrix[0][6] = 0;
        matrix[1][0] = 1;
        matrix[1][1] = 0;
        matrix[1][2] = 0;
        matrix[1][3] = 1;
        matrix[1][4] = 0;
        matrix[1][5] = 0;
        matrix[1][6] = 1;
        matrix[2][0] = 0;
        matrix[2][1] = 1;
        matrix[2][2] = 1;
        matrix[2][3] = 0;
        matrix[2][4] = 0;
        matrix[2][5] = 1;
        matrix[2][6] = 0;
        matrix[3][0] = 1;
        matrix[3][1] = 0;
        matrix[3][2] = 0;
        matrix[3][3] = 1;
        matrix[3][4] = 0;
        matrix[3][5] = 0;
        matrix[3][6] = 0;
        matrix[4][0] = 0;
        matrix[4][1] = 1;
        matrix[4][2] = 0;
        matrix[4][3] = 0;
        matrix[4][4] = 0;
        matrix[4][5] = 0;
        matrix[4][6] = 1;
        matrix[5][0] = 0;
        matrix[5][1] = 0;
        matrix[5][2] = 0;
        matrix[5][3] = 1;
        matrix[5][4] = 1;
        matrix[5][5] = 0;
        matrix[5][6] = 1;
        return matrix;
    }

    public static void main(String[] args) {
        String[] cn = {"A","B","C","D","E","F","G"};
        DancingLinks dl = new DancingLinks(cn);
        for (int i = 0; i < cn.length; i++) {
            dl.insertHeader(cn[i]);
        }

        System.out.println(dl.h.L.Name);




        /*
        int[][] test = dl.testMatrix();
        for (int i = 0; i < 6;i++ ) {
            for (int j = 0; j < 7;j++ ) {
                System.out.print(test[i][j] + "  ");
            }
            System.out.println();
        }
        */

    }

















}
