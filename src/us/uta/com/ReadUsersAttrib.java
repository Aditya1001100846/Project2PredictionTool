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
    System.out.println("Reading users.tsv required attribute files from Java code");
    //Name of the file
    //user2.tsv-->users.tsv--->apps.tsv-->jobs.tsv
    String users="/home/aditya/java-spring/fileLocation/TestData2/users.tsv";
    String user2="/home/aditya/java-spring/fileLocation/TestData2/user2.tsv";
    String apps="/home/aditya/java-spring/fileLocation/TestData2/apps.tsv";
    String jobs="/home/aditya/java-spring/fileLocation/TestData2/jobs.tsv";
    
    try{    
    FileReader fileusers = new FileReader(users);
    FileReader fileuser2 = new FileReader(user2);
    FileReader fileapps = new FileReader(apps);
    FileReader filejobs = new FileReader(apps);
    //Instantiate the BufferedReader Class    
    BufferedReader users1 = new BufferedReader(fileusers); 
    BufferedReader testUser2 = new BufferedReader(fileuser2);
    BufferedReader users2 = new BufferedReader(fileusers);
    BufferedReader uidJobMap = new BufferedReader(fileusers);
    BufferedReader jobPredictor = new BufferedReader(filejobs);
    
//hashmap to store usedId2 and jobId(predictable)
    Map<String, String> userJobMap = new HashMap<String, String>();
//Variable to hold the one line data
    String usersData1;
    String user2Data;
    String usersData2;
    String uidJob;
   
//result set map
    Map<Integer, String> resultMap = new HashMap<Integer, String>(); 
//test data from user2.tsv
    while((user2Data = testUser2.readLine()) != null){
    	Integer test_userId = Integer.parseInt(user2Data);
    
//search for test data in users.tsv
    		String flag = "n";
    		while ((usersData1 = users1.readLine()) != null) {		    	
    			String usersDataValue1[] = usersData1.split("\t");
		        Integer users_uid1 = Integer.parseInt(usersDataValue1[0]);
//if test data found, get the required attribute values for test data
		        if(users_uid1 == test_userId ){	
		        	flag = "y";
			        String test_state = usersDataValue1[2];
			        String test_major = usersDataValue1[6];
			        String test_workHistCount = usersDataValue1[8];			        			        
			        String test_managedOthers = usersDataValue1[11];
//compare each test value with all the values in users.tsv and get the scores for top 10 similar user_Ids. in a list
			        Map<String, Integer> userScore = new HashMap<String, Integer>();
			        Map<String, Integer> topUserScore = new HashMap<String, Integer>();
			        Integer userSpecificScore = 0;
			        while ((usersData2 = users2.readLine()) != null ){			        				        	
			        	//compare each line in users.tsv to test data and check for top 10 similar jobs
			        	String usersDataValue2[] = usersData2.split("\t");
			        	String usersId = usersDataValue2[0];
			        	if(usersDataValue2[2].equals(test_state)){
			        		userSpecificScore = userSpecificScore + 1;
			        	}
			        	if(usersDataValue2[6].equals(test_major)){
			        		userSpecificScore = userSpecificScore + 1;
			        	}
			        	if(usersDataValue2[8].equals(test_workHistCount)){
			        		userSpecificScore = userSpecificScore + 1;
			        	}
			        	if(usersDataValue2[9].equals(test_managedOthers)){
			        		userSpecificScore = userSpecificScore + 1;
			        	}
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
			        int count = 0;
			        for(Map.Entry<String, Integer> entry:list){
			            //find first 10 similar inclinesd user ids			        	
			        	if (count < 10){
			        		topUserScore.put(entry.getKey(), entry.getValue());
			        		count = count + 1;
			        	}
			        }			        
//use topUserScore(top values of similar user) map values to count jobIds probability-start			        
			        Map<String, Integer> individualJobScoreCard = new HashMap<String, Integer>();
			        for (Map.Entry<String, Integer> entry : topUserScore.entrySet()) {
			            //entry.getKey()			        
		        		//String scoredUser = entry.getKey();		        		
		        		while((uidJob = uidJobMap.readLine()) != null){
				        	String usersDataValue3[] = uidJob.split("\t");
				        	if(entry.getKey().equals(usersDataValue3[0])){
				        		if (individualJobScoreCard.containsKey(usersDataValue3[2])) {
				        			individualJobScoreCard.put(usersDataValue3[2], individualJobScoreCard.get(usersDataValue3[2])+1);
				        		} else { 
				        			individualJobScoreCard.put(usersDataValue3[2],1);
				        		}
				        	}				        	
				        }
		        	 }    
//use topUserScore map values to count jobIds probability-end
//sort jobId scorecard for similar type of users applied jobs.			        
			        Set<Entry<String, Integer>> set1 = individualJobScoreCard.entrySet();
			        List<Entry<String, Integer>> list1 = new ArrayList<Entry<String, Integer>>(set1);
			        Collections.sort( list1, new Comparator<Map.Entry<String, Integer>>()
			        {
			            public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
			            {
			                return (o2.getValue()).compareTo( o1.getValue() );
			            }
			        } );
			        for (Map.Entry<String, Integer> entry : individualJobScoreCard.entrySet()) {
			        	String flag2 = "n";
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
				        				}			        				
				        			//}
				        			break;
				        		}
			        		}
			        	if(flag2.equals("y")){
		        			break;
			        	}
			        }
			        
		    	}if (flag.equals("y"))
		    		break;	// means one test data is read and now moving onto next test data i.e. U2 value.	    	
		    }    		
    }
   // System.out.println(appsMap.get(A.get(0)));
    //Close the buffer reader
    testUser2.close();
    users1.close();
    users2.close();
    uidJobMap.close();
    jobPredictor.close();
    
    }catch(Exception e){
            System.out.println("Error while reading file line by line:" 
            + e.getMessage());                      
    }

    }
  }












