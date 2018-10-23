import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.print.attribute.standard.MediaSize;
import java.sql.Array;
import java.util.*;

public class RandomizedQueueTests {
    private RandomizedQueue<String> queue;
    private final String[] items = new String[] { "AA", "BB", "CC", "DD", "EE" };

    @Before
    public void init() {
        queue = new RandomizedQueue<>();
    }

    private boolean check(String[] list) {
        HashSet<String> trueSet = new HashSet<>(Arrays.asList(items));
        HashSet<String> resultSet = new HashSet<>(Arrays.asList(list));

        return trueSet.equals(resultSet);
    }

    private void fillTheQueue() {
        for (String item: items) {
            queue.enqueue(item);
        }
    }
    // Should

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfEnqueuesWithNull() {
        queue.enqueue(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void ThrowAnExceptionIfDequeuesInEmptyQueue() {
        queue.dequeue();
    }

    @Test(expected = NoSuchElementException.class)
    public void ThrowAnExceptionIfTriesToGetItemFromEmptyQueue() {
        queue.sample();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void ThrowAnExceptionIfIteratorTriesToRemoveAnItem() {
        Iterator<String> iterator = queue.iterator();
        iterator.remove();
    }

    @Test(expected = NoSuchElementException.class)
    public void ThrowAnExceptionIfIteratorTriesToGetNextItemInEmptyQueue() {
        Iterator<String> iterator = queue.iterator();
        iterator.next();
    }

    @Test
    public void CreateEmptyQueue() {
        Assert.assertTrue(queue.isEmpty());
    }

    @Test
    public void EnqueueAnItem() {
        queue.enqueue(items[0]);

        Assert.assertFalse(queue.isEmpty());
        Assert.assertEquals(1, queue.size());
        Assert.assertEquals(items[0], queue.sample());
    }

    @Test
    public void EnqueueManyItems() {
        fillTheQueue();

        Assert.assertFalse(queue.isEmpty());
        Assert.assertEquals(items.length, queue.size());
    }

    @Test
    public void EnqueueAndDequeueAnItem() {
        queue.enqueue(items[0]);
        String item = queue.dequeue();

        Assert.assertTrue(queue.isEmpty());
        Assert.assertEquals(items[0], item);
    }

    @Test
    public void EnqueueAndDequeueManyItems() {
        fillTheQueue();

        String[] result = new String[items.length];
        for (int i = 0; i < items.length; i++) {
            result[i] = queue.dequeue();
        }

        Assert.assertTrue(queue.isEmpty());
        Assert.assertTrue(check(result));
    }

    @Test
    public void ReturnRandomItem() {
        fillTheQueue();

        String randItem = queue.sample();

        Assert.assertEquals(items.length, queue.size());
        Assert.assertTrue(Arrays.asList(items).contains(randItem));
    }

    @Test
    public void ReturnIteratorWithRandomOrderedItems() {
        fillTheQueue();
        Iterator<String> iterator = queue.iterator();

        int i = 0;
        String[] result = new String[items.length];
        while (iterator.hasNext()) {
            result[i++] = iterator.next();
        }

        Assert.assertTrue(check(result));
    }

    @Test
    public void ReturnTwoIteratorsWithDifferentOrder() {
        fillTheQueue();
        Iterator<String> iterator1 = queue.iterator();
        Iterator<String> iterator2 = queue.iterator();

        int i = 0;
        String[] result1 = new String[items.length];
        String[] result2 = new String[items.length];
        boolean isEqual = true;
        while (iterator1.hasNext() && iterator2.hasNext()) {
            result1[i] = iterator1.next();
            result2[i] = iterator2.next();
            if (result1[i] != result2[i]) {
                isEqual = false;
            }
            i++;
        }

        HashSet<String> set1 = new HashSet<>(Arrays.asList(result1));
        HashSet<String> set2 = new HashSet<>(Arrays.asList(result2));

        Assert.assertFalse(isEqual);
        Assert.assertTrue(set1.equals(set2));
    }

    @Test
    public void EnqueueTwoItemsArrayAndDequeueOnlyOne() {
        fillTheQueue();
        fillTheQueue();

        for (int i = 0; i < items.length; i++) {
            queue.dequeue();
        }

        Assert.assertFalse(queue.isEmpty());
        Assert.assertEquals(items.length, queue.size());
    }
}
