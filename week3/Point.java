import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {
  private final int x;
  private final int y;
  
  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int compareTo(Point that) {
    if (this.y < that.y) { return -1; }

    if (this.y == that.y && this.x == that.x) { return 0; }

    return 1;
  }

  public double slopeTo(Point that) {
    if(this.compareTo(that) == 0) { return Double.NEGATIVE_INFINITY; }

    double deltaY = that.y - this.y;

    double deltaX = that.x - this.x;
    if (deltaX == 0) { return Double.POSITIVE_INFINITY; }


    return deltaY/deltaX;
  }

  public void drawTo(Point that) {
    System.out.println("drawing");
  }

  public Comparator<Point> slopeOrder() {
    return new PointComparator(this);
  }

  private class PointComparator implements Comparator<Point> {
    Point invokingPoint;

    PointComparator(Point invokingPoint) {
      this.invokingPoint = invokingPoint;
    }

    public int compare(Point one, Point two) {
      double slope1 = this.invokingPoint.slopeTo(one);
      double slope2 = this.invokingPoint.slopeTo(two);

      if (slope1 > slope2) { return 1; }
      else if (slope1 < slope2) { return -1; }
      else { return 0; }
    }
  }

  /* public static void testCompareTo(Point input1, Point input2, int expectedResult) {
    if (input1.compareTo(input2) != expectedResult) {
      System.out.println("Test failed: " + input1.toString() + ", " + input2.toString());
    }
  }

  public static void testSlopeTo(Point input1, Point input2, double expectedResult) {
    if (input1.slopeTo(input2) != expectedResult) {
      System.out.println("Test failed: " + input1.toString() + ", " + input2.toString());
    }
  } */

  /**
  * Draws this point to standard draw.
  */
  public void draw() {
    /* DO NOT MODIFY */
    StdDraw.point(x, y);
  }

  /**
  * Returns a string representation of this point.
  * This method is provide for debugging;
  * your program should not rely on the format of the string representation.
  *
  * @return a string representation of this point
  */
  public String toString() {
    /* DO NOT MODIFY */
    return "(" + x + ", " + y + ")";
  }

  public static void main(String[] args) {
    // // CompareTo test suite
    // testCompareTo(new Point(3, 2), new Point(2, 1), 1);
    // testCompareTo(new Point(3, 2), new Point(2, 2), 1);
    // testCompareTo(new Point(3, 2), new Point(3, 2), 0);
    // testCompareTo(new Point(3, 1), new Point(2, 2), -1);

    // // slopeTo test suite
    // testSlopeTo(new Point(3, 3), new Point(2, 2), 1.0);
    // testSlopeTo(new Point(3, 3), new Point(3, 3), Double.NEGATIVE_INFINITY);
    // testSlopeTo(new Point(3, 3), new Point(6, 3), 0.0);
    // testSlopeTo(new Point(3, 3), new Point(3, 6), Double.POSITIVE_INFINITY);
  }
}

