package Others;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

import Huffman.Huffman;
import RunLenght.RunLenghtC;

public class main {
	static ImageParser p1 = null;
	static BufferedImage bi1 = null;
	
	public static void main(String[] args) {
		
		ImageParser p = null;
		BufferedImage bi = null;
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int seleccion = fileChooser.showOpenDialog(null);
		if(seleccion == JFileChooser.APPROVE_OPTION) {
			File image = fileChooser.getSelectedFile();
			p = new ImageParser(image);
			bi = p.getBI();
		}
		
		
		
		
		try {
			EventQueue.invokeAndWait(new Runnable() {
			    @Override
			    public void run() {
			    	JFileChooser fileChooser1 = new JFileChooser();
					fileChooser1.setCurrentDirectory(new File(System.getProperty("user.dir")));
					int seleccion1 = fileChooser1.showOpenDialog(null);
					if(seleccion1 == JFileChooser.APPROVE_OPTION) {
						File image = fileChooser1.getSelectedFile();
						main.this.p1 = new ImageParser(image);
						main.this.bi1 = p1.getBI();
					}
			    }
			});
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		System.out.println("RUIDO:    " + Utilities.getRuido(p, main.p1));
		
		/*
		ArrayList<Integer> ai = new ArrayList<Integer> ();
		for(int a = 0; a < 2500; a++) {
			for(int b = 0; b < 2000; b++) {
				Color m = new Color(bi.getRGB(b, a));
				ai.add(m.getRed());
			}	
		}*/
		
		
		//p.getBlock(4);
		
		
		//ArrayList<Byte> bytes = Huffman.encode(Huffman.getHuffman(Utilities.getProbabiliades(b1)), b1);
		
		
		/*
		//select path
		JFileChooser chooser = new JFileChooser(); 
		String destination = "";
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle(destination);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);    
		System.out.println("pepe");
		ArrayList<Byte> encoded = null;
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
			destination = chooser.getSelectedFile().toString(); 
			//save archive in the path
			encoded = Utilities.encodeImage(p, 20);
			Utilities.saveFile(encoded, new String(destination+"/imagen.bin"));
		}
		
		
		
		BufferedImage buf = Utilities.decodeImage(encoded);
		try {
			boolean write = ImageIO.write(buf, "bmp", new File("./p.bmp"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			      //get bytecode from path
			//ArrayList<Byte> encodedImage = getFileByteCode(new String(destination+"/imagen.bin"));
			      
			      //get header from btecode
			//Header decodedHeader = getHeader(encodedImage);
			      
			      //prints header info to corroborrate that it works
			//for(int i = 0; i < 25; i++) {
			//	System.out.println(decodedHeader.getEncoder(i));
			//}

		//}
		//System.out.println(Huffman.getHuffman(Utilities.getProbabiliades(b1)).toString());
		
		/* USAR POR SI ACASO 
		 ArrayList<Integer> decoded = Utilities.decodeImage(encoded);
			
			for(int pepe = 0; pepe < 500; pepe++) {
				if(!ai.get(pepe).equals(decoded.get(pepe))) {
					System.out.println("original: " + ai.get(pepe) + " Deco: " + decoded.get(pepe));
				}
			}
		 * */
		
		//System.out.println(bytes.size());
		
		//Huffman.encode(Huffman.getHuffmanTree(), img)
		
		/*
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
