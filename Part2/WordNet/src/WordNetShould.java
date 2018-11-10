import org.junit.Assert;
import org.junit.Test;

public class WordNetShould {
    private WordNet wordNet;

    private final int example1_verticesCount = 12;
    private final int[][] example1 = {
            new int[] { 1, 0 },
            new int[] { 2, 0 },

            new int[] { 3, 1 },
            new int[] { 4, 1 },
            new int[] { 5, 1 },

            new int[] { 6, 3 },
            new int[] { 7, 6 },
            new int[] { 8, 5 },
            new int[] { 9, 5 },

            new int[] { 10, 9 },
            new int[] { 11, 9 }
    };

    private final int example2_verticesCount = 6;
    private final int[][] example2 = {
            new int[] { 1, 0 },
            new int[] { 1, 2 },
            new int[] { 2, 3 },
            new int[] { 3, 4 },
            new int[] { 4, 5 },
            new int[] { 5, 0 }
    };

    private final int example3_verticesCount = 10;
    private final int[][] example3 = {
            new int[] { 1, 0 },
            new int[] { 2, 0 },
            new int[] { 7, 1 },
            new int[] { 3, 1 },
            new int[] { 4, 2 },
            new int[] { 5, 2 },
            new int[] { 5, 6 },
            new int[] { 8, 3 },
            new int[] { 9, 3 }
    };

    private final int example4_verticesCount = 13;
    private final int[][] example4 = {
            new int[] { 7, 3 },
            new int[] { 8, 3 },
            new int[] { 3, 1 },
            new int[] { 4, 1 },
            new int[] { 5, 1 },
            new int[] { 9, 5 },
            new int[] { 10, 5 },
            new int[] { 11, 10 },
            new int[] { 12, 10 },
            new int[] { 1, 0 },
            new int[] { 2, 0 }
    };

    public WordNetShould() {
        wordNet = new WordNet();
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnIllegalArgumentExceptionInConstructor() {
        wordNet = new WordNet(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnIllegalArgumentExceptionInIsNounFunction() {
        wordNet.isNoun(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnIllegalArgumentExceptionInSapFunction() {
        wordNet.sap(null, null);
    }

    @Test
    public void ReturnCorrectDistanceFromExample1() {
        wordNet = new WordNet(example1, example1_verticesCount);
        var nounA = "3";
        var nounB = "10";
        var distance = wordNet.distance(nounA, nounB);
        Assert.assertEquals(4, distance);
    }

    @Test
    public void ReturnCorrectSapFromExample1() {
        wordNet = new WordNet(example1, example1_verticesCount);
        var nounA = "3";
        var nounB = "10";
        var sap = wordNet.sap(nounA, nounB);
        Assert.assertEquals("1", sap);
    }

    @Test
    public void ReturnCorrectDistanceFromExample2() {
        wordNet = new WordNet(example2, example2_verticesCount);
        var nounA = "1";
        var nounB = "5";
        var distance = wordNet.distance(nounA, nounB);
        Assert.assertEquals(2, distance);
    }

    @Test
    public void ReturnCorrectSapFromExample2() {
        wordNet = new WordNet(example2, example2_verticesCount);
        var nounA = "1";
        var nounB = "5";
        var sap = wordNet.sap(nounA, nounB);
        Assert.assertEquals("0", sap);
    }

    @Test
    public void ReturnCorrectDistanceFromExample3() {
        wordNet = new WordNet(example3, example3_verticesCount);
        var nounA = "7";
        var nounB = "9";
        var distance = wordNet.distance(nounA, nounB);
        Assert.assertEquals(3, distance);
    }

    @Test
    public void ReturnCorrectSapFromExample3() {
        wordNet = new WordNet(example3, example3_verticesCount);
        var nounA = "7";
        var nounB = "9";
        var sap = wordNet.sap(nounA, nounB);
        Assert.assertEquals("1", sap);
    }

    @Test
    public void ReturnCorrectDistanceFromExample4() {
        wordNet = new WordNet(example4, example4_verticesCount);
        var nounA = "3";
        var nounB = "11";
        var distance = wordNet.distance(nounA, nounB);
        Assert.assertEquals(4, distance);
    }

    @Test
    public void ReturnCorrectSapFromExample4() {
        wordNet = new WordNet(example4, example4_verticesCount);
        var nounA = "3";
        var nounB = "11";
        var sap = wordNet.sap(nounA, nounB);
        Assert.assertEquals("1", sap);
    }
}
