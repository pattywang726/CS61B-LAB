import edu.princeton.cs.algs4.Queue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // Done: Implement LSD Sort
        int len = asciis.length;
        String[] copy = new String[asciis.length];
        int numberD = 0;
        for (int i=0; i < asciis.length; i += 1) {
            if (numberD < asciis[i].length()) {
                numberD = asciis[i].length();
            }
            copy[i] = asciis[i];
        }

        // pad on the left with empty values
        for (int i=0; i < copy.length; i += 1) {
            if (copy[i].length() < numberD) {
                int toBe = numberD - copy[i].length();
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < toBe; j += 1) {
                    sb.append((char)0);
                }
                copy[i] = copy[i] + sb.toString();
            }
        }
        for (int d=numberD-1; d >= 0; d -= 1) {
            sortHelperLSD(copy, d);
        }
        return copy;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort

        // sorting the char list of charCol;
        // create 256 buckets
        ArrayList<Queue<String>> buckets = new ArrayList<Queue<String>>(256);
        for (int i = 0; i < 256; ++i) {
            buckets.add(new Queue<>());
        }
        // begin sorting, sorting all the String to the buckets according to the order of char.
        for (int i=0; i < asciis.length; i +=1 ) {
            buckets.get((int) asciis[i].charAt(index)).enqueue(asciis[i]);
        }
        // get all String out from the buckets
        int k = 0;
        for (int j=0 ; j < 256; j += 1) {
            if (buckets.get(j).isEmpty()) {
                continue;
            }
            while (!buckets.get(j).isEmpty()) {
                asciis[k] = buckets.get(j).dequeue();
                k += 1;
            }
        }
        return;
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }

//    public static void main (String[] args) {
//        // Unsorted Queue of String
//        String[] SList = new String[] {"ide","quiz","element", "geeks", "practice", "apple"};
//
//        System.out.println("Original unsorted queue:  " + Arrays.toString(SList));
//        String [] sortedS = RadixSort.sort(SList);
//        System.out.println("Sorted queue:  " + Arrays.toString(sortedS));
//    }
}

