package Huffman;

import java.util.HashMap;

public class ChildNode extends Node{
	public ChildNode(Integer name, double prob) {
		this.name = name;
		this.prob = prob;
	}
	
	public HashMap<Integer, Integer> getCode() {
		HashMap<Integer, Integer> codes=new HashMap<Integer, Integer>();
		codes.put(name,1);
		return codes;
	}
	
	@Override
	protected HashMap<Integer, Integer> getCode(Integer lastcode, int code) {
		HashMap<Integer, Integer> codes=new HashMap<Integer, Integer>();
		codes.put(name,code+10*lastcode);
		return codes;
	}
	
	public int getHeight() {
		return 1;
	}
	
}
