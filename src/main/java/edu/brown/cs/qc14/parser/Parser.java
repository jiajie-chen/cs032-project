package edu.brown.cs.qc14.parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.lang.NullPointerException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Set;

/*
 * taking in a sentence, returning parsing result
 */
public class Parser {


	private HashMap<String, HashMap<String, Double>> _rules;
	private HashMap<String, Integer> _counts;
	private HashMap[][] _tree;
	private int _MAX_LENGTH = 25;
	private HashSet<String> _clauseTags, _phraseTags, _verbTags, _prepTags;
	
	public Parser() {
		_rules = new HashMap<String, HashMap<String, Double>>();
		_counts = new HashMap<String, Integer>();
		_clauseTags = new HashSet<String>(Arrays.asList("S", "SBAR", "SBARQ", "SINV", "SQ"));
		_phraseTags = new HashSet<String>(Arrays.asList("NP", "PP", "VP"));   //////
		_verbTags = new HashSet<String>(Arrays.asList("VB", "VBD", "VBG", "VBN", "VBP", "VBZ"));
		_prepTags = new HashSet<String>(Arrays.asList("IN", "TO", "CC"));
	}
	
	public ArrayList<Pointers> partialParse(Pointers top) {
		ArrayList<Pointers> subTrees = new ArrayList<Pointers>();
		subTrees.add(top.getLeft());
		// under while loop
		boolean ready = false;
		while (!ready) {
			ready = true;
			// if containing _claseTags or "_", break it
			ArrayList<Pointers> temp = new ArrayList<Pointers>();
			for (Pointers p : subTrees) {
				if (_clauseTags.contains((p.getLabel())) || p.getLabel().contains("_")) {
					ready = false;
					temp.add(p.getLeft());
					if (p.getRight() != null) {
						temp.add(p.getRight());
					}
				} else {
					temp.add(p);
				}
			}
			subTrees.addAll(temp);
			temp.clear();
			
			// if NP, VP..., call another method
			// 1) VP -> VB NP
			// 2) NP -> NN NN
			// 3) VP -> VP NP
			for (Pointers p : subTrees) {
				if (_phraseTags.contains(p.getLabel())) {
					String left = p.getLeft().getLabel();
					String right = p.getRight().getLabel();
					if ((_phraseTags.contains(left) || _verbTags.contains(left))
							&& (_phraseTags.contains(right) || _verbTags.contains(right))) {
						ready = false;
						temp.add(p.getLeft());
						if (p.getRight() != null) {
							temp.add(p.getRight());
						}
					} else {
						temp.add(p);
					}
				} else {
					temp.add(p);
				}
			}
			subTrees.addAll(temp);
		}
		return subTrees;
	}
	
	// decode to strings, call another method
	// group nodes with _prepTags
	public ArrayList<ArrayList<String>> decodePartialParse(ArrayList<Pointers> subTrees) {
		ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
		ListIterator<Pointers> iter = subTrees.listIterator();
		while (iter.hasNext()) {
			Pointers p = iter.next();
			if (_verbTags.contains(p.getLabel())) {
				Pointers q = iter.next();
				if (_verbTags.contains(q.getLabel())) {
					res.add(new ArrayList<String>(Arrays.asList(p.getLabel(), q.getLabel())));
				} else {
					iter.previous();
				}
			} else if (_prepTags.contains(p.getLabel())) {
				ArrayList<String> temp = this.pointerToStrings(p);
				p = iter.next();
				temp.addAll(this.pointerToStrings(p));
				res.add(temp);
			} else {
				res.add(this.pointerToStrings(p));
			}
		}
		return res;
	}
	
