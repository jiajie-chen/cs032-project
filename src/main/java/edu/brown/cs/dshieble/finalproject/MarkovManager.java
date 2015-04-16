package edu.brown.cs.dshieble.finalproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import com.google.common.collect.ObjectArrays;
/**
 *This class handles the markov chain, the synonym parsing, etc
 *
 ** Preparation
 * -For each priority word:
 * --generate a hashtable that maps the word to a markov chain built only 
 * from sentences that contain that word.
 * --generate a hashtable that maps the word to a unigram hash built from
 * sentences that contain that word
 * -generate a list of sentences of appropriate length

 *
 * Algorithm: 
 * -Pick a random sentence that does not contain any priority words
 * as the starter sentence. Repeat the following process n times:
 * -break the sentence into pieces
 * -remove a section of the sentence
 * -designate the words flanking that section of the sentence as sticky words
 * -pick the priority word whose unigram hash has the best representation of
 * the sum of the sticky words, and at least one instance of the first sticky word
 * -run the method: run markov chain until a string is generated that
 * connects the first and second sticky words, of an appropriate length
 * 
 * --DEFAULT -> If none of the priority words work for any of the chosen sentences,
 * the algorithm just generated a markov sentence
 * @author dshieble
 *
 */
public class MarkovManager {

  /**
   * map from priority word to a map from word to markov chain
   * ALL WORDS ARE STORED IN LOWER CASE
   */
  private Hashtable<String, MarkovChain> wordToMarkov;

  /**
   * possible sentences
   */
  private List<String> candidateSentences;

  /**
   * priority words, in order
   */
  private String[] pW;
  
  /**
   * priority words, in order
   */
  //private boolean validated = false;

  private static final int numRepetitions = 3;
  private static final int FOUR = 4;
  private static final int FIVE = 5;

  /**
   * @param books - A list of strings, each string representing a book
   * @param priorityWords - the words in the chain that we weight more highly,
   * in order to preference
   * 
   */
  public MarkovManager(String[] books, String[] priorityWords) {
    //TODO: LOWERCASE EVERYTHING AND HANDLE CASING SEPERATELY
    //TODO: PRESERVE STARTING AND ENDING WORDS OF SENTENCES (by handling short sentence ssperately)
    pW = priorityWords;
    for (int i = 0; i < pW.length; i++) {
      pW[i] = pW[i].toLowerCase();
    }
    candidateSentences = new ArrayList<String>();
    wordToMarkov = new Hashtable<String, MarkovChain>();
    Boolean hasPriority;
    for (int i = 0; i < books.length; i++) {
      String[] sentences = books[i].split("[.!?]");
      //System.out.println(Arrays.toString(sentences));
      for (int j = 0; j < sentences.length; j++) {
        String sentence = sentences[j].replaceAll(" +", " ");
        String[] sentenceArray = sentence.split(" ");
        hasPriority = false;
        for (String word : pW) {
          //case insensitive regexp
          if (sentence.toLowerCase().contains(word)) {
//            System.out.println(word);
//
//            System.out.println(sentence);
          //if (sentence.matches("(?i).*" + word + ".*")) {
            //hasPriority = true;
            if (!wordToMarkov.containsKey(word)) {
              wordToMarkov.put(word, new MarkovChain());
            }
            wordToMarkov.get(word).addSentence(sentenceArray);
          }
        }
        if (sentenceArray.length >= 3) {
          candidateSentences.add(sentence);
        }
      }
    }
    if (candidateSentences.size() == 0) {
      System.out.println("WARNING: NO CANDIDATE SENTENCES");
    }
  }

  /**
   * Calls each markov chain in order to generate a sentence
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
    for (String word : pW) {
      List<String> sentence =
          wordToMarkov.get(word)
          .makeSentenceFragment(min, max, start, end, numTries);
      if (sentence.size() > 0) {
        return sentence;
      }
    }
    return new ArrayList<String>();
  }
  
  
  /**
   * the major method of the class. 
   * @param recombinations  - the number of recombinations
   * @return a sentence that matches all of the facets.
   */
  public String generateSentence(int recombinations) {
    String sentence = getRandomSentence();
    for (int i = 0; i < recombinations; i++) {
      sentence = recombineSentence(sentence);
    }
    while (sentence.length() < 2) {
      sentence = recombineSentence(sentence);
    }
    return sentence.substring(0, 1).toUpperCase()
        + sentence.substring(1)
        + ".";
  }

