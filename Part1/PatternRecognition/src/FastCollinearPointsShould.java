import org.junit.Assert;
import org.junit.Test;

public class FastCollinearPointsShould {
    private FastCollinearPoints collinear;

    private Point[] oneElementPoints = {
            null
    };

    private Point[] twoElementPoints = {
            new Point(1, 1),
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
            null,
            new Point(0, 1),
            new Point(0, 0)};
    // min 3, max 0

    private Point[] doublePoints = {
            new Point(0, 2),
            new Point(3, 3),
            new Point(0, 1),
            new Point(8, 8),
            new Point(0, 1)};

    private Point[] double1Points = {
            new Point(10622, 10611),
            new Point(10622, 10611),
            new Point(5348, 4126),
            new Point(1112, 7373)};
    // min 2, max 3

    private Point[] collinearPoints = {
            new Point(3, 6),
            new Point(1, 2),
            new Point(0, 0),
            new Point(2, 4)};
    // min 2, max 0

    private Point[] collinear2Points = {
            new Point(2, 2),
            new Point(3, 5),
            new Point(4, 8),
            new Point(6, 3),
            new Point(9, 1),
            new Point(5, 11),
            new Point(0, 7)};

    private Point[] collinear6Points = {
            new Point(19000, 10000),
            new Point(18000, 10000),
            new Point(32000, 10000),
            new Point(21000, 10000),
            new Point(1234, 5678),
            new Point(14000, 10000)};

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

    private Point[] collinear10_1Points = {
            new Point(3, 0),
            new Point(6, 0),
            new Point(2, 1),
            new Point(1, 2),
            new Point(0, 3),
            new Point(6, 3),
            new Point(6, 6),
            new Point(6, 9),
            new Point(2, 5),
            new Point(10, 7),
            new Point(14, 8),
            new Point(14, 11)};

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfFirstElementOfPointsIsNull() {
        new FastCollinearPoints(oneElementPoints);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfSecondElementOfPointsIsNull() {
        new FastCollinearPoints(twoElementPoints);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfPointsIsAnArrayOfDoubles() {
        new FastCollinearPoints(doublePoints);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfPointsIsAnArrayOfDoubles1() {
        new FastCollinearPoints(double1Points);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfPointsIsContainNull() {
        new FastCollinearPoints(zeroPoints);
    }

    @Test
    public void ReturnAllSegmentsForDefaultArray() {
        collinear = new FastCollinearPoints(defaultPoints);
        collinear.segments();
        Assert.assertEquals(0, collinear.numberOfSegments());
    }

    @Test
    public void ReturnOneSegmentForCollinear9Array() {
        collinear = new FastCollinearPoints(collinear9Points);
        var segments = collinear.segments();
        Assert.assertEquals(1, collinear.numberOfSegments());
    }

    @Test
    public void ReturnOneSegmentForCollinear6Array() {
        collinear = new FastCollinearPoints(collinear6Points);
        var segments = collinear.segments();
        Assert.assertEquals(1, collinear.numberOfSegments());
    }

    @Test
    public void ReturnTwoSegmentsForCollinear2Points() {
        collinear = new FastCollinearPoints(collinear2Points);
        var segments = collinear.segments();
        Assert.assertEquals(2, collinear.numberOfSegments());
    }

    @Test
    public void ReturnTwoSegmentsForCollinear8Array() {
        collinear = new FastCollinearPoints(collinear8Points);
        var segments = collinear.segments();
        Assert.assertEquals(2, collinear.numberOfSegments());
    }

    @Test
    public void ReturnTwoSegmentsForCollinear10Points() {
        collinear = new FastCollinearPoints(collinear10Points);
        var segments = collinear.segments();
        Assert.assertEquals(2, collinear.numberOfSegments());
    }

    @Test
    public void ReturnFourSegmentsForCollinear15Points() {
        collinear = new FastCollinearPoints(collinear15Points);
        var segments = collinear.segments();
        Assert.assertEquals(4, collinear.numberOfSegments());
    }

    @Test
    public void ReturnFourSegmentsForCollinear10_1Points() {
        collinear = new FastCollinearPoints(collinear10_1Points);
        var segments = collinear.segments();
        Assert.assertEquals(4, collinear.numberOfSegments());
    }
}
