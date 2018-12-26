import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.FordFulkerson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class BaseballElimination {
    private final int[][] results;
    private final HashMap<String, TeamResult> teams;
    private int sNumber;
    private int tNumber;
    private HashMap<Integer, String[]> games;
    private int size;

    private final class TeamResult {
        private final int wins;
        private final int losses;
        private final int left;
        private final int index;

        private TeamResult(int wins, int losses, int left, int index) {
            this.wins = wins;
            this.losses = losses;
            this.left = left;
            this.index = index;
        }
    }

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException();
        }

        In in = new In(filename);
        int number = Integer.parseInt(in.readLine());
        teams = new HashMap<>();
        results = new int[number][number];
        HashSet<Integer> vertices = new HashSet<>();

        int iterator = 0;
        while (in.hasNextLine()) {
            int index = 0;
            String line = in.readLine();
            if (line == null) continue;
            String[] infos = line.trim().split("\\s+");
            teams.put(infos[index++],
                    new TeamResult(
                    Integer.parseInt(infos[index++]),
                    Integer.parseInt(infos[index++]),
                    Integer.parseInt(infos[index++]),
                            iterator));

            for (int i = 0; i < number; i++) {
                results[iterator][i] = Integer.parseInt(infos[index++]);
                if (results[iterator][i] != 0) {
                    vertices.add(iterator);
                    vertices.add(i);
                }
            }
            iterator++;
        }

        size = vertices.size();
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

        String result = isEliminatedByTrivialReason(team);
        if (teams.containsKey(result)) return true;

        ArrayList<Integer> vertices = runFordFulkerson(team);

        return vertices.size() > 1;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        if (team == null || !teams.containsKey(team)) {
            throw new IllegalArgumentException();
        }

        String result = isEliminatedByTrivialReason(team);
        if (teams.containsKey(result)) return Collections.singletonList(result);

        ArrayList<Integer> vertices = runFordFulkerson(team);

        if (vertices.size() <= 1) return null;

        return constructCertificateSet(vertices);
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

    private Iterable<String> constructCertificateSet(ArrayList<Integer> vertices) {
        HashSet<String> certificate = new HashSet<>();
        for (int elem: vertices) {
            if (games.containsKey(elem)) {
                certificate.add(games.get(elem)[0]);
                certificate.add(games.get(elem)[1]);
            }
        }
        return certificate;
    }

    private FlowNetwork createFlowNetworkForTheTeam(String team) {
        // remove current team info
        TeamResult teamResult = teams.remove(team);

        int winAndRemSum = teamResult.wins + teamResult.left;

        int index = size - 1;
        int sum = 0;
        while (index > 0) {
            sum += index--;
        }

        // 2 = s + t
        int verticesCount = 2 + sum + size;
        sNumber = 0;
        tNumber = verticesCount - 1;

        // from s to every game + every game has 2 result edges
        FlowNetwork network = new FlowNetwork(verticesCount);
        games = new HashMap<>();

        int verticesIndex = 1;
        FlowEdge edge;
        for (Map.Entry<String, TeamResult> key1 : teams.entrySet()) {
            String teamName1 = key1.getKey();
            TeamResult team1 = key1.getValue();
            int index1 = team1.index;
            for (Map.Entry<String, TeamResult> key2 : teams.entrySet()) {
                String teamName2 = key2.getKey();
                TeamResult team2 = key2.getValue();
                int index2 = team2.index;

                if (index1 >= index2 || results[index1][index2] == 0) continue;

                // from s to remaining games
                edge = new FlowEdge(sNumber, verticesIndex, results[index1][index2]);
                network.addEdge(edge);

                // from remaining game to first team
                edge = new FlowEdge(verticesIndex, index1 + sum + 1, Double.POSITIVE_INFINITY);
                network.addEdge(edge);

                // from remaining game to second team
                edge = new FlowEdge(verticesIndex, index2 + sum + 1, Double.POSITIVE_INFINITY);
                network.addEdge(edge);

                games.put(verticesIndex++, new String[]{ teamName1, teamName2 });
            }

            int capacity = winAndRemSum - team1.wins;
            if (capacity != 0) {
                edge = new FlowEdge(1 + sum + index1, tNumber, capacity);
                network.addEdge(edge);
            }
        }

        // put again current team info
        teams.put(team, teamResult);

        return network;
    }

    private ArrayList<Integer> runFordFulkerson(String team) {
        FlowNetwork network = createFlowNetworkForTheTeam(team);
        FordFulkerson algorithm = new FordFulkerson(network, sNumber, tNumber);
        ArrayList<Integer> minCutVertices = new ArrayList<>();
        for (int i = 0; i < network.V(); i++) {
            if (algorithm.inCut(i)) minCutVertices.add(i);
        }
        return minCutVertices;
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
}
