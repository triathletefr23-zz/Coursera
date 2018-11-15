import org.junit.Assert;
import org.junit.Test;

public class OutcastShould {
    private final String[] example5 = { "horse", "zebra", "cat", "bear", "table" };
    private final String[] example8 = { "water", "soda", "bed", "orange_juice",
            "milk", "apple_juice", "tea", "coffee" };
    private final String[] example11 = { "apple", "pear", "peach", "banana", "lime", "lemon", "blueberry", "strawberry",
            "mango", "watermelon", "potato" };
    private Outcast outcast;

    public OutcastShould() {
        WordNet wordnet = new WordNet("data\\synsets.txt", "data\\hypernyms.txt");
        outcast = new Outcast(wordnet);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfWordnetIsNull() {
        outcast = new Outcast(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfArgumentInOutcastIsNull() {
        outcast.outcast(null);
    }

    @Test
    public void ReturnTableForExample5() {
        var res = outcast.outcast(example5);
        Assert.assertEquals("table", res);
    }

    @Test
    public void ReturnTableForExample8() {
        var res = outcast.outcast(example8);
        Assert.assertEquals("bed", res);
    }

    @Test
    public void ReturnTableForExample11() {
        var res = outcast.outcast(example11);
        Assert.assertEquals("potato", res);
    }
}
