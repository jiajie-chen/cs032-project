package edu.brown.cs.jc124.database;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

/**
 * @author jchen
 * Manager class for handling book lookups and corpora loading, as well as metadata.
 */
public final class AssetManager implements Closeable, AutoCloseable {
  private static final String DEFAULT_DB_PATH = "db/smallBooks.sqlite3";
  private static final String DEFAULT_BOOK_PATH = "books/";
  private static final String FILE_TYPE = ".txt";
  
  private String BOOK_PATH;
  private BookDatabase bd;
  
  /**
   * Constructs a manager that queries the given BookDatabase, and loads corpora from the given book path.
   * @param bd the book database that holds the book data.
   * @param bookPath the folder directory that holds the book texts.
   */
  public AssetManager(BookDatabase bd, String bookPath) {
    this.bd = bd;
    this.BOOK_PATH = bookPath;
  }
  
  /**
   * Constructs a manager that queries the given database at the path, and loads corpora from the given book path.
   * @param dbPath the path to the SQLite table of the book database.
   * @param bookPath the folder directory that holds the book texts.
   * @throws ClassNotFoundException if the SQLite adapter is not found.
   * @throws SQLException if the connection to the given table cannot be made.
   */
  public AssetManager(String dbPath, String bookPath) throws ClassNotFoundException, SQLException {
    this(new BookDatabase(dbPath), bookPath);
  }
  
  /**
   * Creates a default manager that queries to the default book database and default book path.
   * @throws ClassNotFoundException if the SQLite adapter is not found.
   * @throws SQLException if the connection to the given table cannot be made.
   */
  public AssetManager() throws ClassNotFoundException, SQLException {
    this(DEFAULT_DB_PATH, DEFAULT_BOOK_PATH);
  }
  
  /**
   * Given a set of filenames, loads the texts of each file into an array.
   * @param filenames the set of filenames.
   * @return the array of corpora, each book a string in the array.
   */
  public String[] loadBooksByName(Set<String> filenames) {
    List<String> corpora = new ArrayList<>();
    
    for (String name : filenames) {
      File f = new File(BOOK_PATH + name + FILE_TYPE);
      try (FileInputStream fs = new FileInputStream(f)) {
        byte[] data = new byte[(int) f.length()];
        fs.read(data);
        fs.close();
        
        corpora.add(CorpusFormatter.formatCorpus(new String(data, "UTF-8")));
      } catch (FileNotFoundException e) {
        System.err.println("FILE LOAD ERROR: " + e.getMessage());
      } catch (IOException e) {
        System.err.println("FILE READ ERROR: " + e.getMessage());
      }
    }
    
    return corpora.toArray(new String[0]);
  }
  
  /**
   * Gets the corpora of the files by the given author.
   * @param author the name of the author.
   * @return the corpora of books written by the author.
   */
  public String[] getFilesByAuthor(String author) {
    try {
      Set<String> names = bd.getBooksByAuthor(author);
      
      return loadBooksByName(names);
    } catch (SQLException e) {
      System.err.println("LOAD BY AUTHOR ERROR: " + e.getMessage());
    }
    return new String[0];
  }
  
  /**
   * Gets the corpora of the files by the given attributes of the books.
   * @param facets the set of facets each book must have; if empty or null, this is ignored.
   * @param locationName the set of locations the books can be set in.
   * @param startYear the starting year for the books to be published in.
   * @param endYear the end year for the book to be published in.
   * @return the corpora of files that satisfy all the facets, are in one of the locations, and published in the date range.
   */
  public String[] getFilesByAttributes(Set<String> facets, Set<String> locationName, int startYear, int endYear) {
    try {
      Set<String> names = new HashSet<>();
      
      Set<String> l = bd.getBooksAtLocationName(locationName);
      names.addAll(l);
      
      Set<String> y = bd.getBooksBetweenYears(startYear, endYear);
      names.retainAll(y);
      
      if(facets != null && facets.size() > 0) {
        Set<String> f = bd.getBooksWithFacets(facets);
        names.retainAll(f);
      }
      
      return loadBooksByName(names);
    } catch (SQLException e) {
      System.err.println("GET ATTRIBUTES ERROR: " + e.getMessage());
    }
    return new String[0];
  }
  
  /**
   * Gets all the authors in the database.
   * @return the names of all the authors in the database.
   */
  public String[] getAllAuthors() {
    try {
      return bd.getAllAuthors().toArray(new String[0]);
    } catch (SQLException e) {
      System.err.println("GET AUTHORS ERROR:" + e.getMessage());
      return new String[0];
    }
  }
  
  /**
   * Gets all the facets stored in the database.
   * @return all the facets in the database.
   */
  public String[] getAllFacets() {
    try {
      return bd.getAllFacets().toArray(new String[0]);
    } catch (SQLException e) {
      System.err.println("GET FACETS ERROR:" + e.getMessage());
      return new String[0];
    }
  }

  @Override
  public void close() throws IOException {
    bd.close();
  }
}
