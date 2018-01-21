import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;

public class Solver {
  private final int moves;
  private final boolean isSolvable;
  private final Iterable<Board> solution;

  public Solver(Board initial) {
    MinPQ<SearchNode> pq = new MinPQ<SearchNode>(new ManhattanComparator());
    MinPQ<SearchNode> twinPq = new MinPQ<SearchNode>(new ManhattanComparator());
    
    pq.insert(new SearchNode(initial, null, 0));
    Board initialTwin = initial.twin();
    twinPq.insert(new SearchNode(initialTwin, null, 0));

    Board currentBoard = null;
    Board twinBoard = null;

    LinkedList<Board> solution = new LinkedList<Board>();
    LinkedList<Board> twinSolution = new LinkedList<Board>();
    int moves = -1;

    do {
      moves++;
      currentBoard = step(pq, solution);
      twinBoard = step(twinPq, twinSolution);
      if (currentBoard.isGoal()) { break; }
      if (twinBoard.isGoal()) { break; }
    } while (true);

    if(twinBoard.isGoal()) {
      this.moves = 0;
      this.solution = null;
      this.isSolvable = false;
    } else {
      this.moves = moves;
      this.solution = new Solution(solution);
      this.isSolvable = true;
    }
  }

  private Board step(MinPQ<SearchNode> pq, LinkedList<Board> solution) {
    SearchNode searchNode = pq.delMin();
    Board currentBoard = searchNode.getBoard();
    solution.add(currentBoard);
    if (currentBoard.isGoal()) {
      return currentBoard;
    }

    Iterator<Board> neighbors = currentBoard.neighbors();
    while(neighbors.hasNext()) {
      Board neighbor = neighbors.next();
      if (!existsInPath(searchNode, neighbor)) {
        pq.insert(new SearchNode(neighbor, searchNode, searchNode.getMoves() + 1));
      }
    }

    return currentBoard;
  }

  private boolean existsInPath(SearchNode node, Board board) {
    SearchNode lastNode = node;
    do {
      lastNode = lastNode.getPreviousNode();
      if (lastNode == null) {
        return false;
      }
      if (lastNode.getBoard().equals(board)) {
        return true;
      }
    } while (true);
  }

  public int moves() { return this.moves; }
  public Iterable<Board> solution() { return this.solution; }
  public boolean isSolvable() { return this.isSolvable; };

  private class SearchNode {
    private final int moves;
    private final Board currentBoard;
    private final SearchNode predecessor;
    private final int manhattan;

    public SearchNode(Board currentBoard, SearchNode predecessor, int moves) {
      this.moves = moves;
      this.currentBoard = currentBoard;
      this.predecessor = predecessor;
      this.manhattan = currentBoard.manhattan();
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

    public int getPriority() {
      return this.moves + this.manhattan;
    }
  }

  private class Solution implements Iterable<Board> {
    private LinkedList<Board> solution;
    public Solution(LinkedList<Board> solution) { this.solution = solution; }
    public Iterator<Board> iterator() { return this.solution.iterator(); }
  }

  private class ManhattanComparator implements Comparator<SearchNode> {
    public int compare(SearchNode node0, SearchNode node1) {
      int priority0 = node0.getPriority();
      int priority1 = node1.getPriority();
      if (priority0 < priority1) { return -1; }
      if (priority0 > priority1) { return  1; }
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
