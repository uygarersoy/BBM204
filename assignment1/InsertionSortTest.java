import java.util.ArrayList;

public class InsertionSortTest {
    public static long[] test (ArrayList<int[]> arr) {
        long[] res = new long[10];
        for (int i = 0; i < arr.size(); i++) {
            int[] original = arr.get(i).clone();
            long average = 0;
            for (int j = 0; j < 10; j++) {
                int[] temp = original.clone();
                long start = System.nanoTime();
                Sort.insertionSort(temp);
                long end = System.nanoTime();
                long elapsedTime = end - start;
                average += elapsedTime;
            }
            long milli = average / 10_000_000;
            res[i] = milli;
        }
        return res;
    }
}
