public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    /** The starting sizArrayDequee of your array should be 8. start my array at 4*/
    public ArrayDeque() {
        items = (T[]) new Object[8]; // special way for creating generic Array list;
        size = 0;
        nextFirst = 4;
        nextLast = 5;
    }

    private int plusone(int index) {
        return (index + 1)%items.length;
    }

    private int minusone(int index) {
        return (index + items.length - 1) % items.length;
    }

    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        int space = capacity - size;
        int oldindex = plusone(nextFirst);
        for (int newindex = 0; newindex < size; newindex += 1) {
            a[newindex] = items[oldindex];
            oldindex  = plusone(oldindex);
        }
        items = a;
        nextFirst = capacity - 1;
        nextLast = size;

//        Arraycopy is a dumb way...,
//        if (nextLast == 0){
//            System.arraycopy(items, 0, a, 0, size);
//            items = a;
//            nextFirst = capacity - 1;
//            nextLast = size;
//        } else {
//            System.arraycopy(items, nextFirst + 1, a, nextFirst + 1 + capacity - size, size - (nextFirst+1)%items.length);
//            System.arraycopy(items, 0, a, 0, nextLast);
//            items = a;
//            nextFirst = nextFirst + capacity - size;
//            // size is still 8, but list length doubled.

    }

    public void addLast(T x){
        if (size == items.length){
            resize(size * 2);
        }
        items[nextLast] = x;
        nextLast = plusone(nextLast);
        size = size + 1;
    }

    public void addFirst(T x){
        if (size == items.length){
            resize(size * 2);
        }

        items[nextFirst] = x;
        nextFirst = minusone(nextFirst);
        size = size + 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size(){
        return size;
    }

    public void printDeque() {
        int index = plusone(nextFirst);
        int count = 0;
        while (count < size) {
            System.out.print(items[index]);
            System.out.print(' ');
            index = plusone(index);
            count = count + 1;
        }
    }

    /** Returns the item from the last of the list. */
    private T getLast() {
        return items[minusone(nextLast)];
    }

    /** Returns the item from the first of the list. */
    private T getFirst() {
        return items[plusone(nextFirst)];
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        if (size / (double) items.length <= 0.25 && items.length > 16){
            resize(size * 2);
        }

        T x = getLast();
        nextLast = minusone(nextLast);
        items[nextLast] = null;
        size = size - 1;
        return x;
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        if (size / (double) items.length <= 0.25 && items.length > 16) {
            resize(size * 2);
        }

        T y = getFirst();
        nextFirst = plusone(nextFirst);
        items[nextFirst] = null;
        size = size - 1;
        return y;
    }

    public T get(int index) {
        if (index > size) {
            return null;
        }
        return items[(index + plusone(nextFirst)) % items.length];
    }
}
