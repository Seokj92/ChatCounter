package edu.handong.csee.java.hw3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a class to read Data
 * @author JS
 * This class has methods(getText, getDirectory)
 */

public class DataReader {
	
	/**
	 * This method is to get text
	 * @param path
	 * @param encoding
	 * @return String
	 */

	public String getText(String path, String encoding) {
		BufferedReader br = null;
		String text = "";
		String line;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), encoding));
			while ((line = br.readLine()) != null) { //read until meet null
				text = text + line + "\n";
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return text;
	}

	/**
	 * Method for getting directory.
	 * @param strDir	 
	 * @throws IOException
	 */
	public List<String> getDirectory(String strDir) throws IOException {
		List<String> result = new ArrayList<>();
		File dir = new File(strDir);
		File[] fileList = dir.listFiles();
		for (int i = 0; i < fileList.length; i++) {
			File file = fileList[i];
			if (file.isFile()) {
				// if there is a file print out the name of the file.
				result.add(file.getName().toString());
			}
		}
		return result;
	}
}