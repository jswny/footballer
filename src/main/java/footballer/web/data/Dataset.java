package footballer.web.data;

import footballer.ranking.logging.Log;
import footballer.structure.Team;
import java.util.ArrayList;
import java.util.List;

public class Dataset {
    private List<DatasetEntry> entries = new ArrayList<>();

    public Dataset(Log log) {
        for (Team team : log.getTeams()) {
            entries.add(new DatasetEntry(team.name, log.getTeamValues(team.name)));
        }
    }

    public List<DatasetEntry> getEntries() {
        return entries;
    }
}
