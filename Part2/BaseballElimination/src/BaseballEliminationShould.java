import org.junit.Assert;
import org.junit.Test;
import java.util.HashSet;

public class BaseballEliminationShould {
    private final static String team4FileName = "data\\teams4.txt";
    private final String testTeamName;
    private final String secondTeamName;
    private BaseballElimination elimination;

    public BaseballEliminationShould() {
        elimination = new BaseballElimination(team4FileName);
        var iterator = elimination.teams().iterator();
        testTeamName = iterator.next();
        secondTeamName = iterator.next();
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfAnyArgIsInvalidTeam() {
        elimination.wins(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfTeamNameIsUnknown() {
        elimination.wins("Rangers");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfAnyArgIsInvalidTeam1() {
        elimination.losses(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfAnyArgIsInvalidTeam2() {
        elimination.remaining(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfAnyArgIsInvalidTeam3() {
        elimination.against(null, testTeamName);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfAnyArgIsInvalidTeam4() {
        elimination.against(testTeamName, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfAnyArgIsInvalidTeam5() {
        elimination.wins(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ThrowAnExceptionIfAnyArgIsInvalidTeam6() {
        elimination.wins(null);
    }

    @Test
    public void ReadAndReturnRightDataFromFileTeam4() {
        Assert.assertEquals(4, elimination.numberOfTeams());
        var teams = new HashSet<>();
        teams.add("Atlanta");
        teams.add("Philadelphia");
        teams.add("New_York");
        teams.add("Montreal");
        var result = new HashSet<>();
        for (var team:elimination.teams()) {
            result.add(team);
        }
        Assert.assertEquals(teams, result);
    }

    @Test
    public void ReturnANumberOfWinsForCurrentFirstTestTeam() {
        Assert.assertEquals(83, elimination.wins(testTeamName));
    }

    @Test
    public void ReturnANumberOfLossesForCurrentFirstTestTeam() {
        Assert.assertEquals(71, elimination.losses(testTeamName));
    }

    @Test
    public void ReturnANumberOfRemainingGamesForCurrentFirstTestTeam() {
        Assert.assertEquals(8, elimination.remaining(testTeamName));
    }

    @Test
    public void ReturnANumberOfRemainingGamesBetweenTestTeamAndSecondTestTeam() {
        Assert.assertEquals(6, elimination.against(testTeamName, secondTeamName));
    }
}
