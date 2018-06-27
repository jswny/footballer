package footballer.web.data;

import footballer.Utils;
import footballer.ranking.RankingSystem;
import footballer.ranking.logging.Log;
import footballer.ranking.system.AdjustedWins;
import footballer.ranking.system.EvenPlay;
import footballer.ranking.system.SelfBased;
import footballer.structure.Season;
import footballer.structure.Team;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.google.gson.Gson;

public class Dataset {

    private static class DatasetEntry {
        public final String label;
        public final List<Double> data;

        public DatasetEntry(String label, List<Double> data) {
            this.label = label;
            this.data = data;
        }
    }
    private List<DatasetEntry> entries = new ArrayList<>();

    /**
     * Creates a {@link Dataset} for a given {@link RankingSystem} up to a given {@link footballer.structure.Week}.
     * Throws a {@link RuntimeException} if no {@link RankingSystem} which matches {@code rankingSystemName} can be found.
     * @param rankingSystemName the name of the {@link RankingSystem} to create the {@link Dataset} for
     * @param upToWeek the {@link footballer.structure.Week} maximum number (inclusive) for which the {@link RankingSystem} should be populated
     */
    public Dataset(Season season, String rankingSystemName, int upToWeek) {
        String[] teamNames = Utils.espnPreseasonRankings;
        List<Team> teams = season.getTeams();

        RankingSystem rankingSystem;
        int maxBaseline;

        switch (rankingSystemName) {
            case "evenplay":
                maxBaseline = 16;
                rankingSystem = new EvenPlay(teams, 1.0);
                break;

            case "adjustedwins":
                maxBaseline = 0;
                rankingSystem = new AdjustedWins(teams);
                break;

            case "selfbased":
                maxBaseline = 16;
                rankingSystem = new SelfBased(teams);
                break;

            default:
                throw new RuntimeException("Cannot create dataset for ranking system: " + rankingSystemName + "!");
        }

        rankingSystem.generateBaselineRanks(teamNames, 0, maxBaseline);
        rankingSystem.applyGames(season);

        populateFromLog(rankingSystem.getLog());
    }

    private void populateFromLog(Log log) {
        for (Team team : log.getTeams()) {
            entries.add(new DatasetEntry(team.name, log.getTeamValues(team.name)));
        }
    }

    public List<DatasetEntry> getEntries() {
        return entries;
    }

    /**
     * Filters a {@link Dataset} by a specified {@link footballer.structure.Division} in a {@link footballer.structure.Conference} in a specific {@link Season}.
     * @param season the {@link Season} to retrieve the {@link footballer.structure.Division} from
     * @param conferenceName the name of the {@link footballer.structure.Conference} which contains the specified {@link footballer.structure.Division}
     * @param divisionName the name of the {@link footballer.structure.Division} to filter based on
     * @return the filtered {@link Dataset}
     */
    public Dataset filterByDivision(Season season, String conferenceName, String divisionName) {
        List<String> teamNamesInDivision = season
                                    .getDivision(conferenceName, divisionName)
                                    .getTeams()
                                    .stream()
                                    .map(team -> team.name)
                                    .collect(Collectors.toList());
        entries.stream().filter(entry -> teamNamesInDivision.contains(entry));
        return this;
    }

    /**
     * Serializes the {@link Dataset} to JSON using {@link Gson}.
     * The {@link Dataset} is serialized by serializing the inner array of {@link DatasetEntry}s without any wrapping object.
     * @return the JSON representation of this {@Dataset}
     */
    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this.getEntries());
    }
}
