import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Stack;

public class MoveToFront {
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        String s = BinaryStdIn.readString();

        Stack<Integer> codedValue = codeString(s);
        for (Integer num: codedValue) {
            BinaryStdOut.write(num);
        }
        BinaryStdOut.close();

//        printInHex(codedValue, s.length());
    }

    private static Stack<Integer> codeString(String s)
    {
        char[] input = s.toCharArray();
        HashMap<Character, Integer> alphabet = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            alphabet.put((char)i, i);
        }

        Stack<Integer> codedValue = new Stack<>();
        for (char c : input) {
            int num = alphabet.get(c);
            alphabet.remove(c);
            codedValue.push(num);

            alphabet.forEach((key, value) -> {
                if (value < num) {
                    alphabet.replace(key, value, value + 1);
                }
            });

            alphabet.put(c, 0);
        }

        return codedValue;
    }

    private static void printInHex(Iterable<Integer> list, int length) {
        for (Integer value : list) {
            StdOut.print(" ");
            StdOut.printf("%02x", value & 0xff);
        }

        StdOut.println();
        StdOut.println(length * 8 + " bits");
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    // static
    public static void decode() {
        char[] alphabet = new char[256];
        for (int i = 0; i < alphabet.length; i++) {
            alphabet[i] = (char)i;
        }

        Deque<Character> deque = new ArrayDeque<>();

        while (!BinaryStdIn.isEmpty()) {
            int num = BinaryStdIn.readInt();
            char c = alphabet[num];
            deque.push(c);
            System.arraycopy(alphabet, 0, alphabet, 1, num);
            alphabet[0] = c;
        }

        while (deque.size() != 0) {
            BinaryStdOut.write(deque.pop());
        }
    }

    private static void decode(Iterable<Integer> encoded) {
        char[] alphabet = new char[256];
        for (int i = 0; i < alphabet.length; i++) {
            alphabet[i] = (char) i;
        }

        Deque<Character> deque = new ArrayDeque<>();
        for (int num : encoded) {
            char c = alphabet[num];
            deque.push(c);
            System.arraycopy(alphabet, 0, alphabet, 1, num);
            alphabet[0] = c;
        }

        while (deque.size() != 0) {
            StdOut.print(deque.pollLast());
        }
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length > 1)
            System.setIn(new FileInputStream(args[1]));

        if      (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
//        {
//            Iterable<Integer> encoded = codeString(BinaryStdIn.readString());
//            decode(encoded);
//        }
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
