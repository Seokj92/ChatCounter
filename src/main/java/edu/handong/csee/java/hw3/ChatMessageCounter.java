package edu.handong.csee.java.hw3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class ChatMessageCounter {

	public static void main(String[] args) {
		DataReader dataReader = new DataReader();

		
		String inputFilePath = args[0];
		

		String outputFilePath = args[1];

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
