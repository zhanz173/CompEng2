package lab4_2;

import java.util.Random;

public class HashTableLin {
	private int size;
	private final double LOAD;
	private int M;
	private Integer[] table;
	public Counter c;
	
	public HashTableLin() {
		LOAD = 0;
		M = 2;
	}
	
	public HashTableLin(int maxNum, double load) {
		if(load <= 0 || load > 0.99|| maxNum < 0) throw new ArithmeticException();
		int n = (int) (maxNum / load);
		
		if (n <= 2) M = 2;
		else Set_M(n);
		
		table = new Integer[this.M];
		LOAD = load;
		c = new Counter();
	}
	
	public void insert(int n) {
		if (size >= M * LOAD) rehash();
		
		int i = hash(n);
		while(table[i] != null) {
			if (table[i] == n) return;
			i = (i + 1) % this.M;
		}
		table[i] = n;
		size++;
	}
	
	private void rehash() {
		int old_M = this.M;
		Integer[] Old_table = this.table;
		Set_M(M*2);
		
		this.table =  new Integer[M];
		size = 0 ;
	
		for (int i = 0; i < old_M; i++) {
			if(Old_table[i] != null)
				insert(Old_table[i]);
		}
		System.out.println("reshashed, table size is " + M);
	}
	
	public boolean isIn(int n) {
		int j = 1;
		for (int i = hash(n); table[i] != null; i = (i + 1) % M) {
			if (table[i] == n) {
				c.Found(j);
				return true;
			}
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
	
	public double getLoadFactor() {return (double)size/M;}
	
	public double getS() { return (double)c.success_probe / (double)c.success;}
	
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
	
	public static void main(String[] args) {
		Random rand = new Random();
		HashTableLin test = new HashTableLin(10,0.3);
		
		
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
}
