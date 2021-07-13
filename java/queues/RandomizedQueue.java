
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;
public class RandomizedQueue<Item> implements Iterable<Item> {

	private int size = 0;
	private Item queue[];
	
    // construct an empty randomized queue
    public RandomizedQueue() {
    	queue = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
    	return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
    	return size;
    }

    // add the item
    public void enqueue(Item item) {
    	if (item == null) throw new IllegalArgumentException();
    	if (size == queue.length)
    		resize(queue.length*2);
    	queue[size++] = item;

    }

    // remove and return a random item
    public Item dequeue() {
    	if (size == 0) throw new NoSuchElementException();
    	int i =  StdRandom.uniform(size);
    	Item item = queue[i];
    	queue[i] = queue[--size];
    	if (size == queue.length/2) resize(size);

    	
    	return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
    	int i =  StdRandom.uniform(size);
    	return queue[i];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
    	return new arrayIterator();
    }
    
    private void resize (int n) {
    	Item[] new_queue = (Item[]) new Object[n+1];
    	for (int i = 0; i < size; i++) {
    		new_queue[i] = queue[i]; 
    	}
    	this.queue = new_queue;
    }
    private class arrayIterator implements Iterator<Item>{
    	private int index;

		@Override
		public boolean hasNext() {
			return index != size;
		}

		@Override
		public Item next() {
			if(!hasNext()) throw new NoSuchElementException();
			Item item = queue[index++];
			return item;
		}
    	
    }
    // unit testing (required)
    
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }
        System.out.println(queue.size());
        for (Integer i : queue) {
            System.out.println(i);
        }
        System.out.println("-------------");

        System.out.println("sample:" + queue.sample());
        System.out.println("dequeue");
        while (!queue.isEmpty()) System.out.println(queue.dequeue());
        System.out.println(queue.size());
    }
    

}
