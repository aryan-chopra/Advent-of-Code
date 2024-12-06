
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class PartTwo {
    public interface CompareLevel {
        public boolean compare(int prevLevel, int currLevel);
    }

    // private boolean isValidReportWithLevelSkipped(List<Integer> report,
    // CompareLevel compareLevel, int levelToSkip) {
    // int prevLevelIndex;
    // if (levelToSkip == 0) {
    // prevLevelIndex = 1;
    // } else {
    // prevLevelIndex = 0;
    // }
    // int prevLevel = report.get(prevLevelIndex);
    // for (int index = prevLevelIndex + 1; index < report.size(); index++) {
    // if (index == levelToSkip) {
    // continue;
    // }
    // int currLevel = report.get(index);
    // if (!compareLevel.compare(prevLevel, currLevel)) {
    // System.out.println("Is NOT safe with " + levelToSkip + " skipped; for " +
    // prevLevel + ", " + currLevel);
    // return false;
    // }
    // prevLevel = report.get(index);
    // }
    // System.out.println("Is safe");
    // return true;
    // }

    // private boolean isValidReport(List<Integer> report, CompareLevel
    // compareLevel) {
    // for (int indexToSkip = 0; indexToSkip < report.size(); indexToSkip++) {
    // if (isValidReportWithLevelSkipped(report, compareLevel, indexToSkip)) {
    // System.out.println("Valid with " + indexToSkip + " skipped");
    // return true;
    // }
    // }

    // System.out.println("None worked");
    // return false;
    // }

    private boolean isValidReportWithLevelSkipped(List<Integer> report, CompareLevel compareLevel, int levelToSkip) {
        int prevLevelIndex;
        if (levelToSkip == 0) {
            prevLevelIndex = 1;
        } else {
            prevLevelIndex = 0;
        }
        int prevLevel;
        int currLevel;
        for (int currLevelIndex = prevLevelIndex + 1; currLevelIndex < report.size(); currLevelIndex++) {
            if (currLevelIndex == levelToSkip) {
                continue;
            }
            prevLevel = report.get(prevLevelIndex);
            currLevel = report.get(currLevelIndex);
            if (!compareLevel.compare(prevLevel, currLevel)) {
                return false;
            }
            prevLevelIndex = currLevelIndex;
        }

        return true;
    }

    private boolean isValidReport(List<Integer> report, CompareLevel compareLevel) {
        int prevLevelIndex = 0;
        int prevLevel;
        int currLevel;
        for (int currLevelIndex = 1; currLevelIndex < report.size(); currLevelIndex++) {
            prevLevel = report.get(prevLevelIndex);
            currLevel = report.get(currLevelIndex);
            if (!compareLevel.compare(prevLevel, currLevel)) {
                return isValidReportWithLevelSkipped(report, compareLevel, currLevelIndex) || isValidReportWithLevelSkipped(report, compareLevel, prevLevelIndex);
            }
            prevLevelIndex = currLevelIndex;
        }

        return true;
    }

    public int getValidReports(List<List<Integer>> data) throws Exception {
        CompareLevel ascendingComparator = (prevLevel, currLevel) -> {
            return (Math.abs(currLevel - prevLevel) <= 3 && currLevel > prevLevel);
        };

        CompareLevel descendingComparator = (prevLevel, currLevel) -> {
            return (Math.abs(currLevel - prevLevel) <= 3 && currLevel < prevLevel);
        };

        int validReports = 0;
        FileWriter file = new FileWriter("./opt_output.txt", true);
        try (BufferedWriter writer = new BufferedWriter(file)) {

            for (List<Integer> report : data) {
                System.out.println("Report: " + report.get(0));
                if (isValidReport(report, ascendingComparator) || isValidReport(report, descendingComparator)) {
                    for (Integer level : report) {
                        writer.write(String.valueOf(level) + " ");
                    }
                    writer.write("\n");
                    validReports++;
                } else {
                    writer.write(".\n");
                }
                System.out.println("-----------------------------------------------------------");
            }
        }
        return validReports;
    }

    public static void main(String[] args) throws Exception {
        PartTwo p = new PartTwo();
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
