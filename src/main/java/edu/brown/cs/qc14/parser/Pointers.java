package edu.brown.cs.qc14.parser;

public class Pointers {
	
	private Pointers _left, _right;
	private String _label;
	private double _mu;
	public Pointers(Pointers left, Pointers right, String label, double mu) {
		_left = left;
		_right = right;
		_label = label;
		_mu = mu;
	}
	
	public boolean isTerminal() {
		return _left == null && _right == null;
	}
	
	// given that node is non-terminal
	public boolean isUnary() {
		return _right == null;
	}
	
	public String getLabel() {
		return _label;
	}
	
	public Pointers getLeft() {
		return _left;
	}
	
	public Pointers getRight() {
		return _right;
	}
	
	public double getMu() {
		return _mu;
	}
	
	public void setLabel(String label) {
		_label = label;
	}
}
