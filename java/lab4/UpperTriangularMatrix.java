package lab4;

public class UpperTriangularMatrix{
	private int size;
	private final int[] elements;
	
	public UpperTriangularMatrix() {
		this.elements = null;
	}
	
	public UpperTriangularMatrix( int n ) {
		if(n<0) throw new ArithmeticException("Invaild input");
		elements = new int [n*(n+1)/2];
		size = n;
	}
	
	public UpperTriangularMatrix( Matrix upTriM ) throws IllegalArgumentException {
		if(!upTriM.isUpperTr() || !upTriM.isSquard()) throw new IllegalArgumentException("Invaild input"); 

		size = upTriM.getCols();
		elements = new int[size*(size+1)/2];

		for(int i=0; i<size; i++)
			for (int j=i; j<size; j++)
				elements[convertIndex(i,j)] = upTriM.getElement(i, j);				
	}
	
	public int getDim() {	return size;}
	
	/* Convert 2d index to 1d index*/
	public int convertIndex(int i, int j) {
		int index = 0;
		for (int c=0; c<i; c++) 
			index += size-c;
		index += j-i;
		return index;
	}
	
	public int getElement(int i, int j) throws IndexOutOfBoundsException
	{
		if((i>=0 && j>=0) && (i<this.size && j<this.size)){
			if (j < i)
				return 0;  //Return 0 if index is under diagonal
			else {
				int index = convertIndex(i,j);
				return this.elements[index];
			}
		}
		else {
			throw new IndexOutOfBoundsException("Invalid indexes");
		}
	}
	
	
	public void setElement(int x, int i, int j) throws IndexOutOfBoundsException,IllegalArgumentException{
		 if((i>=0 && j>=0) && (i<this.size && j<this.size)){
				if ( j<i && x!=0 ) {
					throw new IllegalArgumentException("Incorrect argument");
				}
						
				else {
					int index = convertIndex(i,j);
					this.elements[index] = x;
				}
		}
		else {
			throw new IndexOutOfBoundsException("Invalid indexes");
			}
	 }
	 
	public Matrix fullMatrix() {
		 Matrix mat = new Matrix(size,size);
		 for(int i=0; i<size; i++) {
			 for(int j=i; j<size; j++) {
				 mat.setElement(this.elements[convertIndex(i,j)],i,j);
			 }
		 }
		 return mat;
	 }
	
	public void print1DArray() {
		 for(int i:elements)
			 System.out.print(i+"  ");
	 }
	
	 public String toString() {
		 Matrix fullMat = this.fullMatrix();		
		 return fullMat.toString();
	 }

	 public int getDet() {
		 int det = 1;
		 for(int i=0; i<size; i++) {
			 det *= elements[this.convertIndex(i, i)];
		 }
		 return det;
	 }
	 
	 /*back substitution*/
	 public double[] effSolve(double[] b) throws IllegalArgumentException {
		 if (b.length != size || this.getDet() == 0)
			 throw new IllegalArgumentException("Invalid arguments");
		 
		 double[] x = new double[size];
		 int index = size*(size+1)/2 - 1;		//subscript of uperTri matrix this 
		 		 
		 /*	a(1,1)x1 + a(1,2)x2 + ... a(1,n)xn = b1*/
		 /*	           a(2,2)x2 + ... a(2,n)xn = b2*/
		 /*							  ...          */
		 /*	           			  ... a(n,n)xn = bn*/
		 
		 /* Here i'm using back substitution that calculates leading non-zero term */
		 /* First calculating the last row x(n) = bn/a(n,n)*/
		 /* Then goes up to second last row x(n-1) = [b(n-1)-x(n)*a(n-1,n)]/x(n-1) and so on*/
		 
		 /* i,j is row and column count of a 2d matrix. */
		 /* Sigma is the sum of non-leading zero entries in one row.eg.x(n)*a(n-2,n) + x(n-1)*a(n-2,n-1) */
		 for (int i=size-1; i>=0; i--) {
			 double sigma = 0;
			 for(int j=size-1; j>i; j--,index--) {
				 sigma += elements[index]*x[j];
			 }
			 if(elements[index] == 0) 
				 x[i] = 0;
			 else
				 x[i] = (b[i]-sigma)/elements[index];
			 index--;
		 }
		 
		 return x;
	 }
}
