package us.uta.test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

 public class ReadAll2 
 {
    public static void main(String[] args) 
    {
    System.out.println("Reading File from Java code");
    //Name of the file
    String apps="/home/aditya/java-spring/fileLocation/TestData2/jobs.tsv";
   // String user2="/home/aditya/java-spring/fileLocation/TestData2/user2.tsv";
    try{    
    FileReader inputFile1 = new FileReader(apps);
    //FileReader inputFile2 = new FileReader(user2);
    //Instantiate the BufferedReader Class
    Map<String, String> appsMap = new HashMap<String, String>();
    BufferedReader bufferReader = new BufferedReader(inputFile1);  
    //BufferedReader bufferReader2 = new BufferedReader(inputFile2);
    //Variable to hold the one line data
    //String line1;
    //ArrayList<String> A = new ArrayList<String>();    
    //while ((line1 = bufferReader2.readLine()) != null) {
    //	A.add(line1);    	
    //}
    System.out.println("Reading from jobs into arraylist");
    //System.out.println(" value at specified index: " +A.get(2));
    String line;
    int x= 0;    
    // Read file line by line and print on the console
    int check = 0;
    while ((line = bufferReader.readLine()) != null)   {        
    	if(check != 0) {
            String userDataValue[] = line.split("\t");
            String users_uid = userDataValue[0];
            String dateFormat = userDataValue[9];
            //userLines.add(users_uid + "\t" + state);
            appsMap.put(users_uid, dateFormat);                                   
            //System.out.println(userMap.get(users_uid));
        }
        check = check + 1;           
    	//System.out.println("line");        
    }
    System.out.println(appsMap);
    //Close the buffer reader
    bufferReader.close();
   // bufferReader2.close();
    }catch(Exception e){
            System.out.println("Error while reading file line by line:" 
            + e.getMessage());                      
    }

    }
  }