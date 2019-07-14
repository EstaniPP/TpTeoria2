package Others;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageParser {
	
	// no image setted up at the beginning
	private BufferedImage image = null;
	
	// size of blocks
	// if not setted up, default is 500px by 500px
	private int blockWidth = 500;
	private int blockHeight = 500;
	
	// image size in px
	private int width = 0;
	private int height = 0;
	
	public BufferedImage getBI() {
		return image;
	}
	// consturctor
	public ImageParser(File image){
		try {
			this.image = ImageIO.read(image); 
			width = this.image.getWidth();
			height = this.image.getHeight();
		} catch (IOException e) {
			//e.printStackTrace();
			throw new IllegalArgumentException("Image couldn't be loaded. Check path" + System.getProperty("user.dir"));
		}
	}
	
	public ImageParser(BufferedImage bi){
		image = bi;
		width = this.image.getWidth();
		height = this.image.getHeight();
	}
	
	public void setBlockSize(int bw, int bh) {
		blockWidth = bw;
		blockHeight = bh; 
	}
	
	// returns the number of blocks per column (block height in blocks)
	public int getHeightInBlocks() {
		int blocks = height / blockHeight;
		int mod = height % blockHeight;
		if(mod == 0) {
			return blocks;
		}else {
			return blocks + 1;
		}
	}
	
	// returns the number of blocks per row (block width in blocks)
	public int getWidthInBlocks() {
		int blocks = width / blockWidth;
		int mod = width % blockWidth;
		if(mod == 0) {
			return blocks;
		}else {
			return blocks + 1;
		}
	}
	
	public void breakItDown(int bw, int bh) {
		blockWidth = bw;
		blockHeight = bh;
	}
	
	// find the width of block at x and y
	public int blockWidth(int x, int y) {
		// if it's the last block it might be smaller
		if(getWidthInBlocks() - 1 == x) {
			int mod = width % blockWidth;
			if(mod != 0)
				return mod;
		}
		return blockWidth;
	}
	
	// find the width of block at x and y
	public int blockHeight(int x, int y) {
		// if it's the last block it might be smaller
		if(getHeightInBlocks() - 1 == y) {
			int mod = height % blockHeight;
			if(mod != 0)
				return mod;
		}
		return blockHeight;
	}
	
	// lets start looking at the image as a BufferedImage bidimentional matrix
	public ImageParser getBlock(int x, int y) {
		// get the upper left x cord of block
		int xCord = blockWidth * x;
		// get the upper left y cord of block
		int yCord = blockHeight * y;
		
		// get size
		int blockWidth = blockWidth(x, y);
		int blockHeight = blockHeight(x, y);
		//System.out.println("Block x " + x + " y " + y);
		//System.out.println("width " + blockWidth + " height " + blockHeight);
		return new ImageParser(image.getSubimage(xCord, yCord, blockWidth, blockHeight));		
	}
	
	public String saveBlock(int x, int y) {
		String path = String.valueOf(x) + String.valueOf(y) + ".bmp";
		try {
			ImageIO.write(getBlock(x, y).getBI(), "bmp", new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
	
	public Color getRGB(int i, int j) {
		return new Color(image.getRGB(i, j));
	}
	
	
	public ImageParser getBlock(int bNumber) {		
		int x = bNumber % getWidthInBlocks();
		int y = bNumber / getWidthInBlocks();
		return getBlock(x, y);
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	
}
