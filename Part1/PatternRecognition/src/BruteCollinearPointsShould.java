import org.junit.Assert;
import org.junit.Test;

public class BruteCollinearPointsShould {
    private BruteCollinearPoints collinear;

    private Point[] startNullPoints = {
            null,
            new Point(6, 4),
            new Point(3, 3),
            new Point(0, 1)
    };

    private Point[] middleNullPoints = {
            new Point(6, 4),
            new Point(3, 3),
            null,
            new Point(0, 1)
    };

    private Point[] endNullPoints = {
            new Point(6, 4),
            new Point(3, 3),
            new Point(0, 1),
            null
    };

    private Point[] defaultPoints = {
            new Point(6, 4),
            new Point(3, 3),
            new Point(0, 1),
            new Point(7, 7)};
    // min 2 max 3

    private Point[] zeroPoints = {
            new Point(0, 4),
            new Point(3, 3),
            new Point(0, 1),
            new Point(0, 0)};
    // min 3, max 0

    private Point[] doublePoints = {
            new Point(0, 1),
            new Point(3, 3),
            new Point(0, 1),
            new Point(8, 8)};
    // min 2, max 3

    private Point[] collinearPoints = {
            new Point(3, 6),
            new Point(1, 2),
            new Point(0, 0),
            new Point(2, 4)};
    // min 2, max 0

    private Point[] collinear8Points = {
            new Point(10000, 0),
            new Point(0, 10000),
            new Point(3000, 7000),
            new Point(7000, 3000),
            new Point(20000, 21000),
            new Point(3000, 4000),
            new Point(14000, 15000),
            new Point(6000, 7000) };

    private Point[] collinear9Points = {
            new Point(9000, 9000),
            new Point(8000, 8000),
            new Point(7000, 7000),
            new Point(6000, 6000),
            new Point(5000, 5000),
            new Point(4000, 4000),
            new Point(3000, 3000),
            new Point(2000, 2000),
            new Point(1000, 1000) };

    private Point[] collinear10Points = {
            new Point(4000, 30000),
            new Point(3500, 28000),
            new Point(3000, 26000),
            new Point(2000, 22000),
            new Point(1000, 18000),
            new Point(13000, 21000),
            new Point(23000, 16000),
            new Point(28000, 13500),
            new Point(28000, 5000),
            new Point(28000, 1000)};

    private Point[] collinear15Points = {
            new Point(10000, 0),
            new Point(8000, 2000),
            new Point(2000, 8000),
            new Point(0, 10000),
            new Point(20000, 0),
            new Point(18000, 2000),
            new Point(2000, 18000),
            new Point(10000, 20000),
            new Point(30000, 0),
            new Point(0, 30000),
            new Point(20000, 10000),
            new Point(13000, 0),
            new Point(11000, 3000),
            new Point(5000, 12000),
            new Point(9000, 6000)};

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfElementStartIsNull() {
        collinear = new BruteCollinearPoints(startNullPoints);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfElementMiddleIsNull() {
        collinear = new BruteCollinearPoints(middleNullPoints);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIElementFinishIsNull() {
        collinear = new BruteCollinearPoints(endNullPoints);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfPointsIsNull() {
        collinear = new BruteCollinearPoints(null);
    }

//    @Test(expected = IllegalArgumentException.class)
//    public void ThrowAnExceptionIfLengthOfPointsIsMoreThanFour() {
//        Point[] points = new Point[5];
//        collinear = new BruteCollinearPoints(points);
//    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfOneOfPointsIsNull() {
        Point[] points = { new Point(0, 0), null, new Point(0, 0), new Point(0, 0) };
        collinear = new BruteCollinearPoints(points);
    }

    @Test
    public void ReturnZeroIfSegmentsMethodWasNotCalledYet() {
        collinear = new BruteCollinearPoints(defaultPoints);
        var res = collinear.numberOfSegments();
        Assert.assertEquals(0, res);
    }

    @Test
    public void ReturnEmptyArrayIfUsingDefaultArray() {
        collinear = new BruteCollinearPoints(defaultPoints);
        var segments = collinear.segments();
        Assert.assertEquals(0, collinear.numberOfSegments());
    }

    @Test
    public void ReturnEmptyArrayIfUsingZeroArray() {
        collinear = new BruteCollinearPoints(zeroPoints);
        var segments = collinear.segments();
        Assert.assertEquals(0, collinear.numberOfSegments());
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfDoublesArray() {
        collinear = new BruteCollinearPoints(doublePoints);
    }

    @Test
    public void ReturnArrayWithOneSegmentIfUsingCollinearArray() {
        collinear = new BruteCollinearPoints(collinearPoints);
        var segments = collinear.segments();
        Assert.assertEquals(1, collinear.numberOfSegments());
        var expected = (new LineSegment(collinearPoints[2], collinearPoints[0])).toString();
        Assert.assertEquals(expected, segments[0].toString());
    }

    @Test
    public void ReturnArrayWithTwoSegmentsIfUsingCollinear8Array() {
        collinear = new BruteCollinearPoints(collinear8Points);
        var segments = collinear.segments();
        Assert.assertEquals(2, collinear.numberOfSegments());
    }


    @Test
    public void ReturnArrayWithFourSegmentsIfUsingCollinear15Array() {
        collinear = new BruteCollinearPoints(collinear15Points);
        var segments = collinear.segments();
        Assert.assertEquals(4, collinear.numberOfSegments());
    }
}
