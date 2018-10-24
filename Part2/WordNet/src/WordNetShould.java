import org.junit.Test;

public class WordNetShould {
    private WordNet wordNet;

    public WordNetShould() {
        wordNet = new WordNet("synset", "hypermym");
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
}
