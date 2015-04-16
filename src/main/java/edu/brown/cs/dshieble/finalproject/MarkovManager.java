package edu.brown.cs.dshieble.finalproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

/**
 *This class handles the markov chain, the synonym parsing, etc
 *
 ** Preparation
 * -For each priority word:
 * --generate a hashtable that maps the word to a markov chain built only 
 * from sentences that contain that word.
 * --generate a hashtable that maps the word to a unigram hash built from
 * sentences that contain that word
 * -generate a list of sentences that contain no priority words

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
   */
  private Hashtable<String, MarkovChain> wordToMarkov;
  /**
   * possible sentences
   */
  private List<String> candidateSentences;
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
    candidateSentences = new ArrayList<String>();
    wordToMarkov = new Hashtable<String, MarkovChain>();
    Boolean hasPriority;
    for (int i = 0; i < books.length; i++) {
      String[] sentences = books[i].split("\\.");
      //System.out.println(Arrays.toString(sentences));
      for (int j = 0; j < sentences.length; j++) {
        String sentence = sentences[j].replaceAll(" +", " ");
        String[] sentenceArray = sentence.split(" ");
        hasPriority = false;
        for (String word : priorityWords) {
          if (sentence.contains(word)) {
            hasPriority = true;
            if (!wordToMarkov.containsKey(word)) {
              wordToMarkov.put(word, new MarkovChain());
            }
            wordToMarkov.get(word).addSentence(sentenceArray);
          }
        }
        if (!hasPriority) {
          candidateSentences.add(sentence);
        }
      }
    }
  }

  /**
   * the major method of the class. 
   * @return a sentence that matches all of the facets.
   */
  public String generateSentence() {
    String sentence = getRandomSentence();
    //splitSentence(sentence)
//    Random rand = new Random();
//    int start = 3;//Math.floor(candidateSentences.size());
//    int end = 5;
    return "";
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


