package RunLenght;

import Others.ImageParser;

public class RunLenghtC {
	
	public static StringBuilder getRLC(ImageParser imagen) {
		StringBuilder resultado = new StringBuilder();
		Integer cont=1;
		Integer actual = imagen.getRGB(0, 0).getRed();
		for(int i=0;i<imagen.blockHeight(0, 0);i++) {
			for(int j=0; j<imagen.blockWidth(0, 0);j++) {
				if(imagen.getRGB(j, i).getRed()==actual) {
					if(cont==255) {
						resultado.append(actual+cont);
						cont = 1;
					}else {
						cont++;
					}
				}else {
					resultado.append(actual+cont);
					actual = imagen.getRGB(j, i).getRed();
					cont=1;
				}
			}
		}
		return resultado;
	}
}
