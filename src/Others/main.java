package Others;

import java.util.HashMap;
import java.util.Map.Entry;
import Huffman.Huffman;

public class main {

	public static void main(String[] args) {
			double[] probabilities = {(double)1/22,(double)1/22,(double)9/22,(double)3/22,(double)2/22,(double)2/22,(double)4/22};
			int i=0;
			for(double n: probabilities) {
				System.out.println("Probabilidad de "+i+" es "+ n);
				i++;
			}
			
			HashMap<Integer, Integer> huffmanMap = Huffman.getHuffman(probabilities);
			
			for(Entry<Integer, Integer> n: huffmanMap.entrySet()) {
				System.out.println("Codigo de "+n.getKey()+" es "+ n.getValue());
			}
	}
}
