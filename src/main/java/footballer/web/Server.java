package footballer.web;

import footballer.parse.Parser;
import footballer.structure.Season;
import java.util.List;
import static spark.Spark.*;
import com.google.gson.Gson;
import footballer.web.data.Dataset;
import footballer.web.data.DatasetEntry;

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

            for (String rS : rankingSystems) {
                // Generate one default route for each ranking system by week
                get("/rankings/" + rS + "/:week", (req, res) -> {
                    int week = Integer.parseInt(req.params("week"));
                    Season season = Parser.parseCurrentStructure(2017, week);
                    List<DatasetEntry> entries = new Dataset(season, rS, week).getEntries();
                    return entries;
                }, gson::toJson);

                // Generate one route for each ranking system by week scoped by division
                get("/rankings/" + rS + "/:week/:conference/:division", (req, res) -> {
                    int week = Integer.parseInt(req.params("week"));
                    String conference = req.params("conference");
                    String division = req.params("division");
                    Season season = Parser.parseCurrentStructure(2017, week);
                    List<DatasetEntry> entries = new Dataset(season, rS, week).filterByDivision(season, conference, division).getEntries();
                    return entries;
                }, gson::toJson);
            }
        });
    }

}
