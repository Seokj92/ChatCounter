package edu.handong.csee.java.hw3;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class DataWriter {
	
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