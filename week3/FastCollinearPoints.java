public class FastCollinearPoints {
  //  public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
  //  public           int numberOfSegments()        // the number of line segments
  //  public LineSegment[] segments()                // the line segments

  public FastCollinearPoints(Point[] points) {
    Arrays.sort(points);

    for (int i = 0; i < points.length; i++) {
      
    }
  }

  private static Point[] copyWithout(Point[] input, int exclusionIndex) {
    Point[] newPoints = new Point[input.length - 1];

    for (int i = 0; i < newPoints.length; i++) {
      newPoints[i] = input[exclusionIndex <= i ? i + 1 : i];
    }

    return newPoints;
  }

  public static void main(String[] args) {
    Point[] input = { new Point(1, 1), new Point(2, 2), new Point(3, 3) };
    System.out.println(input[1].compareTo(copyWithout(input, 0)[0]) == 0);
    System.out.println(input[2].compareTo(copyWithout(input, 1)[1]) == 0);
    System.out.println(input[1].compareTo(copyWithout(input, 2)[1]) == 0);
  }
}