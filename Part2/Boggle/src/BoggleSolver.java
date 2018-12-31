import java.util.HashMap;
import java.util.HashSet;

public class BoggleSolver
{
    private final HashMap<Integer, Integer> pointsDictionary;
    private final String[] array;
    private TrieSET dictionary;
    private Helper helper;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        if (dictionary == null || dictionary.length == 0) {
            throw new IllegalArgumentException();
        }

        array = dictionary.clone();
        this.pointsDictionary = new HashMap<>();
        createPointsDictionary();
    }

    private void createPointsDictionary() {
        pointsDictionary.put(0, 0);
        pointsDictionary.put(1, 0);
        pointsDictionary.put(2, 0);
        pointsDictionary.put(3, 1);
        pointsDictionary.put(4, 1);
        pointsDictionary.put(5, 2);
        pointsDictionary.put(6, 3);
        pointsDictionary.put(7, 5);
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        if (board == null) {
            throw new IllegalArgumentException();
        }

        this.dictionary = new TrieSET();
        for (String word: this.array) {
            this.dictionary.add(word);
        }

        int rows = board.rows();
        int cols = board.cols();
        helper = new Helper(rows, cols);

        HashSet<String> validWords = new HashSet<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                boolean[][] marked = new boolean[rows][cols];
                collectWords(board, i, j, marked, "", validWords);
            }
        }

        return validWords;
    }

    private void collectWords(BoggleBoard board, int i, int j, boolean[][] marked, String prefix, HashSet<String> validWords) {
        if (marked[i][j]) {
            return;
        }

        char letter = board.getLetter(i, j);
        String word = prefix + (letter == 'Q' ? "QU" : letter);

        if (!dictionary.hasPrefix(word)) {
            return;
        }

        if (word.length() > 2 && dictionary.contains(word)) {
            validWords.add(word);
        }

        marked[i][j] = true;

        Iterable<int[]> adj = helper.findAdjacentPoints(i, j);
        for (int[] pair : adj) {
            collectWords(board, pair[0], pair[1], marked, word, validWords);
        }

        marked[i][j] = false;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }

        if (word.length() < 8) return pointsDictionary.get(word.length());
        return 11;
    }
}