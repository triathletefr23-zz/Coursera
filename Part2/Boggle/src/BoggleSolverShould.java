import org.junit.Test;

import java.util.ArrayList;

public class BoggleSolverShould {
    private final static String BOARD4x4_PATH = "data\\board4x4.txt";
    private final static String ALGS4_DICT_PATH = "data\\dictionary-algs4.txt";
    private final static BoggleBoard BOARD4x4 = new BoggleBoard(BOARD4x4_PATH);
    private BoggleSolver boggleSolver;

    public BoggleSolverShould() {
        var dictionary = readDictionary(ALGS4_DICT_PATH);
        boggleSolver = new BoggleSolver(dictionary);
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
}
