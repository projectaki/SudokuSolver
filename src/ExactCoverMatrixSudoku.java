import java.util.Arrays;

public class ExactCoverMatrixSudoku {

    private final int n;
    private final int constraintNumber;
    public String[] constraints;
    public String[] options;
    private final String R = "R";
    private final String C = "C";
    private final String B = "B";
    private final String Hash = "#";
    public final int[][] coverMatrix;
    private final int[][] sudokuBoard;

    public ExactCoverMatrixSudoku(int n, int constraintNumber,int[][] grid)
    {
        this.n = n;
        // 4 constraints on sudoku (1 number per cell, 1 of each number/row, 1 of each number/col,1 of each number/box)
        this.constraintNumber = constraintNumber;
        // constraint number is equal to the sum of the (sum of the 4 constraints) 1 constraint has n*n options
        // therefore it is n*n*numberOfConstraints
        constraints = new String[n*n*constraintNumber];
        // n numbers in every box (nxn boxes) so n*n*n
        options = new String[SudokuBoard.optionSize(grid,grid.length)];
        // Cover Matrix with filled 1 or 0 values
        coverMatrix = new int[options.length][constraints.length];
        // Init sudoku board
        sudokuBoard = grid;
    }
/*
    public void fillOptionsEmptySudoku()
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
*/
    public void fillOptionsBasedOnSudokuBoard()
    {
        int counter = 0;
        for (int p = 0; p < sudokuBoard.length; p++) {
            for (int s = 0; s < sudokuBoard.length; s++) {
                if (sudokuBoard[p][s] !=0) options[counter++] =
                        R +  "" + (p+1) + C + "" + (s+1) + "" + Hash + "" + sudokuBoard[p][s];
                else
                {
                    for (int i = 0; i < n; i++) {
                        options[counter++] =
                                R +  "" + (p+1) + C + "" + (s+1) + "" + Hash + "" + (i+1);
                    }
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
        /*
        // Box constraint B1#1
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                constraints[counter++] = B + "" + (i+1) + "" + Hash + "" + (j+1);
                }
            }
*/

    }

    public void fill4constraints()
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

    public boolean isOptionInBox(String option, int numb)
    {
        boolean cont = false;
        String[] temp = mapBoxToRowCol();
        for (int i = (numb-1)*n; i < (numb-1)*n + n; i++) {
            String firstHalf = Character.toString(temp[i].charAt(0)) + temp[i].charAt(1);
            String secondHalf = temp[i].charAt(2) + Character.toString(temp[i].charAt(3));
            if (option.contains(firstHalf) && option.contains(secondHalf)) cont = true;

        }
        return cont;
    }

    private String[] mapBoxToRowCol()
    {
        int counter = 0;
        String[] boxes = new String[n*n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i % Math.sqrt(n) == 0 && j % Math.sqrt(n) == 0)
                {
                    for (int k = i ; k < i + Math.sqrt(n); k++) {
                        for (int l = j ; l < j + Math.sqrt(n); l++) {
                            boxes[counter++] = R + "" + (k + 1) + "" + C + "" + (l + 1);
                        }
                    }
                }

            }
        }
        return boxes;
    }

    public void fillCoverMatrix4Constraints()
    {
        for (int i = 0; i < options.length; i++) {
            for (int j = 0; j < constraints.length-(constraints.length/4); j++) {
                String firstHalf = Character.toString(constraints[j].charAt(0)) + constraints[j].charAt(1);
                String secondHalf = constraints[j].charAt(2) + Character.toString(constraints[j].charAt(3));
                if (options[i].contains(firstHalf) && options[i].contains(secondHalf)) coverMatrix[i][j] = 1;
                else coverMatrix[i][j] = 0;
            }
            for (int k = constraints.length-(constraints.length/4); k < constraints.length; k++) {
                int boxNumber = Character.getNumericValue(constraints[k].charAt(1));
                String secondHalf = constraints[k].charAt(2) + Character.toString(constraints[k].charAt(3));
                if (isOptionInBox(options[i],boxNumber) && options[i].contains(secondHalf)) coverMatrix[i][k] = 1;
                else coverMatrix[i][k] = 0;
            }

        }
    }

    public void fillCoverMatrix3Constraints()
    {
        for (int i = 0; i < options.length; i++) {
            for (int j = 0; j < constraints.length; j++) {
                String firstHalf = Character.toString(constraints[j].charAt(0)) + constraints[j].charAt(1);
                String secondHalf = constraints[j].charAt(2) + Character.toString(constraints[j].charAt(3));
                if (options[i].contains(firstHalf) && options[i].contains(secondHalf)) coverMatrix[i][j] = 1;
                else coverMatrix[i][j] = 0;
            }


        }
    }



    public static void main(String[] args) {
        SudokuBoard boards = new SudokuBoard();
        ExactCoverMatrixSudoku X = new ExactCoverMatrixSudoku(9,4,boards.hardestBoard());

        X.fillOptionsBasedOnSudokuBoard();
        X.fill4constraints();
        X.fillCoverMatrix4Constraints();

        System.out.println(Arrays.toString(X.options));
        System.out.println(Arrays.toString(X.constraints));
        System.out.println(X.options.length);
        //System.out.println(Arrays.toString(X.constraints));
        System.out.println(X.constraints.length);
        // System.out.println(Arrays.toString(X.mapBoxToRowCol()));
        // System.out.println(X.isOptionInBox("R4C1#2", 4));
/*
        for (int i = 0; i < X.options.length; i++) {
            for (int j = 0; j < X.constraints.length; j++) {
                System.out.print(X.coverMatrix[i][j]);

            }
            System.out.println();
        }
*/


    }







}
