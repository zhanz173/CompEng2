public class MaxHeap {
	private Integer[] data;
	private int size = 0;
	private int capacity;
	
	public MaxHeap(int n){
		if (n < 0) throw new ArithmeticException ("Capacity must be a positive number\n");
		capacity = n;
		data = new Integer[n];
	}
	
	public MaxHeap(Integer[] someArray) {
		data = someArray;
		size = someArray.length ;
		capacity = size;
		for (int i = size/2; i > 0; i--) {
			sink(i);
		}
	}
	
	public void insert(int n) {
		if(size == capacity) resize();
		data[size++] = n;
		swim(size);
	}
	
	public int deleteMax() {
		if (size == 0) throw new ArithmeticException("Empty array");
		int max = data[0];
		data[0] = data[size-1];
		data[size-1] = max;
		size--;
		sink(1);
		return max;
	}
	
	public String toString() {
		String s = "";
		for(int i = 0; i < size; i++){
			s += data[i].toString() + ',';
		}
		return s;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public int getCapacity() {
		return this.capacity;
	}
	private void swim(int cur) {
		while(cur > 1 && data[cur-1] > data[cur/2-1]) {
			exch(cur-1,cur/2-1);
			cur = cur/2;
		}
			
	}
	private void resize() {
		Integer[] new_array = new Integer[2*capacity];
		System.arraycopy(data,0,new_array,0,size);
		data = new_array;
		capacity *=2;
	}
	
	private void sink(int cur) {
		for (int child = cur; child *2 <= size; cur = child) {
			child = cur*2;
			if (child != size && data[child-1] < data[child]) child++;
			if (data[cur-1] < data[child-1]) exch(cur-1,child-1);
			else break;
		}
	}
	
	private void exch(int i, int j) {
		Integer temp = data[i];
		data[i] = data[j];
		data[j] = temp;
	}
	
	public static void heapsort(Integer[] arrayToSort) {
		MaxHeap h = new MaxHeap(arrayToSort);
		//h.insert(100);
		int i = 0; int j = h.size -1, temp;
		while(h.size > 0) h.deleteMax();
		while(i < j) {
			temp = arrayToSort[i];
			arrayToSort[i] = arrayToSort[j];
			arrayToSort[j] = temp;
			i++;
			j--;
		}
	}
	
	public static void main(String[] args) {
		Integer[] arr = {727605,549150,-368502,313998,-765352};
		heapsort(arr);
		
	}
}
