package edu.brown.cs.qc14.parser;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;


public class Main {
	
	public static void main(String[] args) {
		Parser parser = new Parser();
		parser.buildRules(args[0]);
		try {
			parser.parse(args[1], args[2]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
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

