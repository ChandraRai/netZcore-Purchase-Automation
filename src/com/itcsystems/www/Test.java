package com.itcsystems.www;

import java.io.IOException;

public class Test {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		String filePath = "";
		//Add money test
		ReadFile rd = new ReadFile();
		if(rd.hostedPaypage(filePath) == 1) {
			System.out.println("Moneris");
		} 
		
		if(rd.hostedPaypage(filePath) == 4) {
			System.out.println("Bambora");
		}
					
	}

}
