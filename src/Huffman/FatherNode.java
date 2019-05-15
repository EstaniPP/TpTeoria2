package Huffman;

import java.util.HashMap;

public class FatherNode extends Node{
	Node n1,n2;
	
	public FatherNode(Node n1, Node n2) {
		this.n1 = n1;
		this.n2 = n2;
		this.name = Node.cont++;
		this.prob = n1.getProb() + n2.getProb();
	}

	public HashMap<Integer, Integer> getCode() {
		HashMap<Integer, Integer> codes=new HashMap<Integer, Integer>();
		codes.putAll(this.getCode(0,1));
		return codes;
	}
	
	@Override
	protected HashMap<Integer, Integer> getCode(Integer lastcode, int code) {
		HashMap<Integer, Integer> codes=new HashMap<Integer, Integer>();
		codes.putAll(n1.getCode(10*lastcode+code,0));
		int exp;
		codes.putAll(n2.getCode(10*lastcode+code,1));
		return codes;
	}
	
	@Override
	public int getHeight() {
		if(n1.getHeight()>n2.getHeight()) {
			return n1.getHeight()+1;
		}
		return n2.getHeight()+1;
	}
	
}
