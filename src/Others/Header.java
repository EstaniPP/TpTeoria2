package Others;

import java.io.Serializable;
import java.util.HashMap;

public class Header implements Serializable {

	Boolean[] encoders;
	HashMap<Integer, Double[]> probabilities;
	Integer size;
	
	public Header(int size) {
		encoders = new Boolean[size];
		this.size = size;
		probabilities = new HashMap<Integer, Double[]>();
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
	
}
