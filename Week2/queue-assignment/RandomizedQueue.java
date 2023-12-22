import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private int capacity;
    private Item[] stack;

    public RandomizedQueue() {
        size = 0;
        capacity = 1;
        stack = (Item[]) new Object[capacity];
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int current = 0;
        private Item[] indices;

        private RandomizedQueueIterator() {
            indices = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                indices[i] = stack[i];
            }
            StdRandom.shuffle(indices);
        }

        public boolean hasNext() {
            return current < size;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("NoSuchElementException for next");
            }
            Item item = indices[current++];
            return item;
        }
    }

    public int size() {
        return size;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        int index = StdRandom.uniformInt(size);
        return stack[index];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void resize(int newCapacity) {
        if (newCapacity < size) {
            throw new IllegalArgumentException("capacity must always be greater than n");
        }
        Item[] newArray = (Item[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newArray[i] = stack[i];
        }
        stack = newArray;
        capacity = newCapacity;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot enqueue null item");
        }
        if (size + 1 > capacity) {
            resize((capacity + 1) * 2);
        }
        stack[size] = item;
        size = size + 1;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot pop from an empty stack");
        }
        if (size <= capacity / 4) {
            resize(capacity / 2);
        }
        int index = StdRandom.uniformInt(size);
        Item item = stack[index];
        stack[index] = stack[size - 1];
        stack[size - 1] = null;
        size--;
        return item;
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        // queue.enqueue(3);
        // queue.enqueue(4);
        // queue.enqueue(5);
        // queue.enqueue(6);
        // queue.enqueue(7);
        // // iterator
        // Iterator<Integer> iterator = queue.iterator();
        // while (iterator.hasNext()) {
        // StdOut.println(iterator.next());
        // }
    }
}