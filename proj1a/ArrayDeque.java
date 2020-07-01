public class ArrayDeque<Item> {
    private Item[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    /** The starting sizArrayDequee of your array should be 8. start my array at 4*/
    public ArrayDeque() {
        items = (Item[]) new Object[8]; // special way for creating generic Array list;
        size = 0;
        nextFirst = 4;
        nextLast = 5;
    }

    private void resize(int capacity){
        Item[] a = (Item[]) new Object[capacity];
        if (nextLast == 0){
            System.arraycopy(items, 0, a, 0, size);
            items = a;
            nextFirst = capacity - 1;
            nextLast = size;
        } else {
            System.arraycopy(items, nextFirst + 1, a, nextFirst + 1 + capacity - size, size - (nextFirst + 1));
            System.arraycopy(items, 0, a, 0, nextLast);
            items = a;
            nextFirst = nextFirst + capacity - size; // size is still 8, but list length doubled.
        }
    }

    public void addLast(Item x){
        if (size == items.length){
            resize(size * 2);
        }
        items[nextLast % items.length] = x;
        size = size + 1;
        nextLast = (nextLast % items.length + 1)%items.length;
    }

    public void addFirst(Item x){
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

    private int Non0nextLast(int nextLast){
        if (nextLast == 0){
            return size;
        }else{
            return nextLast;
        }
    }

    private int Be0nextFirst(int nextFirst){
        if (nextFirst+1 == size){
            return size;
        }else{
            return nextLast;
        }
    }

    public void printDeque(){
        for (int i=nextFirst+1; i<items.length ; i+=1){
            System.out.print(items[i]);
            System.out.print(' ');
        }
        if (size >= items.length-nextFirst){
            for (int j=0; j<Non0nextLast(nextLast) ; j+=1){
                System.out.print(items[j]);
                System.out.print(' ');
        }
        }
    }
    /** Returns the item from the last of the list. */
    private Item getLast() {
        return items[((nextLast + items.length) - 1) % items.length];
    }

    /** Returns the item from the first of the list. */
    private Item getFirst() {
        return items[(nextFirst + 1) % items.length];
    }

    public Item removeLast(){
        if (size / Double.valueOf(items.length) <= 0.50 && items.length > 16)
            resize(size * 2);

        Item x = getLast();
        items[nextLast - 1] = null;
        size = size - 1;
        nextLast = Non0nextLast(nextLast) - 1;
        return x;
    }

    public Item removeFirst(){
        if (size / Double.valueOf(items.length) <= 0.25 && items.length > 16)
            resize(size * 2);

        Item y = getFirst();
        items[nextFirst + 1] = null;
        size = size - 1;
        nextFirst = (nextFirst + 1) % items.length;
        return y;
    }
    public Item get(int index) {
        if (index < items.length - nextFirst - 1) {
            return items[index + nextFirst + 1];
        } else {
            return items[index - (items.length - nextFirst - 1)];
        }
    }
}
