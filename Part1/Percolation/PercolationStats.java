/******************************************************************************
 *  Name:    Pavlo LIAHUSHYN
 *  NetID:   liahus
 *  Precept: P01
 *
 *  Description:  Calculates an average threshold for the system that 
                  percolates.

 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    
    private static final double DEV_CONST = 1.96;

    private double[] thresholdArray;

    private final int countOfTrials;

    private final double meanValue;

    private final double stddevValue;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0) {
            throw new IllegalArgumentException("Size is lower than 0");        
        }

        if (trials <= 0) {
            throw new IllegalArgumentException("Number of trials is lower than 0");
        }

        thresholdArray = new double[trials];
        countOfTrials = trials;

        executePercolation(n);
        meanValue = StdStats.mean(thresholdArray);
        stddevValue = StdStats.stddev(thresholdArray);
    }
    
    // sample mean of percolation threshold
    public double mean() {        
        return meanValue;
    }
    
    // sample standard deviation of percolation threshold
    public double stddev() {        
        return stddevValue;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return meanValue - (DEV_CONST * stddevValue) / Math.sqrt(countOfTrials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return meanValue + (DEV_CONST * stddevValue) / Math.sqrt(countOfTrials);
    }

    // run Percolation emulator
    private void executePercolation(int n) {        
        int row = 0, col = 0;
        Percolation perc;
        for (int i = 0; i < thresholdArray.length; i++) {
            perc = new Percolation(n);
            while (!perc.percolates()) {
                
                while (true) {
                    int newRow = StdRandom.uniform(1, n + 1);
                    int newCol = StdRandom.uniform(1, n + 1);
                    if (!perc.isOpen(newRow, newCol)) {
                        row = newRow;
                        col = newCol;
                        break;
                    }
                }            

                perc.open(row, col);
            }

            thresholdArray[i] = perc.numberOfOpenSites() / (double) (n * n);
        }
    }
 
    // test client (described below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        
        PercolationStats stats = new PercolationStats(n, trials);

        StdOut.printf("mean                    = %10.3f\n", stats.mean());
        StdOut.printf("stddev                  = %10.3f\n", stats.stddev());
        StdOut.printf("low                     = %10.3f\n", stats.confidenceLo());
        StdOut.printf("high                    = %10.3f\n", stats.confidenceHi());
    }
 }