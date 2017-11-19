package footballer.structure;

import java.util.List;
import java.util.ArrayList;

/**
 * Defines an NFL division.
 */
public class Division {
    public final String name;
    private List<Team> teams = new ArrayList<>();

    public Division(String n) {
        name = n;
    }

    /**
     * Adds a {@link Team} to this division.
     * @param teamName the name of the {@link Team} to be added
     * @return the newly created {@link Team}, or {@code null} if a {@link Team} which matches {@code teamName} already exists in this division
     */
    public Team addTeam(String teamName) {
        if (getTeam(teamName) != null) return null;
        Team team = new Team(teamName);
        teams.add(team);
        return team;
    }

    /**
     * Gets a {@link Team} in this division by its name.
     * @param teamName the name of the {@link Team} to get
     * @return the {@link Team} which matches {@code teamName}, or {@code null} if no such team exists
     */
    public Team getTeam(String teamName) {
        for (Team team : teams) {
            if (team.name.equals(teamName)) return team;
        }
        return null;
    }

    /**
     * Gets a list of all {@link Team}s in this division.
     * @return a {@link List} of all {@link Team}s in this division
     */
    public List<Team> getTeams() {
        return teams;
    }

    @Override
    public String toString() {
        String result = name + ": [";
        for (Team team : teams) {
            result += team + ", ";
        }
        result = teams.isEmpty() ? result : result.substring(0, result.length() - 2);
        return result + "]";
    }

}
