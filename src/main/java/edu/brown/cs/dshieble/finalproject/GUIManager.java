package edu.brown.cs.dshieble.finalproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.sql.SQLException;
import java.io.IOException;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;
import edu.brown.cs.jc124.database.*;
import edu.brown.cs.qc14.parser.Parser;

/**
 * This class generates the localhost GUI.
 * @author dshieble
 *
 */
public final class GUIManager {

  private static final Gson GSON = new Gson();
  private static MarkovManager oldMan;
  private static AssetManager db;
  private static Parser p;



  /**
   * Prevents this class from being instantiated.
   */
  private GUIManager() {

  }

  /**
   * Initializes the GUI.
   * @param m the markov manager
   */
  public static void makeGUI() {
    try {
      db = new AssetManager();
    } catch (ClassNotFoundException|SQLException e) {
      db = null;
    }
    oldMan = null;
    p = new Parser();
    runSparkServer();
  }

  /**
   * Opens the GUI.
   */
  private static void runSparkServer() {
    Spark.setPort(5678);
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.get("/", new GetHandler(), new FreeMarkerEngine());
    Spark.post("/results", new ResultsHandler());
    Spark.post("/author", new AuthorHandler());
    Spark.post("/facet", new FacetHandler());
    Spark.post("/location", new LocationHandler());

  }

  /**
   * Handles the Page Load.
   * @author dshieble
   *
   */
  private static class GetHandler implements TemplateViewRoute {

    @Override
    /**
     * Handles Loading the Page.
     * @param req
     * @param res
     * @return
     */
    public ModelAndView handle(spark.Request req, Response res) {
      Map<String, Object>
      variables =  ImmutableMap.of(
          "title", "autocorrect");
      return new ModelAndView(variables, "main.ftl");
    }
  }

  /**
   * Handles the author Request.
   * @author dshieble
   *
   */
  private static class LocationHandler implements Route {

    @Override
    /**
     * Handles the author request
     * @param req
     * @param res
     * @return
     */
    public Object handle(final Request req, final Response res) {
      BookLocation[] locations = db.getAllLocations();
      //System.out.println(Arrays.toString(locations));
      Map<String, Object> variables = new ImmutableMap.Builder()
        .put("locations", locations)
        .build();
      return GSON.toJson(variables);
    }
  }

  /**
   * Handles the facet Request.
   * @author dshieble
   *
   */
  private static class FacetHandler implements Route {

    @Override
    /**
     * Handles the facet request
     * @param req
     * @param res
     * @return
     */
    public Object handle(final Request req, final Response res) {
      String[] facets = db.getAllFacets();
      Map<String, Object> variables = new ImmutableMap.Builder()
        .put("facets", facets)
        .build();
      return GSON.toJson(variables);
    }
  }

  /**
   * Handles the author Request.
   * @author dshieble
   *
   */
  private static class AuthorHandler implements Route {

    @Override
    /**
     * Handles the author request
     * @param req
     * @param res
     * @return
     */
    public Object handle(final Request req, final Response res) {
      String[] authors = db.getAllAuthors();
      Map<String, Object> variables = new ImmutableMap.Builder()
        .put("authors", authors)
        .build();
      return GSON.toJson(variables);
    }
  }


  /**
   * Handles the results Request.
   * @author dshieble
   *
   */
  private static class ResultsHandler implements Route {

    @Override
    /**
     * Handles the results request
     * @param req
     * @param res
     * @return
     */
    public Object handle(final Request req, final Response res) {
      QueryParamsMap qm = req.queryMap();
      MarkovManager man = null;
      if (qm.value("unchanged").equals("yes")) {
        man = oldMan;
      } else {

        int i = 0;
        //priority words
        List<String> priorityWords = new ArrayList<String>();
        while (qm.value("f" + i) != null) {
          String word = qm.value("f" + i).trim();
          String[] syns = db.getSynonyms(word);
          for (String s : syns) {
            //System.out.println(s);
            priorityWords.add(s.trim());
          }
          i++;
        }
        String[] pwArray = priorityWords
            .toArray(new String[priorityWords.size()]);
        i = 0;
        //locations
        Set<String> locationNames = new HashSet<String>();
        if (qm.value("type").equals("location")) {
          while (qm.value("l" + i) != null) {
            locationNames.add(qm.value("l" + i).trim());
            i++;
          }
          i = 0;
        }
        
        //book facets
        Set<String> bookFacets = new HashSet<String>();
        if (qm.value("type").equals("type")) {
          while (qm.value("bf" + i) != null) {
            bookFacets.add(qm.value("bf" + i).trim());
            i++;
          }
        }
//        System.out.println(priorityWords.size());
//        System.out.println(bookFacets.size());
//        System.out.println(locationNames.size());
//        System.out.println(Integer.parseInt(qm.value("date_start")));
//        System.out.println(Integer.parseInt(qm.value("date_end")));
        //get text here
        List<String[]> text = new ArrayList<String[]>();
        if (qm.value("type").equals("none")) {
          text = db.loadBooksByAuthorOrAttributes(
            qm.value("author").trim(),
            bookFacets,
            locationNames,
            1399,
            1399);
        } else {
          text = db.loadBooksByAuthorOrAttributes(
            qm.value("author").trim(),
            bookFacets,
            locationNames,
            Integer.parseInt(qm.value("date_start")),
            Integer.parseInt(qm.value("date_end")));      
        }
        man = new MarkovManager(text, pwArray);
        oldMan = man;
      }
      String str = man.generateSentence(4);
      if (str == null) {
        str = "ERROR: No Sentence";
      }
      String[] sentenceArray = str
        .toLowerCase()
        .replaceAll("[^a-z ]", "")
        .split(" ");
      String json = "";
      try {
        json = p.toJsonString(sentenceArray);
      } catch (Exception e) {
        json = "";
      }
      Map<String, Object> variables = new ImmutableMap.Builder()
        .put("sentence", str)
        .put("tree", json)
        .build();
      return GSON.toJson(variables);
    }
  }
  //      System.out.println(qm.value("author"));
  //      System.out.println(qm.value("date_start"));
  //      System.out.println(qm.value("date_end"));
  //      while (qm.value("l" + i) != null) {
  //System.out.println(qm.value("l" + i));
//        i++;
//      }
//      i = 0;
  
//System.out.println(qm.value("author"));
//System.out.println(text.size());
//System.out.println(locationNames.size());
//System.out.println(man.getHash().size());
}