package footballer.web;

import footballer.parse.Parser;
import footballer.structure.Season;
import static spark.Spark.*;
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
        staticFileLocation("/public");

        String[] rankingSystems = {"evenplay", "adjustedwins", "selfbased"};

        path("/api", () -> {

            for (String rS : rankingSystems) {
                // Generate one default route for each ranking system by week
                get("/rankings/" + rS + "/:week", (req, res) -> {
                    int week = Integer.parseInt(req.params("week"));
                    Season season = Parser.parseCurrentStructure(2017, week);
                    String result = new Dataset(season, rS, week).serialize();
                    return result;
                });

                // Generate one route for each ranking system by week scoped by division
                get("/rankings/" + rS + "/:week/:conference/:division", (req, res) -> {
                    int week = Integer.parseInt(req.params("week"));
                    String conference = req.params("conference");
                    String division = req.params("division");
                    Season season = Parser.parseCurrentStructure(2017, week);
                    String result = new Dataset(season, rS, week).filterByDivision(season, conference, division).serialize();
                    return result;
                });
            }
        });
    }

}
