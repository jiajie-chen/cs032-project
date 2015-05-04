package edu.brown.cs.jc124.database;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

/**
 * @author jchen
 * Manages the queries to the book SQLite database.
 */
public class BookDatabase implements Closeable, AutoCloseable {
  private Connection conn;
  private static final String BOOK_TABLE = "book";
  private static final String BOOK_FACET_TABLE = "book_facet";
  private static final String BOOK_AUTHOR_TABLE = "book_author";
  private static final String LOCATION_TABLE = "location";
  private static final String BOOK_LOCATION_TABLE = "book_location";
  private static final String SYNONYM_TABLE = "synonym";
  
  /**
   * Creates a BookDatabase that queries the SQLite database at the specified path.
   * @param path the path to the SQLite table.
   * @throws ClassNotFoundException if the SQLite adapter is not found.
   * @throws SQLException if the connection to the given table cannot be made.
   */
  public BookDatabase(String path) throws ClassNotFoundException, SQLException {
    Class.forName("org.sqlite.JDBC");
    this.conn = DriverManager.getConnection("jdbc:sqlite:" + path);
    Statement stat = conn.createStatement();
    stat.executeUpdate("PRAGMA foreign_keys = ON;");
    stat.close();
  }
  
  /**
   * Gets all books that contain the facets given. Each book has at least one of the facets given.
   * @param facets the facets the books can have.
   * @return the filenames of all the books with the facets.
   * @throws SQLException if the SQl query fails when executing.
   */
  public Set<String> getBooksWithFacets(Set<String> facets) throws SQLException {
    if (facets.size() <= 0) {
      return new HashSet<>();
    }
    // build a dynamic query for all in the set of facets
    StringBuilder facetQuery = new StringBuilder("(");
    for (int i = 0; i < facets.size(); i++) {
      facetQuery.append("?,");
    }
    facetQuery.deleteCharAt(facetQuery.length() - 1).append(")");
    
    String query = "SELECT"
        + " f.book_id"
        + " FROM"
        + "  $BOOK_FACET_TABLE AS f"
        + " WHERE"
        + "  f.facet IN " + facetQuery
        + " GROUP BY f.book_id;";
        // + " HAVING count(f.book_id) = ?;";
    query = query.replace("$BOOK_FACET_TABLE", BOOK_FACET_TABLE);
    
    Set<String> toReturn = new HashSet<>();
    try (PreparedStatement stat = conn.prepareStatement(query)) {
      int i = 1;
      for (String f : facets) {
        stat.setString(i, f);
        i++;
      }
      
      // stat.setInt(i, i - 1);
      
      try (ResultSet rs = stat.executeQuery()) {
        while (rs.next()) {
          toReturn.add(rs.getString(1));
        }
      }
    }
    
    return toReturn;
  }
  
  /**
   * Gets all books that takes place in at least ONE of the locations given.
   * @param locationName the names of the locations for the books to be in.
   * @return the books that are in at least one of the locations.
   * @throws SQLException if the SQl query fails when executing.
   */
  public Set<String> getBooksAtLocationName(Set<String> locationName) throws SQLException {
    if (locationName.size() <= 0) {
      return new HashSet<>();
    }
    // build a dynamic query for all in the set of locations
    StringBuilder locationQuery = new StringBuilder("(");
    for (int i = 0; i < locationName.size(); i++) {
      locationQuery.append("?,");
    }
    locationQuery.deleteCharAt(locationQuery.length() - 1).append(")");
    
    String query = "SELECT"
        + " bl.book_id"
        + " FROM"
        + "  $BOOK_LOCATION_TABLE AS bl"
        + " WHERE"
        + "  bl.region IN " + locationQuery
        + " GROUP BY bl.book_id;";
    query = query.replace("$BOOK_LOCATION_TABLE", BOOK_LOCATION_TABLE);
    
    Set<String> toReturn = new HashSet<>();
    try (PreparedStatement stat = conn.prepareStatement(query)) {
      int i = 1;
      for (String l : locationName) {
        stat.setString(i, l);
        i++;
      }
      
      try (ResultSet rs = stat.executeQuery()) {
        while (rs.next()) {
          toReturn.add(rs.getString(1));
        }
      }
    }
    
    return toReturn;
  }
  
  /**
   * Gets all books written between the start year and end year.
   * @param startYear the starting year, inclusive.
   * @param endYear the ending year, inclusive.
   * @return the books written between the start year and end year.
   * @throws SQLException if the SQl query fails when executing.
   */
  public Set<String> getBooksBetweenYears(int startYear, int endYear) throws SQLException {
    String query = "SELECT"
        + " b.book_id"
        + " FROM"
        + "  $BOOK_TABLE AS b"
        + " WHERE"
        + "  b.year BETWEEN ? AND ?"
        + " GROUP BY b.book_id;";
    query = query.replace("$BOOK_TABLE", BOOK_TABLE);
    
    Set<String> toReturn = new HashSet<>();
    try (PreparedStatement stat = conn.prepareStatement(query)) {
      stat.setInt(1, startYear);
      stat.setInt(2, endYear);
      
      try (ResultSet rs = stat.executeQuery()) {
        while (rs.next()) {
          toReturn.add(rs.getString(1));
        }
      }
    }
    
    return toReturn;
  }

