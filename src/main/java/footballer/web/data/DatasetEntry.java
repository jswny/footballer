package footballer.web.data;

import java.util.List;

public class DatasetEntry {
    public final String label;
    public final List<Double> data;

    public DatasetEntry(String label, List<Double> data) {
        this.label = label;
        this.data = data;
    }
}
