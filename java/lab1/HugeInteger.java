
import java.util.Arrays;
import java.util.Random;

public class HugeInteger implements Comparable<HugeInteger> {
	byte[] num;
	int size;
	int sign;
	int flag = 0;
	private final byte[] LuT1 = {(byte)0x00, (byte)0x01, (byte)0x02, (byte)0x03, (byte)0x04,
			(byte)0x05, (byte)0x06, (byte)0x07, (byte)0x08, (byte)0x09};
	private final byte[] LuT2 = {(byte)0x00, (byte)0x10, (byte)0x20, (byte)0x30, (byte)0x40,
			(byte)0x50, (byte)0x60, (byte)0x70, (byte)0x80, (byte) 0x90};
	
	public HugeInteger() {
		size = 0;
		sign = 0;
		num = null;
	}
	
	public HugeInteger(String val) {
		int i = 0, j = 0, dig1, dig2;
		size = val.length();
		char[] numbers = val.toCharArray(); 

				
		if(numbers[i] == '-') {
			sign = 1;
			size--;
			i++;
			if(size == 0) throw new NumberFormatException();
		}
		
		while( i<numbers.length-1 && numbers[i] == '0') {
			i++;
			size--;
		}
	
		num = new byte[(size+1)/2];
		if(size % 2 != 0) {
			dig1 = Character.getNumericValue(numbers[i++]); 
			if (dig1< 0 || dig1 >9) {
				throw new NumberFormatException();
			}
			num[j++] =  (byte) (LuT1[dig1]);
		}

		while(i < numbers.length-1) {
			dig1 = Character.getNumericValue(numbers[i++]); 
			if (dig1< 0 || dig1 >9) {
				throw new NumberFormatException();
			}
			dig2 = Character.getNumericValue(numbers[i++]);
			if (dig2< 0 || dig2 >9) {
				throw new NumberFormatException();
			}
			num[j++] =  (byte) (LuT1[dig2] + LuT2[dig1]);
		}
		
	}

	
	public HugeInteger(int n) {
		size = n;
		num = new byte[(n+1)/2];
		Random rn = new Random();
		// generate 1 byte random value
		for (int i = 0; i < (n+1)/2; i++) {
			byte dig1 = LuT1[rn.nextInt(9)];
			byte dig2 = LuT2[rn.nextInt(9)];
			num[i] = (byte) (0xff & (dig1+dig2));
		}
		
		if (n % 2 != 0) {
			num[0] = (byte) (LuT1[rn.nextInt(8)+1]);
		}
				
	}
	
	public HugeInteger(byte[] b) {
		num = b;
		size = getsize();
	}
	
	public HugeInteger add(HugeInteger h) {
		int carry = 0;
		int cursor1, cursor2;
		HugeInteger base, addend, result;
		byte b1,b2,b3;
		result = new HugeInteger();
		
		if (this.sign == 1 && h.sign == 1) 
			result.sign = 1;
		
		
		else if(h.sign == 1 && h.flag == 0) {
			h.flag = 1;  //set flag
			return this.subtract(h);
		}
		else if(this.sign == 1 && this.flag == 0) {
			this.flag = 1;  //set flag
			return h.subtract(this);
		}
				
		
		//take larger number as base, the other as addend
		if(this.size > h.size) {
			base = this;
			addend = h;
			cursor1 = this.arr_len();
			cursor2 = h.arr_len();

		}else {
			base = h;
			addend = this;
			cursor2 = this.arr_len();
			cursor1 = h.arr_len();
		}
		
		result.copy(base);				

		//add operation loop
		while(cursor1 > 0){
			b3 = 0;
			if(cursor1 > 0) 	b1 = base.num[--cursor1];
			else	b1 = 0x00;		
			
			if(cursor2 > 0)		b2 = addend.num[--cursor2];
			else {
				if (carry == 0) break;
				b2 = 0x00;
			}

		
			if (carry == 1) {
				b3 = (byte) ((0x0f & b1) + (0x0f & b2) + 0x01);
				carry = 0;
			}else {
				b3 = (byte) ((0x0f & b1) + (0x0f & b2));
			}
						
		
			if((0xff & b3 >>> 4) != 0) {
				b3 += 0x06;
			}else if (b3 > 0x09 ) {
				b3 -= 0x0a;
				b3 += 0x10;  //add carry to next magnitude		
			}
						
			b3 += (0xf0 & b1) + (0xf0 & b2);
			
			if ((b3 & 0xf0) > 0x90) {
				carry = 1;
				b3 -= 0xa0;
			}else if((b3 & 0xf0) < (b1 & 0xf0) || (b3 & 0xf0) < (b2 & 0xf0)) {
				b3 += 0x60;
				carry = 1;
			}
			result.num[cursor1] = b3;
		}
		
		
		if(carry > 0) {			
			result.resize(carry);
		}
		result.size = result.getsize();
		//sign check
		h.flag = 0;
		this.flag = 0;
		return result;
	}
	
