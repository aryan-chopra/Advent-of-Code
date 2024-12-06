import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class PartOne_Two {

    public int frequencyOf(int num, List<Integer> array) {
        int start = 0;
        int end = array.size() - 1;
        int mid = 0;
        boolean found = false;

        while (start <= end) {
            mid = start + (end - start) / 2;

            if (array.get(mid) == num) {
                found = true;
                break;
            } else if (array.get(mid) < num) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        if (!found) {
            return 0;
        }

        int resultIndex = mid;
        int frequency = 1;
        while (mid > 0 && array.get(mid - 1) == num) {
            mid--;
            frequency++;
        }

        while (resultIndex + 1 < array.size() && array.get(resultIndex + 1) == num) {
            resultIndex++;
            frequency++;
        }

        return frequency;
    }

    public Map<Integer, Integer> mapFrequency(List<Integer> array) {
        @SuppressWarnings("Convert2Diamond")
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();

        for (Integer e : array) {
            map.put(e, map.getOrDefault(e, 0) + 1);
        }

        return map;
    }

    public static void main(String[] args) throws Exception {
        PartOne_Two p = new PartOne_Two();

        List<Integer> arrayOne = new ArrayList<>();
        List<Integer> arrayTwo = new ArrayList<>();

        FileReader file = new FileReader("./input.txt");
        try (BufferedReader reader = new BufferedReader(file)) {
            String line;

            while ((line = reader.readLine()) != null) {
                String tokens[] = line.split("   ");
                arrayOne.add(Integer.valueOf(tokens[0]));
                arrayTwo.add(Integer.valueOf(tokens[1]));
            }
        } catch (Exception e) {
            throw e;
        }

        // Collections.sort(arrayTwo);

        @SuppressWarnings("Convert2Diamond")
        Map<Integer, Integer> similarityScore = new HashMap<Integer, Integer>();
        @SuppressWarnings("Convert2Diamond")
        // Map<Integer, Integer> frequencyMap = new HashMap<Integer, Integer>();

        // for (Integer e : arrayOne) {
        // if (frequencyMap.containsKey(e)) {
        // similarityScore.put(e, similarityScore.get(e) + frequencyMap.get(e));
        // }

        // else {
        // frequencyMap.put(e, e * p.frequencyOf(e, arrayTwo));
        // similarityScore.put(e, frequencyMap.get(e));
        // }

        // System.out.println(e + " is " + similarityScore.get(e));
        // }

        Map<Integer, Integer> frequecyMap = p.mapFrequency(arrayTwo);
        for (Integer e : arrayOne) {
            if (similarityScore.containsKey(e)) {
                similarityScore.put(e, similarityScore.get(e) + (e * frequecyMap.getOrDefault(e, 0)));
            } else {
                similarityScore.put(e, e * frequecyMap.getOrDefault(e, 0));
            }
        }

        AtomicInteger sumOfFrequencies = new AtomicInteger(0);

        similarityScore.forEach((key, val) -> sumOfFrequencies.set(sumOfFrequencies.get() + val));

        System.out.println(sumOfFrequencies.get());
    }
}