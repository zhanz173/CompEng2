
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private final double[] results;
	
	public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException(" ");
        }        
        results = new double[trials];
        for (int i = 0; i < trials; i++) {
        	results[i] = runExperiment(n);
        }
	}
	
	private double runExperiment(int size) {
		Percolation p = new Percolation(size);
		do {
			int i = StdRandom.uniform(1, size + 1);
			int j = StdRandom.uniform(1, size + 1);
			p.open(i, j);
		}while(!p.percolates());
		
		return (double) p.numberOfOpenSites() / (double) (size*size);
	}
	
	public double mean() {
		return StdStats.mean(this.results);
	}
	
    // sample standard deviation of percolation threshold
    public double stddev() {
    	return StdStats.stddev(this.results);
    }
    
    private double confidence() {
        return (1.96 * stddev() / Math.sqrt(results.length));
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
    	return mean() - confidence();
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
    	return mean() + confidence();
    }

   // test client (see below)
   public static void main(String[] args) {

	      int n = Integer.parseInt(args[0]);

	      int testCase = Integer.parseInt(args[1]);

	      PercolationStats tester = new PercolationStats(n, testCase);

	      System.out.format("mean                    = %f\n", tester.mean());

	      System.out.format("stddev                  = %f\n", tester.stddev());

	      System.out.format("95%% confidence interval = [%f, %f]\n", tester.confidenceLo(), tester.confidenceHi());
   }
}
