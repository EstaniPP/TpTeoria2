package Others;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.swing.JFileChooser;



public class Utilities {
	private static int tiradasMinimas = 1000000;
	private static Double epsilonDesvio = 1e-8;
	private static Double epsilonMedia = 1e-8;	
	
	public static double[] getProbabiliades(ImageParser image) {
		double[] probabilidades = new double[256];
		int[] veces = new int[256];
		
		for(int i = 0; i < 256; i++) {
			probabilidades[i] = 0;
			veces[i] = 0;
		}
		
		for(int i = 0; i < 500; i++) {
			for(int j = 0; j < 500; j++) {
				veces[image.getRGB(j, i).getRed()]++;
			}
		}
		for(int i = 0; i < 256; i++) {
			probabilidades[i] = ((double) veces[i]) / (double) (500*500);
		}
		
		return probabilidades;
	}
	
	public static double getEntropiaSMemoria(ImageParser image) {
		double[] p = getProbabiliades(image);
		double suma = 0;
		for(int i = 0; i < 256; i++) {
			if(p[i] != 0) {
				suma += p[i] *  ((double)Math.log((double) p[i]) / (double)Math.log((double) 2));
			}
		}
		
		return -suma;
	}
	
	public static double getEntropiaCMemoria(ImageParser image) {
		double hCond = 0;
		double[] p = getProbabiliades(image);
		double[][] matCond = Utilities.getMatrizCondicional(image);
		for(int i = 0; i < 256; i++) {
				double hi = 0;
				for(int j = 0; j<256; j++) {
					if(matCond[j][i] != 0) {
						hi += (-1 * matCond[j][i] * ((double)Math.log((double) matCond[j][i]) / (double)Math.log((double) 2)));
					}
				}
				hCond  += (p[i] *  hi);
		}
		return hCond;
	}
	
	public static double[][] getMatrizCondicional(ImageParser image){
		double[][] matrizcond = new double[256][256];
		double[] tiradas = new double[256];
		
		for(int i=0;i<256;i++) {
			for(int j=0;j<256;j++) {
				matrizcond[i][j]=0;
			}
			tiradas[i]=0;
		}
		
		int coloranterior= image.getRGB(0, 0).getRed();
		for(int i = 0; i < 500; i++) {
			for(int j = 0; j < 500; j++) {
				int coloractual=image.getRGB(j, i).getRed();;
				if(j!=0 || i!=0) {
					matrizcond[coloractual][coloranterior]++;
					coloranterior=coloractual;
				}
				tiradas[coloranterior]++;
				if(j==499 && i==499) {
					tiradas[coloranterior]--;
				}
			}
		}
		
		for(int i=0;i<256;i++) {
			for(int j=0; j<256; j++) {
				if(tiradas[j]==0) {
					matrizcond[i][j]=0.0f;
				}else {
					matrizcond[i][j]=matrizcond[i][j]/tiradas[j];
				}
			}
		}
		
		return matrizcond;
	}
	

	private static boolean converge(double act, double ant, double epsilon) {
		if(Math.abs(act-ant)<epsilon) {
			return true;
		}
		return false;
	}
	
	private static int getColorMontecarlo(double[] probabilidadAcumulada) {
		double rand = Math.random();
		for(int i=0; i<256; i++) {
			if(rand<=probabilidadAcumulada[i]) {
				return i;
			}
		}
		return 255;
	}
	
	private static int getColorMontecarloCondicional(double[][] matrizAcumulada, int valor) {
		double rand = Math.random();
		for(int i=0; i<256; i++) {
			if(rand<=matrizAcumulada[i][valor]) {
				return i;
			}
		}
		return 255;
	}
	
	public static double[] getProcEstocasticos(ImageParser img) {
		double sumaMedia=0.0;
		double suma = 0.0;
		int tiradas=0;
		double act=0.0;
		double ant=-1.0;
		double mediaant=0.0;
		double mediaact = -1.0;
		
		double[] probabilidadAcumulada = Utilities.getProbabiliades(img);
		double[][] matrizAcumulada = Utilities.getMatrizCondicional(img);
		double sumaprob =0;
		for(int i=0; i<256; i++) {
			double sumacond =0;
			for(int j=1; j<256; j++) {
				sumacond+=matrizAcumulada[j][i];
				matrizAcumulada[j][i]=sumacond;
			}
			sumaprob+=probabilidadAcumulada[i];
			probabilidadAcumulada[i]=sumaprob;	 
		}
		int valor = Utilities.getColorMontecarlo(probabilidadAcumulada);
		
		while((!converge(act,ant, Utilities.epsilonDesvio) && !converge(mediaact,mediaant, Utilities.epsilonMedia)) || tiradas<Utilities.tiradasMinimas ) {
			valor = Utilities.getColorMontecarloCondicional(matrizAcumulada, valor);
			sumaMedia += (double) valor;
			tiradas++;
			suma += Math.pow(((double)valor-(sumaMedia /(double) tiradas)), 2);
			ant=act;
			act=Math.sqrt(suma/(double) tiradas);
			
			mediaant=mediaact;
			mediaact=sumaMedia/(double)tiradas;
		}
		double[] procEstocasticos = new double[]{mediaact,act};
		return procEstocasticos;
	}

