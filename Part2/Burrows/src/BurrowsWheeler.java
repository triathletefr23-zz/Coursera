import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Quick3string;

import java.util.Map;
import java.util.HashMap;

public class BurrowsWheeler {
    // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
    public static void transform() {

        String s = BinaryStdIn.readString();
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray(s);

        for (int i = 0; i < circularSuffixArray.length(); i++) {
            if (circularSuffixArray.index(i) == 0) {
                BinaryStdOut.write(i);
                break;
            }
        }

        for (int i = 0; i < circularSuffixArray.length(); i++) {
            int index = circularSuffixArray.index(i);
            if (index == 0) {
                BinaryStdOut.write(s.charAt(s.length() - 1));
                continue;
            }
            BinaryStdOut.write(s.charAt(index - 1));
        }

        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();

        char[] t = s.toCharArray();
        if (first > t.length) return;

        Map<String, Queue<Integer>> uniqueChars = new HashMap<>();
        int[] next = new int[t.length];

        Queue<String> strings = new Queue<>();
        for (int i = 0; i < t.length; i++) {
            String charInString = Character.toString(t[i]);
            if (!uniqueChars.containsKey(charInString)) {
                uniqueChars.put(charInString, new Queue<>());
            }
            uniqueChars.get(charInString).enqueue(i);
            strings.enqueue(charInString);
        }

        String[] keys = new String[strings.size()];
        for (int i = 0; i < keys.length; i++) {
            keys[i] = strings.dequeue();
        }

        Quick3string.sort(keys);

        int i = 0;
        for (String key: keys) {
            while (!uniqueChars.get(key).isEmpty()) {
                next[i++] = uniqueChars.get(key).dequeue();
            }
        }

        i = 0;
        for (int curRow = next[first]; i < t.length; i++, curRow = next[curRow]) {
            BinaryStdOut.write(t[curRow]);
        }

        BinaryStdOut.close();
    }

    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        switch (args[0]) {
            case "-":
                transform();
                break;
            case "+":
                inverseTransform();
                break;
            default:
                throw new IllegalArgumentException("Illegal command line argument");
        }
    }
}