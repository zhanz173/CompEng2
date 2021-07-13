package lab5;

public class SLLSet {
	private SLLNode head;
	private int size;

	
	 public SLLSet() {
		 head = null;
		 size = 0;
	 }

	 /* backwards create new nodes
	  * update head in every iteration
	  */
	 public SLLSet( int[] sortedArray ) {
		 size = sortedArray.length;
		 head = null;
		 for(int i = size-1; i >= 0 ; i-- ) {
			 head = new SLLNode(sortedArray[i],head);
		 }
	 }
	 
	 public int getSize() {
		 return this.size;
	 }
	 
	 /*
	  * return a deep copy of this
	  */
	 public SLLSet copy() {
		 SLLSet NewSet = new SLLSet();
		 SLLNode tail = null;
		 SLLNode temp = this.head;		 

		 for(int i = 0; i < this.getSize() ; i++) {
			 tail = NewSet.push(temp.value,tail);
			 temp = temp.next;
		 }
		 return NewSet;
	 }
	 
	 /* 
	  * linear search 
	  */
	 public boolean isIn(int v) {
		 SLLNode flag = head;		 
		 for(int i = 0; i < size ; i++) {
			 if(v == flag.value)
				 return true;
			 if(flag.value > v)
				 break;
			 flag = flag.next;
		 }
		 return false;
	 }
	 
	 /*
	  * add a new node ranked by its value 
	  */
	 public void add(int v) {
		 SLLNode flag = head;
		 for(int i = 0; i < size ; i++) {
			 if(flag.value == v) {
				 return;
			 }
			 
			 if(flag.value > v) {
				 head = new SLLNode(v,flag);
				 size++;
				 return;
			 }
			 
			 if(flag.next == null || flag.next.value > v) {
				 flag.next = new SLLNode(v,flag.next);
				 size++;
				 return;
			 }
			 flag = flag.next;
		 }
	 }
	 
	 /*
	  * push a new node to the end of SLLSet
	  */
	 public SLLNode push(int v, SLLNode tail) {
		 if(tail == null) {
			 this.size++;
			 this.head = new SLLNode(v,null);
			 return head;
		 }
		 else {
			 tail.next = new SLLNode(v,null);
			 this.size++;
			 return tail.next;	 
		 }
	 }
	 
	 
	 public void remove(int v){
		 if (size == 0) {
			 System.out.println("cannot delete from null set");
			 return;
		 }
		 
		 /* if v is found on the head  
		  * 	move head to next node
		  * else
		  * 	link previous node to next one  (skip the current node)
		  */

		 if(head.value == v) {
			 head = head.next;
			 size--;
			 return;
		 }
		 
		 SLLNode cur = head.next;
		 SLLNode prev = head;
		 
		 for(int i = 0; i < size-1 ; i++) {
			 if(cur.value == v) {
				 prev.next = cur.next;
				 size--;
				 return;
			 }
			 prev = cur;
			 cur = cur.next;
		 }
		 System.out.printf("%d is not in this set\n", v);
	 }
	 
	 
	 public SLLSet intersection(SLLSet s) {
		 SLLSet NewSet = new SLLSet();
		 SLLNode temp1 = s.head, temp2 = this.head, tail = null;
		 
		 /*compare integer a and b 
		  * 	update a if a < b
		  * 	update b vice verse
		  * 	repeat step 1-2 until fully iterates through either set
		  * push a to the NewSet if they are equal
		  * update both a & b
		  * O(n) = 2n
		  */
		 while(temp1 != null && temp2 != null) {
			 if (temp1.value > temp2.value) {
				 temp2 = temp2.next;
				 continue;
			 }
			 if(temp1.value < temp2.value) {
				 temp1 = temp1.next;
				 continue;
			 }
			 tail = NewSet.push(temp1.value,tail);
			 temp1 = temp1.next;
			 temp2 = temp2.next;
		 }
		 return NewSet;
	 }
		 
	 // unused
	 @SuppressWarnings("unused")
	 private Integer compare(SLLNode temp1, SLLNode temp2) {
		 if (temp1 != null && temp2 != null ) {
			 if (temp1.value > temp2.value) {
				 temp2 = temp2.next;
				 compare(temp1, temp2);
			 }
			 if (temp1.value < temp2.value) {
				 temp1 = temp1.next;
				 compare(temp1, temp2);
			 }
			return temp1.value;
		 }
		 return null;
	 }

	 
	 public SLLSet union(SLLSet s){
		 SLLSet NewSet = new SLLSet();
		 SLLNode temp1 = s.head, temp2 = this.head, tail = NewSet.head;
		 
		 /*	compare two value a, b and push the small one to newset
		  * 	update a or b by calling SLLSet.next() 
		  * 	repeat step 1 & 2 
		  * O(n) = 2n
		  */
		 
		 while(temp1 != null && temp2 != null) {
			 if (temp1.value > temp2.value) {
				 tail = NewSet.push(temp2.value,tail);
				 temp2 = temp2.next;
				 continue;
			 }
			 if(temp1.value < temp2.value) {
				 tail = NewSet.push(temp1.value,tail);
				 temp1 = temp1.next;
				 continue;
			 }
			 tail = NewSet.push(temp1.value,tail);
			 temp1 = temp1.next;
			 temp2 = temp2.next;
		 }

		 //copy the rest elements
		 /* case1: temp1 reaches the end
		  * copy elements left in temp2 to newset 
		  * case2: temp2 reaches the end
		  * copy elements left in temp1 to newset 
		  */
		 if (temp1 == null) {
			 while (temp2 != null) {
				 tail = NewSet.push(temp2.value,tail);
				 temp2 = temp2.next;			 
			 }
			 return NewSet;
		 }
		 else {
			 while (temp1 != null) {
				 tail = NewSet.push(temp1.value,tail);
				 temp1 = temp1.next;
			 }
			 return NewSet;
		}			 
	 }

	 
	 public SLLSet difference(SLLSet s) {
		 SLLSet NewSet = this.copy();
		 SLLSet AnB = this.intersection(s);
		 SLLNode temp = AnB.head;
		 
		 for(int i = 0; i < AnB.getSize(); i++) {
			 NewSet.remove(temp.value);
			 temp = temp.next;
		 } 
		 return NewSet;
	 }
	 
	 
	 public static SLLSet union( SLLSet[] sArray) {
		 SLLSet NewSet = sArray[0].copy();

		 for (int i = 1; i < sArray.length; i++) {
 			 NewSet = NewSet.union(sArray[i]);
		 }
		 return NewSet;
	 }
	 
	 /*overrides toString()
	  * 	return null if length is 0
	  * 	else return every elements stored in node separated by ","
	  */
	 public String toString() {
		 String output = new String();
		 SLLNode flag = this.head;
		 if(flag == null) {
			 return "NULL";
		 }
		 
		 for(int i = 0; i < this.size-1 ; i++) {
			 output += flag.value + ",";
			 flag = flag.next;
		 }
		 output += flag.value;
		 return "{ "+ output + " }";
	 }
	 
	 
	 public static void testDiff() {
		 int diff1[] = {1,2,3,5,7,8,9};
	     int diff2[] = {1,2,3}; // test difference
	     
	     SLLSet listObj98  = new SLLSet(diff1);
	     SLLSet listObj97  = new SLLSet(diff2);

	     SLLSet s = listObj98.difference(listObj97);
	     System.out.println(s.toString());
	 }
}
