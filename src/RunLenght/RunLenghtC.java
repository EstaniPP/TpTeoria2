package RunLenght;

import java.util.ArrayList;

import Others.ImageParser;

public class RunLenghtC {
	
	/*
	  * @desc encodes an image using RunLenght method
	  * @param ImageParser imagen - Raw image
	  * @return ArrayList<Byte> - encoded image
	*/
	
	public static ArrayList<Byte> encode(ImageParser imagen) {
		ArrayList<Byte> resultado = new ArrayList<Byte>();
		int cont = -1;
		int actual = imagen.getRGB(0, 0).getRed();
		for(int i = 0; i < imagen.blockHeight(0, 0); i++) {
			for(int j = 0; j < imagen.blockWidth(0, 0); j++) {
				if(imagen.getRGB(j, i).getRed() == actual) {
					if(cont == 255) {
						resultado.add((byte) actual);
						resultado.add((byte) cont);
						cont = 0;
					}else {
						cont++;
					}
				}else {
					resultado.add((byte) actual);
					resultado.add((byte) cont);
					cont = 0;
					actual = imagen.getRGB(j, i).getRed();
				}
			}
		}
		resultado.add((byte) actual);
		resultado.add((byte) cont);
		return resultado;
	}
	
	/*
	  * @desc dencodes an image using RunLenght method
	  * @param ArrayList<Byte> encoded - encoded image
	  * @return ArrayList<Integer> - decoded image
	*/
	public static ArrayList<Integer> decode(ArrayList<Byte> encoded) {
		ArrayList<Integer> decoded = new ArrayList<Integer>();
		for(int i = 0; i < encoded.size(); i += 2) {
			int n = encoded.get(i + 1);
			int data = encoded.get(i);
			if(n < 0)
				n += 256;
			if(data < 0) {
				data += 256;
			}
			for(int j = 0; j <= n; j++) {
				decoded.add(data);
			}
		}
		return decoded;
	}
}
