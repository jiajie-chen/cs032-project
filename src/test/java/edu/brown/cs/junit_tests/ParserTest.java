package edu.brown.cs.junit_tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.brown.cs.qc14.parser.*;

public class ParserTest {
	
	private static Parser _parser;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		_parser = new Parser();
	}
	
	@Test
	public void testPointersToString() {
		Pointers w1 = new Pointers(null, null, "w1", 0.0);
		Pointers w2 = new Pointers(null, null, "w2", 0.0);
		Pointers w3 = new Pointers(null, null, "w3", 0.0);
		Pointers C = new Pointers(w3, null, "C", 0.0);
		ArrayList<String> res = _parser.pointerToStrings(C);
		assertTrue(res.size() == 1 && res.get(0).equals("w3"));
		Pointers D = new Pointers(w1, null, "D", 0.0);
		Pointers E = new Pointers(w2, null, "E", 0.0);
		Pointers B = new Pointers(D, E, "B", 0.0);
		res = _parser.pointerToStrings(B);
		assertTrue(res.size() == 2 && res.get(0).equals("w1") && res.get(1).equals("w2"));
		Pointers A = new Pointers(B, C, "A", 0.0);
		res = _parser.pointerToStrings(A);
		assertTrue(res.size() == 3);
	}
	
	@Test
	public void test() {
		
	}

}
