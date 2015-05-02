package edu.brown.cs.qc14.parser;

import java.util.ArrayList;
import java.util.Arrays;

public class Node {
	
	private String parent;
	private String name;
	private ArrayList<Node> children;
	
	public Node(String name0, String parent0) {
		name = name0;
		parent = parent0;
	}
	
	public Node(String name0, String parent0, Node left) {
		name = name0;
		parent = parent0;
		children = new ArrayList<Node>(Arrays.asList(left));
	}
	
	public Node(String name0, String parent0, Node left, Node right) {
		name = name0;
		parent = parent0;
		children = new ArrayList<Node>(Arrays.asList(left, right));
	}
}
