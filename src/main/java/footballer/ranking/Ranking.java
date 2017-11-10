package footballer.ranking;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import footballer.structure.Game;
import footballer.structure.Season;
import footballer.structure.Team;
import footballer.structure.Week;

public abstract class Ranking {
    protected List<Rank> ranks = new ArrayList<>();
    protected RankLog log;

    public Ranking(List<Team> teams) {
        for (Team team : teams) ranks.add(new Rank(team));
        log = new RankLog(teams);
    }

    public void applyGames(Season season) {
        for (Week week : season.getWeeks()) {
            for (Game game : week.getGames()) {
                RankLogEntry entry = applyGame(game);
                log.addEntry(week.number, entry);
            }
        }
    }

    // TODO: coordinate with rank log entry to handle tied initial ranks where there is not a favorite and an underdog
    protected abstract RankLogEntry applyGame(Game game);

    public void generateBaselineRankings(String[] teamNames, double increment, double max) {
        double multiplier = 0;

        for (String teamName : teamNames) {
            getRank(teamName).setValue(max - (multiplier * increment));
//			System.out.println(teamName + ", " + (max - (multiplier * increment)));
            multiplier++;
        }
    }

    public RankLog getLog() {
        return log;
    }

    public String getLogForTeam(String teamName) {
        String result = teamName + " log:\n\n";
        for (EntryPair pair : log.getPairsForTeam(teamName)) {
            if (pair.entry != null) result += pair.entry + "\n";
        }
        return result;
    }

    public List<Double> getTeamValues(String teamName) {
        return log.getTeamValues(teamName);
    }

    public Map<String, List<Double>> getAllValues() {
        return log.getAllValues();
    }

    public String getCSVData() {
        return log.getCSVData();
    }

    public EntryPair getGreatestChange(String teamName) {
        return log.getGreatestChange(teamName);
    }

    public Rank getGreaterRank(Team first, Team second) {
        Rank firstRank = getRank(first.name);
        Rank secondRank = getRank(second.name);
        return firstRank.getValue() > secondRank.getValue() ? firstRank : secondRank;
    }

    public Rank getRank(String teamName) {
        for (Rank rank : ranks) {
            if (rank.team.name.equals(teamName)) return rank;
        }
        return null;
    }

    public String toString() {
        String result = "#Ranking<[\n";
        List<Rank> tempList = new ArrayList<>(ranks); // Copy the list so we don't reorder the original
        tempList.sort(Rank.valueComparator);
        int count = 1;
        for (Rank rank : tempList) {
            result += "    " + count + ": " + rank + ",\n";
            count++;
        }
        result = result.substring(0, result.length() - 2);
        return result + "\n]>";
    }
}
