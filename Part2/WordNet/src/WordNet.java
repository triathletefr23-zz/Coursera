import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

import java.util.HashMap;

public class WordNet {

    private Digraph digraph;
    private HashMap<Integer, String> keys;
    private SAP sap;
    private String previousA;
    private String previousB;
    private int[] previousResult;

    public WordNet() {
        digraph = new Digraph(0);
        keys = new HashMap<>();
        sap = new SAP(digraph);
    }

    public WordNet(int[][] edges, int count) {
        digraph = new Digraph(count);
        keys = new HashMap<>();

        for (int[] edge: edges) {
            digraph.addEdge(edge[0], edge[1]);
        }

        sap = new SAP(digraph);

        for (int i = 0; i < count; i++) {
            keys.put(i, String.valueOf(i));
        }
    }

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException();
        }

        keys = new HashMap<>();

        In fileSynsets = new In(synsets);
        In fileHypernyms = new In(hypernyms);

        while (fileSynsets.hasNextLine()) {
            String[] line = parseLine(fileSynsets);
            int v = Integer.parseInt(line[0]);
            if (line[1].contains(" ")) {
                String[] words = line[1].split(" ");
                for (String word: words) {
                    keys.put(v, word);
                }
            }
            else {
                keys.put(v, line[1]);
            }
        }

        digraph = new Digraph(keys.size());

        while (fileHypernyms.hasNextLine()) {
            String[] line = parseLine(fileHypernyms);
            int v = Integer.parseInt(line[0]);
            for (int i = 1; i < line.length; i++) {
                int w = Integer.parseInt(line[i]);
                digraph.addEdge(v, w);
            }
        }

        sap = new SAP(digraph);
        // should throw an exception if
        // the input to the constructor does not correspond to a rooted DAG
    }

    private String[] parseLine(In in) {
        return in.readLine().split(",");
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return keys.values();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }

        return keys.containsValue(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException();
        }

        if (nounA.equals(nounB)) return 0;

        int[] result = runBfs(nounA, nounB);

        return result[0];
    }

    private int[] runBfs(String nounA, String nounB) {
        if (previousA != null && previousB != null && previousA.equals(nounA) && previousB.equals(nounB)) {
            return previousResult;
        }

        Iterable<Integer> vertexesA = findNumbersForNoun(nounA);
        Iterable<Integer> vertexesB = findNumbersForNoun(nounB);

        int distance = sap.length(vertexesA, vertexesB);
        int currSap = sap.ancestor(vertexesA, vertexesB);

        previousA = nounA;
        previousB = nounB;
        previousResult = new int[]{ distance, currSap };
        return previousResult;
    }

    private Iterable<Integer> findNumbersForNoun(String word) {
        int count = 0;
        Stack<Integer> numbers = new Stack<>();
        for (String noun: keys.values()) {
            if (noun.equals(word)) {
                numbers.push(count);
            }
            count++;
        }
        return numbers;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException();
        }

        int[] result = runBfs(nounA, nounB);

        return keys.get(result[1]);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordNet = new WordNet(args[0], args[1]);
    }
}