	public static String getRepeticiones(ImageParser img) {
		int[] repeticiones = new int[256];
		for(int i=0;i<256;i++) {
			repeticiones[i]=0;
		}
		
		for(int j=0;j<500;j++) {
			for(int i=0;i<500;i++) {
				repeticiones[img.getRGB(i, j).getRed()]++;
			}
		}
		StringBuilder salida = new StringBuilder();
		for(int i=0;i<256;i++) {
			salida.append("El color: "+i+" se repitio "+repeticiones[i]+" veces<br />");
		}
		
		return salida.toString();
	}
	
	public static byte[] ConvertByteListToPrimitives(ArrayList<Byte> input) {
		byte[] ret = new byte[input.size()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = input.get(i);
		}

		return ret;
	}
	
	//gets header original size
	public static int getHeaderSize(ArrayList<Byte> byteArray) {
		int headerSize = 0;
		//iterates the 3 first values of the array, where the header size were saved
		for(int i = 0; i < 3; i++) {
			//adjust size by the byte place
			headerSize = headerSize*256;
			int aux = byteArray.get(i);
			//check aux's sign
			if(aux < 0) {
				aux += 256;
			}
			headerSize+=aux;
		}
		return headerSize;
	}
	
	//get header's object
	public static Header getHeader(ArrayList<Byte> byteArray) {
		//gets header size
		int size = getHeaderSize(byteArray);
		//array with the header
		byte[] header = new byte[size];
		//iterates from third place becuase the 3 first values are header size
		for(int i = 3; i < size + 3; i++) {
			header[i-3]=byteArray.get(i);
		}
		
		ByteArrayInputStream bis = new ByteArrayInputStream(header);
		ObjectInput in = null;
		Header h = null;
		try {
			in = new ObjectInputStream(bis);
			h = (Header) in.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		return h;
	}
	
	//this method gets a bitecode with the header and it's size in the 3 first places
	public static ArrayList<Byte> getHeadersByteCode(Header headerToCode){
		//creates returns arrayList 
		ArrayList<Byte> ret = new ArrayList<Byte>();
		
		//convert header to byte code
		/**/
	    ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
	    ObjectOutputStream out;
	    try {
	    	out = new ObjectOutputStream(fileOut);
	    	out.writeObject(headerToCode);
	    	
	    } catch (IOException e) {
			e.printStackTrace();
		}

        byte[] headerArray = fileOut.toByteArray();
        /**/
        
        //size of headers byte code
        long size = fileOut.size();
        
        byte fByte,sByte,tByte;
		tByte = ((byte)(size % 256));
		size = size / 256;
		sByte = ((byte)(size % 256));
		fByte = ((byte)(size / 256));
		ret.add(fByte);
		ret.add(sByte);
		ret.add(tByte);
		
		//saves headers byte code in ret
		for(int i = 0; i < headerArray.length; i++) {
        	ret.add(3+i,headerArray[i]);
        }
		
		return ret;
	}
	
	//creates an archive from an arraylist with the encoded image in the path
	public static void saveArchive(ArrayList<Byte> info, String path) {			
		try {
			//creates bytes array and fill it with arraylist info
			byte[] byteCode = new byte[info.size()];
			for(int i = 0; i < byteCode.length; i++) {
				byteCode[i] = info.get(i);
			}
			FileOutputStream fos = new FileOutputStream(path);
			//writes de file in disk
			fos.write(byteCode);
			fos.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//get bytecode from a path and return it as arraylist
	public static ArrayList<Byte> getArchivesByteCode(String path){
		
		//creates arralist
		ArrayList<Byte> encodeImage = new ArrayList<Byte>();
		try {
			//read file
			byte[] inputSequence = Files.readAllBytes(new File(path).toPath());
			//fill arraylist
			for(int i = 0; i < inputSequence.length; i++) {
				encodeImage.add(inputSequence[i]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		//returns arraylist
		return encodeImage;
	}
	
	public static void main(String[] args) {
		//creates an example header
		Header h = new Header(25);
		for(int i = 0; i < 25; i++) {
			if(i % 2 == 0) {
				Double[] prob = new Double[256];
				for(int j = 0; j < 256; j++) {
					prob[j] = (double)1/j;
				}
				h.setHuffman(i, prob);
			}else {
				h.setRLC(i);
			}
		}
		
		//gets header bytecode
        ArrayList<Byte> bytecode = getHeadersByteCode(h);
        
        //select path
		JFileChooser chooser = new JFileChooser(); 
		String destination = "";
	    chooser.setCurrentDirectory(new java.io.File("."));
	    chooser.setDialogTitle(destination);
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    chooser.setAcceptAllFileFilterUsed(false);    
	    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
	      destination = chooser.getSelectedFile().toString();
	      
	      //save archive in the path
	      saveArchive(bytecode, new String(destination+"/imagen.bin"));
	      
	      //get bytecode from path
	      ArrayList<Byte> encodedImage = getArchivesByteCode(new String(destination+"/imagen.bin"));
	      
	      //get header from btecode
	      Header decodedHeader = getHeader(encodedImage);
	      
	      //prints header info to corroborrate that it works
	      for(int i = 0; i < 25; i++) {
				System.out.println(decodedHeader.getEncoder(i));
			}
	    }
	}
	
}
