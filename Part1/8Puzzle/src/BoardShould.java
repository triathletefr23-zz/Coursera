import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

public class BoardShould {
    private int[][] testBlocks;
    private Board board;

    public void init(int count, boolean isGoal) {
        testBlocks = new int[count][count];
        var generatedValues = generateAllValues(count);
        for (int i = 0; i < testBlocks.length; i++) {
            for (int j = 0; j < testBlocks.length; j++) {
                if (isGoal) {
                    testBlocks[i][j] = i * count + j + 1;
                } else {
                    var value = getRandomValue(generatedValues);                    
                    testBlocks[i][j] = value;
                    generatedValues.remove(0);
                }
            }
        }

        if (isGoal) {
            testBlocks[count - 1][count - 1] = 0;
        }

        board = new Board(testBlocks);
    }

    private ArrayList<Integer> generateAllValues(int size) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < size * size; i++) {
            list.add(i);
        }
        return list;
    }

    private int getRandomValue(ArrayList<Integer> nonGeneratedValues) {
        Collections.shuffle(nonGeneratedValues);
        return nonGeneratedValues.get(0);
    }

    @Test
    public void ReturnRealDimension() {
        init(5, false);
        Assert.assertEquals(5, board.dimension());
    }

    @Test
    public void ReturnCorrectDistanceForCurrentBoard() {
        int[][] currentBlocks = new int[][]{ { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };
        board = new Board(currentBlocks);
        Assert.assertEquals(5, board.hamming());
        Assert.assertEquals(10, board.manhattan());
    }

    @Test
    public void ReturnCorrectDistanceForCurrentBoard1() {
        int[][] currentBlocks = new int[][]{ { 0, 1, 3 }, { 4, 2, 5 }, { 7, 8, 6 } };
        board = new Board(currentBlocks);
        Assert.assertEquals(4, board.hamming());
        Assert.assertEquals(4, board.manhattan());
    }

    @Test
    public void ReturnCorrectDistanceForCurrentBoard2() {
        int[][] currentBlocks = new int[][]{ { 1, 2, 3 }, { 4, 0, 5 }, { 7, 8, 6 } };
        board = new Board(currentBlocks);
        Assert.assertEquals(2, board.hamming());
        Assert.assertEquals(2, board.manhattan());
    }

    @Test
    public void ReturnHammingDistanceForGoalBoard() {
        init(5, true);
        Assert.assertEquals(0, board.hamming());
    }

    @Test
    public void ReturnManhattanDistanceForGoalBoard() {
        init(5, true);
        Assert.assertEquals(0, board.manhattan());
    }

    @Test
    public void ReturnTrueOnGoadBoard() {
        init(5, true);
        Assert.assertEquals(true, board.isGoal());
    }

    @Test
    public void ReturnIntFromChar() {
        int x = 10;
        char character = (char)x;
        int y = (int) character;
        Assert.assertEquals(x, y);
    }


    @Test
    public void ReturnTwinBoardForGoalBoard() {
        init(5, true);
        Board twinBoard = board.twin();
        Assert.assertEquals(2, twinBoard.hamming());
    }

//    @Test
//    public void ReturnTwinBoardForRandomBoard() {
//        init(3, false);
//        Board twinBoard = board.twin();
//        var x = board.manhattan();
//        var y = twinBoard.manhattan();
//        Assert.assertEquals(1, Math.abs(board.manhattan() - twinBoard.manhattan()));
//    }

    @Test
    public void ReturnFalseIfObjectIsNull() {
        init(5, true);
        Assert.assertFalse(board.equals(null));
    }

    @Test
    public void ReturnFalseIfAnotherClassOfObject() {
        init(5, true);
        Assert.assertFalse(board.equals(5));
    }

    @Test
    public void ReturnFalseIfDifferentDimensions() {
        init(5, true);
        Board newBoard = new Board(new int[1][1]);
        Assert.assertFalse(board.equals(newBoard));
    }

    @Test
    public void ReturnFalseIfThereAreDiffElements() {
        init(5, true);
        Board newBoard = board.twin();
        Assert.assertFalse(board.equals(newBoard));
    }

    @Test
    public void ReturnTrueIfBoardsAreEqual() {
        init(5, true);
        Board newBoard = new Board(testBlocks);
        Assert.assertTrue(board.equals(newBoard));
    }

    @Test
    public void ReturnAllNeighborsOfCurrentBoardSize3() {
        init(3, true);
        var boards = board.neighbors();
        int count = 0;
        for (var el: boards) {
            count++;
            Assert.assertEquals(1, Math.abs(board.manhattan() - el.manhattan()));
        }
        Assert.assertEquals(2, count);
    }

    @Test
    public void ReturnAllNeighborsOfCurrentBoardSize5() {
        init(4, true);
        var boards = board.neighbors();
        int count = 0;
        for (var el: boards) {
            count++;
        }
        Assert.assertEquals(2, count);
    }

    @Test
    public void ReturnAllNeighborsOfCurrentRandomBoardSize3() {
        int[][] currentBlocks = new int[][]{ { 1, 2, 3 }, { 4, 0, 5 }, { 7, 8, 6 } };
        board = new Board(currentBlocks);
        var boards = board.neighbors();
        int count = 0;
        for (var el: boards) {
            count++;
            Assert.assertEquals(1, Math.abs(board.manhattan() - el.manhattan()));
        }
        Assert.assertEquals(4, count);
    }

    @Test
    public void ReturnSameTwins() {
        init(3, true);
        Board twin1 = board.twin();
        Board twin2 = board.twin();
        Board twin3 = board.twin();
        Assert.assertTrue(twin1.equals(twin2) && twin2.equals(twin3));
    }
}