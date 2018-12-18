import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class BaseballEliminationShould {
    private final static String team4FileName = "data\\teams4.txt";
    private BaseballElimination elimination;

    public BaseballEliminationShould() {
        elimination = new BaseballElimination(team4FileName);
    }

    @Test
    public void ReadAndReturnRightDataFromFileTeam4() {
        var teams = new String[]{"Atlanta", "Philadelphia", "New_York", "Montreal"};
        Assert.assertEquals(4, elimination.numberOfTeams());
        Assert.assertEquals(Arrays.asList(teams), elimination.teams());

    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfAnyArgIsInvalidTeam() {

    }
}
