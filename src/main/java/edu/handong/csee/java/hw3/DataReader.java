package edu.handong.csee.java.hw3;

import java.io.File;
import java.util.ArrayList;

//read all chat messages


public class DataReader {

	public static void main(String[] args) {
		DataReader dataReader = new DataReader();
		dataReader.getData(args[0]);
	}

	public ArrayList<String> getData(String strDir) {

		getDirectory(strDir);
		File[] files = getListOfFilesFromDirectory(getDirectory(strDir));
		ArrayList<String> messages = readFiles(files);
		return messages;
	}

	private File getDirectory(String strDir) {		
		File myDirectory = new File(strDir);
		return myDirectory;
	}

	private File[] getListOfFilesFromDirectory(File dataDir) {

		for(File file : dataDir.listFiles()) {
			System.out.println(file.getAbsolutePath());
		}
		return dataDir.listFiles();
	}

	private ArrayList<String> readFiles(File[] files) {
		ArrayList<String> messages = new ArrayList<String>();
		return messages;
	}

}
