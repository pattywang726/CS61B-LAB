public class LinkedListDeque<T> implements Deque<T>{
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
    @Override
    public void addFirst(T x) {
        if (isEmpty()){
            sentinel.next = new StuffNode(sentinel, x, sentinel);  // this is wired
            sentinel.prev = sentinel.next;
        }else {
            sentinel.next = new StuffNode(sentinel, x, sentinel.next);
            sentinel.next.next.prev = sentinel.next;
        }
        size = size + 1;
    }

    @Override
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
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
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

    @Override
    public T removeFirst() {
        if (isEmpty()){
            return null;
        }
        T first = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;

        size = size - 1;
        return first;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T last = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;

        size = size - 1;
        return last;
    }

    @Override
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
        if (index == 0) {
            return curr.item;
        }
        return getRecursive(index - 1, curr.next);
    }

    @Override
    public T getRecursive(int index){
        if (index >= size()){
            return null;
        }
        return getRecursive(index, sentinel.next);
    }
}
