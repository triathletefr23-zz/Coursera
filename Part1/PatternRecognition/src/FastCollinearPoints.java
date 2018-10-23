import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FastCollinearPoints {

    private final Point[] points;
    private int count;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] data) {
        checkPoints(data);

        points = new Point[data.length];

        System.arraycopy(data, 0, points, 0, data.length);
        count = 0;
    }

    private void checkPoints(Point[] data) {
        if (data == null || data[0] == null) {
            throw new IllegalArgumentException("Points is null!");
        }

        for (int i = 0; i < data.length; i++) {
            for (int j = i + 1; j < data.length; j++) {
                if (data[i] == null || data[j] == null || data[i].compareTo(data[j]) == 0) {
                    throw new IllegalArgumentException("Points array contains doubles!");
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

        for (Point pivot : currPoints) {
            double previousCoefficient = 0.0;

            Point[] copyPoints = new Point[currPoints.length];
            System.arraycopy(currPoints, 0, copyPoints, 0, currPoints.length);
            Arrays.sort(copyPoints, pivot.slopeOrder());
            ArrayList<Point> collinearPoints = new ArrayList<>();

            for (int i = 0; i < copyPoints.length; i++) {
                double currentCoefficient = copyPoints[i].slopeTo(pivot);
                boolean isLastElement = i + 1 == copyPoints.length;
                if (currentCoefficient != previousCoefficient || (isLastElement && currentCoefficient == previousCoefficient)) {
                    if (isLastElement) {
                        collinearPoints.add(copyPoints[i]);
                    }
                    if (collinearPoints.size() > 2) {
                        collinearPoints.add(pivot);
                        Collections.sort(collinearPoints);

                        Point first = collinearPoints.get(0);
                        int currIndex = getIndexFromFirstPoints(first, previousCoefficient, firstPoints, coefficients);
                        boolean isExist = currIndex != -1;

                        if (!isExist) {
                            firstPoints.add(first);
                            coefficients.add(previousCoefficient);
                            LineSegment segment = new LineSegment(collinearPoints.get(0), collinearPoints.get(collinearPoints.size() - 1));
                            segments.add(segment);
                            count++;
                        }
                    }
                    collinearPoints.clear();
                }
                collinearPoints.add(copyPoints[i]);
                previousCoefficient = currentCoefficient;
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
            if (!first) continue;
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
//        StdDraw.setXscale(0, 32768);
//        StdDraw.setYscale(0, 32768);
//        for (Point p : points) {
//            p.draw();
//        }
//        StdDraw.show();
//
//        // print and draw the line segments
//        FastCollinearPoints collinear = new FastCollinearPoints(points);
//        var segments = collinear.segments();
//        for (LineSegment segment : segments) {
//            StdOut.println(segment);
//            segment.draw();
//        }
//        StdDraw.show();
//    }
}