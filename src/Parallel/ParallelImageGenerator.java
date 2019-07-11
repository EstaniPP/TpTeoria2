package Parallel;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Forms.formDecompression;

public class ParallelImageGenerator implements Runnable{

	BufferedImage nuevaImagen;
	int x;
	int y;
	int pixel;
	ArrayList<Integer> decoded;
	
	public ParallelImageGenerator(ArrayList<Integer> decoded, BufferedImage nuevaImagen, int x, int y, int pixel) {
		this.nuevaImagen = nuevaImagen;
		this.x = x;
		this.y = y;
		this.pixel = pixel;
		this.decoded = decoded;
	}
	@Override
	public void run() {
		int color = 0;
		try {
			color = decoded.get(pixel);
		}catch(IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		nuevaImagen.setRGB(x, y, new Color(color, color, color).getRGB());
		synchronized(formDecompression.progressBar) {
			formDecompression.progressBar.setValue(formDecompression.progressBar.getValue() + 1);
		}
	}

}
