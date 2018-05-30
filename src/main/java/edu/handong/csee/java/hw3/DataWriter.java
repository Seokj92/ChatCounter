package edu.handong.csee.java.hw3;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * This is a class for write the data.
 * @author JS
 * This class has DataWriter method.
 * When you read the file on the wordpad, set the encoding UTF-8 to keep Korean character from breaking.
 */
public class DataWriter {
	
	/**
	 * Method for set the result(CSV file).
	 * Numbers tell you how many times people speak in the kakaotalk
	 * @param filePath
	 * @param fileName
	 * @param list
	 */
	
	public void setCSVFile(String filePath, String fileName, List<String> list){
		try{
			String csvFileName = filePath + "\\" + fileName + ".csv";
			
			String head = "kakao_name, count\r\n";
			String bodyText = "";
			for(int i =0 ; i< list.size(); i++){
				bodyText = bodyText + list.get(i).toString();
				if(i != list.size()-1){
					bodyText = bodyText + "\r\n";
				}
			}
			
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFileName),"UTF-8"));
			writer.write(head);
			writer.write(bodyText);
			
			writer.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}