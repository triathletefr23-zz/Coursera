import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

public class SAP {
    private Digraph digraph;
    private int distance;
    private int sap;
    private int distanceMany;
    private int sapMany;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException();
        }

        digraph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (!isValid(v) || !isValid(w)) {
            throw new IllegalArgumentException();
        }

        runBfs(v, w);
        return distance;
    }

    private boolean isValid(int v) {
        return v > 0 && v < digraph.V();
    }

    private void runBfs(int vertexA, int vertexB) {
        Digraph reGraph = digraph.reverse();
        distance = Integer.MAX_VALUE;
        sap = -1;
        for (int vertex = 0; vertex < digraph.V(); vertex++) {
            BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(reGraph, vertex);
            if (bfs.hasPathTo(vertexA) && bfs.hasPathTo(vertexB)) {
                var curr = bfs.distTo(vertexA) + bfs.distTo(vertexB);
                if (distance > curr) {
                    distance = curr;
                    sap = vertex;
                }
            }
        }

        if (sap == -1) distance = -1;
    }

    private void findMinDistAndSap(Iterable<Integer> first, Iterable<Integer> second) {
        distanceMany = Integer.MAX_VALUE;
        sapMany = -1;
        for (int vertexA: first) {
            for (int vertexB: second) {
                runBfs(vertexA, vertexB);
                if (distance < distanceMany) {
                    distanceMany = distance;
                    sapMany = sap;
                }
            }
        }
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (!isValid(v) || !isValid(w)) {
            throw new IllegalArgumentException();
        }

        runBfs(v, w);
        return sap;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException();
        }

        findMinDistAndSap(v, w);
        return distanceMany;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException();
        }

        findMinDistAndSap(v, w);
        return sapMany;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}