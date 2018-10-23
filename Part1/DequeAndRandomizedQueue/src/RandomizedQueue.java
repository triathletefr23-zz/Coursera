import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] queue;
    private int count = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[1];
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < count; i++) {
            copy[i] = queue[i];
        }
        queue = copy;
    }

    private int randomNumber() {
        return StdRandom.uniform(0, count);
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return count == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return count;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("First item couldn't be null");
        if (count == queue.length) {
            resize(2 * queue.length);
        }
        queue[count++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");

        int num = randomNumber();
        Item item = queue[num];
        queue[num] = queue[count - 1];
        queue[count - 1] = null;
        count--;

        if (count > 0 && count == queue.length / 4) {
            resize(queue.length / 2);
        }

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");

        int num = randomNumber();
        return queue[num];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator<>(queue, count);
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator<Item> implements Iterator<Item> {
        private final Item[] iterQueue;
        private final int count;
        private int iter;

        private ListIterator(Item[] queue, int currCount) {
            count = currCount;
            iterQueue = (Item[]) new Object[count];
            for (int i = 0; i < count; i++) {
                iterQueue[i] = queue[i];
            }
            StdRandom.shuffle(iterQueue);
        }

        public boolean hasNext() { return iter < count; }
        public void remove()     { throw new UnsupportedOperationException(); }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            return iterQueue[iter++];
        }
    }

    // unit testing (optional)
    public static void main(String[] args) {
        // optional
    }
}