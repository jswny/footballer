package footballer.ranking;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import footballer.Utils;
import footballer.structure.Team;

public class RankLog {
    public Map<Integer, List<RankLogEntry>> entriesMap = new HashMap<>();
    private final List<Team> teams;

    public RankLog(List<Team> teams) {
        this.teams = teams;
    }

    public boolean addEntry(int week, RankLogEntry entry) {
        List<RankLogEntry> logEntries = entriesMap.get(week);
        if (logEntries == null) {
            logEntries = new ArrayList<>();
            entriesMap.put(week, logEntries);
        }
        return logEntries.add(entry);
    }

    public EntryPair getGreatestChange(String teamName) {
        List<EntryPair> teamPairs = getPairsForTeam(teamName);

        EntryPair result = teamPairs.get(0);
        teamPairs.remove(0);

        for (EntryPair pair : teamPairs) {
            if (pair.entry != null && Math.abs(pair.entry.getDiff(teamName)) >= Math.abs(result.entry.getDiff(teamName))) result = pair;
        }

        return result;
    }

    private EntryPair getTeamPair(String teamName, int weekNum) {
        for (RankLogEntry entry : entriesMap.get(weekNum)) {
            if (entry.isMember(teamName)) return new EntryPair(weekNum, entry);
        }

        return null;
    }

    public List<EntryPair> getPairsForTeam(String teamName) {
        List<EntryPair> result = new ArrayList<>();

        for (Integer key : entriesMap.keySet()) {
            EntryPair pair = getTeamPair(teamName, key);
            if (pair != null) {
                result.add(pair);
            } else {
                result.add(new EntryPair(key, null));
            }
        }

        return result;
    }

    public String getLogForTeam(String teamName) {
        String result = teamName + " log:\n\n";
        for (EntryPair pair : getPairsForTeam(teamName)) {
            if (pair.entry != null) result += pair.entry + "\n";
        }
        return result;
    }

    public List<Double> getTeamValues(String teamName) {
        List<Double> result = new ArrayList<>();
        List<EntryPair> teamPairs = getPairsForTeam(teamName);

        Double initialValue;

        if (teamPairs.get(0).entry == null) { // Bye week was first week so use the initial value from week 2 instead
            initialValue = teamPairs.get(1).entry.getInitialValue(teamName);
        } else {
            initialValue = teamPairs.get(0).entry.getInitialValue(teamName);
        }

        result.add(initialValue); // Add the starting value as it isn't a "new" value

        for (int i = 0; i < teamPairs.size(); i++) {
            EntryPair pair = teamPairs.get(i);
            if (pair.entry == null) { // Bye week so get last week's value
                Double toAdd;
                if (i == 0) { // First week bye week
                    toAdd = teamPairs.get(i + 1).entry.getNewValue(teamName);
                } else {
                    toAdd = teamPairs.get(i - 1).entry.getNewValue(teamName);
                }
                result.add(Utils.roundDecimal(toAdd, 2));
            } else {
                result.add(Utils.roundDecimal(pair.entry.getNewValue(teamName), 2));
            }
        }

        return result;
    }

    public Map<String, List<Double>> getAllValues() {
        Map<String, List<Double>> result = new HashMap<>();

        for (Team team : teams) {
            result.put(team.name, getTeamValues(team.name));
        }

        return result;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public String getCSVData() {
        String result = "";

        // Team name headers
        for (Team team : teams) {
            result += team.name + ",";
        }

        result = result.substring(0, result.length() - 1);
        result += "\n";

        // Team values
        for (Integer weekNum : entriesMap.keySet()) {
            for (Team team : teams) {
                List<Double> teamValues = getTeamValues(team.name);
                result += teamValues.get(weekNum) + ",";
            }
            result = result.substring(0, result.length() - 1);
            result += "\n";
        }

        return result;
    }

    @Override
    public String toString() {
        String result = "";
        for (Integer key : entriesMap.keySet()) {
            result += "Week " + key + ": \n\n";
            for (RankLogEntry entry : entriesMap.get(key)) {
                result += entry + "\n";
            }
        }
        return result;
    }
}
