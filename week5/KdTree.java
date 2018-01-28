import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Stack;

public class KdTree {
  private Node root;
  private int size = 0;

  public KdTree() {}

  public boolean isEmpty() {
    return this.root == null;
  }
  
  public int size() {
    return this.size;
  }
  
  public void insert(Point2D p) {
    try {
      this.root = this.put(this.root, p, true);
      this.size++;
    } catch (java.lang.IllegalArgumentException e) {

    }
  }

  private Node put(Node node, Point2D point, boolean isVertical) {
    if (node == null) {
      return new Node(point, isVertical);
    }

    if (point.equals(node.point)) {
      throw new java.lang.IllegalArgumentException();
    }

    if (isVertical) {
      if (node.point.x() > point.x()) {
        node.small = this.put(node.small, point, !isVertical);
      } else {
        node.big = this.put(node.big, point, !isVertical);
      }
    } else {
      if (node.point.y() > point.y()) {
        node.small = this.put(node.small, point, !isVertical);
      } else {
        node.big = this.put(node.big, point, !isVertical);
      }
    }

    return node;
  }
  
  public boolean contains(Point2D p) {
    Node currentNode = this.root;

    while (currentNode != null)
    {
      if (currentNode.point.equals(p)) { return true; }

      if (currentNode.isVertical) {
        currentNode = currentNode.point.x() > p.x()
          ? currentNode.small
          : currentNode.big;
      } else {
        currentNode = currentNode.point.y() > p.y()
          ? currentNode.small
          : currentNode.big;
      }
    }

    return false;
  }
  
  public void draw() {
  }
  
  public Iterable<Point2D> range(RectHV rect) {
    Stack<Point2D> containedPoints = new Stack<Point2D>();
    Stack<Node> nodesToExamine = new Stack<Node>();
    
    nodesToExamine.push(this.root);

    while(!nodesToExamine.empty()) {
      Node currentNode = nodesToExamine.pop();
      if (currentNode == null) continue;

      if (rect.contains(currentNode.point)) {
        containedPoints.add(currentNode.point); 
      }

      if (!Node.getRect(currentNode).intersects(rect)) {
        if (currentNode.isVertical) {
          if (currentNode.point.x() > rect.xmax()) {
            // to the left
            nodesToExamine.add(currentNode.small);
          } else if (currentNode.point.x() < rect.xmin()) {
            // to the right
            nodesToExamine.add(currentNode.big);
          }
        } else {
          if (currentNode.point.y() > rect.ymax()) {
            // to the bottom
            nodesToExamine.add(currentNode.small);
          } else if (currentNode.point.y() < rect.ymin()) {
            // to the top
            nodesToExamine.add(currentNode.big);
          }
        }
      } else {
        nodesToExamine.add(currentNode.small);
        nodesToExamine.add(currentNode.big);
      }
    }

    return containedPoints;
  }
  
  public Point2D nearest(Point2D p) {
    Node currentNearest = null;
    double currentDistance = 2;
    Node currentNode = this.root;
    Node swap = null;
    
    while (currentNode != null) {
      if (currentNode.point.distanceSquaredTo(p) < currentDistance) {
        currentDistance = currentNode.point.distanceSquaredTo(p);
        currentNearest = currentNode;
      }

      if (currentNode.isVertical) {
        if (currentNode.point.x() > p.x()) {
          currentNode = currentNode.small;
        } else {
          currentNode = currentNode.big;
        }
      } else {
        if (currentNode.point.y() > p.y()) {
          currentNode = currentNode.small;
        } else {
          currentNode = currentNode.big;
        }
      }
    }

    return currentNearest.point;
  }

  private static class Node {
    private Point2D point;
    private boolean isVertical;
    private Node small, big;

    public Node(Point2D point, boolean isVertical) {
      this.point = point;
      this.isVertical = isVertical;
    }

    public static RectHV getRect(Node node) {
      if (node.isVertical) {
        return new RectHV(node.point.x(), 0, node.point.x(), 1);
      } else {
        return new RectHV(0, node.point.y(), 1, node.point.y());
      }
    }
  }

  // public static void main(String[] args) {
  //   KdTree tree = new KdTree();

  //   // StdOut.println(tree.getRoot() == null);

  //   // tree.insert(new Point2D(0.5, 0.5));
  //   // tree.insert(new Point2D(0.5, 0.5));
  //   // tree.insert(new Point2D(0.6, 0.5));
  //   // tree.insert(new Point2D(0.6, 0.3));
  //   // tree.insert(new Point2D(0.69, 0.5));
  //   // tree.insert(new Point2D(0.4, 0.5));

  //   Point2D testPoint = new Point2D(0.2, 0.4);

  //   tree.insert(new Point2D(0.7, 0.2));
  //   tree.insert(new Point2D(0.5, 0.4));
  //   tree.insert(new Point2D(0.2, 0.3));
  //   tree.insert(new Point2D(0.4, 0.7));
  //   tree.insert(new Point2D(0.9, 0.6));
  //   StdOut.println("Tree size is: " + tree.size());

  //   StdOut.println(tree.contains(testPoint));

  //   Iterable<Point2D> points = tree.range(new RectHV(0.15, 0.25, 0.55, 0.75));

  //   StdOut.println("Points in test rect");
  //   for (Point2D point : points) {
  //     StdOut.println(point);
  //   }
    

  //   // tree.getRoot().getRect().draw();
  //   // tree.getRoot().getPoint().draw();
  // }
}