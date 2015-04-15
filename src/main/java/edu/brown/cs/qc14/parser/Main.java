package edu.brown.cs.qc14.parser;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;


public class Main {
	
	public static void main(String[] args) {
		Parser parser = new Parser();
		parser.buildRules(args[0]);
		try {
			parser.parse(args[1], args[2]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
