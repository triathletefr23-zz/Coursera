import org.junit.Assert;
import org.junit.Test;

public class CircularSuffixArrayShould {
    private final static String ABRA_STRING = new In("data\\abra.txt").readString();
    private final static String ALPHA_STRING = new In("data\\alphanum.txt").readString();
    private final static String COUSCOUS_STRING = new In("data\\couscous.txt").readString();
    private CircularSuffixArray array;

    public CircularSuffixArrayShould() {
        array = new CircularSuffixArray(ABRA_STRING);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnIllegalArgumentExceptionIfInitialStringIsNull() {
        array = new CircularSuffixArray(null);
    }

//    @Test
//    public void Create2DArrayWithSuffixes() {
//
//    }

    @Test
    public void ReturnLengthOfAbra() {
        Assert.assertEquals(ABRA_STRING.length(), array.length());
    }

    @Test
    public void ReturnIndexOfAbraInSortedArray() {
        Assert.assertEquals(2, array.index(11));
    }
}
