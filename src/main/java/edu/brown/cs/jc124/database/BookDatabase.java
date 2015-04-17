package edu.brown.cs.jc124.database;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

/**
 * @author jchen
 *
 */
public class BookDatabase implements Closeable {
  private Connection conn;
  
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
   * @param attr
   * @return
   * @throws SQLException if the SQl query fails when executing.
   */
  public Set<String> getBooksOfAttribute(Set<BookFacet> facets) throws SQLException {
    StringBuilder facetQuery = new StringBuilder("{");
    for (BookFacet f : facets) {
      facetQuery.append("?,");
    }
    
    String query = "SELECT"
        + " b.book_name"
        + " FROM"
        + " books AS b, facets AS f"
        + " WHERE"
        + " b.id = f.id AND f.facet IN " + facetQuery
        + " GROUP BY b.book_name";
    
    try (PreparedStatement stat = conn.prepareStatement(query)) {
      
    }
    return null;
  }
  
  private Object facetToString(BookFacet f) {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * @param author
   * @return
   */
  public Set<String> getBooksOfAuthor(String author) {
    return null;
    
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
