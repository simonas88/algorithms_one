import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class Solver {
  private final int moves;
  private final boolean isSolvable;
  private final SearchNode solution;

  public Solver(Board initial) {
    if (initial == null) {
      throw new java.lang.IllegalArgumentException();
    }

    MinPQ<SearchNode> pq = new MinPQ<SearchNode>(new ManhattanComparator());
    MinPQ<SearchNode> twinPq = new MinPQ<SearchNode>(new ManhattanComparator());
    
    pq.insert(new SearchNode(initial, null, 0));
    Board initialTwin = initial.twin();
    twinPq.insert(new SearchNode(initialTwin, null, 0));

    SearchNode currentNode = null;
    SearchNode twinNode = null;

    do {
      currentNode = step(pq);
      twinNode = step(twinPq);
      if (currentNode.getBoard().isGoal()) { break; }
      if (twinNode.getBoard().isGoal()) { break; }
    } while (true);

    if(twinNode.getBoard().isGoal()) {
      this.moves = -1;
      this.solution = null;
      this.isSolvable = false;
    } else {
      this.moves = currentNode.getMoves();
      this.solution = currentNode;
      this.isSolvable = true;
    }
  }

  private SearchNode step(MinPQ<SearchNode> pq) {
    SearchNode searchNode = pq.delMin();
    SearchNode prevNode = searchNode.getPreviousNode();
    Board currentBoard = searchNode.getBoard();
    if (currentBoard.isGoal()) {
      return searchNode;
    }

    Iterable<Board> neighbors = currentBoard.neighbors();
    for (Board neighbor : neighbors) {
      if (prevNode != null && neighbor.equals(prevNode.getBoard())) { continue; }
      pq.insert(new SearchNode(neighbor, searchNode, searchNode.getMoves() + 1));
    }

    return searchNode;
  }

  public int moves() { return this.moves; }
  public boolean isSolvable() { return this.isSolvable; };

  private class SearchNode {
    private final int moves;
    private final Board currentBoard;
    private final SearchNode predecessor;
    private final int manhattan;
    private final int hamming;

    public SearchNode(Board currentBoard, SearchNode predecessor, int moves) {
      this.moves = moves;
      this.currentBoard = currentBoard;
      this.predecessor = predecessor;
      this.manhattan = currentBoard.manhattan();
      this.hamming = currentBoard.hamming();
    }

    public Board getBoard() {
      return this.currentBoard;
    }

    public SearchNode getPreviousNode() {
      return this.predecessor;
    }

    public int getMoves() {
      return this.moves;
    }

    public int manhattan() {
      return this.manhattan;
    }

    public int hamming() {
      return this.hamming;
    }

    public int getPriority() {
      return this.moves + this.manhattan;
    }
  }

  private class Solution implements Iterator<Board> {
    private Stack<SearchNode> reverseNodes;

    public Solution(SearchNode lastNode) {      
      SearchNode currNode = lastNode;
      Stack<SearchNode> reverseNodes = new Stack<SearchNode>();
      while (currNode != null) {
        reverseNodes.push(currNode);
        currNode = currNode.getPreviousNode();
      }

      this.reverseNodes = reverseNodes;
    }

    public boolean hasNext() {
      return !this.reverseNodes.empty();
    }

    public Board next() {
      return this.reverseNodes.pop().getBoard();
    }
  }

  public Iterable<Board> solution() {
    SearchNode ref = this.solution;
  
    return ref == null ? null : new Iterable<Board>() {
      public Iterator<Board> iterator() {
        return new Solution(ref);
      }
    };
  }

  private class ManhattanComparator implements Comparator<SearchNode> {
    public int compare(SearchNode node0, SearchNode node1) {
      int deltaPriority = node0.getPriority() - node1.getPriority();
      int deltaManhattan = node0.manhattan() - node1.manhattan();
      int deltaHamming = node0.hamming() - node1.hamming();

      if (deltaPriority != 0) { return deltaPriority; }
      if (deltaManhattan != 0) { return deltaManhattan; }
      if (deltaHamming != 0) { return deltaHamming; }

      return 0;
    }
  }

  public static void main(String[] args) {
    // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
        StdOut.println("No solution possible");
    else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            StdOut.println(board);
    }
  }
}
