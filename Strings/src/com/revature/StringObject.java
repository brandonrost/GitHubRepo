package com.revature;

public class StringObject {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s1 = "Hello World!";
		long startTime = System.nanoTime();
		System.out.println(StringObject.reverseStringObject(s1)); 
		long endTime = System.nanoTime(); 
		System.out.println("The program executed reverseStringObject in " + (endTime - startTime) + "ns.");
		

		StringBuffer buffer = new StringBuffer(s1);
		startTime = System.nanoTime();
		System.out.println(StringObject.reverseStringBuffer(s1)); 
		endTime = System.nanoTime(); 
		System.out.println("The program executed reverseStringBuffer in " + (endTime - startTime) + "ns.");
		

		StringBuilder builder = new StringBuilder(s1); 
		startTime = System.nanoTime();
		System.out.println(StringObject.reverseStringBuilder(s1)); 
		endTime = System.nanoTime(); 
		System.out.println("The program executed reverseStringBuilder in " + (endTime - startTime) + "ns.");
	}
	
	public static String reverseStringObject(String s) {
		String s2 = "";
		for(int i =(s.length()-1); i >= 0; i-- ) {
			s2 += s.charAt(i); 
		}
		return s2;
	}
	
	public static String reverseStringBuilder(String s) {
		StringBuilder s2 = new StringBuilder(s);
		String s3 = s2.reverse().toString(); 
		return s3; 
	}
	
	public static String reverseStringBuffer(String s) {
		StringBuffer s2 = new StringBuffer(s);
		String s3 = s2.reverse().toString(); 
		return s3; 
	}
	
}
