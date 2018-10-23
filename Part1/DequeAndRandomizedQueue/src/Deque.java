/******************************************************************************
 *  Name:    Pavlo LIAHUSHYN
 *  NetID:   liahus
 *  Precept: P01
 *
 *  Description:  Creates a generic data type Deque.
 *
 ******************************************************************************/

// Throw a java.lang.IllegalArgumentException if the client calls either addFirst() or addLast() with a null argument.
// Throw a java.util.NoSuchElementException if the client calls either removeFirst() or removeLast when the deque is empty.
// Throw a java.util.NoSuchElementException if the client calls the next() method in the iterator when there are no more items to return.
// Throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator.

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first;
    private Node<Item> last;
    private int n;

    // helper linked list class
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("First item couldn't be null");
        Node<Item> oldFirst = first;
        first = new Node<>();
        first.item = item;
        if (isEmpty()) last = first;
        else {
            oldFirst.prev = first;
            first.next = oldFirst;
        }
        n++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("Last item couldn't be null");
        Node<Item> oldLast = last;
        last = new Node<>();
        last.item = item;
        last.next = null;
        last.prev = oldLast;
        if (isEmpty()) first = last;
        else oldLast.next = last;
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        Item item = first.item;
        first = first.next;
        n--;
        if (isEmpty()) {
            last = null;
        }
        else {
            first.prev = null;
        }
        return item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        Node<Item> oldLast = last;
        last = oldLast.prev;
            n--;

        if (isEmpty()) {
            first = null;
            last = null;
        }
        else {
            last = oldLast.prev;
            last.next = null;
        }

        return oldLast.item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ListIterator<>(first);
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        private ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() { return current != null;                    }
        public void remove()     { throw new UnsupportedOperationException(); }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (optional)
    public static void main(String[] args) {
        /* Deque<String> deque = new Deque<>();
        int i = 0;
        while (i < args.length) {
            String item = args[i++];
            if (!item.equals("-")) {
                deque.addLast(item);
            } else if (!deque.isEmpty()) {
                StdOut.print(deque.removeFirst() + " ");
            }
        }
        StdOut.println("\n(" + deque.size() + " left on queue)");*/
    }
}