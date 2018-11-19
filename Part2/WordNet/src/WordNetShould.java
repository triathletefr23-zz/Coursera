import org.junit.Assert;
import org.junit.Test;

public class WordNetShould {
    private WordNet wordNet;

//    private final int example1_verticesCount = 12;
//    private final int[][] example1 = {
//            new int[] { 1, 0 },
//            new int[] { 2, 0 },
//
//            new int[] { 3, 1 },
//            new int[] { 4, 1 },
//            new int[] { 5, 1 },
//
//            new int[] { 6, 3 },
//            new int[] { 7, 6 },
//            new int[] { 8, 5 },
//            new int[] { 9, 5 },
//
//            new int[] { 10, 9 },
//            new int[] { 11, 9 }
//    };
//
//    private final int example2_verticesCount = 6;
//    private final int[][] example2 = {
//            new int[] { 1, 0 },
//            new int[] { 1, 2 },
//            new int[] { 2, 3 },
//            new int[] { 3, 4 },
//            new int[] { 4, 5 },
//            new int[] { 5, 0 }
//    };
//
//    private final int example3_verticesCount = 10;
//    private final int[][] example3 = {
//            new int[] { 1, 0 },
//            new int[] { 2, 0 },
//            new int[] { 7, 1 },
//            new int[] { 3, 1 },
//            new int[] { 4, 2 },
//            new int[] { 5, 2 },
//            new int[] { 5, 6 },
//            new int[] { 8, 3 },
//            new int[] { 9, 3 }
//    };
//
//    private final int example4_verticesCount = 13;
//    private final int[][] example4 = {
//            new int[] { 7, 3 },
//            new int[] { 8, 3 },
//            new int[] { 3, 1 },
//            new int[] { 4, 1 },
//            new int[] { 5, 1 },
//            new int[] { 9, 5 },
//            new int[] { 10, 5 },
//            new int[] { 11, 10 },
//            new int[] { 12, 10 },
//            new int[] { 1, 0 },
//            new int[] { 2, 0 }
//    };

    public WordNetShould() {
        wordNet = new WordNet("data\\synsets.txt", "data\\hypernyms.txt");
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
    public void ContainNumberOfNounsInWordnetForAfterParsingSynsets() {
        var nouns = wordNet.nouns();
        var count = 0;
        for (String elem: nouns) {
            count++;
        }
        Assert.assertEquals(119188, count);
    }

    @Test
    public void ReturnCorrectSapForRemoteExamples1() {
        var nounA = "white_marlin";
        var nounB = "mileage";
        var distance = wordNet.distance(nounA, nounB);
        Assert.assertEquals(23, distance);
    }

    @Test
    public void ReturnCorrectSapForRemoteExamples2() {
        var nounA = "Black_Plague";
        var nounB = "black_marlin";
        var distance = wordNet.distance(nounA, nounB);
        Assert.assertEquals(33, distance);
    }

    @Test
    public void ReturnCorrectSapForRemoteExamples3() {
        var nounA = "American_water_spaniel";
        var nounB = "histology";
        var distance = wordNet.distance(nounA, nounB);
        Assert.assertEquals(27, distance);
    }

    @Test
    public void ReturnCorrectSapForRemoteExamples4() {
        var nounA = "Brown_Swiss";
        var nounB = "barrel_roll";
        var distance = wordNet.distance(nounA, nounB);
        Assert.assertEquals(29, distance);
    }

    @Test
    public void ReturnCorrectSapAndDistanceFor100SubgraphExample() {
        var nounA = "factor_XII";
        var nounB = "factor_I";
        wordNet = new WordNet("data\\synsets100-subgraph.txt",
                "data\\hypernyms100-subgraph.txt");
        var distance = wordNet.distance(nounA, nounB);
        var sap = wordNet.sap(nounA, nounB);
        Assert.assertEquals(2, distance);
        Assert.assertEquals("coagulation_factor clotting_factor", sap);
    }
}
