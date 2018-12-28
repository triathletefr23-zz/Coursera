import edu.princeton.cs.algs4.TrieSET;

import java.util.*;

public class AllPaths {
    private final int rows;
    private final int cols;
    private final Helper helper;
    private TrieSET dict;
    private boolean[] onPath;        // vertices in current path
    private Stack<Integer> path;     // the current path
    private Set<String> allStrings;
    private Set<String> wordsFromDictionary;

    // show all simple paths from s to t - use DFS
    public AllPaths(BoggleBoard board, int s, int t) {
        rows = board.rows();
        cols = board.cols();
        onPath = new boolean[rows * cols];
        path = new Stack<>();
        allStrings = new HashSet<>();
        helper = new Helper(rows, cols);
        dfs(board, s, t);
    }

    public AllPaths(BoggleBoard board, int s, int t, TrieSET dict) {
        rows = board.rows();
        cols = board.cols();
        onPath = new boolean[rows*cols];
        path = new Stack<>();
        wordsFromDictionary = new HashSet<>();
        helper = new Helper(rows, cols);
        this.dict = dict;
        optimizedDfs(board, s, t);
    }

    // use DFS
    private void dfs(BoggleBoard board, int v, int t) {
        // add v to current path
        path.push(v);
        onPath[v] = true;

        // found path from s to t
        if (v == t) {
            addPathToAllStrings(board);
        }

        // consider all neighbors that would continue path with repeating a node
        else {
            Iterable<Integer> adj = helper.findAdjacentPoints(v, cols);
            for (int i: adj) {
                if (onPath[i]) continue;
                dfs(board, i, t);
            }
        }

        // done exploring from v, so remove from path
        path.pop();
        onPath[v] = false;
    }

    private void optimizedDfs(BoggleBoard board, int v, int t) {
        path.push(v);
        onPath[v] = true;
        if (!checkPrefixInDictionary(board)) {
            if (v == t) {
                addPathToWords(board);
            }

            // consider all neighbors that would continue path with repeating a node
            else {
                Iterable<Integer> adj = helper.findAdjacentPoints(v, cols);
                for (int i : adj) {
                    if (onPath[i]) continue;
                    optimizedDfs(board, i, t);
                }
            }
        }
        // done exploring from v, so remove from path
        path.pop();
        onPath[v] = false;
    }

    private boolean checkPrefixInDictionary(BoggleBoard board) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int dice: path) {
            char letter = board.getLetter(dice / cols, dice % cols);
            stringBuilder.append(letter == 'Q' ? "QU" : letter);
        }
        String prefix = stringBuilder.toString();
        Iterable<String> words = dict.keysWithPrefix(prefix);
        if (dict.contains(prefix)) wordsFromDictionary.add(prefix);
        return helper.getSizeOfIterable(words) == 0;
    }

    private String reverseCurrentPath(BoggleBoard board) {
        Stack<Integer> reverse = new Stack<>();
        for (int v : path) {
            reverse.push(v);
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int index: path) {
            stringBuilder.append(board.getLetter(index / cols, index % cols));
        }
        return stringBuilder.toString();
    }

    private void addPathToWords(BoggleBoard board) {
        String string = reverseCurrentPath(board);
        if (dict.contains(string) && string.length() > 2) wordsFromDictionary.add(string);
    }

    // this implementation just prints the path to standard output
    private void addPathToAllStrings(BoggleBoard board) {
        String string = reverseCurrentPath(board);
        allStrings.add(string);
    }

    public Iterable<String> getPossibleStrings() {
        return allStrings;
    }

    public Iterable<String> getPossibleStringsWithPrefixOptimisation() {
        return wordsFromDictionary;
    }
}
