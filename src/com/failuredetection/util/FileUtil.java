package com.failuredetection.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
	
	public static List<String> readFile(File file) {
		List<String> lines = new ArrayList<String>();
			
		BufferedReader br = null;
		
		try {
			
			br = new BufferedReader(new FileReader(file));
			
			String line;
		    while ((line = br.readLine()) != null) {
		    	lines.add(line);
		    }
			
		} catch (Exception e) {
			System.out.println("Unable to read file");
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return lines;
	}
	
}
