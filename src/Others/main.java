package Others;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

import Huffman.Huffman;

public class main {

	public static void main(String[] args) {
		
		ImageParser p = null;
		BufferedImage bi;
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int seleccion = fileChooser.showOpenDialog(null);
		if(seleccion == JFileChooser.APPROVE_OPTION) {
			File image = fileChooser.getSelectedFile();
			p = new ImageParser(image);
			bi = p.getBI();
		}
		ImageParser b1 = p.getBlock(0, 0);
		ArrayList<Byte> bytes = Huffman.encode(Huffman.getHuffman(Utilities.getProbabiliades(b1)), b1);
		System.out.println(bytes.size());
		for(Byte b : bytes) {
			//if(b != 0)
				//System.out.println(b.toString());
		}
		
		ArrayList<Integer> h = Huffman.decode(Huffman.getHuffmanTree(Utilities.getProbabiliades(b1)), bytes);
		
		System.out.println(b1.getRGB(0, 0).getRed());
		System.out.println(h.get(0));
		/*
		double[] probabilities = {(double)1/22,(double)1/22,(double)9/22,(double)3/22,(double)2/22,(double)2/22,(double)4/22};
			int i=0;
			for(double n: probabilities) {
				System.out.println("Probabilidad de "+i+" es "+ n);
				i++;
			}
			
			HashMap<Integer, ArrayList<Integer>> huffmanMap = Huffman.getHuffman(probabilities);
			
			for(Entry<Integer, ArrayList<Integer>> n: huffmanMap.entrySet()) {
				System.out.println("Codigo de "+n.getKey());
				for(int j=0;j<n.getValue().size();j++) {
					System.out.print(n.getValue().get(j));
				}
				System.out.println("");
				
			}
		*/
	}
}
