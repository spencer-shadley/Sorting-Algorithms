import java.util.Arrays;

/**
 * Created by Spencer on 10/11/2015.
 *
 * Implementations of various sorting algorithms
 * BubbleSort
 * SelectionSort
 * InsertionSort
 *
 * Assuming no duplicates for simplicity
 *
 * All listed times and space big O's are in reference to worst case
 */
public class Sort {

    public static void main(String[] args) {

        final boolean debug = false;

        // make test cases
        int[] inOrder   = {1,2,3,4,5,6,7,8,9};
        int[] backwards = {9,8,7,6,5,4,3,2,1};
        int[] random    = {4,1,7,3,9,5,2,6,8};
        int[][] allTests = {/*inOrder, backwards,*/ random};

        // run all test cases
        for(int[] testCase : allTests) {

            if(debug) {
                System.out.println("test case:");
                System.out.println(Arrays.toString(testCase));
                System.out.println("selected:");
            }

            int[] selected  = selectSort(testCase.clone());
            int[] bubbled   = bubbleSort(testCase.clone());
            int[] inserted  = insertionSort(testCase.clone());
            int[] merged    = mergeSort(testCase.clone());

            if(debug) {
                System.out.println(Arrays.toString(selected));
                System.out.println();
            }

            assert Arrays.equals(inOrder, bubbled);
            assert Arrays.equals(inOrder, selected);
            assert Arrays.equals(inOrder, inserted);
            assert Arrays.equals(inOrder, merged);
        }
    }

    /**
     * @param unsorted array to sort
     * @return sorted array
     */
    // Time: O(N^2)
    // Space: O(1)
    static int[] bubbleSort(int[] unsorted) {
        boolean swapped = true;
        while(swapped) {
            swapped = false;
            for (int i = 0; i < unsorted.length - 1; i++) {
                if (unsorted[i] > unsorted[i + 1]) {

                    // swap i and i+1
                    unsorted[i] ^= unsorted[i + 1];
                    unsorted[i + 1] ^= unsorted[i];
                    unsorted[i] ^= unsorted[i + 1];
                    swapped = true;

                }
            }
        }
        return unsorted;
    }

    /**
     * @param unsorted array to sort
     * @return sorted array
     */
    // find minimum value of current list and swap that with the beginning
    // Time: O(N^2)
    // Space: O(1)
    static int[] selectSort(int[] unsorted) {

        for(int i = 0; i < unsorted.length; i++) {

           int minIndex = getMinIndex(i, unsorted);

            // swap i and min
            int temp = unsorted[i];
            unsorted[i] = unsorted[minIndex];
            unsorted[minIndex] = temp;
        }
        return unsorted;
    }

    /**
     * @param unsorted array to sort
     * @return sorted array
     */
    // go through array, once an out of order element is found swap it down until if fits in place
    // Time: O(N^2)
    // Space: O(1)
    static int[] insertionSort(int[] unsorted) {
        for(int i = 0; i < unsorted.length; i++) {
            int temp = unsorted[i];
            int j;
            for(j = i-1; j >= 0 && temp < unsorted[j]; --j)
                unsorted[j+1] = unsorted[j];
            unsorted[j+1] = temp;
        }
        return unsorted;
    }

    static int[] mergeSortAlt(int[] unsorted) {
        split(0, unsorted.length-1, unsorted);
        return unsorted;
    }

    static void split(int begI, int endI, int[] unsorted) {

        if(begI < endI) {
            int middle = (begI + endI)/2;
            split(begI, middle, unsorted); // left
            split(middle + 1, endI, unsorted); // right
            merge(begI, endI, unsorted);
        }
    }

    static int[] merge(int l, int r, int[] unsorted) {
        System.out.println("merging");
        int[] temp = new int[unsorted.length];

        int leftLeft = l;
        int leftRight = r/2-1;
        int rightLeft = r/2;

        while(l != leftRight) {
            System.out.println(l);
            if(unsorted[leftLeft] < unsorted[rightLeft])
                temp[l++] = unsorted[leftLeft++];
            else
                temp[l++] = unsorted[rightLeft++];
        }
        return temp;
    }

    /**
     * @param unsorted array to sort
     * @return sorted array
     * based on https://www.cs.cmu.edu/~adamchik/15-121/lectures/Sorting%20Algorithms/code/MergeSort.java
     */
    static int[] mergeSort(int[] unsorted) {
        int[] temp = new int[unsorted.length];
        mergeSort(unsorted, temp, 0, unsorted.length-1);
        return unsorted;
    }

    static void mergeSort(int[] unsorted, int[] temp, int left, int right) {
        if(left < right) {
            int center = (left+right)/2;
            mergeSort(unsorted, temp, left, center);
            mergeSort(unsorted, temp, center+1, right);
            merge(unsorted, temp, left, center+1, right);
        }
    }

    static int[] merge(int[] unsorted, int[] temp, int left, int right, int rightEnd) {
        int leftEnd = right - 1;
        int k = left;
        int num = rightEnd - left + 1;
        while(left <= leftEnd && right <= rightEnd)
            if(unsorted[left] <= unsorted[right])
                temp[k++] = unsorted[left++];
            else
                temp[k++] = unsorted[right++];
        while(left <= leftEnd)
            temp[k++] = unsorted[left++];
        while(right <= rightEnd)
            temp[k++] = unsorted[right++];

        for(int i = 0; i < num; i++, rightEnd--)
            unsorted[rightEnd] = temp[rightEnd];
        return unsorted;
    }

    /**
     * @param startIndex subsets the array (begin here)
     * @param arr array to find minimum within
     * @return the minimum element
     */
    // get the minimum index of the sub-array (startIndex to the end of arr)
    static int getMinIndex(int startIndex, int[] arr) {
        int minIndex = startIndex;
        for(int i = startIndex+1; i < arr.length; i++)
            if(arr[i] < arr[minIndex])
                minIndex = i;
        return minIndex;
    }


}
