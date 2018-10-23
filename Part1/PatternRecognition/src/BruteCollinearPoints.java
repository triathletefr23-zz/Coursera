import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private final Point[] points;

    private int count;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] data) {
        checkPoints(data);
        points = new Point[data.length];

        System.arraycopy(data, 0, points, 0, data.length);
        count = 0;
    }

    private void checkPoints(Point[] paramPoints) {
        if (paramPoints == null || paramPoints[0] == null) {
            throw new IllegalArgumentException("Points is null!");
        }

        for (int i = 0; i < paramPoints.length; i++) {
            for (int j = i + 1; j < paramPoints.length; j++) {
                if (paramPoints[i] == null || paramPoints[j] == null || paramPoints[i].compareTo(paramPoints[j]) == 0) {
                    throw new IllegalArgumentException(String.format("Point %2d is null", i));
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return count;
    }

    // the line segments
    public LineSegment[] segments() {
        ArrayList<LineSegment> segments = new ArrayList<>();
        ArrayList<Point> firstPoints = new ArrayList<>();
        ArrayList<Double> coefficients = new ArrayList<>();
        Point[] currPoints = new Point[points.length];
        System.arraycopy(points, 0, currPoints, 0, points.length);
        count = 0;

        Arrays.sort(currPoints);

        for (int iter1 = 0; iter1 < currPoints.length; iter1++) {
            for (int iter2 = iter1 + 1; iter2 < currPoints.length; iter2++) {
                for (int iter3 = iter2 + 1; iter3 < currPoints.length; iter3++) {
                    for (int iter4 = iter3 + 1; iter4 < currPoints.length; iter4++) {
                        double slope1 = currPoints[iter1].slopeTo(currPoints[iter2]);
                        double slope2 = currPoints[iter1].slopeTo(currPoints[iter3]);
                        double slope3 = currPoints[iter1].slopeTo(currPoints[iter4]);

                        if (slope1 == slope2 && slope1 == slope3) {
                            Point[] segmentPoints = new Point[] { currPoints[iter1], currPoints[iter2], currPoints[iter3], currPoints[iter4] };
                            Arrays.sort(segmentPoints);

                            Point first = segmentPoints[0];
                            int currIndex = getIndexFromFirstPoints(first, slope1, firstPoints, coefficients);
                            boolean isExist = currIndex != -1;

                            if (!isExist) {
                                firstPoints.add(first);
                                coefficients.add(slope1);
                                LineSegment segment = new LineSegment(segmentPoints[0], segmentPoints[3]);
                                segments.add(segment);
                                count++;
                            }
                        }
                    }
                }
            }
        }

        return segments.toArray(new LineSegment[0]);
    }

    private int getIndexFromFirstPoints(Point point, double coeff, ArrayList<Point> firstPoints, ArrayList<Double> coefficients) {
        if (!firstPoints.contains(point) || !coefficients.contains(coeff)) {
            return -1;
        }

        for (int i = 0; i < firstPoints.size(); i++) {
            boolean first = firstPoints.get(i).compareTo(point) == 0;
            boolean second = coefficients.get(i).equals(coeff);
            if (first && second) {
                return i;
            }
        }

        return -1;
    }

//    public static void main(String[] args) {
//
//        // read the n points from a file
//        In in = new In(args[0]);
//        int n = in.readInt();
//        Point[] points = new Point[n];
//        for (int i = 0; i < n; i++) {
//            int x = in.readInt();
//            int y = in.readInt();
//            points[i] = new Point(x, y);
//        }
//
//        // draw the points
//        StdDraw.enableDoubleBuffering();
//        StdDraw.setXscale(0, 32767);
//        StdDraw.setYscale(0, 32767);
//        for (Point p : points) {
//            p.draw();
//        }
//        StdDraw.show();
//
//        // print and draw the line segments
//        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
//        for (LineSegment segment: collinear.segments()) {
//            StdOut.println(segment);
//            segment.draw();
//        }
//        StdDraw.show();
//    }
}
