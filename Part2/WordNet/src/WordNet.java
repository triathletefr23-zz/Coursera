import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

import java.util.HashMap;
import java.util.Arrays;
import java.util.Map;

public class WordNet {

    private final HashMap<Integer, Iterable<String>> keys;
    private final SAP sap;
    private String previousA;
    private String previousB;
    private int[] previousResult;

//    public WordNet() {
//        digraph = new Digraph(0);
//        keys = new HashMap<>();
//        sap = new SAP(digraph);
//    }

//    public WordNet(int[][] edges, int count) {
//        digraph = new Digraph(count);
//        keys = new HashMap<>();
//
//        for (int[] edge: edges) {
//            digraph.addEdge(edge[0], edge[1]);
//        }
//
//        sap = new SAP(digraph);
//
//        for (int i = 0; i < count; i++) {
//            List<String> list = new ArrayList<>();
//            list.add(String.valueOf(i));
//            keys.put(i, list);
//        }
//    }

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
            String[] words = line[1].split("\\s+");
            keys.put(v, Arrays.asList(words));
        }

        Digraph digraph = new Digraph(keys.size());

        while (fileHypernyms.hasNextLine()) {
            String[] line = parseLine(fileHypernyms);
            int v = Integer.parseInt(line[0]);
            for (int i = 1; i < line.length; i++) {
                int w = Integer.parseInt(line[i]);
                digraph.addEdge(v, w);
            }
        }

        sap = new SAP(digraph);
    }

    private String[] parseLine(In in) {
        return in.readLine().split(",");
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        Stack<String> words = new Stack<>();
        for (Iterable<String> elem: keys.values()) {
            elem.forEach(words::push);
        }
        return words;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }

        for (Iterable<String> elem: keys.values()) {
            for (String elem1: elem) {
                if (elem1.equals(word)) {
                    return true;
                }
            }
        }

        return false;
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
        Stack<Integer> numbers = new Stack<>();
        for (Map.Entry<Integer, Iterable<String>> nouns: keys.entrySet()) {
            for (String el: nouns.getValue()) {
                if (el.equals(word)) {
                    numbers.push(nouns.getKey());
                    break;
                }
            }
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

        return keys.get(result[1]).iterator().next();
    }

    // do unit testing of this class
    public static void main(String[] args) {
//        WordNet wordNet = new WordNet(args[0], args[1]);
    }
}