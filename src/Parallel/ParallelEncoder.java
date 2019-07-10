package Parallel;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import Forms.formCompression;
import Huffman.Huffman;
import Others.Header;
import Others.ImageParser;
import Others.Utilities;

/*
 * Useful to create callables and encode the blocks
 */

public class ParallelEncoder implements Callable<ArrayList<Byte>>{
	
	ImageParser block = null;
	Header header = null;
	int blockNumber = -1;
	
	public ParallelEncoder(ImageParser block, Header header, int blockNumber) {
		this.block = block;
		this.header = header;
		this.blockNumber = blockNumber;
	}
	@Override
	public ArrayList<Byte> call() throws Exception {
		// get block probabilities
		Double[] probs = Utilities.getPObjectArray(block);
		ArrayList<Byte> hffmn = Huffman.encode(Huffman.getHuffman(probs), block);
		// update progressbar
		synchronized(formCompression.progressBar){
			formCompression.progressBar.setValue(formCompression.progressBar.getValue() + 1);
		}
		//System.out.println("Procesado numero " + blockNumber);
		header.setHuffman(blockNumber, probs);
		header.setBlockSizeEncoded(blockNumber, hffmn.size());
		
		// set width and height of block
		header.setXY(blockNumber, block.getWidth(), block.getHeight());
		return hffmn;
	}

}
