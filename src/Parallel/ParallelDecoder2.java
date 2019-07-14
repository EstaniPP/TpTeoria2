package Parallel;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import Forms.formDecompression;
import Huffman.Huffman;
import Others.Header;

public class ParallelDecoder2 implements Runnable {
	
	ArrayList<Byte> rawBlock;
	Double[] probsO;
	int blockSize;
	Header header;
	BufferedImage nuevaImagen;
	int bn;
	Integer[] decodificada;
	
	public ParallelDecoder2(ArrayList<Byte> rawBlock, Double[] probsO, int blockNumber, Header header, BufferedImage nuevaImagen, Integer[] decodificada) {
		this.probsO = probsO;
		this.rawBlock = rawBlock;
		this.blockSize = header.getBlockSize(blockNumber);
		this.nuevaImagen = nuevaImagen;
		this.bn = blockNumber;
		this.header = header;
		this.decodificada = decodificada;
	}
	
	@Override
	public void run(){
		
		// donde empiezo a escribir en arreglo
		
		int where = 0;
		for(int j = 0; j < bn; j++) {
			where += (header.getX(j) * header.getY(j));
		}
		//
		//System.out.println("BLOQUE " + bn + " COMIENZA EN " + where);
		
		ArrayList<Integer> decod = Huffman.decode(Huffman.getHuffmanTree(probsO), rawBlock, blockSize);
		
		int j = 0;
		for(int i = where; i < where + decod.size(); i++) {
			decodificada[i] = decod.get(j);
			j++;
		}
		
		//synchronized(formDecompression.progressBar) {
		//	formDecompression.progressBar.setValue(formDecompression.progressBar.getValue() + 1);
		//}
		
	}

}
