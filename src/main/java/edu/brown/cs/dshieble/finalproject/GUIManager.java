package edu.brown.cs.dshieble.finalproject;

import java.util.Arrays;
import java.util.Map;

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


/**
 * This class generates the localhost GUI.
 * @author dshieble
 *
 */
public final class GUIManager {

  private static final Gson GSON = new Gson();
  private static MarkovManager man;


  /**
   * Prevents this class from being instantiated.
   */
  private GUIManager() {

  }

  /**
   * Initializes the GUI.
   * @param m the markov manager
   */
  public static void makeGUI(MarkovManager m) {
    man = m;
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
      System.out.println(qm.value("author"));
      System.out.println(qm.value("date_start"));
      System.out.println(qm.value("date_end"));
      int i = 0;
      while (qm.value("l" + i) != null) {
        System.out.println(qm.value("l" + i));
        i++;
      }
      String str = "spark told me this!";
      Map<String, Object> variables = new ImmutableMap.Builder()
        .put("sentence", str)
        .build();
      return GSON.toJson(variables);
    }
  }

}