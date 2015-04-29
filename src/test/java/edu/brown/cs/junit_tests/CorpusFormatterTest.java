package edu.brown.cs.junit_tests;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.brown.cs.jc124.database.CorpusFormatter;

public class CorpusFormatterTest {

  @Test
  public void validSentenceTest() {
    String valid1 = "These are words";
    String valid2 = "abcdefghijklmnop";
    String valid3 = "My sister, Mrs. Joe Gargery, was more than twenty years older than I,"
        + "and had established a great reputation with herself and the neighbors"
        + "because she had brought me up \"by hand.\"";
    
    String invalid1 = "CHAPTER 32";
    String invalid2 = "{123}";
    String invalid3 = "* * *";
    
    assertTrue(CorpusFormatter.validSentence(valid1));
    assertTrue(CorpusFormatter.validSentence(valid2));
    assertTrue(CorpusFormatter.validSentence(valid3));
    
    assertFalse(CorpusFormatter.validSentence(invalid1));
    assertFalse(CorpusFormatter.validSentence(invalid2));
    assertFalse(CorpusFormatter.validSentence(invalid3));
  }
  
  @Test
  public void sentenceSplitTest() {
    String sentenceSame =
        "These are words.\n"
        + "Words are cool!\n"
        + "Where are the words?";
    assertArrayEquals(sentenceSame.split("\n"), CorpusFormatter.formatCorpus(sentenceSame));
    
    String sentenceSplit = "These are words. Words are cool! Where are the words?";
    assertArrayEquals(sentenceSame.split("\n"), CorpusFormatter.formatCorpus(sentenceSplit));
    
    String sentenceJunk =
        "JJJJJJJJJ\n"
        + "*&*@\n"
        + "\n"
        + "These are words. Words are cool! &&&&&&** Where are the words?";
    assertArrayEquals(sentenceSame.split("\n"), CorpusFormatter.formatCorpus(sentenceJunk));
  }
}
