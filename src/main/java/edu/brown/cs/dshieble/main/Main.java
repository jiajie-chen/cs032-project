package edu.brown.cs.dshieble.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import edu.brown.cs.dshieble.finalproject.MarkovManager;
import edu.brown.cs.qc14.parser.Parser;

public class Main {
  // passing "wsj2-21.blt" as args[0]
  public static void main(String[] args) {

    //PARSER STUFF FOR DEMO
    if (args[0].equals("1")) {
      Parser parser = new Parser();
      parser.buildRules("src/main/java/edu/brown/cs/qc14/parser/wsj2-21.blt");
      String[] terminals = 
          "any change of control in farmers needs approval of the insurance commissioners in the nine states .".split(" ");
      System.out.println("****  " + parser.parseSentence(terminals));
      System.out.println(parser.testParsing(terminals));
      // parseSentence method takes a string of sentence (punctuations and words separated by space)
      // returns its parsing in String
      // in Parser.java, I set max_length of input sentence as 25. you may change it.
      //String parsing = parser.parseSentence("replace this with whatever string");
      /*try {
        parser.parse(args[1], args[2]);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }*/
      
      
      //MARKOV STUFF FOR DEMO
    } else if (args[0].equals("2")) {
      assert args.length >= 3;
      String filename = args[1];
      StringBuilder builder = new StringBuilder();
      try (BufferedReader fileReader = new BufferedReader(
        new FileReader(filename))) {
        String line = null;
        while ((line = fileReader.readLine()) != null) {
          builder.append(line);
          builder.append(" ");
        }
      } catch (IOException e) {
        System.out.println("ERROR: File IO Error");
        return;
      }
      String words = builder
           .toString();
      String[] books = new String[] {words};
      MarkovManager man = new MarkovManager(books,
          Arrays.copyOfRange(
          args, 2, args.length));
      for (int i = 0; i < 10; i++) {
        System.out.println(man.generateSentence(10));
      }
      
      
      
      
      
      //DATABASE STUFF FOR DEMO
    } else if (args[0].equals("3")) {
      System.out.println("DATABASE STUFF SHOULD BE HERE");
    } else {
      System.out.println("args[0] should be 1, 2 or 3");
    }
  }
}


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