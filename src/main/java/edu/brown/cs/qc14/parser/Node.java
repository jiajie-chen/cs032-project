package edu.brown.cs.qc14.parser;

import java.util.ArrayList;
import java.util.Arrays;

public class Node {
	
	private String _parent;
	private String _name;
	private ArrayList<Node> _children;
	
	public Node(String name, String parent) {
		_name = name;
		_parent = parent;
	}
	
	public Node(String name, String parent, Node left) {
		_name = name;
		_parent = parent;
		_children = new ArrayList<Node>(Arrays.asList(left));
	}
	
	public Node(String name, String parent, Node left, Node right) {
		_name = name;
		_parent = parent;
		_children = new ArrayList<Node>(Arrays.asList(left, right));
	}
}
