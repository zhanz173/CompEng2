import java.util.Arrays;
import java.util.NoSuchElementException;

public class BSTSet{
	private TNode root;
	
	public BSTSet() {
		 root = null;
	}
	
	//O(n) = nlog(n) + n
	public BSTSet(MyQueue<Integer> queue) {
		int[] arr = new int[queue.size()];
		int i = 0;
		while(!queue.isEmpty()) {
			arr[i++] = queue.dequeue();
		}
		root = build(arr, 0, arr.length-1);
	}
	
	
	//O(n) = nlog(n)
	public BSTSet(int[] input) {
		// sort(input);
		Arrays.sort(input); 
		root = build(input, 0, input.length-1);
	}
	
	//O(n) = nlog(n)
	public boolean isIn(int v) {
		TNode temp = root;
		while (temp != null) {
			if (temp.element < v) temp = temp.right;
			else if (temp.element > v) temp = temp.left;
			else if (temp.element == v) return true;
		}
		return false;
	}
	
	//O(n) = log(n)
	public void add(int v) {
		root = add(root, v);	//dummy function
	}

	public boolean remove(int v) {
		try {
			root = remove(root, v);	
		}catch(Exception  NoSuchElementException) {
			return false;
		}
		return true;
	}
	
	public int size() {
		if (root == null) return 0;
		return size(root);
	}
	
	public int height() {
		if (root == null) return -1;
		return height(root);
	}
	
	//O(n) = 3n
	public BSTSet intersection(BSTSet s) {
		int n = s.size();
		if (n == 0) return new BSTSet();
		MyQueue<Integer> queue = new MyQueue<Integer>();	
		int[] arr = new int[n];
		toArray(s.root, arr, 0);
		
		for (int i = 0; i < n; i++) {
			if (this.isIn(arr[i])) queue.enqueue(arr[i]);
		}
		return new BSTSet(queue);
	}
	
	//O(n) = 3n    
	public BSTSet union(BSTSet s) {
		int n = this.size() ;
		int m = s.size();
		if (n == 0) 	return s;
		if (m == 0)    	return this;
		
		int[] arr1 = new int[n];
		int[] arr2 = new int[m];
		toArray(this.root, arr1, 0);
		toArray(s.root, arr2, 0);
		
		return new BSTSet(merge(arr1,arr2));
	}
	
	public BSTSet difference(BSTSet s) {
		int[] arr = new int[this.size()];
		MyQueue<Integer> queue = new MyQueue<Integer>();
		toArray(s.root, arr, 0);
		
		for (int i = 0; i < arr.length; i++) {
			if (!s.isIn(arr[i])) queue.enqueue(arr[i]);
		}
		return new BSTSet(queue);
	}
	
	public void printNonRec() {
		MyQueue<TNode> q = new MyQueue<TNode>();	
		TNode cur = root;
		
		while(cur != null || !q.isEmpty()) {
			while(cur != null) {
				q.enqueue(cur);
				cur = cur.left;
			}
			cur = q.pop();
			System.out.print(cur.element + ", ");
			cur = cur.right;
		}
		System.out.println();
	}
	
	private TNode add(TNode n, int v) {
		if (n == null) return new TNode(v,null,null);
		if (n.element < v) n.right = add(n.right, v);
		else if (n.element > v) n.left = add(n.left, v);
		return n;
	}
	
	private int toArray(TNode n, int[] arr, int i) {
	    if(n == null) return i;
	    
	    i = toArray(n.left, arr, i);
	    arr[i++] = n.element;
	    i = toArray(n.right, arr, i);
	    return i;
	}
	
	private int size(TNode n) {
		int count = 1;
		
		if (n.left != null) count += size(n.left);
		if (n.right != null) count += size(n.right);
		return count;
	}
	
	private TNode build(int[] a, int begin, int end) {
		if(begin > end) return null;
		
		int mid = (begin + end)/2,i=mid;
		TNode n = new TNode(a[mid],null,null);

		while (i != 0 && a[i] == a[i-1])	i--;
		n.left = build(a, begin, i-1);
		
		i = mid;
		while (i <end && a[i] == a[i+1])	i++;
		n.right = build(a, i+1, end);
		
		return n;
	}
	
