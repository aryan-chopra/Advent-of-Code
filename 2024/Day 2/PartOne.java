
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class PartOne {
    public interface CompareLevel {
        public boolean compare(int prevLevel, int currLevel);
    }

    private boolean isValidReport(List<Integer> report, CompareLevel compareLevel) {
        for (int index = 1; index < report.size(); index++) {
            int currLevel = report.get(index);
            int prevLevel = report.get(index - 1);

            if (!(compareLevel.compare(prevLevel, currLevel))) {
                return false;
            }
        }

        return true;
    }

    public int getValidReports(List<List<Integer>> data) {
        CompareLevel ascendingComparator = (prevLevel, currLevel) -> {
            return (Math.abs(currLevel - prevLevel) <= 3 && currLevel > prevLevel);
        };

        CompareLevel descendingComparator = (prevLevel, currLevel) -> {
            return (Math.abs(currLevel - prevLevel) <= 3 && currLevel < prevLevel);
        };

        int validReports = 0;
        for (List<Integer> report : data) {
            if (isValidReport(report, ascendingComparator) || isValidReport(report, descendingComparator)) {
                validReports++;
            }
        }
        return validReports;
    }

    public static void main(String[] args) throws Exception {
        PartOne p = new PartOne();
        @SuppressWarnings("Convert2Diamond")
        List<List<Integer>> data = new ArrayList<List<Integer>>();
        FileReader file = new FileReader("./input.txt");

        try (BufferedReader reader = new BufferedReader(file)) {
            String line;

            while ((line = reader.readLine()) != null) {
                String tokens[] = line.split(" ");
                @SuppressWarnings("Convert2Diamond")
                List<Integer> row = new ArrayList<Integer>();

                for (String token : tokens) {
                    row.add(Integer.valueOf(token));
                }

                data.add(row);
            }
        }

        System.out.println(p.getValidReports(data));
    }
}
