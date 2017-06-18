import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF grid;
    private int[] status;
    private WeightedQuickUnionUF back_water;
    private int len;
    private int count;

    public Percolation(int n) {
    // create n-by-n grid, with all sites blocked
        if (n <= 0) throw new IllegalArgumentException();
        this.grid = new WeightedQuickUnionUF(n*n+2);
        this.status = new int[n*n+2];
        this.back_water = new WeightedQuickUnionUF(n*n+1);
        this.len = n;
        this.count = 0;
        this.status[0] = 1;
        this.status[n*n+1] = 1;
    }

    private void union(int row, int col, int row2, int col2) {
        row--;
        col--;
        row2--;
        col2--;

        int p = row*len+col+1;
        int q = row2*len+col2+1;
        grid.union(p, q);
        back_water.union(p, q);
    }

    private void print_grid() {
        System.out.println("****************************");
        for (int i=0; i<len; i++) {
            for (int j=0; j<len; j++) {
                System.out.print(this.status[i*len+j+1]);
                System.out.print(", ");
            }
            System.out.println("");
        }
    }

    public void open(int row, int col) {
        // open site (row, col) if it is not open already
        if (row <= 0 || row > len || col <= 0 || col > len) throw new IndexOutOfBoundsException();

        if (!isOpen(row, col)) {
            this.status[(row-1)*len+col] = 1;
            this.count++;

            // union up
            if (row-1 > 0 && isOpen(row-1, col)) {
                union(row, col, row-1, col);
            } else if (row-1 == 0) {
                // first row
                grid.union((row-1)*len+col, 0);
                back_water.union((row-1)*len+col, 0);
            }
            // union down
            if (row+1 <= len && isOpen(row+1, col)) {
                union(row, col, row+1, col);
            } else if (row+1 > len) {
                // first row
                grid.union((row-1)*len+col, len*len+1);
            }
            // union left
            if (col-1 > 0 && isOpen(row, col-1)) {
                union(row, col, row, col-1);
            }
            // union right
            if (col+1 <= len && isOpen(row, col+1)) {
                union(row, col, row, col+1);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > len || col <= 0 || col > len) throw new IndexOutOfBoundsException();
        // is site (row, col) open?
        if (this.status[(row-1)*len+col] == 1) return true;
        else return false;
    }

    public boolean isFull(int row, int col) {
        if (row <= 0 || row > len || col <= 0 || col > len) throw new IndexOutOfBoundsException();
        // is site (row, col) full?
        int p = (row-1)*len+col;
        if (row == len) {
            return grid.connected(p, 0) && back_water.connected(p, 0);
        } else {
            return grid.connected(p, 0);
        }
    }

    public int numberOfOpenSites() {      
    // number of open sites
        return count;
    }

    public boolean percolates() {             
    // does the system percolate?
        return grid.connected(0, len*len+1);
    }

    public static void main(String[] args) {  
    // test client (optional)
        Percolation test = new Percolation(3);

        test.open(1,3);
        test.print_grid();
        test.open(2,3);
        test.print_grid();
        test.open(3,3);
        test.print_grid();
        test.open(3,1);
        test.print_grid();
        System.out.println(test.isFull(3,1));
    }
}