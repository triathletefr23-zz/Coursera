import com.google.common.collect.Iterables;
import org.junit.Assert;
import org.junit.Test;

public class BoggleSolverShould {
    private final static String[] ALGS4_DICTIONARY = new In("data\\dictionary-algs4.txt").readAllStrings();
    private final static String[] YAWL_DICTIONARY = new In("data\\dictionary-yawl.txt").readAllStrings();
    private final static String[] ZING_DICTIONARY = new In("data\\dictionary-zingarelli2005.txt").readAllStrings();
    private final static BoggleBoard BOARD4x4 = new BoggleBoard("data\\board4x4.txt");
    private final static BoggleBoard BOARD_NOON = new BoggleBoard("data\\board-noon.txt");
    private final static BoggleBoard BOARD4x4_Qu = new BoggleBoard("data\\board-q.txt");
    private BoggleSolver boggleSolver;

    public BoggleSolverShould() {
        boggleSolver = new BoggleSolver(ALGS4_DICTIONARY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfDictInConstructorIsNull() {
        boggleSolver = new BoggleSolver(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfDictInConstructorContainsNoElements() {
        boggleSolver = new BoggleSolver(new String[]{ });
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfBoardIsNull() {
        boggleSolver.getAllValidWords(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfWordIsNull() {
        boggleSolver.scoreOf(null);
    }

    @Test
    public void ReturnLessThan11PointsForCurrentWords() {
        Assert.assertEquals(0, boggleSolver.scoreOf("hi"));
        Assert.assertEquals(1, boggleSolver.scoreOf("test"));
        Assert.assertEquals(2, boggleSolver.scoreOf("hello"));
    }

    @Test
    public void Return11PointsForCurrentWords() {
        Assert.assertEquals(11, boggleSolver.scoreOf("triangle"));
    }

    @Test
    public void Return29WordsForBoard4x4() {
        var words = boggleSolver.getAllValidWords(BOARD4x4);
        Assert.assertEquals(29, Iterables.size(words));
    }

    @Test
    public void GetAllPossibleWordsForBOARD4x4() {
        var score = getScoreOfTheBoard(BOARD4x4);
        Assert.assertEquals(33, score);
    }

    @Test
    public void GetAllPossibleWordsForBOARD4x4_NOON() {
        var score = getScoreOfTheBoard(BOARD_NOON);
        Assert.assertEquals(1, score);
    }

    @Test
    public void GetAllPossibleWordsForBOARD4x4_Qu() {
        var score = getScoreOfTheBoard(BOARD4x4_Qu);
        Assert.assertEquals(84, score);
    }

    @Test
    public void TimingTestYAWLDictionary() {
        boggleSolver = new BoggleSolver(YAWL_DICTIONARY);
        var startTime = System.currentTimeMillis();
        boggleSolver.getAllValidWords(BOARD4x4);
        var time = System.currentTimeMillis() - startTime;
        Assert.assertTrue(time < 1500);
    }

    @Test
    public void TimingTestZingDictionary() {
        boggleSolver = new BoggleSolver(ZING_DICTIONARY);
        var startTime = System.currentTimeMillis();
        boggleSolver.getAllValidWords(BOARD4x4);
        var time = System.currentTimeMillis() - startTime;
        Assert.assertTrue(time < 2500);
    }

    @Test
    public void GetAllPossibleWordsForBOARD4x4FromYAWL() {
        boggleSolver = new BoggleSolver(YAWL_DICTIONARY);
        var words = boggleSolver.getAllValidWords(BOARD4x4);
        Assert.assertTrue(Iterables.size(words) > 0);
    }

    private int getScoreOfTheBoard(BoggleBoard board) {
        var score = 0;
        var actual = boggleSolver.getAllValidWords(board);
        for (var word: actual) {
            score += boggleSolver.scoreOf(word);
        }
        return score;
    }
}
