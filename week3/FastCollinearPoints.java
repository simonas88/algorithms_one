import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
  //  public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
  //  public           int numberOfSegments()        // the number of line segments
  //  public LineSegment[] segments()                // the line segments

  private final LineSegment[] segments;
  private static final double epsilon = 1 / 32768;

  public FastCollinearPoints(Point[] inputPoints) {
    verifyInput(inputPoints);
    Point[] points = copy(inputPoints);

    List<Point[]> segments = new ArrayList<Point[]>();

    for (int i = 0; i < points.length; i++) {
      Point pivotPoint = points[i];
      Point[] pointCopy = copyWithout(points, i);
      Arrays.sort(pointCopy, pivotPoint.slopeOrder());

      ArrayList<Point> collinearPoints = new ArrayList<Point>();

      for (int j = 0; j < pointCopy.length; j++) {
        Point currentPoint = pointCopy[j];
        Point lastCollinearPoint = collinearPoints.isEmpty()
          ? null
          : collinearPoints.get(collinearPoints.size() - 1);
        
        if (lastCollinearPoint == null) {
          collinearPoints.add(currentPoint);
          continue;
        }

        if (slopeEquals(pivotPoint, currentPoint, lastCollinearPoint)) {
          addPoint(collinearPoints, currentPoint);
          continue;
        } else {
          if (collinearPoints.size() > 2) {
            addPoint(collinearPoints, pivotPoint);
            segments.add(pointListToArray(collinearPoints));
          }

          collinearPoints.clear();
          collinearPoints.add(currentPoint);
        }
      }

      if (collinearPoints.size() > 2) {
        addPoint(collinearPoints, pivotPoint);
        segments.add(pointListToArray(collinearPoints));
      }

      collinearPoints.clear();
    }

    this.segments = toLineSegmentArray(segments);
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

  private static void addPoint (ArrayList<Point> list, Point point) {
    int index = 0;

    for (int i = 0; i < list.size(); i++) {
      if (point.compareTo(list.get(i)) > 0) {
        index += 1;
      }
    }

    list.add(index, point);
  }

  private static Point[] pointListToArray(ArrayList<Point> input) {
    Point[] output = new Point[input.size()];
    for(int i = 0; i < output.length; i++) {
      output[i] = input.get(i);
    }
    return output;
  }

  private static LineSegment[] toLineSegmentArray(List<Point[]> segments) {
    for (int i = 0; i < segments.size(); i++) {
      Point[] outerSegment = segments.get(i);
      for (int j = i + 1; j < segments.size();) {
        Point[] innerSegment = segments.get(j);
        if (isSegmentsCollinear(outerSegment, innerSegment)) {
          if (outerSegment.length >= innerSegment.length) {
            segments.remove(j);
            continue;
          }
        }
        j++;
      }
    }

    LineSegment[] segmentArray = new LineSegment[segments.size()];
    for (int i = 0; i < segmentArray.length; i++) {
      Point[] segment = segments.get(i);
      segmentArray[i] = new LineSegment(
        segment[0],
        segment[segment.length - 1]
      );
    }

    return segmentArray;
  }

  private static boolean isSegmentsCollinear(Point[] seg1, Point[] seg2) {
    if (Math.abs(
      seg1[0].slopeTo(seg1[seg1.length - 1]) - seg2[0].slopeTo(seg2[seg2.length - 1])
    ) > epsilon) {
      return false;
    }

    short samePoints = 0;

    for (Point point1 : seg1) {
      for (Point point2: seg2) {
        if (point1.compareTo(point2) == 0) {
          samePoints++;
        }
        if (samePoints >= 2) { return true; }
      }
      if (samePoints >= 2) { return true; }
    }

    return samePoints >= 2;
  }

  private static boolean slopeEquals(Point pivot, Point current, Point last) {
    double slopeCurrent = pivot.slopeTo(current);
    double slopeLast = pivot.slopeTo(last);

    if (Double.compare(slopeLast, slopeCurrent) == 0) {
      return true;
    }

    return Math.abs(slopeCurrent - slopeLast) <= epsilon;
  }
  
  public int numberOfSegments() {
    return this.segments.length;
  }

  public LineSegment[] segments() {
    LineSegment[] returns = new LineSegment[this.segments.length];
    for(int i = 0; i < returns.length; i++) {
      returns[i] = this.segments[i];
    }

    return returns;
  }

  private static Point[] copyWithout(Point[] input, int exclusionIndex) {
    Point[] newPoints = new Point[input.length - 1];

    for (int i = 0; i < newPoints.length; i++) {
      newPoints[i] = input[exclusionIndex <= i ? i + 1 : i];
    }

    return newPoints;
  }

  private static Point[] copy(Point[] input) {
    Point[] copied = new Point[input.length];
    for (int i = 0; i < input.length; i++) {
      copied[i] = input[i];
    }
    return copied;
  }

  public static void main(String[] args) {
    Point[] input = { new Point(1, 1), new Point(2, 2), new Point(3, 3) };
    System.out.println(input[1].compareTo(copyWithout(input, 0)[0]) == 0);
    System.out.println(input[2].compareTo(copyWithout(input, 1)[1]) == 0);
    System.out.println(input[1].compareTo(copyWithout(input, 2)[1]) == 0);
  }
}