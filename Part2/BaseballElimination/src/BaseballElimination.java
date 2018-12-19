import edu.princeton.cs.algs4.*;

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

    private final int sNumber;
    private final int tNumber;
    private final int[][] results;
    private HashMap<String, TeamResult> teams;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException();
        }

        In in = new In(filename);
        int number = Integer.parseInt(in.readLine());
        sNumber = number;
        tNumber = number + 1;
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

    private FlowNetwork CreateFlowNetworkForTheTeam(String team) {
        TeamResult teamResult = teams.get(team);
        int winAndremSum = teamResult.wins + teamResult.left;

        int index = teams.size();
        int sum = 0;
        while (index > 0) {
            sum+=index--;
        }

        // 2 = s + t
        int vertices = 2 + sum + teams.size();

        // from s to every game + every game has 2 result edges = 3
        int edges = sum * 3 + teams.size();

        FlowNetwork network = new FlowNetwork(vertices, edges);

        // exclude the team from the loops
        for (int i = 0; i < teams.size(); i++) {
            for (int j = i + 1; j < teams.size(); j++) {
                // get current team index to get win count
                // ????
                int currentTeamWin = 0;

                int gamesNumber = i * 10 + j;
                // from s to remaining games
                FlowEdge edge = new FlowEdge(sNumber, gamesNumber, results[i][j]);
                network.addEdge(edge);

                // from remaining game to first team
                edge = new FlowEdge(gamesNumber, i, Double.POSITIVE_INFINITY);
                network.addEdge(edge);

                // from remaining game to second team
                edge = new FlowEdge(gamesNumber, j, Double.POSITIVE_INFINITY);
                network.addEdge(edge);

                // from every team to t
                edge = new FlowEdge(i, tNumber, winAndremSum - currentTeamWin);
            }
        }


    }

    private void RunFordFulkerson() {

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

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
