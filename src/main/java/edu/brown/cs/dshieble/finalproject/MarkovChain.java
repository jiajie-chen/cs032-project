package edu.brown.cs.dshieble.finalproject;

import java.util.ArrayList;
import java.util.Arrays;
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
   Random rand = new Random();
   if (!bigram.containsKey(word)) {
     Set<Entry<String>> keys = unigram.entrySet();
     return keys.toArray(new String[keys.size()])[rand.nextInt(keys.size())];
   } else {
     List<String> l = bigram.get(word);
     int s = l.size();
     return l.get(rand.nextInt(s));
   }
 }

  /**
  *
  * @param sentenceArray - the sentence, split on spaces, that we want to add
  */
  public void addSentence(String[] sentenceArray) {
    //unigram.put(sentenceArray[0], unigram.get(sentenceArray[0] + 1));
    for (int i = 0; i < sentenceArray.length; i++) {
      unigram.add(sentenceArray[i]);
      if (i > 0) {
        if (!bigram.containsKey(sentenceArray[i - 1])) {
          bigram.put(sentenceArray[i - 1], new ArrayList<String>());
          //System.out.println(Arrays.toString(bigram.keySet().toArray()));
        }
        bigram.get(sentenceArray[i - 1]).add(sentenceArray[i]);
      }
    }
  }

  /**
  * @param n the length of the string that we are making
  * @return A random, awesome, string of words
  */
  public String makeRandomString(int n) {
    String[] output = new String[n];
    output[0] = "I";
    for (int i = 1; i < n; i++) {
      output[i] =  getNextWord(output[i - 1].toLowerCase());
      String prev = output[i - 1];
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
      if (!str.matches(".':;, ")) {
        b.append(" ");
      }
      b.append(str);
    }
    return b.toString();
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
 
 
}
