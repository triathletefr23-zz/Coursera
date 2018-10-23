import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.Assert;
import org.junit.Test;

public class KdTreeShould {
    private KdTree kdTree;
    private final static Point2D[] testSet5 = new Point2D[] {
            new Point2D(.7, .2), new Point2D(.5, .4),
            new Point2D(.2, .3), new Point2D(.4, .7),
            new Point2D(.9, .6)
    };

    private final static Point2D[] testSet8 = new Point2D[] {
            new Point2D(.5, .6), new Point2D(.7, .5),
            new Point2D(.3, .8), new Point2D(.2, .1),
            new Point2D(.1, .6), new Point2D(.4, .9),
            new Point2D(.6, .4), new Point2D(.9, .9)
    };

    private final static Point2D[] testRandomDistinctPoint_1 = new Point2D[] {
            new Point2D(.25, 1), new Point2D(.75, .75),
            new Point2D(.5, .5), new Point2D(.75, .125),
            new Point2D(0, .625), new Point2D(.75, .25),
            new Point2D(.25, .25)
    };

    private final static Point2D[] testRandomDistinctPoint_2 = new Point2D[] {
            new Point2D(1, 0.5), new Point2D(.5, .75),
            new Point2D(1, .75), new Point2D(0, .5),
            new Point2D(0, .25), new Point2D(1, 0),
            new Point2D(0, 1), new Point2D(1, .25),
            new Point2D(.5, .5), new Point2D(.75, 1)
    };

    private final static Point2D[] testRandomDistinctPoint_3 = new Point2D[] {
            new Point2D(.25, .25), new Point2D(.75, 0),
            new Point2D(1, .75), new Point2D(.75, .5),
            new Point2D(1, .25), new Point2D(0, .25),
            new Point2D(0, 0), new Point2D(.5, .5),
            new Point2D(.5, .75), new Point2D(.5, .25)
    };

    private final static Point2D[] testRandomDistinctPoint_4 = new Point2D[] {
            new Point2D(.7, .2), new Point2D(.5, .4),
            new Point2D(.2, .3), new Point2D(.4, .7),
            new Point2D(.9, .6)
    };

    private final static RectHV testRectZeroSet5 = new RectHV(0.1, 0.7, 0.3, 0.9);
    private final static RectHV testRect3Set8 = new RectHV(0.1, 0.2, 0.9, 0.5);

    private final static RectHV testRectZeroSet8 = new RectHV(0, 0, 0.1, 0.1);
    private final static RectHV testRect1ForTopTopSet8 = new RectHV(0.9, 0.9, 1, 1);
    private final static RectHV testRect1WithMinusSet8 = new RectHV(0, 0.4, 0.2, 0.7);
    private final static RectHV testRect3Set8_1 = new RectHV(0.5, 0.3, 0.8, 0.6);

    public KdTreeShould() {
        kdTree = new KdTree();
    }

    private final void insertPoints(int count, double step) {
        for (var i = 0.0; i < count * step; i+= step) {
            kdTree.insert(new Point2D(i, i));
        }
    }

    private final void insertPointsFromExample1() {
        for (var p: testSet5) {
            kdTree.insert(p);
        }
    }

    private final void insertPointsFromExample2() {
        for (var p: testSet8) {
            kdTree.insert(p);
        }
    }

    private final int countPointsInRange(Iterable<Point2D> iterable) {
        int count = 0;
        for (var el: iterable) {
            count++;
        }
        return count;
    }

