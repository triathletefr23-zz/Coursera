import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {

    private int moves;
    private boolean isSolvable;
    private Stack<Board> solutionBoards;

    private static class Node<Item> implements Comparable<Node<Item>> {
        private Item board;
        private int priority;
        private boolean isTwin;
        private int moves;
        private Node<Item> predecessor;

        @Override
        public int compareTo(Node<Item> that) {
            if (that == null) throw new IllegalArgumentException();
            if (this.priority > that.priority) return 1;
            else if (this.priority == that.priority) return 0;
            return -1;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        run(initial);
    }

    private void run(Board initial) {
        moves = 0;
        MinPQ<Node<Board>> originalPQ = new MinPQ<>();
        MinPQ<Node<Board>> twinPQ = new MinPQ<>();
        solutionBoards = new Stack<>();
        isSolvable = true;

        Node<Board> searchNode = createNode(initial, null);
        originalPQ.insert(searchNode);

        Node<Board> twinNode = createNode(searchNode.board.twin(),null);
        twinPQ.insert(twinNode);

        while (!checkOnGoalBoard(searchNode, false) && !checkOnGoalBoard(twinNode, true)) {
            searchNode = originalPQ.delMin();
            twinNode = twinPQ.delMin();

            originalPQ = addNeighborsToPQ(searchNode, originalPQ);
            twinPQ = addNeighborsToPQ(twinNode, twinPQ);
        }

        if (isSolvable) {
            while (searchNode.predecessor != null) {
                solutionBoards.push(searchNode.board);
                searchNode = searchNode.predecessor;
            }

            solutionBoards.push(initial);
        }
    }

    private boolean checkOnGoalBoard(Node<Board> node, boolean isTwin) {
        if (node.board.isGoal() && !isTwin) {
            isSolvable = true;
            moves = node.moves;
            return true;
        } else if (node.board.isGoal() && isTwin) {
            isSolvable = false;
            return true;
        }
        return false;
    }

    private MinPQ<Node<Board>> addNeighborsToPQ(Node<Board> searchNode, MinPQ<Node<Board>> originalPQ) {
        for (Board neighbor: searchNode.board.neighbors()) {
            if (searchNode.predecessor != null && neighbor.equals(searchNode.predecessor.board)) {
                continue;
            }
            Node<Board> nextNode = createNode(neighbor, searchNode);
            originalPQ.insert(nextNode);
        }
        return originalPQ;
    }

    private Node<Board> createNode(Board board, Node<Board> predecessor) {
        Node<Board> node = new Node<>();

        node.predecessor = predecessor;
        node.board = board;
        node.moves = predecessor == null ? 0 : predecessor.moves + 1;
        node.priority = board.manhattan() + node.moves;

        return node;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable) return -1;
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable) return null;
        return solutionBoards;
    }

//    public static void main(String[] args) {
//        // create initial board from file
//        In in = new In(args[0]);
//        int n = in.readInt();
//        int[][] blocks = new int[n][n];
//        for (int i = 0; i < n; i++)
//            for (int j = 0; j < n; j++)
//                blocks[i][j] = in.readInt();
//        Board initial = new Board(blocks);
//
//        // solve the puzzle
//        Solver solver = new Solver(initial);
//
//        // print solution to standard output
//        if (!solver.isSolvable())
//            StdOut.println("No solution possible");
//        else {
//            StdOut.println("Minimum number of moves = " + solver.moves());
//            for (Board board : solver.solution())
//                StdOut.println(board);
//        }
//    }
}