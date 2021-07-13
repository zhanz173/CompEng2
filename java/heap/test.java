import java.util.Random;

public class test {
	public static void main(String[] args) {
		 MaxHeap heap1;
		 Random random = new Random();
		 Integer[] arr1;
		 int size = 10;
		 
		 arr1 = new Integer[size];
         for(int j=0;j<size;j++){
             arr1[j] = random.nextInt((int)1e4);
         }
		 System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		 System.out.println("Testing constructor 1 -- invalid input");
		 try {
			 heap1 = new MaxHeap(-1);
		 }catch(ArithmeticException e) {
			 System.out.println(e);
		 }
		 System.out.println("Testing constructor 1 -- valid input");
		 System.out.println("Input number is 10");
		 try {
			 heap1 = new MaxHeap(size);
			 System.out.println("Heap capacity -- 10");
			 System.out.println(heap1.getCapacity());
			 System.out.println("Heap size -- 0");
			 System.out.println(heap1.getSize());
		 }catch(ArithmeticException e) {
			 System.out.println(e);
		 }
		 
		 System.out.println("Testing constructor 2 -- valid input");
		 System.out.println("Input an array with length 10 ");
		 try {
			 heap1 = new MaxHeap(arr1);
			 System.out.println("Heap capacity -- 10");
			 System.out.println(heap1.getCapacity());
			 System.out.println("Heap size -- 10");
			 System.out.println(heap1.getSize());
		 }catch(ArithmeticException e) {
			 System.out.println(e);
		 }
		 
		 System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		 System.out.println("Insert  -- insert 10 times");
		 try {
			 heap1 = new MaxHeap(size);
			 int k;
			 System.out.println("Input data:");
			 for (int i = 0; i < size; i++) {
				 k = random.nextInt((int)1e4);
				 System.out.print(k + ",");
				 heap1.insert(k);
			 }
			 System.out.println();
			 System.out.println(heap1.toString());
		 }catch(ArithmeticException e){
			 System.out.println("Failed");
		 }	  
		 System.out.println();
		 System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		 System.out.println("deleteMax -- empty array");
		 try {
			 heap1 = new MaxHeap(size);
			 heap1.deleteMax();
		 }catch(ArithmeticException e) {
			 System.out.println(e);
			 System.out.println("Pass");
		 }
		 
		 System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		 System.out.println("Heapsort -- descending order");
		 try {
			 MaxHeap.heapsort(arr1);
			 for(Integer i : arr1)
				 System.out.print(i + ",");
		 }catch(ArithmeticException e) {
			 System.out.println(e);
		 }
		 
		 
	}
}
