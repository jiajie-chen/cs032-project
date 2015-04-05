package edu.brown.cs.dshieble.finalproject;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
 * The Main Class for out final project.
 *
 */
public final class Main {


  /**
   * Prevents this class from being instantiated.
   */
  private Main() {

  }

  /**
   * Mainline of code. Parses user input and finds path between actors.
   * @param args CL args
   */
  public static void main(final String[] args) {
    System.out.println("working");
    GUIManager.makeGUI();
  }
    //gui inputs
}

