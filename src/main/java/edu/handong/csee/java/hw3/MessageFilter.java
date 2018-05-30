package edu.handong.csee.java.hw3;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * This is a MessageFilter class
 * It has methods to classify two type of message(.txt, .csv)
 * I combined two class(reader for .txt, .csv) and put into this class. I apologize for the inconvenience in reading.
 * @author JS
 *
 */

public class MessageFilter {

	final static String DATE_CSV_FORMAT = "yyyy-MM-dd HH:mm:ss";

	final static String DATE_TXT_FORMAT = "yyyy년 MM월 dd일 E";
	
	String txtDate = "";
	
	// to integrate the content of the messages read I declare it static variable
	
	private ArrayList<HashMap<String, String>> result = new ArrayList<>();

	/**	 
	 * @param filePath
	 * @param files
	 * It is for classifying messages.
	 * It is only for the .txt or .csv file.
	 */
	public ArrayList<HashMap<String, String>> messageFiler(String filePath, List<String> files) {
		ArrayList<HashMap<String, String>> result = this.result;
		
		for(int i = 0 ; i<files.size(); i ++){
			DataReader dataReader = new DataReader();
			String text = dataReader.getText(filePath + "\\" + files.get(i), "UTF-8");
			String extension = files.get(i).split("\\.")[1];
			
			if (extension.equals("csv")) {
				System.out.println(files.get(i) + "Chat message");
				csvFilter(text);
			} else if (extension.equals("txt")) {
				System.out.println(files.get(i) + "Chat message");
				textFilter(text);
			}else{
				System.out.println(files.get(i) + " It is not .csv or .txt file.");
				System.out.println();
			}
		}

		return result;
	}

	/**
	 * This is for reading csv files and filter for csv files.
	 * You can check duplicate message.
	 * Refer my single line comment.
	 * @param text
	 * 
	 */
	public ArrayList<HashMap<String, String>> csvFilter(String text) {
		String photo = "Photo";
		ArrayList<HashMap<String, String>> result = this.result;
		String[] line = text.split("\n"); //split the message by \n

		for (int i = 0; i < line.length; i++) {// this loop is for reading data(.csv)
			HashMap map = new HashMap<>();			
			System.out.println(line[i]);
			
			if (line[i].length() > 20) { //the kakaotalk message in CSV file is longer than 20(when split with line)
				if (isDateCSVValid(line[i].substring(0, 19))) {//The 0-19 area indicates the date. So i checked again whether it indicates date or not.
					boolean check = true;
					String[] split = line[i].split(","); //when split with "," we can get 3parts(date, username, message)
					String date = split[0];
					date = date.substring(0,16);
					String userName = split[1].substring(1, split[1].length() - 1);//delete the punctuation(" ")
					String message = split[2].substring(1, split[2].length() - 1);//delete the punctuation(" ")
					
					if (message.equals(photo)) check = false; //photo message regarded as redundant message.
                 
					for (int j = 0; j < result.size(); j++) {// it is for the duplicate message.
						if (result.get(j).get("date").toString().equals(date)) {
							if (result.get(j).get("userName").toString().equals(userName)) {
								if (result.get(j).get("message").toString().equals(message)) {
									// when date,userName, message is all the same, do not count it again.
									check = false;
								}
							}
						}
					}

					if (check) {
						map.put("date", date);
						map.put("userName", userName);
						map.put("message", message);
						result.add(map);
					}

				}
			}
		}

		return result;
	}
	
	/**
	 * This is for reading txt files and filter for txt files.
	 * You can check duplicate message.
	 * Refer my single line comment.
	 * @param text
	 * 
	 */

