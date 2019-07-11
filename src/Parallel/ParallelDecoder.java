package Parallel;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import Forms.formDecompression;
import Huffman.Huffman;

public class ParallelDecoder implements Callable<ArrayList<Integer>> {
	
	ArrayList<Byte> rawBlock;
	Double[] probsO;
	int blockSize;
	
	public ParallelDecoder(ArrayList<Byte> rawBlock, Double[] probsO, int blockSize) {
		this.probsO = probsO;
		this.rawBlock = rawBlock;
		this.blockSize = blockSize;
	}
	
	@Override
	public ArrayList<Integer> call() throws Exception {
		ArrayList<Integer> deco = Huffman.decode(Huffman.getHuffmanTree(probsO), rawBlock, blockSize);
		synchronized(formDecompression.progressBar) {
			formDecompression.progressBar.setValue(formDecompression.progressBar.getValue() + 1);
		}
		return deco;
	}

}
