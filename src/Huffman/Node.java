package Huffman;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Node implements Comparable<Node>{
	Integer name;
	static Integer cont = 257;
	Double prob;
	
	public abstract HashMap<Integer,ArrayList<Integer>> getCode();
	
	protected abstract HashMap<Integer,ArrayList<Integer>> getCode(ArrayList<Integer> previousArray);
	
	public double getProb() {
		return prob;
	}
	
	public int getName() {
		return name; 
	}
	
	public abstract int getHeight();
		
	public int compareTo(Node o) {
		Node node = o;
		/*if(this.getProb()==node.getProb())
			if(this.getHeight()!=node.getHeight()) {
				System.out.println("1");
				return (int) (-this.getHeight()+node.getHeight());
			}else{
				System.out.println("2");
				return (int) (this.getName()-node.getName()-1);
		}
		System.out.println("3");
		return (int) (this.getProb()-node.getProb()-1);
		*/
		if(this.getProb()==node.getProb()) {
			return 0;
		}else {
			if(this.getProb() > node.getProb()) {
				return 1;
			}else {
				return -1;
			}
		}
	}
	
	public abstract boolean isLeaf();
}