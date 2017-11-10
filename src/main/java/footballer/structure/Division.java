package footballer.structure;

import java.util.List;
import java.util.ArrayList;

public class Division {
    public final String name;
    private List<Team> teams = new ArrayList<>();

    public Division(String n) {
        name = n;
    }

    public Team addTeam(String teamName) {
        if (getTeam(teamName) != null) return null;
        Team team = new Team(teamName);
        teams.add(team);
        return team;
    }

    public Team getTeam(String teamName) {
        for (Team team : teams) {
            if (team.name.equals(teamName)) return team;
        }
        return null;
    }

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
