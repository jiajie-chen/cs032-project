package edu.brown.cs.jc124.database;

import java.io.Closeable;
import java.io.File;
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
 *
 */
public class BookDatabase implements Closeable {
  private Connection conn;
  private static final String BOOK_TABLE = "book";
  private static final String FACET_TABLE = "book_facet";
  private static final String AUTHOR_TABLE = "book_author";
  
  /**
   * @param path
   * @throws ClassNotFoundException
   * @throws SQLException
   */
  public BookDatabase(String path) throws ClassNotFoundException, SQLException {
    Class.forName("org.sqlite.JDBC");
    this.conn = DriverManager.getConnection("jdbc:sqlite:" + path);
    Statement stat = conn.createStatement();
    stat.executeUpdate("PRAGMA foreign_keys = ON;");
    stat.close();
  }
  
  /**
   * @param facets
   * @return
   * @throws SQLException if the SQl query fails when executing.
   */
  public Set<String> getBooksByAuthorWithAttributeInRange(String author, Set<String> facets, int startYear, int endYear) throws SQLException {
    // build a dynamic query for all in the set of facets
    StringBuilder facetQuery = new StringBuilder("(");
    for (int i = 0; i < facets.size(); i++) {
      facetQuery.append("?,");
    }
    facetQuery.deleteCharAt(facetQuery.length() - 1).append(")");
    
    String query = "SELECT"
        + " b.file_id"
        + " FROM"
        + " " + BOOK_TABLE + " AS b, "
        + " " + FACET_TABLE + " AS f,"
        + " " + AUTHOR_TABLE + " AS a"
        + " WHERE"
        + "  b.file_id = f.book_id"
        + "  AND b.file_id = a.book_id"
        + "  AND f.facet IN " + facetQuery
        + "  AND a.author = ?"
        + "  AND b.year BETWEEN (?, ?)"
        + " GROUP BY b.file_id"
        + " HAVING count(DISTINCT b.file_id) = ?;";
    
    Set<String> toReturn = new HashSet<>();
    try (PreparedStatement stat = conn.prepareStatement(query)) {
      int i = 1;
      for (String f : facets) {
        stat.setString(i, f);
        i++;
      }
      
      stat.setString(i, author);
      stat.setInt(i + 1, startYear);
      stat.setInt(i + 2, endYear);
      stat.setInt(i + 3, i - 1);
      
      try (ResultSet rs = stat.executeQuery()) {
        while (rs.next()) {
          toReturn.add(rs.getString(1));
        }
      }
    }
    
    return toReturn;
  }
  
  /**
   * @param facets
   * @return
   * @throws SQLException if the SQl query fails when executing.
   */
  public Set<String> getBooksWithAttribute(Set<String> facets) throws SQLException {
    // build a dynamic query for all in the set of facets
    StringBuilder facetQuery = new StringBuilder("(");
    for (int i = 0; i < facets.size(); i++) {
      facetQuery.append("?,");
    }
    facetQuery.deleteCharAt(facetQuery.length() - 1).append(")");
    
    String query = "SELECT"
        + " b.file_id"
        + " FROM"
        + " " + BOOK_TABLE + " AS b, " + FACET_TABLE + " AS f"
        + " WHERE"
        + "  b.file_id = f.book_id"
        + "  AND f.facet IN " + facetQuery
        + " GROUP BY b.file_id"
        + " HAVING count(DISTINCT b.file_id) = ?;";
    
    Set<String> toReturn = new HashSet<>();
    try (PreparedStatement stat = conn.prepareStatement(query)) {
      int i = 1;
      for (String f : facets) {
        stat.setString(i, f);
        i++;
      }
      
      stat.setInt(i, i - 1);
      
      try (ResultSet rs = stat.executeQuery()) {
        while (rs.next()) {
          toReturn.add(rs.getString(1));
        }
      }
    }
    
    return toReturn;
  }

  /**
   * @param author
   * @return
   * @throws SQLException 
   */
  public Set<String> getBooksByAuthor(String author) throws SQLException {
    String query = "SELECT"
        + " b.file_id"
        + " FROM"
        + " " + BOOK_TABLE + " AS b, " + AUTHOR_TABLE + " AS a"
        + " WHERE"
        + "  b.file_id = a.book_id"
        + "  AND a.author = ?"
        + " GROUP BY b.file_id;";
    
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
  
  public Set<String> getBooksOfAuthorInRange(String author, int startYear, int endYear) throws SQLException {
    if (endYear < startYear) {
      throw new IllegalArgumentException("start year cannot be greater than end year");
    }
    
    String query = "SELECT"
        + " b.file_id"
        + " FROM"
        + " " + BOOK_TABLE + " AS b, " + AUTHOR_TABLE + " AS a"
        + " WHERE"
        + "  b.file_id = a.book_id"
        + "  AND a.author = ?"
        + "  AND b.year BETWEEN (?, ?)"
        + " GROUP BY b.file_id;";
    
    Set<String> toReturn = new HashSet<>();
    try (PreparedStatement stat = conn.prepareStatement(query)) {
      stat.setString(1, author);
      stat.setInt(2, startYear);
      stat.setInt(3, startYear);
      
      try (ResultSet rs = stat.executeQuery()) {
        while (rs.next()) {
          toReturn.add(rs.getString(1));
        }
      }
    }
    
    return toReturn;
  }
  
  public Set<String> getAllAuthors() throws SQLException {
    String query = "SELECT"
        + " a.author"
        + " FROM"
        + " " + AUTHOR_TABLE + " AS a"
        + " GROUP BY a.author;";
    
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
  
  public Set<String> getAllFacets() throws SQLException {
    String query = "SELECT"
        + " f.facet"
        + " FROM"
        + " " + FACET_TABLE + " AS f"
        + " GROUP BY f.facet;";
    
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

  @Override
  public void close() throws IOException {
    try {
      conn.close();
    } catch (SQLException e) {
      throw new IOException(e);
    }
  }
}
