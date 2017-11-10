package footballer.structure;

import java.util.ArrayList;
import java.util.List;

public class Week {
    public final int number;
    private List<Game> games = new ArrayList<>();

    public Week(int num) {
        number = num;
    }

    public Game addGame(Game game) {
        games.add(game);
        return game;
    }

    public boolean isEmpty() {
        return games.size() == 0;
    }

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
