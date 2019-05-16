package Huffman;

import java.util.HashMap;

public abstract class Node implements Comparable<Node>{
	Integer name;
	static Integer cont = 257;
	double prob;
	
	public abstract HashMap<Integer,Integer> getCode();
	
	protected abstract HashMap<Integer,Integer> getCode(Integer lastcode,int code);
	
	public double getProb() {
		return prob;
	}
	
	public int getName() {
		return name;
	}
	
	public abstract int getHeight();
		
	public int compareTo(Node o) {
		Node node = o;
		if(this.getProb()==node.getProb())
			if(this.getHeight()!=node.getHeight()) {
				return (int) (-this.getHeight()+node.getHeight());
			}else{
				return (int) (this.getName()-node.getName()-1);
		}
		return (int) (this.getProb()-node.getProb()-1);
	}
}