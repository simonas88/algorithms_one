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

  public static void main(String[] args) {
    int[][] blocks = new int[3][];
    blocks[0] = new int[3];
    blocks[1] = new int[3];
    blocks[2] = new int[3];

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        blocks[i][j] = 1;
      }
    }

    Board board = new Board(blocks);

    board.printGoal();
    StdOut.println(board.hamming());
  }
}
