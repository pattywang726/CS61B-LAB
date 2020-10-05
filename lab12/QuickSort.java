import edu.princeton.cs.algs4.Queue;

import java.util.ArrayList;
import java.util.Collections;

public class QuickSort {
    /**
     * Returns a new queue that contains the given queues catenated together.
     *
     * The items in q2 will be catenated after all of the items in q1.
     */
    private static <Item extends Comparable> Queue<Item> catenate(Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> catenated = new Queue<Item>();
        for (Item item : q1) {
            catenated.enqueue(item);
        }
        for (Item item: q2) {
            catenated.enqueue(item);
        }
        return catenated;
    }

    /** Returns a random item from the given queue. */
    private static <Item extends Comparable> Item getRandomItem(Queue<Item> items) {
        int pivotIndex = (int) (Math.random() * items.size());
        Item pivot = null;
        // Walk through the queue to find the item at the given index.
        for (Item item : items) {
            if (pivotIndex == 0) {
                pivot = item;
                break;
            }
            pivotIndex--;
        }
        return pivot;
    }

    /**
     * Partitions the given unsorted queue by pivoting on the given item.
     *
     * @param unsorted  A Queue of unsorted items
     * @param pivot     The item to pivot on
     * @param less      An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are less than the given pivot.
     * @param equal     An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are equal to the given pivot.
     * @param greater   An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are greater than the given pivot.
     */
    private static <Item extends Comparable> void partition(
            Queue<Item> unsorted, Item pivot,
            Queue<Item> less, Queue<Item> equal, Queue<Item> greater) {
        // Your code here!
        for (Item item : unsorted) {
            if (item.equals(pivot)) {
                equal.enqueue(item);
            } else if (item.compareTo(pivot) < 0) {
                less.enqueue(item);
            } else {
                greater.enqueue(item);
            }
        }
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> quickSort(
            Queue<Item> items) {
        // Your code here!
        Queue<Item> less = new Queue<>();
        Queue<Item> greater = new Queue<>();
        Queue<Item> equal = new Queue<>();
        Queue<Item> left;
        Queue<Item> right;
        Item pivot = getRandomItem(items);
        partition(items, pivot, less, equal, greater);

        // for left part, catenate the pivot to the left.
        if (less.size() <= 1) {
            left = catenate(less, equal);
        } else {
            left = quickSort(less);
            left = catenate(left, equal);
        }
        if (greater.size() <= 1) {
            right = greater;
        } else {
            right = quickSort(greater);
        }
        return catenate(left, right);
    }

    public static void main(String[] args) {
        // Unsorted Queue of Integer
        ArrayList<Integer> IList = new ArrayList<>();
            for (int i=1; i<10; i+= 1) {
                if (i % 2.0 == 0) {
                    IList.add(1);
                } else {
                    IList.add(i);
                }
        }
        Collections.shuffle(IList);
        Queue qInt = new Queue<Integer>();

            for (Integer item : IList) {
            qInt.enqueue(item);
        }
            System.out.println("Original unsorted queue:  " + qInt);
        Queue<Integer> sorted = new Queue<>();
        sorted = QuickSort.quickSort(qInt);
            System.out.println("Sorted queue:  " + sorted);

        // Unsorted Queue of String
        ArrayList<String> SList = new ArrayList<String>();
            SList.add("ide");
            SList.add("quiz");
            SList.add("geeksforgeeks");
            SList.add("quiz");
            SList.add("practice");
            SList.add("qa");

            Collections.shuffle(SList);
        Queue qStr = new Queue<String>();
            for (String item : SList) {
            qStr.enqueue(item);
        }
            System.out.println("Original unsorted queue:  " + qStr);
        Queue<String> sortedS = new Queue<>();
        sortedS = QuickSort.quickSort(qStr);
            System.out.println("Sorted queue:  " + sortedS);
    }
}
