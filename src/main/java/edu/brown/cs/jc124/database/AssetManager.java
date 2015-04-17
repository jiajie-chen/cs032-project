package edu.brown.cs.jc124.database;

import java.util.Set;

public class AssetManager {
  private BookDatabase bd;
  
  public AssetManager(BookDatabase bd) {
    this.bd = bd;
  }
  
  public TextIterator getByAuthor(String author) {
    
  }
  
  public TextIterator getByFacet(Set<BookFacet>  attr) {
    
  }
}
