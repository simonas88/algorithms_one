import edu.princeton.cs.algs4.StdOut;
import java.util.Objects;
import java.util.Iterator;

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
    int[] copy = new int[this.blocks.length];
    System.arraycopy(this.blocks, 0, copy, 0, this.blocks.length);

    int zeroIndex = this.getZeroIndex();

    int first = zeroIndex == 0 ? 1 : 0;
    int second = (first + 1) == zeroIndex ? first + 2 : first + 1; 

    int swap = copy[first];
    copy[first] = copy[second];
    copy[second] = swap;

    return new Board(get2Dblocks(copy, this.dimension));
  }

  @Override
  public boolean equals(Object o) {
    // self check
    if (this == o)
        return true;
    // null check
    if (o == null)
        return false;
    // type check and cast
    if (this.getClass() != o.getClass())
        return false;
    Board board = (Board) o;
    // field comparison

    return Objects.equals(this.dimension, board.dimension)
      && areBlocksSame(this.blocks, board.blocks);
  }

  private boolean areBlocksSame(int[] blocks0, int[] blocks1) {
    for (int i = 0; i < blocks0.length; i++) {
      if (blocks1[i] != blocks0[i]) {
        return false;
      }
    }
    return true;
  }

  private int getZeroIndex() {
    for (int i = 0; i < this.blocks.length; i++) {
      if (this.blocks[i] == 0) {
        return i;
      }
    }
    return -1;
  }

  private static int[][] get2Dblocks(int[] blocks, int dimension) {
    int[][] twinBlocks = new int[dimension][];
    for (int i = 0; i < dimension; i++) {
      int[] row = new int[dimension];
      for (int j = 0; j < dimension; j++) {
        int localIndex = i * dimension + j;
        row[j] = blocks[localIndex];
      }
      twinBlocks[i] = row;
    }

    return twinBlocks;
  }

  private int[] getNeighborBlocks(int offset) {
    int[] copy = new int[this.blocks.length];
    int zeroIndex = this.getZeroIndex();
    System.arraycopy(this.blocks, 0, copy, 0, this.blocks.length);

    copy[zeroIndex] = copy[zeroIndex + offset];
    copy[zeroIndex + offset] = 0;

    return copy;    
  }

  private Board getNeighborBoard(int offset) {
    int[] neighborBlocks = this.getNeighborBlocks(offset);
    
    return new Board(get2Dblocks(neighborBlocks, this.dimension));
  }

  private int[] getValidNeighborOffsets() {
    int[] validOffsets = {0,0,0,0};

    int zeroIndex = this.getZeroIndex();

    // isTopRow
    if (zeroIndex / this.dimension != 0) {
      validOffsets[0] = -1 * this.dimension;
    }

    // isLeftCol
    if (zeroIndex % this.dimension != 0) {
      validOffsets[1] = -1;
    }
    
    // isRightCol
    if (zeroIndex % this.dimension != (this.dimension - 1)) {
      validOffsets[2] = 1;
    }

    // isBottomRow
    if ((zeroIndex / this.dimension) != (this.dimension - 1)) {
      validOffsets[3] = this.dimension;
    }

    int neighCount = 0;
    for (int offset : validOffsets) { neighCount += offset == 0 ? 0 : 1; }

    int[] finalOffsets = new int[neighCount];
    int neighIndex = 0;
    for (int offset : validOffsets) {
      if (offset != 0) {
        finalOffsets[neighIndex++] = offset;
      }
    }

    return finalOffsets;
   }

  private class NeighborIterator implements Iterator<Board> {
    //  1
    // 2 3
    //  4
    private final Board origin;
    private final int[] neighborOffsets;
    private int currentNeighborOffset = 0;

    public NeighborIterator(Board origin, int[] neighborOffsets) {
      this.neighborOffsets = neighborOffsets;
      this.origin = origin;
    }

    public boolean hasNext() {
      return currentNeighborOffset < neighborOffsets.length;
    }

    public Board next() {
      if (currentNeighborOffset >= neighborOffsets.length) {
        throw new java.util.NoSuchElementException();
      }

      return this.origin.getNeighborBoard(this.neighborOffsets[this.currentNeighborOffset++]);
    }
  }

  public Iterable<Board> neighbors() {
    Board thisRef = this;
    return new Iterable<Board>() {
      public Iterator<Board> iterator() {
        return new NeighborIterator(thisRef, thisRef.getValidNeighborOffsets());
      }
    };
  }

  public String toString() {
    StringBuilder string = new StringBuilder();
    string.append(this.dimension);
    string.append("\n");
    for (int i = 0; i < this.blocks.length; i++) {
      string.append(" " + this.blocks[i]);
      if ((i + 1) % this.dimension == 0) {
        string.append("\n");
        continue;
      }
    }
    return string.toString();
  }
}
