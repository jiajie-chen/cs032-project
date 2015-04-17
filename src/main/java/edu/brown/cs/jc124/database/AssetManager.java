package edu.brown.cs.jc124.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class AssetManager {
  private static final String BOOK_PATH = "/home/jchen/course/cs032-project/books/";
  private static final String FILE_TYPE = ".txt";
  private BookDatabase bd;
  
  public AssetManager(String dbPath) throws ClassNotFoundException, SQLException {
    this.bd = new BookDatabase(dbPath);
  }
  
  public AssetManager(BookDatabase bd) {
    this.bd = bd;
  }
  
  public String[] loadBooksByName(Set<String> filenames) {
    List<String> corpora = new ArrayList<>();
    
    for (String name : filenames) {
      File f = new File(BOOK_PATH + name + FILE_TYPE);
      try (FileInputStream fs = new FileInputStream(f)) {
        byte[] data = new byte[(int) f.length()];
        fs.read(data);
        fs.close();
        
        corpora.add(new String(data, "UTF-8"));
      } catch (FileNotFoundException e) {
        System.err.println("FILE LOAD ERROR: " + e.getMessage());
      } catch (IOException e) {
        System.err.println("FILE READ ERROR: " + e.getMessage());
      }
    }
    
    return corpora.toArray(new String[0]);
  }
  
  /*
  
  public Iterator<String> getByAuthor(String author) {
    
  }
  
  public Iterator<String> getByFacet(Set<BookFacet>  attr) {
    
  }
  
  */
  
  public String[] getFilesByAuthor(String author) {
    try {
      Set<String> names = bd.getBooksOfAuthor(author);
      return loadBooksByName(names);
    } catch (SQLException e) {
      System.err.println("LOAD BY AUTHOR ERROR: " + e.getMessage());
    }
    return new String[0];
  }
  
  public String[] getFilesByFacet(Set<String> facets) {
    try {
      Set<String> names = bd.getBooksOfAttribute(facets);
      return loadBooksByName(names);
    } catch (SQLException e) {
      System.err.println("LOAD BY FACET ERROR: " + e.getMessage());
    }
    return new String[0];
  }
  
  public void Main(String[] args) {
    AssetManager a;
    try {
      a = new AssetManager("/home/jchen/course/cs032-project/books/smallBooks.sqlite3");
      System.out.println(Arrays.toString(a.getFilesByAuthor("James Joyce")));
    } catch (ClassNotFoundException |SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
  }
}
