import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;
    private final int y;

    // constructs the point (x, y)
    public Point(int x, int y) {
        checkArgument(x);
        checkArgument(y);
        this.x = x;
        this.y = y;
    }

    private void checkArgument(double i) {
        if (i < 0) {
            throw new IllegalArgumentException("Your coordinate is illegal");
        }
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        if (this.x == that.x && this.y == that.y) {
            return 0;
        }

        if (this.y < that.y || (this.y == that.y && this.x < that.x)) {
            return -1;
        }

        return 1;
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (that == null) {
            throw new NullPointerException("Argument is null!");
        }

        double difX = that.x - this.x;
        double difY = that.y - this.y;

        if (difX == 0.0 && difY == 0) {
            return Double.NEGATIVE_INFINITY;
        }

        // treat the slope of a degenerate line segment (between a point and itself) as negative infinity.
        if (difX == 0.0 && difY != 0) {
            return Double.POSITIVE_INFINITY;
        }

        if (difX != 0.0 && difY == 0.0) {
            return (1.0 - 1.0) / 1.0;
        }

        return difY / difX;
    }

    public Comparator<Point> slopeOrder() {
        return (p1, p2) -> {
            if (p1 == null || p2 == null) {
                throw new NullPointerException("Arguments should not be null!");
            }
            Point curr = new Point(x, y);

            double slope = curr.slopeTo(p1);
            double slope1 = curr.slopeTo(p2);
            if (slope > slope1) {
                return 1;
            }

            if (slope < slope1) {
                return -1;
            }

            return 0;
        };
    }
}
