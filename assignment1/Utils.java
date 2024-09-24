import java.lang.reflect.Array;
import java.util.ArrayList;

public class Utils {
    static int[] partitionValues = {500, 1_000, 2_000, 4_000, 8_000, 16_000, 32_000, 64_000, 128_000, 250_000};

    public static ArrayList<int[]> part (int[] arr) {
        ArrayList<int[]> res = new ArrayList<>();

        for (int i = 0; i < partitionValues.length; i++) {
            int[] temp = new int[partitionValues[i]];
            for (int j = 0; j < partitionValues[i]; j++) {
                temp[j] = arr[j];
            }
            res.add(temp);
        }
        return res;
    }

    public static void reverse (ArrayList<int[]> arr) {
        for (int i = 0; i < arr.size(); i++) {
            for (int j = 0; j < (arr.get(i).length) / 2; j++) {
                int temp = arr.get(i)[j];
                arr.get(i)[j] = arr.get(i)[arr.get(i).length-j-1];
                arr.get(i)[arr.get(i).length-j-1] = temp;
            }
        }
    }

    public static void sortArrayListOfArrays (ArrayList<int[]> arr) {
        for (int i = 0; i < arr.size(); i++) {
            Sort.mergeSort(arr.get(i));
        }
    }

    public static int findMax (int[] arr) {
        //assume there exists at least one item in the arr
        int res = arr[0];

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] >= res) {
                res = arr[i];
            }
        }
        return res;
    }

    public static void print (long[] timeValues, String sortSearch, String secType, String randSortRev) {
        for (int i = 0; i < 10; i++) {
            System.out.println("Time taken for " + sortSearch + " for size " + partitionValues[i] + " on " + randSortRev + " is " + timeValues[i] + " " + secType);
        }
    }
}