	public ArrayList<HashMap<String, String>> textFilter(String text) {
		ArrayList<HashMap<String, String>> result = this.result;
		String photo = "사진";
		String[] line = text.split("\n"); //split it by \n
		for (int i = 0; i < line.length; i++) {// loop for reading text
			HashMap map = new HashMap<>();
			
			System.out.println(line[i]);
			if(line[i].length()>=48){//the length of date is 48
				if(isDateTXTValid(line[i].substring(16, line[i].length()-16))){//I check it again whether it is date format or not
					txtDate = line[i].substring(16, line[i].length()-16); //put the area of date on static variable.
					String yyyy = txtDate.substring(0, txtDate.indexOf('년'));
					String mm = txtDate.substring(txtDate.indexOf('년')+2, txtDate.indexOf('월'));
					String dd = txtDate.substring(txtDate.indexOf('월')+2,txtDate.indexOf('일'));
					txtDate = yyyy + "-" + convertMM(mm) + "-" + dd; //convertMM method(refer the method below) is needed because January~September is shown 1~9 not 01~09. 
							//make it [YYYY-MM-DD] form
				}
			}
			if(line[i].indexOf("[") >= 0 
				&& line[i].indexOf("]") >=0
				&& line[i].indexOf("[", line[i].indexOf("[") + 1) >=0 
				&& line[i].indexOf("]", line[i].indexOf("]") + 1) >=0){// this conditional sentence is for the message
				boolean check = true;
				
				String userName = line[i].substring(line[i].indexOf("[") +1 ,line[i].indexOf("]"));// first [ ] section in message.(name part)
				String AAHHMM = line[i].substring(line[i].indexOf("[", line[i].indexOf("[") + 1) +1 
								,line[i].indexOf("]", line[i].indexOf("]") + 1)); // second [ ] section in message.(date part)
				
				AAHHMM = convertHH(AAHHMM); //convert the form of date(refer the method below)
				
				String date = txtDate + " " + AAHHMM;
				String message = line[i].substring(line[i].indexOf("]", line[i].indexOf("]") + 1) + 2 
												, line[i].length());
				if(message.equals(photo)) check = false;
				for (int j = 0; j < result.size(); j++) {// it is for checking duplicate message.
					if (result.get(j).get("date").toString().equals(date)) {
						if (result.get(j).get("userName").toString().equals(userName)) {
							if (result.get(j).get("message").toString().equals(message)) {
								
								check = false;
							}
						}
					}
				}

				if (check) {
					map.put("date", date);
					map.put("userName", userName);
					map.put("message", message);
					result.add(map);
				}
			}
		}
		return result;
	}
	
	/**
	 * It is method for checking validity of date in CSV file
	 * @param date
	 * @return boolean
	 */
	
	public static boolean isDateCSVValid(String date) {
		try {
			DateFormat df = new SimpleDateFormat(DATE_CSV_FORMAT);
			df.setLenient(false);
			df.parse(date);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
	
	/**
	 * It is method for checking validity of date in TXT file
	 * @param date
	 * @return boolean
	 */
	
	public static boolean isDateTXTValid(String date) {
		try {
			DateFormat df = new SimpleDateFormat(DATE_TXT_FORMAT);
			df.setLenient(false);
			df.parse(date);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
	/**
	 * It is method for convert "오전 1시  33분 to 01:33"
	 * To make the date form of the CSV file and the TXT file identical for checking duplicate message.
	 * @param AAHHMM
	 * @return
	 */
	
	public static String convertHH(String AAHHMM){
		String result = "";
		
		String[] AAHHMMsplit = AAHHMM.split(":");
		
		String[] AAHHsplit = AAHHMMsplit[0].split(" ");
		
		String aa = AAHHsplit[0];
		String hh = AAHHsplit[1];
		String mm = AAHHMMsplit[1];
		
		if(aa.equals("오전")){
			if(hh.equals("1")){
				result = "01:" + mm;
			}else if(hh.equals("2")){
				result = "02:" + mm;
			}else if(hh.equals("3")){
				result = "03:" + mm;
			}else if(hh.equals("4")){
				result = "04:" + mm;
			}else if(hh.equals("5")){
				result = "05:" + mm;
			}else if(hh.equals("6")){
				result = "06:" + mm;
			}else if(hh.equals("7")){
				result = "07:" + mm;
			}else if(hh.equals("8")){
				result = "08:" + mm;
			}else if(hh.equals("9")){
				result = "09:" + mm;
			}else if(hh.equals("10")){
				result = "10:" + mm;
			}else if(hh.equals("11")){
				result = "11:" + mm;
			}else if(hh.equals("12")){
				result = "00:" + mm;
			}
		}else if(aa.equals("오후")){
			if(hh.equals("1")){
				result = "13:" + mm;
			}else if(hh.equals("2")){
				result = "14:" + mm;
			}else if(hh.equals("3")){
				result = "15:" + mm;
			}else if(hh.equals("4")){
				result = "16:" + mm;
			}else if(hh.equals("5")){
				result = "17:" + mm;
			}else if(hh.equals("6")){
				result = "18:" + mm;
			}else if(hh.equals("7")){
				result = "19:" + mm;
			}else if(hh.equals("8")){
				result = "20:" + mm;
			}else if(hh.equals("9")){
				result = "21:" + mm;
			}else if(hh.equals("10")){
				result = "22:" + mm;
			}else if(hh.equals("11")){
				result = "23:" + mm;
			}else if(hh.equals("12")){
				result = "12:" + mm;
			}
		}
		return result;
	}
	
	
	/**
	 * This is method for changing form of month
	 * It is also to make the date form of the CSV file and the TXT file identical for checking duplicate message.
	 * @param mm
	 * @return String
	 */
	public static String convertMM(String mm){
		String result = "";

		int month = Integer.parseInt(mm);

		if(month < 10){
			result = "0"+ mm;
		}else{
			result = mm;
		}

		return result;
	}


	/**
	 * This is method to get result.
	 * It is also 
	 * @return result
	 */
	public ArrayList<HashMap<String, String>> getResult() {
		return result;
	}
}
