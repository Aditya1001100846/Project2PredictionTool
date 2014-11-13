package us.uta.test;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class JobToUser { 
 
    public static void main(String[] args) 
    {
    	Map<String, Integer> lMap=new HashMap<String, Integer>();
        lMap.put("A", 35);
        lMap.put("B", 25);
        lMap.put("C", 50);
        lMap.put("D", 40);

        Set<Entry<String, Integer>> set = lMap.entrySet();
        List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
        Collections.sort( list, new Comparator<Map.Entry<String, Integer>>()
        {
            public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
            {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        } );
        for(Map.Entry<String, Integer> entry:list){
        	
            System.out.println(entry.getKey());//+" ==== "+entry.getValue());     
    }
  }
}