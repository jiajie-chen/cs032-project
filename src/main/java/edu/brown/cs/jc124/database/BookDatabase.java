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
  private static final String BOOK_FACET_TABLE = "book_facet";
  private static final String BOOK_AUTHOR_TABLE = "book_author";
  private static final String LOCATION_TABLE = "location";
  private static final String BOOK_LOCATION_TABLE = "book_location";
  
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
  public Set<String> getBooksWithFacets(Set<String> facets) throws SQLException {
    // build a dynamic query for all in the set of facets
    StringBuilder facetQuery = new StringBuilder("(");
    for (int i = 0; i < facets.size(); i++) {
      facetQuery.append("?,");
    }
    facetQuery.deleteCharAt(facetQuery.length() - 1).append(")");
    
    String query = "SELECT"
        + " f.book_id"
        + " FROM"
        + " " + BOOK_FACET_TABLE + " AS f"
        + " WHERE"
        + "  f.facet IN " + facetQuery
        + " GROUP BY f.book_id"
        + " HAVING count(DISTINCT f.book_id) = ?;";
    
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
  
  public Set<String> getBooksAtLocation(double lat, double lng) throws SQLException {
    String query = "SELECT"
        + " bl.book_id"
        + " FROM"
        + " " + BOOK_LOCATION_TABLE + " AS bl,"
        + " " + LOCATION_TABLE + " AS l"
        + " WHERE"
        + "  bl.region = l.region"
        + "  AND l.latitude = ?"
        + "  AND l.longitude = ?"
        + " GROUP BY bl.book_id;";
    
    Set<String> toReturn = new HashSet<>();
    try (PreparedStatement stat = conn.prepareStatement(query)) {
      stat.setDouble(1, lat);
      stat.setDouble(2, lng);
      
      try (ResultSet rs = stat.executeQuery()) {
        while (rs.next()) {
          toReturn.add(rs.getString(1));
        }
      }
    }
    
    return toReturn;
  }
  
  public Set<String> getBooksBetweenYears(int startYear, int endYear) throws SQLException {
    String query = "SELECT"
        + " b.book_id"
        + " FROM"
        + " " + BOOK_TABLE + " AS b"
        + " WHERE"
        + "  b.year BETWEEN (?, ?)"
        + " GROUP BY b.book_id;";
    
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
   * @param author
   * @return
   * @throws SQLException 
   */
  public Set<String> getBooksByAuthor(String author) throws SQLException {
    String query = "SELECT"
        + " a.book_id"
        + " FROM"
        + " " + BOOK_AUTHOR_TABLE + " AS a"
        + " WHERE"
        + "  a.author = ?"
        + " GROUP BY a.book_id;";
    
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
  
  public Set<String> getAllAuthors() throws SQLException {
    String query = "SELECT"
        + " a.author"
        + " FROM"
        + " " + BOOK_AUTHOR_TABLE + " AS a"
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
        + " " + BOOK_FACET_TABLE + " AS f"
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
