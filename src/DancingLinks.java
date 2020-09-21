public class DancingLinks {
    private class Node {
        private Node L;
        private Node R;
        private Node U;
        private Node D;
        private Node C;
    }

    private class ColumnNode extends Node {
        private int size;
        private String Name;
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
        DancingLinks dl = new DancingLinks();
        int[][] test = dl.testMatrix();
        for (int i = 0; i < 6;i++ ) {
            for (int j = 0; j < 7;j++ ) {
                System.out.print(test[i][j] + "  ");
            }
            System.out.println();
        }
    }

















}
