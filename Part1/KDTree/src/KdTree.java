import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;

public class KdTree {

    private Node first;
    private int count;

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;   // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private boolean isVertical;

        public int compareTo(Point2D that) {
            if ((isVertical && p.x() > that.x()) || (!isVertical && p.y() > that.y())) return -1;
            if ((isVertical && p.x() == that.x()) || (!isVertical && p.y() == that.y())) return 0;
            return 1;
        }
    }

    // construct an empty set of points
    public KdTree() {
        count = 0;
        first = null;
    }

    private static boolean checkArgument(Point2D p) {
        return p.x() < 0 || p.x() > 1 || p.y() < 0 || p.y() > 1;
    }

    // is the set empty?
    public boolean isEmpty() {
        return count == 0;
    }

    // number of points in the set
    public int size() {
        return count;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null || checkArgument(p)) {
            throw new IllegalArgumentException();
        }

        if (first == null) {
            count++;
            first = new Node();
            first.rect = new RectHV(0.0, 0.0, 1.0, 1.0);
            first.p = p;
            first.isVertical = true;
            return;
        }

        if (contains(p)) return;

        Node current = first;
        while (current.p != null) {
            if ((current.isVertical && p.x() <= current.p.x()) || (!current.isVertical && p.y() <= current.p.y())) {
                // left non existing node
                if (current.lb == null) {
                    current.lb = createNewNode(current, p, true);
                    break;
                }

                // left existing node
                current = current.lb;
                continue;
            }

            // right non existing node
            if (current.rt == null) {
                current.rt = createNewNode(current, p, false);
                break;
            }

            // right existing node
            current = current.rt;
        }
    }

    private Node createNewNode(Node parent, Point2D p, boolean isLeft) {
        count++;
        Node newNode = new Node();
        newNode.isVertical = !parent.isVertical;

        double xmin, ymin, xmax, ymax;
        if (isLeft) {
            xmin = parent.rect.xmin();
            ymin = parent.rect.ymin();

            if (parent.isVertical) {
                xmax = parent.p.x();
                ymax = parent.rect.ymax();
            }
            else {
                xmax = parent.rect.xmax();
                ymax = parent.p.y();
            }
        }
        else {
            if (parent.isVertical) {
                xmin = parent.p.x();
                ymin = parent.rect.ymin();
            } else {
                xmin = parent.rect.xmin();
                ymin = parent.p.y();
            }

            xmax = parent.rect.xmax();
            ymax = parent.rect.ymax();
        }
        newNode.rect = new RectHV(xmin, ymin, xmax, ymax);
        newNode.p = p;
        return newNode;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null || checkArgument(p)) {
            throw new IllegalArgumentException();
        }

        return getPoint(first, p);
    }

    private static boolean getPoint(Node node, Point2D p) {
        if (node == null) return false;
        if (node.p.equals(p)) return true;
        if (node.compareTo(p) != 1) return getPoint(node.lb, p);
        return getPoint(node.rt, p);
    }

    private static void drawPoint(Node node) {
        if (node == null) return;
        node.rect.draw();
        drawPoint(node.lb);
        drawPoint(node.rt);
    }

    // draw all points to standard draw
    public void draw() {
        drawPoint(first);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        Stack<Point2D> stack = new Stack<>();

        getRange(first, rect, stack);

        return stack;
    }

    private void getRange(Node node, RectHV rect, Stack<Point2D> stack) {
        if (node == null) return;

        if (node.rect.intersects(rect)) {
            if (rect.contains(node.p)) {
                stack.push(node.p);
            }
            getRange(node.lb, rect, stack);
            getRange(node.rt, rect, stack);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (isEmpty()) return null;

        return getNearest(p);
    }

    private Point2D getNearest(Point2D p) {

        return getNearest(first, p, first.p, Double.POSITIVE_INFINITY);
    }

    private Point2D getNearest(Node node, Point2D p, Point2D nearest, double smallestDist) {
        if (node == null) return nearest;

        if (node.p.distanceSquaredTo(p) < smallestDist) {
            nearest = node.p;
            smallestDist = nearest.distanceSquaredTo(p);
        }

        if (node.lb != null && node.lb.rect.distanceSquaredTo(p) < smallestDist) {
            nearest = getNearest(node.lb, p, nearest, smallestDist);
            smallestDist = nearest.distanceSquaredTo(p);
        }

        if (node.rt != null && node.rt.rect.distanceSquaredTo(p) < smallestDist) {
            nearest = getNearest(node.rt, p, nearest, smallestDist);
        }

        return nearest;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        // optional
    }
}
