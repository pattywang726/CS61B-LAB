package synthesizer;
import java.util.Iterator;

//TODO: Make sure to make this class and all of its methods public

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        this.capacity = capacity;
        rb = (T[]) new Object[capacity];  // generic AList rule! casting?
        first = 0;
        last = 0;
        fillCount = 0;

        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring Buffer Overflow");
        }
        rb[last] = x;
        last = (last + 1) % capacity;
        fillCount += 1;
        // TODO: Enqueue the item. Don't forget to increase fillCount and update last.
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and update 
        if (isEmpty()) {
            throw new RuntimeException("Ring Buffer Underflow");
        }
        T firstItem = rb[first];
        rb[first] = null;
        first = (first + 1) % capacity;
        fillCount -= 1;
        return firstItem;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        // TODO: Return the first item. None of your instance variables should change.
        T oldestItem = rb[first];
        return oldestItem;
    }

    public Iterator<T> iterator() {
        // TODO: When you get to part 5, implement the needed code to support iteration.
        return new KeyInterator();
    }

    public class KeyInterator implements Iterator<T>{
        private int ptr;
        private int scan;
        public KeyInterator(){
            ptr = first;
            scan = 1;
        }

        @Override
        public boolean hasNext() {
            return (scan != fillCount);
        }

        @Override
        public T next() {
            T returnItem = rb[ptr];
            ptr = ptr + 1;
            scan = scan + 1;
            return returnItem;
        }
    }
}
