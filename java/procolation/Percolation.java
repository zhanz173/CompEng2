import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // creates n-by-n grid, with all sites initially blocked
	private final boolean[] opensites;
	private final int dim;
	private final WeightedQuickUnionUF uf;
	private final static int top = 0;
	private final int bottom;
	private int opened = 0;

	
    public Percolation(int n) {
    	if (n<0) throw new IllegalArgumentException("n must be greater than 0.");
    	dim = n;
    	bottom = n*n+1;
    	opensites = new boolean [n*n];
    	uf = new WeightedQuickUnionUF(n*n+2);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
    	if (row>dim || col>dim) throw new IndexOutOfBoundsException(" ");
    	int index = index(row,col);
    	
    	if (!isOpen(row, col)) {
    		opened++;
    		if (row == 1) {
    			uf.union(index, top);
    		}
    		if (row == dim) {
    			uf.union(index, bottom);
    		}
    		if (row>1 && isOpen(row-1, col)) {
    			uf.union(index,index(row-1, col));
    		}
    		if (row<dim && isOpen(row+1, col)) {
    			uf.union(index,index(row+1, col));
    		}
    		if (col<dim && isOpen(row, col+1)) {
    			uf.union(index,index(row, col+1));
    		}
    		if (col>1 && isOpen(row, col-1)) {
    			uf.union(index,index(row, col-1));
    		}
    		opensites[index] = true;
    	}
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
    	return opensites[index(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
    	//if (isInvalid(row, col)) throw new IndexOutOfBoundsException(" ");
    	return uf.connected(row, col);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
    	return opened;
    }

    // does the system percolate?
    public boolean percolates() {
    	return uf.connected(top, bottom);
    }

    // test client (optional)
    public static void main(String[] args) {
    	
    }
    
    private int index(int i, int j) {
    	return (i-1)*dim+j-1;
    }
    
    private boolean isInvalid(int i, int j) {
    	return (i < 1 || i > this.dim || j < 1 || j > this.dim);
    }
}
