package footballer.ranking;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import footballer.Utils;
import footballer.structure.Team;

/**
 * Maintains a log of {@link Rank} changes for each team in a {@link footballer.structure.Season}.
 */
public class RankLog {
    /**
     * Defines a pairing of a {@link footballer.structure.Week} number ({@code int}), and a {@link RankLogEntry}.
     * This class is used (internally) to keep track of each {@link footballer.structure.Team}'s change in {@link Rank} for each week.
     */
    private static class EntryPair {
        final int week;
        final RankLogEntry entry;

        EntryPair(int week, RankLogEntry entry) {
            this.week = week;
            this.entry = entry;
        }

        @Override
        public String toString() {
            return "Week " + week + ": \n" + entry;
        }
    }

    /** {@link Map} which maintains a correspondence of {@link RankLogEntry}s to their corresponding {@link footballer.structure.Week} numbers. */
    public Map<Integer, List<RankLogEntry>> entriesMap = new HashMap<>();
    private final List<Team> teams;

    public RankLog(List<Team> teams) {
        this.teams = teams;
    }

    /**
     * Adds a {@link RankLogEntry} to a given {@link footballer.structure.Week} number in this rank log.
     * @param weekNum the number of the week to add the {@link RankLogEntry} to
     * @param entry the {@link RankLogEntry} to add
     * @return the newly added {@link RankLogEntry}
     */
    public boolean addEntry(int weekNum, RankLogEntry entry) {
        List<RankLogEntry> logEntries = entriesMap.get(weekNum);
        if (logEntries == null) {
            logEntries = new ArrayList<>();
            entriesMap.put(weekNum, logEntries);
        }
        return logEntries.add(entry);
    }

    /**
     * Gets the greatest change (absolute value) for a given {@link Team}.
     * @param teamName the name of the {@link Team} to examine
     * @return the {@link EntryPair} which identifies the {@link footballer.structure.Week} number
     * and the corresponding {@link RankLogEntry} representing the greatest change for the {@link Team} which matches {@code teamName}
     */
    private EntryPair getGreatestChange(String teamName) {
        List<EntryPair> teamPairs = getPairsForTeam(teamName);

        EntryPair result = teamPairs.get(0);
        teamPairs.remove(0);

        for (EntryPair pair : teamPairs) {
            if (pair.entry != null && Math.abs(pair.entry.getDiff(teamName)) >= Math.abs(result.entry.getDiff(teamName))) result = pair;
        }

        return result;
    }

    /**
     * Gets the {@link EntryPair} for a given {@link Team} and a given {@link footballer.structure.Week} number.
     * @param teamName the name of the {@link Team} to find in the {@link List} of {@link RankLogEntry}s for the given {@link footballer.structure.Week} number
     * @param weekNum the {@link footballer.structure.Week} number to examine to find the corresponding {@link Team}'s {@link RankLogEntry}
     * @return the {@link EntryPair} representing the {@link footballer.structure.Week} number which matches {@code weekNum},
     * and the corresponding {@link RankLogEntry} for the {@link Team} which matches which matches {@code teamName},
     * or {@code null} if no such {@link RankLogEntry} exists
     */
    private EntryPair getTeamPair(String teamName, int weekNum) {
        for (RankLogEntry entry : entriesMap.get(weekNum)) {
            if (entry.isMember(teamName)) return new EntryPair(weekNum, entry);
        }

        return null;
    }

    /**
     * Gets all {@link EntryPair}s for a given {@link Team}.
     *
     * Fills in any {@link footballer.structure.Week}s for which the given {@link Team} does not have an {@link RankLogEntry}
     * with a {@link EntryPair} containing a {@code null} value for its {@code entry} field.
     *
     * @param teamName the name of the {@link Team} go retrieve {@link EntryPair}s for
     * @return a {@link List} of all {@link EntryPair}s which match {@code teamName}
     */
    private List<EntryPair> getPairsForTeam(String teamName) {
        List<EntryPair> result = new ArrayList<>();

        for (Integer key : entriesMap.keySet()) {
            EntryPair pair = getTeamPair(teamName, key);
            if (pair != null) {
                result.add(pair);
            } else {
                // If a team does not have an entry for this week,
                result.add(new EntryPair(key, null));
            }
        }

        return result;
    }

    /**
     * Gets a ({@link String}) listing of all {@link RankLogEntry}s for a given {@link Team}.
     * @param teamName the name of the {@link Team} to get the listing for
     * @return a {@link String} which contains all {@link RankLogEntry}s which match {@code teamName},
     * and their corresponding {@link footballer.structure.Week} numbers
     */
    public String getLogForTeam(String teamName) {
        String result = teamName + " log:\n\n";
        for (EntryPair pair : getPairsForTeam(teamName)) {
            if (pair.entry != null) result += pair.entry + "\n";
        }
        return result;
    }

    /**
     * Gets a given {@link Team}'s {@link Rank} values ({@link Double}s) for every {@link footballer.structure.Week} in this log.
     *
     * This method gets values for the {@link footballer.structure.Week}s which have been populated with at least one {@link RankLogEntry} so far.
     * For the first {@link footballer.structure.Week}, the {@link RankLogEntry}'s initial value for the given {@link Team} is added as the first item in the {@link List}.
     * For the rest of the {@link List}, the {@link RankLogEntry}'s new value for the given {@link Team} on that {@link footballer.structure.Week} is added to the list.
     * The only exceptions to these rules are the rules for bye {@link footballer.structure.Week}s, which are adjusted for as follows:
     * <ol>
     *     <li>
     *         If the bye {@link footballer.structure.Week} was the first week of the {@link footballer.structure.Season},
     *         the initial value from the second week's {@link RankLogEntry} is added to the {@link List}.
     *     </li>
     *     <li>
     *         If the bye {@link footballer.structure.Week} is any other week,
     *         the new value from the next week's {@link RankLogEntry} is added to the {@link List}.
     *     </li>
     * </ol>
     *
     * @param teamName the name of the {@link Team} to get the values for
     * @return the {@link List} containing all the {@link Double} {@link Rank} values for the given team, adjusted for bye weeks
     */
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

//    public Map<String, List<Double>> getAllValues() {
//        Map<String, List<Double>> result = new HashMap<>();
//
//        for (Team team : teams) {
//            result.put(team.name, getTeamValues(team.name));
//        }
//
//        return result;
//    }

    /**
     * Gets the {@link List} of {@link Team}s which this rank log is keeping track of.
     * @return the {link List} of all {@link Team}s this rank log can be used for
     */
    public List<Team> getTeams() {
        return teams;
    }

    /**
     * Gets each {@link Team}'s ranking data as defined by {@link #getTeamValues(String)} in CSV format.
     * @return the CSV-formatted data for each {@link Team} in this rank log
     */
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
