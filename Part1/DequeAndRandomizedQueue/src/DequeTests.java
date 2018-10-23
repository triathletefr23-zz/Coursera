import org.junit.Assert;
import org.junit.Test;

import java.util.NoSuchElementException;

public class DequeTests {
    private final String[] PHRASE = new String[]{"be", "to", "not"};
    private final String[] ONE_ELEMENT_PHRASE = new String[]{"to"};
    private Deque<String> emptyDeque;
    private Deque<String> deque;

    public DequeTests() {
        emptyDeque = new Deque<>();
        deque = new Deque<>();
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfItemIsNullInAddFirst() {
        emptyDeque.addFirst(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfItemIsNullInAddLast() {
        emptyDeque.addLast(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void ThrowAnExceptionIfDequeIsEmptyInRemoveFirst() {
        emptyDeque.removeFirst();
    }

    @Test(expected = NoSuchElementException.class)
    public void ThrowAnExceptionIfDequeIsEmptyInRemoveLast() {
        emptyDeque.removeLast();
    }

    @Test
    public void AddFirstElementToTheEmptyDeque() {
        String item = "to";
        deque.addFirst(item);
        Assert.assertEquals(1, deque.size());
        Assert.assertEquals(deque.iterator().next(), item);
    }

    @Test
    public void AddLastElementToTheEmptyDeque() {
        String item = "to";
        deque.addLast(item);
        Assert.assertEquals(1, deque.size());
        Assert.assertEquals(deque.iterator().next(), item);
    }

    @Test
    public void RemoveLastElementFromOneElementDeque() {
        String expected = ONE_ELEMENT_PHRASE[0];
        deque.addFirst(expected);
        String res = deque.removeLast();
        Assert.assertEquals(0, deque.size());
        Assert.assertEquals(expected, res);
    }

    @Test
    public void RemoveFirstElementFromOneElementDeque() {
        String expected = ONE_ELEMENT_PHRASE[0];
        deque.addFirst(expected);
        String res = deque.removeFirst();
        Assert.assertEquals(0, deque.size());
        Assert.assertEquals(expected, res);
    }

    @Test
    public void RemoveFirstElementFromOneElementDequeIfItWasAddedLast() {
        String expected = ONE_ELEMENT_PHRASE[0];
        deque.addLast(expected);
        String res = deque.removeFirst();
        Assert.assertEquals(0, deque.size());
        Assert.assertEquals(expected, res);
    }

    @Test
    public void RemoveLastElementFromOneElementDequeIfItWasAddedLast() {
        String expected = ONE_ELEMENT_PHRASE[0];
        deque.addLast(expected);
        String res = deque.removeLast();
        Assert.assertEquals(0, deque.size());
        Assert.assertEquals(expected, res);
    }

    @Test
    public void AddThreeElementsInFirstPosition() {
        for (String elem : PHRASE) {
            deque.addFirst(elem);
        }

        Assert.assertFalse(deque.isEmpty());
        Assert.assertEquals(3, deque.size());
        Assert.assertEquals(PHRASE[2], deque.iterator().next());
    }

    @Test
    public void AddThreeElementsInLastPosition() {
        for (String elem : PHRASE) {
            deque.addLast(elem);
        }

        Assert.assertFalse(deque.isEmpty());
        Assert.assertEquals(3, deque.size());
        Assert.assertEquals(PHRASE[0], deque.iterator().next());
    }

    @Test
    public void AddThreeElementsInFirstPositionAndRemoveFirst() {
        for (String elem : PHRASE) {
            deque.addFirst(elem);
        }

        String first = deque.removeFirst();

        Assert.assertFalse(deque.isEmpty());
        Assert.assertEquals(2, deque.size());
        Assert.assertEquals("not", first);
        Assert.assertEquals("to", deque.iterator().next());
    }

    @Test
    public void AddThreeElementsInFirstPositionAndRemoveLast() {
        for (String elem : PHRASE) {
            deque.addFirst(elem);
        }

        String last = deque.removeLast();

        Assert.assertFalse(deque.isEmpty());
        Assert.assertEquals(2, deque.size());
        Assert.assertEquals("be", last);
        Assert.assertEquals("not", deque.iterator().next());
    }

    @Test
    public void AddThreeElementsInLastPositionAndRemoveFirst() {
        for (String elem : PHRASE) {
            deque.addLast(elem);
        }

        String first = deque.removeFirst();

        Assert.assertFalse(deque.isEmpty());
        Assert.assertEquals(2, deque.size());
        Assert.assertEquals("be", first);
        Assert.assertEquals("to", deque.iterator().next());
    }

    @Test
    public void AddThreeElementsInLastPositionAndRemoveLast() {
        for (String elem : PHRASE) {
            deque.addLast(elem);
        }

        String last = deque.removeLast();

        Assert.assertFalse(deque.isEmpty());
        Assert.assertEquals(2, deque.size());
        Assert.assertEquals("not", last);
        Assert.assertEquals("be", deque.iterator().next());
    }

    @Test
    public void AddThreeElementsInFirstPositionAndAddFirst() {
        for (String elem : PHRASE) {
            deque.addFirst(elem);
        }

        deque.addFirst("or");

        Assert.assertFalse(deque.isEmpty());
        Assert.assertEquals(4, deque.size());
        Assert.assertEquals("or", deque.iterator().next());
    }

    @Test
    public void AddThreeElementsInFirstPositionAndAddLast() {
        for (String elem : PHRASE) {
            deque.addFirst(elem);
        }

        deque.addLast("or");

        Assert.assertFalse(deque.isEmpty());
        Assert.assertEquals(4, deque.size());
        Assert.assertEquals("not", deque.iterator().next());
    }

    @Test
    public void AddThreeElementsInLastPositionAndAddFirst() {
        for (String elem : PHRASE) {
            deque.addLast(elem);
        }

        deque.addFirst("or");

        Assert.assertFalse(deque.isEmpty());
        Assert.assertEquals(4, deque.size());
        Assert.assertEquals("or", deque.iterator().next());
    }

    @Test
    public void AddThreeElementsInLastPositionAndAddLast() {
        for (String elem : PHRASE) {
            deque.addLast(elem);
        }

        deque.addLast("or");

        Assert.assertFalse(deque.isEmpty());
        Assert.assertEquals(4, deque.size());
        Assert.assertEquals("be", deque.iterator().next());
    }

    @Test
    public void RemoveAllFirstElementsFromThreeElementsDeque() {
        for (String elem : PHRASE) {
            deque.addFirst(elem);
        }

        deque.removeFirst();
        String mid = deque.removeFirst();
        deque.removeFirst();

        Assert.assertTrue(deque.isEmpty());
        Assert.assertEquals("to", mid);
    }

    @Test
    public void RemoveAllLastElementsFromThreeElementsDeque() {
        for (String elem : PHRASE) {
            deque.addFirst(elem);
        }

        deque.removeLast();
        String mid = deque.removeLast();
        deque.removeLast();

        Assert.assertTrue(deque.isEmpty());
        Assert.assertEquals("to", mid);
    }

    @Test
    public void RemoveFirstAndLastElementsFromThreeElementsDeque() {
        for (String elem : PHRASE) {
            deque.addFirst(elem);
        }

        var first = deque.removeFirst();
        var last = deque.removeLast();

        Assert.assertEquals(1, deque.size());
        Assert.assertEquals("not", first);
        Assert.assertEquals("be", last);
    }

    @Test
    public void CallIntermixAndNotFailToGetIterator() {
        int n = 20;
        for (int i = 0; i < n; i++) {
            deque.addFirst("AA");
            deque.addLast("BB");
            deque.removeFirst();
            deque.removeLast();
            Assert.assertFalse(deque.iterator().hasNext());
        }
    }
}