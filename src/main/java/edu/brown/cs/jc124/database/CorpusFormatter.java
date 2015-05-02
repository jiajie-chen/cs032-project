package edu.brown.cs.jc124.database;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jchen
 * Utility class for formatting book texts into an appropriate format.
 */
public final class CorpusFormatter {
  private static final String SENTENCE_CLEANER = "[^a-zA-Z0-9';,.?!\\s]+";
  private static final String SENTENCE_SPLITTER = "(?<!\\w\\.\\w.)(?<![A-Z][a-z]\\.)(?<=\\.|\\?|!)(\\s)";
  
  private static final String[] SENTENCE_WHITELIST = {

  };
  
  private static final String[] SENTENCE_BLACKLIST = {
    "^\\s*$", // blank line
    "^[^a-z]*$", // no lower case
    "^[^a-zA-Z]*$", // no letters
  };
  
  /**
   * Formats the corpora into a text easily readable by text parsers.
   * @param raw the raw text to format.
   * @return the formatted corpus, split into sentences for each line, with noise data removed.
   */
  public static String[] formatCorpus(String raw) {
    String sentences = raw.replaceAll(SENTENCE_CLEANER, " ").replaceAll(SENTENCE_SPLITTER, "\n");
    String[] lines = sentences.split("\n");
    
    List<String> toReturn = new ArrayList<>();
    String sentence = "";
    for (String l : lines) {
      String trimmed = l.trim();
      if (validSentence(trimmed)) {
        sentence += " " + trimmed;
        if (sentence.matches("^[\\s\\S]*[.?!]$")) {
          toReturn.add(sentence.trim());
          sentence = "";
        }
      }
    }
    
    return toReturn.toArray(new String[0]);
  }
  
  /**
   * Determines if a given line fits the sentence criteria.
   * @param sentence the sentence to check.
   * @return true if the sentence fits the criteria, false otherwise.
   */
  public static boolean validSentence(String sentence) {
    for (String regex : SENTENCE_WHITELIST) {
      // if sentence is not in whitelist, fail
      if (!sentence.matches(regex)) {
        return false;
      }
    }
    
    for (String regex : SENTENCE_BLACKLIST) {
      // if sentence is in blacklist, fail
      if (sentence.matches(regex)) {
        return false;
      }
    }
    
    return true;
  }
}
