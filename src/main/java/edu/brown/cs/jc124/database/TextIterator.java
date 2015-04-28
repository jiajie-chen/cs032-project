package edu.brown.cs.jc124.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

/**
 * Provides a line-by-line Iterator through a text corpus.
 * @author jchen
 */
public class TextIterator implements Iterator<String>, AutoCloseable {
  private BufferedReader br;
  private String next;
  
  public TextIterator(InputStream is) throws UnsupportedEncodingException {
    br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
    next = null;
  }
  
  public TextIterator() {
    next = null;
  }

  @Override
  public boolean hasNext() {
    // if next already found, true
    if (next != null) {
      return true;
    }
    
    // else, try to find the next line
    try {
      next = br.readLine();
      return (next != null);
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public String next() {
    // if next already found, reset and return
    if (next != null) {
      next = null;
      return next;
    }
    
    // else, try to find the next line
    try {
      return br.readLine();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public void close() throws Exception {
    br.close();
  }
}
