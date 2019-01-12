import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class SuffixesGenerator {
    private final Map<String, Integer> originalSuffixes;
    private final String[] sortedSuffixes;
    private char[] t;
    private int first = 0;

    SuffixesGenerator(String s) {
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

        char lastCharacter = s.charAt(s.length() - 1);

        // name from the assignment
        t = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            t[i] = sortedSuffixes[i].charAt(s.length() - 1);
            if (t[i] == lastCharacter) {
                first = i;
            }
        }
    }

    Map<String, Integer> getOriginalSuffixes() {
        return this.originalSuffixes;
    }

    String[] getSortedSuffixes() {
        return this.sortedSuffixes;
    }

    int getFirst() {
        return first;
    }

    char[] getT() {
        return t;
    }
}
