import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
  private final LineSegment[] segments;
  private final double precision = 1 / 32768;

  public BruteCollinearPoints(Point[] points) {
    verifyInput(points);

    List<LineSegment> segments = new ArrayList<LineSegment>();
    
    for (int p0 = 0; p0 < points.length - 3; p0++) {
      for (int p1 = p0 + 1; p1 < points.length - 2; p1++) {
        for (int p2 = p1 + 1; p2 < points.length - 1; p2++) {
          for (int p3 = p2 + 1; p3 < points.length; p3++) {
            double slope1 = points[p0].slopeTo(points[p1]);
            double slope2 = points[p1].slopeTo(points[p2]);
            double slope3 = points[p2].slopeTo(points[p3]);

            if (Math.abs(slope1 - slope2) < precision && Math.abs(slope2 - slope3) < precision) {
              stashSegment(segments, points[p0], points[p1], points[p2], points[p3]);
            } else if (slope1 == slope2 && slope2 == slope3 && slope3 == Double.POSITIVE_INFINITY) {
              stashSegment(segments, points[p0], points[p1], points[p2], points[p3]);
            }
          }
        }
      }
    }

    LineSegment[] toSave = new LineSegment[segments.size()];

    for (int i = 0; i < toSave.length; i++) {
      toSave[i] = segments.get(i);
    }

    this.segments = toSave;
  }

  private static void verifyInput(Point[] input) {
    if (input == null) {
      throw new java.lang.IllegalArgumentException();
    }

    if (input[0] == null) {
      throw new java.lang.IllegalArgumentException();
    }
    for (int i = 0; i < input.length; i++) {
      for (int j = i + 1; j < input.length; j++) {
        if (input[j] == null) {
          throw new java.lang.IllegalArgumentException();
        }
        if (input[i].compareTo(input[j]) == 0) {
          throw new java.lang.IllegalArgumentException();
        }
      }
    }
  }

  private static void stashSegment(List<LineSegment> segments, Point p0, Point p1, Point p2, Point p3) {
    Point[] fourPoints = { p0, p1, p2, p3 };
    Arrays.sort(fourPoints);

    segments.add(new LineSegment(fourPoints[0], fourPoints[3]));
  }

  public int numberOfSegments() {
    return this.segments.length;
  }

  public LineSegment[] segments() {
    return this.segments;
  }

  public static void main(String[] args) {
    
  }
}