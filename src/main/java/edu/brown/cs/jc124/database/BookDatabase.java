package edu.brown.cs.jc124.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

public class BookDatabase {
  private Connection conn;
  
  public BookDatabase(String path) throws ClassNotFoundException, SQLException {
    Class.forName("org.sqlite.JDBC");
    this.conn = DriverManager.getConnection("jdbc:sqlite:" + path);
    Statement stat = conn.createStatement();
    stat.executeUpdate("PRAGMA foreign_keys = ON;");
    stat.close();
  }
  
  public Set<String> getBooksOfAttribute(Set<BookAttributes> attr) {
    return null;
    
  }
  
  public Set<String> getBooksOfAuthor(String author) {
    return null;
    
  }
}
