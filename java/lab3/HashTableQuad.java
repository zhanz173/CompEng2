package lab4_2;

import java.util.Random;

public class HashTableQuad {
	private int size;
	private final double LOAD;
	private int M;
	private Integer[] table;
	
	public Counter c;
	
	public HashTableQuad() {
		LOAD = 0;
		M = 2;
	}
	
	public HashTableQuad(int maxNum, double load) {
		if(load <= 0 || maxNum < 0) throw new ArithmeticException();
		if(load > 0.5) {
			System.out.println("Warning: load can not exceed upper limit 0.5");
			System.out.println("Load will be automatically set to 0.5");
			load = 0.5;
		}
		int n = (int) (maxNum / load);
		
		if (n <= 2) M = 2;
		else Set_M(n);
		
		table = new Integer[this.M];
		LOAD = load;
		c = new Counter();
	}
	
	public void insert(int n) {
		if (size >= M * LOAD) rehash();
		
		final int H = hash(n);
		int j = 1,k = H;
		while(table[k] != null) {
			if (table[k] == n) return;
			k = ((int) Math.pow(j, 2) + H) % M;		
			j++;
		}
		table[k] = n;
		size++;
	}
	
	private void rehash() {		
		int old_M = this.M;
		Integer[] Old_table = this.table;
		size = 0;
		
		Set_M(M*2);
		this.table  = new Integer[M];
	
		for (int i = 0; i < old_M; i++) {
			if(Old_table[i] != null)
				insert(Old_table[i]);
		}
		System.out.println("reshashed, table size is " + M);
	}
	
	public boolean isIn(int n) {
		int H = hash(n);
		int j = 1;
		for (int i = H; table[i] != null;) {
			if (table[i] == n) {
				c.Found(j);
				return true;
			}
			i = ((int)(H + Math.pow(j, 2))) % M;
			j++;
		}
		c.NotFound(j);
		return false;
	}
	
	public void printKeys() {
		for (int i = 0; i < table.length; i++) 
			if (table[i] != null) 
				System.out.println("key: "+ table[i]);
	}
	
	public void printKeysAndIndexes() {
		for (int i = 0; i < table.length; i++) 
			if (table[i] != null) 
				System.out.println("index: " + i + " key: "+ table[i] );
	}
	
	public int getSize() { return size;}
	
	public double getLoadFactor() { return (double)size/M;}
	
	public double getS() { return (double)c.success_probe / c.success;}
	
	public double getF() { return (double)c.failed_probe / c.failed;}
	
	public int getM() { return this.M;}
	
	private void Set_M(int n) {
		int flag = 0;
		int end = (int)Math.sqrt(n) + 1;
		while (true) {
			flag = 1;
			for (int i = 2; i <= end; i++) {
				if (n % i == 0) { 
					n++;
					flag = 0;
					break;
				}
			}
			if (flag == 1) {
				this.M = n;
				return;
			}
		}
	}
	
	private int hash(int k) {
		return Math.abs(k) % M;
	}
	
	public class Counter{
		int success = 0;
		int success_probe = 0;
		int failed = 0;
		int failed_probe = 0;
		
		public void Found(int n) {
			success++;
			success_probe += n;
		}
		
		public void NotFound(int n) {
			failed++;
			failed_probe += n;
		}
	}
	
	public static void main(String[] args) {
		HashTableQuad table8 = new HashTableQuad(5,0.9);
		int[] arr2 = {13,24,35,46,57,2,3,111,1011,1100};
		for (int i = 0; i < 10; i++) {
			table8.insert(arr2[i]);
		}
		System.out.println("table8:  ");
		table8.printKeysAndIndexes();
		System.out.println("20 is not in the table: ");
		System.out.println(table8.isIn(20));
		System.out.println("2 is in the table ");
		System.out.println(table8.isIn(2));
	}
}
