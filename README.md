# Sudoku solver project
Nowadays the brute solver is plenty enough for solving most 9x9 sudokus in less than a second, but it is quite simple to make, and not so fancy. On the other hand Donald Knuth came up with a solution for how this problem can be turned into an exact cover problem, and this method is much more complicated than the brute force. This project is my implementation of the Brute force solver, and the Dancing Links method. The Dancing Links method is faster than the brute force, but the speed is only significant when we look at bigger dimension boards.

## Brute force solution

The brute force solver is a backtracking algorithm which tries every path until its valid, then backtracks if rules are violated. Works very fast for most boards, but can be slower for boards which were generated against this algorithm (having to take the longest possible path).

## Dancing Links Solution
 
The dancing links solution uses the Exact cover problem to solve the puzzle. It makes a cover matrix of 0s and 1s, with options, and constraint sets. Then an exact cover is found from the rows of options. The algorithm to find the exact cover is Donald Knuths Algorithm X, and the Data Structure for representing the cover matrix is a quadruply linked and circular list, which is also Donald Knuths technique.

## How to use
The input for the algorithms are 2D arrays representing the sudoku board with clues. (0s at non-given places, and the number (1-n) at given positions). 
