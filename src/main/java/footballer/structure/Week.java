package footballer.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines an NFL week of {@link Game}s.
 */
public class Week {
    public final int number;
    private List<Game> games = new ArrayList<>();

    public Week(int num) {
        number = num;
    }

    /**
     * Adds a {@link Game} to this week.
     * @param game the game to be added
     * @return the {@link Game} which was added
     */
    public Game addGame(Game game) {
        games.add(game);
        return game;
    }

    /**
     * Determines if a week is empty.
     * A week is empty when no games have been played in it yet.
     * @return {@code true} if the week is empty, or {@code false} otherwise
     */
    public boolean isEmpty() {
        return games.size() == 0;
    }

    /**
     * Gets all of the {@link Game}s in this week.
     * @return a {@link List} of all {@link Game}s in this week
     */
    public List<Game> getGames() {
        return games;
    }

    @Override
    public String toString() {
        if (isEmpty()) return number + ": empty";
        String result = number + ": [\n";
        for (Game game : games) {
            result += "            " + game + ",\n";
        }
        result = result.substring(0, result.length() - 2);
        return result + "\n        ]";
    }
}
