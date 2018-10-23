import org.junit.Assert;
import org.junit.Test;

public class PointShould {

    private Point point;

    public PointShould() {
        this.point = new Point(0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfFirstArgumentIsIllegal() {
        this.point = new Point(-1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfSecondArgumentIsIllegal() {
        this.point = new Point(0, -1);
    }

    @Test
    public void CreatePointWithValue() {
        Assert.assertNotNull(this.point);
    }

    @Test
    public void ReturnStringRepresantationOfThePoint() {
        String result = this.point.toString();

        Assert.assertTrue(result.contains("(0, 0)"));
    }

    @Test
    public void ReturnThePositiveInfIfOrdinatesAreEqual() {
        Point that = new Point(3, 0);

        double slope = this.point.slopeTo(that);
        double a = 1.0;
        Assert.assertEquals((a - a) / -a, slope,0.001);
    }

    @Test
    public void ReturnPositiveZeroIfAbscissasAreEqual() {
        Point that = new Point(0, 7);

        double slope = this.point.slopeTo(that);
        Assert.assertEquals(Double.POSITIVE_INFINITY, slope, 0.001);
    }

    @Test
    public void ReturnTheNegativeInfIfCorrespondingCoordinatesAreEqual() {
        Point that = new Point(0, 0);

        double slope = this.point.slopeTo(that);
        Assert.assertEquals(Double.NEGATIVE_INFINITY, slope,0.001);
    }

    @Test
    public void ReturnTheSlopeForTheLineOfPoints() {
        Point that = new Point(1, 1);

        double res = this.point.slopeTo(that);
        Assert.assertEquals(1, res, 0.001);
    }

    @Test
    public void ReturnTheSlopeForTheLine1OfPoints() {
        Point that = new Point(2, 3);

        double res = this.point.slopeTo(that);
        Assert.assertEquals(1.5, res, 0.001);
    }

    @Test
    public void ReturnZeroIfThePointEqualsToThatPoint() {
        Point that = new Point(0, 0);

        var res = this.point.compareTo(that);
        Assert.assertEquals(0, res);
    }

    @Test
    public void ReturnMinusOneIfThePointIsBiggerToThatPointWithBiggerOrdinate() {
        Point that = new Point(0, 1);

        var res = this.point.compareTo(that);
        Assert.assertEquals(-1, res);
    }

    @Test
    public void ReturnMinusOneIfThePointIsBiggerToThatPointWithSameOrdinateAndBiggerAbscissa() {
        Point that = new Point(1, 0);

        var res = this.point.compareTo(that);
        Assert.assertEquals(-1, res);
    }

    @Test
    public void ReturnOneIfThePointBiggerThanThatPoint() {
        this.point = new Point(1, 1);
        Point that = new Point(0, 0);

        var res = this.point.compareTo(that);
        Assert.assertEquals(1, res);
    }

    @Test
    public void ReturnComparatorForTwoEqualPoint() {
        Point first = new Point(1,1);
        Point second = new Point(2,2);

        var comparator = this.point.slopeOrder();
        var res = comparator.compare(first, second);
        Assert.assertEquals(0, res);
    }

    @Test
    public void ReturnComparatorForFirstPointThatIsBiggerThanSecond() {
        Point first = new Point(3,1);
        Point second = new Point(1,2);

        var comparator = this.point.slopeOrder();
        var res = comparator.compare(first, second);
        Assert.assertEquals(-1, res, 0.001);
    }

    @Test
    public void ReturnComparatorForSecondPointThatIsBiggerThanFirst() {
        Point first = new Point(1,1);
        Point second = new Point(6,2);

        var comparator = this.point.slopeOrder();
        var res = comparator.compare(first, second);
        Assert.assertEquals(1, res);
    }
}
