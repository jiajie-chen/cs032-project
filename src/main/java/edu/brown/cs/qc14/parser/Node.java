package edu.brown.cs.qc14.parser;

import java.util.ArrayList;
import java.util.Arrays;

public class Node {
	
	private String parent;
	private String name;
	private ArrayList<Node> children;
	
	public Node(String name, String parent) {
		name = name;
		parent = parent;
	}
	
	public Node(String name, String parent, Node left) {
		name = name;
		parent = parent;
		children = new ArrayList<Node>(Arrays.asList(left));
	}
	
	public Node(String name, String parent, Node left, Node right) {
		name = name;
		parent = parent;
		children = new ArrayList<Node>(Arrays.asList(left, right));
	}
}
