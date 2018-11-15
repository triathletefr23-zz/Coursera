import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class SAP {
    private final Digraph digraph;

    private int[] lastValues;
    private int[] previousManyValues;
    private int[] resultValues;
    private Iterable<Integer> previousA;
    private Iterable<Integer> previousB;
    private int root;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException();
        }

        digraph = G;
        for (int i = 0; i < digraph.V(); i++) {
            if (digraph.outdegree(i) == 0) {
                root = i;
                break;
            }
        }
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (!isValid(v) || !isValid(w)) {
            throw new IllegalArgumentException();
        }

        int[] result = runBfs(v, w);
        return result[0];
    }

    private boolean isValid(int v) {
        return v > 0 && v < digraph.V();
    }

    private int[] runBfs(int vertexA, int vertexB) {
        if (lastValues != null && lastValues[0] == vertexA && lastValues[1] == vertexB) {
            return resultValues;
        }

        lastValues = new int[]{ vertexA, vertexB };

        Digraph reGraph = digraph.reverse();
        int distance = Integer.MAX_VALUE;
        int sap = -1;

        BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(reGraph, root);
        if (!bfs.hasPathTo(vertexA) || !bfs.hasPathTo(vertexB)) {
            resultValues = new int[]{ -1, -1 };
            return resultValues;
        }

        Iterator<Integer> iteratorA = bfs.pathTo(vertexA).iterator();
        Iterator<Integer> iteratorB = bfs.pathTo(vertexB).iterator();
        while (iteratorA.hasNext() && iteratorB.hasNext()) {
            int elemA = iteratorA.next();
            int elemB = iteratorB.next();
            if (elemA == elemB) {
                if (elemA != root) {
                    bfs = new BreadthFirstDirectedPaths(reGraph, elemA);
                }
                int currDist = bfs.distTo(vertexA) + bfs.distTo(vertexB);
                if (distance > currDist) {
                    distance = currDist;
                    sap = elemA;
                }
                continue;
            }
            break;
        }

        resultValues = new int[]{ distance, sap };
        return resultValues;
    }

    private int[] findMinDistAndSap(Iterable<Integer> first, Iterable<Integer> second) {
        if (previousA != null && previousB != null && areEqualsIterable(first, previousA) && areEqualsIterable(second, previousB)) {
            return previousManyValues;
        }

        int distanceMany = Integer.MAX_VALUE;
        int sapMany = 0;
        for (int vertexA: first) {
            for (int vertexB: second) {
                int[] values = runBfs(vertexA, vertexB);
                int distance = values[0];
                int sap = values[1];
                if (distance < distanceMany) {
                    distanceMany = distance;
                    sapMany = sap;
                }
            }
        }


        previousA = first;
        previousB = second;
        previousManyValues = new int[] { distanceMany, sapMany };
        return previousManyValues;
    }

    private boolean areEqualsIterable(Iterable<Integer> valuesA, Iterable<Integer> valuesB) {
        List<Integer> listA = new ArrayList<>();
        valuesA.forEach(listA::add);

        List<Integer> listB = new ArrayList<>();
        valuesB.forEach(listB::add);

        if (listA.size() != listB.size()) return false;

        return new HashSet<>(listA).equals(new HashSet<>(listB));
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (!isValid(v) || !isValid(w)) {
            throw new IllegalArgumentException();
        }

        int[] result = runBfs(v, w);
        return result[1];
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException();
        }

        int[] result = findMinDistAndSap(v, w);
        return result[0];
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException();
        }

        int[] result = findMinDistAndSap(v, w);
        return result[1];
    }

    // do unit testing of this class
    public static void main(String[] args) {
//        In in = new In(args[0]);
//        Digraph G = new Digraph(in);
//        SAP sap = new SAP(G);
//        while (!StdIn.isEmpty()) {
//            int v = StdIn.readInt();
//            int w = StdIn.readInt();
//            int length = sap.length(v, w);
//            int ancestor = sap.ancestor(v, w);
//            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
//        }
    }
}