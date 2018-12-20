import edu.princeton.cs.algs4.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

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
    private int sNumber;
    private int tNumber;
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

    public FlowNetwork CreateFlowNetworkForTheTeam(String team) {
        TeamResult teamResult = teams.get(team);
        int currentTeamIndex = teamResult.index;
        int winAndRemSum = teamResult.wins + teamResult.left;

        int index = teams.size() - 1;
        int sum = 0;
        while (index > 0) {
            sum+=index--;
        }

        // 2 = s + t
        int vertices = 2 + sum + teams.size();
        sNumber = 0;
        tNumber = vertices - 1;

        // from s to every game + every game has 2 result edges = 3
        int edges = sum * 3 + teams.size();

        FlowNetwork network = new FlowNetwork(vertices, edges);

        Stack<Integer> usedVertices = new Stack<>();
        int verticesCount = 1;
        FlowEdge edge;
        for (TeamResult team1: teams.values()) {
            int index1 = team1.index;
            if (index1 == currentTeamIndex) continue;
            for (TeamResult team2: teams.values()) {
                int index2 = team2.index;
                int gameIndex = index1 * 10 + index2;

                if (index2 == currentTeamIndex ||
                        index1 == index2 ||
                        usedVertices.contains(gameIndex)) continue;

                usedVertices.push(gameIndex);

                // from s to remaining games
                edge = new FlowEdge(sNumber, verticesCount, results[index1][index2]);
                network.addEdge(edge);

                // from remaining game to first team
                edge = new FlowEdge(verticesCount, index1 + sum + 1, Double.POSITIVE_INFINITY);
                network.addEdge(edge);

                // from remaining game to second team
                edge = new FlowEdge(verticesCount++, index2 + sum + 1, Double.POSITIVE_INFINITY);
                network.addEdge(edge);
            }

            // get current team index to get wins count
            int currentTeamWin = team1.wins;
            // from every team to t
            edge = new FlowEdge(index1, tNumber, winAndRemSum - currentTeamWin);
            network.addEdge(edge);
        }

        return network;
    }

    private void RunFordFulkerson() {
        FordFulkerson algorithm;
        for (String team: teams.keySet()) {
            FlowNetwork network = CreateFlowNetworkForTheTeam(team);
            algorithm = new FordFulkerson(network, sNumber, tNumber);
        }
    }

    private String isEliminatedByTrivialReason(String team) {
        TeamResult teamResult = teams.get(team);
        int winAndRem = teamResult.wins + teamResult.left;
        for (String anotherTeam: teams.keySet()) {
            if (team.equals(anotherTeam)) continue;
            if (winAndRem < teams.get(anotherTeam).wins) return anotherTeam;
        }
        return null;
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        if (team == null || !teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }

        String result = isEliminatedByTrivialReason(team);
        if (teams.containsKey(result)) return true;

        return true;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        if (team == null || teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }

        String result = isEliminatedByTrivialReason(team);
        if (teams.containsKey(result)) return Collections.singletonList(result);

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
