package Others;

import java.awt.Color;
import java.awt.image.BufferedImage;
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
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import Forms.formCompression;
import Forms.formDecompression;
import Huffman.Huffman;
import Parallel.ParallelDecoder;
import Parallel.ParallelEncoder;
import Parallel.ParallelImageGenerator;
import RunLenght.RunLenghtC;



public class Utilities {

	
	public static double[] getProbabiliades(ImageParser image) {
		double[] probabilidades = new double[256];
		int[] veces = new int[256];
		
		for(int i = 0; i < 256; i++) {
			probabilidades[i] = 0;
			veces[i] = 0;
		}
		
		for(int i = 0; i < image.getHeight(); i++) {
			for(int j = 0; j < image.getWidth(); j++) {
				veces[image.getRGB(j, i).getRed()]++;
			}
		}
		for(int i = 0; i < 256; i++) {
			probabilidades[i] = ((double) veces[i]) / (double) (image.getHeight()*image.getWidth());
		}
		
		return probabilidades;
	}
	
	public static Double[] getPObjectArray(ImageParser image) {
		Double[] probabilidades = new Double[256];
		int[] veces = new int[256];
		
		for(int i = 0; i < 256; i++) {
			probabilidades[i] = 0d;
			veces[i] = 0;
		}
		
		for(int i = 0; i < image.getHeight(); i++) {
			for(int j = 0; j < image.getWidth(); j++) {
				veces[image.getRGB(j, i).getRed()]++;
			}
		}
		for(int i = 0; i < 256; i++) {
			probabilidades[i] = ((double) veces[i]) / (double) (image.getWidth() * image.getHeight());
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
		for(int i = 0; i < image.getHeight(); i++) {
			for(int j = 0; j < image.getWidth(); j++) {
				int coloractual = 0;
				try {
					coloractual=image.getRGB(j, i).getRed();
				}catch(ArrayIndexOutOfBoundsException e) {
					System.out.println("i: " + i + " j: " + j);
				}
				
				if(j!=0 || i!=0) {
					matrizcond[coloractual][coloranterior]++;
					coloranterior=coloractual;
				}
				tiradas[coloranterior]++;
				if(j==image.getWidth() - 1 && i==image.getHeight() - 1) {
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


	public static String getRepeticiones(ImageParser img) {
		int[] repeticiones = new int[256];
		for(int i=0;i<256;i++) {
			repeticiones[i]=0;
		}
		
		for(int j=0;j<img.getHeight();j++) {
			for(int i=0;i<img.getWidth();i++) {
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
		//iterates the 3 first values of the array, where the header size was saved
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
	
	public static ArrayList<Byte> getNoHeader(ArrayList<Byte> byteArray){
		
		int size = getHeaderSize(byteArray);
		
		System.out.println("Header size: " + size);
		
		
		ArrayList<Byte> aux = new ArrayList<Byte>();
		for(int i = size + 3; i < byteArray.size(); i++)
			aux.add(byteArray.get(i));
		return aux;
	}
	
	//this method gets a bitecode with the header and it's size in the 3 first places
	
	public static ArrayList<Byte> getHeaderByteCode(Header headerToCode){
		//creates returns arrayList 
		ArrayList<Byte> ret = new ArrayList<Byte>();
		
		//convert header to byte code
		
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
	public static void saveFile(ArrayList<Byte> info, String path) {			
		try {
			//creates bytes array and fill it with arraylist info
			byte[] byteCode = new byte[info.size()];
			for(int i = 0; i < byteCode.length; i++) {
				byteCode[i] = info.get(i);
			}
			FileOutputStream fos = new FileOutputStream(path+"/compressed.bin");
			//writes de file in disk
			fos.write(byteCode);
			fos.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//get bytecode from a path and return it as arraylist
	public static ArrayList<Byte> getFileByteCode(String path){
		
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
	
	public static boolean figureOut(Double entrophy, Double ht) {
		// returns true if entrophy is equal greater than ht
		return (entrophy >= ht);
	}
	
	/*
	 * PARALLEL SECTION
	 */
	
	/*
	 * parallelEncoder encodes all the image blocks using Huffman's algorithm
	 */
	public static ArrayList<Byte> parallelEncoder(ImageParser i, int blocks, int processors) {
		
		// number of blocks
		blocks = i.getWidthInBlocks() * i.getHeightInBlocks();
		formCompression.progressBar.setMaximum(blocks);
		// arraylist with the huffman encoded blocks
		ArrayList<Byte> imageByte = new ArrayList<Byte>();
		// create a header for the image
		Header h = new Header(blocks, i.getWidth(), i.getHeight());
		// create a pool of threads with available processors
		if(processors == -1) {
			processors = Runtime.getRuntime().availableProcessors();
			System.out.println("Using available processors");
		}
		ExecutorService executor = Executors.newFixedThreadPool(processors);
		ArrayList<Callable<ArrayList<Byte>>> callables = new ArrayList<Callable<ArrayList<Byte>>>();
		for(int bn = 0; bn < blocks; bn++) {
			// add block to the encoding queue
			callables.add(new ParallelEncoder(i.getBlock(bn), h, bn));
		}
		// invoke all the tasks
		List<Future<ArrayList<Byte>>> result = null;
		try {
			// Results of the task in the same order as added
			result = executor.invokeAll(callables);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(result != null) {
			for(Future<ArrayList<Byte>> alb : result) {
				try {
					imageByte.addAll(alb.get());
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
		}
		ArrayList<Byte> whole = getHeaderByteCode(h);
		whole.addAll(imageByte);
		//formCompression.progressBar.setValue(100);
		return whole;
	}
	/*
	 * END OF PARALLEL SECTION
	 */
	
	@SuppressWarnings("unused")
	public static ArrayList<Byte> encodeImageSequential(ImageParser i, int blocks){
		// number of blocks
		blocks = i.getWidthInBlocks() * i.getHeightInBlocks();
		formCompression.progressBar.setMaximum(blocks);
		
		ArrayList<Byte> imageByte = new ArrayList<Byte>();
		Header h = new Header(blocks, i.getWidth(), i.getHeight());
		for(int j = 0; j < blocks; j++) {
			// get the block
			ImageParser b = i.getBlock(j);
			//System.out.println("BLOQUE " + j);
			if(true) {
				Double[] p = Utilities.getPObjectArray(b);
				// Huffman Case
				//System.out.println("El bloque "+ j + " usa Huffman");
				ArrayList<Byte> hffmn = Huffman.encode(Huffman.getHuffman(p), b);
				h.setHuffman(j, p);
				h.setBlockSizeEncoded(j, hffmn.size());
				imageByte.addAll(hffmn);
				// set width and height of block
				h.setXY(j, b.getWidth(), b.getHeight());
				
				// update progress bar
				formCompression.progressBar.setValue(formCompression.progressBar.getValue() + 1);
				
			}else {
				// rlc case

				System.out.println("El bloque "+ j + " usa RLC");
				ArrayList<Byte> rlc = RunLenghtC.encode(b);
				h.setRLC(j);
				h.setBlockSizeEncoded(j, rlc.size());
				imageByte.addAll(rlc);
			
				formCompression.progressBar.setValue(20+j*5);
			}
			
		}
		
		ArrayList<Byte> whole = getHeaderByteCode(h);
		whole.addAll(imageByte);
		//formCompression.progressBar.setValue(100);
		return whole;
	}
	

	
	public static BufferedImage decodeImage(ArrayList<Byte> encoded) {	

		
		Header header = Utilities.getHeader(encoded);
		ArrayList<Byte> rawImage = Utilities.getNoHeader(encoded);
		ArrayList<Integer> decoded = new ArrayList<Integer>();
		
		formDecompression.label.setText("BLOQUES");
		formDecompression.progressBar.setValue(0);
		formDecompression.progressBar.setMaximum(header.getBlockSizes().length);
		
		int start = 0;
		int blockNumber = 0;
		for(int c : header.getBlockSizes()) {
			ArrayList<Byte> blockBytes = new ArrayList<Byte>();
			for(int i = start; i < c + start; i++) {
				blockBytes.add(rawImage.get(i));
			}
			if(header.getEncoder(blockNumber)) {
				// huffman
				Double[] probsO = header.getProbs(blockNumber);
				double[] probs = new double[probsO.length];
				for(int i = 0; i < probsO.length; i++) {
					probs[i] = probsO[i];	
				}
				ArrayList<Integer> deco = Huffman.decode(Huffman.getHuffmanTree(probsO), blockBytes, header.getBlockSize(blockNumber));
				decoded.addAll(deco);
				
				formDecompression.progressBar.setValue(formDecompression.progressBar.getValue() + 1);
				
			}else {
				// rlc
				decoded.addAll(RunLenghtC.decode(blockBytes));
				formDecompression.progressBar.setValue(20+blockNumber*3);
			}
			start += c;
			blockNumber++;
		}
		
		//System.out.println("Big image size: " + decoded.size());
		
		BufferedImage nuevaImagen = new BufferedImage(header.getWholeX(), header.getWholeY(), BufferedImage.TYPE_BYTE_GRAY);
		
		int pixel = 0;
		/*
		for(int fila = 0; fila < 2500; fila = fila + 500) {
			for(int col = 0; col < 2000; col = col + 500) {
				
				for(int x = fila; x < fila + 500; x++) {
					for(int y = col; y < col + 500; y++) {
						//System.out.println("algo : " + pixel);
						int color = 0;
						try {
							color = decoded.get(pixel);
						}catch(IndexOutOfBoundsException e) {
						}
						
						nuevaImagen.setRGB(y, x, new Color(color, color, color).getRGB());
						pixel++;
					}
				}
				
			}
		}
		*/
		
		// iterate each block
		
		formDecompression.label.setText("GENERANDO IMAGEN");
		formDecompression.progressBar.setValue(0);
		// block number
		int bn = 0;
		int by = 0;
		while(by < header.getWholeY()) {
			int bx = 0;
			while(bx < header.getWholeX()) {
				// intert block
				for(int y = by; y < by + header.getY(bn); y++) {
					for(int x = bx; x < bx + header.getX(bn); x++) {
						//System.out.println("algo : " + pixel);
						int color = 0;
						try {
							color = decoded.get(pixel);
						}catch(IndexOutOfBoundsException e) {
						}
						try {
							nuevaImagen.setRGB(x, y, new Color(color, color, color).getRGB());
						}catch(ArrayIndexOutOfBoundsException e) {
							//System.out.println("x " + x + "y " + y);
						}
						pixel++;
					}
				}
				// moverme en x hasta la pos del ultimo columna del ultimo bloque
				bx += header.getX(bn);
				
				bn++;
				
				formDecompression.progressBar.setValue(formDecompression.progressBar.getValue() + 1);
			}
			// bajo la cantidad del ultimo bloque metido ya q toda esa fila es del mismo alto
			by += header.getY(bn - 1);
		}
		
		System.out.println("BLOCK NUMBER: " + bn);

		return nuevaImagen;
	}
	
	/*
	 * beginning of parallel decoder
	 */
	
	public static BufferedImage parallelDecoder(ArrayList<Byte> encoded, int processors) {
		Header header = Utilities.getHeader(encoded);
		ArrayList<Byte> rawImage = Utilities.getNoHeader(encoded);
		ArrayList<Integer> decoded = new ArrayList<Integer>();

		formDecompression.progressBar.setMaximum(header.getBlockSizes().length);
		formDecompression.progressBar.setValue(0);
		int start = 0;
		int blockNumber = 0;
		
		formDecompression.label.setText("BLOQUES");
		
		// create a thread pool
		ExecutorService executor = Executors.newFixedThreadPool(processors);
		ArrayList<Callable<ArrayList<Integer>>> callables = new ArrayList<Callable<ArrayList<Integer>>>();
		
		// iterate each block
		for(int c : header.getBlockSizes()) {
			ArrayList<Byte> blockBytes = new ArrayList<Byte>();
			// get all the bytes into an arraylist
			for(int i = start; i < c + start; i++) {
				blockBytes.add(rawImage.get(i));
			}
			// get the probabillities 
			Double[] probs = header.getProbs(blockNumber);
			// add the task
			callables.add(new ParallelDecoder(blockBytes, probs, header.getBlockSize(blockNumber)));
			start += c;
			blockNumber++;
		}
		// invoke all tasks
		List<Future<ArrayList<Integer>>> result = null;
		
		try {
			result = executor.invokeAll(callables);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// get all the results
		
		for(Future<ArrayList<Integer>> deco : result) {
			try {
				decoded.addAll(deco.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		// once all the results are in the arraylist, generate the image
		BufferedImage nuevaImagen = new BufferedImage(header.getWholeX(), header.getWholeY(), BufferedImage.TYPE_BYTE_GRAY);
		
		formDecompression.label.setText("GENERANDO IMAGEN");
		formDecompression.progressBar.setValue(0);
		formDecompression.progressBar.setMaximum(header.getBlockSizes().length);
		
		// parallelize image generator
		
		
		
		int pixel = 0;
		// iterate each block
		
		/*
		// block number
				int bn = 0;
				int by = 0;
				while(by < header.getWholeY()) {
					int bx = 0;
					while(bx < header.getWholeX()) {
						// intert block
						for(int y = by; y < by + header.getY(bn); y++) {
							for(int x = bx; x < bx + header.getX(bn); x++) {
								//System.out.println("algo : " + pixel);
								int color = 0;
								try {
									color = decoded.get(pixel);
								}catch(IndexOutOfBoundsException e) {
								}
								try {
									nuevaImagen.setRGB(x, y, new Color(color, color, color).getRGB());
								}catch(ArrayIndexOutOfBoundsException e) {
									//System.out.println("x " + x + "y " + y);
								}
								pixel++;
							}
						}
						// moverme en x hasta la pos del ultimo columna del ultimo bloque
						bx += header.getX(bn);
						
						bn++;
						
						formDecompression.progressBar.setValue(formDecompression.progressBar.getValue() + 1);
					}
					// bajo la cantidad del ultimo bloque metido ya q toda esa fila es del mismo alto
					by += header.getY(bn - 1);
				}
				
		*/
		// block number
		
		
		
		int bn = 0;
		int by = 0;
		while(by < header.getWholeY()) {
			int bx = 0;
			while(bx < header.getWholeX()) {
				
				// see where each block starts generating the image
				int where = 0;
				for(int j = 0; j < bn; j++) {
					where += (header.getX(j) * header.getY(j));
				}
				
				// de donde a donde decodifico
				
				
				executor.execute(new ParallelImageGenerator(decoded, nuevaImagen, where, header, bn, bx, by));
				// moverme en x hasta la pos del ultimo columna del ultimo bloque
				bx += header.getX(bn);
				bn++;
			}
			// bajo la cantidad del ultimo bloque metido ya q toda esa fila es del mismo alto
			by += header.getY(bn - 1);
		}
		
		executor.shutdown();
		
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return nuevaImagen;
	}
	
	/*
	 * end of parallel decoder
	 */
	
	//obtains conditional entropies between sent image and received image
	public static double[] getHi(ImageParser sent, ImageParser received) {
		//creates conditional matrix
		double[][] condMat = new double[256][256];
		//this array has the count of color occurrences
		int[] times = new int[256];
		//array with the conditional entropies
		double[] Hi = new double[256];
		
		//fill condMat and times with zeros
		for(int row = 0; row < 256; row++) {
			times[row] = 0;
			Hi[row] = 0;
			for(int column = 0; column < 256; column++) {
				condMat[row][column] = 0;
			}
		}
		
		//fills conMat with the conditional occurrences between sent and received images
		for(int row = 0; row < 2500; row++) {
			for(int column = 0; column < 2000; column++) {
				//add by one on sent color column and received color row
				condMat[received.getRGB(column, row).getRed()][sent.getRGB(column, row).getRed()]++;
				times[sent.getRGB(column, row).getRed()]++;
			}
		}
		
		//calculates conMat dividing each place of condMat by the total occurrences of the column color
		for(int row = 0; row < 256; row++) {
			for(int column = 0; column < 256; column++) {
				if(times[column] != 0) {
					condMat[row][column] = condMat[row][column] / times[column];
				}else {
					condMat[row][column] = 0;
				}
			}
		}
		
		//fills Hi array with conditional entropy of each color
		for(int column = 0; column < 256; column++) {
			for(int row = 0; row < 256; row++) {
				if(condMat[row][column] != 0) {
					Hi[column] -= condMat[row][column]*Math.log(condMat[row][column]) / Math.log(2);
				}
			}
		}
		
		//returns Hi array
		return Hi;
	}

	//this method gets the noise between the image that was sent and the image that was received
	public static double getRuido(ImageParser sent, ImageParser received) {
		//obtains conditional entropies
		double[] Hi = Utilities.getHi(sent, received);
		//obtains color probabilities of sent image
		double[] prob = Utilities.getProbabiliades(sent);
		
		//obtain noise
		double aux = 0d;
		for(int i = 0; i < 256; i++) {
			aux += prob[i] * Hi[i];
		}
		return aux;
	}
	
	public static void saveImage(BufferedImage bi, String path) {
		try {				
			ImageIO.write(bi, "bmp", new File(path+"/compressedImage.bmp"));
		} catch (IOException e) {
			System.out.println("hola");
			e.printStackTrace();
		}
	}
	
	
}
