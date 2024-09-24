import java.util.ArrayList;
import java.util.Random;

public class LinearSearchTest {
    public static long[] test (ArrayList<int[]> arr) {
        long[] res = new long[arr.size()];
        for (int i = 0; i < arr.size(); i++) {
            int[] temp = arr.get(i);
            long avgSearch = 0;
            for (int j = 0; j < 1_000; j++) {
                Random random = new Random();
                int randomIndex = random.nextInt(temp.length);
                int searchVal = temp[randomIndex];
                long start = System.nanoTime();
                int index = Search.linearSearch(temp, searchVal);
                long end = System.nanoTime();
                long elapsedTime = end - start;
                avgSearch += elapsedTime;
            }
            avgSearch /= 1_000;
            res[i] = avgSearch;
        }
        return res;
    }
}