  /**
   * recombines a sentence - one iteration
   * @param sentence the sentence to recombine
   * @return a sentence that matches all of the facets.
   */
  public String recombineSentence(String sentence) {
    String[] sentenceArray = sentence.split(" ");
    int[] startEnd = splitSentence(sentenceArray);
    String startWord = sentenceArray[startEnd[0]];
    String endWord = sentenceArray[startEnd[1]];
    int len = startEnd[1] - startEnd[0];
    String[] startPart = Arrays.copyOfRange(
        sentenceArray, 0, startEnd[0] + 1);
    if (len == 0) {
      if (startEnd[0] == sentenceArray.length - 1) {
        List<String> frag = makeSentenceFragment(
            2, 2, startWord, null, 100);
        String outputSentence = combine(startPart, frag, new String[] {});
        return outputSentence;
      } else {
        return sentence;
      }
    }
    String[] endPart = Arrays.copyOfRange(
        sentenceArray, startEnd[1], sentenceArray.length);
    List<String> frag = makeSentenceFragment(
        Math.max(1,len - 2),
        Math.min(len + 2, sentenceArray.length),
        startWord, endWord, 100);
    String outputSentence = combine(startPart, frag, endPart);
    return outputSentence;
  }
  
  /**
   * generates indices of a sentence split - this method uses parser
   * @param sentenceArray to split
   * @return indices of words that are start and end to split on
   */
  private int[] splitSentence(String[] sentenceArray) {
    //System.out.println(sentenceArray.length);
    if (sentenceArray.length <= 3) {
      return new int[] {0, sentenceArray.length - 1};
    }
    Random rand = new Random();
    int start = rand.nextInt((int) Math.floor(sentenceArray.length/2));
    int end = start + 1 + rand.nextInt(Math.max(start + 1, sentenceArray.length - start - 1));
    assert end < sentenceArray.length;
    assert start >= 0;
    return new int[] {start, end};
  }

  /**
   * 
   * @param start start string
   * @param frag middle string
   * @param end string
   * @return the combination of the string arrays into a string joined by spaces
   */
  public String combine(String[] start, List<String> frag, String[] end) {
//    String[] outputSentence = 
//        ObjectArrays.concat(
//        ObjectArrays.concat(startPart, frag, String.class),
//        endPart, String.class);
    StringBuilder builder = new StringBuilder();
    for (String s : start) {
      builder.append(s + " ");
    }
    for (String s : frag) {
      builder.append(s + " ");
    }
    for (String s : end) {
      builder.append(s + " ");
    }
    return builder.toString().trim();
  }
  
  /**
   * 
   * @return a random element of candidateSentences
   */
  public String getRandomSentence() {
    Random rand = new Random();
    return candidateSentences.get(rand.nextInt(candidateSentences.size()));
  }

  /**
   * 
   * @return getter
   */
  public List<String> getCandidates() {
    return candidateSentences;
  }

  /**
   * 
   * @return getter
   */
  public Hashtable<String, MarkovChain> getHash() {
    return wordToMarkov;
  }
  
  
  
}

        
//        .toLowerCase()
//        .trim()
//        .replaceAll("[^a-z ]" , " ")
//        .replaceAll(" +", " ")
//        .split(" ");

    
//    //this.text = text;
//    bigramHash = new Hashtable<String, List<String>>();
//    //populate bigram hashes
//    for (int i = 0; i < text.length; i++) {
//      if (text[i].length() > 0) {
//        if (i > 0) {
//          if (!bigramHash.containsKey(text[i - 1])) {
//            List<String> l = new ArrayList<String>();
//            bigramHash.put(text[i - 1], l);
//          }
//          bigramHash.get(text[i - 1]).add(text[i]);
//        }
//      }
//    }
//  }



//  /**
//   *
//   * @param word - the previous word
//   * @return a random selection of next word, weighted by bigram
//   */
//  public String getNextWord(String word) {
//    Random rand = new Random();
//    if (!bigramHash.containsKey(word)) {
//      return text[rand.nextInt(text.length)];
//    } else {
//      List<String> l = bigramHash.get(word);
//      int s = l.size();
//      return l.get(rand.nextInt(s));
//    }
//  }


