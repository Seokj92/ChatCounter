package edu.handong.csee.java.hw3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * This program is to count the numbers of message in the kakaotalk files(.txt, .csv)
 * @author JS
 * This class has main method to run this program.
 * There are 3 String variables(inputFilePath, outFilepath, outFileName)
 *  
 */

public class ChatMessageCounter {
	
	/**
	 * This is a main method for this program
	 * Put the directory path to read all the files to be read
	 * Set the directory path to get the file(in the form of .CSV)
	 * I set the name of outputfile(output)
	 * @param args
	 */

	public static void main(String[] args) {
		
		DataReader dataReader = new DataReader();

		
		String inputFilePath = args[0]; //ex) C:\\Users\\PC\\Downloads\\Chat-Java-20180527T144139Z-001\\Chat-Java
		String outputFilePath = args[1]; //set the directory like inputFilePath
		String outputFileName = "output";
		
		try {
			List<String> files = dataReader.getDirectory(inputFilePath);

			MessageFilter messageFilter = new MessageFilter();
			messageFilter.messageFiler(inputFilePath,files);
			ArrayList<HashMap<String, String>> list = messageFilter.getResult();

			List<String> result = messageCount(list);
			
			DataWriter dataWriter = new DataWriter();
			dataWriter.setCSVFile(outputFilePath, outputFileName, result);
			
			System.out.println(dataReader.getText(outputFilePath + "\\" + outputFileName + ".csv", "UTF-8"));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/** 
	 * @param list
	 * Put the people who participate in the conversation in the nameMap
	 * Initiate the first count as 0
	 * In extracted text, increase the count for each speaker by 1.
	 * Save return list in key(name), value(count) form from saved list by sorted name
	 */
	public static List<String> messageCount(ArrayList<HashMap<String, String>> list) {
		List<String> result = new ArrayList<>();
		
		HashMap<String, Integer> nameMap = new HashMap<>();
		for (int i = 0; i < list.size(); i++) {
			nameMap.put(list.get(i).get("userName").toString(), 0);
																	
		}
		
		for (int i = 0; i < list.size(); i++) {
			for (String key : nameMap.keySet()) {
				if (list.get(i).get("userName").equals(key)) {
					nameMap.put(key, nameMap.get(key) + 1);
				}
			}
		}
		List<String> sortList = sortByValue(nameMap);
				
		for(int i = 0 ; i<sortList.size(); i++ ){
			for (String key : nameMap.keySet()) {
				if (sortList.get(i).toString().equals(key)) {
					result.add(key + "," + nameMap.get(key));
				}
			}
		}
		
		return result;

	}
	
	/**
	 * This method is used to organize the order in descending order.
	 * @param map
	 * 
	 */
	
	public static List<String> sortByValue(HashMap map) {
        List<String> list = new ArrayList();
        list.addAll(map.keySet());
        //descending order
        Collections.sort(list,new Comparator() {
            public int compare(Object o1,Object o2) {
                Object v1 = map.get(o1);
                Object v2 = map.get(o2);
                return ((Comparable) v2).compareTo(v1);
            }
        });
        
        return list;
    }

}


