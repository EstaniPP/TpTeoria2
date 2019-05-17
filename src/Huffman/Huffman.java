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
		for(int i = 0; i < 500; i++) {
			for(int j = 0; j < 500; j++) {
				// get the symbol
				Integer symbol = img.getRGB(i, j).getRed();
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
			if ((bufferPos < 8) && (bufferPos != 0)) {
				buffer = (byte) (buffer << (8 - bufferPos));
				byteCodes.add(buffer);
			}
		}
		
		return byteCodes;
	}

}
