import java.util.ArrayList;
import java.util.List;

/******************************************************************************
 *  Name:    Pavlo LIAHUSHYN
 *  NetID:   liahus
 *  Precept: P01
 *
 *  Description:  Model an n-by-n percolation system using the union-find
 *                data structure.
 ******************************************************************************/

// import edu.princeton.cs.algs4.StdOut;
// import edu.princeton.cs.algs4.StdRandom;
// import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // n-by-n grid
    private byte[][] grid;

    private static final byte CLOSED = 1;

    private static final byte OPEN = 2;

    private static final byte CONNECTED_TO_TOP = 3;
    
    private static final byte CONNECTED_TO_BOTTOM = 4;
    
    private final WeightedQuickUnionUF uf;

    private final int virtualBottomIndex;

    private int numberOfOpenSites;    

    // create n-by-n grid, with all sites blocked
    // create UF strucure with N*N + 2 elements for virtual top
    // and bottom elements
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Size of the grid is non-valid");
        }

        // we have to add a virtual top site and a virtual bottom site
        uf = new WeightedQuickUnionUF(n * n + 2);
        virtualBottomIndex = n * n + 1;
        numberOfOpenSites = 0;

        grid = new byte[n][n];
        for (int i = 0; i < n; i++) {
            grid[i] = new byte[n];
            uf.union(i, virtualBottomIndex - 1);
            int lastLineIndex = getIndex(grid.length, i + 1);
            uf.union(lastLineIndex, virtualBottomIndex);
            for (int j = 0; j < n; j++) {
                grid[i][j] = CLOSED;
            }
        }

        // markConnected();
        // show();
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        checkArgument(row);
        checkArgument(col);

        int currIndex = getIndex(row, col);
        
        if (grid[row-1][col-1] == CLOSED) {
            numberOfOpenSites++;
        }        

        if (row == 1) { 
            grid[row - 1][col - 1] = CONNECTED_TO_TOP;
            return;
        }

        if (row == grid.length) grid[row - 1][col - 1] = CONNECTED_TO_BOTTOM;

        byte openedStatus = OPEN;
        int openedIndex;

        if (col != 1 && isOpen(row, col - 1)) {
            openedStatus = chooseStatus(openedStatus, grid[row - 1][col - 2]);
            openedIndex = getIndex(row, col - 1);
            uf.union(currIndex, openedIndex);
        }
        if (row != 1 && isOpen(row - 1, col)) {
            openedStatus = chooseStatus(openedStatus, grid[row - 2][col - 1]);
            openedIndex = getIndex(row - 1, col);
            uf.union(currIndex, openedIndex);
        }
        if (col != grid.length && isOpen(row, col + 1)) {
            openedStatus = chooseStatus(openedStatus, grid[row - 1][col]);
            openedIndex = getIndex(row, col + 1);
            uf.union(currIndex, openedIndex);
        }
        if (row != grid.length && isOpen(row + 1, col)) {
            openedStatus = chooseStatus(openedStatus, grid[row][col - 1]);
            openedIndex = getIndex(row + 1, col);
            uf.union(currIndex, openedIndex);
        }

        grid[row - 1][col - 1] = openedStatus;
    }

    private byte chooseStatus(byte first, byte second) {        
        return Byte.compare(first, second) > 0 ? first : second;
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkArgument(row);
        checkArgument(col);

         return grid[row - 1][col - 1] == OPEN || 
                grid[row - 1][col - 1] == CONNECTED_TO_TOP ||
                grid[row - 1][col - 1] == CONNECTED_TO_BOTTOM;
    }

    // is site (row, col) full? (connected to top)
    public boolean isFull(int row, int col) {
        checkArgument(row);
        checkArgument(col);
        
        // int currIndex = getIndex(row, col);
        // StdOut.println("Connected to top " + uf.connected(currIndex, virtualBottomIndex - 1));
        
        return grid[row - 1][col - 1] == CONNECTED_TO_TOP;
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        if ((uf.connected(virtualBottomIndex - 1, virtualBottomIndex) && grid.length != 1) ||
            (isOpen(1, 1) && grid.length == 1)) {
            return true;
        }
        return false;
    }

    // check an argument on IllegalArgumentException
    private void checkArgument(int arg) {
        if (arg < 1 || arg > grid.length) {
            throw new IllegalArgumentException("Index " + arg + " is non possible");
        }
    }

    // getting an index in UF structure
    private int getIndex(int row, int col) {
        return grid.length * (row - 1) + (col - 1);
    }

    private void show() {
        for (int i = 0; i < grid.length; i++) {
            StdOut.print("|");
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j] == CONNECTED_TO_TOP) {
                    StdOut.print("X|");
                }
                else if (grid[i][j] == CONNECTED_TO_BOTTOM || grid[i][j] == OPEN) {
                    StdOut.print("O|");
                }
                else {
                    StdOut.print(" |");
                }
            }
            StdOut.print("\n");
        }
    }    

    // private void markConnected() {
    //     for (int i = 0; i < grid.length; i++) {
    //         for (int j = 0; j < grid.length; j++) {
    //             int index = getIndex(i + 1, j + 1);
    //             if (uf.connected(index, virtualBottomIndex - 1) || uf.connected(index, virtualBottomIndex)) {
    //                 grid[i][j] = true;
    //             }
    //         }
    //     }
    // }

    // test client (optional)
    public static void main(String[] args) {
        // int n = Integer.parseInt(args[0]);
        
        int n = 0;
        
        List<Pair> list = new ArrayList<Pair>();
        try {
            In in = new In(args[0]);
            n = Integer.parseInt(in.readLine());
            StdOut.println("Size : " + n);

            while (!in.isEmpty()) {
                String[] nums = in.readLine().replaceAll("(^\\s+|\\s+$)", "").split("\\s+");
                //StdOut.println("First " + nums[0]);
                //StdOut.println("Second " + nums[1]);
                
                list.add(new Pair(Integer.parseInt(nums[0]), Integer.parseInt(nums[1])));
            }
        }
        catch (IllegalArgumentException e) {
            StdOut.println(e);
        }

        Percolation perc = new Percolation(n);

        int i = 1;
        for (Pair pair: list) {
            int row = pair.X;
            int col = pair.Y;
            perc.open(row, col);

            StdOut.println(i + ") (" + row + ", " + col + ")");
            StdOut.println("Open " + perc.isOpen(row, col));
            StdOut.println("Percolates " + perc.percolates());
            StdOut.println("NumberOfOpenSites " + perc.numberOfOpenSites());
            StdOut.println("Full " + perc.isFull(row, col));

            perc.show();
            i++;
        }

        // StdOut.println("Res first " + list.get(0).X);
        // StdOut.println("Res second " + list.get(0).Y);

        // Percolation perc = new Percolation(n);

        // do {
        //     int row = 1, col = 1;
            
        //     if (n != 1) {
        //         row = StdRandom.uniform(1, n + 1);
        //         col = StdRandom.uniform(1, n + 1);
        //     }
            
        //     if (!perc.isOpen(row, col)) perc.open(row, col);
        // }
        // while (!perc.percolates());

        // StdOut.println("Number of open sites is " + perc.numberOfOpenSites() + " for " + (n * n));
    }
}