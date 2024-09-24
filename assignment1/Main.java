import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main (String[] args) throws IOException {
        int[] res = FileIO.readIntegersFromFile(args[0]);
        ArrayList<int[]> partedResRandom1 = Utils.part(res);

        long[] insertionSortRandomTimes = InsertionSortTest.test(partedResRandom1);
        Utils.print(insertionSortRandomTimes, "InsertionSort", "milliseconds", "random data");
        long[] mergeSortRandomTimes = MergeSortTest.test(partedResRandom1);
        Utils.print(mergeSortRandomTimes, "MergeSort", "milliseconds", "random data");
        long[] countingSortRandomTimes = CountingSortTest.test(partedResRandom1);
        Utils.print(countingSortRandomTimes, "CountingSort", "milliseconds", "random data");
        long[] linearSearchRandomTimes = LinearSearchTest.test(partedResRandom1);

        Plot.graph(mergeSortRandomTimes, insertionSortRandomTimes, countingSortRandomTimes, "RandomData", "MergeSort", "InsertionSort", "CountingSort", "Time in milliseconds");

        Utils.sortArrayListOfArrays(partedResRandom1);
        long[] insertionSortSortedTimes = InsertionSortTest.test(partedResRandom1);
        Utils.print(insertionSortSortedTimes, "InsertionSort", "milliseconds", "sorted data");
        long[] mergeSortSortedTimes = MergeSortTest.test(partedResRandom1);
        Utils.print(mergeSortSortedTimes, "MergeSort", "milliseconds", "sorted data");
        long[] countingSortSortedTimes = CountingSortTest.test(partedResRandom1);
        Utils.print(countingSortSortedTimes, "CountingSort", "milliseconds", "sorted data");

        Plot.graph(mergeSortSortedTimes, insertionSortSortedTimes, countingSortSortedTimes, "SortedData", "MergeSort", "InsertionSort", "CountingSort", "Time in milliseconds");

        Utils.reverse(partedResRandom1);
        long[] insertionSortReverseTimes = InsertionSortTest.test(partedResRandom1);
        Utils.print(insertionSortReverseTimes, "InsertionSort", "milliseconds", "reverse sorted data");
        long[] mergeSortReverseTimes = MergeSortTest.test(partedResRandom1);
        Utils.print(mergeSortReverseTimes, "MergeSort", "milliseconds", "reverse sorted data");
        long[] countingSortReverseTimes = CountingSortTest.test(partedResRandom1);
        Utils.print(countingSortReverseTimes, "CountingSort", "milliseconds", "reverse sorted data");

        Plot.graph(mergeSortReverseTimes, insertionSortReverseTimes, countingSortReverseTimes, "ReverseData", "MergeSort", "InsertionSort", "CountingSort", "Time in milliseconds");

        Utils.print(linearSearchRandomTimes, "LinearSearch", "nanoseconds", "random data");
        Utils.reverse(partedResRandom1);
        long[] linearSearchSortedTimes = LinearSearchTest.test(partedResRandom1);
        Utils.print(linearSearchSortedTimes, "LinearSearch", "nanoseconds", "sorted data");
        long[] binarySearchSortedTimes = BinarySearchTest.test(partedResRandom1);
        Utils.print(binarySearchSortedTimes, "BinarySearch", "nanoseconds", "sorted data");

        Plot.graph(linearSearchRandomTimes, linearSearchSortedTimes, binarySearchSortedTimes, "SearchTimes", "LinearSearchRandom", "LinearSearchSorted", "BinarySearch", "Time in nanoseconds");
    }
}