package us.uta.com;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReadUsersAttrib  
 {
    public static void main(String[] args) 
    {
    System.out.println("Reading users.tsv required attribute files from Java code");
    //Name of the file
    String users="/home/aditya/java-spring/fileLocation/TestData2/users.tsv";
    String user2="/home/aditya/java-spring/fileLocation/TestData2/user2.tsv";
    
    try{    
    FileReader inputFile1 = new FileReader(users);
    FileReader inputFile2 = new FileReader(user2); 
    //Instantiate the BufferedReader Class
    Map<String, String> appsMap = new HashMap<String, String>();
    BufferedReader users1 = new BufferedReader(inputFile1); 
    BufferedReader testUser2 = new BufferedReader(inputFile2);
    BufferedReader users2 = new BufferedReader(inputFile1);
    
//Variable to hold the one line data
    String usersData1;
    String testData;
    String usersData11;
   
//test data from user2.tsv
    while((testData = testUser2.readLine()) != null){
    	Integer test_userId = Integer.parseInt(testData);
    
//search for test data in users.tsv
    		String flag = "n";
    		while ((usersData1 = users1.readLine()) != null) {		    	
    			String userDataValue[] = usersData1.split("\t");
		        Integer users_uid = Integer.parseInt(userDataValue[0]);
//if test data found, get the required attribute values for test data
		        if(users_uid == test_userId ){	
		        	flag = "y";
			        String test_state = userDataValue[2];
			        String test_major = userDataValue[6];
			        String test_workHistCount = userDataValue[8];
			        String test_experirnce = userDataValue[9];			        
			        String test_managedOthers = userDataValue[11];
//compare each test value with all the values in users.tsv and get the scores for top 10 similar user_Ids. in a list 
			        while ((usersData11 = users2.readLine()) != null ){			        	
			        	//compare each line in users.tsv to test data and check for top 10 similar jobs
			        	String userDataValue11[] = usersData11.split("\t");
			        	//create hashmap such that it stores jobids and scores for similarity for that jobId with test_userId.
			        	//at last sort all these jobIds with highest to lowest scores and check from highest to loweset for hashmap for jobs.tsv enddate.
			        	//which ever job_id is encountered first, is the possible predication for that particular testuserId.
			        	//write it on text file and close all open collections and while loops except top one
			        	
			        }
		    	}if (flag.equals("y"))
		    		break;		    	
		    }    		
    }
   // System.out.println(appsMap.get(A.get(0)));
    //Close the buffer reader
    testUser2.close();
    users1.close();
    users2.close();
    
    }catch(Exception e){
            System.out.println("Error while reading file line by line:" 
            + e.getMessage());                      
    }

    }
  }












