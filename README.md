# Sudoku solver project

## Brute force solution

The brute force solver is a backtracking algorithm which tries every path until its valid, then backtracks if rules are violated. Works very fast for most boards, but can be slower for boards which were generated against this algorithm (having to take the longest possible path).

## Dancing Links Solution
 
The dancing links solution uses the Exact cover problem to solve the puzzle. It makes a cover matrix of 0s and 1s, with options, and constraint sets. Then an exact cover is found from the rows of options. The algorithm to find the exact cover is Donald Knuths Algorithm X, and the Data Structure for representing the cover matrix is a quadruply linked and circular list, which is also Donald Knuths technique.
