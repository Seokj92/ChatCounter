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
		String outputFileName = "output";//you can change the name of this file. ex)output => args[2](you can put the name in the run configurations)
		
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

/*
* It should be fixed in order for it to work.

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;



	public void run(String[] args) {
		Options options = createOptions();

		if(parseOptions(options, args)){
			if (help){
				printHelp(options);
				return;
			}


			System.out.println("You provided \"" + path + "\" as the value of the option i");


			if(verbose) {

				System.out.println("Your program is terminated. );
			}
		}
	}

	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {

			CommandLine cmd = parser.parse(options, args);

			inputPath = cmd.getOptionValue("i");
			outputPath = cmd.getOptionValue("o");
			help = cmd.hasOption("h");

		} catch (Exception e) {
			printHelp(options);
			return false;
		}

		return true;
	}


	private Options createOptions() {
		Options options = new Options();


		options.addOption(Option.builder("i").longOpt("path")
				.desc("Set a path of a directory that contains input files")
				.hasArg()
				.argName("Directory path")
				.required()
				.build());


		options.addOption(Option.builder("o").longOpt("pathtobesaved")
				.desc("Set a path of a directory to save the file")
				.hasArg()    
				.argName("Directory path")
				.required()
				.build());


		options.addOption(Option.builder("h").longOpt("help")
		        .desc("Help")
		        .build());

		return options;
	}

	private void printHelp(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String header = "Message Counter";
		String footer ="\nPlease report issues at https://github.com/Seokj92/ChatCounter";
		formatter.printHelp("CLIExample", header, options, footer, true);
	}

}
 */
