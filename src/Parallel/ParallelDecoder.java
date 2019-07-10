package Parallel;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class ParallelDecoder implements Callable<ArrayList<Integer>> {
	
	ArrayList<Byte> rawBlock;
	Double[] probsO;
	int blockSize;
	
	public ParallelDecoder(ArrayList<Byte> rawBlock, Double[] probsO, int BlockSize) {
		this.probsO = probsO;
		this.rawBlock = rawBlock;
		this.blockSize = blockSize;
	}
	@Override
	public ArrayList<Integer> call() throws Exception {
		
		return null;
	}

}
