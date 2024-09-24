public class Sort {

    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && key < arr[j]) {
                arr[j+1] = arr[j];
                j--;
            }
            arr[j+1] = key;
        }
    }

    public static void mergeSort(int[] arr) {
        if (arr.length < 2) {
            return;
        }

        int middle = arr.length / 2;
        
        int[] left = new int[middle];
        int[] right = new int[arr.length - middle];

        for (int i = 0; i < middle; i++) {
            left[i] = arr[i];
        }

        for (int j = middle; j < arr.length; j++) {
            right[j-middle] = arr[j];
        }

        mergeSort(left);
        mergeSort(right);
        merge(left, right, arr);
    }

    public static void merge(int[] left, int[] right, int[] arr) {
        int i = 0; int j = 0; int k = 0;
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                arr[k] = left[i];
                i++;
            }
            else {
                arr[k] = right[j];
                j++;
            }
            k++;
        }

        while (i < left.length) {
            arr[k] = left[i];
            i++;
            k++;
        }

        while (j < right.length) {
            arr[k] = right[j];
            j++;
            k++;
        }
    }

    public static int[] countingSort(int[] arr, int max) {
        int[] count = new int[max+1];
        int[] output = new int[arr.length];

        for (int i = 0; i < max + 1; i++) {
            count[i] = 0;
        }

        for (int i = 0; i < arr.length; i++) {
            count[arr[i]] += 1;
        }

        for (int i = 1; i < count.length; i++) {
            count[i] += count[i-1];
        }

        for (int i = arr.length - 1; i > -1; i--) {
            count[arr[i]]--;
            output[count[arr[i]]] = arr[i];
        }
        return output;
    }

}