	//10's compliment
	
	// stadard form = A - B = C,   A & B are both positive integer
	/*
	 * case 1:  -A - (-B) => B - A
	 * case 2:  -A - B    => -(A + B)
	 * case 3:  A - B     => ///
	 * case 4:  A - (-B)  => A + B
	 */
	
	
	// TO DO:
	/*
	 *  fix : sign error when A < B in standard case
	 *  optimization: minimize subclasses
	 *  
	 */
	public HugeInteger subtract(HugeInteger h) {
		HugeInteger result;
		// case 1 & 4, B is negative, take inverse of B and set the B flag
		if(h.sign == 1 && h.flag == 0) {
			h.flag = 1;
			this.flag = 1;
			if(this.sign == 1)		// A is negative => case1
			{
				result = h.subtract(this);
			}
			else {
				result = h.add(this);
			}
			return result;
		}
		
		
		if(this.sign == 1 && this.flag == 0) {
			this.flag = 1;
			h.flag = 1;
			result = add(h);
			result.sign = 1;
			return result;
		}
		
		
		// handle case 2
		if(this.sign == 1 && this.flag == 0) {
			this.flag = 1;
			h.flag = 1;
			result = add(h);
			result.sign = 1;
			return result;
		}
		
		
		// handle standard case

		result = this.add(complement(h));
		h.flag = 0;
		this.flag = 0;
		if(result.size > this.size && result.size > h.size) {
			if(result.size%2 == 0) {
				result.num[0] &= 0x0f;
			}else
				result.resize(-1);			
			result.sign = 0;
			return result.add(new HugeInteger("1"));
		}
		result = h.complement(result);
		result.sign = 1;
		return result;
	}
	
	@Override
	//return 1 if this > h, -1 otherwise
	//positive sign = 0, negative sign = 1
	public int compareTo(HugeInteger h) {
		if (this.sign > h.sign) return -1;	//this is negative, h is positive
		if (this.sign < h.sign) return 1;	//h is negative, this is positive
		if(this.size > h.size) return (this.sign == 0)? 1:-1;  
		/*
		 * Byte by byte comparison
		 * check the first byte and then the next one if they are equal
		 * return result otherwise
		 */
		for(int i=0; i < this.arr_len(); i++) {
			if((0xff&this.num[i]) > (0xff & h.num[i])) return (this.sign == 0)? 1:-1;
			if(this.num[i] == h.num[i]) continue;
			
			return (h.sign == 0)? -1:1; // unsigned h > unsigned this
		}
		return 0;
	}
		
	public HugeInteger multiply(HugeInteger h) {
		HugeInteger result = Multiplication.multiply(this.num,h.num);
		if (this.sign != h.sign)
			result.sign = 1;
		else result.sign = 0;
		result.size = result.getsize();
		
		return result;
	}

