package us.uta.com;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class JobToUser { 
 
    public static void main(String[] args) 
    {
    System.out.println("Reading File from Java code");
    //Name of the file
    String apps="/home/aditya/java-spring/fileLocation/TestData2/jobs.tsv";    
    try{    
    FileReader inputFile1 = new FileReader(apps);    
    //Instantiate the BufferedReader Class
    Map<String, String> appsMap = new HashMap<String, String>();
    BufferedReader bufferReader1 = new BufferedReader(inputFile1);    
    String line1;            
    // Read file line by line and print on the console
    int count = 0;
    while ((line1 = bufferReader1.readLine()) != null)   {        
    	    count = count+1;
            }
                         
    	//System.out.println("line");        
       System.out.println("no of lines: " +count);
       
    
    BufferedReader bufferReader2 = new BufferedReader(inputFile1);   
    String line2;
    int num = 0;
    while ((line2 = bufferReader2.readLine()) != null )   {         	    
            
	    	if (num <= 10){
	    		String userDataValue[] = line2.split("\t");    	
	            String users_uid = userDataValue[0];
	            String job_id = userDataValue[9];      
	            appsMap.put(users_uid,job_id);
	            System.out.println(appsMap.get(users_uid));
	    	}
            num = num+1;
        }       
    Set entries = appsMap.entrySet();
    Iterator iterator = entries.iterator();
    while (iterator.hasNext()) {
    	Map.Entry entry = (Map.Entry) iterator.next();
    	String key = (String) entry.getKey();
    	Integer value = (Integer) entry.getValue();
       System.out.println("userId:" +key + " jobId:" +value );    
    }
    bufferReader1.close();
    bufferReader2.close();
    
    }catch(Exception e){
            System.out.println("Error while reading file line by line:" 
            + e.getMessage());                      
    }

    }
  }