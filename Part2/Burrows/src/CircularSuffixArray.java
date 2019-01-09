import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CircularSuffixArray {
    private final Map<String, Integer> originalSuffixes;
    private final String[] sortedSuffixes;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) throw new IllegalArgumentException();

        originalSuffixes = new HashMap<>();
        sortedSuffixes = new String[s.length()];

        originalSuffixes.put(s, 0);
        sortedSuffixes[0] = s;

        for (int i = 1; i < s.length(); i++) {
            char[] prev = sortedSuffixes[i - 1].toCharArray();
            char[] curr = new char[s.length()];
            System.arraycopy(prev, 1, curr, 0, s.length() - 1);
            curr[s.length() - 1] = prev[0];
            String newString = new String(curr);
            originalSuffixes.put(newString, i);
            sortedSuffixes[i] = newString;
        }

        Arrays.sort(sortedSuffixes);
    }

    private void printSuffixes(Iterable values) {
        for (var el : values) {
            StdOut.println(el);
        }
    }

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
    public static void main(String[] args) {
        // not implemented
    }
}