import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WordNet {

    private final HashMap<Integer, String> keys;
    private final HashMap<String, List<Integer>> nouns;
    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String fileS, String fileH) {
        if (fileS == null || fileH == null) {
            throw new IllegalArgumentException();
        }

        keys = new HashMap<>();
        nouns = new HashMap<>();

        In inSynsets = new In(fileS);
        In inHypernyms = new In(fileH);

        while (inSynsets.hasNextLine()) {
            String[] line = parseLine(inSynsets);
            int v = Integer.parseInt(line[0]);
            keys.put(v, line[1]);
            String[] synsets = line[1].split(" ");
            for (String noun: synsets) {
                List<Integer> synsetIds;
                if (nouns.containsKey(noun)) {
                    synsetIds = nouns.get(noun);
                }
                else {
                    synsetIds = new ArrayList<>();
                }

                synsetIds.add(v);
                nouns.put(noun, synsetIds);
            }
        }

        Digraph digraph = new Digraph(keys.size());

        while (inHypernyms.hasNextLine()) {
            String[] line = parseLine(inHypernyms);
            int v = Integer.parseInt(line[0]);
            for (int i = 1; i < line.length; i++) {
                int w = Integer.parseInt(line[i]);
                digraph.addEdge(v, w);
            }
        }

        DirectedCycle finder = new DirectedCycle(digraph);
        if (finder.hasCycle()) {
            throw new IllegalArgumentException();
        }

        // Check if not rooted
        int rooted = 0;
        for (int i = 0; i < digraph.V(); i++) {
            if (!digraph.adj(i).iterator().hasNext())
                rooted++;
        }

        if (rooted != 1) {
            throw new IllegalArgumentException("Not a rooted DAG");
        }

        sap = new SAP(digraph);
    }

    private String[] parseLine(In in) {
        return in.readLine().split(",");
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nouns.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }

        return nouns.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException();
        }

        if (nounA.equals(nounB)) return 0;

        return sap.length(nouns.get(nounA), nouns.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException();
        }

        int ancestor = sap.ancestor(nouns.get(nounA), nouns.get(nounB));

        return keys.get(ancestor);
    }

    // do unit testing of this class
    public static void main(String[] args) {
//        WordNet wordNet = new WordNet(args[0], args[1]);
    }
}