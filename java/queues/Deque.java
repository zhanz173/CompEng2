import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	private int size;
	private Node first,last;
	
	private class Node{
		Node next;
		Node prev;
		Item item;
	}

    // construct an empty deque
    public Deque() {
    	first = null;
    	size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
    	return size == 0;
    }

    // return the number of items on the deque
    public int size() {
    	return this.size;
    }

    // add the item to the front
    public void addFirst(Item item) {
    	if (item == null) throw new IllegalArgumentException();
    	size++;
    	if (size <= 1) {
    		first = new Node();
    		last = first;
    		first.item = item;
    		return;
    	}
    	Node old_first = first;
    	first = new Node();
    	first.item = item;
    	first.next = old_first;
    	old_first.prev = first;
    }

    // add the item to the back
    public void addLast(Item item) {
    	if (item == null) throw new IllegalArgumentException();
    	Node old_last = last;
    	last = new Node();
    	size++;
    	
    	if (first == null) {
    		first = last;
    	}
    	else {
    		old_last.next = last;
    	}
    	last.prev = old_last;
    	last.item = item;

    }

    // remove and return the item from the front
    public Item removeFirst() {
    	if (first == null) throw new NoSuchElementException();
    	size--;
    	Item item = first.item;
    	first = first.next;
    	if (first == null) {
    		last = null;
    		return item;
    	}
    	first.prev = null;
    	return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
    	if (last == null) throw new NoSuchElementException();
		Item item = last.item;
    	if(size == 1) {
    		first = null;
    		last = null;

    	}
    	else {
    		last = last.prev;
    		last.next = null;
    	}
		size--;
		return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator(){
    	//System.out.println("New iterator created");
    	return new ListIterator();
    }
    
    private class ListIterator implements Iterator<Item>{
    	private Node cur = first;
		@Override
		public boolean hasNext() {
			return cur != null;
		}

		@Override
		public Item next() {
			if(cur == null) throw new NoSuchElementException();
			Item val = cur.item;
			cur = cur.next;
			return val;
		}
    }

    // unit testing (required)
    
    public static void main(String[] args) {
    	Deque<Integer> dq = new Deque<Integer>();
    	dq.addLast(4);
    	dq.addLast(5);
    	dq.addLast(6);
    	dq.addLast(7);


    	Iterator<Integer> iter1 = dq.iterator();
    	Iterator<Integer> iter2 = dq.iterator();
    	iter1.remove();
    	
    	while (iter1.hasNext()) {
    	System.out.println(iter1.next());}

    }
    

}
