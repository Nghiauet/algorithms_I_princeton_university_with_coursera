import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private MagicNode<Item> first;
    private MagicNode<Item> last;
    private int size;
    private class MagicNode<Item> {
        Item data;
        MagicNode<Item> next;
        MagicNode<Item> prev;
    }
    private class DequeIterator<item> implements Iterator<Item> {
        private MagicNode<Item> current;
        
        private DequeIterator(MagicNode<Item> item) {
            current = item;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("NoSuchElementException for next");
            }
            Item data = current.data;
            current = current.next;
            return data;
        }
    }
    public Deque() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item data) {
        MagicNode<Item> temp = new MagicNode<>();
        temp.data = data;
        if (isEmpty()) {
            first = last = temp;
        } else {
            temp.next = first;
            first.prev = temp;
            first = temp;
        }
        size++;
    }

    public void addLast(Item data) {
        MagicNode<Item> temp = new MagicNode<>();
        temp.data = data;
        if (isEmpty()) {
            last = first = temp;
        } else {
            last.next = temp;
            temp.prev = last;
            last = temp;
        }
        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("NoSuchElementException for removeFirst");
        }
        Item data = first.data;
        if (first.next == null) {
            first = last = null;
        } else {
            first = first.next;
            first.prev = null;
        }
        size--;
        System.out.println("removeFirst pop: " + data);
        return data;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("NoSuchElementException for removeLast");
        }
        Item data = last.data;
        if (last.prev == null) {
            last = first = null;
        } else {
            last = last.prev;
            last.next = null;
        }
        size--;
        System.out.println("removeLast pop: " + data);
        return data;
    }
    public Iterator<Item> iterator() {
        return new DequeIterator<>(first);
    }
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(4);
        deque.addFirst(5);
        deque.addFirst(6);
        deque.addLast(3);
        deque.addLast(2);
        deque.addLast(1);
        // deque.removeFirst();
        // deque.removeFirst();
        // deque.removeFirst();
        // deque.removeFirst();
        // deque.removeFirst();
        // deque.removeFirst();
        // deque.removeFirst();
        // deque.removeFirst();
        // iterator 
        Iterator<Integer> iterator = deque.iterator();
        while (iterator.hasNext()) {
            StdOut.println(iterator.next());
        }
    }
}