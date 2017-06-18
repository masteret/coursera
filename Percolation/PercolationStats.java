import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] count_list;
    private int trial_c;

    public PercolationStats(int n, int trials) {
        // perform trials independent experiments on an n-by-n grid
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        this.count_list = new double[trials];
        this.trial_c = trials;
        for (int i = 0; i < trials; i++) {
            int[] used = new int[n*n];
            Percolation test = new Percolation(n);
            double count = 0;
            while (!test.percolates()) {
                int num = StdRandom.uniform(n*n);
                if (used[num] == 0) {
                    int row = num/n+1;
                    int col = num%n+1;
                    test.open(row, col);
                    count++;
                    used[num] = 1;
                }
            }
            count_list[i] = count/(n*n);
        }
    }
    public double mean() {
        // sample mean of percolation threshold
        return StdStats.mean(count_list);
    }                        
    public double stddev() {
        // sample standard deviation of percolation threshold
        return StdStats.stddev(count_list);
    }                       
    public double confidenceLo() {
        // low  endpoint of 95% confidence interval
        return StdStats.mean(count_list) - 1.96*StdStats.stddev(count_list)/Math.sqrt(trial_c);
    }                 
    public double confidenceHi() {
        // high endpoint of 95% confidence interval
        return StdStats.mean(count_list) + 1.96*StdStats.stddev(count_list)/Math.sqrt(trial_c);
    }                 

    public static void main(String[] args) {
        // test client (described below)
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats test = new PercolationStats(n, trials);
        System.out.println("mean = " + test.mean());
        System.out.println("stddev = " + test.stddev());
        System.out.println("95% confidence interval = [" + test.confidenceLo() + ", " + test.confidenceHi() + "]");
    }       
}