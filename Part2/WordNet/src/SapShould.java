import edu.princeton.cs.algs4.Digraph;
import org.junit.Assert;
import org.junit.Test;

public class SapShould {
    private SAP sap;

    private final int example1_verticesCount = 13;
    private final int[][] example1 = {
            new int[] { 7, 3 },
            new int[] { 8, 3 },
            new int[] { 3, 1 },
            new int[] { 4, 1 },
            new int[] { 5, 1 },
            new int[] { 9, 5 },
            new int[] { 10, 5 },
            new int[] { 11, 10 },
            new int[] { 12, 10 },
            new int[] { 1, 0 },
            new int[] { 2, 0 }
    };

    public void createSap(int[][] edges, int count) {
        Digraph digraph = new Digraph(count);

        for (int[] edge: edges) {
            digraph.addEdge(edge[0], edge[1]);
        }

        sap = new SAP(digraph);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnIllegalArgumentExceptionInConstructor() {
        sap = new SAP(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnIllegalArgumentExceptionInLengthFunction() {
        createSap(example1, example1_verticesCount);
        sap.length(-1, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnIllegalArgumentExceptionInAncestorFunction() {
        createSap(example1, example1_verticesCount);
        sap.ancestor(-1, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnIllegalArgumentExceptionInLengthFunctionForMany() {
        createSap(example1, example1_verticesCount);
        sap.length(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnIllegalArgumentExceptionInAncestorFunctionForMany() {
        createSap(example1, example1_verticesCount);
        sap.ancestor(null, null);
    }

    @Test
    public void ReturnLengthAndAncestorForPair1InExample1() {
        createSap(example1, example1_verticesCount);
        var distance = sap.length(3,11);
        var ancestor = sap.ancestor(3,11);
        Assert.assertEquals(4, distance);
        Assert.assertEquals(1, ancestor);
    }

    @Test
    public void ReturnLengthAndAncestorForPair2InExample1() {
        createSap(example1, example1_verticesCount);
        var distance = sap.length(9,12);
        var ancestor = sap.ancestor(9,12);
        Assert.assertEquals(3, distance);
        Assert.assertEquals(5, ancestor);
    }

    @Test
    public void ReturnLengthAndAncestorForPair3InExample1() {
        createSap(example1, example1_verticesCount);
        var distance = sap.length(7,2);
        var ancestor = sap.ancestor(7,2);
        Assert.assertEquals(4, distance);
        Assert.assertEquals(0, ancestor);
    }

    @Test
    public void ReturnLengthAndAncestorForPair4InExample1() {
        createSap(example1, example1_verticesCount);
        var distance = sap.length(1,6);
        var ancestor = sap.ancestor(1,6);
        Assert.assertEquals(-1, distance);
        Assert.assertEquals(-1, ancestor);
    }
}