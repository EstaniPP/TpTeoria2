package Parallel;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Forms.formDecompression;
import Others.Header;

public class ParallelImageGenerator2{

	BufferedImage nuevaImagen;
	Header header;
	int bn;
	ArrayList<Integer> decoded;
	
	
	public ParallelImageGenerator2(ArrayList<Integer> decoded, BufferedImage nuevaImagen, Header header, int bn) {
		this.header = header;
		this.nuevaImagen = nuevaImagen;
		this.decoded = decoded;
		this.bn = bn;
	}
	
	public void write() {
		// size of block matrix
		int bWidth = (int) Math.ceil(header.getWholeX() / header.getX(0));
		int bHeight = (int) Math.ceil(header.getWholeY() / header.getY(0));
		
		// position of block in block matrix
		int bPosX = bn % bWidth;
		int bPosY = bn / bHeight;
		
		// coords of block in image
		int bStartX = header.getX(0) * bPosX;
		int bStartY = header.getY(0) * bPosY;
		
		int blockWidth = header.getX(bn);
		int blockHeight = header.getY(bn);
		int where = 0;
		for(int y = bStartY; y < bStartY + blockHeight; y++) {
			for(int x = bStartX; x < bStartX + blockWidth; x++) {
				int color = 0;
				try {
					color = decoded.get(where);
				}catch(IndexOutOfBoundsException e) {
					e.printStackTrace();
				}
				if(bn == (bWidth * bHeight - 1))
					System.out.println("Bloque " + bn + " en x " + x + " en y " + y);
				nuevaImagen.setRGB(x, y, new Color(color, color, color).getRGB());
				where++;
			}
		}
		
		synchronized(formDecompression.progressBar) {
			formDecompression.progressBar.setValue(formDecompression.progressBar.getValue() + 1);
		}
		System.out.println("END: " + bn);
		
	}

}