	public HugeInteger complement (HugeInteger h) {
		HugeInteger h_comp = new HugeInteger();
		HugeInteger base = this.size > h.size ? this : h;
		int len = (base.size+1)/2;
		byte[] new_num = new byte[len];

		for (int i = 0, j = 0; i < len ; i++) {
			if (i < len - h.arr_len() )
				new_num[i] = (byte) (0x99);
			else
				new_num[i] = (byte) (0x99 - h.num[j++]);
			h_comp.size += 2;
		}
		
		if (base.size % 2 == 1) {
			new_num[0] = (byte) (new_num[0] & 0x0f);
			h_comp.size--;
		}
		h_comp.num = new_num;
		return h_comp;
	}
	
	public void resize(int carry) {
	    if (carry == 1) {
			byte[] new_arr = new byte[arr_len()+1];
			new_arr[0] = (byte)carry;
			for(int i = 0; i < arr_len(); i++) {
				new_arr[i+1] = num[i];
			}
			num = new_arr;
			size++;
	    }
	    else {
	    	byte[] new_arr = new byte[arr_len()+carry];
			for(int i = 0; i < arr_len()+carry; i++) {
				new_arr[i] = num[i-carry];
			}
			num = new_arr;
			size--;
	    }
	}
	
	public String toString() {
		String s = new String();
		for(byte i : num) {
			int dig1 = (0xff & i) >>> 4;
			int dig2 = 0x0f & i;
			s += dig1;
			s += dig2;
		}
		int i = s.length();
		while(s.charAt(0) == '0') {
			if(i == 1) break;
			s = s.substring(1);
			i--;
		}
		if(this.sign == 1 && !s.equals("0")){
			return "-" + s;
		}
		
		return s;
	}
	
	public int arr_len() {
		return num.length;
	}
	
	public void copy(HugeInteger h) {
		byte[] b = h.num;
		num = new byte[b.length];
		for (int i = 0; i < b.length; i++) {
			num[i] = b[i];
		}
		sign = h.sign;
		size = h.size;
	}
	
	public int getsize() {
		int NumberOfDigits = num.length * 2;
		if((num[0] & 0xf0 )== 0) {
			NumberOfDigits--;
		}
		return NumberOfDigits;
	}
	
	public static void print(HugeInteger h) {
		StringBuilder sb = new StringBuilder();
		for (byte b : h.num) {
		        sb.append(String.format("%02X ", b));
		 }
		    System.out.println(sb.toString());
	}
	
	public static void main(String []args) {

	}
	
	/*
	 * 	12345 * 1234 -> make them equal in size
	 *  12345 * 01234 -> main loop
	 * 	[123]  [45] * [012]  [34] -> first recursion 
	 * -> calling back with 123 * 012 (sub loop 1)
	 * [12] [3] * [01] [2] 
	 * 
	 * ->calling back with 12 * 0 (sub loop 1-1)
	 * return 12 -> END OF SUBLOOP 1-1
	 * 
	 * ->calling back with 3 * 2 (sub loop 1-2)
	 * return 6 -> END OF SUBLOOP 1-2
	 * 
	 * -> return 12*10000 + (12+3)*(3) *100 + 2*3 -> END OF SUB LOOP 1
	 * 
	 * -> calling back with 012 * 34 (sub loop )
	 * 			same steps as before
	 * 
	 */
	private static class Multiplication {
		final static HugeInteger ZERO = new HugeInteger("0");
		
		private static byte[] makeEqualLength(byte[] b1, byte[] b2) {
			byte[] new_num = new byte[b1.length];
			for(int i = 0, j = 0; i < b1.length; i++){
				if(i < b1.length - b2.length)
					new_num[i] = (byte)0x00;
				else
					new_num[i] = b2[j++];
				}
			return new_num;
		}
		