	private int height(TNode n) {
		if (n == null) return 0;
		
		int left = height(n.left);
		int right = height(n.right);
		
		if (left > right) return 1 + left;
		return 1 + right;
	}
	
	private void sort(int [] arr) {
        int n = arr.length; 
        for (int i = 1; i < n; i++) { 
            int flag = arr[i]; 
            int j = i - 1; 

            while (j >= 0 && arr[j] > flag) { 
                arr[j + 1] = arr[j]; 
                j--; 
            } 
            if (arr[j + 1] == flag);
            arr[j + 1] = flag; 
        } 
        /*
        if (repeat != 0) {
        	int[] new_arr = new int[n-repeat];
        	for(int i = 1; i < n-repeat; i++) {
        		
        	}
        }*/
	}
	
	private int[] merge(int[] a, int[] b) {
		int i = 0, j = 0, n = a.length + b.length;
		int n_temp = n;
		int[] arr = new int[n];
		
		for (int k = 0; k < n; k++) {
			if (i >= a.length)			arr[k] = b[j++];
			else if (j >= b.length)		arr[k] = a[i++];
			else if (a[i] > b[j])		arr[k] = b[j++];
			else if (a[i] < b[j])		arr[k] = a[i++];
			else {
				arr[k++] = a[i++];
				n_temp--;
			}
		}
		if (n_temp != n) {
			int[] new_arr = new int[n_temp];
			System.arraycopy(arr, 0, new_arr, 0, n_temp);
			return new_arr;
		}
		return arr;
	}
	
	//O(log n) 
	private TNode remove(TNode n, int v){
		if (n == null) throw new NoSuchElementException(); 
		
		if (n.element < v) n.right = remove(n.right, v);
		else if (n.element > v) n.left = remove(n.left, v);
		else {
			if(n.left == null) return n.right;		//delete node with one child
			if(n.right == null) return n.left;
			
			n.element = findMin(n.right).element;
			n.right = removeMin(n.right);
		}
		return n;
	}
	
	private TNode removeMin(TNode n) {
		if (n.left == null) return n.right;
		
		n.left = removeMin(n.left);
		return n;
	}
	
	private TNode findMin(TNode n) {
		if (n.left == null) return n;
		return findMin(n.left);
	}
	
	private void printBSTSet(TNode t){
		if(t!=null){ 
			printBSTSet(t.left); 
			System.out.print(" " + t.element + ", "); 
			printBSTSet(t.right);
			} 
		}
	
	public void printBSTSet(){
		if(root==null) {
			System.out.println("The set is empty");
		}else{ 
			System.out.print("The set elements are: "); 
			printBSTSet(root); 
			System.out.print("\n");
		}
	}
	
