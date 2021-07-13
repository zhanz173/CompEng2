package lab4;

import java.util.Arrays;

public class Matrix{
	private  final int[][]  matrixData;	// integer array to store integer data
	private  int    rowsNum;	// number of rows
	private  int    colsNum;	// number of columns
	
	public Matrix() {
		this.matrixData = null;
	}

	public Matrix( int row, int col ) //constructor1
	{
		/* constructs a row-by-col matrix with all elements equal to 0;
		if row<=0 then the number of rows of the matrix is set to 3;
		likewise, if col<=0 the number of columns of the matrix is set to 3. */
		if(row<0 || col<0) throw new IndexOutOfBoundsException("Invalid row or col number");
		this.rowsNum = row;
		this.colsNum = col;
		this.matrixData = new int[row][col];

	}//end first constructor


	public Matrix( int[][] table) // constructor2
	{
		/* constructs a matrix out of the two dimensional array table,
		   with the same number of rows, columns, and the same element in each
		 position as array table. */

		rowsNum = table.length;
		colsNum = table[0].length;
		matrixData = new int[rowsNum][colsNum]; // allocates memory for the 2D array
		//loop to fill the array with values:
		for (int i=0; i<rowsNum; i++)
			for(int j=0; j<colsNum; j++)
				matrixData[i][j] = table[i][j];

	}//end second constructor

	
	public int getRows() {return rowsNum;}
	
	
	public int getCols() {return colsNum;}
	
	
	public boolean equals( Matrix that) {
		return (this.colsNum == that.getCols()) && (this.rowsNum == that.getRows());
	}
	
	
	public boolean isSquard() {
		return this.colsNum == this.rowsNum;
	}
	
	
	public int getElement(int i, int j) throws IndexOutOfBoundsException
	{
		/* if i and j are valid indices of this matrix,
		   then the element on row i and column j of this matrix
		   is returned; otherwise it throws an exception with message "Invalid indexes".*/

		if  ( (i>=0 && j>=0) && (i<this.rowsNum && j<this.colsNum)) {
			return this.matrixData[i][j];
		}
		else {
			throw new IndexOutOfBoundsException("Invalid indexes.");
		}

	}//end getElement

	/* Only copy the data of a matrix not the matrix class*/
	public void copy(Matrix that) //Assuming matrix that is larger than this
	{
		for (int i = 0; i < this.rowsNum; i++) {
			this.matrixData[i] = Arrays.copyOf(that.matrixData[i],this.getCols());
		}
	}
	
	
	public boolean setElement(int  x, int i, int j)
	{
		/* if i and j are valid indexes of this matrix, then the element on  row i and
           column j of this matrix is assigned the value  x and true is returned;
           otherwise false is returned and no change in the matrix is performed */
		if  ( (i>=0 && j>=0) && (i<this.rowsNum && j<this.colsNum)) {
			this.matrixData[i][j] = x;
			return true;
		}
		return false;
	}//end setElement

	
	public Matrix copy()
	{ /* returns a deep copy of this Matrix */
		final int[][]  new_matrixData =new int[this.rowsNum][this.colsNum];
		for (int i = 0; i < this.rowsNum; i++) {
			 new_matrixData[i] =  Arrays.copyOf(this.matrixData[i],this.getCols());
		 }
		return new Matrix(new_matrixData) ;
	}//end copy

	
	public void addTo( Matrix m ) throws ArithmeticException
	{
		/*adds Matrix m to this Matrix; it throws an exception message "Invalid operation"
		  if the matrix addition is not defined*/

		if ( m.getRows() == this.getRows() && m.getCols() == this.getCols() ) {
			for(int i=0; i<this.rowsNum; i++)
				for( int j=0; j<this.colsNum; j++)
					this.matrixData[i][j] += m.getElement(i,j); 
		}
		else {
			throw new ArithmeticException("Invalid operation");
		}

	}

	
	public Matrix subMatrix(int i, int j) throws ArithmeticException
	{
	    /*  returns a new Matrix object, which represents a submatrix of this Matrix,
	    	formed out of rows 0 through i and columns 0 through j;
	    	 the method should first check if values i and j are within the required range,
	    	 and throw an exception if any of them is not. The exception detail message should read: "Submatrix not defined".
	    	 Note: The new object should be constructed in such a way that changes in the new matrix do not affect
	    	 this Matrix. */
		if( (i<this.rowsNum && j<this.colsNum) && (i>0 && j>0)) {
			Matrix sub = new Matrix(i,j);
			for (int k = 0; k < i; k++) {
				sub.copy(this);
			}
			return sub;
		}
		
		throw new ArithmeticException("Out of range");
	}
	
	public boolean isUpperTr() {
		for (int i=0; i<this.rowsNum; i++) {
			for (int j=0; j<i && j<this.colsNum; j++) 
			{
				if (this.matrixData[i][j] != 0 )
					return false;
			}
		}
		return true;
	}
	
	/*Sum up the matArray
	 * First duplicate the first matrix in this array
	 * Then add the rest elements to the first matrix */
	public static Matrix sum (Matrix[] matArray) throws ArithmeticException{
		Matrix mat = matArray[0].copy();
		for(int i=1; i<matArray.length; i++) {
			if(!mat.equals(matArray[i]))
				throw new ArithmeticException("Invalid Opertaion");
			mat.addTo(matArray[i]);
		}
		return mat;
	}
	
	public String toString(  )
	{
		/* returns a string representing the matrix,
		   with each row on a line, and the elements in each row being separated by 2 blank spaces. */

		String output = new String(); // creates an empty string
        	for(int i = 0; i < rowsNum; i++){
        		for(int j = 0; j < colsNum; j++){
        			output += matrixData[i][j] + "  ";
        		}
        	output += "\n";
        	}
        return output;

	}//end toString
}//end class
