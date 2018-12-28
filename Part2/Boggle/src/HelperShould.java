import com.google.common.collect.Iterables;
import org.junit.Assert;
import org.junit.Test;

public class HelperShould {
    private final Helper helper;
    private final static int SIZE = 4;

    public HelperShould() {
        helper = new Helper(4, 5);
    }

    @Test
    public void Return8AdjacentPointsForCentralPoint() {
        var count = Iterables.size(helper.findAdjacentPoints(SIZE + 1, SIZE));
        Assert.assertEquals(8, count);
    }

    @Test
    public void Return3AdjacentPointsForCornerPoint() {
        var count = Iterables.size(helper.findAdjacentPoints(0, SIZE));
        Assert.assertEquals(3, count);
    }

    @Test
    public void Return5AdjacentPointsForBorderPoint() {
        var count = Iterables.size(helper.findAdjacentPoints(1, SIZE));
        Assert.assertEquals(5, count);
    }
}
