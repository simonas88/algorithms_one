import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  //  public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
  //  public double mean()                          // sample mean of percolation threshold
  //  public double stddev()                        // sample standard deviation of percolation threshold
  //  public double confidenceLo()                  // low  endpoint of 95% confidence interval
  //  public double confidenceHi()                  // high endpoint of 95% confidence interval

  //  public static void main(String[] args)        // test client (described below)

  private int trials;
  private double[] percolationRatio;
  private double mean;
  private double stddev;
  private double confidenceLo;
  private double confidenceHi;

  public PercolationStats(int n, int trials) {
    if (n <= 0 || trials <= 0) {
      throw new java.lang.IllegalArgumentException();
    }

    this.trials = trials;
    this.percolationRatio = new double[trials];

    for (int i = 0; i < trials; i++) {
      Percolation percolation = new Percolation(n);
      while (!percolation.percolates()) {
        int row = StdRandom.uniform(1, n + 1);
        int col = StdRandom.uniform(1, n + 1);

        percolation.open(row, col);
      }

      this.percolationRatio[i] = (double) percolation.numberOfOpenSites() / (n * n);
    }

    this.mean = StdStats.mean(this.percolationRatio);
    this.stddev = StdStats.stddev(this.percolationRatio);
    this.confidenceLo = this.mean - 1.96 * (this.stddev / Math.sqrt(this.trials));
    this.confidenceHi = this.mean + 1.96 * (this.stddev / Math.sqrt(this.trials));
  }

  public static void main(String[] args) {
    PercolationStats stats = new PercolationStats(
      java.lang.Integer.parseInt(args[0]),
      java.lang.Integer.parseInt(args[1])
    );

    System.out.println("mean\t\t\t= " + stats.mean());
    System.out.println("stddev\t\t\t= " + stats.stddev());
    System.out.println(
      "95% confidence interval\t= ["
      + stats.confidenceLo()
      + ", "
      + stats.confidenceHi()
      + "]"
    );
  }

  public double mean() {
    return this.mean;
  }

  public double stddev() {
    return this.stddev;
  }

  public double confidenceLo() {
    return this.confidenceLo;
  }

  public double confidenceHi() {
    return this.confidenceHi;
  }
}