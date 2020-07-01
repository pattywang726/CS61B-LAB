public class LinkedListDeque<T> {
    private class StuffNode {
        private T item;
        private StuffNode prev;
        private StuffNode next;

        public StuffNode(StuffNode m, T i, StuffNode n) {
            prev = m;
            item = i;
            next = n;
//            System.out.println(size);
        }
    }

    /* The first item (if it exists) is at sentinel.next. */
    private StuffNode sentinel;
    private int size;
//    public T[] list_item;

    /** Creates an empty SLList. */
    public LinkedListDeque() {
        sentinel = new StuffNode(null,null,null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

//    public LinkedListDeque(T x) {
//        sentinel = new StuffNode(null,null,null);
//        sentinel.next = new StuffNode(null,x,null);
//        sentinel.next.next = sentinel;
//
//        sentinel.prev = sentinel.next;
//        sentinel.next.prev = sentinel;
//
//        size = 1;
//    }

    /** Adds x to the front of the list. */
    public void addFirst(T x) {
        if (isEmpty()){
            sentinel.next = new StuffNode(sentinel, x, sentinel);
            sentinel.prev = sentinel.next;
        }else {
            sentinel.next = new StuffNode(sentinel, x, sentinel.next);
            sentinel.next.next.prev = sentinel.next.next;
        }
        size = size + 1;
    }

    public void addLast(T x) {
        if (isEmpty()){
            sentinel.prev = new StuffNode(sentinel.prev, x, sentinel);
            sentinel.next = sentinel.prev;
        }else {
            sentinel.prev = new StuffNode(sentinel.prev, x, sentinel);
            sentinel.prev.prev.next = sentinel.prev;
        }
        size = size + 1;
    }

    // Returns true if deque is empty, false otherwise. //
    public boolean isEmpty() {
        if (size == 0){
            return true;
        } else{
            return false;
        }
    }

    public int size(){
        return size;
    }

    public void printDeque() {
        if (isEmpty()) {
            System.out.print(' ');
        }

        StuffNode p = sentinel.next;

        for(int i=0; i<size; i+=1){
            System.out.print(p.item);
            System.out.print(' ');
            p = p.next;
        }
    }

    public T removeFirst(){
        if (isEmpty()){
            return null;
        }
        T first = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size = size - 1;

        return first;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T last = sentinel.next.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;

        size = size - 1;
        return last;
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        }

        StuffNode p = sentinel.next;

        for (int i=0; i<size; i+=1) {
            if (i == index){
                break;
            }
            p = p.next;
        }
        return p.item;
    }

    /**
     * Same as get, but uses recursion
     * First, need a private helper method
     */
    private T getRecursive(int index, StuffNode curr){
        if (index == (size()-1)) {
            return curr.item;
        }
        return getRecursive(index+1, curr.prev);
    }

    public T getRecursive(int index){
        if (index >= size()){
            return null;
        }
        return getRecursive(index, sentinel.prev);
    }
}
