package Parallel;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Forms.formDecompression;
import Others.Header;

public class ParallelImageGenerator implements Runnable{

	BufferedImage nuevaImagen;
	Header header;
	int where;
	ArrayList<Integer> decoded;
	int bn;
	int bx;
	int by;
	
	public ParallelImageGenerator(ArrayList<Integer> decoded, BufferedImage nuevaImagen, int where, Header header, int bn, int bx, int by) {
		this.nuevaImagen = nuevaImagen;
		this.where = where;
		this.decoded = decoded;
		this.header = header;
		this.bn = bn;
		this.by = by;
		this.bx = bx;
		System.out.println("GENERATING: " + bn);
		
	}
	@Override
	public void run() {
		
		// intert block
		for(int y = by; y < by + header.getY(bn); y++) {
			for(int x = bx; x < bx + header.getX(bn); x++) {
				int color = 0;
				try {
					color = decoded.get(where);
				}catch(IndexOutOfBoundsException e) {
					e.printStackTrace();
				}
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
