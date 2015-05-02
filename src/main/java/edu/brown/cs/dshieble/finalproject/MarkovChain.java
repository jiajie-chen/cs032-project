package edu.brown.cs.dshieble.finalproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset.Entry;

/**
 * This class represents a markov chain that stores bigram and
 * unigram frequencies
 * @author dshieble
 *
 */
public class MarkovChain {

  /**
   * the bigram hash
   */
  Map<String, List<String>> bigram;

  /**
   * the unigram hash
   */
  HashMultiset<String> unigram;

//  /**
//   * stores all mapped to values
//   */
//  List<String> allValues;

  /**
   * constructor just initialized the hashes - we need to add words manually
   */
  public MarkovChain() {
    bigram = new Hashtable<String, List<String>>();
    unigram = HashMultiset.create();
    //allValues = new ArrayList<String>();
  }  

  /**
  *
  * @param word - the previous word
  * @return a random selection of next word, weighted by bigram
  */
 public String getNextWord(String word) {
   Set<String> keys = unigram.elementSet();
   assert keys.size() > 0;
   Random rand = new Random();
   if (!bigram.containsKey(word)) {
     return keys.toArray(new String[keys.size()])[rand.nextInt(keys.size())];
   } else {

     List<String> l = bigram.get(word);
     int s = l.size();
//     System.out.println(word);
//     System.out.println(Arrays.toString(l.toArray()));
     return l.get(rand.nextInt(s));
   }
 }

  /**
  *
  * @param sentenceArray - the sentence, split on spaces, that we want to add
  */
  public void addSentence(String[] sentenceArray) {
    //System.out.println(word);
    //System.out.println(Arrays.toString(sentenceArray));
    //unigram.put(sentenceArray[0], unigram.get(sentenceArray[0] + 1));
    for (int i = 0; i < sentenceArray.length; i++) {
      unigram.add(sentenceArray[i]);
      if (i > 0) {
        if (!bigram.containsKey(sentenceArray[i - 1])) {
          bigram.put(sentenceArray[i - 1], new ArrayList<String>());
          //System.out.println(Arrays`.toString(bigram.keySet().toArray()));
        }
//        if (sentenceArray[i - 1].equals("Words")) {
//          System.out.println(Arrays.toString(sentenceArray));
//        }

        bigram.get(sentenceArray[i - 1]).add(sentenceArray[i]);
      }
    }
  }

  /**
   * tries n times to generate a string within the min and max length. 
   * If it cannot, it returns "".
   * @param min minimum length
   * @param max maximum length
   * @param start the start word (not included)
   * @param end the last word (not included)
   * @return the string
   */
  public List<String> makeSentenceFragment(int min, int max,
      String start, String end, int numTries) {
    assert min > 0;
    assert max >= min;
    //make the string starting at first word and ending at last
    List<String> output = new ArrayList<String>();
    if (unigram.elementSet().size() == 0) {
      return output;
    }
    for (int i = 0; i < numTries; i ++) {
      output = new ArrayList<String>();
      output.add(getNextWord(start));
      while (output.size() <= max) {
        String n = getNextWord(output.get(output.size() - 1));
//        System.out.println(output.get(output.size() - 1));
//        System.out.println(n);
        if ((end == null || n.equals(end)) && output.size() >= min ) {
          return output;
        } else {
          output.add(n);
        }
      }
    }
    //make the string starting at first word
//    for (int i = 0; i < numTries; i ++) {
//      output = new ArrayList<String>();
//      output.add(getNextWord(start));
//      while (output.size() <= max) {
//        String n = getNextWord(output.get(output.size() - 1));
//        if (output.size() >= min) { 
//          if (bigram.containsKey(n))  {
//            if (bigram.get(n).contains(end)) {
//              return output;
//            }
//          }
//        }
//        output.add(n);
//      }
//    } 
    output = new ArrayList<String>();
    //output.add(makeRandomString(min));
    return output;
  }

  /**
  * @param n the length of the string that we are making
  * @return A random, awesome, string of words
  */
  public String makeRandomString(int n, String prev) {
    String[] output = new String[n];
    for (int i = 0; i < n; i++) {
      output[i] =  getNextWord(prev);
      prev = output[i];
      int l = prev.length();
      if (output[i].length() > 0
                      && (output[i].equals("i")
                      || prev.substring(l - 1).equals("."))) {
        output[i] = output[i]
           .substring(0, 1)
           .toUpperCase()
           .concat(output[i].substring(1));
      }
    }
    StringBuilder b = new StringBuilder();
    for (int i = 0; i < output.length; i++) {
      String str = output[i];
      b.append(" ");
      b.append(str);
    }
    return b.toString().trim();
  }


 /**
  * Simple Getter.
  * @return the bi hash
  */
 public Map<String, List<String>> getBigramHash() {
   return bigram;
 }

 /**
  * Simple Getter.
  * @return the uni hash
  */
 public HashMultiset<String> getUnigramHash() {
   return unigram;
 }

 public void print(Collection c) {
   System.out.println(Arrays.toString(c.toArray()));

 }

 public int size() {
   return unigram.elementSet().size();
 }
}
