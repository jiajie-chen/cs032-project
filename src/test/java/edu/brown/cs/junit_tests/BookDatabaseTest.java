package edu.brown.cs.junit_tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.ImmutableSet;

import edu.brown.cs.jc124.database.BookDatabase;

public class BookDatabaseTest {
  private static final String DEFAULT_DB_PATH = "db/smallBooks.sqlite3";

  /*
  @Test
  public void facetsTest() {
    try (BookDatabase bd = new BookDatabase(DEFAULT_DB_PATH)) {
      
      Set<String> facets = ImmutableSet.of("Poetry");
      assertEquals(bd.getBooksWithFacets(facets), ImmutableSet.of("poetics", "poems_dickenson", "works_aristotle"));
      
      facets = ImmutableSet.of("Fantasy", "Mythology");
      assertEquals(bd.getBooksWithFacets(facets), ImmutableSet.of("thousand_one_nights", "canterbury_tales", "aesop_fables", "sindbad_sailor"));
      
      facets = ImmutableSet.of("Instruction", "Mythology");
      assertEquals(bd.getBooksWithFacets(facets), ImmutableSet.builder().build());
      
    } catch (ClassNotFoundException | IOException | SQLException e) {
      fail(e.getMessage());
    }
  }
  */
  
  @Test
  public void locationTest() {
    try (BookDatabase bd = new BookDatabase(DEFAULT_DB_PATH)) {
      
      Set<String> usa = ImmutableSet.of("wizard_of_oz", "wit_humor_america", "sketch_book_crayon", "emerson", "frederick_douglass", "poems_dickenson", "the_yellow_wallpaper");
      Set<String> turkey = ImmutableSet.of("iliad");
      Set<String> usaTurkey = new HashSet<>(usa);
      usaTurkey.addAll(turkey);
      
      Set<String> locations = ImmutableSet.of("USA");
      assertEquals(bd.getBooksAtLocationName(locations), usa);
      
      locations = ImmutableSet.of("Turkey");
      assertEquals(bd.getBooksAtLocationName(locations), turkey);
      
      locations = ImmutableSet.of("USA", "Turkey");
      assertEquals(bd.getBooksAtLocationName(locations), usaTurkey);
      
    } catch (ClassNotFoundException | IOException | SQLException e) {
      fail(e.getMessage());
    }
  }
  
  @Test
  public void yearsTest() {
    try (BookDatabase bd = new BookDatabase(DEFAULT_DB_PATH)) {
      
      Set<String> from1400To1500 = ImmutableSet.of("canterbury_tales");
      Set<String> from1500To1700 = ImmutableSet.of("discourse_method", "leviathan");
      Set<String> from1400To1700 = new HashSet<>(from1400To1500);
      from1400To1700.addAll(from1500To1700);
      
      assertEquals(bd.getBooksBetweenYears(1400, 1500), from1400To1500);
      
      assertEquals(bd.getBooksBetweenYears(1500, 1700), from1500To1700);
      
      assertEquals(bd.getBooksBetweenYears(1400, 1700), from1400To1700);
      
    } catch (ClassNotFoundException | IOException | SQLException e) {
      fail(e.getMessage());
    }
  }
  
  @Test
  public void authorTest() {
    try (BookDatabase bd = new BookDatabase(DEFAULT_DB_PATH)) {
      
      Set<String> homer = ImmutableSet.of("iliad", "odyssey");
      Set<String> arthurDoyle = ImmutableSet.of("hound_baskervilles", "sherlock_holmes", "return_sherlock_holmes");
      
      assertEquals(bd.getBooksByAuthor("Homer"), homer);
      
      assertEquals(bd.getBooksByAuthor("Arthur Doyle"), arthurDoyle);
      
    } catch (ClassNotFoundException | IOException | SQLException e) {
      fail(e.getMessage());
    }
  }
}
