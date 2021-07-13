package lab4_2;

import java.util.Random;

public class Test {
	public static void main(String[] args) {
		System.out.println("--------Constructor test---------");
		try {
			System.out.println("0.5 load");
			HashTableLin table1 = new HashTableLin(10,0.5);
			System.out.println("0 load");
			HashTableLin table2 = new HashTableLin(10,1);
			
		}catch(ArithmeticException e) {
			System.out.println("Error: load factor must greater than 0");
		}
		
		try {
			System.out.println("0.5 load");
			HashTableQuad table1 = new HashTableQuad(10,0.5);
			System.out.println("0 load");
			HashTableQuad table2 = new HashTableQuad(10,-1);
			
		}catch(ArithmeticException e) {
			System.out.println("Error: load factor must greater than 0");
		}
		
		System.out.println();
		
		try {
			System.out.println("0.5 load");
			HashTableLin table1 = new HashTableLin(10,0.5);
			System.out.println("0 load");
			HashTableLin table2 = new HashTableLin(-10,0);
			
		}catch(ArithmeticException e) {
			System.out.println("Error: table size must greater than 0");
		}
		
		try {
			System.out.println("0.5 load");
			HashTableQuad table1 = new HashTableQuad(10,0.5);
			System.out.println("0 load");
			HashTableQuad table2 = new HashTableQuad(10,1);
			
		}catch(ArithmeticException e) {
			System.out.println("Error: load factor must less than 1");
		}
		
		System.out.println("\n--------Rehashing test---------");
		System.out.println("-----HashTableLin-----");
		System.out.println("Table size = 1, load = 0.5");
		System.out.println("insert 100 elements ");
		System.out.println("log2 (100) = 6 rehash needed at most");
		HashTableLin table5 = new HashTableLin(1,0.5);
		Random rand = new Random();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				table5.insert(rand.nextInt(101));
			}
			System.out.println("load factor: " + table5.getLoadFactor());
		}
		System.out.println("table size: " + table5.getM());
		
		
		
		System.out.println("\n-----HashTableQuad-----");
		HashTableQuad table6 = new HashTableQuad(1,0.5);
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				table6.insert(rand.nextInt(101));
			}
			System.out.println("load factor: " + table6.getLoadFactor());
		}
		System.out.println("table size: " + table6.getM());
		
		
		System.out.println("\n--------Table size test---------");
		try {
			HashTableLin table1 = new HashTableLin(10,0.3);
			HashTableLin table2 = new HashTableLin(10,0.4);
			HashTableQuad table3 = new HashTableQuad(13,0.3);
			
			System.out.println("M = 10 / 0.3 = 37: " + table1.getM());
			System.out.println("M = 10 / 0.4 = 29: " + table2.getM());
			System.out.println("M = 13 / 0.3 = 43: " + table3.getM());
		}catch(ArithmeticException e) {
			
		}
		
		
		
		System.out.println("\n--------isIn test---------");
		HashTableLin table7 = new HashTableLin(5,0.9);
		int[] arr1 = {13,24,35,46,57,2,3,111,1011,1100};
		for (int i = 0; i < 10; i++) {
			table7.insert(arr1[i]);
		}
		System.out.println("table7:  ");
		table7.printKeysAndIndexes();
		System.out.println("20 is not in the table: ");
		System.out.println(table7.isIn(20));
		System.out.println("2 is in the table ");
		System.out.println(table7.isIn(2));
		System.out.println("24 is in the table ");
		System.out.println(table7.isIn(24));
		
		System.out.println("\n-----HashTableQuad-----");
		HashTableQuad table8 = new HashTableQuad(5,0.9);
		for (int i = 0; i < 10; i++) {
			table8.insert(arr1[i]);
		}
		System.out.println("table8:  ");
		table8.printKeysAndIndexes();
		System.out.println("20 is not in the table: ");
		System.out.println(table8.isIn(20));
		System.out.println("2 is in the table ");
		System.out.println(table8.isIn(2));
		System.out.println("24 is in the table ");
		System.out.println(table8.isIn(24));
		
		System.out.println("--------complexity test---------");
		double i = 0.1;
		while (i < 0.9) {
			RuningTimeTest(i);
			i += 0.1;
		}
		
		System.out.println("\n-----HashTableQuad-----");
		i = 0.1;
		while(i < 0.5) {
			RuningTimeTest2(i);
			i += 0.1;
		}
		
	}
	
	public static void RuningTimeTest(double lambda) {
		Random rand = new Random();
		HashTableLin table = new HashTableLin(100000,lambda);
		for (int i = 0; i < 100000; i++) {
			table.insert(rand.nextInt(1000000));
		}
		for (int i = 0; i < 1000; i++) {
			table.isIn(rand.nextInt(1000000));
		}
		
		System.out.println("successful search: " + table.getS());
		System.out.println("theoratical time complexity: " + 0.5*(1.0 + 1.0/(1-table.getLoadFactor())));
		System.out.println();
		System.out.println("failed search: " + table.getF());
		System.out.println("theoratical time complexity: " + 
		0.5*(1.0 + 1.0/Math.pow(1-table.getLoadFactor(),2)));
		System.out.println("-------------------------");
	}
	
	public static void RuningTimeTest2(double lambda) {
		Random rand = new Random();
		HashTableQuad table = new HashTableQuad(100000,lambda);
		for (int i = 0; i < 100000; i++) {
			table.insert(rand.nextInt(1000000));
		}
		for (int i = 0; i < 1000; i++) {
			table.isIn(rand.nextInt(1000000));
		}
		
		System.out.println("successful search: " + table.getS());
		System.out.println("theoratical time complexity: " + 0.5*(1.0 + 1.0/(1-table.getLoadFactor())));
		System.out.println();
		System.out.println("failed search: " + table.getF());
		System.out.println("theoratical time complexity: " + 
		0.5*(1.0 + 1.0/Math.pow(1-table.getLoadFactor(),2)));
		System.out.println("-------------------------");
	}
}
