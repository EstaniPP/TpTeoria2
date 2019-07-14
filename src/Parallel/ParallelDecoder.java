package Parallel;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import Forms.formDecompression;
import Huffman.Huffman;
import Others.Header;

public class ParallelDecoder implements Callable<ArrayList<Integer>> {
	
	ArrayList<Byte> rawBlock;
	Double[] probsO;
	int blockSize;
	Header header;
	BufferedImage nuevaImagen;
	int bn;
	
	public ParallelDecoder(ArrayList<Byte> rawBlock, Double[] probsO, int blockNumber, Header header, BufferedImage nuevaImagen) {
		this.probsO = probsO;
		this.rawBlock = rawBlock;
		this.blockSize = header.getBlockSize(blockNumber);
		this.nuevaImagen = nuevaImagen;
		this.bn = blockNumber;
		this.header = header;
	}
	
	@Override
	public ArrayList<Integer> call() throws Exception {
		
		// donde empiezo a escribir en arreglo
		/*
		int where = 0;
		for(int j = 0; j < bn; j++) {
			where += (header.getX(j) * header.getY(j));
		}*/
		//
		
		ArrayList<Integer> decod = Huffman.decode(Huffman.getHuffmanTree(probsO), rawBlock, blockSize);
		/*
		int j = 0;
		for(int i = where; i < decod.size(); i++, j++) {
			deco[i] = decod.get(j);
		}*/
		synchronized(formDecompression.progressBar) {
			formDecompression.progressBar.setValue(formDecompression.progressBar.getValue() + 1);
		}
		return decod;
	}

}
