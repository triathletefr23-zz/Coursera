import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import org.junit.Assert;
import org.junit.Test;

import java.util.Stack;

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

    private SAP initSapFromDigraph(String file) {
        Digraph digraph = new Digraph(new In(file));
        return new SAP(digraph);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfNonValidArgumentForAncestor() {
        sap = initSapFromDigraph("data\\digraph1.txt");
        sap.ancestor(13, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfNonValidArgumentForLength() {
        sap = initSapFromDigraph("data\\digraph1.txt");
        sap.length(13, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfIterableContainsNullForAncestor() {
        sap = initSapFromDigraph("data\\digraph1.txt");
        var list1 = new Stack<Integer>();
        list1.push(1);
        list1.push(2);
        list1.push(null);
        list1.push(4);
        var list2 = new Stack<Integer>();
        list2.push(1);
        sap.ancestor(list2, list1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfIterableContainsNullForLength() {
        sap = initSapFromDigraph("data\\digraph1.txt");
        var list1 = new Stack<Integer>();
        list1.push(1);
        list1.push(2);
        list1.push(null);
        list1.push(4);
        var list2 = new Stack<Integer>();
        list2.push(1);
        sap.length(list1, list2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfIterableContainsIllegalElementForAncestor() {
        sap = initSapFromDigraph("data\\digraph1.txt");
        var list1 = new Stack<Integer>();
        list1.push(13);
        list1.push(1);
        list1.push(2);
        list1.push(3);
        list1.push(4);
        list1.push(10);
        var list2 = new Stack<Integer>();
        list2.push(6);
        list2.push(8);
        list2.push(2);
        sap.ancestor(list1, list2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfIterableContainsIllegalElementForLength() {
        sap = initSapFromDigraph("data\\digraph1.txt");
        var list1 = new Stack<Integer>();
        list1.push(1);
        list1.push(2);
        list1.push(13);
        list1.push(4);
        var list2 = new Stack<Integer>();
        list2.push(1);
        sap.length(list1, list2);
    }

    @Test
    public void ReturnLengthAndAncestorForDigraph11() {
        sap = initSapFromDigraph("data\\digraph1.txt");
        var distance = sap.length(7,4);
        var ancestor = sap.ancestor(7,4);
        Assert.assertEquals(3, distance);
        Assert.assertEquals(1, ancestor);
    }

    @Test
    public void ReturnLengthAndAncestorForDigraph12() {
        sap = initSapFromDigraph("data\\digraph1.txt");
        var distance = sap.length(2,6);
        var ancestor = sap.ancestor(2,6);
        Assert.assertEquals(-1, distance);
        Assert.assertEquals(-1, ancestor);
    }

    @Test
    public void ReturnLengthAndAncestorForDigraph13() {
        sap = initSapFromDigraph("data\\digraph1.txt");
        var distance = sap.length(0,6);
//        var ancestor = sap.ancestor(0,6);
        Assert.assertEquals(-1, distance);
//        Assert.assertEquals(-1, ancestor);
    }


    @Test
    public void ReturnLengthAndAncestorForDigraph21() {
        sap = initSapFromDigraph("data\\digraph2.txt");
        var distance = sap.length(1,3);
        Assert.assertEquals(2, distance);
    }

    @Test
    public void ReturnLengthAndAncestorForDigraph22() {
        sap = initSapFromDigraph("data\\digraph2.txt");
        var distance = sap.length(1,5);
        Assert.assertEquals(2, distance);
    }

    @Test
    public void ReturnLengthAndAncestorForDigraph3() {
        sap = initSapFromDigraph("data\\digraph3.txt");
        var distance = sap.length(14,8);
        var ancestor = sap.ancestor(14,8);
        Assert.assertEquals(4, distance);
        Assert.assertEquals(8, ancestor);
    }

    @Test
    public void ReturnLengthAndAncestorForDigraph31() {
        sap = initSapFromDigraph("data\\digraph3.txt");
        var distance = sap.length(7,12);
//        var ancestor = sap.ancestor(7,12);
        Assert.assertEquals(2, distance);
//        Assert.assertEquals(2, ancestor);
    }

    @Test
    public void ReturnLengthAndAncestorForDigraph32() {
        sap = initSapFromDigraph("data\\digraph3.txt");
        var distance = sap.length(0,7);
//        var ancestor = sap.ancestor(7,12);
        Assert.assertEquals(4, distance);
//        Assert.assertEquals(2, ancestor);
    }

    @Test
    public void ReturnLengthAndAncestorForDigraph5() {
        sap = initSapFromDigraph("data\\digraph5.txt");
        var distance = sap.length(9,12);
//        var ancestor = sap.ancestor(9,12);
        Assert.assertEquals(3, distance);
//        Assert.assertEquals(8, ancestor);
    }

    @Test
    public void ReturnLengthAndAncestorForDigraph9() {
        sap = initSapFromDigraph("data\\digraph9.txt");
        var distance = sap.length(8,5);
        var ancestor = sap.ancestor(8,5);
        Assert.assertEquals(1, distance);
        Assert.assertEquals(8, ancestor);
    }

    @Test
    public void ReturnLengthAndAncestorForDigraph91() {
        sap = initSapFromDigraph("data\\digraph9.txt");
        var distance = sap.length(3,4);
        Assert.assertEquals(1, distance);
    }

    @Test
    public void ReturnLengthAndAncestorForDigraph92() {
        sap = initSapFromDigraph("data\\digraph9.txt");
        var distance = sap.length(8,4);
        var ancestor = sap.ancestor(8,4);
        Assert.assertEquals(-1, distance);
        Assert.assertEquals(-1, ancestor);
    }

    @Test
    public void ReturnLengthAndAncestorForDigraph93() {
        sap = initSapFromDigraph("data\\digraph9.txt");
        var distance = sap.length(5,3);
//        var ancestor = sap.ancestor(5,3);
        Assert.assertEquals(2, distance);
//        Assert.assertEquals(-1, ancestor);
    }

    @Test
    public void ReturnLengthAndAncestorForDigraphWordnet1() {
        sap = initSapFromDigraph("data\\digraph-wordnet.txt");
        var distance = sap.length(57201,47105);
        Assert.assertEquals(12, distance);
    }

    @Test
    public void ReturnLengthAndAncestorForDigraphWordnet12() {
        sap = initSapFromDigraph("data\\digraph-wordnet.txt");
        var distance = sap.length(55395,68394);
        Assert.assertEquals(8, distance);
    }

    @Test
    public void ReturnLengthAndAncestorForDigraphWordnet() {
        sap = initSapFromDigraph("data\\digraph-wordnet.txt");
        var distance = sap.length(55395,68394);
        Assert.assertEquals(8, distance);
    }
}