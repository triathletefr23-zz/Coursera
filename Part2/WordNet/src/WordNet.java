import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;

public class WordNet {

    private Digraph digraph;
    private HashMap<String, Integer> values;
    private HashMap<Integer, String> keys;
    private int distance;
    private int sap;

    public WordNet() {
        digraph = new Digraph(0);
        values = new HashMap<>();
        keys = new HashMap<>();
    }

    public WordNet(int[][] edges, int count) {
        digraph = new Digraph(count);
        values = new HashMap<>();
        keys = new HashMap<>();

        for (int[] edge: edges) {
            digraph.addEdge(edge[0], edge[1]);
        }

        for (int i = 0; i < count; i++) {
            keys.put(i, String.valueOf(i));
            values.put(String.valueOf(i), i);
        }
    }

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException();
        }

        values = new HashMap<>();
        In fileSynsets = new In(synsets);
        In fileHypernyms = new In(hypernyms);

        while (fileSynsets.hasNextLine()) {
            String[] line = parseLine(fileSynsets);
            int v = Integer.parseInt(line[0]);
            String word = line[1];
            values.put(word, v);
            keys.put(v, word);
        }

        digraph = new Digraph(values.size());

        while (fileHypernyms.hasNextLine()) {
            String[] line = parseLine(fileHypernyms);
            int v = Integer.parseInt(line[0]);
            for (int i = 1; i < line.length; i++) {
                int w = Integer.parseInt(line[i]);
                digraph.addEdge(v, w);
            }
        }

        // should throw an exception if
        // the input to the constructor does not correspond to a rooted DAG
    }

    private String[] parseLine(In in) {
        return in.readLine().split(",");
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return values.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }

        return values.containsValue(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException();
        }

        runBfs(nounA, nounB);

        return distance;
    }

    private void runBfs(String nounA, String nounB) {
        int vertexA = values.get(nounA);
        int vertexB = values.get(nounB);

        Digraph reGraph = digraph.reverse();
        distance = Integer.MAX_VALUE;
        sap = 0;
        for (int v = 0; v < digraph.V(); v++) {
            BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(reGraph, v);
            if (bfs.hasPathTo(vertexA) && bfs.hasPathTo(vertexB)) {
                var curr = bfs.distTo(vertexA) + bfs.distTo(vertexB);
                if (distance > curr) {
                    distance = curr;
                    sap = v;
                }
            }
        }
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException();
        }

        runBfs(nounA, nounB);

        return keys.get(sap);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordNet = new WordNet(args[0], args[1]);
    }
}