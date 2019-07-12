package Huffman;

import java.util.ArrayList;
import java.util.HashMap;

public class ChildNode extends Node{
	public ChildNode(Integer name, Double prob) {
		this.name = name;
		this.prob = prob;
	}
	
	public HashMap<Integer, ArrayList<Integer>> getCode() {
		HashMap<Integer, ArrayList<Integer>> codes=new HashMap<Integer, ArrayList<Integer>>();
		ArrayList<Integer> ar = new ArrayList<Integer>();
		//ar.add(0);
		codes.put(name, ar);
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
		return codes;	
	}
}
