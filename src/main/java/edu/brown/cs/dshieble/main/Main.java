package edu.brown.cs.dshieble.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import edu.brown.cs.dshieble.finalproject.GUIManager;
import edu.brown.cs.dshieble.finalproject.MarkovManager;
import edu.brown.cs.qc14.parser.Parser;

public class Main {
  // passing "wsj2-21.blt" as args[0]
  public static void main(String[] args) {
    GUIManager.makeGUI();
	
//
//    
//
//      Parser parser = new Parser();
//      String[] terminals = "i have".split(" ");
//          //"i believe that she had met with considerable success .".split(" ");
//      System.out.println("parse into parts: " + parser.parseSentence(terminals));
//      System.out.println("JSON string: " + parser.toTreeData());
//      //System.out.println("regular parsing " + parser.normalParsing(terminals));
      
      /*
       * parseSentence method takes a string of sentence (punctuations and words separated by space)
       * returns its parsing in String
       * in Parser.java, I set max_length of input sentence as 25. you may change it.
       * String parsing = parser.parseSentence("replace this with whatever string");
       * 
       * parse.toTreeData() will return the Json string of the latest parsing.
       * it must be called immediately after parse.parseSentence().
      **/
      
      /*
      try {
        parser.parse(args[1], args[2]);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }*/
    
  }
}
//      
//      //MARKOV STUFF FOR DEMO
//    } else if (args[0].equals("2")) {
//      //./run 2 faust.txt crime keys lamp thunder spirits be me the
//      // assert args.length >= 3;
//      // String filename = args[1];
//      // StringBuilder builder = new StringBuilder();
//      // try (BufferedReader fileReader = new BufferedReader(
//      //   new FileReader(filename))) {
//      //   String line = null;
//      //   while ((line = fileReader.readLine()) != null) {
//      //     builder.append(line);
//      //     builder.append(" ");
//      //   }
//      // } catch (IOException e) {
//      //   System.out.println("ERROR: File IO Error");
//      //   return;
//      // }
//      // String words = builder
//      //      .toString();
//      // String[] books = new String[] {words};
//      GUIManager.makeGUI();
//      //DATABASE STUFF FOR DEMO
//    } else if (args[0].equals("3")) {
//      System.out.println("DATABASE STUFF SHOULD BE HERE");
//    } else {
//      System.out.println("args[0] should be 1, 2 or 3");
//    }



////Dan's mainline
//public final class Main {
//
//
///**
// * Prevents this class from being instantiated.
// */
//private Main() {
//
//}
//
///**
// * Mainline of code. Parses user input and finds path between actors.
// * @param args CL args
// */
//public static void main(final String[] args) {
//  System.out.println("working");
//  //GUIManager.makeGUI();
//  String[] text = {"this is the first book. Its a fun book. Oh, it is so good.", 
//                   "This is the second book. Man this one sucks. It is really not fun at all."};
//  String[] pw = {"fun, its"};
//}
//  //gui inputs
//}