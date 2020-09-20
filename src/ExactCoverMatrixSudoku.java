import java.util.Arrays;

public class ExactCoverMatrixSudoku {

    private final int n;
    private final int constraintNumber;
    private String[] constraints;
    private String[] options;
    private final String R = "R";
    private final String C = "C";
    private final String B = "B";
    private final String Hash = "#";
    private final int[][] coverMatrix;

    public ExactCoverMatrixSudoku(int n, int constraintNumber)
    {
        this.n = n;
        // 4 constraints on sudoku (1 number per cell, 1 of each number/row, 1 of each number/col,1 of each number/box)
        this.constraintNumber = constraintNumber;
        // constraint number is equal to the sum of the (sum of the 4 constraints) 1 constraint has n*n options
        // therefore it is n*n*numberOfConstraints
        constraints = new String[n*n*constraintNumber];
        // n numbers in every box (nxn boxes) so n*n*n
        options = new String[n*n*n];
        // Cover Matrix with filled 1 or 0 values
        coverMatrix = new int[options.length][constraints.length];
    }

    public void fillOptions()
    {
        int counter = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    options[counter++] = R +  "" + (i+1) + C + "" + (j+1) + "" + Hash + "" + (k+1);
                }
            }
        }
    }

    public void fillConstraints()
    {
        int counter = 0;
        // Cell constraint R1C1.....
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                constraints[counter++] = R + "" + (i+1) + "" + C + "" + (j+1);
            }
        }
        // Row constraint R1#1
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                constraints[counter++] = R + "" + (i+1) + "" + Hash + "" + (j+1);
            }
        }
        // Col constraint C1#1
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                constraints[counter++] = C + "" + (i+1) + "" + Hash + "" + (j+1);
            }
        }
        // Box constraint B1#1
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                constraints[counter++] = B + "" + (i+1) + "" + Hash + "" + (j+1);
            }
        }
    }

    public void fillCoverMatrix()
    {
        for (int i = 0; i < options.length; i++) {
            for (int j = 0; j < constraints.length; j++) {
                String firstHalf = Character.toString(constraints[j].charAt(0)) + Character.toString(constraints[j].charAt(1));
                String secondHalf = Character.toString(constraints[j].charAt(2)) + Character.toString(constraints[j].charAt(3));;
                if (options[i].contains(firstHalf) && options[i].contains(secondHalf)) coverMatrix[i][j] = 1;
                else coverMatrix[i][j] = 0;
            }
        }
    }

    public static void main(String[] args) {
        ExactCoverMatrixSudoku X = new ExactCoverMatrixSudoku(9,4);

        X.fillOptions();
        X.fillConstraints();
        X.fillCoverMatrix();

        //System.out.println(Arrays.toString(X.options));
        //System.out.println(X.options.length);
        //System.out.println(Arrays.toString(X.constraints));
        //System.out.println(X.constraints.length);
        for (int i = 0; i < X.options.length; i++) {
            for (int j = 0; j < X.constraints.length; j++) {
                System.out.print(X.coverMatrix[i][j]);

            }
            System.out.println();
        }

    }







}