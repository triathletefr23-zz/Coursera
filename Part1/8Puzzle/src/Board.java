import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;

public final class Board {

    private final char[][] blocks;
    private final int dimension;
    private int manhattan;
    private int hamming;
    private boolean areBlocksChanged;
    private int randRow1;
    private int randRow2;
    private int randCol1;
    private int randCol2;
    private boolean isTwin;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        areBlocksChanged = true;
        isTwin = true;
        dimension = blocks.length;
        this.blocks = new char[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.blocks[i][j] = (char) blocks[i][j];
            }
        }

        manhattan();
        hamming();
        areBlocksChanged = false;
    }

    // board dimension n
    public int dimension() {
        return dimension;
    }

    private boolean isEmptyBlock(int i, int j) {
        int value = (int) blocks[i][j];
        return value == 0;
    }

    // number of blocks out of place
    public int hamming() {
        if (!areBlocksChanged) {
            return hamming;
        }
        hamming = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] != i * dimension + j + 1 && !isEmptyBlock(i, j)) {
                    hamming++;
                }
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        if (!areBlocksChanged) {
            return manhattan;
        }
        manhattan = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] != i * dimension + j + 1 && !isEmptyBlock(i, j)) {
                    int iGoal = (blocks[i][j] - 1) / dimension;
                    int jGoal = (blocks[i][j] - 1) % dimension;
                    manhattan += Math.abs(i - iGoal) + Math.abs(j - jGoal);
                }
            }
        }

        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        if (!isEmptyBlock(dimension - 1, dimension - 1)) {
            return false;
        }
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] != i * dimension + j + 1 && !isEmptyBlock(i, j)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isNonValidIndexes(int i, int j) {
        return i < 0 || i >= dimension || j < 0 || j >= dimension;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int[][] twinBlocks = new int[dimension][dimension];
        int dirRow, dirCol;

        if (isTwin) {
            do {
                randRow1 = StdRandom.uniform(dimension);
                randCol1 = StdRandom.uniform(dimension);
                dirRow = StdRandom.uniform(-1, 2);
                dirCol = StdRandom.uniform(-1, 2);
                randRow2 = randRow1 + dirRow;
                randCol2 = randCol1 + dirCol;
            } while (isNonValidIndexes(randRow2, randCol2) || isEmptyBlock(randRow1, randCol1) ||
                    Math.abs(dirRow) == Math.abs(dirCol) || isEmptyBlock(randRow2, randCol2));
            isTwin = false;
        }

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                twinBlocks[i][j] = blocks[i][j];
            }
        }

        twinBlocks = exchangeInt(twinBlocks, randRow1, randCol1, randRow2, randCol2);

        return new Board(twinBlocks);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.dimension != that.dimension()) return false;
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                if (this.blocks[i][j] != that.blocks[i][j]) return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<>();
        int[][] moves = new int[][]{ { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
        int[] empty = findEmptyBlock();

        for (int[] dir : moves) {
            int row = empty[0] + dir[0];
            int col = empty[1] + dir[1];
            if (!isNonValidIndexes(row, col)) {
                int[][] neighborBoard = copyCharToInt(blocks);
                neighborBoard = exchangeInt(neighborBoard, empty[0], empty[1], row, col);
                neighbors.push(new Board(neighborBoard));
            }

        }

        return neighbors;
    }

    private int[] findEmptyBlock() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (isEmptyBlock(i, j))
                    return new int[]{ i, j };
            }
        }
        return new int[]{ dimension - 1, dimension - 1 };
    }

    private int[][] copyCharToInt(char[][] array) {
        int[][] res = new int[array.length][array.length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                res[i][j] = (int) array[i][j];
            }
        }
        return res;
    }

    private static int[][] exchangeInt(int[][] array, int row1, int col1, int row2, int col2) {
        int temp = array[row1][col1];
        array[row1][col1] = array[row2][col2];
        array[row2][col2] = temp;

        return array;
    }

//    private static char[][] exchangeChar(char[][] array, int row1, int col1, int row2, int col2) {
//        char temp = array[row1][col1];
//        array[row1][col1] = array[row2][col2];
//        array[row2][col2] = temp;
//
//        return array;
//    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension).append("\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                s.append(String.format("%2d ", (int) blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        // something
    }
}
