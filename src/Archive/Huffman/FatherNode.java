package Huffman;

import java.util.ArrayList;
import java.util.HashMap;

public class FatherNode extends Node{
	Node n1,n2;
	
	public FatherNode(Node n1, Node n2) {
		this.n1 = n1;
		this.n2 = n2;
		this.name = Node.cont++;
		if(n1 != null && n2 != null) {
			this.prob = n1.getProb() + n2.getProb();
		}else {
			if(n1 == null) {
				this.prob = n2.getProb();
			}else {
				this.prob = n1.getProb();
			}
		}
		
	}

	public HashMap<Integer, ArrayList<Integer>> getCode() {
		ArrayList<Integer> array = new ArrayList<Integer>();
		HashMap<Integer, ArrayList<Integer>> codes = new HashMap<Integer, ArrayList<Integer>>();
		codes.putAll(this.getCode(array));
		return codes;
	}
	
	protected HashMap<Integer, ArrayList<Integer>> getCode(ArrayList<Integer> previousArray) {
		ArrayList<Integer> array = new ArrayList<Integer>(previousArray);
		HashMap<Integer, ArrayList<Integer>> codes=new HashMap<Integer, ArrayList<Integer>>();
		array.add(0);
		codes.putAll(n1.getCode(array));
		ArrayList<Integer> array2 = new ArrayList<Integer>(previousArray);
		array2.add(1);
		codes.putAll(n2.getCode(array2));
		return codes;
	}
	
	public int getHeight() { 
		if(n1.getHeight()>n2.getHeight()) {
			return n1.getHeight()+1;
		}
		return n2.getHeight()+1;
	}
	
	public boolean isLeaf() {
		return false;
	}
	
	public Node getLeftSon() {
		return n1;
	}
	
	public Node getRightSon() {
		return n2;
	}
}
