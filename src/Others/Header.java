package Others;

import java.io.Serializable;
import java.util.HashMap;

public class Header implements Serializable {

	Boolean[] encoders;
	
	Integer[] blockSizeEncoded;
	
	HashMap<Integer, Double[]> probabilities;
	
	Integer size;
	
	int wholeX;
	int wholeY;
	
	int blockHeight = 500;
	int blockWidth = 500;
	
	Integer[] xSize;
	Integer[] ySize;
	
	public Header(int size, int wx, int wy) {
		
		encoders = new Boolean[size];
		blockSizeEncoded = new Integer[size];
		
		xSize = new Integer[size];
		ySize = new Integer[size];
		
		this.size = size;
		probabilities = new HashMap<Integer, Double[]>();
		
		wholeX = wx;
		wholeY = wy;
		
	}
	
	//set true in index's array and add the probilities to the hash
	public void setHuffman(Integer index, Double[] prob) {
		encoders[index]=true;
		probabilities.put(index, prob);
	}
	
	//set false in index's array
	public void setRLC(int index) {
		encoders[index]=false;
	}
	
	//returns true if the encoder is huffman and false if encoder is RLC
	public Boolean getEncoder(int index) {
		return encoders[index];
	}
	
	//returns block's probabilities
	public Double[] getProbs(Integer index) {
		return probabilities.get(index);
	}
	
	public int getBlockSize(int block) {
		return xSize[block] * ySize[block];
	}
	
	public int getQtyBlocks() {
		return size;
	}
	public int getBlockSizeEncoded(int i) {
		return blockSizeEncoded[i];
	}
	public void setBlockSizeEncoded(int i, int size) {
		blockSizeEncoded[i] = size;
	}
	
	public Integer[] getBlockSizes() {
		return blockSizeEncoded;
	}
	
	public int getX(int block) {
		return xSize[block];
	}
	public int getY(int block) {
		return ySize[block];
	}
	
	public void setXY(int block, int x, int y) {
		xSize[block] = x;
		ySize[block] = y;
	}
	public int getWholeX() {
		return wholeX;
	}
	
	public int getWholeY() {
		return wholeY;
	}
	
}
