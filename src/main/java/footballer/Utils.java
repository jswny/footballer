package footballer;

import footballer.structure.*;

public class Utils {
    public static String[] currentTeams = {
            "Patriots",
            "Bills",
            "Dolphins",
            "Jets",
            "Steelers",
            "Ravens",
            "Bengals",
            "Browns",
            "Titans",
            "Jaguars",
            "Texans",
            "Colts",
            "Chiefs",
            "Broncos",
            "Raiders",
            "Chargers",
            "Eagles",
            "Redskins",
            "Cowboys",
            "Giants",
            "Vikings",
            "Packers",
            "Lions",
            "Bears",
            "Panthers",
            "Saints",
            "Falcons",
            "Buccaneers",
            "Rams",
            "Seahawks",
            "Cardinals",
            "49ers"
    };

    public static String[] espnPreseasonRankings = {
            "Patriots",
            "Falcons",
            "Seahawks",
            "Steelers",
            "Packers",
            "Cowboys",
            "Raiders",
            "Chiefs",
            "Broncos",
            "Cardinals",
            "Giants",
            "Titans",
            "Buccaneers",
            "Texans",
            "Eagles",
            "Panthers",
            "Vikings",
            "Colts",
            "Lions",
            "Redskins",
            "Ravens",
            "Bengals",
            "Saints",
            "Chargers",
            "Dolphins",
            "Bills",
            "Rams",
            "Jaguars",
            "Bears",
            "Browns",
            "49ers",
            "Jets"
    };

    /**
     * Generates a season with the current NFL structure as of 2017.
     * @return the correctly-structured season
     */
    public static Season createCurrentStructure(int seasonYear) {
        // Create the season
        Season season = new Season(seasonYear);

        // Create the conferences
        String[] conferences = {"AFC", "NFC"};
        for (String conf : conferences) season.addConference(conf);

        // Create the divisions
        String[] divisions = {"East", "North", "South", "West"};
        for (String conf : conferences) {
            for (String div : divisions) {
                season.addDivision(conf, div);
            }
        }

        //
        // Create the teams
        //

        String[] teams;

        // AFC

        teams = new String[] {"Patriots", "Bills", "Dolphins", "Jets"};
        for (String teamName : teams) {
            season.addTeam("AFC", "East", teamName);
        }

        teams = new String[] {"Steelers", "Ravens", "Bengals", "Browns"};
        for (String teamName : teams) {
            season.addTeam("AFC", "North", teamName);
        }

        teams = new String[] {"Titans", "Jaguars", "Texans", "Colts"};
        for (String teamName : teams) {
            season.addTeam("AFC", "South", teamName);
        }

        teams = new String[] {"Chiefs", "Broncos", "Raiders", "Chargers"};
        for (String teamName : teams) {
            season.addTeam("AFC", "West", teamName);
        }

        // NFC

        teams = new String[] {"Eagles", "Redskins", "Cowboys", "Giants"};
        for (String teamName : teams) {
            season.addTeam("NFC", "East", teamName);
        }

        teams = new String[] {"Vikings", "Packers", "Lions", "Bears"};
        for (String teamName : teams) {
            season.addTeam("NFC", "North", teamName);
        }

        teams = new String[] {"Panthers", "Saints", "Falcons", "Buccaneers"};
        for (String teamName : teams) {
            season.addTeam("NFC", "South", teamName);
        }

        teams = new String[] {"Rams", "Seahawks", "Cardinals", "49ers"};
        for (String teamName : teams) {
            season.addTeam("NFC", "West", teamName);
        }

        return season;
    }

    public static double roundDecimal(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    public static String upperCaseFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}

