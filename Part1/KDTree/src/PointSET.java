import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.Stack;
import java.util.TreeSet;

public class PointSET {

    private final TreeSet<Point2D> tree;
    private int count;
    // construct an empty set of points
    public PointSET() {
        tree = new TreeSet<>();
        count = 0;
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
        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (!tree.contains(p)) {
            tree.add(p);
            count++;
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        return tree.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D point : tree) {
            point.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        Stack<Point2D> stack = new Stack<>();

        for (Point2D point: tree) {
            if (rect.contains(point)) {
                stack.push(point);
            }
        }

        return stack;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (isEmpty()) return null;
        Point2D nearestPoint = tree.first();
        double smallestDist = Integer.MAX_VALUE;

        for (Point2D point: tree) {
            double currDist = point.distanceSquaredTo(p);
            if (currDist < smallestDist) {
                smallestDist = currDist;
                nearestPoint = point;
            }
        }

        return nearestPoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        // optional
    }
}