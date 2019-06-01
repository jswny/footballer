package footballer.web;

import com.google.gson.Gson;
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
            Gson gson = new Gson();

            get("/:year/divisions", (req, res) -> {
                int year = Integer.parseInt(req.params("year"));
                Season season = Parser.parseCurrentStructure(year, 1);
                return season.getDivisionNames();
            }, gson::toJson);

            for (String rS : rankingSystems) {
                get("/:year/ranking/" + rS + "/week/:week", (req, res) -> { // Generate one default route for each ranking system by week
                    int year = Integer.parseInt(req.params("year"));
                    int week = Integer.parseInt(req.params("week"));
                    Season season = Parser.parseCurrentStructure(year, week);
                    String result = new Dataset(season, rS, week).serialize();
                    return result;
                });

                get("/:year/ranking/" + rS + "/week/:week/conference/:conference/division/:division", (req, res) -> { // Generate one route for each ranking system by week scoped by division
                    int year = Integer.parseInt(req.params("year"));
                    int week = Integer.parseInt(req.params("week"));
                    String conference = req.params("conference");
                    String division = req.params("division");
                    Season season = Parser.parseCurrentStructure(year, week);
                    String result = new Dataset(season, rS, week).filterByDivision(season, conference, division).serialize();
                    return result;
                });
            }
        });
    }

}
