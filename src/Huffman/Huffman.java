package Huffman;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;

public class Huffman {

	public static HashMap<Integer, ArrayList<Integer>> getHuffman(double[] probabilities) {
		
		LinkedList<Node> huffmanList = new LinkedList<Node>();
		for(Integer i=0; i<probabilities.length; i++) {
			if(probabilities[i]!=0){
				ChildNode node = new ChildNode((Integer)i,probabilities[i]);
				huffmanList.add(node);
			}
		}
		Collections.sort(huffmanList);
		
		while(huffmanList.size() !=1) {
			Node minor2 = huffmanList.remove(1);
			Node minor1 = huffmanList.remove(0);
			FatherNode n = new FatherNode(minor1,minor2);
			huffmanList.add(n);
			Collections.sort(huffmanList);
		}
		
		return huffmanList.get(0).getCode();
	}
	
	public static Node getHuffmanTree(double[] probabilities) {

		LinkedList<Node> huffmanList = new LinkedList<Node>();
		for(Integer i=0; i<probabilities.length; i++) {
			if(probabilities[i]!=0){
				ChildNode node = new ChildNode((Integer)i,probabilities[i]);
				huffmanList.add(node);
			}
		}
		Collections.sort(huffmanList);
		
		while(huffmanList.size() !=1) {
			Node minor2 = huffmanList.remove(1);
			Node minor1 = huffmanList.remove(0);
			FatherNode n = new FatherNode(minor1,minor2);
			huffmanList.add(n);
			Collections.sort(huffmanList);
		}
		
		return huffmanList.get(0);
}
	
	

}
