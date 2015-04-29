package edu.brown.cs.jc124.database;

/**
 * @author jchen
 * Wrapper class for book location, for sending to front-end.
 */
public class BookLocation {
  private String name;
  @SuppressWarnings("unused")
  private double coordinates[];
  
  /**
   * Makes a new location.
   * @param name the name of the location
   * @param lat the latitude of the location's point representation
   * @param lng the longitude of the location's point 
   */
  public BookLocation(String name, double lat, double lng) {
    this.name = name;
    coordinates = new double[] {lat, lng};
  }
  
  @Override
  public int hashCode() {
    return name.hashCode();
  }
}
