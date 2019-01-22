import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Arrays;
import java.util.Comparator;

public class CircularSuffixArray {
//    private final String string;
//    private final Integer[] indexes;
//
//    // circular suffix array of s
//    public CircularSuffixArray(String s) {
//        if (s == null || s.equals("")) throw new IllegalArgumentException();
//        string = s;
//
//        indexes = new Integer[s.length()];
//        for (int i = 0; i < s.length(); i++) {
//            indexes[i] = i;
//        }
//
//        Arrays.sort(indexes, (first, second) -> {
//            int startFirst = first;
//            int startSecond = second;
//
//            for (char ignored : string.toCharArray()) {
//                if (startFirst > string.length() - 1) {
//                    startFirst = 0;
//                }
//
//                if (startSecond > string.length() - 1) {
//                    startSecond = 0;
//                }
//
//                if (string.charAt(startFirst) > string.charAt(startSecond))
//                    return 1;
//
//                if (string.charAt(startFirst) < string.charAt(startSecond))
//                    return -1;
//
//                if (string.charAt(startFirst) == string.charAt(startSecond)) {
//                    startFirst++;
//                    startSecond++;
//                }
//            }
//
//            return 0;
//        });
//    }
//
//    // length of s
//    public int length() {
//        return string.length();
//    }
//
//    // returns index of ith sorted suffix
//    public int index(int i) {
//        if (i < 0 || i > length()) throw new IllegalArgumentException();
//
//        return indexes[i];
//    }
//
//    // unit testing (required)
//    public static void main(String[] args) {
//
//        CircularSuffixArray array = new CircularSuffixArray(BinaryStdIn.readString());
//        BinaryStdOut.write(array.length());
//        BinaryStdOut.write(array.index(0));
//    }

    private final String s;
    private final Integer[] indices;

    public CircularSuffixArray(String s) {
        this.s = notNull(s);
        this.indices = countIndices(s);
    }

    private Integer[] countIndices(String word) {
        Integer[] shifts = new Integer[word.length()];
        for (int i = 0; i < word.length(); i++) {
            shifts[i] = i;
        }
        Arrays.sort(shifts, new RefComparator());
        return shifts;
    }

    public int length() {
        return s.length();
    }

    public int index(int i) {
        inRange(i);
        return indices[i];
    }

    private String notNull(String word) {
        if (word == null) {
            throw new IllegalArgumentException("S should be not null");
        }

        return word;
    }

    private void inRange(int i) {
        if (i < 0 || i > length() - 1) {
            throw new IllegalArgumentException("I should be between 0 and n-1");
        }
    }

    private class RefComparator implements Comparator<Integer> {

        @Override
        public int compare(Integer tShift, Integer oShift) {
            int iT = tShift;
            int iO = oShift;

            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(iT) > s.charAt(iO)) {
                    return 1;
                } else if (s.charAt(iT) < s.charAt(iO)) {
                    return -1;
                }
                iT = inc(iT);
                iO = inc(iO);
            }

            return 0;
        }

        private int inc(int i) {
            if (i < s.length() - 1) {
                return i + 1;
            } else {
                return 0;
            }
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

        CircularSuffixArray array = new CircularSuffixArray(BinaryStdIn.readString());
        BinaryStdOut.write(array.length());
        BinaryStdOut.write(array.index(0));
    }
}