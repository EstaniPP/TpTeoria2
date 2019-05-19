package Others;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

import Huffman.Huffman;
import RunLenght.RunLenghtC;

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
		
		ArrayList<Byte> encoded = RunLenghtC.encode(b1);
		
		ArrayList<Integer> decoded = RunLenghtC.decode(encoded);
		
		System.out.println(decoded.size());
		int comienzo = 0;
		for(int i = 0; i < 500; i++) {
			for(int j = 0; j < 500; j++) {
				int original = b1.getRGB(j, i).getRed();
				int deco = decoded.get(i * 500 + j);
				if(original != deco) {
					System.out.println("comienzo: "+ comienzo + " coordenada "+ i + ", "+ j + " | original: "+original + " deco: " +deco);
				}
				comienzo++;
			}
		}
		
		/*
		ArrayList<Byte> bytes = Huffman.encode(Huffman.getHuffman(Utilities.getProbabiliades(b1)), b1);
		//System.out.println(bytes.size());
		
		ArrayList<Integer> h = Huffman.decode(Huffman.getHuffmanTree(Utilities.getProbabiliades(b1)), bytes);
		int comienzo = 0;
		for(int i = 0; i < 500; i++) {
			for(int j = 0; j < 500; j++) {
				int original = b1.getRGB(i, j).getRed();
				int decoded = h.get(comienzo);
				if(original == decoded && false) {
					System.out.println("comienzo: "+ comienzo + " coordenada "+ i + ", "+ j + " | original: "+original + " decoded: " +decoded);
				}
				comienzo++;
			}
		}
		
		// escribir a una img random
		BufferedImage img = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
		
		comienzo = 0;
		for(int i = 0; i < 500; i++) {
			for(int j = 0; j < 500; j++) {
				int original = b1.getRGB(i, j).getRed();
				int decoded = h.get(comienzo);
				int p1 = (decoded<<24) | (decoded<<16) | (decoded<<8) | decoded; 
				img.setRGB(i, j, p1);
				comienzo++;
			}
		}

		
		
		File file = new File("myimage.png");
        try {
			ImageIO.write(img, "png", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		*/
		//System.out.println("Color original"+ b1.getRGB(0, 30).getRed());
		//System.out.println("Color decodeado"+ h.get(30));
		
		//System.out.println(h.size());
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
				
			}*/
		
/*	      try {
	          ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
	          ObjectOutputStream out = new ObjectOutputStream(fileOut);
	          out.writeObject(h);
	          out.close();
	          System.out.println(fileOut.size());
	          fileOut.close();
	          System.out.printf("Serialized data is saved in /tmp/employee.bin");
	       } catch (IOException i) {
	          i.printStackTrace();
	       }
	      
	      //FileInputStream fis = new FileInputStream(fileName);
	      //ObjectInputStream ois = new ObjectInputStream(fis);) {
	      //stu = (Header) ois.readObject();
	       */
	}
}
