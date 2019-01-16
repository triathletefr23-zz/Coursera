import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Arrays;

public class CircularSuffixArray {
    private final String string;
    private final Integer[] indexes;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null || s.equals("")) throw new IllegalArgumentException();
        string = s;

        indexes = new Integer[s.length()];
        for (int i = 0; i < s.length(); i++) {
            indexes[i] = i;
        }

        Arrays.sort(indexes, (first, second) -> {
            int startFirst = first;
            int startSecond = second;

            for (char ignored : string.toCharArray()) {
                if (startFirst > string.length() - 1) {
                    startFirst = 0;
                }

                if (startSecond > string.length() - 1) {
                    startSecond = 0;
                }

                if (string.charAt(startFirst) > string.charAt(startSecond))
                    return 1;

                if (string.charAt(startFirst) < string.charAt(startSecond))
                    return -1;

                if (string.charAt(startFirst) == string.charAt(startSecond)) {
                    startFirst++;
                    startSecond++;
                }
            }

            return 0;
        });
    }


    // length of s
    public int length() {
        return string.length();
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i > length()) throw new IllegalArgumentException();

        return indexes[i];
    }

    // unit testing (required)
    public static void main(String[] args) {

        CircularSuffixArray array = new CircularSuffixArray(BinaryStdIn.readString());
        BinaryStdOut.write(array.length());
        BinaryStdOut.write(array.index(0));
    }
}