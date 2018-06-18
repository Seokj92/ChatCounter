/*
package edu.handong.csee.java.hw3;


import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class Apachecommonscli {
	
	String inputFilePath;
	String outputFilePath;
	boolean verbose;
	boolean help;

	public static void main(String[] args) {

		Apachecommonscli myRunner = new Apachecommonscli();
		myRunner.run(args);

	}
	
		public void run(String[] args) {
			Options options = createOptions();

			if(parseOptions(options, args)){
				if (help){
					printHelp(options);
					return;
				}


				System.out.println("You provided \"" + inputFilePath + "\" as the value of the option i");


				if(verbose) {

					System.out.println("Your program is terminated." );
				}
			}
		}

		private boolean parseOptions(Options options, String[] args) {
			CommandLineParser parser = new DefaultParser();

			try {

				CommandLine cmd = parser.parse(options, args);

				inputFilePath = cmd.getOptionValue("i");
				outputFilePath = cmd.getOptionValue("o");
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
			String footer ="\nPlease report issues at JS";
			formatter.printHelp("CLIExample", header, options, footer, true);
		}
		
		public String inputFilePath() {
			return inputFilePath;
		}
		
		public String outputFilePath() {
			return outputFilePath;
		}


	}

*/
