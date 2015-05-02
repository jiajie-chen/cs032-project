package edu.brown.cs.junit_tests;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import org.junit.BeforeClass;
import org.junit.Test;
// truly Beauty Reach out be forgotten, There love a Dagger
// 473 - 440 - 248
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
	public void testPreprocess() {
		String[] input = {"dfghkjdfg", ",", ".", "me", "it", "popopopo"};
		input = _parser.preprocess(input);
		String[] result = {"*UNK*", ",", ".", "me", "it", "*UNK*"};
		for (int i=0; i<input.length; i++) {
			assertTrue(input[i].equals(result[i]));
		}
	}

	@Test
	public void testSimpleSentences() {
//		
//		System.out.println(_parser.testParsing(_parser.preprocess("he believes that she thinks that i say that it is a bump .".split(" "))));
//		System.out.println(_parser.testParsing(_parser.preprocess("shake off this Downey sleepe , deaths counterfeit , and look on death it selfe .".split(" "))));
//		System.out.println(_parser.testParsing(_parser.preprocess("oops .".split(" "))));
//		System.out.println(_parser.testParsing(_parser.preprocess("i have it .".split(" "))));
//		System.out.println(_parser.parseSentence(_parser.preprocess("i have it .".split(" "))));
//		System.out.println(_parser.parseSentence(_parser.preprocess("he believes that she thinks that i say that it is a bump .".split(" "))));
//		System.out.println(_parser.parseSentence(_parser.preprocess("shake off this Downey sleepe , deaths counterfeit , and look on death it selfe .".split(" "))));
//		System.out.println(_parser.parseSentence(_parser.preprocess("oops .".split(" "))));
	}
	
	@Test
	public void testMoreComplicated() {
		
	}
	
	@Test
	public void testShort() {
		
	}
	
	@Test
	public void testToTreeData() {
		_parser.parseSentence(("this is a sentence longer than 28 , this is a sentence longer than 28 , "
				+ "this is a sentence longer than 28 , this is a sentence longer than 28 , ").split(" "));
	}
	
	@Test
	public void testReplaceUNK() {
		Pointers w1 = new Pointers(null, null, "*UNK*", 0.0);
		Pointers w2 = new Pointers(null, null, "*UNK*", 0.0);
		Pointers w3 = new Pointers(null, null, "*UNK*", 0.0);
		Pointers C = new Pointers(w3, null, "C", 0.0);
		Pointers D = new Pointers(w1, null, "D", 0.0);
		Pointers E = new Pointers(w2, null, "E", 0.0);
		Pointers B = new Pointers(D, E, "B", 0.0);
		Pointers A = new Pointers(B, C, "A", 0.0);
		String[] terminals = new String[] {"d", "w2", "c"};
		_parser.replaceUNKs(A, terminals);
		assertTrue(w1.getLabel().equals("d"));
		assertTrue(w2.getLabel().equals("w2"));
		assertTrue(w3.getLabel().equals("c"));
	}
	
	@Test
	public void testToJsonString() {
		assertTrue(!_parser.toJsonString("i have jpjpjpjp .".split(" ")).contains("*UNK*"));
		System.out.println(_parser.normalParsing("truly Beauty Reach out be forgotten, There love a Dagger".split(" ")));
		System.out.println(_parser.toJsonString("truly Beauty Reach out be forgotten, There love a Dagger".split(" ")));
	}
}
