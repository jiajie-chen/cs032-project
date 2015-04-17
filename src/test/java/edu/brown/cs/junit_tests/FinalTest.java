package edu.brown.cs.junit_tests;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.brown.cs.dshieble.finalproject.*;
import edu.brown.cs.qc14.parser.Parser;
import static org.junit.Assert.*;

import com.google.common.collect.HashMultiset;


public class FinalTest {

	/*
  public boolean nearlyEqual(double n1, double n2) {
    return Math.abs(n1 - n2) < 1;
  }
  
  public void print(Collection c) {
    System.out.println(Arrays.toString(c.toArray()));

  }

  @Test
  public void testTest() {
    assertTrue(1 == 1);
  }

  @Test
  public void markovTest1() {
    //add markov chain tests here
    MarkovChain m = new MarkovChain();
    String[] sentence1 = {"blah", "blah", "hey", "yeah"};
    m.addSentence(sentence1);
    HashMultiset<String> u = m.getUnigramHash();
    assertTrue(u.elementSet().size() == 3);
    assertTrue(u.count("blah") == 2);
    assertTrue(u.count("hey") == 1);
  }

  @Test
  public void markovTest2() {
    String[] words = {"uh", "huh", "uh", "huh", "uh", "oh", "uh"};
    MarkovChain m = new MarkovChain();
    m.addSentence(words);
    Map<String, List<String>> b = m.getBigramHash();
    //System.out.println(b.keySet().size());
    assertTrue(b.size() == 3);
    assertTrue(b.get("uh").get(0) == "huh");
    assertTrue(b.get("uh").get(2) == "oh");
    
  }
  
  @Test
  public void markovTest3() {
    String[] words = {"uh", "huh", "uh", "huh", "uh", "oh", "uh"};
    MarkovChain m = new MarkovChain();
    m.addSentence(words);
    String n;
    for (int i = 0; i < 100; i ++) {
      assertTrue(m.getNextWord("oh").equals("uh"));
      n = m.getNextWord("uh");
      assertTrue(n.equals("huh") || n.equals("oh"));
    }
  }
  
  @Test
  public void markovManagerTest1() {
    String[] text = new String[] {
        "This sentence has a lot of words. Words are fun",
        "This only has one sentence. Actually I lied."};
    String[] pw = new String[] {"fuN", "Lied"};
    MarkovManager man = new MarkovManager(text, pw);
    List<String> c = man.getCandidates();
    //System.out.println(Arrays.toString(c.toArray()));
    assertTrue(c.size() == 4);
//    for (String s : c) {
//      assertTrue(!c.contains(pw[0]));
//      assertTrue(!c.contains(pw[1]));
//    }
  }
  
  @Test
  public void markovManagerTest2() {
    String[] text = new String[] {
        "This sentence has a lot of words. Words are only",
        "This only has one sentence. Actually I lied.",
        "This File is kind of long. It starts with more words. Yay, I love words",
        "Oh my god so many letters. How many words can you place if you love placing words? Does that make sense?"};
    String[] pw = new String[] {"love", "Only", "This", "file"};
    MarkovManager man = new MarkovManager(text, pw);
    Hashtable<String, MarkovChain> wordToMarkov = man.getHash();
    MarkovChain m1 = wordToMarkov.get("love");
    Map<String, List<String>> big1 = m1.getBigramHash();
    for (int i = 0; i < 100; i++) {
      List<String> frag = m1.makeSentenceFragment(2, 5, "love", "place", 100);
      assertTrue(frag.get(0).equals("words") || frag.get(0).equals("placing"));
      assertTrue(frag.get(frag.size() - 1).equals("you"));
    }
    
    MarkovChain m2 = wordToMarkov.get("only");
    Map<String, List<String>> big2 = m2.getBigramHash();
    for (int i = 0; i < 100; i++) {
      List<String> frag = m2.makeSentenceFragment(2, 5, "Words", "sentence", 100);
      print(frag);
      assertTrue(frag.get(0).equals("are"));// || frag.get(0).equals("placing"));
      assertTrue(frag.get(frag.size() - 1).equals("one"));
    }
    
    for (int i = 0; i < 100; i++) {
      List<String> frag = man.makeSentenceFragment(2, 5, "This", "kind", 100);
      assertTrue(frag.get(0).equals("File"));// || frag.get(0).equals("placing"));
      assertTrue(frag.get(frag.size() - 1).equals("is"));    
    }
  }
  
  @Test
  public void markovManagerTest3() {
    String[] text = new String[] {
        "This sentence has a lot of words. Words are only",
        "This only has one sentence. Actually I lied.",
        "This File is kind of long. It starts with more words. Yay, I love words",
        "Oh my god so many letters. How many words can you place if you love placing words? Does that make sense?"};
    String[] pw = new String[] {"love", "Only", "This", "file"};
    MarkovManager man = new MarkovManager(text, pw);
    for (int i = 0; i < 100; i++) {
      man.generateSentence(10); //ERROR TEST
    }
    
  }
  
  @Test
  public void parseTester() {
    String sentence = "This is a good sentence to test parsing.";
    Parser P = new Parser();
    //System.out.println(P.parseSentence(sentence));

  } */
  
  
 // System.out.println(Arrays.toString(c.toArray()));
  //
//try {
//  System.out.println(man.generateSentence(10));
//} catch (Exception e) {
//  e.printStackTrace(); //replace this with an informative message 
//  assertTrue(false);
//}
}
