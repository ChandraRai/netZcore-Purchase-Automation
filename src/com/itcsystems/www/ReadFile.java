package com.itcsystems.www;

import java.io.*;


public class ReadFile {	
		
	public int hostedPaypage(String filePath) throws IOException {
		String st;
		int result = 0;
		
		File file = new File(filePath); 
		
	    @SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(file)); 
	   
	    while ((st = br.readLine()) != null) {
	    	
	    	if(st.contains("UseHostedPaypage")) {
	    		
	    		if(st.contains("1")) {
	    			return result = 1;
	    		} 
	    		
	    		if(st.contains("4")) {
	    			return result = 4;
	    		}
	    	}	    
	    }
	    return result;
	}
}
