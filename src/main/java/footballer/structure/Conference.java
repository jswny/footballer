package footballer.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines an NFL conference.
 */
public class Conference {
    public final String name;
    private List<Division> divisions = new ArrayList<>();

    public Conference(String n) {
        name = n;
    }

    /**
     * Adds a {@link Division} to this conference.
     * @param divisionName the name of the {@link Division} to be added
     * @return the newly created division, or {@code null} if the {@code divisionName} already exists in this conference
     */
    public Division addDivision(String divisionName) {
        if (getDivision(divisionName) != null) return null;
        Division div = new Division(divisionName);
        divisions.add(div);
        return div;
    }

    /**
     * Adds a {@link Team} to a {@link Division} in this conference.
     * @param divisionName the name of the {@link Division} to add the team to
     * @param teamName the name of the {@link Team} to add
     * @return the newly added team, or {@code null} if the specified {@code divisionName} does not exist in this conference
     */
    public Team addTeam(String divisionName, String teamName) {
        Division div = getDivision(divisionName);
        if (div == null) return null;
        return div.addTeam(teamName);
    }

    /**
     * Gets a {@link Division} in this conference.
     * @param divisionName the name of the {@link Division} to get
     * @return the {@link Division} with a {@code name} field which matches {@code divisionName}, or {@code null} if no such {@link Division} exists
     */
    public Division getDivision(String divisionName) {
        for (Division div : divisions) {
            if (div.name.equals(divisionName)) return div;
        }
        return null;
    }

    /**
     * Gets a list of all {@link Team}s in this conference.
     * @return A {@link List} of all {@link Team}s in {@link Division}s in this conference
     */
    public List<Team> getTeams() {
        List<Team> teams = new ArrayList<>();
        for (Division div : divisions) {
            teams.addAll(div.getTeams());
        }
        return teams;
    }

    @Override
    public String toString() {
        String result = name + ": [\n";
        for (Division div : divisions) {
            result += "            " + div + ",\n";
        }
        result = result.substring(0, result.length() - 2);
        return result + "\n        ]";
    }
}
