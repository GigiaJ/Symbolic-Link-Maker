package main;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

	static Path targetDir = null;
	static int nameCount = 0; 
	public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter dir to create symbolic links for");
		Path dir = Paths.get(input.nextLine());
		nameCount = dir.getNameCount();
		System.out.println("Enter target dir");
		targetDir = Paths.get(input.nextLine());
		createLinks(dir.toFile());
		input.close();
	}

	private static void createLinks(File directory) {
	    for (File file : directory.listFiles()) {
	    	  if (file.isDirectory()) {
	        	  StringBuilder sb = new StringBuilder(directory.getPath());
	        	  for (int i = 0; i < nameCount; i++) {
	        		sb = sb.replace(0, sb.indexOf("\\") + 1, "");     		  
	        	  }
	        	  try {
					Files.createDirectories(Paths.get(targetDir + "\\" + sb.toString()));
				} catch (IOException e) {
					e.printStackTrace();
				}
	        	createLinks(file);
	          }
	          if (file.isFile()) {
	        	  StringBuilder sb = new StringBuilder(directory.getPath());
	        	  for (int i = 0; i < nameCount; i++) {
	        		sb = sb.replace(0, sb.indexOf("\\") + 1, "");     		  
	        	  }
	        	  try {
	        		 if (!Files.exists(Paths.get(targetDir + "\\" + sb.toString() + "\\" + file.getName()))) {
	        			  Files.createDirectories(Paths.get(targetDir + "\\" + sb.toString()));
	        		  }
					Files.createSymbolicLink(Paths.get(targetDir + "\\" + sb.toString() + "\\" + file.getName()), Paths.get(directory.toPath() + "\\" + sb.toString() + "\\" + file.getName()));
				} catch (IOException e) {
					e.printStackTrace();
				}
	          }
	        
	    }
	}	
}
