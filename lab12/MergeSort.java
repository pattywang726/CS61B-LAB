import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     *
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /** Returns a queue of queues that each contain one item from items. */
    private static <Item extends Comparable> Queue<Queue<Item>>
            makeSingleItemQueues(Queue<Item> items) {
        // Your code here!
        Queue<Queue<Item>> qOfq = new Queue<Queue<Item>>();
        while (!items.isEmpty()) {
            Queue<Item> newQ = new Queue<Item>();
            newQ.enqueue(items.dequeue());
            qOfq.enqueue(newQ);
        }
        return qOfq;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     *
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      A Queue containing all of the q1 and q2 in sorted order, from least to
     *              greatest.
     *
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        // Your code here!
        Queue<Item> newSortedQ = new Queue<Item>();
        while (!q1.isEmpty() || !q2.isEmpty()) {
            newSortedQ.enqueue(getMin(q1, q2));
        }
        return newSortedQ;
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        // Your code here!
        if (items.size() <= 1) {
            return items;
        }
        // splitting -> Use a loop to remove the first half of the items and put them into a new queue.
//        Queue<Queue<Item>> QItems = makeSingleItemQueues(items);
        int N = items.size();
        Queue<Item> left = new Queue<Item>();
        for (int i=0; i < N / 2; i += 1) {
            left.enqueue(items.dequeue());
        }
        Queue<Item> right = items;

        // MergeSort each half
        Queue<Item> sortedL = mergeSort(left);
        Queue<Item> sortedR = mergeSort(right);

        // merging
        return mergeSortedQueues(sortedL, sortedR);
    }

    public static void main(String[] args) {
        // Unsorted Queue of Integer
        ArrayList<Integer> IList = new ArrayList<>();
        for (int i=1; i<1000; i+= 1) {
            IList.add(i);
        }
        Collections.shuffle(IList);
        Queue qInt = new Queue<Integer>();

        for (Integer item : IList) {
            qInt.enqueue(item);
        }
        System.out.println("Original unsorted queue:  " + qInt);
        Queue<Integer> sorted = new Queue<>();
        sorted = MergeSort.mergeSort(qInt);
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
        sortedS = MergeSort.mergeSort(qStr);
        System.out.println("Sorted queue:  " + sortedS);
    }
}
