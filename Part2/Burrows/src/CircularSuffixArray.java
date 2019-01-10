import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.StdOut;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public class CircularSuffixArray {
    private final Map<String, Integer> originalSuffixes;
    private final String[] sortedSuffixes;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) throw new IllegalArgumentException();

        SuffixesGenerator generator = new SuffixesGenerator(s);
        originalSuffixes = generator.getOriginalSuffixes();
        sortedSuffixes = generator.getSortedSuffixes();
    }

//    private void printSuffixes(Iterable values) {
//        for (var el : values) {
//            StdOut.println(el);
//        }
//    }

    // length of s
    public int length() {
        return originalSuffixes.size();
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i > length()) throw new IllegalArgumentException();

        return originalSuffixes.get(sortedSuffixes[i]);
    }

    // unit testing (required)
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length > 1)
            System.setIn(new FileInputStream(args[1]));

        CircularSuffixArray array = new CircularSuffixArray(BinaryStdIn.readString());
        StdOut.println("Length of the word " + array.length());
        StdOut.println("Index of the 11th suffix in the sorted array is " + array.index(11));
    }
}