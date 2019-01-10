import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BurrowsWheeler {

    // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
    public static void transform() {
        String s = BinaryStdIn.readString();

        SuffixesGenerator generator = new SuffixesGenerator(s);

        String[] sortedSuffixes = generator.getSortedSuffixes();

        char lastCharacter = s.charAt(s.length() - 1);

        // name from the assignment
        int first = 0;
        char[] t = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            t[i] = sortedSuffixes[i].charAt(s.length() - 1);
            if (t[i] == lastCharacter) {
                first = i;
            }
        }

        BinaryStdOut.write(first);
        for (char c: t) {
            BinaryStdOut.write(c);
        }
        BinaryStdOut.write("\n");
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform() {

    }

    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length > 1)
            System.setIn(new FileInputStream(args[1]));

        if (args[0].equals("-")) transform();
        else if (args[0].equals("+")) inverseTransform();
    }
}