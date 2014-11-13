package us.uta.com;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.text.html.HTMLDocument.Iterator;

public class ReadUsersAttrib  
 {
    public static void main(String[] args) 
    {
    System.out.println("Prediction Tool Project");
    //Name of the file
    //user2.tsv-->users.tsv--->apps.tsv-->jobs.tsv
    String users="/home/aditya/java-spring/fileLocation/TestData2/users.tsv";
    String user2="/home/aditya/java-spring/fileLocation/TestData2/user2.tsv";
    String apps="/home/aditya/java-spring/fileLocation/TestData2/apps.tsv";
    String jobs="/home/aditya/java-spring/fileLocation/TestData2/jobs.tsv";
    
    try{    
    
    FileReader fileuser2 = new FileReader(user2);
    
    FileReader filejobs = new FileReader(jobs);
    //Instantiate the BufferedReader Class    
     
    BufferedReader buser2 = new BufferedReader(fileuser2);    
    BufferedReader jobPredictor = new BufferedReader(filejobs);
    
//hashmap to store usedId2 and jobId(predictable)
    Map<String, String> userJobMap = new HashMap<String, String>();
//Variable to hold the one line data
    String user2Data;              
   
//result set map
    Map<Integer, String> resultMap = new HashMap<Integer, String>(); 
//test data from user2.tsv
    System.out.println("Reading user2.tsv required attribute files from Java code");    
    while((user2Data = buser2.readLine()) != null){
    	Integer test_userId = Integer.parseInt(user2Data);
    	System.out.println("inside user2.tsv, data read: " +test_userId); 
    
//search for test data in users.tsv    		
    		
    		FileReader fileusers = new FileReader(users);
    		BufferedReader b1users = new BufferedReader(fileusers);
    		String usersData1;
    		System.out.println("Reading users.tsv, data read: ");
    		while ((usersData1 = b1users.readLine()) != null) {	
    			String flag = "n";
    			String usersDataValue1[] = usersData1.split("\t");
		        Integer users1Data_uid = Integer.parseInt(usersDataValue1[0]);
		        System.out.println("inside users.tsv, data read: " +users1Data_uid);
//if test data found, get the required attribute values for test data
		        if(test_userId == users1Data_uid){	
		        	flag = "y";
			        String test_state = usersDataValue1[2];
			        String test_major = usersDataValue1[6];
			        String test_workHistCount = usersDataValue1[8];			        			        
			        String test_managedOthers = usersDataValue1[11];
//compare each test value with all the values in users.tsv and get the scores for top 10 similar user_Ids. in a list	
			        FileReader file2users = new FileReader(users);
			        BufferedReader b2users = new BufferedReader(file2users);
			        String usersData2;
			        Map<String, Integer> userScore = new HashMap<String, Integer>();		        			        
			        while ((usersData2 = b2users.readLine()) != null ){			        				        	
			        	//compare each line in users.tsv to test data and check for top 10 similar jobs
			        	String usersDataValue2[] = usersData2.split("\t");
			        	Integer userSpecificScore = 0;
			        	String usersId = usersDataValue2[0];
			        	System.out.println("inside 2nd walla users.tsv, data read: " +users1Data_uid);
			        	if(usersDataValue2[2].equals(test_state)){
			        		userSpecificScore = userSpecificScore + 1;
			        	}
			        	if(usersDataValue2[6].equals(test_major)){
			        		userSpecificScore = userSpecificScore + 1;
			        	}
			        	if(usersDataValue2[8].equals(test_workHistCount)){
			        		userSpecificScore = userSpecificScore + 1;
			        	}
			        	if(usersDataValue2[11].equals(test_managedOthers)){
			        		userSpecificScore = userSpecificScore + 1;
			        	}
			        	System.out.println("score of " +usersId+" is: " +userSpecificScore);
//create hashmap such that it stores jobids and scores for similarity for that jobId with test_userId. //at last sort all these jobIds with highest to lowest scores and check from highest to loweset for hashmap for jobs.tsv enddate. //which ever job_id is encountered first, is the possible predication for that particular testuserId.
//write it on text file and close all open collections and while loops except top one.			        	
			        	userScore.put(usersId, userSpecificScore);			        	
			        }
//sort userScore based on values i.e. userscore descending order
			        Set<Entry<String, Integer>> set = userScore.entrySet();
			        List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
			        Collections.sort( list, new Comparator<Map.Entry<String, Integer>>()
			        {
			            public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
			            {
			                return (o2.getValue()).compareTo( o1.getValue() );
			            }
			        } );
			        System.out.println("users score set in userScore map for the specific user2 value---- ");
			        int count = 0;
			        Map<String, Integer> topUserScore = new HashMap<String, Integer>();
			        for(Map.Entry<String, Integer> entry:list){
			            //find first 10 similar inclined userIds			        	
			        	if (count < 10){
			        		topUserScore.put(entry.getKey(), entry.getValue());
			        		count = count + 1;
			        	}
			        }			        
//use topUserScore(top values of similar user) map values to count jobIds probability-start
			        //start now
			        Map<String, Integer> individualJobScoreCard = new HashMap<String, Integer>();
			        individualJobScoreCard.clear();
			        System.out.println("reading scored users: " +topUserScore);   
			        for (Map.Entry<String, Integer> entry : topUserScore.entrySet()) {
			            //entry.getKey()	
			        	System.out.println("topUser: "+topUserScore);
		        		String scoredUser = entry.getKey();			        	
			        	System.out.println("Reading apps.tsv, read required attribute files");
		        		String uidJob;
		        		FileReader fileapps = new FileReader(apps);
		        		BufferedReader bapps = new BufferedReader(fileapps);
		        		System.out.println("jobs scorecard: " +individualJobScoreCard);
			        	while((uidJob = bapps.readLine()) != null){
				        	String usersDataValue3[] = uidJob.split("\t");
				        	if((scoredUser).equals(usersDataValue3[0])){
				        		if (individualJobScoreCard.containsKey(usersDataValue3[2])) {
				        			individualJobScoreCard.put(usersDataValue3[2], individualJobScoreCard.get(usersDataValue3[2])+1);
				        		} else { 
				        			individualJobScoreCard.put(usersDataValue3[2],1);
				        		}
				        	}				        	
				        }
			        	bapps.close();
		        	 }    
			        System.out.println("jobs scorecard: " +individualJobScoreCard);
//use topUserScore map values to count jobIds probability-end
//sort jobId scorecard for similar type of users applied jobs.		
			        System.out.println("you get the values for jobs score for user2 in individualJobScoreCard map");
			        System.out.println("get the values sorted in individualJobScoreCard map");
			        Set<Entry<String, Integer>> set1 = individualJobScoreCard.entrySet();
			        List<Entry<String, Integer>> list1 = new ArrayList<Entry<String, Integer>>(set1);
			        Collections.sort( list1, new Comparator<Map.Entry<String, Integer>>()
			        {
			            public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
			            {
			                return (o2.getValue()).compareTo( o1.getValue() );
			            }
			        } );
			        System.out.println("creating predication finally");
			        for (Map.Entry<String, Integer> entry : individualJobScoreCard.entrySet()) {
			        	String flag2 = "n";
			        	String uidJob;
			        	while((uidJob = jobPredictor.readLine()) != null){
			        	 //entry.getKey();
			        		String usersDataValue4[] = uidJob.split("\t");
				        		if(entry.getKey().equals(usersDataValue4[0])){
				        			//if(usersDataValue4[9].after()){			        			
				        				String str = "04/09/2012 00:00";
				        				DateFormat formatter  = new SimpleDateFormat("dd/MMM/yy h:m");
				        				Date datelimit = formatter.parse(str);
				        				Date date = formatter.parse(usersDataValue4[9]);
				        				// access date fields
				        				if(date.after(datelimit)){
				        					flag2 = "y";
				        					resultMap.put(test_userId,"entry.getKey()");
				        					System.out.println("ResultMap: " +resultMap);
				        				}			        				
				        			//}
				        			break;
				        		}
			        		}
			        	if(flag2.equals("y")){
		        			break;
			        	}
			        }
			        b2users.close();
			        
		    	}if (flag.equals("y"))
		    		break;	// means one test data is read and now moving onto next test data i.e. U2 value.	    	
		    }
    		
    		b1users.close();
    		
    }
    //System.out.println("ResultMap: " +resultMap);
   // System.out.println(appsMap.get(A.get(0)));
    //Close the buffer reader
    buser2.close();           
    jobPredictor.close();
    
    }catch(Exception e){
            System.out.println("Error while reading file line by line:" 
            + e.getMessage());                      
    }

    }
  }












