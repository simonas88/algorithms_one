import edu.princeton.cs.algs4.StdOut;

public class Board {
  // public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
                                            // (where blocks[i][j] = block in row i, column j)
  // public int dimension()                 // board dimension n
  // public int hamming()                   // number of blocks out of place
  // public int manhattan()                 // sum of Manhattan distances between blocks and goal
  // public boolean isGoal()                // is this board the goal board?
  // public Board twin()                    // a board that is obtained by exchanging any pair of blocks
  // public boolean equals(Object y)        // does this board equal y?
  // public Iterable<Board> neighbors()     // all neighboring boards
  // public String toString()               // string representation of this board (in the output format specified below)

  // public static void main(String[] args) // unit tests (not graded)

  private final int[] blocks;
  private final int[] goal;
  private final int dimension;

  public Board(int[][] blocks) {
    this.dimension = blocks.length;

    int[] localBlocks = new int[this.dimension * this.dimension];
    int[] goal = new int[localBlocks.length];

    for (int i = 0; i < blocks.length; i++) {
      int[] currentBlock = blocks[i];
      for (int j = 0; j < currentBlock.length; j++) {
        int localIndex = i * this.dimension + j;
        localBlocks[localIndex] = blocks[i][j];
        goal[localIndex] = localIndex + 1;
      }
    }

    goal[goal.length - 1] = 0;

    this.blocks = localBlocks;
    this.goal = goal;
  }

  public int dimension() {
    return this.dimension;
  }

  public int hamming() {
    int distance = 0;

    for (int i = 0; i < this.blocks.length; i++) {
      if (this.blocks[i] != this.goal[i] && this.blocks[i] != 0) {
        distance++;
      }
    }

    return distance;
  }

  private int[] getFinalPosition(int num) {
    int[] dims = new int[2];

    dims[0] = (num == 0 ? 0 : num - 1) / this.dimension;
    dims[1] = (num == 0 ? 0 : num - 1) % this.dimension;

    return dims;
  }

  private int[] getCurrentPosition(int index) {
    int[] dims = new int[2];

    dims[0] = index / this.dimension;
    dims[1] = index % this.dimension;

    return dims;
  }

  private int getPositionDiff(int num, int index) {
    if (num == 0) {
      return 0;
    }

    int[] finalPosition = this.getFinalPosition(num);
    int[] currentPosition = this.getCurrentPosition(index);

    int deltaRow = Math.abs(finalPosition[0] - currentPosition[0]);
    int deltaCol = Math.abs(finalPosition[1] - currentPosition[1]);

    return deltaRow + deltaCol;
  }

  public int manhattan() {
    int distance = 0;

    for (int i = 0; i < this.dimension * this.dimension; i++) {
      int currentBlock = this.blocks[i];
      distance += this.getPositionDiff(currentBlock, i);
    }

    return distance;
  }

  public boolean isGoal() {
    for (int i = 0; i < this.blocks.length; i++) {
      if (this.blocks[i] != this.goal[i]) {
        return false;
      }
    }
    return true;
  }

  public Board twin() {
    int[][] twinBlocks = new int[this.dimension][];
    for (int i = 0; i < this.dimension; i++) {
      int[] row = new int[this.dimension];
      for (int j = 0; j < this.dimension; j++) {
        int localIndex = i * this.dimension + j;
        row[j] = this.blocks[localIndex];
      }
      twinBlocks[i] = row;
    }

    int swap = twinBlocks[0][this.dimension - 1];
    twinBlocks[0][this.dimension - 1] = twinBlocks[this.dimension - 1][0];
    twinBlocks[this.dimension - 1][0] = swap;

    return new Board(twinBlocks);
  }

  public void printGoal() {
    StdOut.println(this.dimension);

    for (int i = 0; i < this.blocks.length; i++) {
      if ((i + 1) % this.dimension == 0) {
        StdOut.println(" " + this.goal[i]);
        continue;
      }
      StdOut.print(" " + this.goal[i]);
    }
  }

  public void printBoard() {
    StdOut.println(this.dimension);

    for (int i = 0; i < this.blocks.length; i++) {
      if ((i + 1) % this.dimension == 0) {
        StdOut.println(" " + this.blocks[i]);
        continue;
      }
      StdOut.print(" " + this.blocks[i]);
      }
    }

  public static void main(String[] args) {
    int[][] blocks = new int[3][];
    int[] block0 = new int[]{ 8, 1, 3 };
    int[] block1 = new int[]{ 4, 0, 2 };
    int[] block2 = new int[]{ 7, 6, 5 };
    blocks[0] = block0;
    blocks[1] = block1;
    blocks[2] = block2;

    Board board = new Board(blocks);

    board.printGoal();
    board.printBoard();
    StdOut.println(board.isGoal());
    StdOut.println(board.hamming());
    StdOut.println(board.manhattan());
    board.twin().printBoard();

    blocks = new int[3][];
    block0 = new int[]{ 1, 2, 3 };
    block1 = new int[]{ 4, 5, 6 };
    block2 = new int[]{ 7, 8, 0 };
    blocks[0] = block0;
    blocks[1] = block1;
    blocks[2] = block2;

    board = new Board(blocks);
    StdOut.println(board.isGoal());
  }
}