		public static HugeInteger multiply(byte[] b1, byte[] b2){
			if(IsZero(b1) || IsZero(b2))	return ZERO;
			
			if(b1.length > b2.length) {
				b2 = makeEqualLength(b1, b2);
			}
			else if(b1.length < b2.length) {
				b1 = makeEqualLength(b2, b1);
			}
			
			if(b1.length < 2) {
				int i1 = ((b1[0] >> 4)&0x0f) *10 + (int)(b1[0] & 0x0f);
				int i2 = ((b2[0] >> 4)&0x0f) *10 + (int)(b2[0] & 0x0f);
				
				return new HugeInteger(Integer.toString(i1*i2));
			}
			
			
			byte[] sub_h1_msb, sub_h1_lsb,sub_h2_msb,sub_h2_lsb;
			int n = (b1.length+1)/2;
			sub_h1_msb = Arrays.copyOfRange(b1,0,n);
			sub_h1_lsb = Arrays.copyOfRange(b1,n,b1.length);
			sub_h2_msb = Arrays.copyOfRange(b2,0,n);
			sub_h2_lsb = Arrays.copyOfRange(b2,n,b2.length);
			HugeInteger P1 = multiply(sub_h1_msb,sub_h2_msb);  //most significant bits
			HugeInteger P2 = multiply(sub_h1_lsb,sub_h2_lsb);
			HugeInteger P3 = multiply(simple_add(sub_h1_msb,sub_h1_lsb),simple_add(sub_h2_msb,sub_h2_lsb));
			int multiplier = (b1.length-n);
			
			/*
			 * size update
			 */
			P1.size = P1.getsize();
			P3.size = P3.getsize();
			
			P3 = P3.subtract(P1).subtract(P2);
			
			/*
			 * skip resize zero
			 */
			if(P1 != ZERO) {
				P1.num = times_ten(P1.num,2*multiplier);
				P1.size += 4*multiplier;
			}
			
			if(P3 != ZERO){
				P3.num = times_ten(P3.num,multiplier);
				P3.size += 2*multiplier;
			}

			HugeInteger result = P1.add(P2).add(P3);
			return result;
			
		}
		
		private static byte[] times_ten(byte[] a,int n) {
			byte[] a_b = new byte[a.length + n];
			for(int i = 0; i < a.length; i++) {
				a_b[i] = a[i];
			}
			return a_b;
		}
			
		//Assuming b1 >= b2 in length
		private static byte[] simple_add(byte[]b1, byte[]b2) {
			byte b3,b4;
			int carry = 0;
			for(int i = b1.length-1, j = b2.length-1; i >= 0; i--) {
				if(j < 0 )
					if(carry == 0) break;
					else b4 = 0;
				else
					b4 = b2[j--];
				
				if (carry == 1) {
					b3 = (byte) ((0x0f & b1[i]) + (0x0f & b4) + 0x01);
					carry = 0;
				}else {
					b3 = (byte) ((0x0f & b1[i]) + (0x0f & b4));
				}
					
				if((0xff & b3 >>> 4) != 0) {
					b3 += 0x06;
				}else if (b3 > 0x09 ) {
					b3 -= 0x0a;
					b3 += 0x10;  //add carry to next magnitude		
				}
							
				b3 += (0xf0 & b1[i]) + (0xf0 & b4);
				
				if ((b3 & 0xf0) > 0x90) {
					carry = 1;
					b3 -= 0xa0;
				}else if((b3 & 0xf0) < (b1[i] & 0xf0) || (b3 & 0xf0) < (b4 & 0xf0)) {
					b3 += 0x60;
					carry = 1;
				}
				b1[i] = b3;
			}
			if(carry == 1) {
				byte[] new_arr = new byte[b1.length+1];
				new_arr[0] = (byte)carry;
				for(int i = 0; i < b1.length; i++) {
					new_arr[i+1] = b1[i];
				}
				return new_arr;
			}
			return b1;
		}
			
		private static boolean IsZero(byte[] b) {
			for(int i = 0; i < b.length; i++) {
				if (b[i] != 0) return false;
			}
			return true;
		}
		
		public static void main(String []args) {
			HugeInteger num16 = new HugeInteger("1721754168001");
			HugeInteger num17 = new HugeInteger("-99");
			HugeInteger num18 = new HugeInteger("0");;
			System.out.println(num18.multiply(num17).toString());
		}
	}
}
