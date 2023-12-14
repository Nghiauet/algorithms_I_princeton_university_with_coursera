
public class RandomizedQueue<Item> implements Iterable<item> {
    private int n = 0;
    private int capacity = 1;
    private Object[] stack = new Object[1];
    private Random random = new Random();
    private class RandomizedQueueIterator<Item> implements Iterator<Item> {
    private 
    }
    public boolean isEmpty() {
        return n == 0;
    }

    private void resize(int newCapacity) {
        if (newCapacity < n) {
            throw new IllegalArgumentException("capacity must always be greater than n");
        }
        Object[] newArray = new Object[newCapacity];
        for (int i = 0; i < n; i++) {
            newArray[i] = stack[i];
        }
        stack = newArray;
        capacity = newCapacity;
    }

    public void push(Object item) {
        if (n + 1 > capacity) {
            resize((capacity + 1) * 2);
        }
        stack[n++]  = item;
    }

    public Object pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot pop from an empty stack");
        }
        if (n <= capacity / 4) {
            resize(capacity / 2);
        }
        int index = random.nextInt(n);
        Object item = stack[index];
        stack[index] = stack[n - 1];
        stack[n - 1] = null;
        n--;
        return item;
    }

    

    // Define other necessary methods or a test main method if you want to run and test the class.
}