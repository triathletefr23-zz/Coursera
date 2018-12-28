import java.util.Stack;

public final class Helper {
    private final int cols;
    private final int rows;

    public Helper(int rows, int cols) {
        this.cols = cols;
        this.rows = rows;
    }

    public Iterable<Integer> findAdjacentPoints(int i, int cols) {
        Stack<Integer> adjList = new Stack<>();
        int[] delta = new int[]{ -1, 0, 1 };
        int iX = i / cols;
        int iY = i % cols;
        for (int deltaX: delta) {
            int adjX = iX + deltaX;
            for (int deltaY: delta) {
                int adjY = iY + deltaY;

                if (iX == adjX && iY == adjY || !validatePoint(adjX, adjY)) continue;

                adjList.add(adjX * cols + adjY);
            }
        }
        return adjList;
    }

    // private
    private boolean validatePoint(int i, int j) {
        return i >= 0 && i < rows && j >= 0 && j < cols;
    }

    public int getSizeOfIterable(Iterable list) {
        int count = 0;
        for (Object el: list) {
            count++;
        }
        return count;
    }
}
