import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.TrieSET;

import java.util.HashMap;
import java.util.TreeSet;



public class BoggleSolver
{
    private final HashMap<Integer, Integer> pointsDictionary;
    private final TrieSET wordsDictionary;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        if (dictionary == null || dictionary.length == 0) {
            throw new IllegalArgumentException();
        }

        pointsDictionary = new HashMap<>();
        createPointsDictionary();

        wordsDictionary = new TrieSET();
        for (String word: dictionary) {
            wordsDictionary.add(word);
        }
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

        int cols = board.cols();
        int rows = board.rows();

        TreeSet<String> set = new TreeSet<>();
        for (int i = 0; i < rows * cols; i++) {
            for (int j = 0; j < rows * cols; j++) {
                AllPaths modifiedDFS = new AllPaths(board, i, j, wordsDictionary);
                for (String string: modifiedDFS.getPossibleStringsWithPrefixOptimisation()) {
                    set.add(string);
                }
            }
        }

        return set;
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

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}