import com.google.common.collect.Iterables;
import edu.princeton.cs.algs4.TrieSET;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

public class AllPathShould {
    private final static BoggleBoard BOARD4x4 = new BoggleBoard("data\\board4x4.txt");
    private final static BoggleBoard BOARD4x4_Qu = new BoggleBoard("data\\board-q.txt");
    private final static String[] DICTIONARY = new In("data\\dictionary-algs4.txt").readAllStrings();
    private static Helper HELPER = new Helper(BOARD4x4.rows(), BOARD4x4.cols());
    private AllPaths modifiedDFS;
    private TrieSET dict;

    public AllPathShould() {
        dict = new TrieSET();
        for (var elem: DICTIONARY) {
            dict.add(elem);
        }
    }

    @Test
    public void ReturnAllPaths() {
        var list = new ArrayList<String>();
        for (int i = 0; i < BOARD4x4.rows() * BOARD4x4.cols(); i++) {
            for (int j = 0; j < BOARD4x4.rows() * BOARD4x4.cols(); j++) {
                modifiedDFS = new AllPaths(BOARD4x4, i, j);
                list.addAll((Collection<String>) modifiedDFS.getPossibleStrings());
            }
        }
        var count = countAllElements(list);
        Assert.assertTrue(count > 0);
    }

    @Test
    public void ReturnAllWordsForCurrentPoint() {
        modifiedDFS = new AllPaths(BOARD4x4, 6, 11, dict);
        var count = Iterables.size(modifiedDFS.getPossibleStringsWithPrefixOptimisation());
        Assert.assertTrue(count > 0);
    }

    @Test
    public void ReturnAllWordsForCurrentPointWithQu() {
        modifiedDFS = new AllPaths(BOARD4x4_Qu, 8, 1, dict);
        var count = Iterables.size(modifiedDFS.getPossibleStringsWithPrefixOptimisation());
        Assert.assertTrue(count > 0);
    }

    @Test
    public void ReturnAllWords() {
        var list = new ArrayList<String>();
        for (int i = 0; i < BOARD4x4.rows() * BOARD4x4.cols(); i++) {
            for (int j = 0; j < BOARD4x4.rows() * BOARD4x4.cols(); j++) {
                modifiedDFS = new AllPaths(BOARD4x4, i, j, dict);
                list.addAll((Collection<String>) modifiedDFS.getPossibleStringsWithPrefixOptimisation());
            }
        }
        var count = countAllElements(list);
        Assert.assertTrue(count > 0);
    }



    private int countAllElements(Iterable<String> strings) {
        var count = 0;
        for (var ignored : strings) {
            count++;
        }
        return count;
    }
}
