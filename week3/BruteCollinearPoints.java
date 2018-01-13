import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
  private List<LineSegment> segments = new ArrayList<LineSegment>();
  private final double precision = 1 / 32768;

  public BruteCollinearPoints(Point[] points) {
    
    for (int p0 = 0; p0 < points.length - 3; p0++) {
      for (int p1 = p0 + 1; p1 < points.length - 2; p1++) {
        for (int p2 = p1 + 1; p2 < points.length - 1; p2++) {
          for (int p3 = p2 + 1; p3 < points.length; p3++) {
            double slope1 = points[p0].slopeTo(points[p1]);
            double slope2 = points[p1].slopeTo(points[p2]);
            double slope3 = points[p2].slopeTo(points[p3]);

            if (Math.abs(slope1 - slope2) < precision && Math.abs(slope2 - slope3) < precision) {
              this.stashSegment(points[p0], points[p1], points[p2], points[p3]);
            } else if (slope1 == slope2 && slope2 == slope3 && slope3 == Double.POSITIVE_INFINITY) {
              this.stashSegment(points[p0], points[p1], points[p2], points[p3]);
            }
          }
        }
      }
    }
  }

  private void stashSegment(Point p0, Point p1, Point p2, Point p3) {
    Point[] fourPoints = { p0, p1, p2, p3 };
    Arrays.sort(fourPoints);

    this.segments.add(new LineSegment(fourPoints[0], fourPoints[3]));
  }

  public int numberOfSegments() {
    return this.segments.size();
  }

  public LineSegment[] segments() {
    return (LineSegment[]) this.segments.toArray();
  }

  public static void main(String[] args) {
    
  }
}