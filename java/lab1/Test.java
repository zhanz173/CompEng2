import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class Test {

	public static void main(String[] args) {		
		try {
			
			HugeInteger num1 = new HugeInteger("333");
			HugeInteger num2 = new HugeInteger("-333");
			HugeInteger num3 = new HugeInteger("1");
			HugeInteger num4 = new HugeInteger("0");
			
			HugeInteger num5 = num1.add(num2);
			HugeInteger num6 = num2.add(num3);
			HugeInteger num7 = num1.add(num3);
			HugeInteger num8 = num1.add(num4);
			
			System.out.println(num5.toString());
			System.out.println(num6.toString());
			System.out.println(num7.toString());
			System.out.println(num8.toString());

			
		} catch (NumberFormatException e) {
			
			e.printStackTrace();
		}	
		
		try {
			
			HugeInteger num8 = new HugeInteger("-");
			HugeInteger num9 = new HugeInteger("+");
			HugeInteger num10 = new HugeInteger("a");
			
			System.out.println(num8.toString());
			System.out.println(num9.toString());
			System.out.println(num10.toString());
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}		
		
		try {
			HugeInteger num10 = new HugeInteger("0");
			HugeInteger num11 = new HugeInteger("-9999");
			HugeInteger num12 = new HugeInteger("9999");
			HugeInteger num13 = new HugeInteger("-1");
			
			System.out.println(num10.multiply(num11).toString());
			System.out.println(num12.multiply(num11).toString());
			System.out.println(num10.multiply(num12).toString());
			System.out.println(num13.multiply(num11).toString());
			
		}catch(NumberFormatException e) {
			e.printStackTrace();	
		}
		
		try {
			HugeInteger num14 = new HugeInteger("0");
			HugeInteger num15 = new HugeInteger("-9999");
			HugeInteger num16 = new HugeInteger("9999");
			HugeInteger num17 = new HugeInteger("-1");
			HugeInteger num18 = num17.subtract(num15);
			System.out.println(num15.subtract(num17).toString());
			System.out.println(num14.subtract(num17).toString());
			System.out.println(num18.toString());
			System.out.println(num16.subtract(num17).toString());
			
		}catch(NumberFormatException e) {
			e.printStackTrace();	
		}
		
		try {
			HugeInteger num19 = new HugeInteger("-3330");
			HugeInteger num20 = new HugeInteger("-333");
			HugeInteger num21 = new HugeInteger("00001");
			HugeInteger num22 = new HugeInteger("1");
			System.out.println(num19.compareTo(num20));  //0
			System.out.println(num19.compareTo(num20));  //-1
			System.out.println(num19.compareTo(num22));  //-1
			System.out.println(num22.compareTo(num21));  //0
			
		}catch(NumberFormatException e) {
			e.printStackTrace();	
		}
		
		/*
		try {
			MULTIPLICATION();
		}catch(FileNotFoundException e){
			e.printStackTrace();	
		}catch(UnsupportedEncodingException e) {
			e.printStackTrace();	
		}
	*/
	}
	public static void MULTIPLICATION() throws FileNotFoundException, UnsupportedEncodingException {
		long startTime, endTime;
		double runTime=0.0; 
		HugeInteger huge1, huge2;
		System.out.println("Size of integer for multiplication: ");
		Scanner input = new Scanner(System.in);
		int MAXNUMINTS = input.nextInt();
		input.close();
		PrintWriter writer = new PrintWriter("multiplication_stats.txt", "UTF-8");		
		
		for (int numInts=1; numInts < MAXNUMINTS;){
			huge1 = new HugeInteger(numInts);
			huge2 = new HugeInteger(numInts);
			startTime = System.currentTimeMillis(); 
			
			for(int numRun=0; numRun < 2; numRun++) {
				 huge1.multiply(huge2);
			}
			endTime = System.currentTimeMillis();
			runTime =(double) (endTime - startTime)/((double) 2);
			writer.println(numInts + " " + runTime);
			numInts += 2;
			//System.out.println("status: " + numInts);
			if((numInts-1) %100 == 0) {
				System.out.println("status: " + (double)numInts/MAXNUMINTS);
			}
		}
		writer.close();
		System.out.println("finished");
	}
	
	public static void ADD() throws FileNotFoundException, UnsupportedEncodingException {
		long startTime, endTime;
		double runTime=0.0; 
		HugeInteger huge1, huge2;
		System.out.println("Size of integer for add: ");
		Scanner input = new Scanner(System.in);
		int MAXNUMINTS = input.nextInt();
		input.close();
		PrintWriter writer = new PrintWriter("ADD_stats.txt", "UTF-8");		
		
		for (int numInts=1; numInts < MAXNUMINTS;){
			huge1 = new HugeInteger(numInts);
			huge2 = new HugeInteger(numInts);
			startTime = System.currentTimeMillis(); 
			
			for(int numRun=0; numRun < 100; numRun++) {
				 huge1.add(huge2);
			}
			endTime = System.currentTimeMillis();
			runTime =(double) (endTime - startTime)/((double) 100);
			writer.println(numInts + " " + runTime);
			numInts += 1;
			if(numInts %1000 == 0) {
				System.out.println("status: " + (double)numInts/MAXNUMINTS);
			}
		}
		writer.close();
		System.out.println("finished");
		}
	
	public static void SUBTRACT() throws FileNotFoundException, UnsupportedEncodingException {
		long startTime, endTime;
		double runTime=0.0; 
		HugeInteger huge1, huge2;
		System.out.println("Size of integer for SUBTRACT: ");
		Scanner input = new Scanner(System.in);
		int MAXNUMINTS = input.nextInt();
		input.close();
		PrintWriter writer = new PrintWriter("SUBTRACT_stats.txt", "UTF-8");		
		
		for (int numInts=1; numInts < MAXNUMINTS;){
			huge1 = new HugeInteger(numInts);
			huge2 = new HugeInteger(numInts);
			startTime = System.currentTimeMillis(); 
			
			for(int numRun=0; numRun < 100; numRun++) {
				 huge1.subtract(huge2);
			}
			endTime = System.currentTimeMillis();
			runTime =(double) (endTime - startTime)/((double) 100);
			writer.println(numInts + " " + runTime);
			numInts += 1;
			if(numInts %1000 == 0) {
				System.out.println("status: " + (double)numInts/MAXNUMINTS);
			}
		}
		writer.close();
		System.out.println("finished");
		}
	
	public static void COMPARE() throws FileNotFoundException, UnsupportedEncodingException {
		long startTime, endTime;
		double runTime=0.0; 
		HugeInteger huge1, huge2;
		System.out.println("Size of integer for SUBTRACT: ");
		Scanner input = new Scanner(System.in);
		int MAXNUMINTS = input.nextInt();
		input.close();
		PrintWriter writer = new PrintWriter("COMPARE.txt", "UTF-8");		
		
		for (int numInts=1; numInts < MAXNUMINTS;){
			huge1 = new HugeInteger(numInts);
			huge2 = new HugeInteger(numInts);
			startTime = System.currentTimeMillis(); 
			
			for(int numRun=0; numRun < 100; numRun++) {
				 huge1.compareTo(huge2);
			}
			endTime = System.currentTimeMillis();
			runTime =(double) (endTime - startTime)/((double) 10);
			writer.println(numInts + " " + runTime);
			numInts += 1;
			if(numInts %1000 == 0) {
				System.out.println("status: " + (double)numInts/MAXNUMINTS);
			}
		}
		writer.close();
		System.out.println("finished");
		}
}
