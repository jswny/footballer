package footballer;

import footballer.structure.*;

/**
 * Defines utilities to be used in multiple classes.
 */
public class Utils {
    /** Defines a list of the names of the current {@link Team}s as of 2017. */
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

    /**
     * Defines a list of {@link Team} names in order according to ESPN's final preseason rankings.
     *
     * For example, the {@code Patriots} are the first {@link Team} in the array, so they are ranked the highest (#1),
     * while the {@code Jets} are the last team in the array, so they are ranked last (#32).
     */
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
     * Generates a {@link Season} with the current NFL structure as of 2017.
     *
     * The structure is defined as follows:
     * <ul>
     *     <li>
     *         {@code AFC}
     *         <ul>
     *             <li>
     *                 {@code North}
     *                 <ul>
     *                     <li>{@code Steelers}</li>
     *                     <li>{@code Ravens}</li>
     *                     <li>{@code Browns}</li>
     *                     <li>{@code Bengals}</li>
     *                 </ul>
     *             </li>
     *             <li>
     *                 {@code East}
     *                 <ul>
     *                     <li>{@code Patriots}</li>
     *                     <li>{@code Jets}</li>
     *                     <li>{@code Dolphins}</li>
     *                     <li>{@code Bills}</li>
     *                 </ul>
     *             </li>
     *             <li>
     *                 {@code South}
     *                 <ul>
     *                     <li>{@code Jaguars}</li>
     *                     <li>{@code Titans}</li>
     *                     <li>{@code Texans}</li>
     *                     <li>{@code Colts}</li>
     *                 </ul>
     *             </li>
     *             <li>
     *                 {@code West}
     *                 <ul>
     *                     <li>{@code Chiefs}</li>
     *                     <li>{@code Chargers}</li>
     *                     <li>{@code Broncos}</li>
     *                     <li>{@code Raiders}</li>
     *                 </ul>
     *             </li>
     *         </ul>
     *     </li>
     *     <li>
     *         {@code NFC}
     *         <ul>
     *             <li>
     *                 {@code North}
     *                 <ul>
     *                     <li>{@code Vikings}</li>
     *                     <li>{@code Lions}</li>
     *                     <li>{@code Packers}</li>
     *                     <li>{@code Bears}</li>
     *                 </ul>
     *             </li>
     *             <li>
     *                 {@code East}
     *                 <ul>
     *                     <li>{@code Giants}</li>
     *                     <li>{@code Eagles}</li>
     *                     <li>{@code Cowboys}</li>
     *                     <li>{@code Redskins}</li>
     *                 </ul>
     *             </li>
     *             <li>
     *                 {@code South}
     *                 <ul>
     *                     <li>{@code Saints}</li>
     *                     <li>{@code Panthers}</li>
     *                     <li>{@code Falcons}</li>
     *                     <li>{@code Buccaneers}</li>
     *                 </ul>
     *             </li>
     *             <li>
     *                 {@code West}
     *                 <ul>
     *                     <li>{@code Rams}</li>
     *                     <li>{@code Seahawks}</li>
     *                     <li>{@code Cardinals}</li>
     *                     <li>{@code 49ers}</li>
     *                 </ul>
     *             </li>
     *         </ul>
     *     </li>
     * </ul>
     * @return the correctly-structured {@link Season}
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

    /**
     * Rounds a given {@code double} value to a certain decimal.
     * @param value the {@code double} value to be rounded
     * @param precision the precision to round {@code value} to
     * @return the rounded {@code double} value
     */
    public static double roundDecimal(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    /**
     * Upper cases the first letter of a given {@link String}.
     * @param str the {@link String} to be upper cased
     * @return the {@link String} {@code str} with its first letter upper cased
     */
    public static String upperCaseFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}

