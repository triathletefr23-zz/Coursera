import edu.princeton.cs.algs4.In;
import java.util.HashMap;

public class BaseballElimination {
    private final class TeamResult {
        private int wins;
        private int losses;
        private int left;
        private int index;

        private TeamResult(int wins, int losses, int left, int index) {
            this.wins = wins;
            this.losses = losses;
            this.left = left;
            this.index = index;
        }
    }

    private final int[][] results;
    private HashMap<String, TeamResult> teams;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException();
        }

        In in = new In(filename);
        int number = Integer.parseInt(in.readLine());
        teams = new HashMap<>();
        results = new int[number][number];

        int iterator = 0;
        while (in.hasNextLine()) {
            var index = 0;
            String[] infos = in.readLine().split("\\s+");
            teams.put(infos[index++],
                    new TeamResult(
                    Integer.parseInt(infos[index++]),
                    Integer.parseInt(infos[index++]),
                    Integer.parseInt(infos[index++]),
                            iterator));

            for (int i = 0; i < number; i++) {
                results[iterator][i] = Integer.parseInt(infos[index++]);
            }
            iterator++;
        }
    }

    // number of teams
    public int numberOfTeams() {
        return teams.size();
    }

    // all teams
    public Iterable<String> teams() {
        return teams.keySet();
    }

    // number of wins for given team
    public int wins(String team) {
        if (team == null || !teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }

        return teams.get(team).wins;
    }

    // number of losses for given team
    public int losses(String team) {
        if (team == null || !teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }

        return teams.get(team).losses;
    }

    // number of remaining games for given team
    public int remaining(String team) {
        if (team == null || !teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }

        return teams.get(team).left;
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        if (team1 == null || team2 == null || !teams.containsKey(team1) || !teams.containsKey(team2)) {
            throw new IllegalArgumentException();
        }

        int firstTeamIndex = teams.get(team1).index;
        int secondTeamIndex = teams.get(team2).index;
        return results[firstTeamIndex][secondTeamIndex];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        if (team == null || !teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }

        return true;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        if (team == null || teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }

        return teams.keySet();
    }
}
