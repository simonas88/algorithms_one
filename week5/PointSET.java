import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Stack;

public class PointSET {
  private SET<Point2D> points;

  public PointSET() {
    this.points = new SET<Point2D>();
  }

  public boolean isEmpty() {
    return this.points.isEmpty();
  }
  
  public int size() {
    return this.points.size();
  }
  
  public void insert(Point2D p) {
    this.points.add(p);
  }
  
  public boolean contains(Point2D p) {
    return this.points.contains(p);
  }
  
  public void draw() {
    for (Point2D point : this.points) {
      point.draw();
    }
  }
  
  public Iterable<Point2D> range(RectHV rect) {
    Stack<Point2D> points = new Stack<Point2D>();

    for (Point2D setPoint : this.points) {
      if (rect.contains(setPoint)) points.add(setPoint);
    }

    return points;
  }
  
  public Point2D nearest(Point2D p) {
    if (this.points.isEmpty()) { return null; }

    Point2D nearestPoint = this.points.min();

    for (Point2D setPoint : this.points) {
      if (p.distanceSquaredTo(nearestPoint) > p.distanceSquaredTo(setPoint)) {
        nearestPoint = setPoint;
      }
    }

    return nearestPoint;
  }
}