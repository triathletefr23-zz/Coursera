import java.util.Stack;

public final class Helper {
    private final int cols;
    private final int rows;

    public Helper(int rows, int cols) {
        this.cols = cols;
        this.rows = rows;
    }

    Iterable<int[]> findAdjacentPoints(int i, int j) {
        Stack<int[]> adjList = new Stack<>();
        int[] delta = new int[]{ -1, 0, 1 };
        for (int deltaX : delta) {
            int adjX = i + deltaX;
            for (int deltaY : delta) {
                int adjY = j + deltaY;

                if (i == adjX && j == adjY || !validatePoint(adjX, adjY)) continue;

                adjList.add(new int[]{ adjX, adjY });
            }
        }

        return adjList;
    }

    // private
    private boolean validatePoint(int i, int j) {
        return i >= 0 && i < rows && j >= 0 && j < cols;
    }
}
