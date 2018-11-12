import org.junit.Test;

public class OutcastShould {
    private final String[] example5 = { "horse", "zebra", "cat", "bear", "table" };
    private final String[] example8 = { "water", "soda", "bed", "orange_juice",
            "milk", "apple_juice", "tea", "coffee" };
    private final String[] example11 = { "apple", "pear", "peach", "banana", "lime", "lemon", "blueberry", "strawberry",
            "mango", "watermelon", "potato" };
    private Outcast outcast;

    public OutcastShould() {
        outcast = new Outcast(new WordNet());
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfWordnetIsNull() {
        new Outcast(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfArgumentInOutcastIsNull() {
        outcast.outcast(null);
    }
}
