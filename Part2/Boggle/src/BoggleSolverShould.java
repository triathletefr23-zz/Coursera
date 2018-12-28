import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class BoggleSolverShould {
    private final static String ALGS4_DICT_PATH = "data\\dictionary-algs4.txt";
    private final String[] algs4_dict;
    private final static BoggleBoard BOARD4x4 = new BoggleBoard("data\\board4x4.txt");
    private final static BoggleBoard BOARD_NOON = new BoggleBoard("data\\board-noon.txt");
    private final static BoggleBoard BOARD4x4_Qu = new BoggleBoard("data\\board-q.txt");
    private BoggleSolver boggleSolver;

    public BoggleSolverShould() {
        var dictionary = readDictionary(ALGS4_DICT_PATH);
        boggleSolver = new BoggleSolver(dictionary);
        var in = new In(ALGS4_DICT_PATH);
        algs4_dict = in.readAllStrings();
    }

    private String[] readDictionary(String filePath) {
        var in = new In(filePath);
        var list = new ArrayList<String>();
        while (in.hasNextLine()) {
            list.add(in.readLine());
        }
        return list.toArray(new String[]{ });
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
        boggleSolver = new BoggleSolver(algs4_dict);
        Assert.assertEquals(0, boggleSolver.scoreOf("hi"));
        Assert.assertEquals(1, boggleSolver.scoreOf("test"));
        Assert.assertEquals(2, boggleSolver.scoreOf("hello"));
    }

    @Test
    public void Return11PointsForCurrentWords() {
        boggleSolver = new BoggleSolver(algs4_dict);
        Assert.assertEquals(11, boggleSolver.scoreOf("triangle"));
    }

    @Test
    public void GetAllPossibleWordsForBOARD4x4() {
        var score = getScoreOfTheBoard(BOARD4x4, algs4_dict);
        Assert.assertEquals(33, score);
    }

    @Test
    public void GetAllPossibleWordsForBOARD4x4_NOON() {
        var score = getScoreOfTheBoard(BOARD_NOON, algs4_dict);
        Assert.assertEquals(1, score);
    }

    @Test
    public void GetAllPossibleWordsForBOARD4x4_Qu() {
        var score = getScoreOfTheBoard(BOARD4x4_Qu, algs4_dict);
        Assert.assertEquals(84, score);
    }

    private int getScoreOfTheBoard(BoggleBoard board, String[] dict) {
        boggleSolver = new BoggleSolver(dict);
        var score = 0;
        var actual = boggleSolver.getAllValidWords(board);
        for (var word: actual) {
            score += boggleSolver.scoreOf(word);
        }
        return score;
    }
}
