import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
  //  public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
  //  public           int numberOfSegments()        // the number of line segments
  //  public LineSegment[] segments()                // the line segments

  private final LineSegment[] segments;

  public FastCollinearPoints(Point[] points) {
    Arrays.sort(points);

    List<LineSegment> segments = new ArrayList<LineSegment>();

    for (int i = 0; i < points.length; i++) {
      Point[] pointCopy = copyWithout(points, i);
      Point pivotPoint = points[i];
      Arrays.sort(pointCopy, pivotPoint.slopeOrder());

      ArrayList<Point> collinearPoints = new ArrayList<Point>();
      for (int j = 0; j < pointCopy.length; j++) {
        Point currentPoint = pointCopy[j];
        Point lastCollinearPoint = collinearPoints.isEmpty()
          ? null
          : collinearPoints.get(collinearPoints.size() - 1);
        
        Point firstCollinearPoint = lastCollinearPoint != null && collinearPoints.size() > 1
          ? collinearPoints.get(0)
          : lastCollinearPoint;

        if (lastCollinearPoint == null) {
          collinearPoints.add(currentPoint);
          continue;
        }

        if (pivotPoint.slopeTo(currentPoint) == pivotPoint.slopeTo(lastCollinearPoint)) {
          collinearPoints.add(currentPoint);
          continue;
        }

        if (collinearPoints.size() > 3) {
          Point[] lineEnds = getLineEnds(collinearPoints);

          segments.add(new LineSegment(lineEnds[0], lineEnds[1]));
          collinearPoints.clear();
        }
      }
    }

    this.segments = (LineSegment[]) segments.toArray();
  }
  
  public int numberOfSegments() {
    return this.segments.length;
  }

  public LineSegment[] segments() {
    return this.segments;
  }

  private static Point[] getLineEnds(List<Point> input) {
    Point[] result = { input.get(0), input.get(0) };

    for (Point point : input) {
      if (point.compareTo(result[0]) == -1) {
        result[0] = point;
      } 

      if (point.compareTo(result[1]) == 1) {
        result[1] = point;
      }
    }

    return result;
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