	public static void main(String[] args){
        int[] d1 = {1, 0, 6, 3, 2, 7, 5, 8, 4}; //no repetitions
        int[] d2 = {5, 6, 5, 1, 2, 3, 2, 7, 9, 8, 10, 12, 4, 11};//with repetitions
        int v1 = 1; //value in set d1
        int v2 = 9; //value not in set d1
        int v3 = 0; //another value in set d1

        int[] d3 = {10, 2, 9, 8, 11}; //has elements in common with d1
        int[] d4 = {12, 16, 14, 13, 15}; //no common elements with d1

        int[] d6 = {3, 4, 5, 9, 10, 11, 15, 16, 17, 23, 23, 24, 12, 13}; //almost sorted array
        int[] d7 = {2, 3, 4, 11, 12, 13, 25, 26}; // sorted; has elements in common with d6		

        System.out.println("Test1---constructor1" );
        BSTSet a0 = new BSTSet();
        a0.printBSTSet();
        System.out.println("\n");

        System.out.println("Test2---constructor2---no repetitions" );
        BSTSet a1 = new BSTSet(d1);
        a1.printBSTSet();
        System.out.println("\n");

        System.out.println("Test3---constructor2---with repetitions" );
        BSTSet a2 = new BSTSet(d2);
        a2.printBSTSet();
        System.out.println("\n");

        System.out.println("Test4---isIn(v1)---true" );
        System.out.println("v1 is in set d1");
        System.out.println(a1.isIn(v1));
        System.out.println("\n");

        System.out.println("Test5---isIn(v2)---false" );
        System.out.println("v2 is in set d1");
        System.out.println(a1.isIn(v2));
        System.out.println("\n");

        System.out.println("Test6---add(v1)---v1 was in the set; no change" );
        a1 = new BSTSet(d1);
        a1.add(v1);
        a1.printBSTSet();
        System.out.println("\n");

        System.out.println("Test7---add(v2)---v2 was not in the set; it is added" );
        a1 = new BSTSet(d1);
        a1.add(v2);
        a1.printBSTSet();
        System.out.println("\n");			

        System.out.println("Test9---remove(v2)--v2 was not in the set; no change" );
        a1 = new BSTSet(d1);
        System.out.println(a1.remove(v2)); //false
        a1.printBSTSet();
        System.out.println("\n");

        System.out.println("Test10---remove(v1)--v1 was in the set; it is removed" );
        a1 = new BSTSet(d1);
        a1.printBSTSet();
        System.out.println(a1.remove(v1)); //true
        a1.printBSTSet();
        System.out.println("\n");

        System.out.println("Test11---remove(v3)--v3 was in the set; it is removed" );
        a1 = new BSTSet(d1);
        System.out.println(a1.remove(v3)); //true
        a1.printBSTSet();
        System.out.println("\n");

        System.out.println("Test12---union()---sets with common elements" );
        a1 = new BSTSet(d1);
        BSTSet a3 = new BSTSet(d3);
        BSTSet a5 = a1.union(a3); //union of d1 and d3
        a5.printBSTSet();
        System.out.println("\n");


        System.out.println("Test13---union()---sets without common elements" );
        a1 = new BSTSet(d1);
        BSTSet a4 = new BSTSet(d4);
        a5 = a4.union(a1); //union of d1 and d4
        a5.printBSTSet();
        System.out.println("\n");

        System.out.println("Test14---intersection()---sets with common elements" );
        a1 = new BSTSet(d1);
        a3 = new BSTSet(d3);
        a5 = a3.intersection(a1); //intersection of d1 and d3
        a5.printBSTSet();
        System.out.println("\n");

        System.out.println("Test15---intersection()---sets with no common elements" );
        a1 = new BSTSet(d1);
        a4 = new BSTSet(d4);
        a5 = a1.intersection(a4); //intersection of d1 and d4; should be empty
        a5.printBSTSet();
        System.out.println("\n");


        System.out.println("Test16---intersection()---with empty set" );
        a0 = new BSTSet(); //empty set
        a1 = new BSTSet(d1);
        a5 = a1.intersection(a0); //intersection of d1 and the empty set; should be empty
        a5.printBSTSet();
        System.out.println("\n");

        System.out.println("Test17---size() + height()" );
        a1 = new BSTSet(d1);
        System.out.println("The size of d1 is " + a1.size());
        System.out.println("The height d1 is " + a1.height()); //height should be minimum for bonus
        a1 = new BSTSet(d6);
        System.out.println("The size of d6 is " + a1.size());
        System.out.println("The height of d6 is " + a1.height()); 


        System.out.println("Test18---size() + height()---empty set" );
        a0 = new BSTSet(); //empty set
        System.out.println("The size of the empty set is " + a0.size()); // should be 0
        System.out.println("The height of the empty set is " + a0.height());//should be -1

        System.out.println("Test19---printNonRec()--elements should be in increasing order" );
        a1 = new BSTSet(d1);
        a1.printNonRec(); //set d1
        System.out.println("\n");
        a1 = new BSTSet(d6);
        a1.printNonRec(); //set d6


        //Test bonus
        System.out.println("Test21---bonus" );
        a1 = new BSTSet(d6);
        System.out.println("The height of d6 is " + a1.height());
        a2 = new BSTSet(d7);
        System.out.println("The height of d7 2 is " + a2.height());
        a3 = a1.union(a2); 
        a4 = a1.intersection(a2);
        System.out.println("Print union:");
        a3.printBSTSet(); 
        System.out.println("The height of the union is " + a3.height());
        System.out.println("Print intersection:");
        a4.printBSTSet(); 
        System.out.println("The height of the intersection is " + a4.height());
}
	    
}
