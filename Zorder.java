package SearchCircleSkyline;

import java.io.*;
import java.util.*;
import java.util.Date;
import java.util.Random;
import java.util.ArrayList;
import java.math.*;

public class Zorder {
	// Pad a String 
	public static String createExtra(int num) {
		if( num < 1 ) return "";

		char[] extra = new char[num];
		for (int i = 0; i < num; i++) 
			extra[i] = '0';
		return (new String(extra)); 	
	}


	public static int maxDecDigits( int dimension, int max) {
		BigInteger maxDec = new BigInteger( "1" );
		maxDec = maxDec.shiftLeft( dimension * max );
		maxDec.subtract( BigInteger.ONE );
		return maxDec.toString().length();
	}

	public static String maxDecString( int dimension ) {
		int max = 32;
		BigInteger maxDec = new BigInteger( "1" );
		maxDec = maxDec.shiftLeft( dimension * max );
		maxDec.subtract( BigInteger.ONE );
		return maxDec.toString();
	}
	
	// Convert an multi-dimensional coordinate into a zorder
	// coordinates have already been scaled and shifted
	public static String valueOf(int dimension, int[] coord) {
		int max = 32;
		Vector<String> arrPtr = new Vector<String>(dimension);
		//System.out.println( "maxDec " + maxDec.toString() );
		int fix = maxDecDigits(dimension, max); //global maximum possible zvalue length
		//System.out.println( fix );

		for (int i = 0; i < dimension; i++) {
			String p = Integer.toBinaryString((int)coord[i]);
			//System.out.println( coord[i] + " " + p ); 
			arrPtr.add(p);
		}

		for( int i = 0; i < arrPtr.size(); ++i ) {
			String extra = createExtra( max - arrPtr.elementAt(i).length() ); 
			arrPtr.set(i, extra + arrPtr.elementAt(i) );
			//System.out.println( i + " " + arrPtr.elementAt(i) );
		}
	
		char[] value = new char[dimension * max];
		int index = 0;

		// Create Zorder
		for (int i = 0; i < max; ++i ) {
			for (String e: arrPtr) {
				char ch = e.charAt(i);
				value[index++] = ch;
			}	
		}		
			
		String order = new String(value);
		//System.out.println( value );
		// Covert a binary representation of order into a big integer
		BigInteger ret = new BigInteger( order, 2 );

		// Return a fixed length decimal String representation of 
		// the big integer (z-order)
		order = ret.toString();	
		//System.out.println( order );
		if (order.length() < fix) {
			String extra = createExtra(fix - order.length());
			order = extra + order;
		} else if (order.length() > fix) {
			System.out.println("too big zorder, need to fix Zorder.java");
			System.exit(-1);
		}

		//System.out.println(order);
		
		return order;
	}


	public static void main(String[] args) {
		// Test case
	}
}
