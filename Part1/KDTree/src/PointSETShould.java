import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.Assert;
import org.junit.Test;

public class PointSETShould {
    private PointSET pointSet;
    private RectHV rectHV;

    public PointSETShould() {
        pointSet = new PointSET();
    }

    private void insertPoints(int count, double step) {
        for (var i = 0.0; i < count * step; i+= step) {
            pointSet.insert(new Point2D(i, i));
        }
    }

    private int countPoints(Iterable<Point2D> stack) {
        var count = 0;
        for (var elem: stack) {
            count++;
        }
        return count;
    }

    @Test
    public void ReturnTrueIfTreeIsEmpty() {
        Assert.assertTrue(pointSet.isEmpty());
        Assert.assertTrue(pointSet.size() == 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfParamIsNull() {
        pointSet.insert(null);
        pointSet.contains(null);
        pointSet.nearest(null);
    }

    @Test
    public void Return5PointsInRectangleRange() {
        rectHV = new RectHV(0, 0, 1, 1);
        insertPoints(5, 0.1);
        var count = countPoints(pointSet.range(rectHV));
        Assert.assertEquals(5, count);
    }

    @Test
    public void Return3PointsInRectangleRange() {
        rectHV = new RectHV(-1, -1, 0.5, 0.5);
        insertPoints(5, 0.2);
        var count = countPoints(pointSet.range(rectHV));
        Assert.assertEquals(3, count);
    }

    @Test
    public void ReturnEmptyStackInRectangleRange() {
        rectHV = new RectHV(-1, -1, -0.1, -0.2);
        insertPoints(5, 0.2);
        var count = countPoints(pointSet.range(rectHV));
        Assert.assertEquals(0, count);
    }

    @Test
    public void ReturnNearestNeighborZero() {
        var currPoint = new Point2D(0, 0);
        insertPoints(5, 0.2);
        var resPoint = pointSet.nearest(currPoint);
        Assert.assertEquals(0, resPoint.compareTo(new Point2D(0.0, 0.0)));
    }

    @Test
    public void ReturnNearestNeighborNonZero() {
        var currPoint = new Point2D(0.8, 0.0);
        insertPoints(5, 0.2);
        var resPoint = pointSet.nearest(currPoint);
        Assert.assertEquals(0, resPoint.compareTo(new Point2D(0.4, 0.4)));
    }
}
