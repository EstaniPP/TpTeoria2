package Huffman;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;

import Others.ImageParser;

public class Huffman {

	public static HashMap<Integer, ArrayList<Integer>> getHuffman(Double[] probabilities) {
		
		LinkedList<Node> huffmanList = new LinkedList<Node>();
		

		for(Integer i=0; i < probabilities.length; i++) {
			if(probabilities[i] != 0){
				ChildNode node = new ChildNode((Integer) i, probabilities[i]);
				huffmanList.add(node);

			}
		}
		Collections.sort(huffmanList);
		
		// delete
		
		if(huffmanList.size() == 1) {
			Node a = huffmanList.remove(0);
			FatherNode fn = new FatherNode(a, new ChildNode(100 - a.getName(), 0d));
			huffmanList.add(fn);
		}
		
		// delete
		
		
		while(huffmanList.size() != 1) {
			Node minor2 = huffmanList.remove(1);
			Node minor1 = huffmanList.remove(0);
			FatherNode n = new FatherNode(minor1,minor2);
			huffmanList.add(n);
			Collections.sort(huffmanList);
		}
		
		return huffmanList.get(0).getCode();
	}
	
	public static Node getHuffmanTree(Double[] probabilities) {

		LinkedList<Node> huffmanList = new LinkedList<Node>();

		for(Integer i=0; i<probabilities.length; i++) {
			if(probabilities[i]!=0){
				ChildNode node = new ChildNode((Integer)i, probabilities[i]);
				huffmanList.add(node);
				
			}
		}
		Collections.sort(huffmanList);
		
		// delete
		if(huffmanList.size() == 1) {
			Node a = huffmanList.remove(0);
			FatherNode fn = new FatherNode(a, new ChildNode(100 - a.getName(), 0d));
			huffmanList.add(fn);
		}
		// delete
		
		while(huffmanList.size() !=1) {
			Node minor2 = huffmanList.remove(1);
			Node minor1 = huffmanList.remove(0);
			FatherNode n = new FatherNode(minor1,minor2);
			huffmanList.add(n);
			Collections.sort(huffmanList);
		}
		
		return huffmanList.get(0);
	}
	
	/*
	  * @desc encodes a picture using Huffman's method
	  * @param HashMap<Integer, ArrayList<Integer>> codes - code for each symbol
	  * @param ImageParser - the image to be encoded
	  * @return byte[] - picture encoded
	*/
	public static ArrayList<Byte> encode(HashMap<Integer, ArrayList<Integer>> codes, ImageParser img) {
		// byte arraylist to fill up
		ArrayList<Byte> byteCodes = new ArrayList<Byte>();
		// byte to fill
		byte buffer = 0;
		int bufferPos = 0;
		// iterate the whole picture
		for(int i = 0; i < img.getHeight(); i++) {
			for(int j = 0; j < img.getWidth(); j++) {
				// get the symbol
				Integer symbol = img.getRGB(j, i).getRed();
				// get the code of the symbol
				ArrayList<Integer> symbolCode = codes.get(symbol);
				// put it in a byte
				for(int bit : symbolCode) {
					buffer = (byte) (buffer << 1);
					bufferPos++;
					if(bit == 1) {
						buffer = (byte) (buffer | 1);
					}
					if (bufferPos == 8) {
						byteCodes.add(buffer);
						buffer = 0;
						bufferPos = 0;
					}
				}
			} 
		}
		if ((bufferPos < 8) && (bufferPos != 0)) {
			buffer = (byte) (buffer << (8 - bufferPos));
			byteCodes.add(buffer);
		}
		return byteCodes;
	}
	
	/*
	  * @desc decodes a picture using Huffman's method
	  * @param Node hTree - Huffman's tree
	  * @param ArrayList<Byte> bytes - the encoded picture
	  * @return ArrayList<Integer> - decoded
	*/
	
	@SuppressWarnings("unused")
	public static ArrayList<Integer> decode(Node hTree, ArrayList<Byte> bytes, int blockSize) {
		// initialize araylist
		ArrayList<Integer> ret = new ArrayList<Integer>();
		// hTree is the tree root
		// i need another temporal node to iterate
		Node tempNode = hTree;
		// set the mask on 10000000
		byte mask = (byte) (1 << 7);
		// iterate each byte
		int qty = 0;
		for(byte b : bytes) {
			// iterate the byte itself
			int bufferPos = 0;
			while(bufferPos < 8) {
				if(tempNode.isLeaf()) {
					// as i found a symbol, I need to start from the root again for next symbol
					ret.add(tempNode.getName());
					qty++;
					tempNode = hTree;
					//bufferPos++;
				}else {
					if((b & mask) == mask) {
						// it is a 1 -> lets get the node's right child
						tempNode = ((FatherNode) tempNode).getRightSon();
					}else {
						// it is a 0 -> lets get the node's left child
						tempNode = ((FatherNode) tempNode).getLeftSon();
					}
					bufferPos++;
					b = (byte) (b << 1);
				}
				if(qty == blockSize)
					break;
			}
			if(tempNode.isLeaf()) {
				ret.add(tempNode.getName());
				qty++;
				tempNode = hTree;
			}
		}
		return ret;
	}
}
