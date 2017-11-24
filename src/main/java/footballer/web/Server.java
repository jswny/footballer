package footballer.web;

import footballer.Utils;
import footballer.parse.Parser;
import footballer.ranking.RankingSystem;
import footballer.ranking.system.AdjustedWins;
import footballer.ranking.system.EvenPlay;
import footballer.ranking.system.SelfBased;
import footballer.structure.Season;
import footballer.structure.Team;
import java.util.List;
import static spark.Spark.*;
import com.google.gson.Gson;
import footballer.web.data.Dataset;

/**
 * Defines the entry point for the web component of this application.
 */
public class Server {

    /**
     * Initializes the API endpoints and frontend server based on the ranking systems defined in this method.
     * @param args command line arguments (currently not used)
     */
    public static void main(String[] args) {
        Gson gson = new Gson();
        staticFileLocation("/public");

        String[] rankingSystems = {"evenplay", "adjustedwins", "selfbased"};

        path("/api", () -> {

            // Generate one route for each ranking system
            for (String rS : rankingSystems) {
                get("/rankings/" + rS + "/:week", (req, res) -> {
                    return getRankingSystemDataset(rS, Integer.parseInt(req.params("week"))).getEntries();
                }, gson::toJson);
            }
        });
    }

    /**
     * Creates a {@link Dataset} for a given {@link RankingSystem} up to a given {@link footballer.structure.Week}.
     * This method throws a {@link RuntimeException} if no {@link RankingSystem} which matches {@code rankingSystemName} can be found.
     *
     * @param rankingSystemName the name of the {@link RankingSystem} to create the {@link Dataset} for
     * @param upToWeek the {@link footballer.structure.Week} maximum number (inclusive) for which the {@link RankingSystem} should be populated
     * @return the {@link Dataset} created using the {@link RankingSystem} which matches {@code rankingSystemName}, populated through {@code upToWeek}
     */
    private static Dataset getRankingSystemDataset(String rankingSystemName, int upToWeek) {
        Season season = Parser.parseCurrentStructure(2017, upToWeek);
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

        rankingSystem.generateBaselineRanking(teamNames, 0, maxBaseline);
        rankingSystem.applyGames(season);

        return new Dataset(rankingSystem.getLog());
    }
}
