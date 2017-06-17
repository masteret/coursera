// import java.util.Arrays;

public class Percolation {
    private int[] grid;
    private int[] status;
    private int len;
    private int count;

    public Percolation(int n) {
    // create n-by-n grid, with all sites blocked
        if (n <= 0) throw new IllegalArgumentException();
        this.grid = new int[n*n+1];
        this.status = new int[n*n+1];
        this.len = n;
        this.count = 0;

        for (int i = 0; i < n*n+1; i++) {
            this.grid[i] = i;
            this.status[i] = 0;
        }
        // for (int i = 1; i <= n; i++) {
        //     this.grid[i] = 0;
        // }

    }

    private int root(int row, int col) {
        int i = row*len+col+1;
        while (i != grid[i]) {
            i = grid[i];
        }
        if (i < len && status[i] == 1) {
            return 0;
        }
        return grid[i];
    }

    private void union(int row, int col, int row2, int col2) {
        row--;
        col--;
        row2--;
        col2--;
        if (grid[root(row, col)] == 0) {
            grid[root(row2, col2)] = grid[root(row, col)];
        } else {
            grid[root(row, col)] = grid[root(row2, col2)];
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
                grid[(row-1)*len+col] = 0;
            }
            // union down
            if (row+1 <= len && isOpen(row+1, col)) {
                union(row, col, row+1, col);
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
        if (root(row-1, col-1) == 0 && status[(row-1)*len+col] == 1) return true;
        else return false;
    }

    public int numberOfOpenSites() {      
    // number of open sites
        return count;
    }

    public boolean percolates() {             
    // does the system percolate?
        for (int i = 0; i < len; i++) {
            if (root(len-1, i) == 0) return true;
        }
        return false;
    }

    private void print_grid() {
        System.out.println("****************************");
        System.out.println(grid[0]);
        for (int i=0; i<len; i++) {
            for (int j=0; j<len; j++) {
                System.out.print(this.grid[i*len+j+1]);
                System.out.print(", ");
            }
            System.out.println("");
        }
        for (int i=0; i<len; i++) {
            for (int j=0; j<len; j++) {
                System.out.print(this.status[i*len+j+1]);
                System.out.print(", ");
            }
            System.out.println("");
        }
    }

    public static void main(String[] args) {  
    // test client (optional)
        Percolation test = new Percolation(5);

        test.open(1,2);
        test.print_grid();
        test.open(2,2);
        test.print_grid();
        test.open(3,2);
        test.print_grid();
        test.open(4,2);
        test.print_grid();
        test.open(5,2);
        test.print_grid();
        System.out.println(test.percolates());
    }
}