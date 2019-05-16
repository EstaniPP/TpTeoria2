package RunLenght;

import java.util.ArrayList;

import Others.ImageParser;

public class RunLenghtC {
	
	public static Byte[] getRLC(ImageParser imagen) {
		ArrayList<Byte> resultado = new ArrayList<Byte>();
		int cont=1;
		int actual = imagen.getRGB(0, 0).getRed();
		for(int i=0;i<imagen.blockHeight(0, 0);i++) {
			for(int j=0; j<imagen.blockWidth(0, 0);j++) {
				if(imagen.getRGB(j, i).getRed()==actual) {
					if(cont==255) {
						resultado.add((byte)actual);
						resultado.add((byte)cont);
						cont = 1;
					}else {
						cont++;
					}
				}else {
					resultado.add((byte)actual);
					resultado.add((byte)cont);
					cont=1;
				}
			}
		}
		return (Byte[]) resultado.toArray();
	}
}
