import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private int trials;
    private double[] openSitesFraction;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();

        this.trials = trials;
        openSitesFraction = new double[trials];

        int size = n * n;

        for (int i = 0; i != trials; ++i) {
            Percolation percolationModel = new Percolation(n);
            while (!percolationModel.percolates()) {
                percolationModel.open(StdRandom.uniformInt(1, n + 1),
                                      StdRandom.uniformInt(1, n + 1));
            }
            openSitesFraction[i] =
                    (double) percolationModel.numberOfOpenSites() / size;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(openSitesFraction);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(openSitesFraction);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (
                (CONFIDENCE_95 * Math.sqrt(stddev())) / Math.sqrt(trials)
        );
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (
                (CONFIDENCE_95 * Math.sqrt(stddev())) / Math.sqrt(trials)
        );
    }

    // test client
    public static void main(String[] args) {
        PercolationStats percolationStatsModel = new PercolationStats(
                Integer.parseInt(args[0]), Integer.parseInt(args[1])
        );

        StdOut.print("mean                    = ");
        StdOut.println(percolationStatsModel.mean());

        StdOut.print("stddev                  = ");
        StdOut.println(percolationStatsModel.stddev());

        StdOut.print("95% confidence interval = [");
        StdOut.print(percolationStatsModel.confidenceLo());
        StdOut.print(", ");
        StdOut.print(percolationStatsModel.confidenceHi());
        StdOut.println("]");
    }

}