	public ArrayList<String> pointerToStrings(Pointers p) {
		ArrayList<String> res = new ArrayList<String>();
		if (p.isTerminal()) {
			res.add(p.getLabel());
			return res;
		}
		res.addAll(this.pointerToStrings(p.getLeft()));
		if (p.getRight() != null) {
			res.addAll(this.pointerToStrings(p.getRight()));
		}
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ArrayList<String>> parseSentence(String[] terminals) {
		ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
		if (terminals.length > _MAX_LENGTH) {
			res.add(new ArrayList<String>(Arrays.asList("*IGNORE*")));
			return res;
			//return "*IGNORE*";
		} else {
			_tree = new HashMap[terminals.length][terminals.length];
			for (int m=1; m <= terminals.length; m++) {
				for (int n=0; n <= terminals.length-m; n++) {
					this.fillCell(n, n+m, terminals);
				}
			}
			HashMap<String, Pointers> root = _tree[terminals.length-1][0];
			if (root.containsKey("TOP")) {
				return this.decodePartialParse(this.partialParse(root.get("TOP")));
				//return this.debinarization(root.get("TOP"));
			} else {
				res.add(new ArrayList<String>(Arrays.asList("No Parsing")));
				return res;
				//return "No Parsing";
			}
		}
	}
	
	/*
	 * cell -- list of Symbols
	 * parse tree -- [[C, C, C], [C, C], [C]]  a list of levels of cells
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void parse(String filepath, String outputpath) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(outputpath, "UTF-8");
		BufferedReader reader;
		Path path = FileSystems.getDefault().getPath(filepath, "");
		try {
			reader = Files.newBufferedReader(path);
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] terminals = line.split(" ");
				
				// tree : {label: [left, right]}
				// values : {label: mu}
				if (terminals.length > 25) {
					System.out.println("*IGNORE*");
					writer.println("*IGNORE*");
				} else {
					_tree = new HashMap[terminals.length][terminals.length];
					for (int m=1; m <= terminals.length; m++) {
						for (int n=0; n <= terminals.length-m; n++) {
							this.fillCell(n, n+m, terminals);
						}
					}
					
					HashMap<String, Pointers> root = _tree[terminals.length-1][0];
					
					if (root.containsKey("TOP")) {
						String result = this.debinarization(root.get("TOP"));
						System.out.println(result);
						writer.println(result);
					}
				}
			}
			reader.close();
		} catch (IOException e) {
			System.err.println("ERROR: cannot open file");
		}
		writer.close();
	}
	
	// cell(i,k)
	@SuppressWarnings("unchecked")
	public void fillCell(int i, int k, String[] terminals) {
		HashMap<String, Pointers> cell = new HashMap<String, Pointers>();
		_tree[k-i-1][i] = cell;
		if (k == i+1) {
			// terminal rules
			cell.put(terminals[i], new Pointers(null, null, terminals[i], 1.0));
		} else {
			// binary rules
			// mid point j
			for (int j=i+1; j<k; j++) {
				HashMap<String, Pointers> leftCell = _tree[j-i-1][i];
				HashMap<String, Pointers> rightCell = _tree[k-j-1][j];
				for (String rhs1 : leftCell.keySet()) {
					try {
						HashMap<String, Double> ruleValues = _rules.get(rhs1);
						for (String s : ruleValues.keySet()) {
							String[] parts = s.split(" ");
							if (parts.length == 2) {
								try {
									Pointers right = rightCell.get(parts[1]);
									Pointers left = leftCell.get(rhs1);
									double mu = ruleValues.get(s) * left.getMu() * right.getMu();
									try {
										if (mu > cell.get(parts[0]).getMu()) {
											cell.put(parts[0], new Pointers(left, right, parts[0], mu));
										}
									} catch (NullPointerException e) {
										cell.put(parts[0], new Pointers(left, right, parts[0], mu));
									}
								} catch (NullPointerException e) {
								}
							}
						}
					} catch (NullPointerException e) {
					}
				}
			}
		}
		// unary rules
		boolean updated = true;
		while (updated) {
			updated = false;
			ArrayList<String> keySet = new ArrayList<String>();
			keySet.addAll(cell.keySet());
			for (String rhs : keySet) {
				try {
					HashMap<String, Double> ruleValues = _rules.get(rhs);
					for (String lhs : ruleValues.keySet()) {
						// if unary rule
						if (lhs.split(" ").length == 1) {
							double mu = ruleValues.get(lhs) * cell.get(rhs).getMu();
							try {
								if (mu > cell.get(lhs).getMu()) {
									cell.put(lhs, new Pointers(cell.get(rhs), null, lhs, mu));
									updated = true;
								}
							} catch (NullPointerException e) {
								cell.put(lhs, new Pointers(cell.get(rhs), null, lhs, mu));
								updated = true;
							}
						}
					}
				} catch (NullPointerException e) {
				}
			}
		}
	}
	
	/*
	 * rule -- [lhs, rhs1, rhs2, prob]
	 * rules -- {rhs1 : {"lhs rhs2" : mu, ...}, ...}
	 */
	public void buildRules(String filepath) {
		BufferedReader reader;
		Path path = FileSystems.getDefault().getPath(filepath, "");
		// get total count for each non-terminals
		try {
			reader = Files.newBufferedReader(path);
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(" ");
				try{
					_counts.put(parts[1], _counts.get(parts[1]) + Integer.parseInt(parts[0]));
				} catch (NullPointerException e) {
					_counts.put(parts[1], Integer.parseInt(parts[0]));
				}
			}
			reader.close();
		} catch (IOException e) {
			System.err.println("ERROR: cannot open file");
		}
		try {
			reader = Files.newBufferedReader(path);
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(" ");
				double prob = Double.parseDouble(parts[0]) / _counts.get(parts[1]);
				if (parts.length == 5) {
					try {
						HashMap<String, Double> value = _rules.get(parts[3]);
						value.put(parts[1] + " " + parts[4], prob);
					} catch (NullPointerException e) {
						HashMap<String, Double> value = new HashMap<String, Double>();
						value.put(parts[1] + " " + parts[4], prob);
						_rules.put(parts[3], value);
					}
				} else {
					try {
						HashMap<String, Double> value = _rules.get(parts[3]);
						value.put(parts[1], prob);
					} catch (NullPointerException e) {
						HashMap<String, Double> value = new HashMap<String, Double>();
						value.put(parts[1], prob);
						_rules.put(parts[3], value);
					}
				}
			}
			reader.close();
		} catch (IOException e) {
			System.err.println("ERROR: cannot open file");
		}
	}
	
	public String debinarization(Pointers node) {
		if (node.isTerminal()) {
			return node.getLabel();
		}
		if (node.getLabel().contains("_")) {
			return this.debinarization(node.getLeft()) + " " + this.debinarization(node.getRight());
		}
		String repr = "(" + node.getLabel() + " ";
		if (node.isUnary()) {
			repr += this.debinarization(node.getLeft());
		} else {
			repr += this.debinarization(node.getLeft());
			repr += " ";
			repr += this.debinarization(node.getRight());
		}
		repr += ")";
		return repr;
	}
}