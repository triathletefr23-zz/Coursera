import org.junit.Assert;
import org.junit.Test;

public class SolverShould {
    private Board boardPuzzle02 = new Board(new int[][]{ { 1, 0 }, { 3, 2 } });
    private Board boardPuzzle03 = new Board(new int[][]{ { 2, 0 }, { 1, 3 } });
    private Board boardUnsolvableEx = new Board(new int[][]{ { 1, 2, 3 }, { 4, 5, 6 }, { 8, 7, 0 } });
    private Board boardPuzzle04 = new Board(new int[][]{ { 0, 1, 3 }, { 4, 2, 5 }, { 7, 8, 6 } });
    private Board boardPuzzle05 = new Board(new int[][]{ { 4, 1, 3 }, { 0, 2, 6 }, { 7, 5, 8 } });
    private Board boardPuzzle08 = new Board(new int[][]{ { 2, 3, 5 }, { 1, 0, 4 }, { 7, 8, 6 } });
    private Solver solver;

    private int countSteps() {
        int count = 0;
        for (Board el : solver.solution()) {
            count++;
        }
        return count;
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfInitBoardIsNull() {
        new Solver(null);
    }

    @Test
    public void ReturnCountOfMovesAndSolutionForPuzzle04() {
        solver = new Solver(boardPuzzle04);

        Assert.assertEquals(4, solver.moves());
        Assert.assertEquals(5, countSteps());
    }

    @Test
    public void ReturnCountOfMovesAndSolutionForPuzzle02() {
        solver = new Solver(boardPuzzle02);

        Assert.assertEquals(1, solver.moves());
        Assert.assertEquals(2, countSteps());
    }

    @Test
    public void ReturnCountOfMovesAndSolutionForPuzzle03() {
        solver = new Solver(boardPuzzle03);

        Assert.assertEquals(3, solver.moves());
    }

    @Test
    public void ReturnNegativeOneForUnsolvablePuzzle04() {
        solver = new Solver(boardUnsolvableEx);

        Assert.assertEquals(-1, solver.moves());
        Assert.assertNull(solver.solution());
    }

    @Test
    public void ReturnCountOfMovesAndSolutionForPuzzle05() {
        solver = new Solver(boardPuzzle05);

        Assert.assertEquals(5, solver.moves());
    }

    @Test
    public void ReturnCountOfMovesAndSolutionForPuzzle08() {
        solver = new Solver(boardPuzzle08);

        Assert.assertEquals(8, solver.moves());
    }
}
