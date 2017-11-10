package footballer.web;

import footballer.Utils;
import footballer.parse.Parser;
import footballer.ranking.Ranking;
import footballer.ranking.system.AdjustedWins;
import footballer.ranking.system.EvenPlay;
import footballer.ranking.system.SelfBased;
import footballer.structure.Season;
import footballer.structure.Team;
import java.util.List;
import static spark.Spark.*;
import com.google.gson.Gson;
import footballer.web.data.Dataset;

public class Server {
    public static void main(String[] args) {
        Gson gson = new Gson();
        staticFileLocation("/public");

        path("/api", () -> {
            get("/rankings/evenplay/:week", (req, res) -> {
                return getRankingDataset("evenplay", Integer.parseInt(req.params("week"))).getEntries();
            }, gson::toJson);

            get("/rankings/adjustedwins/:week", (req, res) -> {
                return getRankingDataset("adjustedwins", Integer.parseInt(req.params("week"))).getEntries();
            }, gson::toJson);

            get("/rankings/selfbased/:week", (req, res) -> {
                return getRankingDataset("selfbased", Integer.parseInt(req.params("week"))).getEntries();
            }, gson::toJson);
        });
    }

    public static Dataset getRankingDataset(String rankingName, int upToWeek) {
        Season season = Parser.parseCurrentStructure(2017, upToWeek);
        String[] teamNames = Utils.espnPreseasonRankings;
        List<Team> teams = season.getTeams();

        Ranking ranking;
        int maxBaseline;

        switch (rankingName) {
            case "evenplay":
                maxBaseline = 16;
                ranking = new EvenPlay(teams, 1.0);
                break;

            case "adjustedwins":
                maxBaseline = 0;
                ranking = new AdjustedWins(teams);
                break;

            case "selfbased":
                maxBaseline = 16;
                ranking = new SelfBased(teams);
                break;

            default:
                throw new RuntimeException("Cannot find dataset for ranking: " + rankingName + "!");
        }

        ranking.generateBaselineRankings(teamNames, 0, maxBaseline);
        ranking.applyGames(season);

        return new Dataset(ranking.getLog());
    }
}
