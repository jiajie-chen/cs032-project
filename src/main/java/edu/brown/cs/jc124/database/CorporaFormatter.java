package edu.brown.cs.jc124.database;

public class CorporaFormatter {
  private static final String SENTENCE_SPLITTER = "(?<!\\w\\.\\w.)(?<![A-Z][a-z]\\.)(?<=\\.|\\?|!)\\s";
  
  public static String formatCorpus(String raw) {
    String sentences = raw.replaceAll(SENTENCE_SPLITTER, "\n");
    String[] lines = sentences.split("\n");
    
    StringBuilder sb = new StringBuilder();
    for (String l : lines) {
      if (!l.matches("\\s*") && !l.matches("[A-Z]*")) {
        sb.append(l);
        sb.append("\n");
      }
    }
    
    return sb.toString();
  }
}
