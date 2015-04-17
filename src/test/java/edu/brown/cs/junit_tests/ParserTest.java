package edu.brown.cs.junit_tests;

import org.junit.Test;

import edu.brown.cs.qc14.parser.Parser;



public class ParserTest {
	
	//@Test
	public void test() {
		Parser parser = new Parser();
		parser.buildRules("edu.brown.cs.qc14.parser.wsj2-21");
		String[] terminals = "they never considered themselves to be anything else .".split(" ");
		System.out.println("****  " + parser.parseSentence(terminals));
	}

}
