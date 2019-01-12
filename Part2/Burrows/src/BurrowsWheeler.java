import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class BurrowsWheeler {

    // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
    public static void transform() {
        String s = BinaryStdIn.readString();

        SuffixesGenerator generator = new SuffixesGenerator(s);

        int first = generator.getFirst();
        char[] t = generator.getT();

        BinaryStdOut.write(first);
        for (char c: t) {
            BinaryStdOut.write(c);
        }
        BinaryStdOut.write("\n");
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();
        char[] t = s.toCharArray();

        char[] sorted = new char[t.length];
        HashMap<Character, ArrayList<Integer>> uniqueChars = new HashMap<>();
        int[] next = new int[t.length];

        for (int i = 0; i < t.length; i++) {
            sorted[i] = t[i];
            ArrayList<Integer> indexes = new ArrayList<>();
            if (uniqueChars.containsKey(t[i])) {
                indexes = uniqueChars.get(t[i]);
                uniqueChars.remove(t[i]);
            }
            indexes.add(i);
            uniqueChars.put(t[i], indexes);
            next[i] = -1;
        }

        Arrays.sort(sorted);

        String sortedS = new String(sorted);
        for (int i = 0; i < t.length; i++) {
            if (uniqueChars.containsKey(t[i]) && uniqueChars.get(t[i]).size() == 1) {
                next[sortedS.indexOf(t[i])] = s.indexOf(t[i]);
                uniqueChars.remove(t[i]);
            }
        }

        for (int i = 0; i < t.length; i++) {
            ArrayList<Integer> indexes = uniqueChars.get(sorted[i]);
            if (next[i] == -1 && indexes != null && indexes.size() > 0) {
                Collections.sort(indexes);
                next[i] = indexes.get(0);
                indexes.remove(0);
                uniqueChars.replace(sorted[i], indexes);
            }
        }

        StringBuilder builder = new StringBuilder();
        int nextElement = first;
        do {
            builder.append(sorted[nextElement]);
            nextElement = next[nextElement];
        } while (nextElement != first);

        BinaryStdOut.write(builder.toString());
        BinaryStdOut.close();
    }

    public static void inverseTransform(boolean test) {
        String input = BinaryStdIn.readString();
        SuffixesGenerator generator = new SuffixesGenerator(input);

        int first = generator.getFirst();
        char[] t = generator.getT();
        String s = new String(t);
        char[] sorted = new char[t.length];
        HashMap<Character, ArrayList<Integer>> uniqueChars = new HashMap<>();
        int[] next = new int[t.length];

        for (int i = 0; i < t.length; i++) {
            sorted[i] = t[i];
            ArrayList<Integer> indexes = new ArrayList<>();
            if (uniqueChars.containsKey(t[i])) {
                indexes = uniqueChars.get(t[i]);
                uniqueChars.remove(t[i]);
            }
            indexes.add(i);
            uniqueChars.put(t[i], indexes);
            next[i] = -1;
        }

        Arrays.sort(sorted);

        String sortedS = new String(sorted);
        for (int i = 0; i < t.length; i++) {
            if (uniqueChars.containsKey(t[i]) && uniqueChars.get(t[i]).size() == 1) {
                next[sortedS.indexOf(t[i])] = s.indexOf(t[i]);
                uniqueChars.remove(t[i]);
            }
        }

        for (int i = 0; i < t.length; i++) {
            ArrayList<Integer> indexes = uniqueChars.get(sorted[i]);
            if (next[i] == -1 && indexes != null && indexes.size() > 0) {
                Collections.sort(indexes);
                next[i] = indexes.get(0);
                indexes.remove(0);
                uniqueChars.replace(sorted[i], indexes);
            }
        }

        StringBuilder builder = new StringBuilder();
        int nextElement = first;
        do {
            builder.append(sorted[nextElement]);
            nextElement = next[nextElement];
        } while (nextElement != first);

        BinaryStdOut.write(builder.toString());
        BinaryStdOut.close();
    }

    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length > 1)
            System.setIn(new FileInputStream(args[1]));

        if (args[0].equals("-")) transform();
        else if (args[0].equals("+")) inverseTransform();
//        else if (args[0].equals("+")) {
//            inverseTransform(true);
//        }
    }
}