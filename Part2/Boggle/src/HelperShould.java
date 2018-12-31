import com.google.common.collect.Iterables;
import org.junit.Assert;
import org.junit.Test;

public class HelperShould {
    private final Helper helper;

    public HelperShould() {
        helper = new Helper(4, 4);
    }

    @Test
    public void Return8AdjacentPointsForCentralPoint() {
        var count = Iterables.size(helper.findAdjacentPoints(1,1));
        Assert.assertEquals(8, count);
    }

    @Test
    public void Return3AdjacentPointsForCornerPoint() {
        var count = Iterables.size(helper.findAdjacentPoints(0, 0));
        Assert.assertEquals(3, count);
    }

    @Test
    public void Return5AdjacentPointsForBorderPoint() {
        var count = Iterables.size(helper.findAdjacentPoints(0, 1));
        Assert.assertEquals(5, count);
    }
}
