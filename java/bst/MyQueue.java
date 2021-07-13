import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyQueue<Item> implements Iterable<Item> {
	private int size = 0;
	private int front = 0;
	private int end = 0;
	private Item[] data;
	
	public MyQueue() {
		data = (Item[]) new Object[1];
	}
	
	void enqueue(Item item) {
		if (item == null) throw new IllegalArgumentException();
		if (end == data.length)
			resize(size*2);
		data[end++] = item;
		size++;
	}
	
	Item dequeue() {
		if (size == 0) throw new NoSuchElementException();
		size--;
		return data[front++];
	}
	
	Item peek(int index) {
		return data[index];
	}
	
	Item pop() {
		if (size == 0) throw new NoSuchElementException();
		size--;
		return data[end-- -1];
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public int size() {
		return this.size;
	}
	
	void resize(int n) {
		Item[] new_data = (Item[]) new Object[n+1];
		for (int i = front, j = 0; i < end; i++, j++) {
			new_data[j] = data[i];
		}
		
		front = 0;
		end = front + size;
		this.data = new_data;
	}
	

	@Override
	public Iterator<Item> iterator() {
		return new ListIterator();
	}
	
	private class ListIterator implements Iterator<Item>{
		int index = front;
		@Override
		public boolean hasNext() {
			return index != end;
		}

		@Override
		public Item next() {
			if(!hasNext()) throw new NoSuchElementException();
			Item cur = data[index++];
			return cur;
		}
		  
	}
	
	public static void main(String[] args) {
		MyQueue<Integer> queue = new MyQueue<Integer>();
		for (int i = 0; i<10; i++) {
			queue.enqueue(i);
		}
		
		Iterator<Integer> iter = queue.iterator();
		
		for (int i = 0; i<10; i++) {
			System.out.println(iter.next());
		}
		System.out.println("DONE");
	}
}
