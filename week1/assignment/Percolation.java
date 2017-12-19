public class Percolation {
  private int gridSize;
  private boolean[][] grid;
  private QuickUnion connections;

  public Percolation(int n) {
    if (n <= 0) {
      throw new java.lang.IllegalArgumentException("Grid size should be a natural number bigger than 0.");
    }

    this.gridSize = n;
    this.grid = new boolean[n][n];
    this.connections = new QuickUnion(n*n, n);

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        this.grid[i][j] = false;
      }
    }
  }

  public boolean percolates() {
    return this.connections.percolates();
  }

  private void validateInput(int param) {
    if (param >= this.gridSize || param < 0) {
      throw new java.lang.IllegalArgumentException("The parameter is out of bounds.");
    }
  }

  private int getIndex(int row, int col) {
    return col + row * this.gridSize;
  }

  private void connectNeighbors(int row, int col) {
    int[][] neighbors = {
      {row - 1, col},
      {row, col - 1},
      {row, col + 1},
      {row + 1, col}
    };

    for (int i = 0; i < 4; i++) {
      try {
        if (this.isOpen(neighbors[i][0] + 1, neighbors[i][1] + 1)) {
          this.connections.union(
            this.getIndex(row, col),
            this.getIndex(neighbors[i][0], neighbors[i][1])
          );
        }
      } catch (java.lang.IllegalArgumentException | java.lang.ArrayIndexOutOfBoundsException e) {}
    }
  }

  public void open(int inputRow, int inputCol) {
    int row = inputRow - 1;
    int col = inputCol - 1;
    
    this.validateInput(row);
    this.validateInput(col);

    this.grid[row][col] = true;
    this.connectNeighbors(row, col);
  }

  public boolean isOpen(int inputRow, int inputCol) {
    int row = inputRow - 1;
    int col = inputCol - 1;

    this.validateInput(row);
    this.validateInput(col);
    
    return this.grid[row][col];
  }

  public boolean isFull(int inputRow, int inputCol) {
    int row = inputRow - 1;
    int col = inputCol - 1;

    this.validateInput(row);
    this.validateInput(col);

    return this.isOpen(inputRow, inputCol) && this.connections.isFull(this.getIndex(row, col));
  }

  public int numberOfOpenSites() {
    int openCount = 0;

    for (int i = 0; i < this.gridSize; i++) {
      for (int j = 0; j < this.gridSize; j++) {
        openCount += this.grid[i][j] ? 1 : 0;
      }
    }

    return openCount;
  }

  private void printGrid() {
    int gridSize = this.grid.length;

    for (int i = 0; i < gridSize; i++) {
      for (int j = 0; j < gridSize; j++) {
        System.out.print(grid[i][j] ? "\u25A0" : "\u25A1");
      }
      System.out.print("\n");
    }
  }

  public static void main(String[] args) {
    Percolation percolation = new Percolation(5);

    System.out.println("Interface tests...");
    percolation.open(3, 3);
    percolation.printGrid();
    System.out.println("Site (3, 3) should be open: " + (percolation.isOpen(3, 3) == true));
    System.out.println("Site (3, 3) should not be full: " + (percolation.isFull(2, 3) == false));
    System.out.println("There should be 1 open site: " + (percolation.numberOfOpenSites() == 1));
    System.out.println("System should not percolate: " + (percolation.percolates() == false));

    percolation.open(1, 3);
    percolation.open(2, 3);
    percolation.open(3, 3);
    percolation.open(4, 3);
    percolation.open(5, 3);

    System.out.println("");
    percolation.printGrid();
    System.out.println("System should percolate: " + (percolation.percolates() == true));
  }

  private class QuickUnion {
    private int[] members;
    private int[] treeSizes;
    private int top;
    private int bottom;

    public QuickUnion(int members, int gridSize) {
      this.members = new int[members + 2];
      this.treeSizes = new int[members + 2];

      this.top = members;
      this.bottom = members + 1;

      for (int i = 0; i < members + 2; i++) {
        this.members[i] = i;
        this.treeSizes[i] = 1;
      }

      for (int i = 0; i < gridSize; i++) {
        this.union(this.top, i);
        if (gridSize > 1) {
          this.union(this.bottom, members - gridSize + i);
        }
      }
    }

    public int[] getCoordinates(int i, int dimension) {
      int[] result = { i / dimension, i % dimension };
      return result;
    }

    public int root(int input) {
      while (input != this.members[input]) {
        input = this.members[input];
      }

      return input;
    }

    public boolean isConnected(int member0, int member1) {
      return this.root(member0) == this.root(member1);
    }

    public boolean isFull(int member) {
      return this.isConnected(this.top, member);
    }

    public void union(int member0, int member1) {
      int root0 = root(member0);
      int root1 = root(member1);

      if (root0 == root1) { return; }

      if (this.treeSizes[root0] >= this.treeSizes[root1]) {
        this.members[root1] = root0;
        this.treeSizes[root0] += this.treeSizes[root1];
        this.treeSizes[root1] = this.treeSizes[root0];
      } else {
        this.members[root0] = root1;
        this.treeSizes[root1] += this.treeSizes[root0];
        this.treeSizes[root0] = this.treeSizes[root1];
      }
    }

    public boolean percolates() {
      return root(this.top) == root(this.bottom);
    }
  }
}
