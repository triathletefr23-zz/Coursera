import org.junit.Assert;
import org.junit.Test;

public class LineSegmentShould {
    LineSegment line;

    public LineSegmentShould() {
        Point start = new Point(1, 1);
        Point finish = new Point(3,3);
        line = new LineSegment(start, finish);
    }

    @Test
    public void ShowLineSegmentInStringFormat() {
        var res = line.toString();

        Assert.assertTrue(res.contains(" -> "));
    }
}
