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

    private void resize(int capacity){
        T[] a = (T[]) new Object[capacity];
        if (nextLast == 0){
            System.arraycopy(items, 0, a, 0, size);
            items = a;
            nextFirst = capacity - 1;
            nextLast = size;
        } else {
            System.arraycopy(items, nextFirst + 1, a, nextFirst + 1 + capacity - size, size - (nextFirst+1)%items.length);
            System.arraycopy(items, 0, a, 0, nextLast);
            items = a;
            nextFirst = nextFirst + capacity - size; // size is still 8, but list length doubled.
        }
    }

    public void addLast(T x){
        if (size == items.length){
            resize(size * 2);
        }
        items[nextLast % items.length] = x;
        size = size + 1;
        nextLast = (nextLast % items.length + 1)%items.length;
    }

    public void addFirst(T x){
        if (size == items.length){
            resize(size * 2);
        }

        items[(nextFirst+items.length) % items.length] = x;
        size = size + 1;
        nextFirst = (nextFirst + items.length) % items.length - 1;
    }

    public boolean isEmpty(){
        if (size==0){
            return true;
        } else {
            return false;
        }
    }

    public int size(){
        return size;
    }

    private int non0(int Last){
        if (Last == 0){
            return size;
        }else{
            return Last;
        }
    }

//    private int be0(int First) {
//        if (First+1 == size){
//            return 0;
//        }else{
//            return First + 1;
//        }
//    }

    public void printDeque(){
        for (int i=nextFirst+1; i<items.length ; i+=1) {
            System.out.print(items[i]);
            System.out.print(' ');
        }
        if (size >= items.length-nextFirst){
            for (int j=0; j<non0(nextLast) ; j+=1){
                System.out.print(items[j]);
                System.out.print(' ');
            }
        }
    }
    /** Returns the item from the last of the list. */
    private T getLast() {
        return items[((nextLast + items.length) - 1) % items.length];
    }

    /** Returns the item from the first of the list. */
    private T getFirst() {
        return items[(nextFirst + 1) % items.length];
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        if (size / (double) items.length <= 0.50 && items.length > 16){
            resize(size * 2);
        }

        T x = getLast();
        items[non0(nextLast) - 1] = null;
        size = size - 1;
        nextLast = non0(nextLast) - 1;
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
        items[(nextFirst+1)%items.length] = null;
        size = size - 1;
        nextFirst = (nextFirst+1)%items.length;
        return y;
    }
    public T get(int index) {
        if (index < items.length - nextFirst - 1) {
            return items[index + nextFirst + 1];
        } else {
            return items[index - (items.length - nextFirst - 1)];
        }
    }
}
