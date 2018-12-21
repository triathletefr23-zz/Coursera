import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

public class BaseballEliminationShould {
    private final static String FOUR_TEAMS_FILENAME = "data\\teams4.txt";
    private final static String FIVE_TEAMS_FILENAME = "data\\teams5.txt";
    private final static String SEVEN_TEAMS_FILENAME = "data\\teams7.txt";
    private final static String TWENTY_FOUR_TEAMS_FILENAME = "data\\teams24.txt";
    private final static String DETROIT = "Detroit";
    private final static String MONTREAL = "Montreal";
    private final static String ATLANTA = "Atlanta";
    private final static String PHILADELPHIA = "Philadelphia";
    private final static String NEW_YORK = "New_York";
    private final static String IRELAND = "Ireland";
    private final String testTeamName;
    private final String secondTeamName;
    private BaseballElimination elimination;

    public BaseballEliminationShould() {
        elimination = new BaseballElimination(FOUR_TEAMS_FILENAME);
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

//    @Test
//    public void ReturnTeamForTrivialEliminationOfMontreal() {
//        Assert.assertNotNull(elimination.isEliminatedByTrivialReason(MONTREAL));
//    }
//
//    @Test
//    public void ReturnNullForTrivialEliminationOfMontreal() {
//        Assert.assertNull(elimination.isEliminatedByTrivialReason(ATLANTA));
//    }

//    @Test
////    public void ConstructFlowNetworkAtalantaFor4Teams() {
////        var network = elimination.CreateFlowNetworkForTheTeam(ATLANTA);
////        var sum = computeSum(elimination.numberOfTeams() - 1);
////        var result = 3 * sum + elimination.numberOfTeams() - 1;
////        Assert.assertEquals(8, network.V());
////        Assert.assertEquals(result, network.E());
////    }
////
////    @Test
////    public void ConstructFlowNetworkPhiladelphiaFor4Teams() {
////        var network = elimination.CreateFlowNetworkForTheTeam(PHILADELPHIA);
////        var sum = computeSum(elimination.numberOfTeams() - 1);
////        var result = 3 * sum + elimination.numberOfTeams() - 1;
////        Assert.assertEquals(8, network.V());
////        Assert.assertEquals(result, network.E());
////    }

    @Test
    public void ReturnTrueForPhiladelphia() {
        var result = elimination.isEliminated(PHILADELPHIA);
        Assert.assertTrue(result);
    }

    @Test
    public void ReturnFalseForNew_York() {
        var result = elimination.isEliminated(NEW_YORK);
        Assert.assertFalse(result);
    }

    @Test
    public void ReturnFalseForAtlanta() {
        var result = elimination.isEliminated(ATLANTA);
        Assert.assertFalse(result);
    }

    @Test
    public void ReturnFalseForNew_YourFor5Teams() {
        elimination = new BaseballElimination(FIVE_TEAMS_FILENAME);
        var result = elimination.isEliminated(NEW_YORK);
        Assert.assertFalse(result);
    }

    @Test
    public void ReturnTrueForDetroitFor5Teams() {
        elimination = new BaseballElimination(FIVE_TEAMS_FILENAME);
        var result = elimination.isEliminated(DETROIT);
        Assert.assertTrue(result);
    }

    @Test
    public void ReturnTrueForAnotherTeamFor5Teams() {
        elimination = new BaseballElimination(FIVE_TEAMS_FILENAME);
        var team = elimination.teams().iterator().next();
        var result = elimination.isEliminated(team);
        Assert.assertTrue(result);
    }

    @Test
    public void ReturnRWithAtlantaForMontrealFor4Teams() {
        var certificate = convertIterableToList(elimination.certificateOfElimination(MONTREAL));
        Assert.assertEquals(1, certificate.size());
        Assert.assertEquals(ATLANTA, certificate.get(0));
    }

    @Test
    public void ReturnRWithAtlantaAndNew_YorkForPhiladelphiaFor4Teams() {
        var certificate = convertIterableToList(elimination.certificateOfElimination(PHILADELPHIA));
        Assert.assertEquals(2, certificate.size());
        Assert.assertTrue(certificate.contains(ATLANTA));
        Assert.assertTrue(certificate.contains(NEW_YORK));
    }

    @Test
    public void ReturnNullForNotEliminatedNew_YorkFor5Teams() {
        elimination = new BaseballElimination(FIVE_TEAMS_FILENAME);
        var certificate = elimination.certificateOfElimination(NEW_YORK);
        Assert.assertNull(certificate);
    }

    @Test
    public void ReturnRWith4TeamsForDetroitFor5Teams() {
        elimination = new BaseballElimination(FIVE_TEAMS_FILENAME);
        var certificate = convertIterableToList(elimination.certificateOfElimination(DETROIT));
        Assert.assertEquals(4, certificate.size());
    }

    @Test
    public void ReturnTrueForEliminatedIrelandFor7Teams() {
        elimination = new BaseballElimination(SEVEN_TEAMS_FILENAME);
        var result = elimination.isEliminated(IRELAND);
        Assert.assertTrue(result);
    }

    @Test
    public void ReturnTrueForEliminatedTeam13For24Teams() {
        elimination = new BaseballElimination(TWENTY_FOUR_TEAMS_FILENAME);
        var result = elimination.isEliminated("Team13");
        Assert.assertTrue(result);
    }

    private ArrayList<String> convertIterableToList(Iterable<String> iterable) {
        ArrayList<String> list = new ArrayList<>();
        for (var elem: iterable) {
            list.add(elem);
        }
        return list;
    }

    private int computeSum(int n) {
        var i = n - 1;
        var sum = 0;
        while (i > 0) {
            sum+=i--;
        }
        return sum;
    }
}
