package Huffman;

import java.util.ArrayList;
import java.util.HashMap;

public class ChildNode extends Node{
	public ChildNode(Integer name, double prob) {
		this.name = name;
		this.prob = prob;
	}
	
	public HashMap<Integer, ArrayList<Integer>> getCode() {
		HashMap<Integer, ArrayList<Integer>> codes=new HashMap<Integer, ArrayList<Integer>>();
		codes.put(name, new ArrayList<Integer>());
		return codes;
	}
	
	public int getHeight() {
		return 1;
	}
	
	public boolean isLeaf() {
		return true;
	}

	@Override
	protected HashMap<Integer, ArrayList<Integer>> getCode(ArrayList<Integer> previousArray) {
		HashMap<Integer, ArrayList<Integer>> codes=new HashMap<Integer, ArrayList<Integer>>();
		codes.put(name,previousArray);
		return codes;	}
}
