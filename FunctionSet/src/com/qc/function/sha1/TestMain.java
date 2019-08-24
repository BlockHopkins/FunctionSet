package com.qc.function.sha1;

public class TestMain {
	public static void main(String[] args) {
		println(SecurityFunctions.generateVerifyCode("hqc"));
	}
	
	private static void println(Object obj){
		System.out.println(obj);
	}
}
