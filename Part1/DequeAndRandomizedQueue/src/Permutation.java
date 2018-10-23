import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
//        In in;
//        if (args[1].contains("txt")) {
//            in = new In(args[1]);
//        }
//        else {
//            in = new In(args[2]);
//        }

        RandomizedQueue<String> queue = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            queue.enqueue(item);
        }

        while (k != 0) {
            StdOut.println(queue.dequeue());
            k--;
        }
    }
}