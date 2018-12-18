import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

import java.util.Arrays;

public class BaseballElimination {
    private final String[] teams;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException();
        }

        In in = new In(filename);

        int number = Integer.parseInt(in.readLine());
        teams = new String[number];

        int iter = 0;
        while (in.hasNextLine()) {
            teams[iter] = in.readLine();

        }
    }

    // number of teams
    public int numberOfTeams() {
        return teams.length;
    }

    // all teams
    public Iterable<String> teams() {
        return Arrays.asList(teams);
    }

    // number of wins for given team
    public int wins(String team) {

    }

    // number of losses for given team
    public int losses(String team) {

    }

    // number of remaining games for given team
    public int remaining(String team) {

    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {

    }

    // is given team eliminated?
    public boolean isEliminated(String team) {

    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {

    }
}
