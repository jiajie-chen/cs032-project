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

import static org.junit.Assert.*;

import com.google.common.collect.HashMultiset;

import edu.brown.cs.dshieble.finalproject.MarkovChain;
import edu.brown.cs.dshieble.finalproject.MarkovManager;
import edu.brown.cs.qc14.parser.Parser;


public class FinalTest {

	
  public boolean nearlyEqual(double n1, double n2) {
    return Math.abs(n1 - n2) < 1;
  }
  
  public void print(Collection c) {
    System.out.println(Arrays.toString(c.toArray()));

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
     List<String[]> text = new ArrayList<String[]>();
     text.add(new String[] {"This sentence has a lot of words.", "Words are fun"});
     text.add(new String[] {"This only has one sentence.", "Actually I lied."});
     String[] pw = new String[] {"fuN", "Lied"};
     MarkovManager man = new MarkovManager(text, pw);
     List<String> c = man.getCandidates();
     //System.out.println(Arrays.toString(c.toArray()));
     assertTrue(c.size() == 2);
  //    for (String s : c) {
  //      assertTrue(!c.contains(pw[0]));
  //      assertTrue(!c.contains(pw[1]));
  //    }
   }
 
   @Test
   public void markovManagerTest2() {
     List<String[]> text = new ArrayList<String[]>();
     text.add(new String[] {"This sentence has a lot of words.", "Words are only fun"});
     text.add(new String[] {"This only has one sentence.", "Actually I lied."});
     text.add(new String[] {"This File is kind of long.", "It starts with more words.", "Yay, I love words."});
     text.add(new String[] {"Oh my god so many letters.", "How many words can you place if you love placing words?", "Does that make sense?"});  
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
   
//     MarkovChain m2 = wordToMarkov.get("only");
//     Map<String, List<String>> big2 = m2.getBigramHash();
//     for (int i = 0; i < 100; i++) {
//       List<String> frag = m2.makeSentenceFragment(2, 5, "Words", "sentence", 100);
//       assertTrue(frag.get(0).equals("are"));// || frag.get(0).equals("placing"));
//       assertTrue(frag.get(frag.size() - 1).equals("one") || frag.get(frag.size() - 1).equals("fun"));
//     }
   
// // //   for (int i = 0; i < 100; i++) {
// // //     List<String> frag = man.makeSentenceFragment(2, 5, "This", "kind", 100);
// // //     print(frag);
// // //
// // //     //assertTrue(frag.get(0).equals("File"));// || frag.get(0).equals("placing"));
// // //     assertTrue(frag.get(frag.size() - 1).equals("is"));    
// // //   }
   }
 
   @Test
   public void markovManagerTest3() {
     List<String[]> text = new ArrayList<String[]>();
     text.add(new String[] {"This sentence has a lot of words.", "Words are fun"});
     text.add(new String[] {"This only has one sentence.", "Actually I lied."});
     text.add(new String[] {"This File is kind of long.", "It starts with more words.", "Yay, I love words"});
     text.add(new String[] {"Oh my god so many letters.", " How many words can you place if you love placing words?", " Does that make sense?"});  
     String[] pw = new String[] {"love", "Only", "This", "file"};
     MarkovManager man = new MarkovManager(text, pw);
     for (int i = 0; i < 5; i++) {
       //System.out.println(man.generateSentence(10));
       man.generateSentence(10); //ERROR TEST, and can also use to print
     }
   }

   @Test
   public void markovManagerTest4() {
     List<String[]> text = getSherlockText();
     String[] pw = new String[] {"past", "disappointment, government, appointment, married"};
     MarkovManager man = new MarkovManager(text, pw);
     for (int i = 0; i < 5; i++) {
       String s = man.generateSentence(10);
       //System.out.println(s);
     }
   }  
 
 
  @Test
  //TODO: Fill in this test
  public void splitSentenceParseTest() {
    String sentence1 = 
        "I believe that she has met with considerable success .";
    String[] sentenceArray = sentence1
        .toLowerCase()
        .replaceAll("[^a-z, -]", "")
        .split(" ");
    Parser P = new Parser();
    List<ArrayList<String>> parsed = P.parseSentence(sentenceArray);  
 //    for (ArrayList<String> a : parsed) {
 //      print(a);
 //    }
    List<String[]> text = getSherlockText();
    String[] pw = new String[] {"past", "disappointment, government, appointment, married"};
    MarkovManager man = new MarkovManager(text, pw);
    int[] indices = man.splitSentenceParse(sentenceArray);
    for (int i = 0; i < 10; i ++) {
      indices = man.splitSentenceParse(sentenceArray);
      assertTrue(indices[0] == 0 || indices[0] == 3 || indices[0] == 6);
      assertTrue(indices[1] == 2 || indices[1] == 5 || indices[1] == 8);
      //System.out.println(man.recombineSentence(sentence1, -2));
    }
  }
  
  public List<String[]> getSherlockText() {
    String s = "And thus was solved the mystery of the sinister house with the " +
        "copper beeches in front of the door. Mr. Rucastle survived, but " +
        "was always a broken man, kept alive solely through the care of " +
        "his devoted wife. They still live with their old servants, who " +
        "probably know so much of Rucastle's past life that he finds it " +
        "difficult to part from them. Mr. Fowler and Miss Rucastle were " +
        "married, by special license, in Southampton the day after their " +
        "flight, and he is now the holder of a government appointment in " +
        "the island of Mauritius. As to Miss Violet Hunter, my friend " +
        "Holmes, rather to my disappointment, manifested no further " +
        "interest in her when once she had ceased to be the centre of one " +
        "of his problems, and she is now the head of a private school at " +
        "Walsall, where I believe that she has met with considerable success.";
    String[] sArray = s.split("\\.");
    List<String[]> l = new ArrayList<String[]>();
    l.add(sArray);
    return l;
  }
  
  
 // System.out.println(Arrays.toString(c.toArray()));
  //
//try {
//  System.out.println(man.generateSentence(10));
//} catch (Exception e) {
//  e.printStackTrace(); //replace this with an informative message 
//  assertTrue(false);
//}
}

//String sentence2 = 
//  "The mystery of the sinister house was solved .";
//String[] sentenceArray2 = sentence2
//  .toLowerCase()
//  .replaceAll("[^a-z, -]", "")
//  .split(" ");
//List<ArrayList<String>> parsed2 = P.parseSentence(sentenceArray2);
//for (ArrayList<String> a : parsed2) {
//print(a);
//}
//int[] indices2 = man.splitSentenceParse(sentenceArray);
//for (int i = 0; i < 10; i ++) {
//indices2 = man.splitSentenceParse(sentenceArray2);
////      assertTrue(indices[0] == 0 || indices[0] == 3 || indices[0] == 6);
////      assertTrue(indices[1] == 2 || indices[1] == 5 || indices[1] == 8);
//// System.out.println(man.recombineSentence(sentence1, -2));

//}
