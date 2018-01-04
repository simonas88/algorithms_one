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

  // public            double slopeTo(Point that)       // the slope between this point and that point
  // public Comparator<Point> slopeOrder()              // compare two points by slopes they make with this point

  public static void testCompareTo(Point input1, Point input2, int expectedResult) {
    if (input1.compareTo(input2) != expectedResult) {
      System.out.println("Test failed: " + input1.toString() + ", " + input2.toString());
    }
  }

  public static void testSlopeTo(Point input1, Point input2, double expectedResult) {
    if (input1.slopeTo(input2) != expectedResult) {
      System.out.println("Test failed: " + input1.toString() + ", " + input2.toString());
    }
  }

  public static void main(String[] args) {
    // CompareTo test suite
    testCompareTo(new Point(3, 2), new Point(2, 1), 1);
    testCompareTo(new Point(3, 2), new Point(2, 2), 1);
    testCompareTo(new Point(3, 2), new Point(3, 2), 0);
    testCompareTo(new Point(3, 1), new Point(2, 2), -1);

    // slopeTo test suite
    testSlopeTo(new Point(3, 3), new Point(2, 2), 1.0);
    testSlopeTo(new Point(3, 3), new Point(3, 3), Double.NEGATIVE_INFINITY);
    testSlopeTo(new Point(3, 3), new Point(6, 3), 0.0);
    testSlopeTo(new Point(3, 3), new Point(3, 6), Double.POSITIVE_INFINITY);
  }
}