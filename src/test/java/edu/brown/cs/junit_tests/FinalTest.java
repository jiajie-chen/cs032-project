package edu.brown.cs.junit_tests;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
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
import static org.junit.Assert.*;

import com.google.common.collect.HashMultiset;


public class FinalTest {

  public boolean nearlyEqual(double n1, double n2) {
    return Math.abs(n1 - n2) < 1;
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
  public void markovManagerTest() {
    String[] text = new String[] {
        "this sentence has a lot of words. words are fun",
        "this only has one sentence. Actually I lied."};
    String[] pw = new String[] {"fun", "lied"};
    MarkovManager man = new MarkovManager(text, pw);
    List<String> c = man.getCandidates();
    System.out.println(Arrays.toString(c.toArray()));
    assertTrue(c.size() == 2);
    for (String s : c) {
      assertTrue(!c.contains(pw[0]));
      assertTrue(!c.contains(pw[1]));
    }
  }
  
  
  
  
 // System.out.println(Arrays.toString(c.toArray()));

}
