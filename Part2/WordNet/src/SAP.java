import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

import java.util.ArrayList;
import java.util.List;


public class SAP {
    private final Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException();
        }

        digraph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (isIllegalArgument(v) || isIllegalArgument(w)) {
            throw new IllegalArgumentException();
        }

        int[] result = runBfs(v, w);
        return result[0];
    }

    private boolean isIllegalArgument(int v) {
        return v < 0 || v >= digraph.V();
    }

    private boolean isIllegalIterable(Iterable<Integer> v) {
        List<Integer> list = new ArrayList<>();
        v.forEach(list::add);
        return list.isEmpty();
    }

    private int[] runBfs(int vertexA, int vertexB) {
        int distance = Integer.MAX_VALUE;
        int sap = -1;

        BreadthFirstDirectedPaths bfsA = new BreadthFirstDirectedPaths(digraph, vertexA);
        BreadthFirstDirectedPaths bfsB = new BreadthFirstDirectedPaths(digraph, vertexB);

        for (int i = 0; i < digraph.V(); i++) {
            if (bfsA.hasPathTo(i) && bfsB.hasPathTo(i)) {
                int currentDistance = bfsA.distTo(i) + bfsB.distTo(i);
                if (currentDistance < distance) {
                    distance = currentDistance;
                    sap = i;
                }
            }
        }

        if (sap == -1) distance = -1;

        return new int[]{ distance, sap };
    }

    private int[] findMinDistAndSap(Iterable<Integer> first, Iterable<Integer> second) {
        int distanceMany = Integer.MAX_VALUE;
        int sapMany = 0;

        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(digraph, first);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(digraph, second);
        for (int i = 0; i < digraph.V(); i++) {
            if (bfs1.hasPathTo(i) && bfs2.hasPathTo(i)) {
                int curr = bfs1.distTo(i) + bfs2.distTo(i);
                if (curr < distanceMany) {
                    distanceMany = curr;
                    sapMany = i;
                }
            }
        }

        return new int[] { distanceMany, sapMany };
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (isIllegalArgument(v) || isIllegalArgument(w)) {
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

        if (isIllegalIterable(v) || isIllegalIterable(w)) {
            return -1;
        }

        containsNullValue(v);
        containsNullValue(w);

        int[] result = findMinDistAndSap(v, w);
        return result[0];
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException();
        }

        if (isIllegalIterable(v) || isIllegalIterable(w)) {
            return -1;
        }

        containsNullValue(v);
        containsNullValue(w);

        int[] result = findMinDistAndSap(v, w);
        return result[1];
    }

    private  void containsNullValue(Iterable<Integer> v) {
        for (Integer elem: v) {
            if (elem == null) throw new IllegalArgumentException();
        }
    }

    // do unit testing of this class
    public static void main(String[] args) {
        // optional
    }
}