    @Test
    public void ReturnTrueIfTreeIsEmpty() {
        Assert.assertTrue(kdTree.isEmpty());
        Assert.assertTrue(kdTree.size() == 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfParamIsNull() {
        kdTree.insert(null);
        kdTree.contains(null);
        kdTree.nearest(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfCoordinatesAreNonValid() {
        kdTree.insert(new Point2D(-1, 0));
        kdTree.insert(new Point2D(-1, -1));
        kdTree.insert(new Point2D(2, 2));
    }

    @Test
    public void Insert5Points() {
        insertPoints(5, 0.1);
        var res = kdTree.size();
        Assert.assertEquals(5, res);
    }

    @Test
    public void Insert5PointsFromExample() {
        insertPointsFromExample1();
        Assert.assertEquals(5, kdTree.size());
    }

    @Test
    public void Insert8PointsFromExample() {
        insertPointsFromExample2();
        Assert.assertEquals(8, kdTree.size());
    }

    @Test
    public void InsertDistinctPoints1() {
        for (var elem: testRandomDistinctPoint_1) {
            kdTree.insert(elem);
        }
        Assert.assertEquals(testRandomDistinctPoint_1.length, kdTree.size());
    }

    @Test
    public void ReturnTrueIfPointIsInTree1() {
        insertPointsFromExample1();
        for (var p: testSet5) {
            Assert.assertTrue(kdTree.contains(p));
        }
    }

    @Test
    public void ReturnTrueIfPointIsInTree2() {
        insertPointsFromExample2();
        for (var p: testSet8) {
            Assert.assertTrue(kdTree.contains(p));
        }
    }

    @Test
    public void ReturnFalseIfPointIsNotInTheSet() {
        insertPointsFromExample1();
        Assert.assertFalse(kdTree.contains(new Point2D(0,0)));
    }

    @Test
    public void ReturnRangeOfZeroPointsForSet5() {
        insertPointsFromExample1();
        var range = kdTree.range(testRectZeroSet5);
        Assert.assertEquals(0, countPointsInRange(range));
    }

    @Test
    public void ReturnRangeOf3PointsForSet5() {
        insertPointsFromExample1();
        var range = kdTree.range(testRect3Set8);
        Assert.assertEquals(3, countPointsInRange(range));
    }

    @Test
    public void ReturnRangeOfZeroPointsForSet8() {
        insertPointsFromExample1();
        var range = kdTree.range(testRectZeroSet8);
        Assert.assertEquals(0, countPointsInRange(range));
    }

    @Test
    public void ReturnRangeOfAPointForSet8() {
        insertPointsFromExample2();
        var range = kdTree.range(testRect1ForTopTopSet8);
        Assert.assertEquals(1, countPointsInRange(range));
    }

    @Test
    public void ReturnRangeOf3PointsForSet8() {
        insertPointsFromExample1();
        var range = kdTree.range(testRect3Set8);
        Assert.assertEquals(3, countPointsInRange(range));
    }

    @Test
    public void ReturnRangeOf1PointMinusForSet8() {
        insertPointsFromExample2();
        var range = kdTree.range(testRect1WithMinusSet8);
        Assert.assertEquals(1, countPointsInRange(range));
    }

    @Test
    public void ReturnRangeOf3PointsForSet8_1() {
        insertPointsFromExample2();
        var range = kdTree.range(testRect3Set8_1);
        Assert.assertEquals(3, countPointsInRange(range));
    }

    @Test
    public void ReturnPoint4AsNearest() {
        insertPointsFromExample1();
        var expected = testSet5[3];
        var nearest = kdTree.nearest(new Point2D(.5, .9));
        Assert.assertEquals(expected, nearest);
    }

    @Test
    public void ReturnPoint3AsNearest() {
        insertPointsFromExample1();
        var expected = testSet5[2];
        var nearest = kdTree.nearest(new Point2D(.3, .1));
        Assert.assertEquals(expected, nearest);
    }

    @Test
    public void ReturnPoint7AsNearest() {
        insertPointsFromExample2();
        var expected = testSet8[7];
        var nearest = kdTree.nearest(new Point2D(1, 1));
        Assert.assertEquals(expected, nearest);
    }

    @Test
    public void ReturnPoint6AsNearest() {
        insertPointsFromExample2();
        var expected = testSet8[1];
        var nearest = kdTree.nearest(new Point2D(.9, .2));
        Assert.assertEquals(expected, nearest);
    }

    @Test
    public void ContainNextPointForRandomDistinctPoints_2() {
        for (var elem: testRandomDistinctPoint_2) {
            kdTree.insert(elem);
        }

        Assert.assertTrue(kdTree.contains(new Point2D(.5, .5)));
    }

    @Test
    public void ReturnNextNearestPointForRandomDistinctPoints_3() {
        for (var elem: testRandomDistinctPoint_3) {
            kdTree.insert(elem);
        }
        var nearest = kdTree.nearest(new Point2D(1.0, 1.0));
        var expected = new Point2D(1, .75);
        Assert.assertEquals(expected, nearest);
    }

    @Test
    public void ReturnNextNearestPointForRandomDistinctPoints_4() {
        for (var elem: testRandomDistinctPoint_4) {
            kdTree.insert(elem);
        }
        var nearest = kdTree.nearest(new Point2D(.151, .495));
        var expected = new Point2D(.2, .3);
        Assert.assertEquals(expected, nearest);
    }
}
