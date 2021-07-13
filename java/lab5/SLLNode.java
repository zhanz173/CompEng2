package lab5;

public class SLLNode {
	int value; 
	SLLNode next; 
	
	public SLLNode() {
		//default constructor
		value = 0;
		next = null;
	}
	
	public SLLNode(int i, SLLNode n) 
	{ 
		value = i; 
		next = n;
	}
}
