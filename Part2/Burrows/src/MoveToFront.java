//import edu.princeton.cs.algs4.BinaryStdIn;
//import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    private static final int R = 256;

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        char[] alphabet = initAlphabet();

        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            for (int i = 0; i < R; i++) {
                if (alphabet[i] == c) {
                    BinaryStdOut.write((char) i);
//                    for (int j = i; j > 0; j--) {
//                        alphabet[j] = alphabet[j - 1];
//                    }
                    System.arraycopy(alphabet, 0, alphabet, 1, i);
                    alphabet[0] = c;
                    break;
                }
            }
        }

        BinaryStdOut.flush();
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    // static
    public static void decode() {
        char[] alphabet = initAlphabet();

        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            BinaryStdOut.write(alphabet[c]);
            char tmp = alphabet[c];
            System.arraycopy(alphabet, 0, alphabet, 1, c);
            alphabet[0] = tmp;
        }
        BinaryStdOut.flush();
        BinaryStdOut.close();
    }

    private static char[] initAlphabet() {
        char[] alphabet = new char[R];
        for (int i = 0; i < alphabet.length; i++) {
            alphabet[i] = (char) i;
        }
        return alphabet;
    }

    public static void main(String[] args) {
        if (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
