package edu.brown.cs.dshieble.finalproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import com.google.common.collect.ObjectArrays;

import edu.brown.cs.qc14.parser.Parser;
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
  private List<String> candidateSentencesPriority;
  
  /**
   * possible sentences
   */
  private List<String> candidateSentences;
  
  /**
   * priority words, in order
   */
  private String[] usedPW;

  /**
   * a sentence parser
   */
  private Parser p;

  /**
   * minimum sentence length
   */
  private static final int minLength = 4;

  /**
   * maximum sentence length
   */
  private static final int bigLength = 8;

  /**
   * maximum sentence length
   */
  private static final int maxLength = 25;
  
  /**
   * maximum sentence length
   */
  private static final int numTries = 5;
  
  
  /**
   * priority words, in order
   */
  //private boolean validated = false;

  private static final int numRepetitions = 3;
  private static final int maxTries = 1000;
  private static final int FIVE = 5;

  /**
   * @param books - A list of strings, each string representing a book
   * @param priorityWords - the words in the chain that we weight more highly,
   * in order to preference
   * 
   */
  public MarkovManager(List<String[]> books, String[] priorityWords) {
    candidateSentences = new ArrayList<String>();
    candidateSentencesPriority = new ArrayList<String>();

    wordToMarkov = new Hashtable<String, MarkovChain>();
    wordToMarkov.put(" ", new MarkovChain());
    p = new Parser();

    if (books.size() == 0) {
      System.out.println("ERROR: No Books Submitted");
      return;
    }
    String[] pW = priorityWords;
    for (int i = 0; i < pW.length; i++) {
      pW[i] = pW[i].toLowerCase();
      
    }
    Random rand = new Random();
    for (int i = 0; i < books.size(); i++) {
      String[] sentences = books.get(i);
      for (int j = 0; j < sentences.length; j++) {
        String sentence = sentences[j]
          .replaceAll(" +", " ")
          .replaceAll("[^A-Za-z0-9,;: -]", "")
          .trim();
        String[] sentenceArray = sentence.split(" ");
        boolean isPriority = false;
        for (String word : pW) {
          boolean contains = false;
          for (String s : sentenceArray) {
            if (s.toLowerCase().equals(word)) {
              contains = true;
              break;
            }
          }
          if (contains) {
            isPriority = true;
            if (!wordToMarkov.containsKey(word)) {
              wordToMarkov.put(word, new MarkovChain());
            }
            wordToMarkov.get(word).addSentence(sentenceArray);
          } else {
            wordToMarkov.get(" ").addSentence(sentenceArray);
          }
        }
        if (sentenceArray.length >= minLength
            && sentenceArray.length <= maxLength) {
          if (isPriority) {
            candidateSentencesPriority.add(sentence);
          } else {
            candidateSentences.add(sentence);
          }
        }
      }
    }
    if (candidateSentences.size() == 0 && candidateSentencesPriority.size() == 0) {
      System.out.println("WARNING: NO CANDIDATE SENTENCES");
      //System.out.println(Arrays.toString(books.get(0)));
    }
    //System.out.println(candidateSentencesPriority.size());
    List<String> usedWords = new ArrayList<String>();
    for (String word : pW) {
      if (wordToMarkov.containsKey(word)) {
        usedWords.add(word);
      }
    }
    usedPW = usedWords.toArray(new String[usedWords.size()]);
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
    if (usedPW.length > 0) {
      Random r = new Random();
      int i = r.nextInt(usedPW.length);
      int I = i;
      boolean go = true;
      while (i != I || go) {
        go = false;
        String word = usedPW[i];
        List<String> sentence =
            wordToMarkov.get(word)
            .makeSentenceFragment(min, max, start, end, numTries);
        if (sentence.size() > 0) {
          return sentence;
        }
        i = (i + 1) % (usedPW.length);
      }
    }
    List<String> sentence =
        wordToMarkov.get(" ")
        .makeSentenceFragment(min, max, start, end, numTries);
    if (sentence.size() > 0) {
      return sentence;
    }
    return new ArrayList<String>();
  }
  
  
  /**
   * the major method of the class. 
   * @param recombinations  - the number of recombinations
   * @return a sentence that matches all of the facets.
   */
  public String generateSentence(int recombinations) {
    if (candidateSentences.size() == 0) {
      return "ERROR: No candidate sentences";
    } else {
      //System.out.println(candidateSentences.size());
    }
    String originalSentence = getRandomSentence();
    String sentence = originalSentence;
    for (int i = 0; i < recombinations; i++) {
      System.out.println(sentence);
//      if (sentence.length() > maxLength && i > (((double) recombinations) / 2)) {
//        break;
//      }
      sentence = recombineSentence(sentence);
      if (sentence == null) {
        sentence = recombineSentence(getRandomSentence());
      }
    }
    if (sentence.toLowerCase().equals(originalSentence.toLowerCase())) {
      System.out.println(4444);
      String first = getRandomSentence().split(" ")[0];
      if (usedPW.length > 0) {
        Random r = new Random();
        int i = r.nextInt(usedPW.length);
        int I = i;
        boolean go = true;
        while (i != I || go) {
          go = false;
          String word = usedPW[i];
          if (wordToMarkov.get(word).size() > 20) {
            sentence = wordToMarkov.get(word)
                .makeRandomString(10, first);
            break;
          }
          i = (i + 1) % (usedPW.length);
        }
      }
    }
    sentence = sentence
        .toLowerCase()
        .trim()
        .replaceAll("([a-z])([A-Z])", "$1 $2")
        //.replaceAll("( [A-Z][a-z]+)", "$1,")
        .replaceAll(" i ", " I ")
        .replaceAll(",+", ",")
        .replaceAll("[;,.?!]$", "");
    return sentence.substring(0, 1).toUpperCase()
        + sentence.substring(1)
        + ".";
  }

  /**
   * recombines a sentence - one iteration
   * @param sentence the sentence to recombine
   * @param minAdder a paramreter to add to the
   * minimum value for sentence length
   * @return a sentence that matches all of the facets.
   */
  public String recombineSentence(String sentence) {
    String[] sentenceArray = sentence.split(" ");
    if (sentenceArray.length <= 1) {
      return null;
    }
    int[] startEnd = null;
    Random rand = new Random();
    if (sentenceArray.length < 25 && rand.nextInt() > 0.5) {
      startEnd = splitSentenceParse(sentenceArray.clone());
    } else {
      startEnd = splitSentenceNaive(sentenceArray.clone());
    }
    //System.out.println(Arrays.toString(startEnd));
    String startWord = sentenceArray[startEnd[0]];
    String endWord = sentenceArray[startEnd[1]];
    int len = startEnd[1] - startEnd[0];
    String[] startPart = Arrays.copyOfRange(
        sentenceArray, 0, startEnd[0] + 1);
    List<String> frag = new ArrayList<String>();
    String[] endPart = new String[] {};
    if (len == 0) {
      if (startEnd[0] == sentenceArray.length - 1) {
        frag = makeSentenceFragment(
            2, 5, startWord, null, numTries);
      }
    } else {
      endPart = Arrays.copyOfRange(
          sentenceArray, startEnd[1], sentenceArray.length);
      frag = makeSentenceFragment(
          Math.max(1, len - 2),
          Math.min(len + 2, sentenceArray.length),
          startWord, endWord, numTries);
    }
    String outputSentence = null;
    if (frag.size() != 0) {
      outputSentence = combine(startPart, frag, endPart);
    } else {
      //System.out.println("something got messed up");
      outputSentence = sentence;
    }
    return outputSentence;
  }

  /**
   * generates indices of a sentence split - this method uses parser
   * @param sentenceArray to split
   * @return indices of words that are start and end to split on
   */
  private int[] splitSentenceNaive(String[] sentenceArray) {
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
   * generates indices of a sentence split - this method uses parser
   * @param sentenceArray to split
   * @return indices of words that are start and end to split on
   */
  public int[] splitSentenceParse(String[] sentenceArray) {
    //Don't split really short sentences
    if (sentenceArray.length <= 3) {
      return new int[] {0, sentenceArray.length - 1};
    }
    //parser expects lowercase
    for (int i = 0; i < sentenceArray.length; i++) {
      sentenceArray[i] = sentenceArray[i]
          .toLowerCase()
          .replaceAll("[^a-z, -]", "");
    }
    try {
      List<ArrayList<String>> parsed = p.parseSentence(sentenceArray);
      //make sure the parser output is valid
      boolean bad = false;
      if (parsed.size() > 1) {
        int counter = 0;
        for (ArrayList<String> a : parsed) {
          counter += a.size();
        }
        if (counter != sentenceArray.length) {
          bad = true;
        }
      } else {
        bad = true;
      }
      //System.out.println(bad);
      if (bad) {
        return splitSentenceNaive(sentenceArray);
      }
      Random rand = new Random();
      int index = rand.nextInt(parsed.size());
      //System.out.println(index);
      //System.out.println(parsed.size());
      int start = 0;
      for (int i = 0; i < index; i++) {
        start += parsed.get(i).size();
        //System.out.println(parsed.get(i));
      }
      int end = start + parsed.get(index).size() - 1;
      //int end = start + 1 + rand.nextInt(Math.max(start + 1, sentenceArray.length - start - 1));
      //assert end < sentenceArray.length;
      assert start >= 0;
      assert end < sentenceArray.length;
      return new int[] {start, end};
    } catch(Exception e) {
      return splitSentenceNaive(sentenceArray);
    }
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
    builder.append(" ");
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
    if (candidateSentencesPriority.size() > 0) {
      return candidateSentencesPriority.get(
          rand.nextInt(candidateSentencesPriority.size()));
    }
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
  
  /**
   * convenient debugging method,
   * @param c collection to print
   */
  public void print(Collection c) {
    System.out.println(Arrays.toString(c.toArray()));
  }
  
}
//
//*/
//public MarkovManager(String[] books, String[] priorityWords) {
// candidateSentences = new ArrayList<String>();
// wordToMarkov = new Hashtable<String, MarkovChain>();
// p = new Parser();
//
// if (books.length == 0) {
//   System.out.println("ERROR: No Books Submitted");
//   return;
// }
// String[] pW = priorityWords;
// for (int i = 0; i < pW.length; i++) {
//   pW[i] = pW[i].toLowerCase();
// }
//   
// int a = 0;
// if (books[0].equals("@")) {
//   a = 1;
// }
// Random rand = new Random();
// for (int i = a; i < books.length; i++) {
//   String sp = "(?<!Mr?s?|\\b[A-Z])\\.\\s*";
////   if (a == 0) {
////     sp = "\\r?\\n";
////   }
//   String[] sentences = books[i]
//       .replaceAll("[^A-Za-z0-9,?!;\\.: -]", "")
//       .split(sp);
//   //System.out.println(sentences.length + " sentences");
//   for (int j = 0; j < sentences.length; j++) {
//     String sentence = sentences[j]
//       .replaceAll(" +", " ");
//     String[] sentenceArray = sentence.split(" ");
//     if (sentenceArray.length >= 4) {
//       candidateSentences.add(sentence);
//     }
//     for (String word : pW) {
//       //case insensitive regexp
//       if (sentence.toLowerCase().contains(word)) {
//         if (!wordToMarkov.containsKey(word)) {
//           wordToMarkov.put(word, new MarkovChain());
//         }
//         wordToMarkov.get(word).addSentence(sentenceArray);
//       } else {
//         //" " chain contains 50% of sentences
//         if (rand.nextInt(100) > 50 || true) {
//           if (!wordToMarkov.containsKey(" ")) {
//             wordToMarkov.put(" ", new MarkovChain());
//           }
//           wordToMarkov.get(" ").addSentence(sentenceArray);
//         }
//       }
//     }
//
//   }
// }
// if (candidateSentences.size() == 0) {
//   System.out.println("WARNING: NO CANDIDATE SENTENCES");
// }
// List<String> usedWords = new ArrayList<String>();
// for (String word : pW) {
//   if (wordToMarkov.containsKey(word)) {
//     usedWords.add(word);
//   }
// }
// usedPW = usedWords.toArray(new String[usedWords.size()]);
//}
















        
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


//
//
//
//
//
//
//package edu.brown.cs.qc14.parser;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.util.Arrays;
//
//import edu.brown.cs.dshieble.finalproject.MarkovManager;
//
//
//public class Main {
//  // passing "wsj2-21.blt" as args[0]
//  public static void main(String[] args) {
//    
//    //PARSER STUFF FOR DEMO
//    if (args[0].equals("1")) {
//      Parser parser = new Parser();
//      parser.buildRules(args[1]);
//      // parseSentence method takes a string of sentence (punctuations and words separated by space)
//      // returns its parsing in String
//      // in Parser.java, I set max_length of input sentence as 25. you may change it.
//      String parsing = parser.parseSentence("replace this with whatever string");
//      /*try {
//        parser.parse(args[1], args[2]);
//      } catch (FileNotFoundException e) {
//        e.printStackTrace();
//      } catch (UnsupportedEncodingException e) {
//        e.printStackTrace();
//      }*/
//      
//      
//      //MARKOV STUFF FOR DEMO
//    } else if (args[0].equals("2")) {
//      assert args.length >= 3;
//      String filename = args[1];
//      StringBuilder builder = new StringBuilder();
//      try (BufferedReader fileReader = new BufferedReader(
//        new FileReader(filename))) {
//        String line = null;
//        while ((line = fileReader.readLine()) != null) {
//          builder.append(line);
//          builder.append(" ");
//        }
//      } catch (IOException e) {
//        System.out.println("ERROR: File IO Error");
//        return;
//      }
//      String words = builder
//           .toString();
//      String[] books = new String[] {words};
//      MarkovManager man = new MarkovManager(books,
//          Arrays.copyOfRange(
//          args, 2, args.length));
//      for (int i = 0; i < 10; i++) {
//        System.out.println(man.generateSentence(10));
//      }
//      
//      
//      
//      
//      
//      //DATABASE STUFF FOR DEMO
//    } else if (args[0].equals("3")) {
//      System.out.println("DATABASE STUFF SHOULD BE HERE");
//    } else {
//      System.out.println("args[0] should be 1, 2 or 3");
//    }
//  }
//}
//

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


