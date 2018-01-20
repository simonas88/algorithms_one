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

  private final int[][] blocks;
  private final int[][] goalBoard;

  public Board(int[][] blocks) {
    int[][] localBlocks = new int[blocks.length][];
    int[][] goalBoard = new int[blocks.length][];

    for (int i = 0; i < blocks.length; i++) {
      int[] currentBlock = blocks[i];
      localBlocks[i] = new int[currentBlock.length];
      goalBoard[i] = new int[currentBlock.length];

      for (int j = 0; j < currentBlock.length; j++) {
        localBlocks[i][j] = currentBlock[j];
        goalBoard[i][j] = currentBlock.length * i + 1 + j;
      }
    }

    goalBoard[blocks.length - 1][blocks.length -1] = 0;

    this.blocks = localBlocks;
    this.goalBoard = goalBoard;
  }

  public int dimension() {
    return this.blocks.length;
  }

  public void printGoal() {
    for (int[] row : this.goalBoard) {
      for (int num : row) {
        StdOut.print(num + ", ");
      }
      StdOut.println();
    }
  }

  public static void main(String[] args) {
    int[][] blocks = new int[3][];
    blocks[0] = new int[3];
    blocks[1] = new int[3];
    blocks[2] = new int[3];

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        blocks[i][j] = 56;
      }
    }

    Board board = new Board(blocks);

    board.printGoal();
  }
}