  /**
   * Gets all the books authored by the given author.
   * @param author the author of the book.
   * @return all the books authored by the given author.
   * @throws SQLException if the SQl query fails when executing.
   */
  public Set<String> getBooksByAuthor(String author) throws SQLException {
    String query = "SELECT"
        + " a.book_id"
        + " FROM"
        + "  $BOOK_AUTHOR_TABLE AS a"
        + " WHERE"
        + "  a.author = ?"
        + " GROUP BY a.book_id;";
    query = query.replace("$BOOK_AUTHOR_TABLE", BOOK_AUTHOR_TABLE);
    
    Set<String> toReturn = new HashSet<>();
    try (PreparedStatement stat = conn.prepareStatement(query)) {
      stat.setString(1, author);
      
      try (ResultSet rs = stat.executeQuery()) {
        while (rs.next()) {
          toReturn.add(rs.getString(1));
        }
      }
    }
    
    return toReturn;
  }
  
  /**
   * Gets all the authors in the database.
   * @return all the authors in the database.
   * @throws SQLException if the SQl query fails when executing.
   */
  public Set<String> getAllAuthors() throws SQLException {
    String query = "SELECT"
        + " a.author"
        + " FROM"
        + "  $BOOK_AUTHOR_TABLE AS a"
        + " GROUP BY a.author;";
    query = query.replace("$BOOK_AUTHOR_TABLE", BOOK_AUTHOR_TABLE);
    
    Set<String> toReturn = new HashSet<>();
    try (PreparedStatement stat = conn.prepareStatement(query)) {
      try (ResultSet rs = stat.executeQuery()) {
        while (rs.next()) {
          toReturn.add(rs.getString(1));
        }
      }
    }
    
    return toReturn;
  }
  
  /**
   * Gets all the possible facets in the database.
   * @return all the facets in the database.
   * @throws SQLException if the SQl query fails when executing.
   */
  public Set<String> getAllFacets() throws SQLException {
    String query = "SELECT"
        + " f.facet"
        + " FROM"
        + "  $BOOK_FACET_TABLE AS f"
        + " GROUP BY f.facet;";
    query = query.replace("$BOOK_FACET_TABLE", BOOK_FACET_TABLE);
    
    Set<String> toReturn = new HashSet<>();
    try (PreparedStatement stat = conn.prepareStatement(query)) {
      try (ResultSet rs = stat.executeQuery()) {
        while (rs.next()) {
          toReturn.add(rs.getString(1));
        }
      }
    }
    
    return toReturn;
  }
  
  /**
   * Gets all the book locations in the database.
   * @return all the locations in the database.
   * @throws SQLException if the SQl query fails when executing.
   */
  public Set<BookLocation> getAllLocations() throws SQLException {
    String query = "SELECT"
        + " l.region, l.latitude, l.longitude"
        + " FROM"
        + "  $LOCATION_TABLE AS l"
        + " GROUP BY l.region;";
    query = query.replace("$LOCATION_TABLE", LOCATION_TABLE);
    
    Set<BookLocation> toReturn = new HashSet<>();
    try (PreparedStatement stat = conn.prepareStatement(query)) {
      try (ResultSet rs = stat.executeQuery()) {
        while (rs.next()) {
          BookLocation loc = new BookLocation(rs.getString(1), rs.getDouble(2), rs.getDouble(3));
          toReturn.add(loc);
        }
      }
    }
    
    return toReturn;
  }
  
  public Set<String> getSynonyms(String word) throws SQLException {
    String query = "SELECT"
        + " s.synonym"
        + " FROM"
        + "  $SYNONYM_TABLE AS s"
        + " WHERE"
        + "  s.word = ?"
        + " GROUP BY s.synonym;";
    query = query.replace("$SYNONYM_TABLE", SYNONYM_TABLE);
    
    Set<String> toReturn = new HashSet<>();
    try (PreparedStatement stat = conn.prepareStatement(query)) {
      stat.setString(1, word);
      
      try (ResultSet rs = stat.executeQuery()) {
        while (rs.next()) {
          toReturn.add(rs.getString(1));
        }
      }
    }
    
    return toReturn;
  }

  @Override
  public void close() throws IOException {
    try {
      conn.close();
    } catch (SQLException e) {
      throw new IOException(e);
    }
  }
}
