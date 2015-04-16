package edu.brown.cs.qc14.parser;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;


public class Main {
	// passing "wsj2-21.blt" as args[0]
	public static void main(String[] args) {
		Parser parser = new Parser();
		parser.buildRules(args[0]);
		// parseSentence method takes a string of sentence (punctuations and words separated by space)
		// returns its parsing in String
		// in Parser.java, I set max_length of input sentence as 25. you may change it.
		String parsing = parser.parseSentence("replace this with whatever string");
		/*try {
			parser.parse(args[1], args[2]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/
	}
}


// //Dan's mainline
//public final class Main {
//
//
//  /**
//   * Prevents this class from being instantiated.
//   */
//  private Main() {
//
//  }
//
//  /**
//   * Mainline of code. Parses user input and finds path between actors.
//   * @param args CL args
//   */
//  public static void main(final String[] args) {
//    System.out.println("working");
//    //GUIManager.makeGUI();
//    String[] text = {"this is the first book. Its a fun book. Oh, it is so good.", 
//                     "This is the second book. Man this one sucks. It is really not fun at all."};
//    String[] pw = {"fun, its"};
//  }
//    //gui inputs
//}

