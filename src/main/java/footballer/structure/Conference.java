package footballer.structure;

import java.util.ArrayList;
import java.util.List;

public class Conference {
    public final String name;
    private List<Division> divisions = new ArrayList<>();

    public Conference(String n) {
        name = n;
    }

    public Division addDivision(String divisionName) {
        if (getDivision(divisionName) != null) return null;
        Division div = new Division(divisionName);
        divisions.add(div);
        return div;
    }

    public Team addTeam(String divisionName, String teamName) {
        Division div = getDivision(divisionName);
        if (div == null) return null;
        return div.addTeam(teamName);
    }

    public Division getDivision(String divisionName) {
        for (Division div : divisions) {
            if (div.name.equals(divisionName)) return div;
        }
        return null;
    }

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
